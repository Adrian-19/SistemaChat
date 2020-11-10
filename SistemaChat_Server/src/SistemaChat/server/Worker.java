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
                System.out.println("method: "+method);
                switch(method){
                //case Protocol.LOGIN: done on accept
                case Protocol.LOGOUT:
                    try {
                        Service.getInstance().logout(user);
                    } catch (Exception ex) {}
                    stop();
                    break;                 
                case Protocol.SEND:
<<<<<<< HEAD
                    System.out.println("esto no se deberia ejecutar ahorita");
                    Message message;
=======
                    Message message=null;
>>>>>>> 44c0d9465c1688a9cc7b987d561e4982d32db56c
                    try {
                        message = (Message)in.readObject(); 
                        message.setText(user.getUsername() + ": " + message.getText());
                        Service.getInstance().send(message);
                    } catch (ClassNotFoundException ex) {}
                    break;
                    
                case Protocol.SEARCH:
                    System.out.println("Se ejecuta el caso SEARCH del listen del worker");
                    String username = (String) in.readObject(); 
                    User existeC= Service.getInstance().readContactFromDB(username); //le paso el nombre de usuario para verificar
                    
                    
                    out.writeInt(Protocol.VALIDCONT); //para que lo reciba el listener del proxyServer
                    System.out.println("Envía el protocolo ValidCont al listener del proxy");
                    out.writeObject(existeC);  //envía serializado un usuario para agregar a contactos

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
