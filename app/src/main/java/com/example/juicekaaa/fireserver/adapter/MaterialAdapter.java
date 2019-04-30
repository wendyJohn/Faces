package com.example.juicekaaa.fireserver.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.juicekaaa.fireserver.R;
import com.example.juicekaaa.fireserver.net.ArchitectureBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 物资数据适配器
 *
 * @author QiaoShi
 *
 */
public class MaterialAdapter extends BaseAdapter {

	private List<ArchitectureBean> mList;
	private Context mContext;

	public MaterialAdapter(Context mContext, List<ArchitectureBean> mList) {
		super();
		this.mContext = mContext;
		this.mList = mList;
	}

	@Override
	public int getCount() {
        if(mList.size()%2>0) {
            return mList.size()/2+1;
        } else {
            return mList.size()/2;
        }
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressWarnings("deprecation")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		Holder holder;

		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.material_item, null);
			holder = new Holder();
			holder.name = convertView.findViewById(R.id.name);
			holder.number =convertView.findViewById(R.id.number);
			holder.icon =convertView.findViewById(R.id.icon);

            holder.names = convertView.findViewById(R.id.names);
            holder.numbers =convertView.findViewById(R.id.numbers);
            holder.icons =convertView.findViewById(R.id.icons);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
        int distance = mList.size() - position*2;
		int cellCount = distance >= 2? 2:distance;
		final List<ArchitectureBean> itemList = mList.subList(position*2,position*2+cellCount);
		if (itemList.size() >0) {
            String lack=mList.get(position).getLack();
            holder.name.setText(mList.get(position).getName());
            if(lack.equals("0")){
                holder.number.setText("数量:"+ mList.get(position).getNumber());
                holder.number.setTextColor(mContext.getResources().getColor(R.color.black));
            }else{
                holder.number.setText("数量:"+ mList.get(position).getNumber()+"  "+"( 缺:"+ lack+" )");
                holder.number.setTextColor(mContext.getResources().getColor(R.color.red));
            }
            String image_type=mList.get(position).getType();
            if(image_type.equals("ZJHXQ")||image_type.equals("HXQ")){
                holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.a));
            }
            if(image_type.equals("XFTK")){
                holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.b));
            }
            if(image_type.equals("FHF")){
                holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.c));
            }
            if(image_type.equals("ST")){
                holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.d));
            }
            if(image_type.equals("AQS")){
                holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.e));
            }
            if(image_type.equals("YD")){
                holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.f));
            }
            if(image_type.equals("JX")){
                holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.g));
            }
            if(image_type.equals("SD")){
                holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.h));
            }
            if(image_type.equals("XFQT")){
                holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.i));
            }
            if(image_type.equals("SDJT")){
                holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.j));
            }
            if(image_type.equals("ZDDJ")){
                holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.k));
            }
            if(image_type.equals("MHQ")){
                holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.l));
            }
            if(image_type.equals("TSYJX")){
                holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.m));
            }
            if(image_type.equals("XFT")){
                holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.n));
            }
            if(image_type.equals("KYLB")){
                holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.o));
            }
            if(image_type.equals("LY")) {
                holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.p));
            }
            if(image_type.equals("JJX")) {
                holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.jjx));
            }
            if(image_type.equals("YYYQD")) {
                holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.yyyqd));
            }
            if(image_type.equals("FDMJ")) {
                holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.fdmj));
            }
            if(image_type.equals("MHT")) {
                holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.mht));
            }
            if(image_type.equals("JTZ")) {
                holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.jtz));
            }
            if(image_type.equals("FBSD")) {
                holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.fbsd));
            }
            if(image_type.equals("XFJBS")) {
                holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.xfjbs));
            }
            if(image_type.equals("XFC")) {
                holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.xfc));
            }
            if(image_type.equals("XFF")) {
                holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.xff));
            }
            if(image_type.equals("AEDZDTWCCY")) {
                holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.defibrillator_icon));
            }
            if (itemList.size() >1){
                holder.names.setText(mList.get(position).getName());
                if(lack.equals("0")){
                    holder.numbers.setText("数量:"+ mList.get(position).getNumber());
                    holder.numbers.setTextColor(mContext.getResources().getColor(R.color.black));
                }else{
                    holder.numbers.setText("数量:"+ mList.get(position).getNumber()+"  "+"( 缺:"+ lack+" )");
                    holder.numbers.setTextColor(mContext.getResources().getColor(R.color.red));
                }
                if(image_type.equals("ZJHXQ")||image_type.equals("HXQ")){
                    holder.icons.setBackground(mContext.getResources().getDrawable(R.drawable.a));
                }
                if(image_type.equals("XFTK")){
                    holder.icons.setBackground(mContext.getResources().getDrawable(R.drawable.b));
                }
                if(image_type.equals("FHF")){
                    holder.icons.setBackground(mContext.getResources().getDrawable(R.drawable.c));
                }
                if(image_type.equals("ST")){
                    holder.icons.setBackground(mContext.getResources().getDrawable(R.drawable.d));
                }
                if(image_type.equals("AQS")){
                    holder.icons.setBackground(mContext.getResources().getDrawable(R.drawable.e));
                }
                if(image_type.equals("YD")){
                    holder.icons.setBackground(mContext.getResources().getDrawable(R.drawable.f));
                }
                if(image_type.equals("JX")){
                    holder.icons.setBackground(mContext.getResources().getDrawable(R.drawable.g));
                }
                if(image_type.equals("SD")){
                    holder.icons.setBackground(mContext.getResources().getDrawable(R.drawable.h));
                }
                if(image_type.equals("XFQT")){
                    holder.icons.setBackground(mContext.getResources().getDrawable(R.drawable.i));
                }
                if(image_type.equals("SDJT")){
                    holder.icons.setBackground(mContext.getResources().getDrawable(R.drawable.j));
                }
                if(image_type.equals("ZDDJ")){
                    holder.icons.setBackground(mContext.getResources().getDrawable(R.drawable.k));
                }
                if(image_type.equals("MHQ")){
                    holder.icons.setBackground(mContext.getResources().getDrawable(R.drawable.l));
                }
                if(image_type.equals("TSYJX")){
                    holder.icons.setBackground(mContext.getResources().getDrawable(R.drawable.m));
                }
                if(image_type.equals("XFT")){
                    holder.icons.setBackground(mContext.getResources().getDrawable(R.drawable.n));
                }
                if(image_type.equals("KYLB")){
                    holder.icons.setBackground(mContext.getResources().getDrawable(R.drawable.o));
                }
                if(image_type.equals("LY")) {
                    holder.icons.setBackground(mContext.getResources().getDrawable(R.drawable.p));
                }
                if(image_type.equals("JJX")) {
                    holder.icons.setBackground(mContext.getResources().getDrawable(R.drawable.jjx));
                }
                if(image_type.equals("YYYQD")) {
                    holder.icons.setBackground(mContext.getResources().getDrawable(R.drawable.yyyqd));
                }
                if(image_type.equals("FDMJ")) {
                    holder.icons.setBackground(mContext.getResources().getDrawable(R.drawable.fdmj));
                }
                if(image_type.equals("MHT")) {
                    holder.icons.setBackground(mContext.getResources().getDrawable(R.drawable.mht));
                }
                if(image_type.equals("JTZ")) {
                    holder.icons.setBackground(mContext.getResources().getDrawable(R.drawable.jtz));
                }
                if(image_type.equals("FBSD")) {
                    holder.icons.setBackground(mContext.getResources().getDrawable(R.drawable.fbsd));
                }
                if(image_type.equals("XFJBS")) {
                    holder.icons.setBackground(mContext.getResources().getDrawable(R.drawable.xfjbs));
                }
                if(image_type.equals("XFC")) {
                    holder.icons.setBackground(mContext.getResources().getDrawable(R.drawable.xfc));
                }
                if(image_type.equals("XFF")) {
                    holder.icons.setBackground(mContext.getResources().getDrawable(R.drawable.xff));
                }
                if(image_type.equals("AEDZDTWCCY")) {
                    holder.icons.setBackground(mContext.getResources().getDrawable(R.drawable.defibrillator_icon));
                }
            }

        }else {
            holder.names.setVisibility(View.INVISIBLE);
            holder.numbers.setVisibility(View.INVISIBLE);
            holder.icons.setVisibility(View.INVISIBLE);

        }


