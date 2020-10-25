package com.gc.nfc.utils;

public class Tools {
  private static final int MIN_CLICK_DELAY_TIME = 1000;
  
  private static long lastClickTime;
  
  public static boolean isFastClick() {
    boolean bool = false;
    long l = System.currentTimeMillis();
    if (l - lastClickTime >= MIN_CLICK_DELAY_TIME)
      bool = true; 
    lastClickTime = l;
    return bool;
  }
}