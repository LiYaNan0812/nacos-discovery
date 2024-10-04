package com.lyn.jvmtest;

public class Demo {

    private static final int _1MB = 1024 * 1024;

    public static void main(String[] args) {
        testAllocation();
    }

    /**
     * VM参数； -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8
     */
    private static void testAllocation() {
        byte[] allocation1, allocation2, allocation3, allocation4, allocation5;
        allocation1 = new byte[2 * _1MB];
        allocation2 = new byte[2 * _1MB];
        allocation3 = new byte[2 * _1MB];
        allocation1 = null;
        allocation2 = null;
        allocation3 = null;

        allocation4 = new byte[400 * 1024];
        allocation5 = new byte[500 * 1024];

        allocation1 = new byte[2 * _1MB];
        allocation2 = new byte[2 * _1MB];
        allocation3 = new byte[2 * _1MB];
//        allocation4 = new byte[2 * _1MB]; // 出现一次minor GC
    }
}
