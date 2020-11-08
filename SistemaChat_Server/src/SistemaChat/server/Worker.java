/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SistemaChat.server;

import SistemaChat.logic.Message;
import SistemaChat.logic.User;
import SistemaChat.protocol.Protocol;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

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
                        Service.getInstance().logout(user);
                    } catch (Exception ex) {}
                    stop();
                    break;                 
                case Protocol.SEND:
                    Message message=null;
                    try {
                        message = (Message)in.readObject();
                        message.setText(user.getUsername() + ": " + message.getText());
                        Service.getInstance().send(message);
                    } catch (ClassNotFoundException ex) {}
                    break;
                case Protocol.SEARCH:
                    try{
                        List<User> lista = Service.getInstance().getContactos((List<User>) in.readObject());
                        // Debido a que no tira excepcion, se escribira el protocol de NO ERROR
                        out.writeInt(Protocol.ERROR_NO_ERROR);
                        // Se mandara en el outputstream la nueva lista creada
                        out.writeObject(lista);
                    }
                    catch(Exception e){}
                    break;
                }
                out.flush();
            } catch (IOException  ex) {
                continuar = false;
            }                        
        }
    }
    
    public void deliver(Message message){
        try {
            out.writeInt(Protocol.DELIVER);
            out.writeObject(message);
            out.flush();
        } 
        catch (IOException ex) {}
    }
}
