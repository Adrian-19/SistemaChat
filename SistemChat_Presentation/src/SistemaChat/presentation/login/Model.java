
package SistemaChat.presentation.login;

import SistemaChat.logic.User;
import java.util.ArrayList;
import java.util.List; 

/**
 *
 * @author adria
 */
public class Model extends java.util.Observable {
    
    User currentUser; 
    List<String> messages; 
    
    public Model() {
       currentUser = null;
       messages=new ArrayList<>();
    }
    public User getCurrentUser() {
        return currentUser;
    }
        public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
    public List<String> getMessages() {
        return messages;
    }
    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    @Override
    public void addObserver(java.util.Observer o) {
        super.addObserver(o);
        this.commit();
    }

    public void commit() {
        this.setChanged(); 
        this.notifyObservers();
    }
    
}
