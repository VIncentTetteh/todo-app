package com.chrisbone.todolist.v1.configs;

import com.chrisbone.todolist.v1.configs.UserConfig;
import com.chrisbone.todolist.v1.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserManagerConfig implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .map(UserConfig::new)
                .orElseThrow(() -> new UsernameNotFoundException("UserEmail: "+ email+ " does not exist"));
    }
}
