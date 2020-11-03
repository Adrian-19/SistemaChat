/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SistemaChat.presentation.login;

import SistemaChat.presentation.ServiceProxy;

/**
 *
 * @author adria
 */
public class Controller {
    private View view;
    private Model model;
    private ServiceProxy proxy;

    public Controller(View view, Model model, ServiceProxy proxy) {
        this.view = view;
        this.model = model;
        this.proxy = proxy;
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
    
    
}
