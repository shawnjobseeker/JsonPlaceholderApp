package uk.co.theappexperts.shawn.assignment;

import android.app.Application;
import android.support.v4.util.LruCache;

import com.laimiux.rxnetwork.RxNetwork;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static uk.co.theappexperts.shawn.assignment.Constants.BASE_URL;

/**
 * Created by TheAppExperts on 03/01/2017.
 */

public class ApiClient {


    private static Retrofit retrofit = null;

    public static Retrofit getClient(Application application) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = buildClient(application);
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }
    public static OkHttpClient buildClient(final Application application){

        Cache cache = new Cache(application.getCacheDir(), 10 * 1024 * 1024);
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .cache(cache)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        //this is where we will add whatever we want to our request headers.
                        Response response = chain.proceed(chain.request());
                        if (RxNetwork.getConnectivityStatus(application.getBaseContext())) {
                            response = response.newBuilder().header("Cache-Control", "public, max-age=" + 60).build();
                        } else {
                            response = response.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build();
                        }
                        return response;
                    }
                });
        /*try {
            cache.delete();
        }
        catch (IOException io) {
            io.printStackTrace();
        }*/
        return builder.build();
    }
}
