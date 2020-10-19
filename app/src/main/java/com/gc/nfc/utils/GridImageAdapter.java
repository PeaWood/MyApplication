package com.gc.nfc.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.gc.nfc.R;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.DateUtils;
import com.luck.picture.lib.tools.StringUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GridImageAdapter extends RecyclerView.Adapter<GridImageAdapter.ViewHolder> {
  public static final int TYPE_CAMERA = 1;
  
  public static final int TYPE_PICTURE = 2;
  
  private Context context;
  
  private List<LocalMedia> list = new ArrayList<LocalMedia>();
  
  private LayoutInflater mInflater;
  
  private OnItemClickListener mItemClickListener;
  
  private onAddPicClickListener mOnAddPicClickListener;
  
  private int selectMax = 9;
  
  public GridImageAdapter(Context paramContext, onAddPicClickListener paramonAddPicClickListener) {
    this.context = paramContext;
    this.mInflater = LayoutInflater.from(paramContext);
    this.mOnAddPicClickListener = paramonAddPicClickListener;
  }
  
  private boolean isShowAddItem(int paramInt) {
    return (paramInt == this.list.size());
  }
  
  public int getItemCount() {
    return (this.list.size() < this.selectMax) ? (this.list.size() + 1) : this.list.size();
  }
  
  public int getItemViewType(int paramInt) {
    return isShowAddItem(paramInt) ? 1 : 2;
  }
  
  public void onBindViewHolder(final ViewHolder viewHolder, int paramInt) {
    String str;
    if (getItemViewType(paramInt) == 1) {
      viewHolder.mImg.setImageResource(R.drawable.icon_add);
      viewHolder.mImg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View param1View) {
              GridImageAdapter.this.mOnAddPicClickListener.onAddPicClick();
            }
          });
      viewHolder.ll_del.setVisibility(View.INVISIBLE);
      return;
    } 
    viewHolder.ll_del.setVisibility(View.VISIBLE);
    viewHolder.ll_del.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            int i = viewHolder.getAdapterPosition();
            if (i != -1) {
              GridImageAdapter.this.list.remove(i);
              GridImageAdapter.this.notifyItemRemoved(i);
              GridImageAdapter.this.notifyItemRangeChanged(i, GridImageAdapter.this.list.size());
              Log.i("delete position:", i + "--->remove after:" + GridImageAdapter.this.list.size());
            } 
          }
        });
    LocalMedia localMedia = this.list.get(paramInt);
    int i = localMedia.getMimeType();
    if (localMedia.isCut() && !localMedia.isCompressed()) {
      str = localMedia.getCutPath();
    } else if (localMedia.isCompressed() || (localMedia.isCut() && localMedia.isCompressed())) {
      str = localMedia.getCompressPath();
    } else {
      str = localMedia.getPath();
    } 
    if (localMedia.isCompressed()) {
      Log.i("compress image result:", ((new File(localMedia.getCompressPath())).length() / 1024L) + "k");
      Log.i("压缩地址::", localMedia.getCompressPath());
    } 
    Log.i("原图地址::", localMedia.getPath());
    paramInt = PictureMimeType.isPictureType(localMedia.getPictureType());
    if (localMedia.isCut())
      Log.i("裁剪地址::", localMedia.getCutPath()); 
    long l = localMedia.getDuration();
    TextView textView = viewHolder.tv_duration;
    if (paramInt == 2) {
      paramInt = 0;
    } else {
      paramInt = 8;
    } 
    textView.setVisibility(paramInt);
    if (i == PictureMimeType.ofAudio()) {
      viewHolder.tv_duration.setVisibility(View.VISIBLE);
      Drawable drawable = ContextCompat.getDrawable(this.context, R.drawable.icon_audio);
      StringUtils.modifyTextViewDrawable(viewHolder.tv_duration, drawable, 0);
    } else {
      Drawable drawable = ContextCompat.getDrawable(this.context, R.drawable.icon_add);
      StringUtils.modifyTextViewDrawable(viewHolder.tv_duration, drawable, 0);
    } 
    viewHolder.tv_duration.setText(DateUtils.timeParse(l));
    if (i == PictureMimeType.ofAudio()) {
      viewHolder.mImg.setImageResource(R.drawable.icon_audio);
    } else {
      RequestOptions requestOptions = (new RequestOptions()).centerCrop().placeholder(R.drawable.icon_add).diskCacheStrategy(DiskCacheStrategy.ALL);
      Glide.with(viewHolder.itemView.getContext()).load(str).apply(requestOptions).into(viewHolder.mImg);
    } 
    if (this.mItemClickListener != null)
      viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View param1View) {
              int i = viewHolder.getAdapterPosition();
              GridImageAdapter.this.mItemClickListener.onItemClick(i, param1View);
            }
          }); 
  }
  
  public ViewHolder onCreateViewHolder(ViewGroup paramViewGroup, int paramInt) {
    return new ViewHolder(this.mInflater.inflate(R.layout.item_filter_image, paramViewGroup, false));
  }
  
  public void setList(List<LocalMedia> paramList) {
    this.list = paramList;
  }
  
  public void setOnItemClickListener(OnItemClickListener paramOnItemClickListener) {
    this.mItemClickListener = paramOnItemClickListener;
  }
  
  public void setSelectMax(int paramInt) {
    this.selectMax = paramInt;
  }
  
  public static interface OnItemClickListener {
    void onItemClick(int param1Int, View param1View);
  }
  
  public class ViewHolder extends RecyclerView.ViewHolder {
    LinearLayout ll_del;
    
    ImageView mImg;
    
    TextView tv_duration;
    
    ViewHolder(View param1View) {
      super(param1View);
      this.mImg = (ImageView)param1View.findViewById(R.id.fiv);
      this.ll_del = (LinearLayout)param1View.findViewById(R.id.ll_del);
      this.tv_duration = (TextView)param1View.findViewById(R.id.tv_duration);
    }
  }
  
  public static interface onAddPicClickListener {
    void onAddPicClick();
  }
}

