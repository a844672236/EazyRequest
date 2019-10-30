package mvp.lxxjx.com.tmvp_eazyhttp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import mvp.lxxjx.com.tmvp_eazyhttp.net.RetrofitUtils;
import mvp.lxxjx.com.tmvp_eazyhttp.net.base.BaseCallBack;
import mvp.lxxjx.com.tmvp_eazyhttp.net.imp.MoudleImp;

public class MainActivity extends AppCompatActivity implements BaseCallBack{
    /**
     * 头：
     x-user-id   a838ffead11f44e18cc2965cea495448
     x-device    android
     参数
     userId  a838ffead11f44e18cc2965cea495448
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Map<String, String> headerMap = RetrofitUtils.getHeaderMap();
        headerMap.put("x-device","android");
        headerMap.put("x-user-id","a838ffead11f44e18cc2965cea495448");


        HashMap<String, String> requestMap = new HashMap<>();
        requestMap.put("phone","17611401667");
        requestMap.put("userId","a838ffead11f44e18cc2965cea495448");
        new MoudleImp().doGet("rest/seventwomatchinfo/seventwoMatchInfo/get/json",requestMap,this);

    }

    @Override
    public void getResult(String result, String requestUrl) {
        Log.e("TAG__","  返回参数--->" + result);

    }

    public void processUrl1(String data){
    }
    public void processUrl2(String data){
        Log.e("TAG__","  返回参数--->" + data);
    }
    @Override
    public void getErrorMessage(String errMsg) {
        //错误处理
        Log.e("TAG__","请求地址--->"+errMsg);
    }


}
