/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SistemaChat.presentation.chat;

import SistemaChat.logic.User;
import SistemaChat.presentation.ServiceProxy;

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
        User nuevo = new User();
        // BORRAR LUEGO DE IMPLEMENTAR LA FUNCION DE AGREGAR CONTACTOS
        nuevo.setUsername("diana");
        nuevo.setPassword("5678");
        nuevo.setEstado("online");
        c.getUserList().add(nuevo);
        model.setContactsList(ServiceProxy.getInstance().getContactos(c.getUserList()));
        model.commit();
    }
    
    public void logout()
    { 
        this.hide();
        parent.logout();
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

}
