package com.ernesto.charmapp.presentation.fragments.doctorFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ernesto.charmapp.R;
import com.ernesto.charmapp.data.RetrofitClient;
import com.ernesto.charmapp.domain.Doctor;
import com.ernesto.charmapp.domain.Patient;
import com.ernesto.charmapp.interactors.responses.ChangePasswordResponse;
import com.ernesto.charmapp.presentation.dialogs.ErrorDialog;
import com.ernesto.charmapp.presentation.fragments.patientFragments.PatientProfileFragment;

import net.cachapa.expandablelayout.ExpandableLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorProfileFragment extends Fragment {
    private Doctor doctor;

    private String name;
    private String email;
    private String phone;

    // Vistas del fragment
    private TextView nameFragmentHeader;
    private TextView emailFragmentHeader;

    private TextView nameFragment;
    private TextView emailFragment;
    private TextView phoneFragment;

    private Button expandBtnLayout;
    private ExpandableLayout expandableLayout;
    private EditText oldPasswordTxt;
    private String oldPassword;
    private EditText newPasswordTxt;
    private String newPassword;
    private Button changePassword;

    public static DoctorProfileFragment create(Doctor doctor) {
        Bundle args = new Bundle();
        args.putSerializable("doctor", doctor);
        DoctorProfileFragment f = new DoctorProfileFragment();
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        doctor = (Doctor) arguments.getSerializable("doctor");
        name = doctor.getName() + " " + doctor.getSurname1() + " " + doctor.getSurname2();
        email = doctor.getEmail();
        phone = doctor.getPhone().toString();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_doctor_profile, container, false);

        nameFragmentHeader = v.findViewById(R.id.nameLbl_DiagonalLayout_profile);
        nameFragmentHeader.setText(this.name);
        emailFragmentHeader = v.findViewById(R.id.emailLbl_DiagonalLayout_profile);
        emailFragmentHeader.setText(this.email);

        nameFragment = v.findViewById(R.id.nameLbl_profile);
        nameFragment.setText(this.name);
        emailFragment = v.findViewById(R.id.emailLbl_profile);
        emailFragment.setText(this.email);
        phoneFragment = v.findViewById(R.id.phoneLbl_profile);
        phoneFragment.setText(this.phone);

        expandBtnLayout = v.findViewById(R.id.expandBtn_profile);
        expandableLayout = v.findViewById(R.id.ExpandableLayout);
        expandBtnLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (expandableLayout.isExpanded()) {
                    expandBtnLayout.setCompoundDrawablesWithIntrinsicBounds(null, null, getContext().getDrawable(R.drawable.ic_expand), null);
                    expandableLayout.collapse();
                } else {
                    expandBtnLayout.setCompoundDrawablesWithIntrinsicBounds(null, null, getContext().getDrawable(R.drawable.ic_collapse), null);
                    expandableLayout.expand();
                }
            }
        });

        oldPasswordTxt = v.findViewById(R.id.oldPasswordTxt_profile);

        newPasswordTxt = v.findViewById(R.id.newPasswordTxt_profile);

        changePassword = v.findViewById(R.id.changePasswordBtn_profile);
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveFields();
                if ((oldPassword != null) && (newPassword != null)) {
                    final Call<ChangePasswordResponse> changePassword = RetrofitClient
                            .getInstance()
                            .getAPI()
                            .changePassword(email, oldPassword, newPassword);
                    changePassword.enqueue(new Callback<ChangePasswordResponse>() {
                        @Override
                        public void onResponse(Call<ChangePasswordResponse> call, Response<ChangePasswordResponse> response) {
                            ChangePasswordResponse changePasswordResponse = response.body();
                            Toast.makeText(getActivity(), changePasswordResponse.getMensaje(), Toast.LENGTH_LONG).show();
                            if (changePasswordResponse.getMensaje().equals("La contraseña actual no es correcta")) {
                                oldPasswordTxt.setError("La contraseña actual no es correcta");
                                oldPasswordTxt.requestFocus();
                            }
                        }

                        @Override
                        public void onFailure(Call<ChangePasswordResponse> call, Throwable t) {

                        }
                    });
                }
                else{
                    showErrorDialog();
                }
            }
        });

        return v;
    }

    public void saveFields() {
        oldPassword = oldPasswordTxt.getText().toString();
        newPassword = newPasswordTxt.getText().toString();
    }

    public void showErrorDialog() {
        ErrorDialog errorDialog = new ErrorDialog("Error: Revise los siguientes campos antes de continuar", "Contraseña actual");
        errorDialog.show(getActivity().getSupportFragmentManager(), "ERROR_DIALOG");
    }
}
