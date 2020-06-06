package com.katzendorn.demo.config;

import com.katzendorn.demo.config.handler.LoginSuccessHandler;
import com.katzendorn.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserService userService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
//                .headers().frameOptions().sameOrigin().disable()
//                .headers().frameOptions().sameOrigin().and().hsts().disable();
                .authorizeRequests()
//                .antMatchers("/registration").not().fullyAuthenticated()
                .antMatchers("/api/v1/**").permitAll()
                .antMatchers("/", "/login", "/static/**/*").permitAll()
                .antMatchers("/admin/**", "/registration").hasRole("ADMIN")
                .antMatchers("/user").hasRole("USER")
                //use authentification
        .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .successHandler(new LoginSuccessHandler())
                .permitAll();

//        httpSecurity.ignoring().antMatchers("/resources/**/*", "/resources/**", "/resources/css/**");



        http.logout()
                .permitAll()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout")
                .and().csrf().disable();
    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**", "/static/**/*","/webjars/**", "/css/*");
    }

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder());
    }
}
