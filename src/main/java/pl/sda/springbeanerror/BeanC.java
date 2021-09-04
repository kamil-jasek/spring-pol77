package pl.sda.springbeanerror;

import org.springframework.stereotype.Component;

@Component
public class BeanC {

    private final BeanA a;

    public BeanC(BeanA a) {
        this.a = a;
    }

    public void test() {
        a.test();
    }
}
