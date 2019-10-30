package mvp.lxxjx.com.tmvp_eazyhttp.net.intercepter;

import android.util.Log;

import java.io.IOException;
import java.util.Map;
import java.util.function.BiConsumer;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class HeaderIntercepter implements Interceptor {

    private Map<String ,String> header;
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder request = chain.request()
                .newBuilder();
        if (header!=null){
            for(Map.Entry<String, String> entry : header.entrySet()){
                String key = entry.getKey();
                String value = entry.getValue();
                request.addHeader(key,value);
            }
        }
        Request build = request.build();
        return chain.proceed(build);
    }

    public void addHeader(Map<String ,String> header){
        this.header =   header;
    }
}
