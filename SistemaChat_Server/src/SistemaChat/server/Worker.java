/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SistemaChat.server;

import SistemaChat.logic.User;
import SistemaChat.protocol.Protocol;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author adria
 */
public class Worker {
    Socket socket;
    ObjectInputStream in;
    ObjectOutputStream out;
    User user;

    public Worker(Socket socket, ObjectInputStream in, ObjectOutputStream out, User user) {
        this.socket = socket;
        this.in = in;
        this.out = out;
        this.user = user;
    }
    
    boolean continuar;
    public void start()
    {
        try {
            System.out.println("Worker awaiting commands");
            Thread thread = new Thread(new Runnable(){
                public void run(){
                    listen();
                }
            });
            continuar = true;
            thread.start();
        } catch (Exception ex) {  
        }
    }
    
    public void stop(){
        continuar=false;
    }
    
    public void listen(){
        int method;
        while (continuar) {
            try {
                method = in.readInt();
                switch(method){
                //case Protocol.LOGIN: done on accept
                case Protocol.LOGOUT:
                    try {
                        //Service.instance().logout(user);
                    } catch (Exception ex) {}
                    stop();
                    break;                 
                case Protocol.SEND:
                    String message=null;
                    try {
                        message = (String)in.readObject();
                        //Service.instance().post(user.getId()+": "+message);
                    } catch (ClassNotFoundException ex) {}
                    break;                     
                }
                out.flush();
            } catch (IOException  ex) {
                continuar = false;
            }                        
        }
    }
    
    public void deliver(String message){
        try {
            out.writeInt(Protocol.DELIVER);
            out.writeObject(message);
            out.flush();
        } 
        catch (IOException ex) {}
    }
}
