package com.example.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import Constants.Constants;
import Repository.LoginsRepositoryImpl;

public class MainActivity extends AppCompatActivity {

    private LoginsRepositoryImpl repository;
    private ConstraintLayout constraintLayout;
    private TextView login,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        repository = LoginsRepositoryImpl.getInstance();
        constraintLayout = findViewById(R.id.constraint);
        login = findViewById(R.id.loginText);
        password = findViewById(R.id.passwordText);

    }

    public void onClickRegister(View v){
        String log = login.getText().toString();
        String pass = password.getText().toString();

        String result = repository.addLogin(log,pass);

        startActivity(makeIntent(log,result));

    }

    public void onClickLogin(View v){
        String log = ((TextView)findViewById(R.id.loginText)).getText().toString();
        String pass = ((TextView)findViewById(R.id.passwordText)).getText().toString();

        boolean result = repository.checkPassword(log,pass);
        if(result){
            startActivity(makeIntent(log,getString(R.string.loggedIn)));
        }
        else{
            String text = getString(R.string.youNotRegistered);
            Snackbar snackbar = Snackbar
                    .make(constraintLayout,text,Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }

    public Intent makeIntent(String login, String result){
        Intent intent = new Intent(MainActivity.this,FontStyling.class);
        intent.putExtra(Constants.LOGIN,login);
        intent.putExtra(Constants.RESULT,result);
        return intent;
    }
}
