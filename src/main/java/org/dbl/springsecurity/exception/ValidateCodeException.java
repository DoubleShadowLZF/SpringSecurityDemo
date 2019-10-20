package org.dbl.springsecurity.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * ClassName: ValidateCodeException <br>
 * Description: 验证码异常<br>
 * date: 2019/10/20 22:21<br>
 *
 * @author Double <br>
 * @since JDK 1.8
 */
public class ValidateCodeException extends AuthenticationException {
  public ValidateCodeException(String msg) {
    super(msg);
  }
}
