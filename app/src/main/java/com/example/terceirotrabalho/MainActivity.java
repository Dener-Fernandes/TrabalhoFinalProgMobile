package com.example.terceirotrabalho;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

import com.example.terceirotrabalho.fragments.LoginFragment;
import com.example.terceirotrabalho.fragments.SignupFragment;

public class MainActivity extends AppCompatActivity {
    LoginFragment loginFragment;

    SignupFragment signupFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginFragment = new LoginFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, loginFragment);
        transaction.addToBackStack("loginFragment");
        transaction.commit();
    }

    public void openSignUpFragment(View v) {
        signupFragment = new SignupFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, signupFragment);
        transaction.addToBackStack("signupFragment");
        transaction.commit();
    }

    public void returnToLoginFragment(View v) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        // Volta para o fragmento de login (substitui o fragmento de SignUp pelo fragmento de Login)
        fragmentManager.beginTransaction()
                .replace(R.id.frameLayout, new LoginFragment()) // Substitua LoginFragment pelo nome do seu fragmento de login
                .addToBackStack(null)
                .commit();
    }
}