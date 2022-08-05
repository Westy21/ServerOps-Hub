/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.deskserver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;

/**
 *
 * @author Westy
 */
public class FileManager {
    
    private final String DefaultFolder = "Downloads";
    
    public File getSelectedFile(){
        JFileChooser JFC = new JFileChooser();
        if(JFC.showOpenDialog(null) == 0){
            return JFC.getSelectedFile();
        }else{
            return null;
        }
    }
    
    public File getSelectedFile(File FileDirectory){
        JFileChooser JFC = new JFileChooser(FileDirectory);
        if(JFC.showOpenDialog(null) == 0){
            return JFC.getSelectedFile();
        }else{
            return null;
        }
       
    }
    
    public FileStream deconstructFile(File File){
        
        //Huge files cannot be deconstructed as they take up large amounts of memory
        // a way of solving this is to sent it as small packs of its data
            FileStream FileStream = null;
            try {
                var FIS = new FileInputStream(File);
                FileStream = new FileStream();
                FileStream.setFileName(File.getName());
                FileStream.setFileAttr(File.getParentFile().getName(),FIS.readAllBytes());
                FileStream.setFileSize();
                FIS.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
            }catch (NullPointerException ex){
                Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
            }

            return FileStream;
    }
    
    public boolean constructFile(FileStream FileStream) throws IOException{
        var Dir = new File(DefaultFolder);
        Dir.mkdir();
        var File = new File(DefaultFolder+"\\"+FileStream.getFileName());
        var FOS = new FileOutputStream(File);
        FOS.write(FileStream.getFileData());
        FOS.flush();
        FOS.close();
        return File.createNewFile();
    }
}
