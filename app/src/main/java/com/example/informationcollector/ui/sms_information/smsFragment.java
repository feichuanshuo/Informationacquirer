package com.example.informationcollector.ui.sms_information;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.informationcollector.databinding.FragmentSmsInformationBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class smsFragment extends Fragment {
    private FragmentSmsInformationBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                              ViewGroup container, Bundle savedInstanceState){
        binding = FragmentSmsInformationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            // 如果没有获取短信权限，则请求权限
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_SMS}, 1);
        } else {
            // 如果已经获取短信权限，则开始读取短信
            readSMS();
        }



        return root;
    }

    private void readSMS(){
        // 将日期时间格式转换为SimpleDateFormat对象
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");

        List<String> smsList = new ArrayList<>();
        Uri uri = Uri.parse("content://sms/inbox");
        Cursor cursor = getContext().getContentResolver().query(uri, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String address = "";
                String body = "";
                String date = "";

                int addressIndex = cursor.getColumnIndex("address");
                int bodyIndex = cursor.getColumnIndex("body");
                int dateIndex = cursor.getColumnIndex("date");

                if (addressIndex >=0 ){
                    address = cursor.getString(addressIndex);
                }
                if (bodyIndex >=0 ){
                    body = cursor.getString(bodyIndex);
                }
                if (dateIndex >=0 ){

                    date = sdf.format(new Date(cursor.getLong(dateIndex)));
                }

                smsList.add(
                        "联系人：" + address + "\n" +
                        "内容：\n" + body + "\n" +
                        "时间：" + date);


            } while (cursor.moveToNext());
        }

        // 创建ArrayAdapter适配器
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_list_item_1, smsList);
        // 获取binding实例中的ListView
        final ListView contactsListView = binding.smsList;
        contactsListView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
