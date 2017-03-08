package com.football.yuedong.happysports.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.football.yuedong.happysports.R;
import com.football.yuedong.happysports.domain.Order;
import com.football.yuedong.happysports.wxapi.WXpay;

/**
 * Created by liuyj on 2016/4/19.
 */
public class OrderConfirmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_order_confirm);

        findViewById(R.id.id_pay_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        findViewById(R.id.id_pay_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pay();
            }
        });
    }

    private void pay() {
        Order order = getIntent().getParcelableExtra("order");
        JSONObject orderObj = JSON.parseObject(order.getValue());
        WXpay pay = new WXpay();
        pay.regToWx(this);
        pay.setupWxPay(orderObj.getIntValue("amount") + "", orderObj.getLongValue("id") + "");
    }
}
