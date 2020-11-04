/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SistemaChat.server;

import SistemaChat.data.UsuarioDao;
import SistemaChat.logic.Message;
import SistemaChat.logic.User;
import SistemaChat.protocol.IService;

/**
 *
 * @author adria
 */
public class Service implements IService {
    
    private static IService instance;
    public static IService getInstance()
    {
        if(instance == null)
        {
            instance = new Service();
        }
        return instance;
    }
    
    Server server;
    private UsuarioDao usuarioDao;
    
    public Service(){
        usuarioDao = new UsuarioDao();
    }
    
    public void setServer(Server server){
        this.server = server;
    }
    
    public void send(Message m){
        server.deliver(m);
    }
    
    public User login(User u) throws Exception{
        User result = usuarioDao.read(u.getUsername());
        if(result==null)
        {
            throw new Exception("User does not exist");
        }
        if(!result.getPassword().equals(u.getPassword()))
        {
            throw new Exception("User does not exist");
        }
        return result;
    }
    
    public void logout(User u) throws Exception
    {
        server.remove(u);
    }
}
