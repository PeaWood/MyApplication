package com.gc.nfc.utils;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public class FullyGridLayoutManager extends GridLayoutManager {
  private int[] mMeasuredDimension = new int[2];
  
  final RecyclerView.State mState = new RecyclerView.State();
  
  public FullyGridLayoutManager(Context paramContext, int paramInt) {
    super(paramContext, paramInt);
  }
  
  public FullyGridLayoutManager(Context paramContext, int paramInt1, int paramInt2, boolean paramBoolean) {
    super(paramContext, paramInt1, paramInt2, paramBoolean);
  }
  
  private void measureScrapChild(RecyclerView.Recycler paramRecycler, int paramInt1, int paramInt2, int paramInt3, int[] paramArrayOfint) {
    if (paramInt1 < this.mState.getItemCount())
      try {
        View view = paramRecycler.getViewForPosition(0);
        if (view != null) {
          RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams)view.getLayoutParams();
          view.measure(ViewGroup.getChildMeasureSpec(paramInt2, getPaddingLeft() + getPaddingRight(), layoutParams.width), ViewGroup.getChildMeasureSpec(paramInt3, getPaddingTop() + getPaddingBottom(), layoutParams.height));
          paramArrayOfint[0] = view.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
          paramArrayOfint[1] = view.getMeasuredHeight() + layoutParams.bottomMargin + layoutParams.topMargin;
          paramRecycler.recycleView(view);
        } 
      } catch (Exception exception) {
        exception.printStackTrace();
      }  
  }
  
  public void onMeasure(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, int paramInt1, int paramInt2) {
    int i = View.MeasureSpec.getMode(paramInt1);
    int j = View.MeasureSpec.getMode(paramInt2);
    int k = View.MeasureSpec.getSize(paramInt1);
    int m = View.MeasureSpec.getSize(paramInt2);
    paramInt2 = 0;
    paramInt1 = 0;
    int n = getItemCount();
    int i1 = getSpanCount();
    for (byte b = 0; b < n; b++) {
      measureScrapChild(paramRecycler, b, View.MeasureSpec.makeMeasureSpec(b, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(b, 0), this.mMeasuredDimension);
      if (getOrientation() == 0) {
        int i2 = paramInt2;
        if (b % i1 == 0)
          i2 = paramInt2 + this.mMeasuredDimension[0]; 
        paramInt2 = i2;
        if (b == 0) {
          paramInt1 = this.mMeasuredDimension[1];
          paramInt2 = i2;
        } 
      } else {
        int i2 = paramInt1;
        if (b % i1 == 0)
          i2 = paramInt1 + this.mMeasuredDimension[1]; 
        paramInt1 = i2;
        if (b == 0) {
          paramInt2 = this.mMeasuredDimension[0];
          paramInt1 = i2;
        } 
      } 
    } 
    switch (i) {
      default:
        switch (j) {
          default:
            setMeasuredDimension(paramInt2, paramInt1);
            return;
          case -1://我改的  zwz
            break;
        } 
        break;
      case 1073741824:
        paramInt2 = k;
    } 
    paramInt1 = m;
  }
}

