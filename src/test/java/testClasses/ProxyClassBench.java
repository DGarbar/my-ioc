package testClasses;

import ioc.annotation.Autowired;
import ioc.annotation.Benchmark;
import ioc.annotation.Init;
import ioc.annotation.Koko;
import ioc.annotation.PostConstruct;
import ioc.annotation.Transaction;
import java.util.List;

public class ProxyClassBench {
    @Autowired
    public ProxyClassBench() {
    }

    public ProxyClassBench(List<String> list) {
    }


    public ProxyClassBench(Object o) {
    }


//    @PostConstruct
//    public void postC() {
//        System.out.println("PostConstruct");
//    }

//    @Benchmark
//    @Init
//    public int initBench() {
//        System.out.println("BENCH ");
//        System.out.println("INIT");
//        return 0;
//    }
//
    @Benchmark
    public void voidBench() {
        System.out.println("asdasdas dasd asd sd ad WORK WAEW ");
    }

    public void not() {
        System.out.println("simple ");
    }

    @Benchmark
    @Koko
    public void koko() {
        System.out.println("koko ");
    }

    @Transaction
    public void tr() {
        System.out.println("tr");
    }

}
