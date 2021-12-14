/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import java.util.List;
import Client.User;

public interface UserManagerInterface {

    public void connect();

    public void disconnect();

    public User checkPasswordGood(User user);
    
    public User checkUserGood(String username);

    public User getUserByDNI(String dni);


    public void createUserRegister(User newuser);
    
    public String getPassword(String dni);

}
