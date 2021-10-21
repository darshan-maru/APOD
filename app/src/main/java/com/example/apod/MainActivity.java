package com.example.apod;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.apod.helper.UserDAO;
import com.example.apod.model.User;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editTextEmailLogin, editTextPasswordLogin;
    Button buttonLogIn, buttonSignUp;
    User user;
    UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    private void initialize() {
        editTextEmailLogin = findViewById(R.id.editTextEmailLogin);
        editTextPasswordLogin = findViewById(R.id.editTextPasswordLogin);

        buttonLogIn = findViewById(R.id.buttonLogIn);
        buttonSignUp = findViewById(R.id.buttonSignUp);

        buttonLogIn.setOnClickListener(this);
        buttonSignUp.setOnClickListener(this);

        userDAO = new UserDAO(getApplicationContext());
        Bundle data = getIntent().getExtras();
        if(data != null){
            editTextEmailLogin.setText(data.getString("email"));

            Log.i("USER:", user.getEmail());
            Log.i("PASSWORD:", user.getPassword());
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonLogIn: {
                buttonLogInClicked();
                break;
            }
            case R.id.buttonSignUp: {
                buttonSignUpClicked();
                break;
            }
            default:
                break;
        }
    }
    private void buttonLogInClicked() {
        String email = editTextEmailLogin.getText().toString();
        String password = editTextPasswordLogin.getText().toString();
        user = userDAO.find(email);

        if(!email.isEmpty() && !password.isEmpty()){
            if(user != null){
                if(editTextEmailLogin.getText().toString().equals(user.getEmail())){
                    if(user.isPasswordCorrect(password, user.getPassword())){
                        Intent ListActivity = new Intent(this, ListActivity.class);
                        startActivity(ListActivity);
                    } else {
                        Toast.makeText(getApplicationContext(), "Invalid password", Toast.LENGTH_LONG).show();
                        return;
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Invalid email", Toast.LENGTH_LONG).show();
                    return;
                }
            } else {
                Toast.makeText(getApplicationContext(), "No account associated to this email", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Invalid email and/or password", Toast.LENGTH_LONG).show();
        }
        return;

    }
    private void buttonSignUpClicked() {
        Intent SignUpActivity = new Intent(this, SignUpActivity.class);
        startActivity(SignUpActivity);
    }
}