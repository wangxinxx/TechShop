package com.isliam.techshop.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    entityManagerFactoryRef = "entityManagerFactory",
    transactionManagerRef = "transactionManager",
    basePackages = { "com.isliam.techshop.repository" })
public class TechShopDatasourceConfig {




    @Bean("techShopDataSourceProperties")
    @ConfigurationProperties("spring.datasource")
    public DataSourceProperties techShopDatasourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "techShopDataSource")
    public DataSource devDataSource(@Qualifier("techShopDataSourceProperties") DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().build();
    }



    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean techShopEntityManagerFactory(
        EntityManagerFactoryBuilder builder,
        @Qualifier("techShopDataSource") DataSource techShopDataSource) {
        return builder
            .dataSource(techShopDataSource)
            .packages("com.isliam.techshop.domain")
            .persistenceUnit("bar")
            .build();
    }

    @Bean(name = "transactionManager")
    public PlatformTransactionManager techShopTransactionManager(
        @Qualifier("entityManagerFactory") EntityManagerFactory barEntityManagerFactory) {
        return new JpaTransactionManager(barEntityManagerFactory);
    }

}
