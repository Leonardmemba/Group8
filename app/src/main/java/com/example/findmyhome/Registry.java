package com.example.findmyhome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Registry extends AppCompatActivity {

    EditText email, password, confirmPassword;
    Button registerButton;
    FirebaseAuth fAuth;
    TextView backButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registry);

        email = findViewById(R.id.emailRegister);
        password = findViewById(R.id.passwordRegister);
        confirmPassword = findViewById(R.id.passwordRegisterConfirm);
        registerButton = findViewById(R.id.registerButton);
        backButton = findViewById(R.id.textView7);

        fAuth = FirebaseAuth.getInstance();

        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), Login.class));
            finish();
        }

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email2 = email.getText().toString().trim();
                String password2 = password.getText().toString().trim();
                String password3 = confirmPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email2)){
                    email.setError("Email is required.");
                    return;
                }
                if(TextUtils.isEmpty(password2)){
                    password.setError("Password is required.");
                    return;
                }
                if(TextUtils.isEmpty(password3)){
                    password.setError("Password confirm is required.");
                    return;
                }
                if(password2.equals(password3)){ //start of password check/else if statement
                //register user in firebase

                fAuth.createUserWithEmailAndPassword(email2,password2).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){



                            Toast.makeText(Registry.this, "Account created successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), Login.class));



                        }else{
                            Toast.makeText(Registry.this, "Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                } //end of if
                else{  Toast.makeText(Registry.this, "Please match your passwords!", Toast.LENGTH_SHORT).show();  } //end of password check

            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Registry.this, Login.class));

            }
        });

    }


    public void haveAcctAlready(View view){
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

}