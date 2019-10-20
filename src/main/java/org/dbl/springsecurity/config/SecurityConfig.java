package org.dbl.springsecurity.config;

import org.dbl.springsecurity.filter.ValidationCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * ClassName: SecurityConfig <br>
 * Description: SpringSecurity 配置类 <br>
 * date: 2019/10/20 16:07<br>
 *
 * @author Double <br>
 * @since JDK 1.8
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  @Autowired private SecurityProperties securityProperties;
  @Autowired private SystemAuthenticationFailureHandler authenticationFailureHandler;
  @Autowired private SystemAuthenticationSuccessHandler authenticationSuccessHandler;

  /**
   * 密码加密器
   *
   * @return
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  public void configure(HttpSecurity http) throws Exception {
    // 设置错误失败处理器
    ValidationCodeFilter filter = new ValidationCodeFilter(authenticationFailureHandler);
    http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
    http.formLogin()
        .loginPage("/login.html") // 设置登录页面
        .permitAll()
        .loginProcessingUrl("/authentication/index") // 登录成功后跳转链接
        .defaultSuccessUrl("/user", true)
        .successHandler(authenticationSuccessHandler)
        .failureHandler(authenticationFailureHandler)
        //        .failureForwardUrl("/login.html")
        .and()
        .authorizeRequests() // 请求授权;
        .antMatchers("/code/image", "browser_error.html")
        .permitAll() // 匹配“/code/image”、“/browser_error.html”页面
        .anyRequest()
        .authenticated() // 其他请求都需要认证
        .and()
        .csrf()
        .disable(); // 关闭跨站请求攻击
    /*http.authorizeRequests() // authorizeRequests()定义哪些URL需要被保护、哪些不需要被保护
    .antMatchers("/user/**")
    .authenticated()
    .anyRequest()
    .permitAll()
    .and()
    .formLogin() // 表单登录
    //        .loginPage("/login") //指定登录页面
    .defaultSuccessUrl("/user", true)
    .permitAll()
    .and()
    .logout()
    .permitAll()
    .and()
    .csrf()
    .disable();*/
  }
}
