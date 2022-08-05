/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.deskserver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Westy
 */
public class Client implements Runnable{
    
    private String Address;
    private int Port;
    
    public  Socket Socket;
    private ObjectInputStream OIS;
    private ObjectOutputStream OOS;
    
    public FileManager FM = new FileManager();
    private ClientFrm CF;
    private boolean Testing = false;
    
    //Service
    public Client(ClientFrm CF, String Address, int Port){
        this.Address = Address;
        this.Port = Port;
        this.CF = CF;
    }  
    
    
    //Testing Connection
    public Client(String Address, int Port){
        Testing = true;
        this.Address = Address;
        this.Port = Port;
    }
    
    public boolean connect(){
        try {
            Socket = new Socket(Address, Port);
            OIS = new ObjectInputStream(Socket.getInputStream());
            OOS = new ObjectOutputStream(Socket.getOutputStream());
        } catch (IOException ex) {
            System.out.println("Connection Failed. ");
        }
        
        if(Socket!=null){
            if(Socket.isConnected()){
                return true;
            }
        }
        return false;
    }
    
    public void disConnect(){
        try {
            Socket.close();
            OIS.close();
            OOS.close();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void sentToServer(FileStream FileStream){
        try {
            if(FileStream.getFileData()!=null){
                CF.updateUpload(FileStream.getFileData().length);
            }
            
            OOS.writeObject(FileStream);
            OOS.flush();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }catch(NullPointerException ex){
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Failed to sent due tu a null attribute: " + ex.getLocalizedMessage());
        }
    }
    
    private void listenToServer(){
        Object Object;
        FileStream FileStream;
        ServerData ServerData;
        try{
            while(true){
                if((Object = OIS.readObject())!=null){
                    try{
                        FileStream = (FileStream)Object;
                        CF.updateDownload(FileStream.getFileData().length);
                        if(FM.constructFile(FileStream)){
                            CF.showMessage("Download of file " + FileStream.getFileName() + "completed!");
                        }else{
                            CF.showMessage("you already have the file");
                        }
                    }catch(ClassCastException Ex){}

                    try{
                        ServerData = (ServerData)Object;
                        CF.updateFileTree(ServerData.getFileTree());
                    }catch(ClassCastException Ex){}

                }
            }
        } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                //Server disconnected/is offline
                CF.updateStatus("Server - Offline");
        } catch (ClassNotFoundException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            disConnect();
            System.out.println("Client disconnected");
        }
    }
    
    @Override
    public void run() {
        while(true){
            if(Socket == null){
                //offline mode
            }else{
                if(Testing){
                    break;
                }else{
                    listenToServer();
                    System.out.println("Listining to server");
                }
            }
        }
    }
}
