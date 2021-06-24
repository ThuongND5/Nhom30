package com.example.ck_android.quanlysinhvienadmin;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ck_android.R;
import com.example.ck_android.adapter.GiaoVienAdapter;
import com.example.ck_android.model.GiaoVien;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Admin_Activity extends AppCompatActivity {

    private ListView lvGiaoVien;
    private ArrayList<GiaoVien> giaoVienArrayList;
    private GiaoVienAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvGiaoVien = findViewById(R.id.lvSinhVien);

        //khởi tạo danh sách sinh viên mẫu
        giaoVienArrayList = new ArrayList<>();
        GetData();
        //tạo Custom Adapter để gán cho listview.
        //Đối số 1: màn hình hiện tại (this) | Đối số 2: là view hiển thị cho từng sinh viên | Đối số 3: danh sách sinh viên ( dữ liệu để truyền vào listview)
        adapter = new GiaoVienAdapter(this, R.layout.custom_listview_item, giaoVienArrayList);

        //set Adapter cho listview
        lvGiaoVien.setAdapter(adapter);
    }

    // Lấy danh sách sinh viên từ Firebase Database
    private void GetData() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Teacher");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //xóa dữ liệu trên listview và cập nhật lại
                adapter.clear();

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    //convert data qua SinhVien
                    GiaoVien sinhVien = data.getValue(GiaoVien.class);
                    //thêm sinh viên vào listview
                    sinhVien.setTid(data.getKey());
                    adapter.add(sinhVien);
                    Log.d("MYTAG", "onDataChange: " + sinhVien.getTname());
                }
                Toast.makeText(getApplicationContext(), "Load Data Success", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Load Data Failed" + databaseError.toString(), Toast.LENGTH_LONG).show();
                Log.d("MYTAG", "onCancelled: " + databaseError.toString());
            }
        });
    }
}
