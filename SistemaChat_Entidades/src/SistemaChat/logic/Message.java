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
<<<<<<< HEAD
public class Message implements Serializable {
=======
public class Message implements Serializable{
>>>>>>> 44c0d9465c1688a9cc7b987d561e4982d32db56c
    @XmlIDREF
    private User sender;
    private String text;

    public Message(User sender, String text) {
        this.sender = sender;
        this.text = text;
    }
    
    public Message() {
        this.sender = new User();
        this.text = "";
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
    
    
}
