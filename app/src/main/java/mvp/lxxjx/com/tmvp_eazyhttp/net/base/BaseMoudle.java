package mvp.lxxjx.com.tmvp_eazyhttp.net.base;

import java.io.IOException;
import java.util.Map;

import mvp.lxxjx.com.tmvp_eazyhttp.net.RetrofitUtils;
import mvp.lxxjx.com.tmvp_eazyhttp.net.service.BaseService;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public abstract class BaseMoudle {
    protected BaseService mServicee = getmService();
    protected Retrofit mRetrofit = getmRetrofit();
    private String requestUrl;

    //获取RetrofitUtils实例对象
    protected RetrofitUtils getRetrofitInstance(){
        return RetrofitUtils.getInstance();
    }

    //获取Service实例对象
    protected BaseService getmService(){
        return getRetrofitInstance().getmService();
    }

    //获取Retrofit实例对象
    protected Retrofit getmRetrofit(){
        return getRetrofitInstance().getRetrofitInstance();
    }

    //Get请求有参
    public void doGet(String url, Map<String,String> requestMap,BaseCallBack callBack){
        setUrl(url);
        autoCallBack(getmService().doGet(url,requestMap),callBack);
    }

    //无参Get请求
    public void doGet(String url,BaseCallBack callBack){
        setUrl(url);
        autoCallBack(getmService().doGet(url),callBack);
    }

    //无参Post请求
    public void doPost(String url,BaseCallBack callBack){
        setUrl(url);
        autoCallBack(getmService().doPost(url),callBack);
    }

    //有参Post请求
    public void doPost(String url , Map<String,String> requestMap ,BaseCallBack callBack){
        setUrl(url);
        autoCallBack(getmService().doPost(url,requestMap),callBack);
    }

    private void setUrl(String url){
        this.requestUrl = url;
    };

    private String getUrl(){
        return this.requestUrl;
    }

    //回调,且将接口返回
    private void autoCallBack(Observable<ResponseBody> observable, final BaseCallBack callBack){
        //通过Observable发起请求
        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.getErrorMessage(e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String string = responseBody.string();
                            if (string == null) {
                                callBack.getErrorMessage("服务器无数据响应");
                            }else{
                                callBack.getResult(string,getUrl());
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                            callBack.getErrorMessage(e.getMessage());
                        }
                    }
                });//发起请求，请求的结果会回调到订阅者observer中

    }
}
