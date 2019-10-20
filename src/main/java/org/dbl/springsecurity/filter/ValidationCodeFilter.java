package org.dbl.springsecurity.filter;

import org.dbl.springsecurity.Controller.ValidateCodeController;
import org.dbl.springsecurity.entity.ImageCode;
import org.dbl.springsecurity.exception.ValidateCodeException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * ClassName: ValidationCodeFilter <br>
 * Description: <br>
 * date: 2019/10/20 22:23<br>
 *
 * @author Double <br>
 * @since JDK 1.8
 */
public class ValidationCodeFilter extends OncePerRequestFilter {

  public ValidationCodeFilter(AuthenticationFailureHandler authenticationFailureHandler) {
    this.authenticationFailureHandler = authenticationFailureHandler;
  }

  private AuthenticationFailureHandler authenticationFailureHandler;
  private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

  @Override
  protected void doFilterInternal(
      HttpServletRequest req, HttpServletResponse resp, FilterChain filterChain)
      throws ServletException, IOException {
    if (StringUtils.pathEquals("/authentication/index", req.getRequestURI())
        && "post".equalsIgnoreCase(req.getMethod())) {
      try {
        validate(new ServletWebRequest(req));
      } catch (ValidateCodeException e) {
        authenticationFailureHandler.onAuthenticationFailure(req, resp, e);
        return;
      }
    }
    filterChain.doFilter(req, resp);
  }

  private void validate(ServletWebRequest req)
      throws ServletRequestBindingException, ValidateCodeException {
    // 从 session中获取imageCode对象
    ImageCode imageCode =
        (ImageCode) sessionStrategy.getAttribute(req, ValidateCodeController.SESSION_KEY);
    String codeInRequest = ServletRequestUtils.getStringParameter(req.getRequest(), "imageCode");
    if (StringUtils.isEmpty(codeInRequest) || imageCode == null) {
      throw new ValidateCodeException("验证码为空");
    }
    if (imageCode.isExpire()) {
      throw new ValidateCodeException("验证码过期");
    }
    if (!codeInRequest.equals(imageCode.getCode())) {
      throw new ValidateCodeException("验证码不匹配");
    }
    // 从session中移除key
    sessionStrategy.removeAttribute(req, ValidateCodeController.SESSION_KEY);
  }
}
