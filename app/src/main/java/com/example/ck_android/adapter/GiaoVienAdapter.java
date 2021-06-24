package com.example.ck_android.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.example.ck_android.R;
import com.example.ck_android.model.GiaoVien;
import com.example.ck_android.quanlysinhvienadmin.MainActivity;
import com.example.ck_android.quanlysinhvienadmin.SuaSinhVienActivity;
import com.example.ck_android.quanlysinhvienadmin.ThemSvActivity;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

public class GiaoVienAdapter extends ArrayAdapter<GiaoVien> {

    @NonNull
    private Activity activity;
    private int resource;
    @NonNull
    private List<GiaoVien> objects;

    public GiaoVienAdapter(@NonNull Activity activity, int resource, @NonNull List<GiaoVien> objects) {
        super(activity, resource, objects);
        this.activity = activity;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position,@Nullable View convertView,@NonNull ViewGroup parent) {
        //Cứ mỗi một đối tượng sinh viên, sẽ trả về một View,với 1 view sẽ tương ứng với đối tượng sinh viên, và nó lưu lại Position (vị trí) của sinh viên đó.
        LayoutInflater inflater = this.activity.getLayoutInflater();
        View view = inflater.inflate(this.resource,null);

        //khai báo 2 textView
        TextView txtHoTen = view.findViewById(R.id.txtHoTen);
        TextView txtEmail = view.findViewById(R.id.txtEmail);

        //Lấy đối tượng sinh viên và đưa tên, email lên textView;
        // lấy được sinh viên nhờ vào position. Object là danh sách sinh viên mà ta đã truyền vào.
        final GiaoVien giaoVien = this.objects.get(position);
        txtHoTen.setText(giaoVien.getTname());
        txtEmail.setText(giaoVien.getSubject());

        ImageView btnMenu = view.findViewById(R.id.btnMenu);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(activity,view);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if(menuItem.getItemId() == R.id.item_them_sv){
                            //Thêm sinh viên vào Firebase Database
                            //Khi nhấn nút thêm, sẽ mở ra một màn hình khác có form để nhập thông tin sinh viên và thêm. Bây giờ sẽ tạo một màn hình mới.
                            //Đối số 1: màn hình hiện tại :activity. Đối số 2: màn hình mà khi mình nhấn vào nút Thêm sẽ hiển thị lên là màn hình mới vừa tạo: ThemSvActivity
                            Intent intent = new Intent(activity,ThemSvActivity.class);
                            //mở màn hình thêm lên
                            activity.startActivity(intent);
                        }
                        else if(menuItem.getItemId() == R.id.item_sua_sv){
                            //khi nhấn nút sửa, mở ra màn hình sửa kèm theo đó là thông tin của sinh viên sẽ hiển thị lên edittext
                            Intent intent = new Intent(activity,SuaSinhVienActivity.class);
                            //gửi sinh viên từ đây qua màn hình Sửa
                            // ở đây đối số 1: là khóa, dùng để nhận dạng gói tin, gửi qa bên màn hình sửa, để lấy đúng chính xác sinh viên thì cần dùng khóa này.
                            //đối số 2: đối tượng sinhvien, ở đây đối tượng sinh viên cần Implement Serializable để nó có thể truyền từ màn hình này tới màn hình khác
                            intent.putExtra("SINHVIEN", giaoVien);
                            //mở màn hình
                            activity.startActivity(intent);
                        }
                        else if(menuItem.getItemId() == R.id.item_xoa_sv){
                            //khi nhấn nút xóa, tìm sinh viên đó trên firebase và xóa.
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference myRef = database.getReference("DbSinhVien");
                            myRef.child(giaoVien.getTid()).removeValue(new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                                    //thành công
                                    Toast.makeText(activity,"Xóa thành công",Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                        return false;
                    }
                });
                //truyền popup_menu.xml vào để nó show lên
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu,popupMenu.getMenu());

                //show Icon
                try{
                    Field field = popupMenu.getClass().getDeclaredField("mPopup");
                    field.setAccessible(true);
                    Object popUpMenuHelper = field.get(popupMenu);
                    Class<?> cls = Class.forName("com.android.internal.view.menu.MenuPopupHelper");
                    Method method = cls.getDeclaredMethod("setForceShowIcon",new Class[] {boolean.class});
                    method.setAccessible(true);
                    method.invoke(popUpMenuHelper,new Object[]{true});
                } catch (Exception e){
                    Log.d("MYTAG", "onClick: " + e.toString());
                }
                popupMenu.show();
            }
        });
        return view;
    }
}
