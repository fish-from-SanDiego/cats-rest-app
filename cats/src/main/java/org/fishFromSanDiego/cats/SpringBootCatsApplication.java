package org.fishFromSanDiego.cats;

import org.FishFromSanDiego.cats.formatters.StringToColourConverter;
import org.FishFromSanDiego.cats.formatters.StringToFriendshipTypeConverter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableConfigurationProperties
@EnableJpaRepositories("org.FishFromSanDiego.cats.repositories")
@EntityScan("org.FishFromSanDiego.cats.models")
@ComponentScan("org.FishFromSanDiego.cats")
public class SpringBootCatsApplication {
    @Bean
    public StringToColourConverter stringToColourConverter() {
        return new StringToColourConverter();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public StringToFriendshipTypeConverter stringToFriendshipTypeConverter() {
        return new StringToFriendshipTypeConverter();
    }
}
