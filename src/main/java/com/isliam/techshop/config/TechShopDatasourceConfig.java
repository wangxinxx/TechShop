package com.isliam.techshop.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

//    @Bean(name = "techShopDataSource")
//    @ConfigurationProperties(prefix="spring.datasource")
//    public DataSource primaryDataSource() {
//        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
//        dataSourceBuilder.driverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//        dataSourceBuilder.url("jdbc:sqlserver://kyrsa4.database.windows.net:1433;database=TechShop;user=root1@kyrsa4;password=STRONGpwd1$;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;");
//        dataSourceBuilder.username("root1");
//        dataSourceBuilder.password("STRONGpwd1$");
//        return dataSourceBuilder.build();
//    }
//
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
