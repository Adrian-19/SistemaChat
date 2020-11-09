/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SistemaChat.logic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author adria
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class User implements Serializable{
    @XmlID
    private String username;
    
    private String password;
    private String estado;
    private List<User> userList;
    private List<Chat> chatList;

    public User(String username, String password, String estado, List<User> userList, List<Chat> chatList) {
        this.username = username;
        this.password = password;
        this.estado = estado;
        this.userList = userList;
        this.chatList = chatList;
    }
    
    public User() {
        this.username = "";
        this.password = "";
        this.estado = "";
        this.userList = new ArrayList<>();
        this.chatList = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    
    
    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public List<Chat> getChatList() {
        return chatList;
    }

    public void setChatList(List<Chat> chatList) {
        this.chatList = chatList;
    }
    
    
    
}
