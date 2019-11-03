package my.edu.tarc.user.fyp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class TopupActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText textCardNum,textExpiryDate,textCvv,editMinAmount;
    private Button btnSubmit;
    private CheckBox save;
    private DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topup);
        textCardNum = (EditText) findViewById(R.id.editCardNum);
        textExpiryDate = (EditText) findViewById(R.id.editExpiryDate);
        textCvv = (EditText) findViewById(R.id.editCVV);
        editMinAmount = (EditText) findViewById(R.id.editMinAmount);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        save = (CheckBox) findViewById(R.id.checkSave);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");

        btnSubmit.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v == btnSubmit){
            if(save.isChecked()){
                saveCardDetails();
            }

            String amount = editMinAmount.getText().toString().trim();
            String activity = "Topup";

            Intent intent = new Intent(this,TransactionConfirmation.class);
            intent.putExtra("amount",amount);
            intent.putExtra("activity",activity);
            startActivity(intent);
        }
    }

    private void saveCardDetails() {
        String cardNumber = textCardNum.getText().toString().trim();
        String expiryDate = textExpiryDate.getText().toString().trim();
        String cvv =  textCvv.getText().toString().trim();

        CardDetails cardDetails = new CardDetails(cardNumber,expiryDate,cvv);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference.child(user.getUid().toString()).child("Card Information").setValue(cardDetails);
        Toast.makeText(this,"Card Information saved.",Toast.LENGTH_LONG).show();
    }
}
