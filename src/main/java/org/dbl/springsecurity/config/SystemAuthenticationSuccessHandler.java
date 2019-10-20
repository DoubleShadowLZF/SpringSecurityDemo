package org.dbl.springsecurity.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: SystemAuthenticationSuccessHandler <br>
 * Description: <br>
 * date: 2019/10/20 22:54<br>
 *
 * @author Double <br>
 * @since JDK 1.8
 */
@Component
@Slf4j
public class SystemAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
  @Autowired private ObjectMapper objectMapper;
  @Autowired private SecurityProperties securityProperties;

  @Override
  public void onAuthenticationSuccess(
      HttpServletRequest request, HttpServletResponse response, Authentication authentication)
      throws IOException, ServletException {
    log.info("登录成功了");
    Map<String, Object> successJson = new HashMap<>();
    successJson.put("code", "200");
    successJson.put("msg", "success");
    response.getWriter().write(objectMapper.writeValueAsString(successJson));
    //        if(LoginType)
  }
}
