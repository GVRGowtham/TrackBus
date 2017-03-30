package com.locatebus;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by new on 3/30/2017.
 */

public class register extends Activity {

    EditText edit_email;
    EditText edit_pass;
    EditText edit_confirm;
    Button register;
    FirebaseAuth firebaseAuth;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        edit_email = (EditText)  findViewById(R.id.reg_email);
        edit_pass = (EditText)  findViewById(R.id.reg_pass);
        edit_confirm = (EditText)  findViewById(R.id.reg_confirm);
        register = (Button) findViewById(R.id.btn_reg_submit);
        firebaseAuth = FirebaseAuth.getInstance();
        pd=new ProgressDialog(this);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = edit_email.getText().toString().trim();
                final String pass = edit_pass.getText().toString().trim();
                String confirm = edit_confirm.getText().toString().trim();
                if(pass.compareTo(confirm)==0){
                    pd.setMessage("Signing you up");
                    pd.show();
                    firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                pd.hide();
                                Toast.makeText(register.this,"Registration Success",Toast.LENGTH_SHORT).show();
                                pd.setMessage("Logging you in");
                                pd.show();
                                firebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if(task.isSuccessful()){
                                            pd.hide();
                                            Toast.makeText(register.this,"Login Success",Toast.LENGTH_SHORT).show();
                                            Intent i=new Intent(register.this,trackBus.class);
                                            startActivity(i);
                                        }
                                        else{
                                            pd.hide();
                                            Toast.makeText(register.this,"Login Failed",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                            else{
                                pd.hide();
                                Toast.makeText(register.this,"Sign up Failed",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(register.this,"Passwords do not match",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
