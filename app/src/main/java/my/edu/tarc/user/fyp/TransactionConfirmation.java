package my.edu.tarc.user.fyp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

public class TransactionConfirmation extends AppCompatActivity {
    private TextView transID, transTo, transDesc,transAmount;
    private Button btnSubmit,btnCancel;
    FirebaseDatabase database;
    DatabaseReference transaction;
    FirebaseAuth auth;
    FirebaseUser user;
    String amount ,activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_confirmation);

        transID = findViewById(R.id.transactionID);
        transTo = findViewById(R.id.To);
        transDesc = findViewById(R.id.transDesc);
        transAmount = findViewById(R.id.transAmount);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnCancel = findViewById(R.id.btnCancel);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        user = auth.getCurrentUser();
        amount = getIntent().getStringExtra("amount");
        activity = getIntent().getExtras().getString("activity");
        transaction = database.getReference("Transaction");
        displayTransactionConfirmation();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v == btnSubmit){
                    String id = transID.getText().toString().trim();
                    String to = transTo.getText().toString().trim();
                    String desc = transDesc.getText().toString().trim();
                    String status = "Successful";

                    Intent intent = new Intent(getApplicationContext(),TransactionDoneActivity.class);
                    intent.putExtra("id",id);
                    intent.putExtra("to",to);
                    intent.putExtra("transactionAmount",amount);
                    intent.putExtra("desc",desc);
                    intent.putExtra("status",status);
                    startActivity(intent);
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = transID.getText().toString().trim();
                String to = transTo.getText().toString().trim();
                String desc = transDesc.getText().toString().trim();
                String status = "Failed";

                Intent intent = new Intent(getApplicationContext(),TransactionDoneActivity.class);
                intent.putExtra("id",id);
                intent.putExtra("to",to);
                intent.putExtra("amount",amount);
                intent.putExtra("desc",desc);
                intent.putExtra("status",status);
                startActivity(intent);
            }
        });
    }

    private void displayTransactionConfirmation() {
        generateTransactionID();
        transAmount.setText("RM " +amount+ ".00");

        if(activity.equals("Topup")){
            transDesc.setText(activity);
            transTo.setText("Smart Dresser");
        }
        if(activity.equals("Withdraw")){
            transDesc.setText(activity);
            transTo.setText("UserName");
        }
        if(activity.equals("Payment")){
            transDesc.setText(activity + "OrderID");
            transTo.setText("Smart Dresser");
        }

    }

    private void generateTransactionID() {
        final Query  transactionQuery = transaction.orderByKey().limitToLast(1);
        final String[] lastTransaction = new String[1];
        final Date date = Calendar.getInstance().getTime();
        final SimpleDateFormat df = new SimpleDateFormat("yyMMdd");
        final String fd =  df.format(date);
        transactionQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    lastTransaction[0] = dataSnapshot.getChildren().iterator().next().getKey();
                    String datePart = lastTransaction[0].substring(0, 6);
                    String indexPart = lastTransaction[0].substring(6, 12);

                    if (datePart.equals(fd)) {
                        int tempIndex = Integer.parseInt(indexPart);
                        tempIndex += 1;
                        if (tempIndex > 0 && tempIndex < 10) {
                            transID.setText(fd + "00000" + tempIndex);
                        }
                        if (tempIndex > 9 && tempIndex < 100) {
                            transID.setText(fd + "0000" + tempIndex);
                        }
                        if (tempIndex > 99 && tempIndex < 1000) {
                            transID.setText(fd + "000" + tempIndex);
                        }
                        if (tempIndex > 999 && tempIndex < 10000) {
                            transID.setText(fd + "00" + tempIndex);
                        }
                        if (tempIndex > 9999 && tempIndex < 100000) {
                            transID.setText(fd + "00" + tempIndex);
                        }
                        if (tempIndex > 99999 && tempIndex < 1000000) {
                            transID.setText(fd + "0" + tempIndex);
                        }
                        if (tempIndex > 9999 && tempIndex < 100000) {
                            transID.setText(fd + tempIndex);
                        }
                    }
                    //the next day transaction begin
                    if (!datePart.equals(fd)) {
                        datePart = fd;
                        int index = 0;
                        index += 1;
                        transID.setText(fd + "00000" + index);

                    }

                } else {
                    int index = 0;
                    index += 1;
                    transID.setText(fd + "00000" + index);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }
}
