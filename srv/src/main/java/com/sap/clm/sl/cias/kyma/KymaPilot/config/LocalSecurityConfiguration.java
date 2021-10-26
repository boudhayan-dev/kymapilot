package com.sap.clm.sl.cias.kyma.KymaPilot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@Profile({"dev"})
public class LocalSecurityConfiguration extends WebSecurityConfigurerAdapter  {

    @Override
    public void configure(final HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .anyRequest().permitAll().and().headers().frameOptions().disable();
        http.csrf().disable();

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/h2-console/**")
                .antMatchers("/h2server/**");
    }
}
