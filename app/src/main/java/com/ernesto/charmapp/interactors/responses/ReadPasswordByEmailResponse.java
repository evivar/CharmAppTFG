package com.ernesto.charmapp.interactors.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReadPasswordByEmailResponse {

    @SerializedName("Contraseña")
    @Expose
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
