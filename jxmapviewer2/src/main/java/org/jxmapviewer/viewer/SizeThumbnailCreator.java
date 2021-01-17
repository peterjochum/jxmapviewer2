package org.jxmapviewer.viewer;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.jxmapviewer.util.GraphicsUtilities.createCompatibleImage;

public class SizeThumbnailCreator extends AbstractThumbnailCreator {
    int newSize;

   public SizeThumbnailCreator(BufferedImage image,int newSize){
       super(image);
       this.newSize=newSize;
   }



    public void checkDimensionValidity() throws IllegalArgumentException{
       int width=getWidth();
       int height=getHeight();

        boolean isWidthGreater = width > height;

        if (isWidthGreater) {
            if (newSize >= width) {
                throw new IllegalArgumentException("newSize must be lower than" +
                        " the image width");
            }
        } else if (newSize >= height) {
            throw new IllegalArgumentException("newSize must be lower than" +
                    " the image height");
        }

        if (newSize <= 0) {
            throw new IllegalArgumentException("newSize must" +
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
       boolean isTranslucent = image.getTransparency() != Transparency.OPAQUE;

       BufferedImage temp = null;

       float ratioWH = (float) width / (float) height;
       float ratioHW = (float) height / (float) width;


        try {
            int previousWidth = width;
            int previousHeight = height;

            do {
                if (isWidthGreater) {
                    width /= 2;
                    if (width < newSize) {
                        width = newSize;
                    }
                    height = (int) (width / ratioWH);
                } else {
                    height /= 2;
                    if (height < newSize) {
                        height = newSize;
                    }
                    width = (int) (height / ratioHW);
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

                setThumb(temp);
            } while (newSize != (isWidthGreater ? width : height));
        } finally {
            if (g2 != null) {
                g2.dispose();
            }
        }
    }




}
