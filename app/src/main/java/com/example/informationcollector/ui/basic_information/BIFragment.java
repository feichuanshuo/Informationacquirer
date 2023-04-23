package com.example.informationcollector.ui.basic_information;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.informationcollector.databinding.FragmentBasicInformationBinding;

import java.util.ArrayList;
import java.util.List;

public class BIFragment extends Fragment {

    private FragmentBasicInformationBinding binding; // 创建一个用于数据绑定的类的实例

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // 从FragmentBasicInformationBinding中获取binding实例
        binding = FragmentBasicInformationBinding.inflate(inflater, container, false);

        // 获取binding实例中的根视图
        View root = binding.getRoot();

        // 获取BIViewModel中的数据列表
        List<String> dataList = new ArrayList<>();
        dataList.add("设备基板:" + Build.BOARD);
        dataList.add("设备引导程序版本号:" + Build.BOOTLOADER);
        dataList.add("设备品牌:" + Build.BRAND);
        dataList.add("设备驱动:" + Build.DEVICE);
        dataList.add("版本信息:" + Build.DISPLAY);
        dataList.add("唯一标识:" + Build.FINGERPRINT);
        dataList.add("硬件名称:" + Build.HARDWARE);
        dataList.add("主机地址:" + Build.HOST);
        dataList.add("设备版本号:" + Build.ID);
        dataList.add("设备制造商:" + Build.MANUFACTURER);
        dataList.add("设备型号:" + Build.MODEL);
        dataList.add("整个产品的名称:" + Build.PRODUCT);
        dataList.add("设备标签:" + Build.TAGS);
        dataList.add("设备版本类型:" + Build.TYPE);

        // 创建ArrayAdapter适配器
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_list_item_1, dataList);

        // 获取binding实例中的ListView
        final ListView listView = binding.BIlist;
        listView.setAdapter(adapter);


        return root; // 返回根视图
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // 将binding实例置为空
    }
}