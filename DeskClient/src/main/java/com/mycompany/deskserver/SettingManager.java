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
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Westy
 */
public class SettingManager {
    //update and save users settings
    
    private final String SettingFolderName = "Setting";
    private final File SettingFolder = new File(SettingFolderName);
    
    private final String SettingFileName = "Setting.txt";
    private final File SettingFile = new File(SettingFolderName+"\\"+SettingFileName);
        
    
    public SettingManager(){
        //build Setting fileTree_For first time use
        if(SettingFolder.exists()){
            if(SettingFile.exists()){
                getSetting();
            }else{
                try {
                    SettingFile.createNewFile();
                    saveSetting(new Setting());
                } catch (IOException ex) {
                    Logger.getLogger(SettingManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }else{
            SettingFolder.mkdir();
            try {
                SettingFile.createNewFile();
                saveSetting(new Setting());
            } catch (IOException ex) {
                Logger.getLogger(SettingManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public Setting getSetting(){
            try {
                //load settings from Directory
                //for first time users_No settings exist therefore use default values from Settings class
                
                FileInputStream FIS = new FileInputStream(SettingFile);
                ObjectInputStream OIS = new ObjectInputStream(FIS);
                Setting Setting = (Setting)OIS.readObject();
                OIS.close();
                FIS.close();
                return Setting;
            } catch (FileNotFoundException ex) {
                Logger.getLogger(SettingManager.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("File not found");
            } catch (IOException ex) {
                Logger.getLogger(SettingManager.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(SettingManager.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Failed to cast class(Setting)!");
            }
            System.out.println("Failed to getSetting_ from File");
            return null;
    }
    
    public void saveSetting(Setting newSetting){
        try {
            //override existing settings in directory with new settings
            FileOutputStream FOS = new FileOutputStream(SettingFile);
            ObjectOutputStream OOS = new ObjectOutputStream(FOS);
            OOS.writeObject(newSetting);
            OOS.flush();
            OOS.close();
            FOS.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SettingManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SettingManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }  
        
}
