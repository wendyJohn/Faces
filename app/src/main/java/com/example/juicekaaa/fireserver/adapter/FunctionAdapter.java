package com.example.juicekaaa.fireserver.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.juicekaaa.fireserver.R;
import com.example.juicekaaa.fireserver.net.ArchitectureBean;

import java.util.List;


/**
 * 应用适配器
 *
 * @author QiaoShi
 */
public class FunctionAdapter extends BaseAdapter {

    private Context mContext;
    private List<ArchitectureBean> mList;

    public FunctionAdapter(Context mContext, List<ArchitectureBean> mList) {
        // TODO Auto-generated constructor stub
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        Holder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.myfunction_item, null);
            holder = new Holder();
            holder.name = convertView.findViewById(R.id.myfunc_name);
            holder.icon = convertView.findViewById(R.id.myfunc_icon);

            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        String lack = mList.get(position).getLack();
        if (lack.equals("0")) {
            holder.name.setText(mList.get(position).getName() + "(" + mList.get(position).getNumber() + ")");
            holder.name.setTextColor(mContext.getResources().getColor(R.color.black));
        } else {
            holder.name.setText(mList.get(position).getName() + "(" + mList.get(position).getNumber() + " " + " ( 缺:" + lack + " ))");
            holder.name.setTextColor(mContext.getResources().getColor(R.color.red));
        }
        String image_type = mList.get(position).getType();
        if (image_type.equals("ZJHXQ") || image_type.equals("HXQ")) {
            holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.a));
        }
        if (image_type.equals("XFTK")) {
            holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.b));
        }
        if (image_type.equals("FHF")) {
            holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.c));
        }
        if (image_type.equals("ST")) {
            holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.d));
        }
        if (image_type.equals("AQS")) {
            holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.e));
        }
        if (image_type.equals("YD")) {
            holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.f));
        }
        if (image_type.equals("JX")) {
            holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.g));
        }
        if (image_type.equals("SD")) {
            holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.h));
        }
        if (image_type.equals("XFQT")) {
            holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.i));
        }
        if (image_type.equals("SDJT")) {
            holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.j));
        }
        if (image_type.equals("ZDDJ")) {
            holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.k));
        }
        if (image_type.equals("MHQ")) {
            holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.l));
        }
        if (image_type.equals("TSYJX")) {
            holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.m));
        }
        if (image_type.equals("XFT")) {
            holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.n));
        }
        if (image_type.equals("KYLB")) {
            holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.o));
        }
        if (image_type.equals("LY")) {
            holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.p));
        }
        if (image_type.equals("JJX")) {
            holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.jjx));
        }
        if (image_type.equals("YYYQD")) {
            holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.yyyqd));
        }
        if (image_type.equals("FDMJ")) {
            holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.fdmj));
        }
        if (image_type.equals("MHT")) {
            holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.mht));
        }
        if (image_type.equals("JTZ")) {
            holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.jtz));
        }
        if (image_type.equals("FBSD")) {
            holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.fbsd));
        }
        if (image_type.equals("XFJBS")) {
            holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.xfjbs));
        }
        if (image_type.equals("XFC")) {
            holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.xfc));
        }
        if (image_type.equals("XFF")) {
            holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.xff));
        }
        if (image_type.equals("AEDZDTWCCY")) {
            holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.defibrillator_icon));
        }

        return convertView;
    }


    class Holder {
        TextView name;
        ImageView icon;

    }
}
