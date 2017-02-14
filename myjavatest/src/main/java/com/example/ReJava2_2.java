package com.example;

import org.reactivestreams.Publisher;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

/**
 * 从零开始的RxJava2.0教程(二)操作符
 */
public class ReJava2_2 {

    public static void main(String[] args) {
        ne ne = new ne();

        List<Integer> list = new ArrayList<>();
        list.add(10);
        list.add(2);
        list.add(16);

        Flowable.just(list).subscribe(new Consumer<List<Integer>>() {
            @Override
            public void accept(List<Integer> integers) throws Exception {
                for (int in : integers)
                    System.out.println(in);
            }
        });
        ne.next();

        //RxJava 2.0 提供了fromIterable方法，可以接收一个 Iterable 容器作为输入，每次发射一个元素。
        Flowable.fromIterable(list).subscribe(num -> System.out.println(num));


        Flowable.just(list).flatMap(new Function<List<Integer>, Publisher<Integer>>() {
            @Override
            public Publisher<Integer> apply(List<Integer> integers) throws Exception {
                return Flowable.fromIterable(integers);
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                System.out.print(integer);
            }
        });
        ne.next();

//      订阅者只能收到大于5的数据，那么你可以这样做
        Flowable.fromArray(1, 20, 5, 0, -1, 8).filter(new Predicate<Integer>() {
            @Override
            public boolean test(Integer integers) throws Exception {
                return integers.intValue() > 5;//筛选出大于5的数
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integers) throws Exception {
                System.out.println(integers);
            }
        });
        ne.next();

        //只想要2个数据:
        Flowable.fromArray(1, 5, 12, 54).take(2).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                System.out.println(integer);
            }
        });
        ne.next();


//        订阅者接收到数据前干点事情，比如记录日志
        Flowable.fromArray(1,2,3,4,5,7).doOnNext(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                System.out.println("发送"+integer);
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                System.out.println(integer);
            }
        });
        ne.next();



        /**
         * 总结：操作符
         *
         * flatMap 可以把一个 Flowable 转换成另一个 Flowable :
         * filter 是用于过滤数据的，返回false表示拦截此数据；
         * take 用于指定订阅者最多收到多少数据。
         * doOnNext 允许我们在每次输出一个元素之前做一些额外的事情
         */


    }

    static class ne{
        /**
         * 换行
         */
        public void next(){
            System.out.print("\n");
            System.out.print("\n");
        }
    }

}
