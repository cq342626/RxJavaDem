package com.example;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * 从零开始的RxJava2.0教程(一)基础
 */
public class ReJava2_1 {

    public static void main(String[] args) {
        System.out.println("我是Java项目");
        //重载了所有的方法
        Flowable<String> flowable = Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(FlowableEmitter<String> e) throws Exception {
                e.onNext("我是ReJava");
                e.onComplete();
            }
        }, BackpressureStrategy.BUFFER);

        Subscriber subscreber = new Subscriber() {
            @Override
            public void onSubscribe(Subscription s) {
                System.out.println("Subscription");
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(Object o) {
                String str = (String) o;
                System.out.println(str);
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }
        };
        flowable.subscribe(subscreber);

        //仅仅发送一条数据
        Flowable<String> flowable2 = Flowable.just("我是ReJava第二次发送");
        Consumer consumer = new Consumer() {
            @Override
            public void accept(Object o) throws Exception {
                System.out.println(((String) o));
            }
        };
        flowable2.subscribe(consumer);

        //无需生产变量的方法
        Flowable.just("我是ReJava第三次发送").subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.println(s);
            }
        });


//        6. 操作符
//        操作符是为了解决 Flowable 对象变换问题而设计的，操作符可以在传递的途中对数据进行修改。
//        RxJava提供了很多实用的操作符。比如 map 操作符，可以把一个事件转换成另一个事件。
        Flowable.just("map").map(new Function<String, String>() {
            @Override
            public String apply(String s) throws Exception {
                return s + "-chenqi";
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.println(s);
            }
        });


//        7. map 操作符进阶
//        map 操作符更神奇的地方是，你可以返回任意类型的 Flowable，也就是说你可以使用 map 操作符发射一个新的数据类型的 Flowable 对象。
//        比如上面的例子，订阅者想要得到字符串的hashcode。
        Flowable.just("map1").map(new Function<String, Integer>() {
            @Override
            public Integer apply(String s) throws Exception {
                return s.hashCode();
            }
        }).map(new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) throws Exception {
                return integer.toString();
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.println(s);
            }
        });


    }

}
