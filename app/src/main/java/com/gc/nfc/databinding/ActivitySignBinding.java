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
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.gc.nfc.R;

public class ActivitySignBinding extends ViewDataBinding {
  @Nullable
  private static final ViewDataBinding.IncludedLayouts sIncludes = null;
  
  @Nullable
  private static final SparseIntArray sViewsWithIds = new SparseIntArray();
  
  @NonNull
  public final Button btn1;
  
  @NonNull
  public final Button btnUpload;
  
  @NonNull
  public final CheckBox checkbox;
  
  @NonNull
  public final ImageView img1;
  
  @NonNull
  public final TextView itemsAddress;
  
  @NonNull
  public final TextView itemsAddressStatic;
  
  @NonNull
  public final TextView itemsCreatTime;
  
  @NonNull
  public final ImageView itemsImageUserIcon;
  
  @NonNull
  public final TextView itemsOrderSn;
  
  @NonNull
  public final TextView itemsPassedTime;
  
  @NonNull
  public final TextView itemsTotalAcount;
  
  @NonNull
  public final TextView itemsTotalCountKP;
  
  @NonNull
  public final TextView itemsTotalCountZP;
  
  @NonNull
  public final TextView itemsTotalMountDeal;
  
  @NonNull
  public final TextView itemsTotalMountDealGp;
  
  @NonNull
  public final TextView itemsTotalMountOrignal;
  
  @NonNull
  public final TextView itemsTotalQuantity;
  
  @NonNull
  public final TextView itemsTotalQuantityGp;
  
  @NonNull
  public final TextView itemsUserCredit;
  
  @NonNull
  public final TextView itemsUserId;
  
  @NonNull
  public final TextView itemsUserPhone;
  
  @NonNull
  public final LinearLayout linearLayoutOrderFirst;
  
  @NonNull
  public final LinearLayout linearLayoutOrderSecond;
  
  @NonNull
  public final LinearLayout linearLayoutOrderThird;
  
  @NonNull
  public final ListView listview;
  
  @NonNull
  public final ListView listviewKp;
  
  @NonNull
  public final ListView listviewZp;
  
  @NonNull
  public final LinearLayout llybuttom1;
  
  @NonNull
  public final LinearLayout llybuttom2;
  
  @NonNull
  public final LinearLayout llybuttom3;
  
  @NonNull
  public final LinearLayout llytop;
  
  @NonNull
  public final LinearLayout llytop2;
  
  @NonNull
  public final ScrollView llyview;
  
  private long mDirtyFlags = -1L;
  
  @NonNull
  public final TextView textviewPicUploadResult;
  
  @NonNull
  public final ListView yjlistview;
  
  static {
    sViewsWithIds.put(R.id.llytop, 1);
    sViewsWithIds.put(R.id.items_orderSn, 2);
    sViewsWithIds.put(R.id.linearLayout_orderFirst, 3);
    sViewsWithIds.put(R.id.items_creatTime, 4);
    sViewsWithIds.put(R.id.items_passedTime, 5);
    sViewsWithIds.put(R.id.linearLayout_orderSecond, 6);
    sViewsWithIds.put(R.id.items_imageUserIcon, 7);
    sViewsWithIds.put(R.id.items_userId, 8);
    sViewsWithIds.put(R.id.items_userPhone, 9);
    sViewsWithIds.put(R.id.items_userCredit, 10);
    sViewsWithIds.put(R.id.linearLayout_orderThird, 11);
    sViewsWithIds.put(R.id.items_addressStatic, 12);
    sViewsWithIds.put(R.id.items_address, 13);
    sViewsWithIds.put(R.id.llytop2, 14);
    sViewsWithIds.put(R.id.listview, 15);
    sViewsWithIds.put(R.id.items_totalQuantity, 16);
    sViewsWithIds.put(R.id.items_totalMountOrignal, 17);
    sViewsWithIds.put(R.id.items_totalMountDeal, 18);
    sViewsWithIds.put(R.id.yjlistview, 19);
    sViewsWithIds.put(R.id.items_totalQuantityGp, 20);
    sViewsWithIds.put(R.id.items_totalMountDealGp, 21);
    sViewsWithIds.put(R.id.items_totalAcount, 22);
    sViewsWithIds.put(R.id.listview_kp, 23);
    sViewsWithIds.put(R.id.items_totalCountKP, 24);
    sViewsWithIds.put(R.id.listview_zp, 25);
    sViewsWithIds.put(R.id.items_totalCountZP, 26);
    sViewsWithIds.put(R.id.llybuttom3, 27);
    sViewsWithIds.put(R.id.checkbox, 28);
    sViewsWithIds.put(R.id.btn1, 29);
    sViewsWithIds.put(R.id.llybuttom2, 30);
    sViewsWithIds.put(R.id.img1, 31);
    sViewsWithIds.put(R.id.llybuttom1, 32);
    sViewsWithIds.put(R.id.btn_upload, 33);
    sViewsWithIds.put(R.id.textview_picUploadResult, 34);
  }
  
