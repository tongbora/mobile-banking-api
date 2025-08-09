package org.istad.mbanking.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.istad.mbanking.domain.User;
import org.istad.mbanking.features.user.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUuid(username).orElseThrow(
                () -> new UsernameNotFoundException("User not found")
        );
//        log.info("User found: {}", user);

        CustomUserDetail userDetail = new CustomUserDetail();
        userDetail.setUser(user);
//        log.info("UserDetail created: {}", userDetail);

        return userDetail;
    }
}
