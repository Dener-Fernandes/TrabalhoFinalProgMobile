package com.example.terceirotrabalho;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.Toast;

import com.example.terceirotrabalho.activities.HomeActivity;
import com.example.terceirotrabalho.database.AppDatabase;
import com.example.terceirotrabalho.fragments.LoginFragment;
import com.example.terceirotrabalho.fragments.SignupFragment;
import com.example.terceirotrabalho.model.Parent;
import com.example.terceirotrabalho.model.Student;
import com.example.terceirotrabalho.model.Teacher;
import com.example.terceirotrabalho.model.Tutor;
import com.example.terceirotrabalho.model.User;

public class MainActivity extends AppCompatActivity {

    AppDatabase database;
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

    public void registerUser(String userName, String userEmail, String userPassword, String userType) {
        long result, finalResult = -1;

        database = AppDatabase.getAppDatabase(getApplicationContext());

        User user = new User(userName, userEmail, userPassword, userType);

        try {
            result = database.userDao().insertUser(user);

            int userId = (int) result;

            if (result >= 0) {
                if (userType == "ALUNO") {
                    Student student = new Student(userId);

                    finalResult = database.studentDao().insertStudent(student);
                }
                if (userType == "PROFESSOR") {
                    Teacher teacher = new Teacher(userId);
                    finalResult = database.teacherDao().insertTeacher(teacher);
                }
                if (userType == "PAI/MÃE") {
                    Parent parent = new Parent(userId);
                    finalResult = database.parentDao().insertParent(parent);
                }
                if (userType == "TUTOR") {
                    Tutor tutor = new Tutor(userId);
                    finalResult = database.tutorDao().insertTutor(tutor);
                }
            }

            if (finalResult >= 0) {
                // Obtém uma referência para o SharedPreferences
                SharedPreferences preferences = getSharedPreferences("loginInfo", Context.MODE_PRIVATE);

                // Obtém um editor para modificar os valores
                SharedPreferences.Editor editor = preferences.edit();

                // Adiciona as informações de login
                editor.putInt("userId", userId);
                editor.putString("userType", userType);
                editor.putBoolean("isLoggedIn", user.getIsUserLogged());

                // Aplica as alterações
                editor.apply();

                Intent it_home = new Intent(this, HomeActivity.class);
                startActivity(it_home);
            }
        } catch (Exception error) {
            Toast.makeText(this, "Erro ao cadastrar. Tente mais tarde", Toast.LENGTH_SHORT).show();
        }
    }

    public void loginUser(String userEmail, String userPassword) {
        database = AppDatabase.getAppDatabase(getApplicationContext());

       try {
           User user = database.userDao().getUserByEmail(userEmail);

           if (user != null) {
               if (userPassword.equals(user.getUserPassword())) {
                   SharedPreferences preferences = getSharedPreferences("loginInfo", Context.MODE_PRIVATE);

                   // Obtém um editor para modificar os valores
                   SharedPreferences.Editor editor = preferences.edit();

                   user.setIsUserLogged(true);
                   // Adiciona as informações de login
                   editor.putInt("userId", user.getUserId());
                   editor.putString("userType", user.getUserType());
                   editor.putBoolean("isLoggedIn", true);

                   // Aplica as alterações
                   editor.apply();

                   Intent it_home = new Intent(this, HomeActivity.class);
                   startActivity(it_home);
               } else {
                   Toast.makeText(this, "E-mail ou senha incorretos.", Toast.LENGTH_LONG).show();
                   return;
               }
           }
       } catch (Exception error) {
           Toast.makeText(this, "Erro ao realizar login. Tente mais tarde", Toast.LENGTH_SHORT).show();
       }

    }
}