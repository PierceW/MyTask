package com.cmcc.ks;

import org.junit.Test;

/**
 * @ClassName UtilsTest
 * @Description TODO
 * @Author Alex
 * @Date 2019/3/23 20:44
 * @Version 1.0
 **/
public class UtilsTest {

    @Test
    public void testStringFormat() {
        String val = String.format("Hi,%s", "王力");
        System.out.println(val);
    }
}
