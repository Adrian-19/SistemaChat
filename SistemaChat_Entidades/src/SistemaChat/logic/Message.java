/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SistemaChat.logic;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlIDREF;

/**
 *
 * @author adria
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Message implements Serializable {
    @XmlIDREF
    private User sender;
    private String text;
    private User recipient;
    
    public Message(User sender, String text, User recipient) {
        this.sender = sender;
        this.text = text;
        this.recipient = recipient;
    }
    
    public Message() {
        this.sender = new User();
        this.text = "";
        this.recipient = new User();
    }
    
    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getRecipient() {
        return recipient;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }
    
    
}
