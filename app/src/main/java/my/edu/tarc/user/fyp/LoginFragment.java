package my.edu.tarc.user.fyp;

import android.content.Intent;
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
import android.app.ProgressDialog;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginFragment extends Fragment {
    private TextView textViewRegister,textViewSignStaff;
    private EditText editEmail,editPassword;
    private Button buttonLogin;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;


    private OnFragmentInteractionListener mListener;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        textViewRegister = (TextView) view.findViewById(R.id.textViewSignup);
        textViewSignStaff = (TextView) view.findViewById(R.id.textViewSignStaff);
        editEmail = (EditText) view.findViewById(R.id.editTextEmail);
        editPassword = (EditText) view.findViewById(R.id.editTextPassword);
        buttonLogin = (Button) view.findViewById(R.id.buttonSignIn);
        progressDialog = new ProgressDialog(getActivity());
        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() != null){
            //profile activity
        }

        textViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             startActivity(new Intent(getActivity(),RegisterActivity.class));
            }
        });

        textViewSignStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    private void userLogin() {
        String email = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(getActivity(),"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(getActivity(),"Please enter password",Toast.LENGTH_LONG).show();
        }

        progressDialog.setMessage("Login...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(),"Successful",Toast.LENGTH_LONG).show();
                if(task.isSuccessful()){
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    ProfileFragment profileFragment = new ProfileFragment();
                    fragmentTransaction.replace(R.id.fragment_container,profileFragment);
                    fragmentTransaction.commit();

                }else{
                    Toast.makeText(getActivity(),"error",Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
