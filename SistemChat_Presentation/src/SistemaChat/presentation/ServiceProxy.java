/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SistemaChat.presentation;

import SistemaChat.logic.Message;
import SistemaChat.logic.User;
import SistemaChat.presentation.login.Controller;
import SistemaChat.presentation.chat.ControllerChat;
import SistemaChat.protocol.IService;
import SistemaChat.protocol.Protocol;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingUtilities;

/**
 *
 * @author adria
 */
public class ServiceProxy implements IService{

    private static IService instance;
    Socket socket;
    ObjectInputStream in;
    ObjectOutputStream out;
    Controller controllerLogin;
    ControllerChat controllerChat;
    
    public static IService getInstance(){
        if (instance == null){
            instance = new ServiceProxy();
        }
        return instance;
    }
    
    public ServiceProxy(){
    }
    
    private void connect() throws Exception // tomar en consideracion el SERVER y PORT
    {
        socket = new Socket(Protocol.SERVER, Protocol.PORT);
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());   
    }
    
    private void disconnect() throws Exception
    {
        socket.shutdownOutput();
        socket.close();
    }
    
    
    public User login(User u) throws Exception {
        connect(); // se conecta al servidor. deberia estar corriendo
        try{
            out.writeInt(Protocol.LOGIN);
            out.writeObject(u);
            out.flush();
            int respuesta = in.readInt();
            if(respuesta == Protocol.ERROR_NO_ERROR)
            {
                User user = (User) in.readObject();
                this.start(); // este inicializa el nuevo thread que escucha todos los deliver
                return user;
            }
            disconnect();
            throw new Exception("El usuario no existe");
        }
        catch(IOException | ClassNotFoundException e){
            return null;
        }
    }
    
    public List<User> getContactos(List<User> list)
    {
        try{
            out.writeInt(Protocol.SEARCH);
            out.writeObject(list);
            System.out.println("object outed");
            int answer = in.readInt();
            System.out.println(answer);
            // Si pudo encontrar los contactos, devuelve el protocol de NO ERROR
            // y procede a devolver la lista de contactos encontrada
            if(answer == Protocol.ERROR_NO_ERROR)
            {
                List<User> lista = (List<User>) in.readObject();
                return lista;
            }
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage()+" este es el msj?");
        }
        // Si no encuentra nada, devuelve una lista nueva vacia.
        return new ArrayList<>();
    }
    
    @Override
    public void logout(User u) throws Exception {
        out.writeInt(Protocol.LOGOUT);
        out.writeObject(u);
        out.flush();
        stop();
        disconnect();
    }
    
    
    
    // LISTENING 
    boolean continuar = true;
    
    public void start(){
        Thread t = new Thread(new Runnable(){
            public void run(){
                listen();
            }
        });
        continuar = true;
        t.start();
    }
    
    public void stop()
    {
        continuar = false;
    }
    
    public void listen()
    {
        int method;
        while (continuar) {
            try {
                System.out.println("se esta ejecutando esto?");
                method = in.readInt();
                switch(method){
                case Protocol.DELIVER:
                    try {
                        Message message=(Message)in.readObject();
                        send(message);
                    } 
                    catch (ClassNotFoundException ex) {}
                    break;
                    
                }
                
                out.flush();
            } catch (IOException  ex) {
                continuar = false;
            }                        
        }
    }
    
    @Override
    public void send(Message msg) {
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
               controllerChat.deliver(msg); // crear metodo deliver
            }
         }
      );
    }

    public void setControllerLogin(Controller controller) {
        this.controllerLogin = controller; 
    }
    
    public void setControllerChat(ControllerChat c)
    {
        controllerChat = c;
    }
    
}
