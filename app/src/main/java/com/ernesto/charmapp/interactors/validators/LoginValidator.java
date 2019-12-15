package com.ernesto.charmapp.interactors.validators;

import android.util.Patterns;

public class LoginValidator {

    public boolean validate(String email, String password){
        boolean valid = true;

        if((email == null) || (!Patterns.EMAIL_ADDRESS.matcher(email).matches())){
            valid = false;
        }

        if(password == null){
            valid = false;
        }

        return valid;
    }

}
