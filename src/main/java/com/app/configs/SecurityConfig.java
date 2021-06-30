package com.app.configs;


//import com.app.service.CustomerUserDetailService;
import com.app.service.CustomSuccessHandler;
import com.app.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService customerUserDetailService;

    @Autowired
    private CustomSuccessHandler customSuccessHandler;

    //mã hoá password theo kiểu bcrypt
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //đây là cách lưu user trên session và quản lý các loại chứng thực
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        //tạo ra 1 session và đưa vào memory
//        auth.inMemoryAuthentication()
//                .withUser("user1").password(passwordEncoder().encode("123456"))
//                .authorities("ROLE_USER");

        // mã khoá khi đăng nhập
//        auth.userDetailsService(customerUserDetailService).passwordEncoder(passwordEncoder());

        //chưa mã hoá để test
        auth.userDetailsService(customerUserDetailService).passwordEncoder(NoOpPasswordEncoder.getInstance());
    }

//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        //chặn request của nơi khác đến file tron resource chắc về bảo mật hiện tại chưa cần thiết
//        web.ignoring().antMatchers("/resources/**");
//    }

    //cấu hình đường dẫn cho phép hay bị chặn + login logout + jwt
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(
//                        "/",
                        "/home"
                        ,"/static/**","/css/**","/fontawesome-free-5.15.3-web/**","/img/**"
                ).permitAll()
                //cho qua đối với các đường dẫn / hoặc /home
                .anyRequest().authenticated()
                .and()
                .formLogin()
                //j_spring_security_check xử lý user và password từ form login của mình chuyển sang
                .loginPage("/login")
                //dùng login là nếu mình ko có request đến /login thì cái /login này sẽ là của spring security có sẵn
                .permitAll().defaultSuccessUrl("/login?success=true")
                .failureUrl("/login?success=false")
                .loginProcessingUrl("/j_spring_security_check")
                .successHandler(customSuccessHandler)
                //nhận username và password theo đúng name giống bên login truyền sang
                .usernameParameter("username")
                .passwordParameter("password")
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .permitAll();
    }



        //tự custome user thì ko cần cái này đâu hihi
//    @Bean
//    @Override
//    public UserDetailsService userDetailsService() {
//        UserDetails user =
//                User.withDefaultPasswordEncoder()
//                        .username("user")
//                        .password("password")
//                        .roles("USER")
//                        .build();
//
//        return new InMemoryUserDetailsManager(user);
//    }

}
