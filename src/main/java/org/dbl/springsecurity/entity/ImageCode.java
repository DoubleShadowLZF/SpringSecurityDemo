package org.dbl.springsecurity.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * ClassName: ImageCode <br>
 * Description: 图片验证码<br>
 * date: 2019/10/20 22:06<br>
 *
 * @author Double <br>
 * @since JDK 1.8
 */
@Data
@AllArgsConstructor
public class ImageCode {
  private BufferedImage image;
  private String code;
  private LocalDateTime expireTime;

  /** @param expireIn 多少秒后过期 */
  public ImageCode(BufferedImage image, String code, int expireIn) {
    this.image = image;
    this.code = code;
    this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
  }

  public boolean isExpire() {
    return expireTime.isBefore(LocalDateTime.now());
  }
}
