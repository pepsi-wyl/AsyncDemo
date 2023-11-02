package com.ylan.asyncdemo.service;

import java.util.concurrent.Future;

/**
 * @author by pepsi-wyl
 * @date 2023-10-31 10:22
 */

public interface AsyncTask {
    void testAsync();
    void asyncInvokeWithException(String s);
    Future<String> asyncInvokeReturnFuture(String s);
}