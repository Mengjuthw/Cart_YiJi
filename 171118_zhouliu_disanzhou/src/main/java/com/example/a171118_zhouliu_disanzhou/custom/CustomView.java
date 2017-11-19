package com.example.a171118_zhouliu_disanzhou.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.a171118_zhouliu_disanzhou.R;
import com.example.a171118_zhouliu_disanzhou.adapter.SecondRecyAdapter;

/**
 * Created by Menglucywhh on 2017/11/19.
 */
public class CustomView extends LinearLayout{

    private Button reverse;
    private Button add;
    private EditText editText;
    private int mCount;
    Context context;
    public CustomView(Context context) {
        super(context);
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context =context;

        View view = View.inflate(context, R.layout.custom_view,this);

        reverse = (Button) view.findViewById(R.id.reverse);
        add = (Button) view.findViewById(R.id.add);
        editText = (EditText) view.findViewById(R.id.count);

       //点击减号,,数量减1
        reverse.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = editText.getText().toString().trim();
                //转成int
                int count = Integer.valueOf(content);
                if(count>1){
                    mCount = count-1;
                    editText.setText(mCount+"");//设置数量

                    //接口回调 把mCount暴露出去,,adapter里面调用
                   if(clickListener!=null){
                       clickListener.click(mCount);
                   }
                }
            }
        });

        //点击加号 数量加1
        add.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = editText.getText().toString().trim();
                int count = Integer.valueOf(content);

                mCount = count+1;//赋值给mcount
                editText.setText(count+"");

                if(clickListener!=null){
                    clickListener.click(mCount);
                }
            }
        });


    }

    ClickListener clickListener;
    public void setClickListener(ClickListener clickListener){
        this.clickListener = clickListener;
    }
    //设置加减号的监听
    public interface ClickListener{
        public void click(int count);//把当前的数量传过去
    }

    //设置初始的数量值
    public void getCount(int count){
        editText.setText(count+"");//设置初始数量
    }



    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
