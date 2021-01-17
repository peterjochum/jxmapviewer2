package org.jxmapviewer;

import org.jxmapviewer.viewer.TileFactoryInfo;
import org.jxmapviewer.viewer.util.MercatorUtils;

/**
 * @author Georgios Migdos
 */

public class WMSTileFactoryInfo extends TileFactoryInfo
{
    private String layers;
    private String styles;
    private String tileBgColor;
    private String tileFormat;
    private String srs;
    

    private WMSTileFactoryInfo(int minZoom, int maxZoom, int totalMapZoom, String baseURL, String layers, String styles, String defaultBgColor, String tileFormat, String srs, int tileSize)
    {
        super(minZoom, maxZoom, totalMapZoom, tileSize, true, true, baseURL, "x", "y", "zoom");
        this.layers = layers;
        this.styles = styles;
        this.tileBgColor = defaultBgColor;
        this.tileFormat = tileFormat;
        this.srs = srs;
    }


    public static class WMSTileFactoryInfoBuilder
    {
        private String layers;
        private String styles="";
        private String tileBgColor;
        private String tileFormat;
        private int tileSize;
        private String srs;

        private int minZoom;
        private int maxZoom;
        private int totalMapZoom;
        private String baseURL;



        public WMSTileFactoryInfoBuilder WMSTileFactoryBuilder(int minZoom, int maxZoom, int totalMapZoom, String baseURL, String layers){
            this.tileFormat="image/jpeg";
            this.tileSize=255;
            this.srs="EPSG:4326";
            this.minZoom=minZoom;
            this.maxZoom=maxZoom;
            this.totalMapZoom=totalMapZoom;
            this.baseURL=baseURL;
            this.layers=layers;
            return this;
        }

        public WMSTileFactoryInfoBuilder withStyles(String styles){

            this.styles=styles;
            return this;
        }

        public WMSTileFactoryInfoBuilder withDefaultBgColor(String defaultBgColor){

            this.tileBgColor=defaultBgColor;
            return this;
        }

        public WMSTileFactoryInfoBuilder withTileInfo( String tileFormat, String srs, int tileSize){

            this.tileFormat=tileFormat;
            this.srs=srs;
            this.tileSize=tileSize;
            return this;
        }

        public WMSTileFactoryInfo build(){
            return new WMSTileFactoryInfo(this.minZoom,this.maxZoom,this.totalMapZoom,this.baseURL,this.layers,this.styles,this.tileBgColor,this.tileFormat,this.srs,this.tileSize);
        }



    }

    @Override
    public String getTileUrl(int x, int y, int zoom)
    {
            int tileSize = getTileSize(zoom);
            zoom = getTotalMapZoom() - zoom;
            int z = (int) Math.pow(2, (double) zoom - 1);

            int m = x - z;
            int n = z - 1 - y;

            int tilesPerDimension = (int) Math.pow(2, zoom);

            double radius = (tileSize * tilesPerDimension) / (2 * Math.PI);
            double ulx = MercatorUtils.xToLong(m * tileSize, radius);
            double uly = MercatorUtils.yToLat(n * tileSize, radius);
            double lrx = MercatorUtils.xToLong((m + 1) * tileSize, radius);
            double lry = MercatorUtils.yToLat((n + 1) * tileSize, radius);

            if(lrx<ulx){lrx = -lrx;}
            String bbox = ulx + "," + uly + "," + lrx + "," + lry;
            String url = getBaseURL() + "?version=1.1.1&request=GetMap&layers=" + this.getLayers() + "&format=" + this.getTileFormat()
                            + "&bbox=" + bbox + "&width=" + tileSize + "&height=" + tileSize + "&srs=" + this.getSrs() + "&styles=" + this.getStyles() +    (this.getTileBgColor()==null?"":"&bgcolor="+this.getTileBgColor());
            return url;
    }

    /**
     * @return the layers
     */
    public String getLayers() {
        return layers;
    }

    /**
     * @param layers the layers to set
     */
    public void setLayers(String layers) {
        this.layers = layers;
    }

    /**
     * @return the styles
     */
    public String getStyles() {
        return styles;
    }

    /**
     * @param styles the styles to set
     */
    public void setStyles(String styles) {
        this.styles = styles;
    }

    /**
     * @return the tileBgColor
     */
    public String getTileBgColor() {
        return tileBgColor;
    }

    /**
     * @param tileBgColor the tileBgColor to set
     */
    public void setTileBgColor(String tileBgColor) {
        this.tileBgColor = tileBgColor;
    }

    /**
     * @return the tileFormat
     */
    public String getTileFormat() {
        return tileFormat;
    }

    /**
     * @param tileFormat the tileFormat to set
     */
    public void setTileFormat(String tileFormat) {
        this.tileFormat = tileFormat;
    }

    /**
     * @return the srs
     */
    public String getSrs() {
        return srs;
    }

    /**
     * @param srs the srs to set
     */
    public void setSrs(String srs) {
        this.srs = srs;
    }
        
        

}
