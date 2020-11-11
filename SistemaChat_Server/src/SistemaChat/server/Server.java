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
        Service localService = (Service)(Service.getInstance());
        localService.setServer(this);
        boolean continuar = true;
        while (continuar) {
            try {
                Socket socket = server.accept();
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream() );
                try {
                    int method = in.readInt(); // should be Protocol.LOGIN                    
                    User user=(User)in.readObject();                          
                    try {
                        user=Service.getInstance().login(user);
                        XmlPersister.getInstance().setPath(user.getUsername()+".xml");
                        User recuperado = new User();
                        try{
                            //Recupera el usuario con el archivo xml
                            recuperado = XmlPersister.getInstance().load();
                        }
                        catch(Exception e){
                            System.out.println(e.getMessage());
                        }
                        
                        User nuevo = new User();
                        
                        // PRUEBA 
//                        nuevo.setUsername("diana");
//                        nuevo.setPassword("5678");
//                        nuevo.setEstado("online");
//                        recuperado.getUserList().add(nuevo);
                        
                        List<User> lista = Service.getInstance().getContactos(recuperado.getUserList());
                        user.setUserList(lista);
                        out.writeInt(Protocol.ERROR_NO_ERROR);
                        out.writeObject(user);
                        out.flush();
                        
                        // INICIALIZA LOS WORKER
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
        }
    }
    
    // METODO PARA TOMAR EN CONSIDERACION EN EL ENVIO DE MENSAJES
    public void deliver(Message message){
        System.out.println("estamos en deliver de server");
        for(Worker wk:workers){
          if(wk.getUser().getUsername().equals(message.getRecipient().getUsername()))
          {
             System.out.println("se enviara a " + wk.getUser().getUsername());
             wk.deliver(message);
            
          } 
          else{
              System.out.println("se enviara a " + wk.getUser().getUsername());
              wk.deliver(message);
          }
         
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
