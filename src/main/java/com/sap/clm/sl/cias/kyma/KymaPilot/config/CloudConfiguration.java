package com.sap.clm.sl.cias.kyma.KymaPilot.config;

import com.sap.cloud.security.xsuaa.token.SpringSecurityContext;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.context.annotation.RequestScope;

import javax.sql.DataSource;

@Configuration
@Profile({"cloud"})
@PropertySource("classpath:application.properties")
public class CloudConfiguration {

    private static final Logger oLogger = LoggerFactory.getLogger(CloudConfiguration.class);


    @Bean
    @RequestScope
    public UserInfo getUserInfo() {
        oLogger.debug("Retrieving the UserInfo Bean");
        UserInfo userInfo = new UserInfo();
        try {
            userInfo.setUsername(SpringSecurityContext.getToken().getLogonName());
            userInfo.setEmail(SpringSecurityContext.getToken().getEmail());
            userInfo.setTenantGuid(SpringSecurityContext.getToken().getZoneId());
            userInfo.setTenantName(SpringSecurityContext.getToken().getSubdomain());
            userInfo.setToken(SpringSecurityContext.getToken().getAppToken());
            userInfo.setFirstName(SpringSecurityContext.getToken().getGivenName());
            userInfo.setLastName(SpringSecurityContext.getToken().getFamilyName());
            userInfo.setTechnicalUser(false);
        } catch (AccessDeniedException e) {
            oLogger.debug("Request is from Technical User and does not have the Principal Token");
        }

        return userInfo;
    }


}
