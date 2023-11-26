package com.example.terceirotrabalho.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.terceirotrabalho.MainActivity;
import com.example.terceirotrabalho.R;

import com.example.terceirotrabalho.adapters.HomeworkAdapter;
import com.example.terceirotrabalho.alarm.MyAlarm;
import com.example.terceirotrabalho.database.AppDatabase;
import com.example.terceirotrabalho.fragments.DateFragment;
import com.example.terceirotrabalho.fragments.TimeFragment;
import com.example.terceirotrabalho.model.Homework;
import com.example.terceirotrabalho.model.User;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.List;


public class CreateHomeworkActivity extends AppCompatActivity {
    EditText homeworkNameField, homeworkDescriptionField, userEmailField;
    AppDatabase database;
    static boolean isActivityRunning = false;
    Spinner menuSpinner;
    String[] menuOptions = {"MENU", "HOME", "CRIAR ATIVIDADE", "SAIR"};
    int year, month, day, hour, minute, loggedUserId;
    String homeworkName, homeworkDescription, userType;
    HomeworkAdapter homeworkAdapter;
    List<Homework> homeworks;
    ListView list;
    TextView textViewErrorFields, textViewErrorSpinner;
    User user;
    public static int isTheSameUser;
    public static final int REQUEST_CODE_EDIT_HOMEWORK = 1;
    public static final int RESULT_SUCCESS_EDIT_HOMEWORK = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_homework);

        isActivityRunning = true;
        database = AppDatabase.getAppDatabase(getApplicationContext());

        setUserOwnEmail();

        // ListView homeworks
        SharedPreferences preferences = getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        loggedUserId = preferences.getInt("userId", 0);

        list = findViewById(R.id.homeworksListView);
        homeworks = database.homeworkDao().getHomeworksForStudent(loggedUserId);
        homeworkAdapter = new HomeworkAdapter(this, homeworks);
        list.setAdapter(homeworkAdapter);
        // ListView homeworks

        // menuSpinner -----------------------------------------------------
        menuSpinner = findViewById(R.id.menuSpinnerCreateHomework);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, menuOptions);
        menuSpinner.setAdapter(adapter);

        menuSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                if (i == 1) {
                    Intent it_home = new Intent(CreateHomeworkActivity.this, HomeActivity.class);
                    startActivity(it_home);
                }
                if (i == 2) {
                    if (!CreateHomeworkActivity.isActivityRunning) {
                        Intent it_create_homework = new Intent(CreateHomeworkActivity.this, CreateHomeworkActivity.class);
                        startActivity(it_create_homework);
                    }
                }

                if (i == 3) {
                    Intent it_main_activity = new Intent(CreateHomeworkActivity.this, MainActivity.class);
                    it_main_activity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(it_main_activity);
                    finish(); // Certifique-se de finalizar a atividade atual para que não fique na pilha
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // menuSpinner -----------------------------------------------------
    }

    public void registerHomework(View v) {
        boolean isHomeworkAbleToSave = true;

        homeworkNameField = findViewById(R.id.homeworkNameEditValue);
        homeworkDescriptionField = findViewById(R.id.homeworkDescriptionEditValue);
        String userEmail = userEmailField.getText().toString();
        user = database.userDao().getUserByEmail(userEmail);

        homeworkName = homeworkNameField.getText().toString();
        homeworkDescription = homeworkDescriptionField.getText().toString();

        if (homeworkName.isEmpty() || homeworkDescription.isEmpty()) {
            isHomeworkAbleToSave = false;
            textViewErrorFields = findViewById(R.id.textViewErrorFields);
            textViewErrorFields.setVisibility(View.VISIBLE);
        }

        if (isHomeworkAbleToSave) {
            SharedPreferences preferences = getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
            int authorId = preferences.getInt("userId", 0);

            LocalDate homeworkDate = LocalDate.of(year, month, day);

            LocalTime homeworkTime = LocalTime.of(hour, minute, 0);

            long homeworkDateInSeconds = homeworkDate.atStartOfDay(ZoneOffset.UTC).toEpochSecond();
            long homeworkTimeInSeconds = homeworkTime.toSecondOfDay();

            Homework homework = new Homework(homeworkName, homeworkDescription, homeworkDateInSeconds,
                    homeworkTimeInSeconds, user.getUserId(), authorId);

            long result = database.homeworkDao().insertHomework(homework);

            if (result > 0) {
                MyAlarm alarm = new MyAlarm();

                alarm.setAlarm(getApplicationContext(), year, month, day, hour, minute, homeworkName, homeworkDescription);

              if (loggedUserId == user.getUserId()) {
                  homework.setId(result);
                  homeworkAdapter.addHomework(homework);
              }
                homeworkNameField.setText("");
                homeworkDescriptionField.setText("");
//                textViewErrorFields.setVisibility(View.GONE);
            }
        }
    }

    public void setDate(int yearValue, int monthValue, int dayValue) {
        year = yearValue;
        month = monthValue;
        day = dayValue;
    }

    public void setTime(int hourValue, int minuteValue) {
        hour = hourValue;
        minute = minuteValue;
    }

    public void showTimePickerDialog(View v) {
        DialogFragment timeFragment = new TimeFragment();
        Bundle args = new Bundle();
        args.putString("classOrigin", "CreateHomeworkActivity");
        timeFragment.setArguments(args);
        timeFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void showDatePickerDialog(View v) {
        DialogFragment dateFragment = new DateFragment();
        Bundle args = new Bundle();
        args.putString("classOrigin", "CreateHomeworkActivity");
        dateFragment.setArguments(args);
        dateFragment.show(getSupportFragmentManager(), "datePicker");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        SharedPreferences preferences = getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        int loggedUser = preferences.getInt("userId", 0);

        if (requestCode == REQUEST_CODE_EDIT_HOMEWORK && resultCode == RESULT_SUCCESS_EDIT_HOMEWORK) {
          if (loggedUser == isTheSameUser) {
              homeworks = database.homeworkDao().getHomeworksForStudent(loggedUser);
              homeworkAdapter.atualizarDados(homeworks);
          }
        }

    }

    public void setUserOwnEmail() {
        SharedPreferences preferences = getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        userType = preferences.getString("userType", "");
        int userId = preferences.getInt("userId", 0);

        User user = database.userDao().getUserById(userId);

        userEmailField = findViewById(R.id.userEmailCreateHomeworkValue);

        if (userType.equals("ALUNO")) {
            userEmailField.setText(user.getUserEmail());
        }
    }
}