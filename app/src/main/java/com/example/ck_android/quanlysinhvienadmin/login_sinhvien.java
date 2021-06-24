package com.example.ck_android.quanlysinhvienadmin;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ck_android.R;
import com.example.ck_android.model.GiaoVien;
import com.example.ck_android.model.SinhVien;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class login_sinhvien extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText username, password;
    Button btnSignIn;

    FirebaseAuth firebaseAuth;
    private SinhVien sinhVien;
    private GiaoVien giaoVien;
    DatabaseReference user;
    FirebaseDatabase firebaseDatabase;

    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_sinhvien);

        username = findViewById(R.id.usernamesv);
        password = findViewById(R.id.passwordsv);
        btnSignIn = findViewById(R.id.btn_loginsv);
        // drop downlist
        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        List<String> categories = new ArrayList<String>();
        categories.add("Giáo Viên");
        categories.add("Sinh Viên");
        categories.add("Admin");
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
               if(String.valueOf(spinner.getSelectedItem())=="Sinh Viên"){
                   SignIn(user,pass);
               }if(String.valueOf(spinner.getSelectedItem())=="Giáo Viên"){
                    SignInGV(user,pass);
                }
            }
        });
    }

    private void SignInGV(final String username,final String password) {
        user = firebaseDatabase.getReference("Teacher");
        user.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child(username).exists()){
                    if(!username.isEmpty()){
                        GiaoVien login= snapshot.child(username).getValue(GiaoVien.class);
                        if(login.getTname().equals(password)){
                            Toast.makeText(login_sinhvien.this, "Success Login", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(login_sinhvien.this, MainActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(login_sinhvien.this, "Password Wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(login_sinhvien.this, "Username is not Registered", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void SignIn(final String username,final String password) {
        user = firebaseDatabase.getReference("DbSinhVien");
        user.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(username.isEmpty()){
                    Toast.makeText(login_sinhvien.this, "Please fill User name", Toast.LENGTH_SHORT).show();
                }
                if(username.isEmpty()) {
                    Toast.makeText(login_sinhvien.this, "Please fill pass word", Toast.LENGTH_SHORT).show();
                }
                if(snapshot.child(username).exists()){
                    if(!username.isEmpty()){
                        SinhVien login= snapshot.child(username).getValue(SinhVien.class);
                        if(login.getHoTen().equals(password)){
                            Toast.makeText(login_sinhvien.this, "Success Login", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(login_sinhvien.this, SinhVienActivity.class);
                            intent.putExtra("Id",username);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(login_sinhvien.this, "Password Wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(login_sinhvien.this, "Username is not Registered", Toast.LENGTH_SHORT).show();
                        //https://docs.google.com/open?id=0B2Nu5U2Cz81qZExGQ25sWVdRd21IOExUUTZsZzFoZw
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