//		String lack=mList.get(position).getLack();
//		holder.name.setText(mList.get(position).getName());
//		if(lack.equals("0")){
//			holder.number.setText("数量:"+ mList.get(position).getNumber());
//			holder.number.setTextColor(mContext.getResources().getColor(R.color.black));
//		}else{
//			holder.number.setText("数量:"+ mList.get(position).getNumber()+"  "+"( 缺:"+ lack+" )");
//			holder.number.setTextColor(mContext.getResources().getColor(R.color.red));
//		}
//		String image_type=mList.get(position).getType();
//		if(image_type.equals("ZJHXQ")||image_type.equals("HXQ")){
//			holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.a));
//		}
//		 if(image_type.equals("XFTK")){
//			holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.b));
//		}
//		 if(image_type.equals("FHF")){
//			holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.c));
//		}
//		if(image_type.equals("ST")){
//			holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.d));
//		}
//		if(image_type.equals("AQS")){
//			holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.e));
//		}
//		 if(image_type.equals("YD")){
//			holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.f));
//		}
//		 if(image_type.equals("JX")){
//			holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.g));
//		}
//		 if(image_type.equals("SD")){
//			holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.h));
//		}
//		 if(image_type.equals("XFQT")){
//			holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.i));
//		}
//		 if(image_type.equals("SDJT")){
//			holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.j));
//		}
//		 if(image_type.equals("ZDDJ")){
//			holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.k));
//		}
//		 if(image_type.equals("MHQ")){
//			holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.l));
//		}
//		 if(image_type.equals("TSYJX")){
//			holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.m));
//		}
//		 if(image_type.equals("XFT")){
//			holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.n));
//		}
//		 if(image_type.equals("KYLB")){
//			holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.o));
//		}
//		 if(image_type.equals("LY")) {
//			holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.p));
//		}
//		if(image_type.equals("JJX")) {
//			holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.jjx));
//		}
//		if(image_type.equals("YYYQD")) {
//			holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.yyyqd));
//		}
//		if(image_type.equals("FDMJ")) {
//			holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.fdmj));
//		}
//		if(image_type.equals("MHT")) {
//			holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.mht));
//		}
//		if(image_type.equals("JTZ")) {
//			holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.jtz));
//		}
//		if(image_type.equals("FBSD")) {
//			holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.fbsd));
//		}
//		if(image_type.equals("XFJBS")) {
//			holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.xfjbs));
//		}
//		if(image_type.equals("XFC")) {
//			holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.xfc));
//		}
//		if(image_type.equals("XFF")) {
//			holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.xff));
//		}
//		if(image_type.equals("AEDZDTWCCY")) {
//			holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.defibrillator_icon));
//		}

		return convertView;
	}

	class Holder {
		TextView name;
		TextView number;
		ImageView icon;

        TextView names;
        TextView numbers;
        ImageView icons;
	}
}
