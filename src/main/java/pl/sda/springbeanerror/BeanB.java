package pl.sda.springbeanerror;

import org.springframework.stereotype.Component;

@Component
public class BeanB {

    private final BeanC c;

    public BeanB(BeanC c) {
        this.c = c;
    }

    public void test() {
        c.test();
    }
}
