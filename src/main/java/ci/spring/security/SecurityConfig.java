/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.spring.security;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author USER
 */
@Configuration // pour dire que cest un fichier de configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    public DataSource dataSource;

    // cette methode permet de gerer l'authentification
    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {

        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery("select username, password, enabled from conseiller where username=?") /*elle nous permet de recuperer un certain nombre d'utilisateur*/
                .authoritiesByUsernameQuery("select username,role from conseiller_roles where username=?")
            
                .passwordEncoder(new BCryptPasswordEncoder());

    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/resources/**").permitAll()
                .antMatchers("/assets/**").permitAll()
                .antMatchers("/login").permitAll()

                .anyRequest().authenticated()
                .and()
                .formLogin() // pour utiler la page login par default
                .loginPage("/login") /*pour notre propre page login qui se trouve a la racine /login*/
                .and()
                .logout()/*pour la deconnexion, on retour a la page login*/
                .permitAll()
                .and()
                .csrf().disable();
        http.exceptionHandling().accessDeniedPage("/403");

    }
}
