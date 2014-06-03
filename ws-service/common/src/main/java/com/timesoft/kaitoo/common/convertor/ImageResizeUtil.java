/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timesoft.kaitoo.common.convertor;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.imageio.ImageIO;
import org.apache.log4j.Logger;

/**
 *
 * @author sorasaks
 */
public class ImageResizeUtil {

    private static final Logger LOG = Logger.getLogger(ImageResizeUtil.class);

    public static final String EXTENSION_PNG = "png";
    public static final String EXTENSION_JPG = "jpg";

    public static void resizeImage(BufferedImage originalImage,
            Integer width,
            Integer height,
            String extension,
            String savePath) throws IOException {

        int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();

        BufferedImage resizedImage = new BufferedImage(width, height, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, width, height, null);
        g.dispose();
        
        LOG.debug(":::::::::::::::::: resizedImage = " + resizedImage);
        
        ImageIO.write(resizedImage, extension, new File(savePath + "\\" + extension));
    }

    public static void resizeImageWithHint(BufferedImage originalImage,
            Integer width,
            Integer height,
            String extension,
            String savePath) throws IOException {

        int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();

        BufferedImage resizedImage = new BufferedImage(width, height, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, width, height, null);
        g.dispose();
        g.setComposite(AlphaComposite.Src);

        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        String filename = ConvertFormatUtil.convertFormat(new Date(), "yyyy-MM-dd-hhmmss", Locale.US);

        LOG.debug(":::::::::::::::::: resizedImage = " + resizedImage);

        ImageIO.write(resizedImage, extension, new File("c:\\image\\mkyong_jpg." + extension));
    }
}
