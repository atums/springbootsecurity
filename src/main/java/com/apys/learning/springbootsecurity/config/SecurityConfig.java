package com.apys.learning.springbootsecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;

    @Autowired
    public SecurityConfig(@Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // Стандартная конфигурация автризации
//        super.configure(http);

        // Настраеваем доступ к страницам по ролям.Кто и на какие URL имеет доступы
        http
                // Ставим защиту от CSRF угроз
                .csrf().disable()
                .authorizeRequests()
                // На рут доступ имеют все
                .antMatchers("/").permitAll()
                // Все что после api при обращении по определенному методу будет подет доступно по определенным РОЛИ
//                .antMatchers(HttpMethod.GET, "/api/**").hasAnyRole(Role.ADMIN.name(), Role.USER.name())
//                .antMatchers(HttpMethod.POST, "/api/**").hasRole(Role.ADMIN.name())
//                .antMatchers(HttpMethod.DELETE, "/api/**").hasRole(Role.ADMIN.name())
                // Доступ будет предостовляться по пермишинам (определенным в Role и Permission)
//                .antMatchers(HttpMethod.GET, "/api/**").hasAuthority(Permission.DEVELOPERS_READ.getPermission())
//                .antMatchers(HttpMethod.POST, "/api/**").hasAuthority(Permission.DEVELOPERS_WRITE.getPermission())
//                .antMatchers(HttpMethod.DELETE, "/api/**").hasAuthority(Permission.DEVELOPERS_WRITE.getPermission())
                // Говорим, что каждый запрос должен быть аутентифицирован
                .anyRequest()
                .authenticated()
                //С помощью HTTP Basic
                .and()
                //Стандартная форма логина
//                .httpBasic();
                // Настраеваем доступ к странице Логина
                .formLogin()
                .loginPage("/auth/login").permitAll()
                .defaultSuccessUrl("/auth/success")
                .and()
                // Настройка Логаут
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/auth/loguot", "POST"))
                // Прекращение сессии с Логином
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                // Очистка кукки данной сессии
                .deleteCookies("JSESSIONID")
                // Куда перенаправляем после Логаута
                .logoutSuccessUrl("/auth/login");

    }
//    @Bean
//    @Override
//    public UserDetailsService userDetailsService() {
//        //Тут говорим о том, что у нас будут пользователи пересоздаваемый при запуске софта (юзер в памяти)
//        return new InMemoryUserDetailsManager(
//                User.builder()
//                        .username("admin")
//                        // Шифруем чтобы не передавать пароль в открытом виде
//                        .password(passwordEncoder().encode("admin"))
//                        // Присвоение Роли пользователю
////                        .roles(Role.ADMIN.name())
//                        // Присвоение роли на основе Пермишин
//                        .authorities(Role.ADMIN.getAuthorities())
//                        .build(),
//                User.builder()
//                        .username("user")
//                        .password(passwordEncoder().encode("user"))
//                        // Присвоение Роли пользователю
////                        .roles(Role.USER.name())
//                        // Присвоение роли на основе Пермишин
//                        .authorities(Role.USER.getAuthorities())
//                        .build()
//        );
//    }

    // Настройка для пользователей хранимых в DB
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    // Шифруем пароль
    @Bean
    protected PasswordEncoder passwordEncoder() {
        //Сила шиврования
        return new BCryptPasswordEncoder(12);
    }
    // Настройка для пользователей хранимых в DB
    @Bean
    protected DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);

        return daoAuthenticationProvider;
    }
}
