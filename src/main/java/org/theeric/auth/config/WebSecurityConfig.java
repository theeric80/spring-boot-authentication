package org.theeric.auth.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.theeric.auth.core.web.authentication.Http401UnauthorizedEntryPoint;
import org.theeric.auth.core.web.authentication.Http403AccessDeniedHandler;
import org.theeric.auth.core.web.authentication.TokenAuthenticationFilter;
import org.theeric.auth.core.web.authentication.TokenAuthenticationProvider;
import org.theeric.auth.core.web.authentication.TokenDetailsService;
import org.theeric.auth.user.service.TokenDetailsServiceImpl;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // @formatter:off
        auth
            .authenticationProvider(authenticationProvider());
        // @formatter:on 
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
            .cors()
                .and()
            .csrf()
                .disable()
            .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
            .addFilterAfter(tokenAuthenticationFilter(), BasicAuthenticationFilter.class)
            .exceptionHandling()
                .authenticationEntryPoint(new Http401UnauthorizedEntryPoint())
                .accessDeniedHandler(new Http403AccessDeniedHandler())
                .and()
            .authorizeRequests()
                .antMatchers("/api/auth/**").permitAll()
                .antMatchers("/api/**").authenticated()
                .anyRequest().permitAll();
        // @formatter:on 
    }

    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() throws Exception {
        return new TokenAuthenticationFilter(authenticationManager());
    }

    @Bean
    public TokenDetailsService tokenDetailsService() {
        return new TokenDetailsServiceImpl();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        return new TokenAuthenticationProvider(tokenDetailsService());
    }


}
