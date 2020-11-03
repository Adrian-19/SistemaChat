/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SistemaChat.logic;

/**
 *
 * @author adria
 */
public class Message {
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