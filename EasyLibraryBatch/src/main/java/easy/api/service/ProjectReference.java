package easy.api.service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import org.apache.commons.io.FileUtils;
/**
 *
 * @author development
 */
public class ProjectReference {
	
    private static volatile String scriptCode = null;

    private static boolean directoryExist(String folder) {
        File f = new File(folder);
        return (f.exists() && f.isDirectory());
    }

    public static void loadScript(HashMap<String, String[]> folderGuide, String folderDest) {
        if (scriptCode == null) {
            String[] kernelFolder = folderGuide.get("LOCAL");
            for (String folderSource : kernelFolder) {
                if (ProjectReference.directoryExist(folderSource)) {
                    
                    try {
                        FileUtils.copyDirectory(new File(folderSource), new File(folderDest));
                    }
                    catch(IOException e) {
                        String msg = e.getMessage();
                    }
                    //folderDest = remoteFolder;
                    break;
                }
                //kernelFolder = folderGuide.get("REMOTE");
                
                scriptCode = "ScriptCode";
            }
        }
    }
}
