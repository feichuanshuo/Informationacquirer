package com.example.informationcollector.ui.sensor_information;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.informationcollector.databinding.FragmentSensorInformationBinding;

import java.util.ArrayList;
import java.util.List;

public class SIFragment extends Fragment {

    private FragmentSensorInformationBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSensorInformationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // 获取SIViewModel中的数据列表
        List<String> dataList = new ArrayList<>();

        // 获取数据
        Context context = getContext();
        SensorManager sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            List<Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);
            for (Sensor sensor : sensorList) {
                dataList.add(
                                "名称：" + sensor.getName()+'\n' +
                                "供应商：" + sensor.getVendor() + '\n' +
                                "类型：" + sensor.getType() + '\n' +
                                "版本：" + sensor.getVersion() + '\n' +
                                "最大和最小范围：" + sensor.getMaximumRange() + '\n' +
                                "最小延迟：" + sensor.getMinDelay() + '\n' +
                                "功率：" + sensor.getPower() + '\n' +
                                "分辨率：" + sensor.getResolution()
                        );
            }

        }


        // 创建ArrayAdapter适配器
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_list_item_1, dataList);

        // 获取binding实例中的ListView
        final ListView listView = binding.SIlist;
        listView.setAdapter(adapter);



        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}