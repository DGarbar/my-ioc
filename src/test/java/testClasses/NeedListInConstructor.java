package testClasses;


import ioc.annotation.Autowired;
import java.util.List;

public class NeedListInConstructor {
    List list;

    @Autowired
    public NeedListInConstructor(List list) {
        this.list = list;
    }

    public List getList() {
        return list;
    }
}
