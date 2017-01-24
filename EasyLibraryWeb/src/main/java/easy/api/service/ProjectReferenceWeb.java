/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package easy.api.service;

import java.util.HashMap;

/**
 *
 * @author development
 */
public class ProjectReferenceWeb {
    public static void loadScript(HashMap<String, String[]> folderGuide, String folderDest) {
        ProjectReference.loadScript(folderGuide, folderDest);
    }
}
