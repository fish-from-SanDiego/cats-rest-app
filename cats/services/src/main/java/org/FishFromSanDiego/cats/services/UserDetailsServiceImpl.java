package org.FishFromSanDiego.cats.services;

import org.FishFromSanDiego.cats.dto.ApplicationUserDetails;
import org.FishFromSanDiego.cats.models.ApplicationUser;
import org.FishFromSanDiego.cats.models.CustomAppProperties;
import org.FishFromSanDiego.cats.models.UserRoleType;
import org.FishFromSanDiego.cats.repositories.ApplicationUsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final ApplicationUsersRepository applicationUsersRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomAppProperties customAppProperties;

    @Autowired
    public UserDetailsServiceImpl(
            ApplicationUsersRepository applicationUsersRepository,
            PasswordEncoder passwordEncoder,
            CustomAppProperties customAppProperties) {
        this.applicationUsersRepository = applicationUsersRepository;
        this.passwordEncoder = passwordEncoder;
        this.customAppProperties = customAppProperties;
        if (applicationUsersRepository.count() == 0) {
            applicationUsersRepository.save(ApplicationUser
                    .builder()
                    .password(passwordEncoder.encode(customAppProperties.getDefaultAdminPassword()))
                    .username(customAppProperties.getDefaultAdminUsername())
                    .userRoleType(UserRoleType.ROLE_ADMIN)
                    .build());
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = applicationUsersRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("no user with such username"));
        return ApplicationUserDetails
                .builder()
                .id(user.getId())
                .password(user.getPassword())
                .username(user.getUsername())
                .userRoleType(user.getUserRoleType())
                .build();
    }
}
