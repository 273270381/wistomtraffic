package com.analysis.wisdomtraffic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.analysis.wisdomtraffic.R;
import com.bumptech.glide.Glide;
import com.youth.banner.adapter.BannerAdapter;
import org.apache.commons.lang3.StringUtils;
import java.util.List;
import java.util.Map;


/**
 * @Author hejunfeng
 * @Date 14:32 2020/12/11 0011
 * @Description com.analysis.dataanalysis.adapter
 **/
public class ImageAdapter extends BannerAdapter<Map<String,String>, ImageAdapter.ImageHolder> {
    private List<Map<String,String>> datas;
    private Context context;
    private int width;

    public ImageAdapter(List<Map<String,String>> datas, Context context) {
        super(datas);
        this.datas = datas;
        this.context = context;
    }

    public void setWidth(int width){
        this.width = width;
    }

    @Override
    public ImageHolder onCreateHolder(ViewGroup parent, int viewType) {
//        ImageView imageView = new ImageView(parent.getContext());
//        //注意，必须设置为match_parent，这个是viewpager2强制要求的
//        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.MATCH_PARENT);
//        imageView.setLayoutParams(params);
//        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        return new ImageHolder(imageView);
        return new ImageHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.banner_image_title, parent, false));
    }

    //更新数据
    public void update(List<Map<String,String>> dataBeans){
        datas.clear();
        datas.addAll(dataBeans);
        notifyDataSetChanged();
    }

    @Override
    public void onBindView(ImageHolder holder, Map<String,String> data, int position, int size) {
        String title = data.get("title");
        String url = data.get("url");
        if (!StringUtils.isEmpty(url)){
            Glide.with(context).load(url).into(holder.imageView);
        }
        if (!StringUtils.isEmpty(title))
        holder.title.setText(title);
    }

    public class ImageHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public TextView title;
        public ImageHolder(@NonNull View itemView) {
            super(itemView);
            this.imageView =itemView.findViewById(R.id.image);
            this.title = itemView.findViewById(R.id.bannerTitle);
        }
    }
}
