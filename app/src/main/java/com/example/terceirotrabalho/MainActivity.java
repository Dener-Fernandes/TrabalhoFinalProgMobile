package com.example.terceirotrabalho;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

import com.example.terceirotrabalho.fragments.LoginFragment;

public class MainActivity extends AppCompatActivity {

    LoginFragment loginFragment;
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


}