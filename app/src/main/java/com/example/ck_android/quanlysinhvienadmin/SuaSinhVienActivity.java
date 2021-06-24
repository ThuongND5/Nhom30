package com.example.ck_android.quanlysinhvienadmin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ck_android.R;
import com.example.ck_android.model.SinhVien;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SuaSinhVienActivity extends AppCompatActivity {

    private EditText edtMssv, edtTen, edtEmail, edtSdt;
    private Button btnCapNhat, btnHuy, btnTroVe;
    private SinhVien sinhVien;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_sinh_vien);
        addControls();
        addEvents();
    }

    private void addControls() {
        edtMssv = findViewById(R.id.edtMssv);
        edtTen = findViewById(R.id.edtHoTen);
        edtEmail = findViewById(R.id.edtEmail);
        edtSdt = findViewById(R.id.edtSoDienThoai);

        btnHuy = findViewById(R.id.btnHuy);
        btnCapNhat = findViewById(R.id.btnCapNhat);
        btnTroVe = findViewById(R.id.btnTroVe);

        //lấy gói tin vừa đc gửi từ màn hình ngoài.
        Intent intent = getIntent();
        //truyền khóa vừa nãy vào
        sinhVien = (SinhVien) intent.getSerializableExtra("SINHVIEN");
        if(sinhVien!= null){
            //đưa thông tin sinh viên lên editText
            edtMssv.setText(sinhVien.getMssv()+"");
            edtTen.setText(sinhVien.getHoTen());
            edtSdt.setText(sinhVien.getSoDienThoai()+"");
            edtEmail.setText(sinhVien.getEmail());
        }
        else{
            Toast.makeText(this,"Lỗi khi load dữ liệu sinh viên",Toast.LENGTH_LONG).show();
        }
    }

    private void addEvents() {
        btnTroVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ví dụ khi người dùng sửa thông tin, nhưng muốn trở lại như cũ thì nhấn nút hủy, dữ liệu cũ sẽ hiển thị trở lại
                if(sinhVien!= null){
                    //đưa thông tin sinh viên lên editText
                    edtMssv.setText(sinhVien.getMssv()+"");
                    edtTen.setText(sinhVien.getHoTen());
                    edtSdt.setText(sinhVien.getSoDienThoai()+"");
                    edtEmail.setText(sinhVien.getEmail());
                }
                else{
                    Toast.makeText(getApplicationContext(),"Lỗi khi load dữ liệu sinh viên",Toast.LENGTH_LONG).show();
                }
            }
        });

        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Cập nhật: lấy toàn bộ thông tin sv trên edt và update lên firebase database
                String mssv = edtMssv.getText().toString();
                String hoten = edtTen.getText().toString();
                String email = edtEmail.getText().toString();
                String sdt = edtSdt.getText().toString();
                String id = sinhVien.getId();
                //ở đây mình cần id để firebase tìm trên csdl, nếu trúng id của sinh viên thì sẽ cập nhật thông tin cho sv đó.
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("DbSinhVien");
                myRef.child(id).child("HoTen").setValue(hoten);
                myRef.child(id).child("Mssv").setValue(mssv);
                myRef.child(id).child("Email").setValue(email);
                myRef.child(id).child("SoDienThoai").setValue(sdt);
                finish(); //đóng màn hình sửa
                Toast.makeText(getApplicationContext(),"Sửa thành công",Toast.LENGTH_LONG).show();
            }
        });
    }
}
