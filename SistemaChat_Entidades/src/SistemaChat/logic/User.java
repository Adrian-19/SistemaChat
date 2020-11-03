/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SistemaChat.logic;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author adria
 */
public class User {
    private String username;
    private String password;
    private List<User> userList;
    private List<Chat> chatList;

    public User(String username, String password, List<User> userList, List<Chat> chatList) {
        this.username = username;
        this.password = password;
        this.userList = userList;
        this.chatList = chatList;
    }
    
    public User() {
        this.username = "";
        this.password = "";
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
