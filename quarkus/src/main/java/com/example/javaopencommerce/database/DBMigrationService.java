package com.example.javaopencommerce.database;

import io.quarkus.arc.properties.IfBuildProperty;
import io.quarkus.runtime.StartupEvent;
import lombok.extern.log4j.Log4j2;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.flywaydb.core.Flyway;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

@Log4j2
@ApplicationScoped
@IfBuildProperty(name = "quarkus.datasource.jdbc", stringValue = "true")
public class DBMigrationService {

  @ConfigProperty(name = "quarkus.datasource.reactive.url")
  String datasourceUrl;

  @ConfigProperty(name = "quarkus.datasource.username")
  String datasourceUsername;

  @ConfigProperty(name = "quarkus.datasource.password")
  String datasourcePassword;

  public void runFlywayMigration(@Observes StartupEvent event) {
    String jdbcUrl = "jdbc:" + this.datasourceUrl;
    log.info("Migrating with flyway, and database: {}", jdbcUrl);
    Flyway flyway = Flyway.configure()
        .dataSource(jdbcUrl, this.datasourceUsername, this.datasourcePassword)
        .load();
    flyway.migrate();

    log.info("LOG: flyway, version: {};, cfg: {}, script: {}",
        flyway.info().current().getVersion().toString(), flyway.getConfiguration(),
        flyway.info().current().getScript());
  }
}
