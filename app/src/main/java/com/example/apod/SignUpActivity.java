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

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    EditText editTextFirstName, editTextLastName, editTextEmail, editTextPassword, editTextConfirmPassword;
    Button buttonSignup, buttonBackSignup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initialize();

    }

    @Override
    public void onStart() {
        super.onStart();

    }

    private void initialize() {
        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);

        buttonSignup = findViewById(R.id.buttonSignup);
        buttonBackSignup = findViewById(R.id.buttonBackSignup);

        buttonSignup.setOnClickListener(this);
        buttonBackSignup.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.buttonSignup){
            registerUser();
        } else if(view.getId() == R.id.buttonBackSignup){
            goBack();
        }

    }

    private void goBack() {
        Intent MainActivity = new Intent(this, MainActivity.class);
        startActivity(MainActivity);
    }

    private void registerUser() {
        String firstName = editTextFirstName.getText().toString();
        String lastName = editTextLastName.getText().toString();
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();
        String confirmPassword = editTextConfirmPassword.getText().toString();

        String emailRegex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern emailPattern = Pattern.compile(emailRegex);
        Matcher emailMatcher = emailPattern.matcher(email);

        String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,20}$";
        Pattern passwordPattern = Pattern.compile(passwordRegex);
        Matcher passwordMatcher = passwordPattern.matcher(password);

        if(firstName.isEmpty()){
            Toast.makeText(getApplicationContext(), "First name cannot be empty", Toast.LENGTH_LONG).show();
            return;
        }else if(lastName.isEmpty()){
            Toast.makeText(getApplicationContext(), "Last name cannot be empty", Toast.LENGTH_LONG).show();
            return;
        }else if(!emailMatcher.matches()){
            Toast.makeText(getApplicationContext(), "Invalid email", Toast.LENGTH_LONG).show();
            return;
        }else if(!passwordMatcher.matches()){
            Toast.makeText(getApplicationContext(), "Password should be between 8 and 20 characters," +
                    "contain at least one lowercase character, one uppercase and one number (0-9)", Toast.LENGTH_LONG).show();
            return;
        }else if(!password.equals(confirmPassword)){
            Toast.makeText(getApplicationContext(), "Passwords must match", Toast.LENGTH_LONG).show();
            return;
        } else{
            UserDAO userDAO = new UserDAO(getApplicationContext());
            User user = new User(firstName, lastName, email, password);

            if(userDAO.save(user)){
                Log.i("DATABASE", "New user saved");
            } else {
                Log.i("DATABASE", "Could not save user.");
            }
            Intent MainActivity = new Intent(this, MainActivity.class);
            MainActivity.putExtra("email", email);
            startActivity(MainActivity);
        }



    }
}