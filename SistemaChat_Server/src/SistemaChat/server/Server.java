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
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author adria
 */
public class Server {
    ServerSocket server;
    List<Worker> workers; 
    
    public Server()
    {
        try {
            server = new ServerSocket(Protocol.PORT);
            workers =  Collections.synchronizedList(new ArrayList<Worker>());
        } 
        catch (IOException ex) {}
    }
    
    public void run(){ // llamado cuando el server se inicializa
        //Service localService = (Service)(Service.instance()); //  implementar Service
        //localService.setSever(this);
        boolean continuar = true;
        while (continuar) {
            try {
                Socket socket = server.accept();
                System.out.println("new socket");
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream() );
                try {
                    int method = in.readInt(); // should be Protocol.LOGIN                    
                    User user=(User)in.readObject();                          
                    try {
                        //user=Service.instance().login(user);   //  implementar Service
                        out.writeInt(Protocol.ERROR_NO_ERROR);
                        out.writeObject(user);
                        out.flush();
                        Worker worker = new Worker(socket,in,out,user); 
                        workers.add(worker);                      
                        worker.start();                            
                    } catch (Exception ex) {
                       out.writeInt(Protocol.ERROR_LOGIN);
                       out.flush();
                    }                          
                } 
                catch (ClassNotFoundException ex) {}                

            } 
            catch (IOException ex) {}
            System.out.println("ciclo terminado");
        }
    }
    
    public void deliver(String message){
        for(Worker wk:workers){
          wk.deliver(message);
        }        
    } 
    
    public void remove(User u){
        for(Worker wk:workers) {
            if(wk.user.equals(u)){
                workers.remove(wk);
                try { wk.socket.close();} catch (IOException ex) {}
                break;
            }
        }
    }
}
