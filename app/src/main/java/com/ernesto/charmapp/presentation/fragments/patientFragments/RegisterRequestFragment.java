package com.ernesto.charmapp.presentation.fragments.patientFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.ernesto.charmapp.R;


public class RegisterRequestFragment extends Fragment {

    private EditText editTextName;

    private EditText editTextSurname;

    private EditText editTextEmail;

    private EditText editTextPhone;

    private Button sendBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_register_request, container, false);

        this.editTextName = v.findViewById(R.id.nameTxt_request);
        this.editTextSurname = v.findViewById(R.id.surnameTxt_request);
        this.editTextEmail = v.findViewById(R.id.emailTxt_request);
        this.editTextPhone = v.findViewById(R.id.phoneTxt_request);

        this.sendBtn = v.findViewById(R.id.saveBtn_request);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString();
                String surname = editTextSurname.getText().toString();
                String email = editTextEmail.getText().toString();
                String phone = editTextPhone.getText().toString();


            }
        });

        return v;
    }
}
