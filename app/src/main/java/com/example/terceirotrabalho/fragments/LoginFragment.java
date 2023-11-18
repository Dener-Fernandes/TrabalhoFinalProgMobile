package com.example.terceirotrabalho.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.terceirotrabalho.MainActivity;
import com.example.terceirotrabalho.R;
import com.google.android.material.button.MaterialButton;


public class LoginFragment extends Fragment {
    TextView errorTextFieldsLogin;
    EditText userEmailText, userPasswordText;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        MaterialButton buttonRegister = view.findViewById(R.id.buttonLogin);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLoginValues();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userEmailText = view.findViewById(R.id.userEmailLoginValue);
        userPasswordText = view.findViewById(R.id.userPasswordLoginValue);
    }

    public void getLoginValues() {
        String userEmail = userEmailText.getText().toString();
        String userPassword = userPasswordText.getText().toString();

        if (userEmail.isEmpty() || userPassword.isEmpty()) {
            errorTextFieldsLogin = getView().findViewById(R.id.errorTextFieldsLogin);
            errorTextFieldsLogin.setVisibility(View.VISIBLE);
        } else {
            ((MainActivity)getActivity()).loginUser(userEmail, userPassword);
        }
    }
}