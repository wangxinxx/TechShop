package com.isliam.techshop.config;

import com.zaxxer.hikari.HikariDataSource;
import org.flowable.common.engine.api.FlowableException;
import org.flowable.common.engine.impl.AbstractEngineConfiguration;
import org.flowable.common.spring.SpringEngineConfiguration;
import org.flowable.dmn.spring.SpringDmnEngineConfiguration;
import org.flowable.dmn.spring.SpringDmnExpressionManager;
import org.flowable.dmn.spring.configurator.SpringDmnEngineConfigurator;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.engine.spring.configurator.SpringProcessEngineConfigurator;
import org.flowable.form.spring.configurator.SpringFormEngineConfigurator;
import org.flowable.spring.SpringExpressionManager;
import org.flowable.spring.SpringProcessEngineConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

//@Configuration

public class FlowableConfig {

//    @Override
//    public void configure(AbstractEngineConfiguration engineConfiguration) {
//        if (processEngineConfiguration == null) {
//            processEngineConfiguration = new SpringProcessEngineConfiguration();
//        }
//
//        if (!(processEngineConfiguration instanceof SpringProcessEngineConfiguration)) {
//            throw new FlowableException("SpringProcessEngineConfigurator accepts only SpringProcessEngineConfiguration. " + processEngineConfiguration.getClass().getName());
//        }
//
////        engineConfiguration.setDataSource(getDataSource());
//        initialiseCommonProperties(engineConfiguration, processEngineConfiguration);
//
//        SpringEngineConfiguration springEngineConfiguration = (SpringEngineConfiguration) engineConfiguration;
//        SpringProcessEngineConfiguration springProcessEngineConfiguration = (SpringProcessEngineConfiguration) processEngineConfiguration;
//        springProcessEngineConfiguration.setTransactionManager(springEngineConfiguration.getTransactionManager());
//        springProcessEngineConfiguration.setExpressionManager(new SpringExpressionManager(
//            springEngineConfiguration.getApplicationContext(), springEngineConfiguration.getBeans()));
//
//        initProcessEngine();
//
//        initServiceConfigurations(engineConfiguration, processEngineConfiguration);
//    }
//
//    @Bean(name = "dataSource")
//    public DataSource getDataSource1() {
//        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
//        dataSourceBuilder.type(HikariDataSource.class);
//        dataSourceBuilder.url("jdbc:mysql://localhost:3306/xyu?useUnicode=true&characterEncoding=utf8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC&rewriteBatchedStatements=true&relaxAutoCommit=true");
//        dataSourceBuilder.username("root");
//        dataSourceBuilder.password("STRONGpwd1$");
//        return dataSourceBuilder.build();
//    }
//
//    @Bean(name = "barDataSource")
//    public DataSource getDataSource2() {
//        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
//        dataSourceBuilder.type(HikariDataSource.class);
//        dataSourceBuilder.url("jdbc:mysql://localhost:3306/xyu?useUnicode=true&characterEncoding=utf8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC&rewriteBatchedStatements=true&relaxAutoCommit=true");
//        dataSourceBuilder.username("root");
//        dataSourceBuilder.password("STRONGpwd1$");
//        return dataSourceBuilder.build();
//    }
//
//
//    @Bean(name = "barDataSource")
//    @ConfigurationProperties(prefix = "bar.datasource")
//    public DataSource dataSource() {
//        return DataSourceBuilder.create().build();
//    }
//
//    @Autowired
//    @Bean(name = "barEntityManagerFactory")
//    public LocalContainerEntityManagerFactoryBean
//    barEntityManagerFactory(
//        EntityManagerFactoryBuilder builder,
//        @Qualifier("barDataSource") DataSource dataSource
//    ) {
//        return
//            builder
//                .dataSource(dataSource)
//                .packages("org.flowable.common.engine.impl")
//                .persistenceUnit("bar")
//                .build();
//    }
//
//    @Autowired
//    @Bean(name = "barTransactionManager")
//    public PlatformTransactionManager barTransactionManager(
//        @Qualifier("barEntityManagerFactory") EntityManagerFactory
//            barEntityManagerFactory
//    ) {
//        return new JpaTransactionManager(barEntityManagerFactory);
//    }


    //    @Bean
//    ProcessEngineConfiguration processEngineConfiguration(){
//        ProcessEngineConfiguration cfg = org.flowable.spring.SpringProcessEngineConfiguration.createStandaloneProcessEngineConfiguration()
//            .setJdbcUrl("jdbc:mysql://localhost:3306/xyu?useUnicode=true&characterEncoding=utf8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC")
//            .setJdbcUsername("root")
//            .setJdbcPassword("STRONGpwd1$").setDatabaseType("com.zaxxer.hikari.HikariDataSource")
//            .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
//
//        return cfg;
//    }
}
