package com.zyaud.idata.iam.common.utils;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @program: idata-iam
 * @description: 同步用户线程池
 * @author: qiuyang
 * @create: 2022-02-21 11:20
 **/
public class PoolExecutorUtil {
    static ThreadPoolExecutor executor;

    public PoolExecutorUtil() {
    }

    public static ThreadPoolExecutor getThreadPool() {
        return executor;
    }

    static {
        executor = new ThreadPoolExecutor(5, 10, 72L, TimeUnit.HOURS, new LinkedBlockingQueue(5000), new ThreadPoolExecutor.CallerRunsPolicy());
    }

}
