package com.meysam.common.model.other;

public class LogContextHolder {
    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();


    public static void save(String logIdentifier) {
        threadLocal.set(logIdentifier);
    }

    public static String read() {
        return threadLocal.get();
    }

    public static void remove() {
        threadLocal.remove();
    }
}
