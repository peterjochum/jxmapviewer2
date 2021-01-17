import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.jxmapviewer.JXMapKit;

import javax.swing.*;

public class JxMapKitTest {

    JXMapKit jxmk;

    @BeforeEach
    void setupJxMapKit() {
        jxmk = new JXMapKit();
    }

    @Test
    void testIsMinimapVisible() {
        Assertions.assertTrue(jxmk.isMiniMapVisible());
        jxmk.setMiniMapVisible(false);
        Assertions.assertFalse(jxmk.isMiniMapVisible());
    }

    @Test
    void getZoomSlider() {
        JSlider slider =jxmk.getZoomSlider();
        Assertions.assertNotNull(slider);
    }

    @Test
    void getZoomButtons() {
        JButton buttonZoomIn = jxmk.getZoomInButton();
        Assertions.assertNotNull(buttonZoomIn);
        JButton buttonZoomOut = jxmk.getZoomOutButton();
        Assertions.assertNotNull(buttonZoomOut);
    }
}


