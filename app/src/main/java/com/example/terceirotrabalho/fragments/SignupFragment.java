package com.example.terceirotrabalho.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.terceirotrabalho.MainActivity;
import com.example.terceirotrabalho.R;
import com.google.android.material.button.MaterialButton;

public class SignupFragment extends Fragment {

    TextView errorTextFields, errorTextTypes;
    EditText userNameText, userEmailText, userPasswordText;
    Spinner mySpinner;
    String[] typeOptions = {"TIPO DE USUÁRIO", "ALUNO", "PROFESSOR", "PAI/MÃE", "TUTOR"};
    String userType;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signup, container, false);

        MaterialButton buttonRegister = view.findViewById(R.id.buttonRegister);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRegisterValues();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userNameText = view.findViewById(R.id.userNameRegisterValue);
        userEmailText = view.findViewById(R.id.userEmailRegisterValue);
        userPasswordText = view.findViewById(R.id.userEmailLoginValue);

        mySpinner = view.findViewById(R.id.typesSpinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_dropdown_item, typeOptions);
        mySpinner.setAdapter(adapter);

        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                userType = typeOptions[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void getRegisterValues() {
        boolean isAllFieldsOk = true;
        boolean isTypeOk = true;

        String userName = userNameText.getText().toString();
        String userEmail = userEmailText.getText().toString();
        String userPassword = userPasswordText.getText().toString();

        if (userName.isEmpty() || userEmail.isEmpty() || userPassword.isEmpty()) {
            errorTextFields = getView().findViewById(R.id.errorTextFields);
            errorTextFields.setVisibility(View.VISIBLE);
            isAllFieldsOk = false;
        }

        if (userType.equals(typeOptions[0])) {
            errorTextTypes = getView().findViewById(R.id.errorTextTypes);
            errorTextTypes.setVisibility(View.VISIBLE);
            isTypeOk = false;
        }

        if (isAllFieldsOk == true && isTypeOk == true) {
            ((MainActivity)getActivity()).registerUser(userName, userEmail, userPassword, userType);
        }
    }
}
