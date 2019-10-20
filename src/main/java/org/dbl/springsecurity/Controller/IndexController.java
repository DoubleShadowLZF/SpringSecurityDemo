package org.dbl.springsecurity.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * ClassName: IndexController <br>
 * Description: 认证后的首页 <br>
 * date: 2019/10/20 23:37<br>
 *
 * @author Double <br>
 * @since JDK 1.8
 */
@Controller
@RequestMapping("/authentication/index")
public class IndexController {
  @PostMapping
  public Object forward2UserPage1() {
    return "forward:/user";
  }
}
