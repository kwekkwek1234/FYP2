package my.edu.tarc.user.fyp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class SetAddressActivity extends AppCompatActivity implements View.OnClickListener {
    private Spinner spinnerState;
    private EditText textReceiverName, textPhoneNumber, textAddress, textPostalCode,textArea;
    private CheckBox cbDefaultAddress;
    private Button btnSubmit;
    FirebaseDatabase database;
    DatabaseReference users;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    public SetAddressActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_address);

        textReceiverName = findViewById(R.id.textReceiverName);
        textPhoneNumber = findViewById(R.id.textPhoneNumber);
        textAddress = findViewById(R.id.textAddress);
        textPostalCode = findViewById(R.id.textPostalCode);
        textArea = findViewById(R.id.textArea);
        btnSubmit = findViewById(R.id.btnSubmit);
        spinnerState = findViewById(R.id.spinnerState);
        cbDefaultAddress = findViewById(R.id.checkSave);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(SetAddressActivity.this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.stateItem));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerState.setAdapter(adapter);

        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        users = database.getReference("Users");

        btnSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == btnSubmit){
            AddAddress();
            finish();
        }
    }

    private void AddAddress() {
        String receiverName = textReceiverName.getText().toString().trim();
        String phoneNumber = textPhoneNumber.getText().toString().trim();
        final String address = textAddress.getText().toString().trim();
        String postalCode = textPostalCode.getText().toString().trim();
        String area = textArea.getText().toString().trim();
        String state = spinnerState.getSelectedItem().toString();
        String defaultAddress = null;

        if(cbDefaultAddress.isChecked()){
            defaultAddress = "Yes";
        }

        if(TextUtils.isEmpty(receiverName)){
            Toast.makeText(this,"Please enter receiver name",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(phoneNumber)){
            Toast.makeText(this,"Please enter your phone number",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(address)){
            Toast.makeText(this,"Please enter your address",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(postalCode)){
            Toast.makeText(this,"Please enter postal code",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(area)){
            Toast.makeText(this,"Please enter area",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(state)){
            Toast.makeText(this,"Please select a state",Toast.LENGTH_LONG).show();
            return;
        }

        final Address address1 = new Address(receiverName,phoneNumber,address,postalCode,area,state,defaultAddress);
        firebaseUser = firebaseAuth.getCurrentUser();
        final DatabaseReference addressRef = FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseUser.getUid()).child("Address");
        Query lastAddress = addressRef.orderByKey().limitToLast(1);
        final String[] lastAddress1 = new String[10];
        lastAddress.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    lastAddress1[0] = dataSnapshot.getChildren().iterator().next().getKey();
                    addressRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            int tempDigit2 = Integer.parseInt(lastAddress1[0]);
                            tempDigit2 += 1;
                            String nextNum  = String.valueOf(tempDigit2);
                            addressRef.child(nextNum).setValue(address1);
                            Toast.makeText(getApplicationContext(),"Address added.",Toast.LENGTH_LONG).show();
                            finish();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }else{
                    lastAddress1[0] = "0";
                    addressRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            int tempDigit = Integer.parseInt(lastAddress1[0]);
                            tempDigit += 1;
                            String nextNo = String.valueOf(tempDigit);
                            addressRef.child(nextNo).setValue(address1);
                            Toast.makeText(getApplicationContext(),"Address added.",Toast.LENGTH_LONG).show();
                            finish();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
