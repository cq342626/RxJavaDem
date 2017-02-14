package com.chenqi.rxjavademo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;

/**
 *
 */
public class RxJava2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.baidu.com")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())// 添加RxJava2的适配器支持
                .build();

        BaiDuService service = retrofit.create(BaiDuService.class);
        service.getText()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        Toast.makeText(RxJava2Activity.this, "获取成功", Toast.LENGTH_SHORT).show();
                        try {
                            Log.e("--------",responseBody.toString());
                            System.out.println(responseBody.toString());
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        t.printStackTrace();
                        Toast.makeText(RxJava2Activity.this, "获取失败，请检查网络是否畅通", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                        Log.e("--------","任务结束");
                        System.out.println("任务结束");
                    }
                });


        List<String> list = Flowable.range(1,100).map(new Function<Integer, String >() {
            @Override
            public String  apply(Integer integer) throws Exception {
                return "id:" + integer;
            }
        }).toList().blockingGet();
        Flowable.fromIterable(list).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.e("+++++++", s);
            }
        });
        Flowable.just(list).flatMap(new Function<List<String>, Publisher<String>>() {
            @Override
            public Publisher<String> apply(List<String> strings) throws Exception {
                return Flowable.fromIterable(strings);
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.e("------", s);
            }
        });


    }
}
