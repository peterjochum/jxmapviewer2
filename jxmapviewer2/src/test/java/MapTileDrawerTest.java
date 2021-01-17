import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.MapTileDrawer;
import org.jxmapviewer.viewer.TileFactoryInfo;
import org.mockito.Mockito;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

import static org.mockito.Mockito.*;

public class MapTileDrawerTest {

    MapTileDrawer mtd;

    @BeforeEach
    public void setupBeforeEach() {
        TileFactoryInfo info = new TileFactoryInfo("EmptyTileFactory 256x256", 1, 15, 17, 256, true, true, "", "x", "y", "z");
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
        URL url = JXMapViewer.class.getResource("/images/loading.png");
        try {
            Image image = ImageIO.read(url);
            mtd = new MapTileDrawer(tileFactory, image, 3 , true, false, false, Color.BLACK);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testMapTiles() {
        Graphics graphics = Mockito.mock(Graphics.class, RETURNS_DEEP_STUBS);
        Rectangle r = new Rectangle(1,2);
        mtd.drawMapTiles(graphics, 2, r);
        // TODO: no idea how to assert correct drawing
    }

    @Test
    public void testDrawTileBorders() {
        Graphics graphics = Mockito.mock(Graphics.class, RETURNS_DEEP_STUBS);
        Rectangle r = new Rectangle(1,2);
        mtd.drawTileBorders(graphics, 12, 1, 3, 1, 1);
        // TODO: no idea how to assert correct drawing
    }


}
