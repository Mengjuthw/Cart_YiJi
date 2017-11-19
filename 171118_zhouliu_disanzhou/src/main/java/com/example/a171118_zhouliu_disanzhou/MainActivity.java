package com.example.a171118_zhouliu_disanzhou;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.a171118_zhouliu_disanzhou.adapter.RecyAdapter;
import com.example.a171118_zhouliu_disanzhou.bean.RecyBean;
import com.example.a171118_zhouliu_disanzhou.okhttp.AbstractUiCallBack;
import com.example.a171118_zhouliu_disanzhou.okhttp.OkhttpUtils;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;
    private RecyAdapter recyAdapter;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (Button) findViewById(R.id.btn);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        //设置成瀑布流式管理
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, LinearLayout.VERTICAL);

        recyclerView.setLayoutManager(staggeredGridLayoutManager);

        getData();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SecondActivity.class);
                startActivity(intent);
            }
        });

    }

    //访问网路数据的方法
    private void getData() {
        String path = "http://120.27.23.105/product/getProducts?pscid=2&page=1";
        //封装网络请求类,
        OkhttpUtils.getInstance().asy(null, path, new AbstractUiCallBack<RecyBean>() {

            @Override
            public void success(RecyBean recyBean) {
                //recyBean是请求到的数据
                System.out.println(recyBean.getData().get(0).getTitle());
                //装着data数据的集合
                List<RecyBean.DataBean> list = recyBean.getData();
                //设置适配器显示
                if(recyAdapter==null) {
                    recyAdapter = new RecyAdapter(MainActivity.this, list);
                    recyclerView.setAdapter(recyAdapter);
                }else{
                    recyAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void failure(Exception e) {

            }
        });
    }
}
