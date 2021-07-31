package com.example.myapplication;


import java.io.Serializable;


public class User implements Serializable{ //basic user class, serielizable so its possible to pass userlist around activities
    protected String pass;
    protected String email;

    User(String em, String pw){
        pass=pw;
        email=em;
    }

    public String getPassword(){
        return pass;
    }
    public String getEmail(){
        return email;
    }

}
