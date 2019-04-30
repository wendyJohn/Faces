package com.example.juicekaaa.fireserver.fid.tool;


import com.example.juicekaaa.fireserver.fid.dao.Reader;

public class ReaderUtil {
	public static int connectCount = 0;
	/**
	 * 限制连接设备数量
	 */
	public final static int MAX_DEVICE_NUM = 50;
	
	public static Reader readers = null;

	public static Reader Doorreaders = null;
}
