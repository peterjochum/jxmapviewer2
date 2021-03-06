package org.jxmapviewer.viewer;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.jxmapviewer.util.GraphicsUtilities.createCompatibleImage;

public abstract class AbstractThumbnailCreator {
    private BufferedImage image;

    protected Graphics2D getG2() {
        return g2;
    }

    protected void setG2(Graphics2D g2) {
        this.g2 = g2;
    }

    private Graphics2D g2;;


    protected void setThumb(BufferedImage thumb) {
        this.thumb = thumb;
    }

    private BufferedImage thumb=null;


    protected BufferedImage getImage() {
        return image;
    }

    protected int getWidth() {
        return width;
    }

    protected int getHeight() {
        return height;
    }

    protected void setWidth(int width) {
        this.width = width;
    }

    protected void setHeight(int height) {
        this.height = height;
    }

    private int width;
    private int height;

    public AbstractThumbnailCreator(BufferedImage image){
        this.image=image;
        this.width = image.getWidth();
        this.height = image.getHeight();
        this.g2 = null;
    }

    public abstract void checkDimensionValidity()  throws IllegalArgumentException;
    public abstract void makeFastCalculation();

    public void makeGeneralCalculations(){
        int width=getWidth();
        int height=getHeight();
        BufferedImage thumb=getThumb();
        BufferedImage image=getImage();
        if (width == thumb.getWidth() && height == thumb.getHeight()) {
            return;
        }

        BufferedImage temp = createCompatibleImage(image, width, height);
        Graphics2D g2 = temp.createGraphics();

        try {
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2.drawImage(thumb, 0, 0, width, height, 0, 0, width, height, null);
        } finally {
            g2.dispose();
        }
    };

    public BufferedImage getThumb(){
        return this.thumb;
    }




}
