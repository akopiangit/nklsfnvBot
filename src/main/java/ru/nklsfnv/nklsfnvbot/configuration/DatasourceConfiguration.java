package ru.nklsfnv.nklsfnvbot.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = "ru.nklsfnv.nklsfnvbot.model.entity")
@EnableJpaRepositories(basePackages = "ru.nklsfnv.nklsfnvbot.repository")
public class DatasourceConfiguration {
}
