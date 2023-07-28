package clutch.clutchserver;

import clutch.clutchserver.building.entity.Building;
import clutch.clutchserver.global.common.enums.Type;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@ConfigurationPropertiesScan
public class ClutchserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClutchserverApplication.class, args);
	}

}
