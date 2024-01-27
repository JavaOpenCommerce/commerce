package com.example.opencommerce.infra;

import io.quarkus.runtime.StartupEvent;
import io.quarkus.runtime.configuration.ConfigUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import lombok.extern.log4j.Log4j2;
import org.eclipse.microprofile.config.ConfigProvider;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Purpose of this class is purely information. It's aim to log active quarkus profiles.
 */
@Log4j2
@ApplicationScoped
public class ApplicationLifecycleManager {

    void onStart(@Observes StartupEvent ev) {
        log.info("The application is starting with profile {}", ConfigUtils.getProfiles());
        log.trace("Lunch mode: {}, and properties: {}", io.quarkus.runtime.LaunchMode.current(),
                StreamSupport.stream(ConfigProvider.getConfig()
                                .getPropertyNames()
                                .spliterator(), false)
                        .map(name -> name + "->" + ConfigProvider.getConfig()
                                .getOptionalValue(name, String.class)
                                .orElse(null))
                        .sorted()
                        .collect(Collectors.joining("\n")));
    }
}
