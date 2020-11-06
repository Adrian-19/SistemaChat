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
