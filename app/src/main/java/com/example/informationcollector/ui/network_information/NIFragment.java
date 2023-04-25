package com.example.informationcollector.ui.network_information;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.informationcollector.databinding.FragmentNetworkInformationBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NIFragment extends Fragment {

    private FragmentNetworkInformationBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState){

        binding = FragmentNetworkInformationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // 已连接的网络
        List<String> CWiFIList = new ArrayList<>();
        // 周围的网络
        List<String> AWiFiList = new ArrayList<>();

        Context context = getContext();

        // 获取当前连接的wifi信息
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
        if (capabilities != null) {
            // 网络已连接
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                // 当前连接的是WiFi网络
                WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                if(wifiInfo == null) {
                    CWiFIList.add("未连接到wifi！");
                }
                else {
                    CWiFIList.add(
                                    "wifi名称：" + wifiInfo.getSSID() + "\n" +
                                    "BSSID：" + wifiInfo.getBSSID() + "\n" +
                                    "IP地址：" + wifiInfo.getIpAddress() + "\n" +
                                    "MAC地址：" + wifiInfo.getMacAddress() + "\n" +
                                    "网络ID：" + wifiInfo.getNetworkId() + "\n" +
                                    "连接状态：" + wifiInfo.getSupplicantState() + "\n" +
                                    "信号强度：" + wifiInfo.getRssi() + "\n" +
                                    "链接速度：" + wifiInfo.getLinkSpeed()
                    );
                }
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                // 当前连接的是移动网络
                CWiFIList.add("当前连接的是移动网络！");
            }
        } else {
            // 网络未连接
            CWiFIList.add("无网络连接！");
        }
        // 创建ArrayAdapter适配器
        ArrayAdapter<String> cadapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_list_item_1, CWiFIList);

        // 获取binding实例中的ListView
        final ListView CWiFilistView = binding.CWiFilist;
        CWiFilistView.setAdapter(cadapter);

        // 将日期时间格式转换为SimpleDateFormat对象
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");

        // 获取周围wifi信息
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Permission not granted, request it
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
        } else {
            // Permission granted, continue with scanning
            List<ScanResult> wifiList = wifiManager.getScanResults();
            for (ScanResult scanResult : wifiList) {
                long currentTimeMillis = System.currentTimeMillis();
                long elapsedTimeMillis = SystemClock.elapsedRealtime();
                long scanTimestamp = scanResult.timestamp / 1000; // 将微秒转换为毫秒
                long unixTimestamp = currentTimeMillis - elapsedTimeMillis + scanTimestamp;
                AWiFiList.add(
                        "wifi名称：" + scanResult.SSID + "\n" +
                        "BSSID：" + scanResult.BSSID + "\n" +
                        "频率：" + scanResult.frequency + "\n" +
                        "加密和身份验证方案：" + scanResult.capabilities + "\n" +
                        "信号强度：" + scanResult.level + "\n" +
                        "时间戳：" + sdf.format(new Date(unixTimestamp))
                );
            }
            // 创建ArrayAdapter适配器
            ArrayAdapter<String> aadapter = new ArrayAdapter<>(requireContext(),
                    android.R.layout.simple_list_item_1,AWiFiList);

            // 获取binding实例中的ListView
            final ListView AWiFilistView = binding.AWiFilist;
            AWiFilistView.setAdapter(aadapter);
        }

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
