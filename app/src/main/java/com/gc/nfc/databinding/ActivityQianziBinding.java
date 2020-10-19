package com.gc.nfc.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;


import com.gc.nfc.R;
import com.venusic.handwrite.view.HandWriteView;

public class ActivityQianziBinding extends ViewDataBinding {
  @Nullable
  private static final IncludedLayouts sIncludes = null;
  
  @Nullable
  private static final SparseIntArray sViewsWithIds = new SparseIntArray();
  
  @NonNull
  public final Button clear;
  
  private long mDirtyFlags = -1L;
  
  @NonNull
  private final RelativeLayout mboundView0;
  
  @NonNull
  public final Button save;
  
  @NonNull
  public final HandWriteView view;
  
  static {
    sViewsWithIds.put(R.id.view, 1);
    sViewsWithIds.put(R.id.save, 2);
    sViewsWithIds.put(R.id.clear, 3);
  }
  
  public ActivityQianziBinding(@NonNull DataBindingComponent paramDataBindingComponent, @NonNull View paramView) {
    super(paramDataBindingComponent, paramView, 0);
    Object[] arrayOfObject = mapBindings(paramDataBindingComponent, paramView, 4, sIncludes, sViewsWithIds);
    this.clear = (Button)arrayOfObject[3];
    this.mboundView0 = (RelativeLayout)arrayOfObject[0];
    this.mboundView0.setTag(null);
    this.save = (Button)arrayOfObject[2];
    this.view = (HandWriteView)arrayOfObject[1];
    setRootTag(paramView);
    invalidateAll();
  }
  
  @NonNull
  public static ActivityQianziBinding bind(@NonNull View paramView) {
    return bind(paramView, DataBindingUtil.getDefaultComponent());
  }
  
  @NonNull
  public static ActivityQianziBinding bind(@NonNull View paramView, @Nullable DataBindingComponent paramDataBindingComponent) {
    if (!"layout/activity_qianzi_0".equals(paramView.getTag()))
      throw new RuntimeException("view tag isn't correct on view:" + paramView.getTag()); 
    return new ActivityQianziBinding(paramDataBindingComponent, paramView);
  }
  
  @NonNull
  public static ActivityQianziBinding inflate(@NonNull LayoutInflater paramLayoutInflater) {
    return inflate(paramLayoutInflater, DataBindingUtil.getDefaultComponent());
  }
  
  @NonNull
  public static ActivityQianziBinding inflate(@NonNull LayoutInflater paramLayoutInflater, @Nullable DataBindingComponent paramDataBindingComponent) {
    return bind(paramLayoutInflater.inflate(R.layout.activity_qianzi, null, false), paramDataBindingComponent);
  }
  
  @NonNull
  public static ActivityQianziBinding inflate(@NonNull LayoutInflater paramLayoutInflater, @Nullable ViewGroup paramViewGroup, boolean paramBoolean) {
    return inflate(paramLayoutInflater, paramViewGroup, paramBoolean, DataBindingUtil.getDefaultComponent());
  }
  
  @NonNull
  public static ActivityQianziBinding inflate(@NonNull LayoutInflater paramLayoutInflater, @Nullable ViewGroup paramViewGroup, boolean paramBoolean, @Nullable DataBindingComponent paramDataBindingComponent) {
    return (ActivityQianziBinding)DataBindingUtil.inflate(paramLayoutInflater, 2131361842, paramViewGroup, paramBoolean, paramDataBindingComponent);
  }
  
  protected void executeBindings() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mDirtyFlags : J
    //   6: lstore_1
    //   7: aload_0
    //   8: lconst_0
    //   9: putfield mDirtyFlags : J
    //   12: aload_0
    //   13: monitorexit
    //   14: return
    //   15: astore_3
    //   16: aload_0
    //   17: monitorexit
    //   18: aload_3
    //   19: athrow
    // Exception table:
    //   from	to	target	type
    //   2	14	15	finally
    //   16	18	15	finally
  }
  
  public boolean hasPendingBindings() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mDirtyFlags : J
    //   6: lconst_0
    //   7: lcmp
    //   8: ifeq -> 17
    //   11: iconst_1
    //   12: istore_1
    //   13: aload_0
    //   14: monitorexit
    //   15: iload_1
    //   16: ireturn
    //   17: aload_0
    //   18: monitorexit
    //   19: iconst_0
    //   20: istore_1
    //   21: goto -> 15
    //   24: astore_2
    //   25: aload_0
    //   26: monitorexit
    //   27: aload_2
    //   28: athrow
    // Exception table:
    //   from	to	target	type
    //   2	11	24	finally
    //   13	15	24	finally
    //   17	19	24	finally
    //   25	27	24	finally
    return false;//自己增加的
  }
  
  public void invalidateAll() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: lconst_1
    //   4: putfield mDirtyFlags : J
    //   7: aload_0
    //   8: monitorexit
    //   9: aload_0
    //   10: invokevirtual requestRebind : ()V
    //   13: return
    //   14: astore_1
    //   15: aload_0
    //   16: monitorexit
    //   17: aload_1
    //   18: athrow
    // Exception table:
    //   from	to	target	type
    //   2	9	14	finally
    //   15	17	14	finally
  }
  
  protected boolean onFieldChange(int paramInt1, Object paramObject, int paramInt2) {
    return false;
  }
  
  public boolean setVariable(int paramInt, @Nullable Object paramObject) {
    return true;
  }
}


/* Location:              D:\智慧配送\classes2-dex2jar.jar!\com\gc\nfc\databinding\ActivityQianziBinding.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */