/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SistemaChat.presentation;

import SistemaChat.presentation.login.Controller;
import SistemaChat.presentation.login.Model;
import SistemaChat.presentation.login.View;

/**
 *
 * @author adria
 */
public class Application {
    public static void main(String[] args)
    {
        Model model = new Model();
        View view= new View();
        Controller controller = new Controller(view,model);
        view.setVisible(true);
    }
}
