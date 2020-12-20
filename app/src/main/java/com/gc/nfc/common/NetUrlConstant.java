package com.gc.nfc.common;

public class NetUrlConstant {
	public static final String BASEURL = "http://www.gasmart.com.cn";//系统基地址
//	public static final String BASEURL = "https://www.yunnanbaijiang.com";//系统基地址
	public static final String LOGINURL = BASEURL+"/api/sysusers/login";//登录接口
	public static final String GASCYLINDERURL = BASEURL+"/api/GasCylinder";  //钢瓶接口
	public static final String GASCYNFACTORYURL = BASEURL+"/api/GasCynFactory";  //6.5.	钢瓶厂家查询
	public static final String SYSUSERKEEPALIVEURL = BASEURL+"/api/sysusers/KeepAlive";  //2.4.	心跳信息
}
