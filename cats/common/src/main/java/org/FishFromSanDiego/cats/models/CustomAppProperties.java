package org.FishFromSanDiego.cats.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("app.admin.default")
@Getter
@Setter
public class CustomAppProperties {
    private String username;
    private String password;
}
