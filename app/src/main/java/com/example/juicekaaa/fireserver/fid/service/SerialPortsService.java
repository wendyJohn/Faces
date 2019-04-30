package com.example.juicekaaa.fireserver.fid.service;

import android_serialport_api.SerialPortDevice;

import java.util.List;


public interface SerialPortsService {
	public List<String> findSerialPorts();

	public SerialPortDevice open(String port, int baudrate);

	public void close(SerialPortDevice serialPorts);

	public boolean send(SerialPortDevice serialPorts, byte[] data);

	public byte[] read(SerialPortDevice serialPorts);
}
