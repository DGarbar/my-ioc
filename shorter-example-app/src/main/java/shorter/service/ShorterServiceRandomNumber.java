package shorter.service;

import ioc.annotation.Benchmark;
import ioc.annotation.Component;
import ioc.annotation.Init;
import java.util.concurrent.ThreadLocalRandom;

@Component(name = "shorterServiceRandomNumber")
public class ShorterServiceRandomNumber implements ShorterService {

    @Init
    public void init(){
        System.out.println("random number init");
    }

    @Benchmark
    @Override
    public String shorten(String path) {
        return String.valueOf(ThreadLocalRandom.current().nextInt(1,1000));
    }
}
