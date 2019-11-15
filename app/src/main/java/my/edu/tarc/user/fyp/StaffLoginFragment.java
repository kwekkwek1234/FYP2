package my.edu.tarc.user.fyp;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StaffLoginFragment extends Fragment implements View.OnClickListener {

    private TextView textCustLogin;
    private EditText editId,editPassword;
    private Button buttonLogin;
    private ProgressDialog progressDialog;
    FirebaseDatabase database;
    DatabaseReference ref;

    public StaffLoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_staff_login, container, false);
        textCustLogin = v.findViewById(R.id.textCusLogin);
        editId = v.findViewById(R.id.staffId);
        editPassword = v.findViewById(R.id.editTextPassword);
        buttonLogin = v.findViewById(R.id.buttonSignIn);
        progressDialog = new ProgressDialog(getActivity());
        database = FirebaseDatabase.getInstance();


        textCustLogin.setOnClickListener(this);
        buttonLogin.setOnClickListener(this);

        return v;
    }


    @Override
    public void onClick(View v) {
        if(v == buttonLogin){
            staffLogin();
        }
        if(v == textCustLogin){
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            LoginFragment loginFragment = new LoginFragment();
            fragmentTransaction.replace(R.id.fragment_container,loginFragment);
        }
    }

    private void staffLogin() {
        final String id = editId.getText().toString().trim();
        final String password = editPassword.getText().toString().trim();

        if (TextUtils.isEmpty(id)) {
            Toast.makeText(getActivity(), "Please enter staff ID", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getActivity(), "Please enter password", Toast.LENGTH_LONG).show();
        }

        progressDialog.setMessage("Login...");
        progressDialog.show();

        ref = database.getReference("Staff").child(id).child("Details");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User u = dataSnapshot.getValue(User.class);
                String sid = u.getStaffId();
                String pass = u.getPassword();
                if(id.equals(sid)){
                    if(password.equals(pass)){
                       progressDialog.dismiss();
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        Bundle bundle = new Bundle();
                        bundle.putString("user","Staff");
                        bundle.putString("staffId",sid);
                        ProfileFragment profileFragment = new ProfileFragment();
                        profileFragment.setArguments(bundle);
                        fragmentTransaction.replace(R.id.fragment_container,profileFragment);
                        fragmentTransaction.commit();

                    }else{
                        Toast.makeText(getActivity(),"Wrong password.Please try again",Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(getActivity(),"Invalid staff. Please contact admin staff",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
