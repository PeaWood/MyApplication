package com.gc.nfc.sca;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity implements OnItemClickListener,
InterClick {
	
	protected static final String TAG = null;
	private Handler handler = new Handler();
	private scalerSDK scale;
	private ListView textScanDevice;
	private TextView stateText;
	private ContentAdapter adapter;
	private ArrayList<Map<String, Object>> listData;
	private ArrayList<Map<String, Object>> listlastData;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		stateText =(TextView)findViewById(R.id.statetextview);
		textScanDevice=(ListView)findViewById(R.id.listView);
		listData = new ArrayList<Map<String, Object>>();
		listlastData = new ArrayList<Map<String, Object>>();
		adapter=new ContentAdapter(this, listData, this);
		textScanDevice.setAdapter(adapter);
		textScanDevice.setOnItemClickListener(this);
        scale=new scalerSDK(this);
		handler.post(task);
	}


	
	private Runnable task =new Runnable() {
	       public void run() {    
	             handler.postDelayed(this,150);
	             if(!scale.bleIsEnabled())
	             {
	            	 return;
	             }
	             scale.Scan(true);
                 if(scale.getState()==0)
	             {
               	 	stateText.setText("蓝牙秤闲  ");
	             }
	             else if(scale.getState()!=0)
	             {
	           	  stateText.setText("蓝牙秤忙  ");	
	             }
                 scale.updatelist();
	             if(scale.getDevicelist().isEmpty()!=true)
            	 {
            		listData.clear();
            		for(int i=0;i<scale.getDevicelist().size();i++)
            		{
            	        Map<String, Object> map = new HashMap<String, Object>();
            	        map.put("devicename",scale.getDevicelist().get(i).devicename);  
            	        map.put("value", String.format("%4.1f"+"kg"+"     "+"SUM:%4.1f"+"kg",scale.getDevicelist().get(i).scalevalue,scale.getDevicelist().get(i).sumvalue));
            			listData.add(map);
            		}  		
            	 }
            	 else
            	 {
            		 listData.clear(); 
            	 }
            
            	 if(listData.equals(listlastData)==false)
            		 	adapter.notifyDataSetChanged();
            	 listlastData.clear();
            	 for(int j=0;j<listData.size();j++)
            		 listlastData.add(listData.get(j));
	            }   
	    };
	    
	    @Override
		public boolean onCreateOptionsMenu(Menu menu) {
			// Inflate the menu; this adds items to the action bar if it is present.
			getMenuInflater().inflate(R.menu.main, menu);
			return true;
		}

		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			// Handle action bar item clicks here. The action bar will
			// automatically handle clicks on the Home/Up button, so long
			// as you specify a parent activity in AndroidManifest.xml.
			int id = item.getItemId();
			if (id == R.id.action_settings) {
				return true;
			}
			return super.onOptionsItemSelected(item);
		}  
	    @Override  
	    protected void onDestroy() 
	    {
	    	super.onDestroy();  
	    	System.exit(0);  
	    	//android.os.Process.killProcess(android.os.Process.myPid());   
	    }	
	
	@Override
	public void ZeroClick(int postion,View v) {	
		scale.bleSend(scale.getDevicelist().get(postion), (byte) 1);
	}


	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
	}	

}
