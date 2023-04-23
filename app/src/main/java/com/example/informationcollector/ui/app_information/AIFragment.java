package com.example.informationcollector.ui.app_information;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.informationcollector.databinding.FragmentAppInformationBinding;

import java.util.List;

public class AIFragment extends Fragment {

    private FragmentAppInformationBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAppInformationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        // 获取数据
        Context context = getContext();
        PackageManager pm = context.getPackageManager();
        List<ApplicationInfo> appList = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        AIadapter adapter = new AIadapter(context,appList);

        // 获取binding实例中的ListView
        final ListView listView = binding.AIlist;
        listView.setAdapter(adapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}