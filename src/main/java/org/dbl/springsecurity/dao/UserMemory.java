package org.dbl.springsecurity.dao;

import com.sun.deploy.security.UserDeclinedException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * ClassName: UserMemory <br>
 * Description: <br>
 * date: 2019/10/20 12:37<br>
 *
 * @author Double <br>
 * @since JDK 1.8
 */
@Repository
public class UserMemory {
  private static Map<Long, User> users = new ConcurrentHashMap<>();

  static {
    users.put(
        0L,
        new User("Double", "12341", AuthorityUtils.commaSeparatedStringToAuthorityList("admin")));

    users.put(
        1L, new User("Jack", "12345", AuthorityUtils.commaSeparatedStringToAuthorityList("admin")));

    users.put(
        2L, new User("Merry", "5432", AuthorityUtils.commaSeparatedStringToAuthorityList("admin")));

    users.put(
        3L,
        new User("Kitty", "23421", AuthorityUtils.commaSeparatedStringToAuthorityList("admin")));
  }

  /**
   * 根据 id 查找用户信息
   *
   * @param info 用户名/（用户名，密码）
   * @return 用户信息
   */
  public static Collection<User> selectAll(String... info) {

    if (info.length == 0) {
      return users.values();
    }

    String username = info.length > 0 ? info[0] : null;
    String password = info.length > 1 ? info[1] : null;

    return users.keySet().stream()
        .map((qid) -> users.get(qid))
        .filter(
            u -> {
              if (username != null && password != null) {
                return username.equals(u.getUsername()) && password.equals(u.getPassword());
              }
              if (username != null) {
                return username.equals(u.getUsername());
              }
              if (password != null) {
                return password.equals(u.getPassword());
              }
              return false;
            })
        .collect(Collectors.toList());
  }

  public static User selectOne(Long id) {
    if (id == null) {
      throw new UserDeclinedException("id is null");
    }
    return users.keySet().stream()
        .filter(mid -> mid.equals(id))
        .map(qid -> users.get(qid))
        .collect(Collectors.toList())
        .get(0);
  }

  public static void main(String[] args) {
    long id = 0L;
    Collection ret = UserMemory.selectAll("Double");
    System.out.println(ret.size());
  }
}
