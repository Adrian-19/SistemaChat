/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SistemaChat.presentation;

import SistemaChat.logic.User;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author adria
 */
public class UsuarioTableModel extends AbstractTableModel implements TableModel {
    String[] columnas = {"Username", "Status"};
    List<User> filas;
    
    public UsuarioTableModel(List<User> f){
        filas = f;
    }
    
    public int getColumnCount(){
        return 2;
    }
    
    public String getColumnName(int col){
        return columnas[col];
    }

    public int getRowCount() {
        return filas.size();
    }
    
    public Object getValueAt(int row, int col) {
        User u = filas.get(row);
        switch (col){
            case 0: return u.getUsername();
            case 1:return u.getEstado();
            default: return "";
        }
    }   
}
