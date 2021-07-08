package com.sun.admin;

import org.junit.jupiter.api.Test;

public class Test1 {
    private Integer i=1;

    @Test
    public void test() {
        Integer k=i;
        k=2;
        System.out.println(k);
        System.out.println(i);
    }
}
