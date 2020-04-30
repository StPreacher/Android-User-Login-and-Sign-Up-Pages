package com.mucahiddogan.userlogin3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.internal.$Gson$Preconditions;

public class SignInActivity extends AppCompatActivity {

    EditText eMailText,passwordText;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        eMailText = findViewById(R.id.eMailText);
        passwordText = findViewById(R.id.passwordText);
        firebaseAuth = FirebaseAuth.getInstance();
    }


    public void signInBtn(View v){

        if(eMailText.getText().toString().matches("") || passwordText.getText().toString().matches("")){
            Toast.makeText(this, "Boş bırakmayınız", Toast.LENGTH_SHORT).show();
        }else{

            String email = eMailText.getText().toString();
            String pass = passwordText.getText().toString();

            firebaseAuth.signInWithEmailAndPassword(email,pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {

                    Intent intent = new Intent(SignInActivity.this,HomeActivity.class);
                    //Eski aktiviteli temizleme
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    Toast.makeText(SignInActivity.this, e.getLocalizedMessage().toString(), Toast.LENGTH_SHORT).show();

                }
            });

        }



    }



}
