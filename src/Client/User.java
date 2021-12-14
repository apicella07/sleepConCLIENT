/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;
import java.io.Serializable;
import java.util.Objects;
import javafx.stage.Stage;


public class User implements Serializable { 
    
   private String username;
    private byte[] password;
    private String pass;
    
    private static final long serialVersionUID = -1;
   
  
    public User(){
        super();
    }
    
    public User(String username, byte[] password){
        super();
        this.username = username;
        this.password = password;
    }
    public User(String username, String pass){
       super();
        this.username = username;
        this.pass = pass; 
    }
    
    
    /*
    public boolean checkUser(Users u, String pas) {
        /* NECESITAMOS QUE users SEA LA LISTA DE USERS EN NUESTRA DATABASE 
        users=getUser();
        for (Users user: users) {
            if(user.authenticate(pas, user.getPassword(), user.getSalt())){
                return true;
            }
        }
        return false;
    }
    */

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return pass;
    }

    public void setPassword(String password) {
        this.pass = pass;
    }

    @Override
    public String toString() {
        return "User{" + "username=" + username + ", password=" + password + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.username);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        if (!Objects.equals(this.username, other.username)) {
            return false;
        }
        return true;
    }
    
}
