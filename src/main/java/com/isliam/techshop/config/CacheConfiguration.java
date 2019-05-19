package com.isliam.techshop.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import io.github.jhipster.config.jcache.BeanClassLoaderAwareJCacheRegionFactory;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        BeanClassLoaderAwareJCacheRegionFactory.setBeanClassLoader(this.getClass().getClassLoader());
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(com.isliam.techshop.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(com.isliam.techshop.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(com.isliam.techshop.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.isliam.techshop.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.isliam.techshop.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.isliam.techshop.domain.Property.class.getName(), jcacheConfiguration);
            cm.createCache(com.isliam.techshop.domain.Product.class.getName(), jcacheConfiguration);
            cm.createCache(com.isliam.techshop.domain.Product.class.getName() + ".properties", jcacheConfiguration);
            cm.createCache(com.isliam.techshop.domain.Property.class.getName() + ".products", jcacheConfiguration);
            cm.createCache(com.isliam.techshop.domain.Item.class.getName(), jcacheConfiguration);
            cm.createCache(com.isliam.techshop.domain.ItemPropertyString.class.getName(), jcacheConfiguration);
            cm.createCache(com.isliam.techshop.domain.ItemPropertyInt.class.getName(), jcacheConfiguration);
            cm.createCache(com.isliam.techshop.domain.ItemPropertyFloat.class.getName(), jcacheConfiguration);
            cm.createCache(com.isliam.techshop.domain.ItemPropertyBool.class.getName(), jcacheConfiguration);
            cm.createCache(com.isliam.techshop.domain.ItemPropertyDouble.class.getName(), jcacheConfiguration);
            cm.createCache(com.isliam.techshop.domain.Passport.class.getName(), jcacheConfiguration);
            cm.createCache(com.isliam.techshop.domain.Profile.class.getName(), jcacheConfiguration);
            cm.createCache(com.isliam.techshop.domain.Permission.class.getName(), jcacheConfiguration);
            cm.createCache(com.isliam.techshop.domain.Position.class.getName(), jcacheConfiguration);
            cm.createCache(com.isliam.techshop.domain.Permission.class.getName() + ".positions", jcacheConfiguration);
            cm.createCache(com.isliam.techshop.domain.Position.class.getName() + ".permissions", jcacheConfiguration);
            cm.createCache(com.isliam.techshop.domain.Profile.class.getName() + ".passports", jcacheConfiguration);
            cm.createCache(com.isliam.techshop.domain.Notification.class.getName(), jcacheConfiguration);
            cm.createCache(com.isliam.techshop.domain.Operation.class.getName(), jcacheConfiguration);
            cm.createCache(com.isliam.techshop.domain.Country.class.getName(), jcacheConfiguration);
            cm.createCache(com.isliam.techshop.domain.City.class.getName(), jcacheConfiguration);
            cm.createCache(com.isliam.techshop.domain.Region.class.getName(), jcacheConfiguration);
            cm.createCache(com.isliam.techshop.domain.Address.class.getName(), jcacheConfiguration);
            cm.createCache(com.isliam.techshop.domain.Manufacturer.class.getName(), jcacheConfiguration);
            cm.createCache(com.isliam.techshop.domain.Manufacturer.class.getName() + ".items", jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
