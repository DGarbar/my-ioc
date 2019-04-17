package shorter.service;

import java.util.concurrent.ThreadLocalRandom;

public class ShorterServiceRandomNumber implements ShorterService {

    public void init(){

    }
    @Override
    public String shorten(String path) {
        return String.valueOf(ThreadLocalRandom.current().nextInt(1,1000));
    }
}
