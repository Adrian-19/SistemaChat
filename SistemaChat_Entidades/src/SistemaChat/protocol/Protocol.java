/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SistemaChat.protocol;

/**
 *
 * @author adria
 */
public class Protocol {
    public static final String SERVER = "localhost";
    public static final int PORT = 20000;

    public static final int LOGIN=1;
    public static final int LOGOUT=2;    
    public static final int SEND=3;
    public static final int SEARCH=4; // busqueda de contactos en base de datos
    public static final int VALIDCONT=5; //validación de contacto. Envía un contacto de la base de datos
    public static final int STATUS_CHANGE = 7;
    
    public static final int DELIVER=1;
    public static final int ADDCONTACT=2; 

    
    public static final int ERROR_NO_ERROR=0;
    public static final int ERROR_LOGIN=1;
    public static final int ERROR_LOGOUT=2;    
    public static final int ERROR_POST=3;
    public static final int ERROR_SEARCH=6;
    
}
