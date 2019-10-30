package mvp.lxxjx.com.tmvp_eazyhttp.net;

import android.util.TimeUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import mvp.lxxjx.com.tmvp_eazyhttp.net.intercepter.LoggerIntercepter;
import mvp.lxxjx.com.tmvp_eazyhttp.net.service.BaseService;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtils {

    private Retrofit mRetrofitInstance;
    private OkHttpClient mClient;
    private static Map<String, String> header;
    private static RetrofitUtils mInstance;
    private LoggerIntercepter loggerIntercepter;

    public static RetrofitUtils getInstance() {
        if (mInstance == null) {
            synchronized (RetrofitUtils.class) {
                if (mInstance == null) {
                    mInstance = new RetrofitUtils();
                    return mInstance;
                } else {
                    return mInstance;
                }
            }
        } else {
            return mInstance;
        }
    }

    private RetrofitUtils() {
        initRetrofit();
    }

    //创建Retrofit对象
    private void initRetrofit() {
        initClinet();
        mRetrofitInstance = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(NetConst.Host)
                .client(mClient)
                .build();
    }

    //设置log拦截器,超时与失败重试
    private void initClinet() {
        OkHttpClient.Builder clientBuild = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(new LoggerIntercepter());
        if (header == null) {
            mClient = clientBuild.build();
        }else{
            mClient = clientBuild.addInterceptor(addHeaderInterceptor())
                    .build();
        }
    }

    //获取服务
    public BaseService getmService() {
        return mRetrofitInstance.create(BaseService.class);
    }

    //获取Retrofit实例
    public Retrofit getRetrofitInstance() {
        return this.mRetrofitInstance;
    }

    //添加请求头
    public static Map<String,String> getHeaderMap() {
        if (header == null) {
            header = new HashMap<>();
        }
        return header;
    }
    //添加请求头到拦截器
    private Interceptor addHeaderInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request.Builder request = chain.request()
                        .newBuilder();
                for (Map.Entry<String, String> entry : header.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    request.addHeader(key, value);
                }
                Request build = request.build();
                return chain.proceed(build);
            }
        };
    }

}
