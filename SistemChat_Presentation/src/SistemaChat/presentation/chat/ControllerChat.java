/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SistemaChat.presentation.chat;

import SistemaChat.logic.Chat;
import SistemaChat.logic.Message;
import SistemaChat.logic.User;
import SistemaChat.presentation.ServiceProxy;
import java.util.ArrayList;

/**
 *
 * @author adria
 */
public class ControllerChat {
    View view;
    Model model;
    ServiceProxy proxy;
    
    SistemaChat.presentation.login.Controller parent;
    
    public ControllerChat(View v, Model m)
    {
        view = v;
        model = m;
        proxy = (ServiceProxy) ServiceProxy.getInstance();
        proxy.setControllerChat(this);
        view.setController(this);
        view.setModel(model);
    }
    
    // Hace una actualizacion de los datos que se presentaran en la pantalla.
    // Recibe como parametro el usuario que fue loggeado para tenerlo en 
    // consideracion en este nuevo M.V.C
    public void preSet(User c)
    {
        model.setCurrent(c);
        model.setRecipient(new User());
        model.setContactsList(c.getUserList());
        model.commit();
    }
    
    public void send(Message msg)
    {
        proxy.send(msg);
    }
    
    public void deliver(Message message){
        for(Chat c : model.getCurrent().getChatList())
        {
            String chatReceiver = c.getReceiver().getUsername();
            String messageRecipient = message.getRecipient().getUsername();
            String messageSender = message.getSender().getUsername();
            if(chatReceiver.equals(messageRecipient)){
                c.getMessageList().add(message);
                model.commit();
                return;
            }else if(chatReceiver.equals(messageSender))
            {
                c.getMessageList().add(message);
                model.commit();
                return;
            }
        }
        
    }    
    
    public void logout()
    { 
        this.hide();
        parent.logout(model.getCurrent());
    }
    
    public void seleccionar(int row)
    {
        model.setRecipient(model.getContactsList().get(row));
        // Busca el chat DEL CURRENT USUARIO que tenga el mismo recipient que 
        // el recipiente del model
        System.out.println("s");
        for(Chat c : model.getCurrent().getChatList())
        {
            if(c.getReceiver() == model.getRecipient())
            {
                model.setChat(c);
                model.commit();  
                return;
            }
        }
        // Si no encuentra el chat, significa que no existe uno
        model.getCurrent().getChatList().add(new Chat(model.getRecipient(), new ArrayList<>()));
        for(Chat c : model.getCurrent().getChatList())
        {
            if(c.getReceiver() == model.getRecipient())
            {
                System.out.println("se creo un nuevo chat");
                model.setChat(c);
                model.commit();  
                return;
            }
        }
    }
    
    public void setParent(SistemaChat.presentation.login.Controller p)
    {
        parent = p;
    }
    
    public void show()
    {
        view.setVisible(true);
    }
    
    public void hide()
    {
        view.setVisible(false);
    }
    
    void addContact(String text) throws Exception { //------------  OK!!!!!
       for(User u : model.getContactsList())
       {
           if (u.getUsername() == null ? text == null : u.getUsername().equals(text))
           {
               throw new Exception("Contacto ya existe");
           }
       }
        proxy.addContact(text);
        
    }
    public void agregarContacto(User u){ //-------------------  OK !!!!
        if(u==null)
        {
            view.mensajeUsuarioNoEncontrado("Usuario no existe");
            return;
        }
        //model.getContactsList().add(u);
        model.getCurrent().getUserList().add(u);
        model.commit(); 
    }


    
    

}
