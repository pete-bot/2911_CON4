import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * This was created to factor out a lot of unecessary paths to asset codes Items
 * are indexed by hash, so calls should be relatively quick. Keys set by
 * filename This is a repository of all items under the assets directory.
 *
 * @author rjs
 *
 */
public class GameAssets {
    private String runningDir;
    private Path assetsLocation;

    private HashMap<String, ImageIcon> assets = new HashMap<String, ImageIcon>();

    public GameAssets() {
        initAssets();
    }

    private void addAssets() {
        File dir = new File(assetsLocation.toString());
        File[] listOfAssets = dir.listFiles();
        for (int i = 0; i < listOfAssets.length; i++) {
            if (listOfAssets[i].isFile()) {
                String filename = listOfAssets[i].getName();
                ImageIcon newAsset = new ImageIcon(assetsLocation + "/"
                        + filename);
                assets.put(filename, newAsset);
                // System.out.println("Adding asset " + filename);
            } else { // All assets must be at the top level
                continue;
            }
        }
    }

    public ImageIcon getAsset(String filename) {
        return assets.get(filename);
    }

    public Path getAssetsDir() {
        // System.out.println(assetsLocation.toString());
        return assetsLocation;
    }

    /*
     * Method that resizes token image icons
     */
    public ImageIcon getResizedAsset(String filename, int width, int height) {
        BufferedImage originalImage;
        BufferedImage resizedImage = new BufferedImage(width / 7, height / 7,
                BufferedImage.TYPE_4BYTE_ABGR);

        try {
            originalImage = ImageIO.read(new File(assetsLocation + "/"
                    + filename));
            // Create graph
            Graphics2D g = resizedImage.createGraphics();
            g.addRenderingHints(new RenderingHints(
                    RenderingHints.KEY_RENDERING,
                    RenderingHints.VALUE_RENDER_QUALITY));
            g.drawImage(originalImage, 0, 0, width / 7, height / 7, null);
            g.dispose();

        } catch (IOException E) {
            E.printStackTrace();
        }
        return new ImageIcon(resizedImage);
    }

    private void initAssets() {
        runningDir = System.getProperty("user.dir");
        // One of two places we could be running the code, under src or from
        // eclipse (in the main project tree)
        assetsLocation = Paths.get(runningDir.matches(".*src") ? runningDir
                .replaceFirst("src", "") + "assets/" : runningDir + "/assets");
        addAssets();
    }
}
