/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SistemaChat.presentation.login;

import SistemaChat.logic.User;
import SistemaChat.presentation.ServiceProxy;

/**
 *
 * @author adria
 */
public class Controller {
    private View view;
    private Model model;
    private ServiceProxy proxy;

    public Controller(View view, Model model) {
        this.view = view;
        this.model = model;
        this.proxy = (ServiceProxy) ServiceProxy.getInstance(); 
        this.proxy.setController(this); 
        view.setController(this); 
        view.setModel(model); 
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

    void login(String userName, String pass) throws Exception{
        User aux = new User(); 
        aux.setUsername(userName);
        aux.setPassword(pass);
        User logged= proxy.getInstance().login(aux); 
        model.setCurrentUser(logged);
        model.commit(); 
    }
    

    

    
}
