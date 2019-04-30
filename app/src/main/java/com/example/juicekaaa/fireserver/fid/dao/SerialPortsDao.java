package com.example.juicekaaa.fireserver.fid.dao;

import android_serialport_api.SerialPortDevice;

import java.util.List;


public interface SerialPortsDao {

	List<String> findSerialPorts();

	SerialPortDevice open(String port, int baudrate);

	boolean send(SerialPortDevice serialPorts, byte[] data);

	byte[] read(SerialPortDevice serialPorts);

	void close(SerialPortDevice serialPorts);
}
