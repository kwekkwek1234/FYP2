package my.edu.tarc.user.fyp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TransactionDoneActivity extends AppCompatActivity {

    private TextView tStatus,tID, tTo, tDesc,tAmount,tBalanceBefore, tBalanceAfter, tDateTime;
    private Button bBack;
    FirebaseDatabase database;
    DatabaseReference transaction;
    FirebaseAuth auth;
    FirebaseUser user;
    String status,tid,to,desc,amount;
    final Date date = Calendar.getInstance().getTime();
    final SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy  HH:mm");
    final String fd =  df.format(date);
    String newBalance1;
    Transaction trans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_done);

        tStatus = findViewById(R.id.status);
        tID = findViewById(R.id.transactionID);
        tTo = findViewById(R.id.To);
        tDesc = findViewById(R.id.transDesc);
        tAmount = findViewById(R.id.transAmount);
        tBalanceBefore = findViewById(R.id.balanceBefore);
        tBalanceAfter = findViewById(R.id.balanceAfter);
        tDateTime = findViewById(R.id.transDateTime);
        bBack = findViewById(R.id.btnBack);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        user = auth.getCurrentUser();
        amount = getIntent().getExtras().getString("transactionAmount");
        status = getIntent().getExtras().getString("status");
        tid = getIntent().getExtras().getString("id");
        to = getIntent().getExtras().getString("to");
        desc = getIntent().getExtras().getString("desc");
        transaction = database.getReference("Transaction");
        displayTransaction();


        bBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTransaction();



            }
        });
    }

    private void saveTransaction() {
        final DatabaseReference transRef = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid()).child("Transaction");

        transaction.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                transaction.child(tid).setValue(trans);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        transRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                transRef.child(tid).setValue(trans);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        transRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                transRef.child(tid).setValue(trans);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        finish();


    }

    private void displayTransaction() {

        tStatus.setText(status);
        tID.setText(tid);
        tAmount.setText("RM " + amount + ".00");
        tTo.setText(to);
        tDesc.setText(desc);
        tDateTime.setText(fd);

        final Query lastTransaction = transaction.orderByKey().limitToLast(1);


        final int amount1 = Integer.parseInt(amount);

        lastTransaction.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot ds : dataSnapshot.getChildren()) {
                        Transaction transaction1 = ds.getValue(Transaction.class);
                        if (desc.equals("Topup")) {
                            assert transaction1 != null;
                            final int balance2 = Integer.parseInt(transaction1.getBalance());
                            tBalanceBefore.setText("RM " + balance2 + ".00");
                            int balance1 = balance2;
                            balance1 += amount1;
                            String newBalance = String.valueOf(balance1);
                            tBalanceAfter.setText("RM " + newBalance + ".00");
                            trans = new Transaction(tid, to, desc, tDateTime.getText().toString(), newBalance, amount, status);
                        }
                        if(desc.equals("Withdraw")){
                            assert transaction1 != null;
                            final int balance2 = Integer.parseInt(transaction1.getBalance());
                            tBalanceBefore.setText("RM " + balance2 + ".00");
                            int balance1 = balance2;
                            balance1 -= amount1;
                            String newBalance = String.valueOf(balance1);
                            tBalanceAfter.setText("RM " + newBalance + ".00");
                            trans = new Transaction(tid, to, desc, tDateTime.getText().toString(), newBalance, amount, status);
                        }
                    }


                }else{
                    tBalanceBefore.setText("RM0.00");
                    int balance = 0;
                    balance += amount1;
                    newBalance1 = String.valueOf(balance);
                    tBalanceAfter.setText("RM " +newBalance1 + ".00");
                    trans = new Transaction(tid,to,desc,tDateTime.getText().toString(),newBalance1,amount,status);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
