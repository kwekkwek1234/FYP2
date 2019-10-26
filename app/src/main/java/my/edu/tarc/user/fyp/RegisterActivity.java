package my.edu.tarc.user.fyp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView textLogin;
    private EditText textName, textEmail,textPassword,textcPassword;
    private Button buttonRegister;
    FirebaseDatabase database;
    DatabaseReference users;
    private ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        textName = (EditText) findViewById(R.id.editTextName);
        textEmail = (EditText)findViewById(R.id.editTextEmail);
        textPassword =(EditText) findViewById(R.id.editTextPassword);
        textcPassword = (EditText) findViewById(R.id.editTextcPassword);
        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        textLogin = (TextView) findViewById(R.id.textViewSignup);
        progressDialog = new ProgressDialog(this);

        buttonRegister.setOnClickListener(this);
        textLogin.setOnClickListener(this);
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        users = database.getReference("Users");
    }

    @Override
    public void onClick(View v) {
        if(v == buttonRegister){
            userRegister();
        }

        if(v == textLogin){
            finish();
        }
    }

    private void userRegister() {
        String name = textName.getText().toString();
        String email = textEmail.getText().toString();
        String password = textPassword.getText().toString();
        String cpassword = textcPassword.getText().toString();


        if(TextUtils.isEmpty(name)){
            Toast.makeText(this,"Please enter your name",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter your email",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter your password",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(cpassword)){
            Toast.makeText(this,"Please enter your password again",Toast.LENGTH_LONG).show();
            return;
        }
        if(!password.equals(cpassword)){
            Toast.makeText(this,"Password must be same",Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog.setMessage("Registering User...");
        progressDialog.show();
        final User user = new User(name,email,password);

        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Registered Successfully",Toast.LENGTH_LONG).show();
                    finish();
                    firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                    users.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(!dataSnapshot.child(user.getName()).exists()){
                                users.child(firebaseUser.getUid().toString()).setValue(user);
                                Toast.makeText(RegisterActivity.this, "Successful Register", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }else{
                    task.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });


    }
}
