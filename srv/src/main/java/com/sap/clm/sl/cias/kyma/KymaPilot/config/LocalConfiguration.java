package com.sap.clm.sl.cias.kyma.KymaPilot.config;

import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.context.annotation.RequestScope;

import javax.sql.DataSource;

@Configuration
@Profile({ "dev" })
@PropertySource("classpath:application.properties")
public class LocalConfiguration {

    @Value("${spring.datasource.url}")
    String url;

    @Value("${spring.datasource.username}")
    String username;

    @Value("${spring.datasource.password}")
    String password;

    @Value("${spring.datasource.driverClassName}")
    String driver;

    private static final Logger oLogger = LoggerFactory.getLogger(LocalConfiguration.class);

    @Bean
    @RequestScope
    public UserInfo getUserInfo() {
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername("user");
        userInfo.setEmail("user");
        userInfo.setTenantGuid("localTenant");
        userInfo.setTenantName("localTenant");
        userInfo.setToken("local");
        return userInfo;

    }

    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create().driverClassName(driver).url(url).username(username).password(password)
                .build();
    }
}
