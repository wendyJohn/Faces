package com.example.juicekaaa.fireserver.fid.entity;


import com.example.juicekaaa.fireserver.fid.dao.Reader;
import com.example.juicekaaa.fireserver.fid.service.CallBack;

public class ReaderCard implements Runnable {
	Reader reader = null;

	CallBack callBack = null;

	public ReaderCard() {
	}

	public ReaderCard(Reader reader, CallBack callBack) {
		this.reader = reader;
		this.callBack = callBack;
	}

	@Override
	public void run() {
		reader.threadFunc(reader, callBack);
	}
}
