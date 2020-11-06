/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SistemaChat.presentation.chat;

import SistemaChat.logic.User;

/**
 *
 * @author adria
 */
public class Model extends java.util.Observable{
    User current;
    public Model(){
        current = new User();
    }
    
    public void setCurrent(User current) {
        this.current = current;   
    }

    public User getCurrent() {
        return current;
    }
    
    @Override
    public void addObserver(java.util.Observer o) {
        super.addObserver(o);
        this.commit();
    }
    
    public void commit(){
        this.setChanged();
        this.notifyObservers();        
    }   
}
