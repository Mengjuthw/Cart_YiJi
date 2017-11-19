package com.example.a171118_zhouliu_disanzhou.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a171118_zhouliu_disanzhou.R;
import com.example.a171118_zhouliu_disanzhou.SecondActivity;
import com.example.a171118_zhouliu_disanzhou.bean.SecondBean;
import com.example.a171118_zhouliu_disanzhou.custom.CustomView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Menglucywhh on 2017/11/18.
 */
public class SecondRecyAdapter extends RecyclerView.Adapter<SecondRecyAdapter.MyViewholder1>{

    Context context;
    List<SecondBean.DataBean.ListBean> listShop;

    public SecondRecyAdapter(Context context, List<SecondBean.DataBean.ListBean> listShop) {
        this.listShop = listShop;
        this.context =  context;
    }


    @Override
    public MyViewholder1 onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context,R.layout.second_recy_adapter,null);
        MyViewholder1 myviewholder1 = new MyViewholder1(view);
        return myviewholder1;
    }

    @Override
    public void onBindViewHolder(final MyViewholder1 holder, final int position) {

        String images = listShop.get(position).getImages();
        String[] split = images.split("\\|");

        //设置商品图片
        ImageLoader.getInstance().displayImage(split[0],holder.ivPic);
        //调用customiew类里面的方法,,设置商品名称
        holder.customView.getCount(listShop.get(position).getNum());
      // holder.tvShopName.setText(listShop.get(position).getSellerid());
        holder.tvName.setText(listShop.get(position).getTitle());
        holder.tvPrice.setText(listShop.get(position).getPrice()+"");
        //给 每个条目的选中按钮 设置状态,根据数据源里面的check
        holder.product_select.setChecked(listShop.get(position).isCheck());
        //主动点击每个条目前面的多选框,点击事件
        holder.product_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               //拿到当前多选框的选中状态,,赋值给数据源里面,,holder.product_select.isChecked()
               listShop.get(position).setCheck(holder.product_select.isChecked());
                notifyDataSetChanged();

                if(checkBoxListener != null){
                    //secondActivity调用方法
                    checkBoxListener.checkBox(listShop);
                }

            }
        });

        //customview中的加减号的点击监听
        holder.customView.setClickListener(new CustomView.ClickListener() {
            @Override
            public void click(int count) {
                //count是当前的数量
                //根据传来的count 改变数据源里的num
                listShop.get(position).setNum(count);
                notifyDataSetChanged();

                //接口回调出去..
                if(customViewListener!=null){
                    //把当前集合传过去
                    customViewListener.customClick(listShop);
                }
            }
        });

        //删除当前条目的点击事件
        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //改变数据源中的 删除当前条目
                listShop.remove(position);
                notifyDataSetChanged();

                //接口回调出去
                if(delItemListener!=null){
                    delItemListener.delItem(listShop);
                }
            }
        });


 }

    @Override
    public int getItemCount() {
        return listShop==null?0:listShop.size();
    }

    public static class MyViewholder1 extends RecyclerView.ViewHolder {

        private final ImageView ivDelete;
        private final TextView tvPrice;
        private final TextView tvName;
        private final ImageView ivSelect;
        CustomView customView;
        private final ImageView ivPic;
        private final CheckBox product_select;//每个条目前面的多选框,
        // private final TextView tvShopName;//商铺名称

        public MyViewholder1(View itemView) {
            super(itemView);
            //找到所有的控件
            ivDelete = (ImageView) itemView.findViewById(R.id.iv_item_shopcart_cloth_delete);
            tvPrice = (TextView) itemView.findViewById(R.id.tv_item_shopcart_cloth_price);
            tvName = (TextView) itemView.findViewById(R.id.tv_item_shopcart_clothname);
           ivSelect = (ImageView) itemView.findViewById(R.id.shopcart_cloth_select);
            ivPic = (ImageView) itemView.findViewById(R.id.iv_item_shopcart_cloth_pic);
            product_select = (CheckBox) itemView.findViewById(R.id.cb_item_shopcart_clothselect);
            customView = (CustomView) itemView.findViewById(R.id.custom_View);
           // tvShopName = (TextView) itemView.findViewById(R.id.tv_item_shopcart_shopname);


        }
    }

    //返回当前集合的方法
    public List<SecondBean.DataBean.ListBean> getList(){
        return listShop;
    }
    //实现多选框的监听
    CheckBoxListener checkBoxListener;
    public void setCheckBoxListener(CheckBoxListener checkBoxListener){
        this.checkBoxListener = checkBoxListener;
    }

    //每个条目前面的多选框点击的监听
    public interface CheckBoxListener{
        public void checkBox(List<SecondBean.DataBean.ListBean> list);
    }

    //实现加减号 改变数量和总价
     public interface CustomViewListener{
        public void customClick(List<SecondBean.DataBean.ListBean> list);
    }
    //实现加减号 的监听
    CustomViewListener customViewListener;
    public void setCustomViewListener(CustomViewListener customViewListener){
        this.customViewListener = customViewListener;
    }

    //实现删除按钮的点击事件
    public interface DelItemListener{
        public void delItem(List<SecondBean.DataBean.ListBean> list);
    }
    DelItemListener delItemListener;
    public void setDelItemListener(DelItemListener delItemListener){
        this.delItemListener = delItemListener;
    }

}
