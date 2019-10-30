package mvp.lxxjx.com.tmvp_eazyhttp;

import com.google.gson.Gson;

import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {


    public static final String json = "{\"code\":\"0\",\"date\":{}} ";
    @Test
    public void addition_isCorrect() {
        Bean bean = new Gson().fromJson(json, Bean.class);
        Field[] fields = bean.getDate().getClass().getDeclaredFields();//获取所有属性
        System.out.println(fields.length);
    }
}