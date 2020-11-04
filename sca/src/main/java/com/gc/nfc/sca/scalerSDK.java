package com.gc.nfc.sca;

import java.util.ArrayList;

import android.content.Context;

public class scalerSDK {

	private Context _context;
	public scalerSDK(Context context)
	{
		this._context=context;
	}
	
	private WeightScale scale=new WeightScale(_context);
	
	public ArrayList<ScaleDevice> getDevicelist()
	{
		return  scale.m_ScaleDevice();
	}
	
	public boolean bleIsEnabled()
	{
	   return scale.bluetoothIsEnabled();
	}
	
	public void Scan(boolean enable)
	{
		if(enable==true)
		{
			if(scale.mScanning==false)
				scale.scanDevice(true);
		}
		else
		{
			scale.scanDevice(false);
		}
	}
	
	public int getState()
	{
		return scale.connectState;
	}
	
	public  void  bleSend(ScaleDevice scaledevice, byte type)
	{
		if(type==1)
			scale.connectDevice(scaledevice, WeightScale.BTN_TYPE.BTN_ZERO);
		if(type==2)
			scale.connectDevice(scaledevice, WeightScale.BTN_TYPE.BTN_CALIIB);
	}
	
	public void updatelist()
	{
		scale.updataDevicelist();
	}
}
