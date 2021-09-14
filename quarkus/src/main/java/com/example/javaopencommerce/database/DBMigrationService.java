package com.example.javaopencommerce.database;

import io.quarkus.runtime.StartupEvent;
import lombok.extern.log4j.Log4j2;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.flywaydb.core.Flyway;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

@Log4j2
@ApplicationScoped
public class DBMigrationService {

    @ConfigProperty(name = "quarkus.datasource.reactive.url")
    String datasourceUrl;

    @ConfigProperty(name = "quarkus.datasource.username")
    String datasourceUsername;

    @ConfigProperty(name = "quarkus.datasource.password")
    String datasourcePassword;

    public void runFlywayMigration(@Observes StartupEvent event) {
        Flyway flyway = Flyway.configure().dataSource("jdbc:" + this.datasourceUrl, this.datasourceUsername, this.datasourcePassword).load();
        flyway.migrate();

        log.info("LOG: flyway, version: {};, cfg: {}, script: {}", flyway.info().current().getVersion().toString(), flyway.getConfiguration(), flyway.info().current().getScript());
    }
}
