package com.gc.nfc.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;
import com.gc.nfc.databinding.ActivityQianziBinding;
import java.io.IOException;

public class QianZiActivity extends AppCompatActivity {
  ActivityQianziBinding binding;
  
  public void onCreate(@Nullable Bundle paramBundle) {
    super.onCreate(paramBundle);
    this.binding = ActivityQianziBinding.inflate(getLayoutInflater());
    setContentView(this.binding.getRoot());
    final boolean isCrop = getIntent().getBooleanExtra("isCrop", false);
    this.binding.clear.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            QianZiActivity.this.binding.view.clear();
          }
        });
    this.binding.save.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            if (QianZiActivity.this.binding.view.isSign()) {
              try {
                if (isCrop) {
                  QianZiActivity.this.binding.view.save(PicSignActivity.path1, true, 10, true);
                  QianZiActivity.this.setResult(101);
                  QianZiActivity.this.finish();
                  return;
                } 
                QianZiActivity.this.binding.view.save(PicSignActivity.path);
                QianZiActivity.this.setResult(100);
                QianZiActivity.this.finish();
              } catch (Exception iOException) {
                iOException.printStackTrace();
              } 
              return;
            } 
            Toast.makeText((Context)QianZiActivity.this, "还没有签名！", Toast.LENGTH_SHORT).show();
          }
        });
  }
}

