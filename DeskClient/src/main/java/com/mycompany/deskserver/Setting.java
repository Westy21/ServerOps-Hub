/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.deskserver;

import java.io.Serializable;

/**
 *
 * @author Westy
 */
public class Setting implements Serializable{
    
    private String UserName;
    private String ID;
    
    //default
    private String Address = "127.0.0.1";
    private int Port = 2021;
    
    
    public Setting(){
        
    }
    
    public void setUserName(String UserName){
        this.UserName = UserName;
    }
    
    public String getUserName(){
        if(UserName == null){
            return "null";
        }else{
            return this.UserName;
        }
    }
    
    public void setID(String ID){
        this.ID = ID;
    }
    
    public String getID(){
        if(ID == null){
            return "null";
        }else{
            return this.ID;
        }
    }    
    
    public void setAddress(String Address){
        this.Address=Address;
    }
    
    public String getAddress(){
        return this.Address;
    }
    
    public void setPort(int Port){
        this.Port=Port;
    }
    
    public int getPort(){
        return this.Port;
    }
    
}
