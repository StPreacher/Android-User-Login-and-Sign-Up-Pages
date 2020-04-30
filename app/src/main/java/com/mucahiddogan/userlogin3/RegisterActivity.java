package com.mucahiddogan.userlogin3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.UUID;

public class RegisterActivity extends AppCompatActivity {

    EditText nameText, eMailText, phoneText, passwordText;
    TextView textView;
    ImageView imageView2;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        imageView2 = findViewById(R.id.imageView2);
        nameText = findViewById(R.id.nameText);
        eMailText = findViewById(R.id.eMailText);
        phoneText = findViewById(R.id.phoneText);
        passwordText = findViewById(R.id.passwordText);
        textView = findViewById(R.id.textView);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser != null){
            Intent intent = new Intent(RegisterActivity.this,HomeActivity.class);
            startActivity(intent);
            finish();
        }

    }
    public void signUpBtn(View v) {
        String email = eMailText.getText().toString();
        String pass = passwordText.getText().toString();
        String name = nameText.getText().toString();
        String phone = phoneText.getText().toString();

        if(name.matches("")){

            Toast.makeText(this, "The namebox cannot be empty ! ", Toast.LENGTH_SHORT).show();

        }else if(email.matches("")){

            Toast.makeText(this, "The emailbox cannot be empty ! ", Toast.LENGTH_SHORT).show();

        }else if(phone.matches("")){

            Toast.makeText(this, "The phonebox cannot be empty ! ", Toast.LENGTH_SHORT).show();

        }else if(pass.matches("")){

            Toast.makeText(this, "The passwordbox cannot be empty ! ", Toast.LENGTH_SHORT).show();

        }else {

            firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Toast.makeText(RegisterActivity.this, "BAşarılı", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                }
            });
            HashMap<String, Object> userInfo = new HashMap<>();
            userInfo.put("Name_Surname", name);
            userInfo.put("e_mail", email);
            userInfo.put("password", pass);
            userInfo.put("phone", phone);
            firebaseFirestore.collection("UserInfos").add(userInfo).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Toast.makeText(RegisterActivity.this, "Complete", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(RegisterActivity.this, e.getLocalizedMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            });
            Intent intent = new Intent(RegisterActivity.this,SignInActivity.class);
            startActivity(intent);
        }
    }
    public void imageView2(View v) {
        Intent intent = new Intent(RegisterActivity.this, SignInActivity.class);
        startActivity(intent);

}}
