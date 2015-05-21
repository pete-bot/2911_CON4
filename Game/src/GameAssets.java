import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

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
    String runningDir;
    private Path assetsLocation;
    private HashMap<String, ImageIcon> assets = new HashMap<String, ImageIcon>();
    private ImageIcon blankTokenIcon;
    private ImageIcon glowingTokenIcon;
    private ImageIcon redTokenIcon;
    private ImageIcon yellowTokenIcon;
    private ImageIcon winTokenIcon;
    private ImageIcon spaceIcon;
    private ImageIcon pvCPUIcon;
    private ImageIcon pvpIcon;
    private ImageIcon optionsIcon;
    private ImageIcon spacerIcon;
    private ImageIcon quitIcon;
    private ImageIcon resumeIcon;
    private ImageIcon restartIcon;
    private ImageIcon exitIcon;
    private ImageIcon menuIcon;

    public GameAssets() {
        runningDir = System.getProperty("user.dir");
        // One of two places we could be running the code, under src or from
        // eclipse (in the main project tree)
        assetsLocation = Paths.get(runningDir.matches(".*src") ? runningDir
                .replaceFirst("src", "") + "assets/" : runningDir + "/assets");
        addAssets();
    }

    private void addAssets() {
        File dir = new File(assetsLocation.toString());
        File[] listOfAssets = dir.listFiles();
        for (int i = 0; i < listOfAssets.length; i++) {
            if (listOfAssets[i].isFile()) {
                String filename = listOfAssets[i].getName();
                System.out.println(filename);
                ImageIcon newAsset = new ImageIcon(assetsLocation + "/"
                        + filename);
                assets.put(filename, newAsset);
            } else { // All assets must be at the top level
                continue;
            }
        }
    }

    public ImageIcon getAsset(String filename) {
        return assets.get(filename);
    }
}
