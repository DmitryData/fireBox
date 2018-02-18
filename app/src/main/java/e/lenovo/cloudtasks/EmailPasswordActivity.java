package e.lenovo.cloudtasks;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EmailPasswordActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private EditText ETemail;
    private EditText ETpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_password);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    //user is signed in
                    Intent intent = new Intent(EmailPasswordActivity.this, ListTasks.class);
                    startActivity(intent);
                } else {
                    //user is signed out
                }
            }
        };

        ETemail = (EditText) findViewById(R.id.etEmail);
        ETpass = (EditText) findViewById(R.id.etPassword);
        findViewById(R.id.btn_log).setOnClickListener(this);
        findViewById(R.id.btn_reg).setOnClickListener(this);

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            //user is signed in
            Intent intent = new Intent(EmailPasswordActivity.this, ListTasks.class);
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.btn_log){
            signing(ETemail.getText().toString(),ETpass.getText().toString());
        }
        else if(view.getId()==R.id.btn_reg){
            registration(ETemail.getText().toString(),ETpass.getText().toString());
        }
    }

    public void signing (String Email, String Password){
        mAuth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(EmailPasswordActivity.this,"Авторизация успешна",Toast.LENGTH_SHORT).show();
                }else{ Toast.makeText(EmailPasswordActivity.this,"Авторизация провалена",Toast.LENGTH_SHORT).show();}
            }
        });
    }

    public void registration (String Email, String Password){
        mAuth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(EmailPasswordActivity.this,"Регистрация успешна",Toast.LENGTH_SHORT).show();
                }else{ Toast.makeText(EmailPasswordActivity.this,"Регистрация провалена",Toast.LENGTH_SHORT).show();}
            }
        });
    }
}
