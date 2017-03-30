package com.locatebus;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {

    EditText edit_email;
    EditText edit_pass;
    Button login;
    Button register;
    FirebaseAuth firebaseAuth;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edit_email = (EditText)  findViewById(R.id.login_email);
        edit_pass = (EditText)  findViewById(R.id.login_pass);
        login = (Button) findViewById(R.id.btn_login);
        register = (Button) findViewById(R.id.btn_register);
        firebaseAuth = FirebaseAuth.getInstance();
        pd=new ProgressDialog(this);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edit_email.getText().toString().trim();
                String pass = edit_pass.getText().toString().trim();
                pd.setMessage("Logging you in");
                pd.show();
                firebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            pd.hide();
                            Toast.makeText(login.this,"Login Success",Toast.LENGTH_SHORT).show();
                            Intent i=new Intent(login.this,trackBus.class);
                            startActivity(i);
                        }
                        else{
                            pd.hide();
                            Toast.makeText(login.this,"Login Failed",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(login.this,register.class);
                startActivity(i);
            }
        });
    }
}
