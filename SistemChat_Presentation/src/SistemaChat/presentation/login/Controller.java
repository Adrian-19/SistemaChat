/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SistemaChat.presentation.login;

import SistemaChat.data.XmlPersister;
import SistemaChat.logic.User;
import SistemaChat.presentation.ServiceProxy;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author adria
 */
public class Controller {
    private View view;
    private Model model;
    private ServiceProxy proxy;
    
    SistemaChat.presentation.chat.Model chat_Model;
    SistemaChat.presentation.chat.View chat_View;
    SistemaChat.presentation.chat.ControllerChat chat_Controller;
    
    public Controller(View view, Model model) {
        this.view = view;
        this.model = model;
        this.proxy = (ServiceProxy) ServiceProxy.getInstance(); 
        this.proxy.setControllerLogin(this); 
        view.setController(this); 
        view.setModel(model); 
        
        initOptions();
    }
    
    public void initOptions()
    {
        chat_Model = new SistemaChat.presentation.chat.Model();
        chat_View = new SistemaChat.presentation.chat.View();
        chat_Controller = new SistemaChat.presentation.chat.ControllerChat(chat_View, chat_Model);
        chat_Controller.setParent(this);
    }
    
    public Controller()
    {
        view = new View();
        model = new Model();
        
    }
    
    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public ServiceProxy getProxy() {
        return proxy;
    }

    public void setProxy(ServiceProxy proxy) {
        this.proxy = proxy;
    }
    
    public void show()
    {
        view.setVisible(true);
    }
    
    public void hide()
    {
        view.setVisible(false);
    }
    
    void login(String userName, String pass) throws Exception{
        User aux = new User(); 
        aux.setUsername(userName);
        aux.setPassword(pass);
        User logged= proxy.getInstance().login(aux);
        
        //Setea el nombre del archivo xml segun el nombre del usuario
        XmlPersister.getInstance().setPath(logged.getUsername()+".xml");
        
        User recuperado = new User();
        try{
            //Recupera el usuario con el archivo xml
            recuperado = XmlPersister.getInstance().load();
        }
        catch(Exception e){
        }
        
        logged.setChatList(recuperado.getChatList());
        logged.setUserList(recuperado.getUserList());
        model.setCurrentUser(logged);
        model.commit(); 
        this.hide();
        // Envia el usuario loggeado
        chat_Controller.preSet(logged);
        chat_Controller.show();
    }
    
    public void logout()
    {
        try {
            XmlPersister.getInstance().store(model.getCurrentUser());
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        try{
            proxy.logout(model.getCurrentUser());
        }
        catch(Exception ex){}
        this.show();
        System.out.println("saliendo");
        model.setCurrentUser(new User());
        model.commit();       
    }
    

    
}
