package com.example.informationcollector.ui.locaton_information;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.informationcollector.databinding.FragmentLocationInformationBinding;

public class LIFragment extends Fragment {
    private FragmentLocationInformationBinding binding;
    private static final int REQUEST_LOCATION_PERMISSION = 1;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState){
        binding = FragmentLocationInformationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Context context = getContext();
        TextView textView = binding.LItext;

        // 获取位置服务
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        // 检查是否有位置权限
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // 获取最近一次位置信息
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location != null) {
                // 获取当前经纬度
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();

                // 处理位置信息
                String text = "经度：" + longitude + "\n" + "纬度：" + latitude ;
                textView.setText(text);
            }
            else {
                textView.setText("无法获得位置信息！");
            }
        }


        return root;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
