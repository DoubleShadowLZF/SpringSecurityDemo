package org.dbl.springsecurity.Controller;

import org.dbl.springsecurity.dao.UserMemory;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * ClassName: UserController <br>
 * Description: <br>
 * date: 2019/10/20 12:35<br>
 *
 * @author Double <br>
 * @since JDK 1.8
 */
@RequestMapping("/user")
@RestController
public class UserController {
  @GetMapping
  public Collection<User> findUsers() {
    return UserMemory.selectAll();
  }

  @GetMapping("/{id}")
  public User findUser(@PathVariable("id") Long id) {
    return UserMemory.selectOne(id);
  }

  @PostMapping
  public Collection<User> forwardTemp() {
    return UserMemory.selectAll();
  }
}
