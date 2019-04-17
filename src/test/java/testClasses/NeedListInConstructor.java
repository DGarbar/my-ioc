package testClasses;


import java.util.List;

public class NeedListInConstructor {
    List list;

    public NeedListInConstructor(List list) {
        this.list = list;
    }

    public List getList() {
        return list;
    }
}
