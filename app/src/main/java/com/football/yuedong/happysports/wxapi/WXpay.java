package com.football.yuedong.happysports.wxapi;

import android.content.Context;
import android.util.Log;

import com.football.yuedong.happysports.BuildVars;
import com.google.gson.Gson;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by liuyanjun on 2016/3/2.
 */
public class WXpay {

    private IWXAPI api;

    public void regToWx(Context context) {
        api = WXAPIFactory.createWXAPI(context, null);
        api.registerApp(BuildVars.WX_PAY_APP_ID);
    }

    /**
     * 启用微信支付
     */
    public void setupWxPay(String amount, String orderId) {
        OkHttpUtils.post().url(BuildVars.SERVER_Prepaid_Order)
                .addParams("itemName", "itemName")
                .addParams("notify_url", BuildVars.SERVER_Validate_Order)
                .addParams("spbill_create_ip", "192.168.1.100")
                .addParams("amount", amount)
                .addParams("attach", orderId)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Log.e("AAAAAA", e.getMessage(), e);
            }

            @Override
            public void onResponse(String s) {
                Log.i("AAAAAA", s);
                open(s);
            }
        });

//        PayReq request = new PayReq();
//        request.appId = BuildVars.WX_PAY_APP_ID;
//        request.partnerId = BuildVars.WX_PARTNER_ID;
//        //pre pay id;
//        request.prepayId = "1101000000140415649af9fc314aa427";
//        request.packageValue = "Sign=WXPay";
//
//        //服务器端返回的随机串
//        request.nonceStr = "1101000000140429eb40476f8896f4c9";
//        request.timeStamp = String.valueOf(System.currentTimeMillis() / 1000);
//
//        //根据随机串生成的签名串//相当于一个数字水印
//        request.sign = "7FFECB600D7157C5AA49810D2D8F28BC2811827B";
//        api.sendReq(request);
    }

    private void open(String s) {
        PayReq request = new Gson().fromJson(s, PayReq.class);
        api.sendReq(request);
    }
}
