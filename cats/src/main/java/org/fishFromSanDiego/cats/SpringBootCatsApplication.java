package org.fishFromSanDiego.cats;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "org.FishFromSanDiego.cats.repositories")
public class SpringBootCatsApplication {
}
