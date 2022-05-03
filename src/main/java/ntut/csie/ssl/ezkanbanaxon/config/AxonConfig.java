package ntut.csie.ssl.ezkanbanaxon.config;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.commandhandling.gateway.DefaultCommandGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "ntut.csie.ssl.ezkanbanaxon")
public class AxonConfig {

    @Bean
    CommandBus commandBus() {
        return SimpleCommandBus.builder().build();
    }

    @Bean
    CommandGateway commandGateway() {
        return DefaultCommandGateway.builder().commandBus(commandBus()).build();
    }
}
