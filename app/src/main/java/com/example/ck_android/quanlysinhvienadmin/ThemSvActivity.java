package com.example.ck_android.quanlysinhvienadmin;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ck_android.R;
import com.example.ck_android.model.SinhVien;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class ThemSvActivity extends AppCompatActivity {

    private EditText edtMssv, edtTen, edtEmail, edtSdt;
    private Button btnThem, btnHuy, btnTroVe;
    private ImageView chonanh;
    private ProgressBar progressBar;
    private Uri imageUri;
    private StorageReference reference = FirebaseStorage.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_sv);
        addControls(); //khai báo các control trên giao diện
        addEvents(); //khai báo các sự kiện vd: btnThem click, btnHuy click..
    }

    private void addEvents() {
        btnTroVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); //trở về lại màn hình trước đó
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //hủy thì sẽ xóa hết các text trên edt
                edtMssv.setText("");
                edtEmail.setText("");
                edtSdt.setText("");
                edtTen.setText("");
            }
        });

        chonanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,2);
            }
        });

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(imageUri!=null){
                    uploadToFirebase(imageUri);
                }
                else {
                    Toast.makeText(ThemSvActivity.this,"Please Select Image",Toast.LENGTH_SHORT).show();
                }
//                //sự kiện thêm. Thêm sinh viên vào firebase database
//                //lấy mssv, ten, email, sdt trên view.
//                String mssv = edtMssv.getText().toString();
//                String ten = edtTen.getText().toString();
//                String email = edtEmail.getText().toString();
//                String sdt = edtSdt.getText().toString();
//                String image = "mca";
//                //ở đây không nhập ID vì Firebase database nó tự sinh cho mình một cái Id
//                SinhVien sinhVien = new SinhVien(mssv, ten, email, sdt,image);
//
//                FirebaseDatabase database = FirebaseDatabase.getInstance();
//                DatabaseReference myRef = database.getReference("DbSinhVien");
//                //tạo một Id ngẫu nhiên trên firebase database/DbSinhVien/
//                String id = myRef.push().getKey();
//                //dựa vào Id này, mình sẽ thêm dữ liệu sinh viên vào
//                myRef.child(id).setValue(sinhVien).addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        //nếu thêm thành công sẽ nhảy vào đây
//                        Toast.makeText(getApplicationContext(),"Thêm thành công!",Toast.LENGTH_LONG).show();
//                        finish(); //thoát màn hình thêm, trở về màn hình danh sách sv
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        //nếu thêm thất bại sẽ nhảy vào đây
//                        Toast.makeText(getApplicationContext(),"Thêm thất bại! " + e.toString(),Toast.LENGTH_LONG).show();
//                    }
//                });
//
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==2 && resultCode == RESULT_OK && data!=null){
            imageUri = data.getData();
            chonanh.setImageURI(imageUri);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

        private void uploadToFirebase(Uri uri){
        final StorageReference fileRef = reference.child(System.currentTimeMillis() + "." + getFileExtension(uri));
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String mssv = edtMssv.getText().toString();
                        String ten = edtTen.getText().toString();
                        String email = edtEmail.getText().toString();
                        String sdt = edtSdt.getText().toString();
                        //ở đây không nhập ID vì Firebase database nó tự sinh cho mình một cái Id
                        SinhVien sinhVien = new SinhVien(mssv, ten, email, sdt,uri.toString());

                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("DbSinhVien");
                        //tạo một Id ngẫu nhiên trên firebase database/DbSinhVien/
                        String id = myRef.push().getKey();
                        myRef.child(id).setValue(sinhVien);
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(ThemSvActivity.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                        //imageView.setImageResource(R.drawable.ic_baseline_add_photo_alternate_24);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(ThemSvActivity.this, "Uploading Failed !!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getFileExtension(Uri mUri){

        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));

    }

    private void addControls() {
        edtMssv = findViewById(R.id.edtMssv);
        edtTen = findViewById(R.id.edtHoTen);
        edtEmail = findViewById(R.id.edtEmail);
        edtSdt = findViewById(R.id.edtSoDienThoai);

        btnHuy = findViewById(R.id.btnHuy);
        btnThem = findViewById(R.id.btnThem);
        btnTroVe = findViewById(R.id.btnTroVe);

        chonanh =findViewById(R.id.chonanh);
    }
}