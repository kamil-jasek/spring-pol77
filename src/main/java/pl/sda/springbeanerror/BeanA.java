package pl.sda.springbeanerror;

import org.springframework.stereotype.Component;

@Component
public class BeanA {

    private final BeanB b;

    public BeanA(BeanB b) {
        this.b = b;
    }

    public void test() {
        b.test();
    }
}
