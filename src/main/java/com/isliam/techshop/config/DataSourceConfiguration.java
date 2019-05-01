package com.isliam.techshop.config;

import com.zaxxer.hikari.HikariDataSource;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
public class DataSourceConfiguration {

    @Bean(name = "techShopDataSource")
    @ConfigurationProperties(prefix="spring.datasource")
    public DataSource primaryDataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.type(HikariDataSource.class);
        dataSourceBuilder.url("jdbc:mysql://localhost:3306/TechShop?useUnicode=true&characterEncoding=utf8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC&rewriteBatchedStatements=true&relaxAutoCommit=true");
        dataSourceBuilder.username("root");
        dataSourceBuilder.password("STRONGpwd1$");
        return dataSourceBuilder.build();
    }

    @Primary
    @Bean(name = "flowableDataSource")
    @ConfigurationProperties(prefix="spring.seconddatasource")
    public DataSource secondaryDataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.type(HikariDataSource.class);
        dataSourceBuilder.url("jdbc:mysql://localhost:3306/flowable?useUnicode=true&characterEncoding=utf8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC&rewriteBatchedStatements=true&relaxAutoCommit=true");
        dataSourceBuilder.username("root");
        dataSourceBuilder.password("STRONGpwd1$");
        return dataSourceBuilder.build();
    }
}
