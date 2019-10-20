package org.dbl.springsecurity.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * ClassName: MyUserDetailService <br>
 * Description: 登录校验 <br>
 * date: 2019/10/20 12:31<br>
 *
 * @author Double <br>
 * @since JDK 1.8
 */
@Slf4j
@Configuration
public class MyUserDetailService implements UserDetailsService {

  @Autowired private PasswordEncoder passwordEncoder;

  /**
   * 对登录的用户名密码进行校验
   *
   * @param username 用户名
   * @return 用户信息
   * @throws UsernameNotFoundException 用户不存在
   */
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    String pwd = passwordEncoder.encode("1234");
    log.info("username:{}\npassword:{}", username, pwd);
    // 根据用户名查找用户信息
    return new User(username, pwd, AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
  }
}
