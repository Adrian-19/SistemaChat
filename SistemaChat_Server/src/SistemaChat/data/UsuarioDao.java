/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SistemaChat.data;

import SistemaChat.logic.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author adria
 */
public class UsuarioDao {
    public User read(String name) throws Exception
    {
        Database.getInstance();
        String sql = "select * from User where username=?";
        PreparedStatement stm = Database.getInstance().prepareStatement(sql);
        stm.setString(1, name); //
        ResultSet rs = Database.getInstance().executeQuery(stm);
        if(rs.next()){
            return from(rs);
        }
        else{
            throw new Exception ("Usuario no existe");
        }
    }
    
    public void update(User o) throws Exception{
        String sql="update User set estado=? "+
                "where username=?";
        PreparedStatement stm = Database.getInstance().prepareStatement(sql);
        stm.setString(1, o.getEstado());
        stm.setString(2, o.getUsername());        
        int count=Database.getInstance().executeUpdate(stm);
        if (count==0){
            throw new Exception("User no existe");
        }
    }
    
    public User from(ResultSet rs)
    {
        try{
            User u = new User();
            u.setUsername(rs.getString("username"));
            u.setPassword(rs.getString("password"));
            u.setEstado(rs.getString("estado"));
            return u;
        }
        catch (SQLException ex){
            return null;
        }
    }
    
    public void close(){
    }
}
