package com.example.a171118_zhouliu_disanzhou;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.a171118_zhouliu_disanzhou.adapter.RecyAdapter;
import com.example.a171118_zhouliu_disanzhou.adapter.SecondRecyAdapter;
import com.example.a171118_zhouliu_disanzhou.bean.SecondBean;
import com.example.a171118_zhouliu_disanzhou.okhttp.AbstractUiCallBack;
import com.example.a171118_zhouliu_disanzhou.okhttp.OkhttpUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class SecondActivity extends AppCompatActivity {

    private TextView all_select;//全选按钮
    private TextView totalPrice;//总价
    private TextView totalNum;//总件数
    private List<SecondBean.DataBean.ListBean> listShop = new ArrayList<>();
    private SecondRecyAdapter secondRecyAdapter;

    boolean mSelect = false;
    private RecyclerView second_recyview;
    private boolean shifoucheck;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        //获取控件

        second_recyview = (RecyclerView) findViewById(R.id.second_recyview);
        all_select = (TextView) findViewById(R.id.quanxuan);//全选按钮
        totalPrice = (TextView) findViewById(R.id.totalprice);
        totalNum = (TextView) findViewById(R.id.totalnum);

       // secondRecyAdapter = new SecondRecyAdapter(SecondActivity.this,listShop);;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SecondActivity.this,LinearLayoutManager.VERTICAL,false);
        second_recyview.setLayoutManager(linearLayoutManager);
        //请求网络数据
        getData();
        //请求完网络数据 设置适配器
        if(secondRecyAdapter==null){
            secondRecyAdapter = new SecondRecyAdapter(SecondActivity.this,listShop);
            second_recyview.setAdapter(secondRecyAdapter);
        }else{
            secondRecyAdapter.notifyDataSetChanged();
        }

        //进入页面先给总的全选按钮设置一个标记tag tag为1  不选中, tag为2选中
        all_select.setTag(1);//默认不选中

        //为了通过all_select改变 每条数据的选中状态
        shifoucheck = false;
        //全选按钮的点击事件
        all_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置图片和字在一个控件里面,,图片的大小不会打乱
                   // Drawable left = getResources().getDrawable(R.drawable.shopcart_selected);
                   // all_select.setCompoundDrawablesWithIntrinsicBounds(left,null,null,null);

                //获取all_select 的tag
                int tag = (int) all_select.getTag();
                if(tag==1){
                    //如果是没选中,点击以后 改变成选中
                    all_select.setTag(2);
                    all_select.setBackgroundResource(R.drawable.shopcart_selected);
                    //控制所有的条目都改成 选中
                    shifoucheck = true;
                }else{
                    //如果当前是选中的 就设置成未选中
                    all_select.setTag(1);
                    all_select.setBackgroundResource(R.drawable.shopcart_unselected);
                    shifoucheck = false;
                }

                //然后遍历集合里面的所有条目都设置成选中
                for (SecondBean.DataBean.ListBean bean : listShop){
                    //给数据源里面的check 标记为 上面改变的
                    bean.setCheck(shifoucheck);
                }
                secondRecyAdapter.notifyDataSetChanged();//刷新适配器
                sum(secondRecyAdapter.getList());//把适配器里面的集合传进去,求和
          }
        });

        //调用适配器里面的选中多选框的监听
        secondRecyAdapter.setCheckBoxListener(new SecondRecyAdapter.CheckBoxListener() {
            @Override
            public void checkBox(List<SecondBean.DataBean.ListBean> list) {
                //调用求和的方法
                sum(list);
            }
        });

        //调用适配器里面的 加减号的监听
        secondRecyAdapter.setCustomViewListener(new SecondRecyAdapter.CustomViewListener() {
            @Override
            public void customClick(List<SecondBean.DataBean.ListBean> list) {
                sum(list);
            }
        });

        //调用适配器里面的 删除当前条目的方法
        secondRecyAdapter.setDelItemListener(new SecondRecyAdapter.DelItemListener() {
            @Override
            public void delItem(List<SecondBean.DataBean.ListBean> list) {
                sum(list);
            }
        });

    }


    //求总价的总数量的方法
    public void sum(List<SecondBean.DataBean.ListBean> listShop){
        float price = 0;
         int count = 0;
        //反着全选的控制,,,控制下面的总的 全选按钮
        boolean allCheck = true;
        for (SecondBean.DataBean.ListBean bean : listShop){
            if(bean.isCheck()){
                //如果当前的check为true.计算总价
                price += bean.getPrice()*bean.getNum();
                count += bean.getNum();
            }else{
                //只要有一个为没选中,就标记,改变下面的全选按钮
                allCheck = false;

            }

            //设置下面的总价和数量
            totalNum.setText("共"+count+"件商品");
            totalPrice.setText("总价: "+price);
        }

        //根据allcheck 改变下面的全选按钮的值
        if(allCheck){
            //如果遍历出来的所有check都是true,就全选也选中
            all_select.setTag(2);
            all_select.setBackgroundResource(R.drawable.shopcart_selected);
        }else{
            all_select.setTag(1);
            all_select.setBackgroundResource(R.drawable.shopcart_unselected);
        }

    }


    //请求网络数据解析
    private void getData() {
        String path = "http://120.27.23.105/product/getCarts?uid=100";
        OkhttpUtils.getInstance().asy(null, path, new AbstractUiCallBack<SecondBean>() {

             @Override
            public void success(SecondBean secondBean) {
               // System.out.println(secondBean.getData().get(0).getList().get(0).getTitle());
               //添加到集合里 data里面的list
                for(int i=0;i<secondBean.getData().size();i++){
                    //将集合里面的list集合添加到大集合里去
                    listShop.addAll(secondBean.getData().get(i).getList());
                }
                // System.out.println(listShop.get(0).getTitle());
                //设置适配器
               /*if(secondRecyAdapter==null){
                    secondRecyAdapter = new SecondRecyAdapter(SecondActivity.this,listShop);
                second_recyview.setAdapter(secondRecyAdapter);
                }else{
                    secondRecyAdapter.notifyDataSetChanged();
                }*/

            }

            @Override
            public void failure(Exception e) {

            }
        });
    }
}
