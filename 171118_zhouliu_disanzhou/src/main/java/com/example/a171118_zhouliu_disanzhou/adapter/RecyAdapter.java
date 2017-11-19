package com.example.a171118_zhouliu_disanzhou.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.a171118_zhouliu_disanzhou.MainActivity;
import com.example.a171118_zhouliu_disanzhou.R;
import com.example.a171118_zhouliu_disanzhou.bean.RecyBean;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;
import java.util.Random;

/**
 * Created by Menglucywhh on 2017/11/18.
 */
public class RecyAdapter extends RecyclerView.Adapter<RecyAdapter.MyViewHolder>{

    private int itemWidth;
    Context context;
    List<RecyBean.DataBean> list;
    public RecyAdapter(Context context, List<RecyBean.DataBean> list) {
        this.list = list;
        this.context = context;

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        int width = wm.getDefaultDisplay().getWidth();
        itemWidth = width/3;//定义固定宽度,页面的3分之1

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.recy_item,null);
        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        //设置高
        ViewGroup.LayoutParams params = holder.rectimage.getLayoutParams();
        //初始世高度为300
        int itemHeight = 300;

        itemHeight = new Random().nextInt(500);
        if(itemHeight<300){
            itemHeight = 300;
        }

        //给imageview设置宽高
        params.width = itemWidth;
        params.height = itemHeight;

        holder.rectimage.setLayoutParams(params);

        String images = list.get(position).getImages();
        String[] split = images.split("\\|");

        ImageLoader.getInstance().displayImage(split[0],holder.rectimage);

    }

    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private final ImageView rectimage;

        public MyViewHolder(View itemView) {
            super(itemView);
            rectimage = (ImageView) itemView.findViewById(R.id.recy_image);

        }
    }
}
