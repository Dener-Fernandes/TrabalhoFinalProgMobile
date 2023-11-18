package com.example.terceirotrabalho.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.terceirotrabalho.MainActivity;
import com.example.terceirotrabalho.R;

public class HomeActivity extends AppCompatActivity {

    static boolean isActivityRunning = false;
    Spinner menuSpinner;
    String[] menuOptions = {"MENU", "HOME", "CRIAR ATIVIDADE","SAIR"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        isActivityRunning = true;

        menuSpinner = findViewById(R.id.menuSpinnerHome);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, menuOptions);
        menuSpinner.setAdapter(adapter);

        menuSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                if (i == 1) {
                   if (!HomeActivity.isActivityRunning) {
                       Intent it_home = new Intent(HomeActivity.this, HomeActivity.class);
                       startActivity(it_home);
                   }
                }
                if (i == 2) {
                    Intent it_create_homework = new Intent(HomeActivity.this, CreateHomeworkActivity.class);
                    startActivity(it_create_homework);
                }

                if (i == 3) {
                    Intent it_main_activity = new Intent(HomeActivity.this, MainActivity.class);
                    it_main_activity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(it_main_activity);
                    finish(); // Certifique-se de finalizar a atividade atual para que não fique na pilha
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}