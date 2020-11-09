/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SistemaChat.logic;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlIDREF;

/**
 *
 * @author adria
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Chat {
    @XmlIDREF
    private User receiver;
    private List<Message> messageList;

    public Chat(User receiver, List<Message> messageList) {
        this.receiver = receiver;
        this.messageList = messageList;
    }
    
    public Chat() {
        this.receiver = new User();
        this.messageList = new ArrayList<>();
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public List<Message> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<Message> messageList) {
        this.messageList = messageList;
    }
    
    
}
