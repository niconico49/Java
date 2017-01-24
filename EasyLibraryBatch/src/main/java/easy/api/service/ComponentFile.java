package easy.api.service;

import java.net.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.MessageFormat;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author development
 */
public class ComponentFile {
    public String getTxtFromFile(String path) {
        String result = "";
		try (FileReader fileReader = new FileReader(path)) {
			BufferedReader bufferedReader = new BufferedReader(fileReader);
            for (String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
				result += MessageFormat.format("{0}\n", line);
            }
/*            
			String line = "";
			while((line = bufferedReader.readLine()) != null) {
				result += MessageFormat.format("{0}\n", line);
			}
*/            
		}
		catch (Exception e) {
            System.out.println(e.getMessage());
		}
        return result;
	}

    public byte[] getBufferFromFile(String path) {
        byte[] buffer = new byte[(int)(new File(path)).length()];
        try {
            buffer = Files.readAllBytes(Paths.get(path));
        }
        catch (IOException ex) {
        }
        return buffer;
    }

    public void writeFileFromTxt(String path, String value) {
		try (FileWriter fileWriter = new FileWriter(path); BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            bufferedWriter.write(value);
		}
		catch (Exception e) {
            System.out.println(e.getMessage());
		}
	}

    public String getTxtFromFileByUrl(String urlString) {
        String result = "";
        try {
            URL url = new URL(urlString);
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()))){
                for (String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
                    result += MessageFormat.format("{0}\n", line);
                }
/*
                String line = "";
                while((line = bufferedReader.readLine()) != null) {
                    result += MessageFormat.format("{0}\n", line);
                }
*/
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        catch (MalformedURLException e) {
            System.out.println(e.getMessage());
        }
        return result;
	}

    public void fileRename(String pathSource, String pathDest) {
        File fileSource = new File(pathSource);
        File fileDest = new File(pathDest);
        fileSource.renameTo(fileDest);
        //boolean b = fileSource.renameTo(fileDest);
        // if b is true, then the file has been renamed successfully
    }

    public void fileDelete(String path) {
        File f = new File(path);
        f.delete();
        //boolean b = f.delete();
        // if b is true, then the file has been deleted successfully
    }

    public boolean fileExist(String path) {
        File f = new File(path);
        return (f.exists() && f.isFile());
    }

    public boolean directoryExist(String folder) {
        File f = new File(folder);
        return (f.exists() && f.isDirectory());
    }
    
    public void copyDirectory(String folderSource, String folderDest) {
        try {
            FileUtils.copyDirectory(new File(folderSource), new File(folderDest));
        }
        catch (IOException e) {
            String msg = e.getMessage();
        }
    }
}
