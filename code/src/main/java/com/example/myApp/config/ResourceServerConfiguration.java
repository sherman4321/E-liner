//package com.example.myApp.config;
//
//import com.example.myApp.service.ServiceImpl;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
//import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
//
////@EnableWebSecurity
////@EnableGlobalMethodSecurity(securedEnabled = true)
//@Configuration
//@EnableResourceServer
//public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
//
//    @Autowired
//    private ServiceImpl serviceImpl;
//
//    public ResourceServerConfiguration() {
//        super();
//    }
//
//    @Bean
//    public AuthenticationProvider authProvider(){
//        final DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(serviceImpl);
//        return authProvider;
//    }
//
//@Override
//public void configure(ResourceServerSecurityConfigurer resources) {
//    resources.resourceId("resource_id").stateless(false);
//}
//
//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        http
//                .anonymous().disable()
//                .authorizeRequests()
//                .antMatchers("/admins/**", "/products/**").access("hasRole('ADMIN')")
//                .and()
//                .exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
//    }
//
//}