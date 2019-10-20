package org.dbl.springsecurity.Controller;

import org.dbl.springsecurity.entity.ImageCode;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**
 * ClassName: ValidateCodeController <br>
 * Description: <br>
 * date: 2019/10/20 22:08<br>
 *
 * @author Double <br>
 * @since JDK 1.8
 */
@RestController
public class ValidateCodeController {
  public static final String SESSION_KEY = "SESSION_KEY_IMAGE_CODE";
  private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

  @GetMapping("/code/image")
  public void createCode(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    // 1、根据随机数生成图片
    ImageCode ic = createImageCode(req);
    // 2、将图片存入session中
    sessionStrategy.setAttribute(new ServletWebRequest(req), SESSION_KEY, ic);
    // 3、将生成的图片写入到接口响应中
    ImageIO.write(ic.getImage(), "JPEG", resp.getOutputStream());
  }

  /**
   * 生成图片校验码
   *
   * @param req http请求
   * @return 图片校验码
   */
  private ImageCode createImageCode(HttpServletRequest req) {
    int width = 67;
    int height = 23;
    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    Graphics g = image.getGraphics();
    Random random = new Random();
    // 设置图片背景色
    g.setColor(getRandColor(200, 500));
    g.setColor(getRandColor(200, 250));
    g.fillRect(0, 0, width, height);
    g.setFont(new Font("Times New Roman", Font.ITALIC, 20));
    g.setColor(getRandColor(160, 200));
    for (int i = 0; i < 155; i++) {
      int x = random.nextInt(width);
      int y = random.nextInt(height);
      int xl = random.nextInt(12);
      int yl = random.nextInt(12);
      g.drawLine(x, y, x + xl, y + yl);
    }
    String sRand = "";
    for (int i = 0; i < 4; i++) {
      String rand = String.valueOf(random.nextInt(10));
      sRand += rand;
      g.setColor(
          new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
      g.drawString(rand, 13 * i + 6, 16);
    }
    g.dispose();
    return new ImageCode(image, sRand, 60);
  }
  /**
   * 生成随机背景条纹
   *
   * @param fc
   * @param bc
   * @return
   */
  private Color getRandColor(int fc, int bc) {
    Random random = new Random();
    if (fc > 255) {
      fc = 255;
    }
    if (bc > 255) {
      bc = 255;
    }
    int r = fc + random.nextInt(bc - fc);
    int g = fc + random.nextInt(bc - fc);
    int b = fc + random.nextInt(bc - fc);
    return new Color(r, g, b);
  }
}
