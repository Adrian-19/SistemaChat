/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SistemaChat.server;

import SistemaChat.data.XmlPersister;
import SistemaChat.logic.Message;
import SistemaChat.logic.User;
import SistemaChat.protocol.Protocol;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
            //System.out.println("Worker awaiting commands");
            Thread thread = new Thread(new Runnable(){
                public void run(){
                    try {
                        listen();
                    } catch (Exception ex) {
                        
                    }
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
    
    public void listen() throws ClassNotFoundException, Exception{
        int method;
        while (continuar) {
            try {
                method = in.readInt();
                switch(method){
                //case Protocol.LOGIN: done on accept
                case Protocol.LOGOUT:
                    User actual = (User)in.readObject();
                    XmlPersister.getInstance().setPath(actual.getUsername()+".xml");
                    XmlPersister.getInstance().store(actual);
                    try {
                        Service.getInstance().logout(user);
                    } catch (Exception ex) {}
                    stop();
                    break;                 
                case Protocol.SEND:
                    System.out.println("esto no se deberia ejecutar ahorita");
                    Message message;
                    try {
                        System.out.println("aqui si?");
                        message = (Message)in.readObject();
                        System.out.println("esto");
                        message.setText(user.getUsername() + ": " + message.getText());
                        Service.getInstance().send(message);
                    } catch (ClassNotFoundException ex) {
                        System.out.println("excepcion");
                    }
                    break;
                case Protocol.SEARCH:
                    String username = (String) in.readObject();
                    User existeC = new User();
                    try{
                        existeC= Service.getInstance().readContactFromDB(username); //le paso el nombre de usuario para verificar
                        out.writeInt(Protocol.VALIDCONT); //para que lo reciba el listener del proxyServer
                        System.out.println("Envía el protocolo ValidCont al listener del proxy");
                        out.writeObject(existeC);  //envía serializado un usuario para agregar a contactos
                    }catch(Exception ex){
                        out.writeInt(Protocol.ERROR_SEARCH);
                    }
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
    
    public void statusChange(User u)
    {
        try{
            out.writeInt(Protocol.STATUS_CHANGE);
            out.writeObject(u);
            out.flush();
        }catch(IOException ex){}
    }
    
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    
}
