/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.deskserver;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Westy
 */
public class Main {
    
    public static void main(String[] args){
        System.out.println("Hello world.. it's westy");
        ClientFrm ClientUI = new ClientFrm();
        ClientUI.show();
        ClientUI.startClient();
    }
}
