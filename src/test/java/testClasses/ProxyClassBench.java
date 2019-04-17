package testClasses;

import ioc.annotation.Benchmark;
import ioc.annotation.Koko;

public class ProxyClassBench {

    @Benchmark
    public int bench() {
        System.out.println("asdasdas dasd asd sd ad WORK WAEW ");
        return 0;
    }

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

}
