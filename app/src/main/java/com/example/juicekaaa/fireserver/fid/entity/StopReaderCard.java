package com.example.juicekaaa.fireserver.fid.entity;


import com.example.juicekaaa.fireserver.fid.dao.Reader;
import com.example.juicekaaa.fireserver.fid.service.CallBackStopReadCard;

public class StopReaderCard implements Runnable {
	Reader reader = null;

	CallBackStopReadCard callBack = null;

	public StopReaderCard() {

	}

	public StopReaderCard(Reader reader, CallBackStopReadCard callBack) {
		this.reader = reader;
		this.callBack = callBack;
	}

	@Override
	public void run() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		callBack.stopReadCard(reader.stopRead);
	}
}
