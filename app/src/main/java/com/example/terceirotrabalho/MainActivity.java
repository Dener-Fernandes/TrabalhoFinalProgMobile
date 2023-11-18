package com.example.terceirotrabalho;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.terceirotrabalho.cryptography.CryptographyUtils;
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

//        String userEncryptedPassword = CryptographyUtils.encryptPassword(userPassword);

//        Log.d("Cadastro", userEncryptedPassword);

        User user = new User(userName, userEmail, userPassword, userType);

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
            Log.d("CAdastro", "SUCESSO");
            // Obtém uma referência para o SharedPreferences
            SharedPreferences preferences = getSharedPreferences("login)info", Context.MODE_PRIVATE);

            // Obtém um editor para modificar os valores
            SharedPreferences.Editor editor = preferences.edit();

            // Adiciona as informações de login
            editor.putInt("user_id", userId);
            editor.putBoolean("is_logged_in", user.getIsUserLogged());

            // Aplica as alterações
            editor.apply();
        }
    }

    public void loginUser(String userEmail, String userPassword) {
        database = AppDatabase.getAppDatabase(getApplicationContext());

        User user = database.userDao().getUserByEmail(userEmail);

//        String userDecryptedPassword = CryptographyUtils.decryptPassword(user.getUserPassword());

     if (userPassword.equals(user.getUserPassword())) {
         Log.d("Resultado", "Usuário logado");
     } else {
         Log.d("Resultado", "E-mail ou senha incorretos");
     }

    }
}