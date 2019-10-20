package org.dbl.springsecurity.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * ClassName: SystemAuthenticationFailureHandler <br>
 * Description: 失败处理逻辑 <br>
 * date: 2019/10/20 22:44<br>
 *
 * @author Double <br>
 * @since JDK 1.8
 */
@Component
public class SystemAuthenticationFailureHandler implements AuthenticationFailureHandler {
  @Autowired private ObjectMapper objectMapper;

  /**
   * 处理系统失败逻辑
   *
   * @param req http请求
   * @param resp http响应
   * @param e 异常
   * @throws IOException
   * @throws ServletException
   */
  @Override
  public void onAuthenticationFailure(
      HttpServletRequest req, HttpServletResponse resp, AuthenticationException e)
      throws IOException, ServletException {

    Map<String, Object> errorJson = new LinkedHashMap<>();
    errorJson.put("code", HttpStatus.CONFLICT.value());
    errorJson.put("msg", e.getMessage());

    resp.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
    resp.setContentType("application/json;charset=UTF-8");
    resp.getWriter().write(objectMapper.writeValueAsString(errorJson));
  }
}
