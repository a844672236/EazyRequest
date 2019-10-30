package mvp.lxxjx.com.tmvp_eazyhttp.net.service;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.FieldMap;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;

public interface BaseService {

    @POST
    Observable<ResponseBody> doPost(@Url String moudleUrl, @FieldMap Map<String,String> requsetMap);
    @POST
    Observable<ResponseBody> doPost(@Url String moudleUrl);
    @GET
    Observable<ResponseBody> doGet(@Url String url , @QueryMap Map<String,String> requestMap);
    @GET
    Observable<ResponseBody> doGet(@Url String url );

}
