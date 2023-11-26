package com.example.terceirotrabalho.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.terceirotrabalho.MainActivity;
import com.example.terceirotrabalho.R;
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

public class EditHomeworkActivity extends AppCompatActivity {
    AppDatabase database;
    Spinner menuSpinner, usersSpinner;
    String[] menuOptions = {"MENU", "HOME", "CRIAR ATIVIDADE","SAIR"};
    int homeworkId, year, month, day, hour, minute, loggedUserId;
    String homeworkName, homeworkDescription, userType, userEmail;
    TextView textViewErrorFields, textViewErrorSpinner;
    EditText homeworkNameEditField, homeworkDescriptionEditField, userEmailEditHomeworkField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_homework);

        database = AppDatabase.getAppDatabase(getApplicationContext());

        setUserOwnEmail();

        homeworkId = getIntent().getIntExtra("homeworkId", -1);
        homeworkName = getIntent().getStringExtra("homeworkName");
        homeworkDescription = getIntent().getStringExtra("homeworkDescription");

        homeworkNameEditField = findViewById(R.id.homeworkNameEditValue);
        homeworkDescriptionEditField = findViewById(R.id.homeworkDescriptionEditValue);

        homeworkNameEditField.setText(homeworkName);
        homeworkDescriptionEditField.setText(homeworkDescription);


        // menuSpinner -----------------------------------------------------
        menuSpinner = findViewById(R.id.menuSpinnerEditHomework);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, menuOptions);
        menuSpinner.setAdapter(adapter);

        menuSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                if (i == 1) {
                    Intent it_home = new Intent(EditHomeworkActivity.this, HomeActivity.class);
                    startActivity(it_home);
                }
                if (i == 2) {
                    Intent it_create_homework = new Intent(EditHomeworkActivity.this, CreateHomeworkActivity.class);
                    startActivity(it_create_homework);
                }

                if (i == 3) {
                    Intent it_main_activity = new Intent(EditHomeworkActivity.this, MainActivity.class);
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

    public void editHomework(View v) {
        boolean isHomeworkAbleToSave = true;
        String newHomeworkName, newHomeworkDescription;

        newHomeworkName = homeworkNameEditField.getText().toString();
        newHomeworkDescription = homeworkDescriptionEditField.getText().toString();
        String userEmail = userEmailEditHomeworkField.getText().toString();
        User user = database.userDao().getUserByEmail(userEmail);

        if (newHomeworkName.isEmpty() || newHomeworkDescription.isEmpty()) {
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

            Homework homework = new Homework(newHomeworkName, newHomeworkDescription, homeworkDateInSeconds,
                    homeworkTimeInSeconds, user.getUserId(), authorId, false);

            homework.setId(homeworkId);

            database.homeworkDao().updateHomework(homework);

            MyAlarm alarm = new MyAlarm();

            alarm.setAlarm(getApplicationContext(), year, month, day, hour, minute, newHomeworkName, newHomeworkDescription);

            CreateHomeworkActivity.isTheSameUser = user.getUserId();
            setResult(CreateHomeworkActivity.RESULT_SUCCESS_EDIT_HOMEWORK);
            finish();
        }
    }

    public void backToHome(View v) {
        finish();
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

    public void showTimePickerDialogEdit(View v) {
        DialogFragment timeFragment = new TimeFragment();
        Bundle args = new Bundle();
        args.putString("classOrigin", "EditHomeworkActivity");
        timeFragment.setArguments(args);
        timeFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void showDatePickerDialogEdit(View v) {
        DialogFragment dateFragment = new DateFragment();
        Bundle args = new Bundle();
        args.putString("classOrigin", "EditHomeworkActivity");
        dateFragment.setArguments(args);
        dateFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void setUserOwnEmail() {
        SharedPreferences preferences = getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        userType = preferences.getString("userType", "");
        int userId = preferences.getInt("userId", 0);

        User user = database.userDao().getUserById(userId);

        userEmailEditHomeworkField = findViewById(R.id.emailUserEditHomeworkValue);

        if (userType.equals("ALUNO")) {
            userEmailEditHomeworkField.setText(user.getUserEmail());
        }
    }

}