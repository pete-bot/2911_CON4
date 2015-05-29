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
    /**String representing current running directory. ex: ~/home/<user>/workspace/2911_Project/src */
	private String runningDir;
	/**Relative path to assets */
    private Path assetsLocation;

    /**Array list of assets for use as image icons */
    private HashMap<String, ImageIcon> assets = new HashMap<String, ImageIcon>();

    /**
     * Construcor to initialise assets class. 
     * Sets the running directory and asset path for each asset in the ../assets folder.
     */
    public GameAssets() {
        initAssets();
    }

    /**
     * Method to add each imageIcon for each asset.
     */
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
    
    /**
     * Method to return the appropriate imageIcon for each asset. 
     * @param filename
     * 		The asset filename. ex: "red_token_win.png"
     * @return
     * 		Return the appropriate image icon for use.
     */
    public ImageIcon getAsset(String filename) {
        return assets.get(filename);
    }

    /**
     * Method to return the asset path.
     * @return
     * 		Return a string of the asset path.
     */
    public Path getAssetsDir() {
        // System.out.println(assetsLocation.toString());
        return assetsLocation;
    }

    /*
     * Method that resizes token image icons
     */
    
    /**
     * Method to resize token image.
     * @param filename
     * 		File to be resized
     * @param width
     * 		New width 
     * @param height
     * 		New height
     * @return
     * 		Return the newly resized imageIcon
     */
    public ImageIcon getResizedAsset(String filename, int width, int height) {
        BufferedImage originalImage;
        BufferedImage resizedImage = new BufferedImage( (width - 190) / 7, (height - 170) / 6,
                BufferedImage.TYPE_4BYTE_ABGR);

        try {
            originalImage = ImageIO.read(new File(assetsLocation + "/"
                    + filename));
            // Create graph
            Graphics2D g = resizedImage.createGraphics();
            g.addRenderingHints(new RenderingHints(
                    RenderingHints.KEY_RENDERING,
                    RenderingHints.VALUE_RENDER_QUALITY));
            g.drawImage(originalImage, 0, 0, (width - 190) / 7, (height - 170) / 6 , null);
            g.dispose();

        } catch (IOException E) {
            E.printStackTrace();
        }
        return new ImageIcon(resizedImage);
    }

    /**
     * Method to initialise the imageIcon assets.
     */
    private void initAssets() {
        runningDir = System.getProperty("user.dir");
        // One of two places we could be running the code, under src or from
        // eclipse (in the main project tree)
        assetsLocation = Paths.get(runningDir.matches(".*src") ? runningDir
                .replaceFirst("src", "") + "assets/" : runningDir + "/assets");
        addAssets();
    }
}
