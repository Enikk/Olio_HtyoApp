package com.example.myapplication;

import java.io.Serializable;
import java.util.ArrayList;

public class UserList implements Serializable { //Class that lists users in an Arraylist, serielizable to be able to be passed between activities.
    ArrayList<User> userlisted = new ArrayList<User>();

    public void printUserList(){ //Not actually needed in the program, just to make testing things easier
        for(User u : userlisted){
            System.out.println(u.getEmail());
            System.out.println(u.getPassword());
            System.out.println("Userlist should be printed above this.");
        }
    }

    public boolean UserExists(String email, String pass) { //checks if an user with a specific Name and Password combination exists within the list
        boolean found = false;
        for(User u : userlisted){
            if (u.getEmail().equals(email) && u.getPassword().equals(pass)){
                found=true;
            }
        }

        return found; //return true or false, depending if it did exist or not


    }


    public void createUser(String Email, String Pass){//Creates a new instance of user class and adds it to the list.
        User added = new User(Email, Pass);
        userlisted.add(added);
    }
}
