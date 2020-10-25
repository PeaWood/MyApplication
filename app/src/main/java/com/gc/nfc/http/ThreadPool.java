package com.gc.nfc.http;

import java.util.concurrent.ExecutorService;

public class ThreadPool {
  private static ThreadPool s = null;
  
  private static ExecutorService service;
  
  public static ThreadPool getInstance() {
    if(s==null){
      s = new ThreadPool();
    }
    return s;
  }
  
  public void addTask(Runnable paramRunnable) {
    service.execute(paramRunnable);
  }
}
