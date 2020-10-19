package com.gc.nfc.service;

import android.app.Service;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.annotation.RequiresApi;
//import com.gc.nfc.utils.AmapLocationService;

@RequiresApi(api = 21)
public class MyJobService extends JobService {
  private Handler mJobHandler = new Handler(new Handler.Callback() {
        public boolean handleMessage(Message param1Message) {
          return true;
        }
      });
  
  public boolean isMyServiceRunning(Class<?> paramClass) {
    // Byte code:
    //   0: aload_0
    //   1: ldc 'activity'
    //   3: invokevirtual getSystemService : (Ljava/lang/String;)Ljava/lang/Object;
    //   6: checkcast android/app/ActivityManager
    //   9: ldc 2147483647
    //   11: invokevirtual getRunningServices : (I)Ljava/util/List;
    //   14: invokeinterface iterator : ()Ljava/util/Iterator;
    //   19: astore_2
    //   20: aload_2
    //   21: invokeinterface hasNext : ()Z
    //   26: ifeq -> 62
    //   29: aload_2
    //   30: invokeinterface next : ()Ljava/lang/Object;
    //   35: checkcast android/app/ActivityManager$RunningServiceInfo
    //   38: astore_3
    //   39: aload_1
    //   40: invokevirtual getName : ()Ljava/lang/String;
    //   43: aload_3
    //   44: getfield service : Landroid/content/ComponentName;
    //   47: invokevirtual getClassName : ()Ljava/lang/String;
    //   50: invokevirtual equals : (Ljava/lang/Object;)Z
    //   53: ifeq -> 20
    //   56: iconst_1
    //   57: istore #4
    //   59: iload #4
    //   61: ireturn
    //   62: iconst_0
    //   63: istore #4
    //   65: goto -> 59
    return  false;
  }
  
  public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2) {
    return Service.START_NOT_STICKY;
  }
  
  public boolean onStartJob(JobParameters paramJobParameters) {
    boolean bool1 = false;
    boolean bool2 = bool1;
    try {
//      if (Build.VERSION.SDK_INT >= 21) {
//        String str1 = paramJobParameters.getExtras().getString("servicename");
//        String str2 = paramJobParameters.getExtras().getString("userId");
//        Class<?> clazz = getClassLoader().loadClass(str1);
//        if (clazz != null && !isMyServiceRunning(clazz)) {
//          Intent intent1 = new Intent();
//          this(getApplicationContext(), clazz);
//          Bundle bundle = new Bundle();
//          this();
//          bundle.putString("userId", str2);
//          intent1.putExtras(bundle);
//          Intent intent2 = new Intent();
//          this(intent1);
//          startService(intent2);
//        }
//        jobFinished(paramJobParameters, false);
//        startJobScheduler(str2);
//        bool2 = true;
//      }
    } catch (Exception exception) {
      exception.printStackTrace();
      bool2 = bool1;
    } 
    return bool2;
  }
  
  public boolean onStopJob(JobParameters paramJobParameters) {
    return false;
  }
  
  @RequiresApi(21)
  public void startJobScheduler(String paramString) {
    JobScheduler jobScheduler = (JobScheduler)getSystemService(Context.JOB_SCHEDULER_SERVICE);
    jobScheduler.cancel(55);
    JobInfo.Builder builder = new JobInfo.Builder(55, new ComponentName((Context)this, MyJobService.class));
    builder.setMinimumLatency(5000L);
    builder.setOverrideDeadline(6000L);
    builder.setBackoffCriteria(30000L, JobInfo.BACKOFF_POLICY_LINEAR);
    builder.setPersisted(true);
    builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
    builder.setRequiresCharging(true);
    PersistableBundle persistableBundle = new PersistableBundle();
//    persistableBundle.putString("servicename", AmapLocationService.class.getName());
    persistableBundle.putString("userId", paramString);
    builder.setExtras(persistableBundle);
    jobScheduler.schedule(builder.build());
  }
}


/* Location:              C:\Users\lenovo\Desktop\classes2-dex2jar.jar!\com\gc\nfc\service\MyJobService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */