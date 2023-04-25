package com.example.informationcollector.ui.contacts_information;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.informationcollector.databinding.FragmentContactsInformationBinding;

import java.util.ArrayList;
import java.util.List;

public class CTIFragment extends Fragment {
    private FragmentContactsInformationBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState){
        binding = FragmentContactsInformationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            // 如果没有权限，则动态请求权限
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.READ_CONTACTS},
                    1);
        } else {
            // 如果已经有权限，则直接获取通讯录
            readContacts();
        }

        return root;
    }

    // 获取通讯录
    private void readContacts() {
        ContentResolver contentResolver = getContext().getContentResolver();
        Cursor cursor = contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[] {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                        ContactsContract.CommonDataKinds.Phone.NUMBER},
                null, null, null);
        List<String> contactsList = new ArrayList<>();
        while (cursor.moveToNext()) {
            String name = "";
            String phoneNumber = "";
            int name_index = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
            if (name_index >= 0) {
                name = cursor.getString(name_index);
            }
            int phone_index = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            if (phone_index >=0 ) {
                phoneNumber = cursor.getString(phone_index);
            }
            contactsList.add(
                    "联系人：" + name + "\n" +
                    "号码：" + phoneNumber
            );
        }
        // 创建ArrayAdapter适配器
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_list_item_1, contactsList);
        // 获取binding实例中的ListView
        final ListView contactsListView = binding.contactsList;
        contactsListView.setAdapter(adapter);
        cursor.close();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
