package com.gc.nfc.sca;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.os.Build;
import android.os.Handler;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WeightScale  {
	private Context context;
	public int connectState=0;
	private Handler handler = new Handler();
    public  ArrayList<ScaleDevice> mScaleDevice;
    public  List<String>  mScanaddr;
	private BluetoothAdapter bluetoothAdapter;
    private BluetoothDevice device;
	private BluetoothGatt bluetoothGatt;
	private BluetoothGattCharacteristic outCharacteristic;
	private	List<Long> ltimes ;
	private float    scalevalue=0f;
	private float    scalesumvalue=0f;
	private byte senddate=0x00;
	private boolean  isBLEWriteWithResponse=false;
	private Long utime;
	private static long scan_PERIOD=10000;
	public boolean mScanning=false;
	private ScanDevice scanDevice=new ScanDevice();
	private boolean scanupdate=false;
	public BLE_item ble_item = new BLE_item();

	public static enum BTN_TYPE
	{
		BTN_ZERO,
		BTN_CALIIB,
	};
	
	public WeightScale(Context context)
	{
		this.context=context;
		bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		mScaleDevice=new ArrayList<ScaleDevice>();
		ltimes = new ArrayList();
		utime=System.currentTimeMillis();
		handler.post(task);
	}
	
	private Runnable task =new Runnable() {
	       public void run() {    
	             handler.postDelayed(this,100);
	             if(scanupdate==true)
	             {
	            	 if(bytestoAnalysis(scanDevice.scanRecord)==true)
	            	 {
	            		 ScaleDevice scaleDevice=new ScaleDevice();	                			
	            		 scaleDevice.devicename=scanDevice.devicename;
	            		 scaleDevice.deviceaddr=scanDevice.deviceaddr;
            			if(scaleDevice.devicename==null)
            			{
            				scaleDevice.devicename="������("+scaleDevice.deviceaddr.substring(12, 14)+scaleDevice.deviceaddr.substring(15, 17)+")";
            			}
	            		scaleDevice.scalevalue=scalevalue;
	            		scaleDevice.sumvalue=scalesumvalue;
            			if(mScaleDevice.isEmpty()!=true)
            			{
                			for(int i=0;i<mScaleDevice.size();i++)
                			{
                				if(mScaleDevice.get(i).deviceaddr.equals(scanDevice.deviceaddr)==true)
                				{
                					mScaleDevice.set(i,scaleDevice);
                					ltimes.set(i,utime);
                					break;
                				}
                				if(i==mScaleDevice.size()-1)
                				{
                					mScaleDevice.add(scaleDevice);
                					ltimes.add(utime);
                				}
                			}	
            			}
            			else
        				{
            				mScaleDevice.add(scaleDevice);	
            				ltimes.add(utime);  
        				}
	            	 }
	            	 scanupdate=false;
	             }	                  
	       }
	};
	
	public boolean bluetoothIsEnabled()
	{
		if(bluetoothAdapter==null)return false;
		if(!bluetoothAdapter.isEnabled())return false;
		return true;
	}
	
	public ArrayList<ScaleDevice> m_ScaleDevice()
	{
		return mScaleDevice;
	}
	public void scanDevice(boolean enable) {
		if(connectState==0)
		{
			if(Build.VERSION.RELEASE=="4.3")
			{
				scan_PERIOD=200;
			}
	        if (enable) {
	        	new Handler().postDelayed(new Runnable(){
	        		@Override
	        		public void run()
	        		{
	        			mScanning=false;
	        			bluetoothAdapter.stopLeScan(mLeScanCallback);
	        		}
	        	}, scan_PERIOD);
	        	mScanning=true;
    			bluetoothAdapter.startLeScan(mLeScanCallback);        	
	        } else {
	        	mScanning=false;
	            bluetoothAdapter.stopLeScan(mLeScanCallback);
	        }
		}
	}

	public void updataDevicelist()
	{
		if((mScaleDevice.isEmpty()==false)&&(connectState==0))
		{
			for(int i=0;i<mScaleDevice.size();i++)
			{
				if((System.currentTimeMillis()-ltimes.get(i))>3000)
				{
					mScaleDevice.remove(i);
					ltimes.remove(i);
				}
			}
		}
	}
	

	private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback()
	{  
	       @Override  
	       public void onLeScan(final BluetoothDevice device, final int rssi, final byte[] scanRecord) {
	    	   
	    	   for(int i=0;i<scanRecord.length;i++)
	    	   {
	    		   if(scanRecord[i]==15)
	    		   {
	    			   if((i+5)<scanRecord.length)
	    			   {
	    				   if(scanRecord[i+1]==22)
	    				   {
	    					   if((scanRecord[i+2]==29)&&(scanRecord[i+3]==24))
	    					   {
	    						   if((scanRecord[i+4]==-6)&&(scanRecord[i+5]==-5))
	    						   {
	    							      scanDevice.deviceaddr=device.getAddress();               			
	    						    	  scanDevice.devicename=device.getName();
	    						    	  scanDevice.scanRecord=scanRecord;
	    						    	  utime=System.currentTimeMillis();
	    						    	  scanupdate=true;
	    						   }
	    					   }
	    				   }
	    			   }
	    		   }   
	    	   }
	       }	    
	     
	};
	
	    
	public boolean  connectDevice(ScaleDevice scaledevice, BTN_TYPE type)
	{	
	 	if(connectState==0)
	 	{		 	
	 		connectState=1;
			bluetoothAdapter.stopLeScan(null);
			device=bluetoothAdapter.getRemoteDevice(scaledevice.deviceaddr);
			bluetoothGatt=device.connectGatt(context, false, mGattCallback);
			if(bluetoothGatt==null) 
			{
				connectState=0;
				return false;			
			}
	        for(int i=0;i<800;i++)
	        {
				if(connectState==2)break;
				if(connectState==0)break;
		        try {Thread.sleep(10);} catch (InterruptedException e) {  } 
	        }
	        if(connectState!=2)
	        {
	        	close();
	        	connectState=0;
	        	return false;
	        }
			if(type==BTN_TYPE.BTN_ZERO) senddate=(byte) 0xc0;
			if(type==BTN_TYPE.BTN_CALIIB)senddate=(byte) 0xEC;	
			for(int i=0;i<100;i++)
			{
				if(ble_item.write_characteristic!=null) break;
				try {Thread.sleep(10);} catch (InterruptedException e) {} 
			}
			try {Thread.sleep(500);} catch (InterruptedException e) {} 
			isBLEWriteWithResponse=false;
			if(write(new byte[]{(senddate)})==0) {
				connectState=0;
				close();
				return false; } 
			connectState=0;
			close();
			return true;
	 	}
		return false;
	}
	
	
    public void close()
    {
        if (bluetoothGatt == null) {return;}
        try {Thread.sleep(100);} catch (InterruptedException e) {}     
        ble_item.write_characteristic=null;
        bluetoothGatt.disconnect();
        try {Thread.sleep(100);} catch (InterruptedException e) {} 
        bluetoothGatt.close();
    }

	private final BluetoothGattCallback mGattCallback = new BluetoothGattCallback() 
    {
        //�������״̬�仯  
    	@Override  
    	public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {  
            if (newState == BluetoothProfile.STATE_CONNECTED)
            {
            	 gatt.discoverServices();
            }
            else if (newState == BluetoothProfile.STATE_DISCONNECTED){
            	connectState=0;
            }
        } 
    	
        public void onServicesDiscovered(BluetoothGatt gatt, int status) 
        {
        	if (status == BluetoothGatt.GATT_SUCCESS)
        	{
        		setServiceUUID(getSupportedGattServices());
                connectState=2;
        	}
        }
        //����û�������д���ݵ�״̬  
        @Override  
        public void onCharacteristicWrite(BluetoothGatt gatt,  BluetoothGattCharacteristic characteristic, int status) 
        {
        	isBLEWriteWithResponse=true;
        }
    };
    
    public List<BluetoothGattService> getSupportedGattServices() {
        if (bluetoothGatt == null) return null;

        return bluetoothGatt.getServices();
    }
    public void setServiceUUID(List<BluetoothGattService> services){
        for (BluetoothGattService service : services){
            ble_item.addService(service);
        }
        for (BluetoothGattService service : services) {
            for (BluetoothGattCharacteristic characteristic : service.getCharacteristics()) {
                final int charaProp = characteristic.getProperties();
                if ((charaProp | BluetoothGattCharacteristic.PROPERTY_READ) > 0) {

                }
                if ((charaProp | BluetoothGattCharacteristic.PROPERTY_NOTIFY) > 0) {
                    setCharacteristicNotification(characteristic, true);
                }
                if ((charaProp | BluetoothGattCharacteristic.PROPERTY_WRITE_NO_RESPONSE) > 0) {
                    if (ble_item.write_characteristic_NoRe == null) {
                        ble_item.write_characteristic_NoRe = characteristic;
                    }
                }
                if ((charaProp | BluetoothGattCharacteristic.PROPERTY_WRITE) > 0) {
                    if (ble_item.write_characteristic == null) {
                        ble_item.write_characteristic = characteristic;
                    }
                }
            }
        }
    }
    
    class BLE_item{
        public ArrayList<String> arr_serviceUUID = new ArrayList<String>();
        public ArrayList<BluetoothGattService> arr_services = new ArrayList<BluetoothGattService>();
        public BluetoothGattCharacteristic write_characteristic;
        public BluetoothGattCharacteristic write_characteristic_NoRe;
        public void addService(BluetoothGattService service){
            service.getCharacteristics();
            arr_services.add(service);
            String str_uuid = service.getUuid().toString();
            arr_serviceUUID.add(str_uuid.substring(4, 8));
            ArrayList list = new ArrayList();
            for (BluetoothGattCharacteristic characteristic : service.getCharacteristics()){
                String str_c_uuid = characteristic.getUuid().toString();
                str_c_uuid = str_c_uuid.substring(4,8);
                list.add(str_c_uuid);
                if (str_c_uuid.toLowerCase().contains("fff1")){
                    setCharacteristicNotification(characteristic, true);
                }
                if (str_c_uuid.toLowerCase().contains("fff2")){
                    write_characteristic = characteristic;
                }
            }
        }
    }
    
    public void setCharacteristicNotification(BluetoothGattCharacteristic characteristic,
            boolean enabled) {
		if (bluetoothAdapter == null || bluetoothGatt == null) {
		return;
		}
		bluetoothGatt.setCharacteristicNotification(characteristic, enabled);
		BluetoothGattDescriptor descriptor = characteristic.getDescriptor(
		UUID.fromString("00002902-0000-1000-8000-00805f9b34fb"));
		//�����޸�
		if (descriptor != null) {
		descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
		bluetoothGatt.writeDescriptor(descriptor);}
     }
    
    public int write(final byte b[]){
        if (ble_item.write_characteristic == null) return 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            ble_item.write_characteristic.setValue(b);
            return writeCharacteristic(ble_item.write_characteristic) ? 1 : 0;

        }
        return 0;
    }
    public boolean writeCharacteristic(BluetoothGattCharacteristic characteristic) {
        if (bluetoothAdapter == null || bluetoothGatt == null) {
            return false;
        }
       
        return bluetoothGatt.writeCharacteristic(characteristic);
    }
    private boolean bytestoAnalysis(byte[] bytes) {
    	byte[] AnChars = new byte[12];
    	for(int i=0;i<bytes.length;i++)
    	{
    		if(bytes[i]==-6){
    			if((bytes.length-i)>=9&&bytes[i+1]==-5)
    			{
    				for(int j=0;j<12;j++)  AnChars[j]=bytes[i+j];
    				i=bytes.length-1;
    			    break;
    			}
    		}
    		if(i==bytes.length-1) return false;
    	}
    	byte xor8;
    	xor8=AnChars[11];
    	if(sum8xor(AnChars,11)==xor8)
    	{
    		scalevalue=ArryToFloat(AnChars,3); 
    		scalesumvalue=ArryToFloat(AnChars,7);
   	    }
    	else return false;
    	return true; 
    }
    
	static float ArryToFloat(byte[] Array,int Pos)     
	{   
	    int accum = 0;   
	    accum = Array[Pos+0] & 0xFF;  
	    accum |= (long)(Array[Pos+1] & 0xFF)<<8;  
	    accum |= (long)(Array[Pos+2] & 0xFF)<<16;  
	    accum |= (long)(Array[Pos+3] & 0xFF)<<24;  
	    return Float.intBitsToFloat(accum);   
	} 
	
	private byte sum8xor(byte[] data,int len)
	{
		int fcs=0;
		int sc;
		for(int i=0;i<len;i++)
			{
			sc=data[i]&0xff;
			fcs+=sc;
			}
		fcs=fcs^0xFF;
		return (byte)fcs;
	}
	}

    