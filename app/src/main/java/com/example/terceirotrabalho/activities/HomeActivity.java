package com.example.terceirotrabalho.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.terceirotrabalho.MainActivity;
import com.example.terceirotrabalho.R;
import com.example.terceirotrabalho.database.AppDatabase;
import com.example.terceirotrabalho.model.Homework;
import com.example.terceirotrabalho.model.Teacher;
import com.example.terceirotrabalho.model.User;

import java.util.List;

public class HomeActivity extends AppCompatActivity {
    AppDatabase database;
    static boolean isActivityRunning = false;
    Spinner menuSpinner;
    String[] menuOptions = {"MENU", "HOME", "CRIAR ATIVIDADE", "SAIR"};
    private List<Homework> homeworks, homeworksFinished;
    String userType, userEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        isActivityRunning = true;
        database = AppDatabase.getAppDatabase(getApplicationContext());

        //Spinner menu
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

            };
            //Spinner menu
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getHomeworks();
        menuSpinner.setSelection(0);
    }

    private void getHomeworks() {
        SharedPreferences preferences = getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        userType = preferences.getString("userType", "");
        int userId = preferences.getInt("userId", 0);

        User user = database.userDao().getUserById(userId);
        TextView textViewHomeworksAvailable = findViewById(R.id.homeworksAvailable);
        TextView textViewHomeworksFinished = findViewById(R.id.homeworksFinished);
        Button buttonCreateHomework = findViewById(R.id.buttonCreateHomework);

        if(userType.equals("ALUNO")) {
            homeworks = database.homeworkDao().getHomeworksForStudentAndFinished(userId, false);
            homeworksFinished = database.homeworkDao().getHomeworksForStudentAndFinished(userId, true);
        } else {
            homeworks = database.homeworkDao().getHomeworkByAuthorAndFinished(userId, false);
            homeworksFinished = database.homeworkDao().getHomeworkByAuthorAndFinished(userId, true);
        }

        if(homeworks.size() > 0) {
            textViewHomeworksAvailable.setText("Total de " + homeworks.size() + " atividades disponíveis.");
        } else {
            textViewHomeworksAvailable.setText("Não existem atividades diponíveis.");
            buttonCreateHomework.setVisibility(View.VISIBLE);
        }

        if(homeworksFinished.size() > 0) {
            textViewHomeworksFinished.setText("Total de: " + homeworksFinished.size() + " atividades concluídas.");
        } else {
            textViewHomeworksFinished.setText("Não existem atividades concluídas.");
        }
    }

    public void openCreateHomework(View v) {
        Intent it_create_homework = new Intent(HomeActivity.this, CreateHomeworkActivity.class);
        startActivity(it_create_homework);
    }
}