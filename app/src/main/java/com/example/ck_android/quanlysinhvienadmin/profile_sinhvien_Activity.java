//package com.example.ck_android.quanlysinhvienadmin;
//import android.os.Bundle;
//import android.widget.ImageView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.example.ck_android.R;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//import com.squareup.picasso.Picasso;
//
//public class profile_sinhvien_Activity extends AppCompatActivity {
//
//    // Initializing the ImageView
//    ImageView rImage;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.profile_activity);
//
//        // getting ImageView by its id
//        rImage = findViewById(R.id.user_imageview);
//
//        // we will get the default FirebaseDatabase instance
//        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
//
//        // we will get a DatabaseReference for the database root node
//        DatabaseReference databaseReference = firebaseDatabase.getReference("DbSinhVien");
//
//        // Here "image" is the child node value we are getting
//        // child node data in the getImage variable
//        DatabaseReference getImage = databaseReference.child("Id1").child("Image");
//
//        // Adding listener for a single change
//        // in the data at this location.
//        // this listener will triggered once
//        // with the value of the data at the location
//        getImage.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                // getting a DataSnapshot for the location at the specified
//                // relative path and getting in the link variable
//                String link = dataSnapshot.getValue(String.class);
//
//                // loading that data into rImage
//                // variable which is ImageView
//                Picasso.get().load(link).into(rImage);
//            }
//
//            // this will called when any problem
//            // occurs in getting data
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                // we are showing that error message in toast
//                Toast.makeText(profile_sinhvien_Activity.this, "Error Loading Image", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//}
package com.example.ck_android.quanlysinhvienadmin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ck_android.R;
import com.example.ck_android.model.SinhVien;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class profile_sinhvien_Activity extends AppCompatActivity {
    private TextView tvten,tvmail,tvphone;
    private ImageView rImage;
    String TAG="FIREBASE";
    private FirebaseDatabase database;
    private String hoten;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);
        tvten = findViewById(R.id.tv_name);
        tvmail = findViewById(R.id.tv_mail);
        tvphone = findViewById(R.id.tv_phone);
        rImage = findViewById(R.id.user_imageview);
//        //receive data from login screen
        Intent intent = getIntent();
        hoten = intent.getStringExtra("key");

        database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("DbSinhVien").child(hoten);

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            String fname, mail, phone;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                SinhVien sv= dataSnapshot.getValue(SinhVien.class);
                tvten.setText(""+sv.getHoTen());
                tvmail.setText(""+sv.getEmail());
                tvphone.setText(""+sv.getSoDienThoai());
                Picasso.get().load(sv.getImage()).into(rImage);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
}
