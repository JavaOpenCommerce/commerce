package com.example;

import io.quarkus.arc.Arc;
import io.quarkus.runtime.StartupEvent;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.sql.DataSource;
import lombok.extern.log4j.Log4j2;
import org.flywaydb.core.Flyway;

@Log4j2
@ApplicationScoped
public class DBMigrationService {

    private final Flyway flyway;

    public DBMigrationService(Flyway container) {
        this.flyway = container;
    }

    void onStart(@Observes StartupEvent ev) {
        Arc.container().instance(DataSource.class).get();

        log.info("LOG: flyway, version: {};, cfg: {}, script: {}"); //,this.flyway.getFlyway().info().current().getVersion().toString(), this.flyway.getFlyway().getConfiguration(), this.flyway.getFlyway().info().current().getScript());
    }
}