  public ActivitySignBinding(@NonNull DataBindingComponent paramDataBindingComponent, @NonNull View paramView) {
    super(paramDataBindingComponent, paramView, 0);
    Object[] arrayOfObject = mapBindings(paramDataBindingComponent, paramView, 35, sIncludes, sViewsWithIds);
    this.btn1 = (Button)arrayOfObject[29];
    this.btnUpload = (Button)arrayOfObject[33];
    this.checkbox = (CheckBox)arrayOfObject[28];
    this.img1 = (ImageView)arrayOfObject[31];
    this.itemsAddress = (TextView)arrayOfObject[13];
    this.itemsAddressStatic = (TextView)arrayOfObject[12];
    this.itemsCreatTime = (TextView)arrayOfObject[4];
    this.itemsImageUserIcon = (ImageView)arrayOfObject[7];
    this.itemsOrderSn = (TextView)arrayOfObject[2];
    this.itemsPassedTime = (TextView)arrayOfObject[5];
    this.itemsTotalAcount = (TextView)arrayOfObject[22];
    this.itemsTotalCountKP = (TextView)arrayOfObject[24];
    this.itemsTotalCountZP = (TextView)arrayOfObject[26];
    this.itemsTotalMountDeal = (TextView)arrayOfObject[18];
    this.itemsTotalMountDealGp = (TextView)arrayOfObject[21];
    this.itemsTotalMountOrignal = (TextView)arrayOfObject[17];
    this.itemsTotalQuantity = (TextView)arrayOfObject[16];
    this.itemsTotalQuantityGp = (TextView)arrayOfObject[20];
    this.itemsUserCredit = (TextView)arrayOfObject[10];
    this.itemsUserId = (TextView)arrayOfObject[8];
    this.itemsUserPhone = (TextView)arrayOfObject[9];
    this.linearLayoutOrderFirst = (LinearLayout)arrayOfObject[3];
    this.linearLayoutOrderSecond = (LinearLayout)arrayOfObject[6];
    this.linearLayoutOrderThird = (LinearLayout)arrayOfObject[11];
    this.listview = (ListView)arrayOfObject[15];
    this.listviewKp = (ListView)arrayOfObject[23];
    this.listviewZp = (ListView)arrayOfObject[25];
    this.llybuttom1 = (LinearLayout)arrayOfObject[32];
    this.llybuttom2 = (LinearLayout)arrayOfObject[30];
    this.llybuttom3 = (LinearLayout)arrayOfObject[27];
    this.llytop = (LinearLayout)arrayOfObject[1];
    this.llytop2 = (LinearLayout)arrayOfObject[14];
    this.llyview = (ScrollView)arrayOfObject[0];
    this.llyview.setTag(null);
    this.textviewPicUploadResult = (TextView)arrayOfObject[34];
    this.yjlistview = (ListView)arrayOfObject[19];
    setRootTag(paramView);
    invalidateAll();
  }
  
  @NonNull
  public static ActivitySignBinding bind(@NonNull View paramView) {
    return bind(paramView, DataBindingUtil.getDefaultComponent());
  }
  
  @NonNull
  public static ActivitySignBinding bind(@NonNull View paramView, @Nullable DataBindingComponent paramDataBindingComponent) {
    if (!"layout/activity_sign_0".equals(paramView.getTag()))
      throw new RuntimeException("view tag isn't correct on view:" + paramView.getTag()); 
    return new ActivitySignBinding(paramDataBindingComponent, paramView);
  }
  
  @NonNull
  public static ActivitySignBinding inflate(@NonNull LayoutInflater paramLayoutInflater) {
    return inflate(paramLayoutInflater, DataBindingUtil.getDefaultComponent());
  }
  
  @NonNull
  public static ActivitySignBinding inflate(@NonNull LayoutInflater paramLayoutInflater, @Nullable DataBindingComponent paramDataBindingComponent) {
    return bind(paramLayoutInflater.inflate(R.layout.activity_sign, null, false), paramDataBindingComponent);
  }
  
  @NonNull
  public static ActivitySignBinding inflate(@NonNull LayoutInflater paramLayoutInflater, @Nullable ViewGroup paramViewGroup, boolean paramBoolean) {
    return inflate(paramLayoutInflater, paramViewGroup, paramBoolean, DataBindingUtil.getDefaultComponent());
  }
  
  @NonNull
  public static ActivitySignBinding inflate(@NonNull LayoutInflater paramLayoutInflater, @Nullable ViewGroup paramViewGroup, boolean paramBoolean, @Nullable DataBindingComponent paramDataBindingComponent) {
    return (ActivitySignBinding)DataBindingUtil.inflate(paramLayoutInflater, 2131361849, paramViewGroup, paramBoolean, paramDataBindingComponent);
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
    return  true;//自己家的
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

