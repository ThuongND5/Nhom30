//package com.example.ck_android.quanlysinhvienadmin;
//
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.database.Cursor;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.Matrix;
//import android.net.Uri;
//import android.provider.MediaStore;
//import android.os.Bundle;
//import android.util.Base64;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.example.ck_android.R;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//
//public class attendanceActivity extends AppCompatActivity {
//    ImageButton btnCapture;
//    ImageButton btnChoose;
//    ImageView imgPicture;
//    Bitmap selectedBitmap;
//    EditText ngaydd,idsv,Lop;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.diemdanhsv_activity);
//        addControls();
//        addEvents();
//    }
//    public void addControls()
//    {
//        btnCapture = findViewById(R.id.btnCapture);
//        btnChoose= findViewById(R.id.btnChoose);
//        imgPicture=findViewById(R.id.imgPicture);
//        ngaydd=findViewById(R.id.edtngay);
//        idsv=findViewById(R.id.edtid);
//        Lop=findViewById(R.id.edtlop);
//    }
//    public void addEvents() {
//        btnCapture.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                capturePicture();
//            }
//        });
//        btnChoose.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                choosePicture();
//            }
//        });
//    }
//    //xử lý chọn hình
//    private void choosePicture() {
//        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
//                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        startActivityForResult(pickPhoto , 200);//one can be replaced with any action code
//    }
//    //xử lý chụp hình
//    private void capturePicture() {
//        Intent cInt = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(cInt,100);
//    }
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 100 && resultCode == RESULT_OK) {
//        //xử lý lấy ảnh trực tiếp lúc chụp hình:
//            selectedBitmap = (Bitmap) data.getExtras().get("data");
//            imgPicture.setImageBitmap(selectedBitmap);
//        } else if (requestCode == 200 && resultCode == RESULT_OK) {
//            try {
//        //xử lý lấy ảnh chọn từ điện thoại:
//                Uri imageUri = data.getData();
//                selectedBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
//                imgPicture.setImageBitmap(selectedBitmap);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//    public void xuLyThemMoi(View view) {
//        try {
//            FirebaseDatabase database = FirebaseDatabase.getInstance();
//            //Kết nối tới node có tên là contacts (node này do ta định nghĩa trong CSDL Firebase)
//            DatabaseReference myRef = database.getReference("attendance");
//            String contactId=ngaydd.getText().toString();
//            String id = idsv.getText().toString();
//            String lop = Lop.getText().toString();
//            myRef.child(contactId).child("Id").setValue(id);
//            myRef.child(contactId).child("class").setValue(lop);
//
//            //đưa bitmap về base64string:
//            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//            selectedBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
//            byte[] byteArray = byteArrayOutputStream .toByteArray();
//            String imgeEncoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
//            myRef.child(contactId).child("picture").setValue(imgeEncoded);
//
//            finish();
//        }
//        catch (Exception ex)
//        {
//            Toast.makeText(this,"Error:"+ex.toString(),Toast.LENGTH_LONG).show();
//        }
//    }
//}