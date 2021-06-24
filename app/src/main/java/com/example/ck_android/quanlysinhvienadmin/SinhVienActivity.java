package com.example.ck_android.quanlysinhvienadmin;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ck_android.R;

public class SinhVienActivity extends AppCompatActivity {

    private TextView tvname;
    private WebView webView;
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sinhvien_activity);
        tvname = findViewById(R.id.tv_hienten);
        name= getIntent().getStringExtra("Id");
        tvname.setText("chao "+name);
        webView = findViewById(R.id.webView);
        webView.loadUrl("http://sv.dut.udn.vn/");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sinhvien_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.dangxuat)
        {
            Intent intent=new Intent(SinhVienActivity.this,login_sinhvien.class);
            startActivity(intent);
        }if(item.getItemId()==R.id.feedback)
        {
            Intent intent=new Intent(SinhVienActivity.this,attendance_recognition.class);
            intent.putExtra("key",name);
            startActivity(intent);
        }if(item.getItemId()==R.id.mnu_profile)
        {
            Intent intent=new Intent(SinhVienActivity.this,profile_sinhvien_Activity.class);
            intent.putExtra("key",name);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}

