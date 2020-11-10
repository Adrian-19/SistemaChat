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
import java.util.logging.Level;
import java.util.logging.Logger;
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
            else{
                disconnect();
                throw new Exception("El usuario no existe");
            }
        }
        catch(IOException | ClassNotFoundException e){
            return null;
        }
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
                try {
                    listen();
                } catch (ClassNotFoundException ex) {

                }
            }
        });
        continuar = true;
        t.start();
    }
    
    public void stop()
    {
        continuar = false;
    }
    
    public void listen() throws ClassNotFoundException
    {
        int method;
        while (continuar) {
            try {
                method = in.readInt(); //
                System.out.println(method);
                switch(method){
                case Protocol.DELIVER:
                    try {
                        Message message=(Message)in.readObject();
                        deliver(message);
                    } 
                    catch (ClassNotFoundException ex) {}
                    break;
                case Protocol.VALIDCONT:
                    System.out.println("Se ejecuta el caso VALIDCONT del listen del ServiceProxy");
                    User temp = (User) in.readObject(); //Falta agregar este usuario a la lista de contastos del model!
                    System.out.println("Se deserializa el contacto que viene de la BD");
                    agregarCont(temp); //si esxiste, este método lo agrega al ControllerChat/Model //usa runnable
                    
                }
                
                out.flush();
            } catch (IOException  ex) {
                continuar = false;
            }                        
        }
    }
    
    @Override
    public void send(Message msg)
    {
        try{
            System.out.println("se ejecuta?");
            out.writeInt(Protocol.SEND);
            out.writeObject(msg);
            out.flush();
        }catch(IOException ex){}
    }
    
    private void deliver(Message msg) {
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
               controllerChat.deliver(msg);
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

    @Override
    public List<User> getContactos(List<User> list) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void addContact(String username)throws IOException{ //------------------------------------- OK!!
        System.out.println("Se ejecuta addContact del ServiceProxy");
        out.writeInt(Protocol.SEARCH);
        out.writeObject((Object) username);
        out.flush();
    } 
    
    
    public void agregarCont(User contacto){ //------------------------------------------------------OK!!!!
        SwingUtilities.invokeLater(new Runnable(){
            
            public void run(){ 
                //HACER VALIDACIONES...
                //NO RECUPERA...
                if(contacto == null){
                    
                    try {
                        throw new Exception("El contacto no existe");
                    } catch (Exception ex) {
                        Logger.getLogger(ServiceProxy.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                } 
                else{
                    controllerChat.agregarContacto(contacto);
                    System.out.println("Se agrego contacto al controllerChat");
                }
               
            }
        }
        );
    }

    @Override
    public User readContactFromDB(String username) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
