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
        userInfo.setUsername("boudhayan.dev@sap.com");
        userInfo.setEmail("boudhayan.dev@sap.com");
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

//        @Bean
//        public DataSource dataSource() {
//
//            oLogger.debug("Retrieving the Datasource Bean");
//            String driver = "com.sap.db.jdbc.Driver";
//            String url = "jdbc:sap://8999d4a8-25a3-4f76-8e99-a136c53e276b.hana.canary-eu10.hanacloud.ondemand.com:443?encrypt=true&validateCertificate=true&currentschema=USR_CMPO0MAON4404WGTIEXUUIK1Y";
//            String username = "USR_CMPO0MAON4404WGTIEXUUIK1Y";
//            String password = "Hg5UC8mVqMr0kuPUk_M_dsB0cR733aWBe7vOKCjmP8t.b9prnyQatNPXdDg-cb-7QEH.i7Ao061s1BsTCOPFraQTiswiY-_vrAW0uJvlDzpsEgeuGzjo0GPcb0PbpTUA";
//
//
//            HikariDataSource dataSource = new HikariDataSource();
//            dataSource.setUsername(username);
//            dataSource.setPassword(password);
//            dataSource.setJdbcUrl(url);
//            dataSource.setDriverClassName(driver);
//            dataSource.setMaximumPoolSize(20);
//            return dataSource;
//        }
}
