/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SistemaChat.presentation.chat;

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
