package org.jxmapviewer;


import java.awt.Rectangle;
import java.awt.geom.Point2D;

import org.jxmapviewer.viewer.GeoPosition;


public interface JXMapViewerInterface {
    public GeoPosition convertPointToGeoPosition(Point2D pt);
    public void setZoom(int zoom);
    public Rectangle getViewportBounds();
    public void setCenter(Point2D center);
    public int getZoom();
    public void repaint();
}
