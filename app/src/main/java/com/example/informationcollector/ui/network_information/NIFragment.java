package com.example.informationcollector.ui.network_information;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.informationcollector.databinding.FragmentNetworkInformationBinding;

import java.util.ArrayList;
import java.util.List;

public class NIFragment extends Fragment {

    private FragmentNetworkInformationBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState){

        binding = FragmentNetworkInformationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        List<String> dataList = new ArrayList<>();

        Context context = getContext();

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
        if (capabilities != null) {
            // 网络已连接
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                // 当前连接的是WiFi网络
                WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                if(wifiInfo == null) {
                    dataList.add("未连接到wifi！");
                }
                else {
                    dataList.add(
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
                dataList.add("当前连接的是移动网络！");
            }
        } else {
            // 网络未连接
            dataList.add("无网络连接！");
        }
        // 创建ArrayAdapter适配器
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_list_item_1, dataList);

        // 获取binding实例中的ListView
        final ListView listView = binding.NIlist;
        listView.setAdapter(adapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
