package org.jxmapviewer.viewer;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.jxmapviewer.util.GraphicsUtilities.createCompatibleImage;

public class WidthHeightThumbnailCreator extends AbstractThumbnailCreator {
    public int getNewHeight() {
        return newHeight;
    }

    public int getNewWidth() {
        return newWidth;
    }

    private int newHeight;
    private int newWidth;

   public WidthHeightThumbnailCreator(BufferedImage image, int newWidth,int newHeight){
       super(image);
       this.newHeight=newWidth;
       this.newHeight=newHeight;

   }




    public void checkDimensionValidity() throws IllegalArgumentException{
       int width=getWidth();
       int height=getHeight();

        if (newWidth >= width || newHeight >= height) {
            throw new IllegalArgumentException("newWidth and newHeight cannot" +
                    " be greater than the image" +
                    " dimensions");
        } else if (newWidth <= 0 || newHeight <= 0) {
            throw new IllegalArgumentException("newWidth and newHeight must" +
                    " be greater than 0");
        }
    }

    @Override
    public void makeFastCalculation() {
       BufferedImage image=getImage();
       Graphics2D g2=getG2();
       BufferedImage thumb=getThumb();

       int width=getWidth();
       int height=getHeight();

       boolean isWidthGreater = width > height;

       BufferedImage temp = null;

       int newHeight=getNewHeight();
       int newWidth=getNewWidth();

        boolean isTranslucent = image.getTransparency() != Transparency.OPAQUE;

        try {
            int previousWidth = width;
            int previousHeight = height;

            do {
                if (width > newWidth) {
                    width /= 2;
                    if (width < newWidth) {
                        width = newWidth;
                    }
                }

                if (height > newHeight) {
                    height /= 2;
                    if (height < newHeight) {
                        height = newHeight;
                    }
                }

                if (temp == null || isTranslucent) {
                    if (g2 != null) {
                        //do not need to wrap with finally
                        //outer finally block will ensure
                        //that resources are properly reclaimed
                        g2.dispose();
                    }
                    temp = createCompatibleImage(image, width, height);
                    g2 = temp.createGraphics();
                    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                            RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                }
                if (g2 != null)    // always the case
                    g2.drawImage(thumb, 0, 0, width, height,
                            0, 0, previousWidth, previousHeight, null);

                previousWidth = width;
                previousHeight = height;

                thumb = temp;
            } while (width != newWidth || height != newHeight);
        } finally {
            if (g2 != null) {
                g2.dispose();
            }
        }
    }



}
