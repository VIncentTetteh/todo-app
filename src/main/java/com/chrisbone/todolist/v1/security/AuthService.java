package com.chrisbone.todolist.v1.security;

import com.chrisbone.todolist.v1.dto.objectMappers.UserMapper;
import com.chrisbone.todolist.v1.dto.requests.UserRequestDTO;
import com.chrisbone.todolist.v1.dto.responses.AuthResponseDTO;
import com.chrisbone.todolist.v1.enums.TokenType;
import com.chrisbone.todolist.v1.models.RefreshToken;
import com.chrisbone.todolist.v1.models.User;
import com.chrisbone.todolist.v1.repositories.RefreshTokenRepository;
import com.chrisbone.todolist.v1.repositories.UserRepository;
import com.chrisbone.todolist.v1.security.jwtAuth.JwtTokenGenerator;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final UserRepository userRepository;
    private final JwtTokenGenerator jwtTokenGenerator;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserMapper userMapper;

    public AuthResponseDTO getJwtTokensAfterAuthentication(Authentication authentication, HttpServletResponse response) {
        try
        {
            String accessToken = jwtTokenGenerator.generateAccessToken(authentication);
            String refreshToken = jwtTokenGenerator.generateRefreshToken(authentication);

            var userInfo = userRepository.findByEmail(authentication.getName())
                    .orElseThrow(()->{
                        log.error("[AuthService:userSignInAuth] User :{} not found",authentication.getName());
                        return new ResponseStatusException(HttpStatus.NOT_FOUND,"USER NOT FOUND ");});


            saveUserRefreshToken(userInfo,refreshToken);
            creatRefreshTokenCookie(response,refreshToken);


            log.info("[AuthService:userSignInAuth] Access token for user:{}, has been generated",userInfo.getUsername());
            return  AuthResponseDTO.builder()
                    .accessToken(accessToken)
                    .accessTokenExpiry(60)
                    .userName(userInfo.getUsername())
                    .tokenType(TokenType.Bearer)
                    .build();


        }catch (Exception e){
            log.error("[AuthService:userSignInAuth]Exception while authenticating the user due to :"+e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Please Try Again");
        }


    }

    public Object getAccessTokenUsingRefreshToken(String authorizationHeader) {

        if(!authorizationHeader.startsWith(TokenType.Bearer.name())){
            return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Please verify your token type");
        }

        final String refreshToken = authorizationHeader.substring(7);

        var refreshTokenEntity = refreshTokenRepository.findByRefreshToken(refreshToken)
                .filter(tokens-> !tokens.isRevoked())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Refresh token revoked"));

        User userInfo = refreshTokenEntity.getUser();

        Authentication authentication =  createAuthenticationObject(userInfo);

        String accessToken = jwtTokenGenerator.generateAccessToken(authentication);

        return  AuthResponseDTO.builder()
                .accessToken(accessToken)
                .accessTokenExpiry(5 * 60)
                .userName(userInfo.getUsername())
                .tokenType(TokenType.Bearer)
                .build();
    }


    public AuthResponseDTO registerUser(UserRequestDTO userRegistrationDto, HttpServletResponse httpServletResponse){

        try{
            log.info("[AuthService:registerUser]User Registration Started with :::{}",userRegistrationDto);

            Optional<User> user = userRepository.findByEmail(userRegistrationDto.email());
            if(user.isPresent()){
                throw new Exception("User Already Exist");
            }

            User userDetailsEntity = userMapper.convertToUser(userRegistrationDto);
            Authentication authentication = createAuthenticationObject(userDetailsEntity);


            String accessToken = jwtTokenGenerator.generateAccessToken(authentication);
            String refreshToken = jwtTokenGenerator.generateRefreshToken(authentication);

            User savedUserDetails = userRepository.save(userDetailsEntity);
            saveUserRefreshToken(userDetailsEntity,refreshToken);

            creatRefreshTokenCookie(httpServletResponse,refreshToken);

            log.info("[AuthService:registerUser] User:{} Successfully registered",savedUserDetails.getUsername());
            return   AuthResponseDTO.builder()
                    .accessToken(accessToken)
                    .accessTokenExpiry(5 * 60)
                    .userName(savedUserDetails.getUsername())
                    .tokenType(TokenType.Bearer)
                    .build();


        }catch (Exception e){
            log.error("[AuthService:registerUser]Exception while registering the user due to :"+e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }

    }

    private void saveUserRefreshToken(User user, String refreshToken) {
        var refreshTokenEntity = RefreshToken.builder()
                .user(user)
                .refreshToken(refreshToken)
                .revoked(false)
                .build();
        refreshTokenRepository.save(refreshTokenEntity);
    }

    private Cookie creatRefreshTokenCookie(HttpServletResponse response, String refreshToken) {
        Cookie refreshTokenCookie = new Cookie("refresh_token",refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true);
        refreshTokenCookie.setMaxAge(15 * 24 * 60 * 60 );
        response.addCookie(refreshTokenCookie);
        return refreshTokenCookie;
    }

    private static Authentication createAuthenticationObject(User user) {
        String username = user.getEmail();
        String password = user.getPassword();
        String roles = user.getRoles().name();

        String[] roleArray = roles.split(",");
        GrantedAuthority[] authorities = Arrays.stream(roleArray)
                .map(role -> (GrantedAuthority) role::trim)
                .toArray(GrantedAuthority[]::new);

        return new UsernamePasswordAuthenticationToken(username, password, Arrays.asList(authorities));
    }
}
