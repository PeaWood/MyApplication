package com.gc.nfc.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.gc.nfc.R;
import com.gc.nfc.utils.FullyGridLayoutManager;
import com.gc.nfc.utils.GridImageAdapter;
//import com.gc.nfc.utils.NetUtil;
import com.gc.nfc.utils.Tools;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.permissions.Permission;
import com.luck.picture.lib.permissions.RxPermissions;
import io.reactivex.functions.Consumer;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class PicSelActivity extends AppCompatActivity implements View.OnClickListener {
  public static String path2;
  
  private GridImageAdapter adapter;
  
  private RecyclerView mRecyclerView;
  
  private int m_MaxPicCount;
  
  private Button m_buttonUpload;
  
  private String m_fileFolder;
  
  private String m_fileNameHeader;
  
  private int maxSelectNum = 9;
  
  private GridImageAdapter.onAddPicClickListener onAddPicClickListener = new GridImageAdapter.onAddPicClickListener() {
      @SuppressLint({"CheckResult"})
      public void onAddPicClick() {
        (new RxPermissions((Activity)PicSelActivity.this)).requestEach(new String[] { "android.permission.WRITE_EXTERNAL_STORAGE" }).subscribe(new Consumer<Permission>() {
              public void accept(Permission param2Permission) {
                if (param2Permission.granted) {
                  PicSelActivity.this.showPop();
                  return;
                } 
                Toast.makeText((Context)PicSelActivity.this, "拒绝", Toast.LENGTH_SHORT).show();
              }
            });
      }
    };
  
  private PopupWindow pop;
  
  private List<LocalMedia> selectList = new ArrayList<LocalMedia>();
  
  private void initWidget() {
    FullyGridLayoutManager fullyGridLayoutManager = new FullyGridLayoutManager((Context)this, 3, 1, false);
    this.mRecyclerView.setLayoutManager((RecyclerView.LayoutManager)fullyGridLayoutManager);
    this.adapter = new GridImageAdapter((Context)this, this.onAddPicClickListener);
    this.adapter.setList(this.selectList);
    this.adapter.setSelectMax(this.maxSelectNum);
    this.mRecyclerView.setAdapter((RecyclerView.Adapter)this.adapter);
    this.adapter.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
          public void onItemClick(int param1Int, View param1View) {
            if (PicSelActivity.this.selectList.size() > 0) {
              LocalMedia localMedia = PicSelActivity.this.selectList.get(param1Int);
              switch (PictureMimeType.pictureToVideo(localMedia.getPictureType())) {
                default:
                  return;
                case 1:
                  PictureSelector.create((Activity)PicSelActivity.this).externalPicturePreview(param1Int, PicSelActivity.this.selectList);
                case 2:
                  PictureSelector.create((Activity)PicSelActivity.this).externalPictureVideo(localMedia.getPath());
                case 3:
                  break;
              } 
              PictureSelector.create((Activity)PicSelActivity.this).externalPictureAudio(localMedia.getPath());
            } 
          }
        });
  }
  
  private void showAlbum() {
    PictureSelector.create((Activity)this).openGallery(PictureMimeType.ofImage()).maxSelectNum(this.maxSelectNum).minSelectNum(1).imageSpanCount(4).selectionMode(2).previewImage(true).isCamera(true).isZoomAnim(true).enableCrop(true).compress(true).glideOverride(160, 160).withAspectRatio(1, 1).rotateEnabled(false).forResult(188);
  }
  
  private void showPop() {
    View view = View.inflate((Context)this, R.layout.layout_bottom_dialog, null);
    TextView textView1 = (TextView)view.findViewById(R.id.tv_album);
    TextView textView2 = (TextView)view.findViewById(R.id.tv_camera);
    TextView textView3 = (TextView)view.findViewById(R.id.tv_cancel);
    this.pop = new PopupWindow(view, -1, -2);
    this.pop.setBackgroundDrawable((Drawable)new ColorDrawable(0));
    this.pop.setOutsideTouchable(true);
    this.pop.setFocusable(true);
    WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
    layoutParams.alpha = 0.5F;
    getWindow().setAttributes(layoutParams);
    this.pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
          public void onDismiss() {
            WindowManager.LayoutParams layoutParams = PicSelActivity.this.getWindow().getAttributes();
            layoutParams.alpha = 1.0F;
            PicSelActivity.this.getWindow().setAttributes(layoutParams);
          }
        });
    this.pop.setAnimationStyle(2131689834);
    this.pop.showAtLocation(getWindow().getDecorView(), 80, 0, 0);
    View.OnClickListener onClickListener = new View.OnClickListener() {
        public void onClick(View param1View) {
          switch (param1View.getId()) {
            default:
              PicSelActivity.this.closePopupWindow();
              return;
            case 2131231124:
              PictureSelector.create((Activity)PicSelActivity.this).openGallery(PictureMimeType.ofImage()).maxSelectNum(PicSelActivity.this.maxSelectNum).minSelectNum(1).imageSpanCount(4).selectionMode(2).forResult(188);
            case 2131231125:
              break;
          } 
          PictureSelector.create((Activity)PicSelActivity.this).openCamera(PictureMimeType.ofImage()).forResult(188);
        }
      };
    textView1.setOnClickListener(onClickListener);
    textView2.setOnClickListener(onClickListener);
    textView3.setOnClickListener(onClickListener);
  }
  
  public static boolean uploadFile(String[] paramArrayOfString, String paramString1, String paramString2) {
    String str = UUID.randomUUID().toString();
    try {
      URL uRL = new URL("");
      StringBuilder stringBuilder4 = new StringBuilder();
      stringBuilder4.append("http://www.gasmart.com.cn/api/Untils/file?fileFolder=").append(paramString1).append("&fileNameHeader=").append(paramString2).toString();
      HttpURLConnection httpURLConnection = (HttpURLConnection)uRL.openConnection();
//      if (NetUtil.m_loginCookies != null) {
//        StringBuilder stringBuilder = new StringBuilder();
//        httpURLConnection.setRequestProperty("Cookie", stringBuilder.append("JSESSIONID=").append(NetUtil.SESSIONID).toString());
//      }
      httpURLConnection.setReadTimeout(100000000);
      httpURLConnection.setConnectTimeout(100000000);
      httpURLConnection.setDoInput(true);
      httpURLConnection.setDoOutput(true);
      httpURLConnection.setUseCaches(false);
      httpURLConnection.setRequestMethod("PUT");
      httpURLConnection.setRequestProperty("Charset", "utf-8");
      httpURLConnection.setRequestProperty("connection", "keep-alive");
      StringBuilder stringBuilder2 = new StringBuilder();
      httpURLConnection.setRequestProperty("Content-Type", stringBuilder2.append("multipart/form-data").append(";boundary=").append(str).toString());
      OutputStream outputStream = httpURLConnection.getOutputStream();
      DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
      StringBuffer stringBuffer = new StringBuffer();
      stringBuffer.append("--");
      stringBuffer.append(str);
      stringBuffer.append("\r\n");
      dataOutputStream.write(stringBuffer.toString().getBytes());
      StringBuilder stringBuilder3 = new StringBuilder();
      dataOutputStream.writeBytes(stringBuilder3.append("Content-Disposition: form-data; name=\"").append("userName").append("\"").append("\r\n").toString());
      dataOutputStream.writeBytes("\r\n");
      dataOutputStream.writeBytes("zhangSan");
      dataOutputStream.writeBytes("\r\n");
      int i = 0;
      while (i < paramArrayOfString.length) {
        File file = new File(paramArrayOfString[i]);
        StringBuffer stringBuffer1 = new StringBuffer();
        stringBuffer1.append("--");
        stringBuffer1.append(str);
        stringBuffer1.append("\r\n");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuffer1.append(stringBuilder.append("Content-Disposition: form-data; name=\"").append(i).append("\"; filename=\"").append(file.getName()).append("\"").append("\r\n").toString());
        stringBuilder = new StringBuilder();
        stringBuffer1.append(stringBuilder.append("Content-Type: application/octet-stream; charset=").append("utf-8").append("\r\n").toString());
        stringBuffer1.append("\r\n");
        dataOutputStream.write(stringBuffer1.toString().getBytes());
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] arrayOfByte = new byte[5242880];
        while (true) {
          int j = fileInputStream.read(arrayOfByte);
          if (j != -1) {
            dataOutputStream.write(arrayOfByte, 0, j);
            continue;
          } 
          fileInputStream.close();
          dataOutputStream.write("\r\n".getBytes());
          i++;
        } 
      } 
      StringBuilder stringBuilder1 = new StringBuilder();
      dataOutputStream.write(stringBuilder1.append("--").append(str).append("--").append("\r\n").toString().getBytes());
      dataOutputStream.flush();
      i = httpURLConnection.getResponseCode();
      if (i == 200)
        return true; 
    } catch (MalformedURLException malformedURLException) {
      malformedURLException.printStackTrace();
    } catch (IOException iOException) {
      iOException.printStackTrace();
    } 
    return false;
  }
  
  public void closePopupWindow() {
    if (this.pop != null && this.pop.isShowing()) {
      this.pop.dismiss();
      this.pop = null;
    } 
  }
  
  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent) {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    if (paramInt2 == -1 && paramInt1 == 188) {
      List<? extends LocalMedia> list = PictureSelector.obtainMultipleResult(paramIntent);
      this.selectList.addAll(list);
      this.adapter.setList(this.selectList);
      this.adapter.notifyDataSetChanged();
    } 
  }
  
  public void onClick(View paramView) {
    switch (paramView.getId()) {
      default:
        return;
      case -1:
        break;
    } 
    if (this.selectList.size() > this.m_MaxPicCount)
      Toast.makeText((Context)this, "提交照片超数量，最多" + this.m_MaxPicCount + "张", Toast.LENGTH_SHORT).show();
    TextView textView = (TextView)findViewById(R.id.textview_picUploadResult);
    textView.setText("上传中");
    if (Tools.isFastClick()) {
      Toast.makeText((Context)this, "正在上传，请稍后", Toast.LENGTH_SHORT).show();
      String[] arrayOfString = new String[this.selectList.size()];
      byte b = 0;
      Iterator<LocalMedia> iterator = this.selectList.iterator();
      while (iterator.hasNext()) {
        arrayOfString[b] = ((LocalMedia)iterator.next()).getPath();
        b++;
      } 
      if (uploadFile(arrayOfString, this.m_fileFolder, this.m_fileNameHeader)) {
        textView.setText("上传成功");
        Toast.makeText((Context)this, "上传成功", Toast.LENGTH_SHORT).show();
      } 
      textView.setText("上传失败，请重新上传");
      Toast.makeText((Context)this, "上传失败，请重新上传", Toast.LENGTH_SHORT).show();
    } 
  }
  
  protected void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    setContentView(R.layout.activity_pic_sel);
    this.mRecyclerView = (RecyclerView)findViewById(R.id.recycler);
    new Bundle();
    paramBundle = getIntent().getExtras();
    this.m_fileFolder = paramBundle.getString("fileFolder");
    this.m_fileNameHeader = paramBundle.getString("fileNameHeader");
    this.m_MaxPicCount = paramBundle.getInt("MaxPicCount");
    this.m_buttonUpload = (Button)findViewById(R.id.button_upload);
    this.m_buttonUpload.setOnClickListener(this);
    initWidget();
  }
}