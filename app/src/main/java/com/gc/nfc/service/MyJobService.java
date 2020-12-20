package com.gc.nfc.service;

import android.app.ActivityManager;
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

import java.util.ArrayList;

@RequiresApi(api = 21)
public class MyJobService extends JobService {
  private Handler mJobHandler = new Handler(new Handler.Callback() {
        public boolean handleMessage(Message param1Message) {
          return true;
        }
      });
  
  public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2) {
    return Service.START_STICKY;
  }

  public boolean isMyServiceRunning(Class<?> paramClass) {
      ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
      ArrayList<ActivityManager.RunningServiceInfo> runningService =
              (ArrayList<ActivityManager.RunningServiceInfo>) activityManager.getRunningServices(40);
      for(int i=0; i<runningService.size();i++){
          String name = runningService.get(i).service.getClassName();
          if(name.equals(paramClass.getName())){
              return true;
          }
      }
      return  false;
  }

  public boolean onStartJob(JobParameters paramJobParameters) {
    boolean bool1 = false;
    boolean bool2 = bool1;
    try {
      if (Build.VERSION.SDK_INT >= 21) {
        String str1 = paramJobParameters.getExtras().getString("servicename");
        String str2 = paramJobParameters.getExtras().getString("userId");
        Class<?> clazz = getClassLoader().loadClass(str1);
        if (clazz != null && !isMyServiceRunning(clazz)) {
          Intent intent1 = new Intent(getApplicationContext(), clazz);
          Bundle bundle = new Bundle();
          bundle.putString("userId", str2);
          intent1.putExtras(bundle);
          Intent intent2 = new Intent(intent1);
          startService(intent2);
        } 
        jobFinished(paramJobParameters, false);
        startJobScheduler(str2);
        bool2 = true;
      } 
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
    persistableBundle.putString("servicename", AmapLocationService.class.getName());
    persistableBundle.putString("userId", paramString);
    builder.setExtras(persistableBundle);
    jobScheduler.schedule(builder.build());
  }
}

