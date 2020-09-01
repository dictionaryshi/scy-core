package com.scy.core;

import com.scy.core.format.MessageUtil;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.Random;

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

        Graphics2D graphics2D = (Graphics2D) bufferedImage.getGraphics();
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        graphics2D.setColor(Color.GRAY);
        graphics2D.fillRect(0, 0, width, height);

        graphics2D.setColor(getRandomColor(200, 250));
        graphics2D.fillRect(0, 0, width, height);

        graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int fontSize = height - 4;
        Font font = new Font("Algerian", Font.ITALIC, fontSize);
        graphics2D.setFont(font);

        graphics2D.setColor(getRandomColor(100, 160));

        char[] captchaTextChars = captchaText.toCharArray();
        for (int i = 0; i < count; i++) {
            graphics2D.drawChars(captchaTextChars, i, 1, (width / count) * i, (height / 2) + (fontSize / 2) - 4);
        }

        graphics2D.dispose();

        return bufferedImage;
    }

    private static Color getRandomColor(int fc, int bc) {
        Random random = RandomUtil.RANDOM;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }
}
