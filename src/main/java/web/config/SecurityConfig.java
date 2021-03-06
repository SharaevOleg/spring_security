package web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.CharacterEncodingFilter;
import web.config.handler.LoginSuccessHandler;
import web.service.IUserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private IUserService userDetailsService;
    @Autowired
    private LoginSuccessHandler loginSuccessHandler;

    @Bean
    public DaoAuthenticationProvider daoAuthProvider() {
        DaoAuthenticationProvider daoAuthProvider = new DaoAuthenticationProvider();
        daoAuthProvider.setUserDetailsService(userDetailsService);
        daoAuthProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(daoAuthProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        http.addFilterBefore(filter, CsrfFilter.class);
        http.formLogin()
                // ?????????????????? ???????????????? ?? ???????????? ????????????
                .loginPage("/login")
                //?????????????????? ???????????? ?????????????????? ?????? ????????????
                .successHandler(loginSuccessHandler)
                // ?????????????????? action ?? ?????????? ????????????
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/")
                // ?????????????????? ?????????????????? ???????????? ?? ???????????? ?? ?????????? ????????????
                .usernameParameter("email")
                .passwordParameter("password")
                // ???????? ???????????? ?? ?????????? ???????????? ????????
                .permitAll();

        http.logout()
                .permitAll()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/login?logout")
                //?????????????????? ?????????????????????????? ?????????????????????? (???? ?????????? ???????????????? ??????????????)
                .and()
                .csrf().disable();
        http
                .authorizeRequests()
                .antMatchers("/admin/**").hasAnyAuthority("ROLE_ADMIN")
                .antMatchers("/user/**").hasAnyAuthority("ROLE_USER")
                .antMatchers("/login").anonymous()
                .anyRequest().authenticated();
//                .and()
//                .formLogin().and().rememberMe().tokenValiditySeconds(5000);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

}
