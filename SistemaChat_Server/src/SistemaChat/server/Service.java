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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        //Se estara creando un usuario nuevo para que este sea
        //el nuevo usuario en la base de datos que tenga el estado
        //de Online.
        User actualizar = new User();
        actualizar.setEstado("Online");
        actualizar.setUsername(result.getUsername());
        //Actualizara el usuario de la base de datos con el nuevo estado.
        usuarioDao.update(actualizar);
        return result;
    }
    
    public void logout(User u) throws Exception
    {
        server.remove(u);
    }

    @Override
    public List<User> getContactos(List<User> list) {
        User tmp = null;
        List<User> tmpList = new ArrayList<>();
        for(User u: list)
        {
            try {
                tmp = usuarioDao.read(u.getUsername());
            } catch (Exception ex) {
                Logger.getLogger(Service.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(tmp!=null)
            {
                tmpList.add(tmp);
            }
        }
        return tmpList;
    }
}
