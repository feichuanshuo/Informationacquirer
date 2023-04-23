package com.example.informationcollector.ui.app_information;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.informationcollector.R;

import java.util.List;

public class AIadapter extends BaseAdapter {
    private Context mContext;
    private List<ApplicationInfo> mItemList;

    public AIadapter(Context context, List<ApplicationInfo> itemList) {
        mContext = context;
        mItemList = itemList;
    }

    @Override
    public int getCount() {
        return mItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return mItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Inflate the custom list item layout
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.app_information_list_item, parent, false);
        }

        // Get the current item from the list
        ApplicationInfo item = mItemList.get(position);

        // 应用图标
        ImageView iconView = convertView.findViewById(R.id.item_icon);
        iconView.setImageDrawable(item.loadIcon(mContext.getPackageManager()));
        // 应用名称
        TextView titleTextView = convertView.findViewById(R.id.item_packageName);
        titleTextView.setText(item.packageName);
        // 应用是否运行
        TextView isEnabledTextView = convertView.findViewById(R.id.item_isEnabled);
        if (item.enabled){
            isEnabledTextView.setText("已启用");
        }
        else{
            isEnabledTextView.setText("未启用");
        }
        // 应用源目录
        TextView sourceDirTextView = convertView.findViewById(R.id.item_sourceDir);
        sourceDirTextView.setText("源目录：" + item.sourceDir);

        // 应用数据目录
        TextView dataDirTextView = convertView.findViewById(R.id.item_dataDir);
        dataDirTextView.setText("数据目录：" + item.dataDir);




        return convertView;
    }
}
