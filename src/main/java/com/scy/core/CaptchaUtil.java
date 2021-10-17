package com.scy.core;

import com.scy.core.format.MessageUtil;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.OutputStream;

/**
 * CaptchaUtil
 *
 * @author shichunyang
 * Created by shichunyang on 2020/9/1.
 */
@Slf4j
public class CaptchaUtil {

    private CaptchaUtil() {
    }

    public static String getCaptcha(OutputStream out) {
        ImageIO.setUseCache(Boolean.FALSE);

        int count = 5;
        String captchaText = RandomUtil.getRandomText(count);

        BufferedImage bufferedImage = createCaptcha(count * 21, 30, captchaText, count);

        try {
            ImageIO.write(bufferedImage, "png", out);
        } catch (Throwable e) {
            log.error(MessageUtil.format("getCaptcha error", e));
            return StringUtil.EMPTY;
        }

        return captchaText;
    }

    private static BufferedImage createCaptcha(int width, int height, String captchaText, int count) {
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = bufferedImage.createGraphics();

        bufferedImage = graphics2D.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
        graphics2D = bufferedImage.createGraphics();

        graphics2D.setColor(Color.BLACK);

        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int fontSize = height - 4;
        Font font = new Font("微软雅黑", Font.PLAIN, fontSize);
        graphics2D.setFont(font);

        char[] captchaTextChars = captchaText.toCharArray();
        for (int i = 0; i < count; i++) {
            graphics2D.drawChars(captchaTextChars, i, 1, (width / count) * i, (height / 2) + (fontSize / 2) - 4);
        }

        graphics2D.dispose();

        return bufferedImage;
    }
}
