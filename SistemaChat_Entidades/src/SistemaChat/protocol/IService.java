/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SistemaChat.protocol;

import SistemaChat.logic.Message;
import SistemaChat.logic.User;
import java.util.List;

/**
 *
 * @author adria
 */
public interface IService {
    public User login(User u) throws Exception;
    public void logout(User u) throws Exception;
    public void send(Message msg);
    
    public List<User> getContactos(List<User> list);
}
