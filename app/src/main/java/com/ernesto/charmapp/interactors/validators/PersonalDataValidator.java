package com.ernesto.charmapp.interactors.validators;

import android.util.Patterns;
import android.widget.RadioButton;

import java.util.regex.Pattern;

public class PersonalDataValidator {

    private final Pattern PHONE_VALIDATOR = Pattern.compile("^(6|7)[0-9]{8}$");
    private final Pattern POSTAL_CODE_VALIDATOR = Pattern.compile("^((((01)|([1-4]{1}[0-9]{1})|(5[0-2]{1}))([0-9]{2})[1-9]{1})|(((0[2-9]{1})|([1-4]{1}[0-9]{1})|(5[0-2]{1}))([0-9]{3})))$");
    private String wrongFields;

    public PersonalDataValidator() {
        this.wrongFields = "";
    }

    // Con el spinner hacer un switch de los id de las opciones

    public boolean validate(String email, String phone, String name, String surname1, String surname2, String birthDate, String gender, String profession,
                            RadioButton professionPlace, String sector, String otherSector, String homeCP, String jobCP) {
        boolean valid = true;

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            valid = false;
            wrongFields += "Email\n";
        }

        if(!PHONE_VALIDATOR.matcher(phone).matches()){
            valid = false;
            wrongFields += "Teléfono\n";
        }

        if((name.isEmpty()) || (name.length() == 0)){
            valid = false;
            wrongFields += "Nombre\n";
        }

        if((surname1.isEmpty()) || (surname1.length() == 0)){
            valid = false;
            wrongFields += "Primer apellido\n";
        }

        if((surname2.isEmpty()) || (name.length() == 0)){
            valid = false;
            wrongFields += "Segundo apellido\n";
        }

        if((birthDate.isEmpty()) || (birthDate.length() != 10)){
            valid = false;
            wrongFields += "Fecha de nacimiento\n";
        }

        if((gender.isEmpty()) || (gender.length() == 0)){
            valid = false;
            wrongFields += "Sexo\n";
        }

        if((profession.isEmpty()) || (profession.length() == 0)){
            valid = false;
            wrongFields += "Profesión\n";
        }

        if(professionPlace == null){
            valid = false;
            wrongFields += "Lugar de profesión\n";
        }

        if((sector.isEmpty()) || (sector.length() == 0)){
            valid = false;
            wrongFields += "Sector de profesión\n";
        }
        if(sector.equals("Otros")){
            if((otherSector.isEmpty()) || (otherSector.length() == 0)){
                valid = false;
                wrongFields += "Por favor, especifique el sector en el campo 'Otro'\n";
            }
        }

        if(!POSTAL_CODE_VALIDATOR.matcher(homeCP).matches()){
            valid = false;
            wrongFields += "Código postal del lugar de residencia\n";
        }

        if(!POSTAL_CODE_VALIDATOR.matcher(jobCP).matches()){
            valid = false;
            wrongFields += "Código postal del lugar de trabajo\n";
        }

        return valid;
    }

    public String getWrongFields() {
        return this.wrongFields;
    }

    public void setWrongFields(String wrongFields) {
        this.wrongFields = wrongFields;
    }

}
