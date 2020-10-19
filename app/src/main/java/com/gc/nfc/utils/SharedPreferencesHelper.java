package com.gc.nfc.utils;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.Map;

public class SharedPreferencesHelper {
  public static final String FILE_NAME = "shared_data";
  
  private static SharedPreferences.Editor editor;
  
  private static SharedPreferences sharedPreferences;
  
  public SharedPreferencesHelper(Context paramContext) {
  }
  
  public static void clear() {
    editor.clear();
    editor.commit();
  }
  
  public static boolean contains(String paramString) {
    return sharedPreferences.contains(paramString);
  }
  
  public static Object get(String paramString, Object paramObject) {
    return (paramObject instanceof String) ? sharedPreferences.getString(paramString, (String)paramObject) : ((paramObject instanceof Integer) ? Integer.valueOf(sharedPreferences.getInt(paramString, ((Integer)paramObject).intValue())) : ((paramObject instanceof Boolean) ? Boolean.valueOf(sharedPreferences.getBoolean(paramString, ((Boolean)paramObject).booleanValue())) : ((paramObject instanceof Float) ? Float.valueOf(sharedPreferences.getFloat(paramString, ((Float)paramObject).floatValue())) : ((paramObject instanceof Long) ? Long.valueOf(sharedPreferences.getLong(paramString, ((Long)paramObject).longValue())) : ((paramObject instanceof Double) ? Double.valueOf(Double.longBitsToDouble(sharedPreferences.getLong(paramString, Double.doubleToLongBits(((Double)paramObject).doubleValue())))) : sharedPreferences.getString(paramString, null))))));
  }
  
  public static Map<String, ?> getAll() {
    return sharedPreferences.getAll();
  }
  
  public static void initial(Context paramContext) {
    sharedPreferences = paramContext.getSharedPreferences(FILE_NAME, 0);
    editor = sharedPreferences.edit();
  }
  
  public static void put(String paramString, Object paramObject) {
    if (paramObject instanceof String) {
      editor.putString(paramString, (String)paramObject);
    } else if (paramObject instanceof Integer) {
      editor.putInt(paramString, ((Integer)paramObject).intValue());
    } else if (paramObject instanceof Boolean) {
      editor.putBoolean(paramString, ((Boolean)paramObject).booleanValue());
    } else if (paramObject instanceof Float) {
      editor.putFloat(paramString, ((Float)paramObject).floatValue());
    } else if (paramObject instanceof Long) {
      editor.putLong(paramString, ((Long)paramObject).longValue());
    } else if (paramObject instanceof Double) {
      editor.putLong(paramString, Double.doubleToRawLongBits(((Double)paramObject).doubleValue()));
    } else {
      editor.putString(paramString, paramObject.toString());
    } 
    editor.commit();
  }
  
  public static void remove(String paramString) {
    editor.remove(paramString);
    editor.commit();
  }
}