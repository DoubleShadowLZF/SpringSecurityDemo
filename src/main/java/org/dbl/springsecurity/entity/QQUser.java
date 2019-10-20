package org.dbl.springsecurity.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * ClassName: QQUser <br>
 * Description: <br>
 * date: 2019/10/20 16:23<br>
 *
 * @author Double <br>
 * @since JDK 1.8
 */
@Data
public class QQUser extends User {
  private Long id;
  private String nickname;

  public QQUser(
      String username, String password, Collection<? extends GrantedAuthority> authorities) {
    super(username, password, authorities);
  }
}
