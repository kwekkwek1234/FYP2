package my.edu.tarc.user.fyp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class WithdrawActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText withdrawAmount, accountNumber;
    private Button submit;
    private TextView available;
    private DatabaseReference ref,ref2;
    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw);

        withdrawAmount = findViewById(R.id.editwithdrawAmount);
        accountNumber = findViewById(R.id.editAccNo);
        submit = findViewById(R.id.btnSubmit);
        available = findViewById(R.id.textAvailable);

        auth = FirebaseAuth.getInstance();
        user =  auth.getCurrentUser();

        firebaseDatabase =FirebaseDatabase.getInstance();
        getAvailableBalance();

        submit.setOnClickListener(this);
    }

    private void getAvailableBalance() {

        ref = firebaseDatabase.getReference("Users").child(user.getUid()).child("Transaction");
        final Query balance = ref.orderByKey().limitToLast(1);
        balance.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String id = dataSnapshot.getChildren().iterator().next().getKey();
                    ref2 = ref.child(id);
                    ref2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Transaction tran =  dataSnapshot.getValue(Transaction.class);
                            available.setText(tran.getBalance() + ".00");
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }else{
                    Toast.makeText(getApplicationContext(),"No enough balance for withdraw", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v == submit){
            String amount = withdrawAmount.getText().toString().trim();
            String accNo = accountNumber.getText().toString().trim();
            String activity = "Withdraw";
            Intent intent = new Intent(this,TransactionConfirmation.class);
            intent.putExtra("amount",amount);
            intent.putExtra("activity",activity);
            intent.putExtra("accNo",accNo);
            startActivity(intent);
            finish();
        }
    }
}
