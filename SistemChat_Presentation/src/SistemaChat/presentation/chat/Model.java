/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SistemaChat.presentation.chat;

import SistemaChat.logic.Chat;
import SistemaChat.logic.Message;
import SistemaChat.logic.User;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author adria
 */
public class Model extends java.util.Observable{
    User current;
    User recipient;
    List<User> contactsList;
    Chat chat;
    
    public Model(){
        current = new User();
        recipient = new User();
        contactsList = new ArrayList<>();
        chat = new Chat();
    }
    
    public void setCurrent(User current) {
        this.current = current;   
    }

    public User getCurrent() {
        return current;
    }

    public User getRecipient() {
        return recipient;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    public List<User> getContactsList() {
        return contactsList;
    }

    public void setContactsList(List<User> contactsList) {
        this.contactsList = contactsList;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
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
