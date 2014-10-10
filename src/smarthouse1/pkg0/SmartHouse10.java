/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smarthouse1.pkg0;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author nursultan.kabylkas
 */
public class SmartHouse10 {
  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    // create packet handler
    packageHandler ph = new packageHandler("COM3");
    ph.startReading();
    ph.startSending();

    // create sensor handler
    SensorHandler sh = new SensorHandler(ph.getInPacks());
    
    // instantiate and add motion sensor to the system
    int houseId = 0x11;
    int deviceId = 0x4d;
    int localId = 0xDE;
    Sensor motionSensor = new BinarySensor(houseId, deviceId, localId);
    sh.addSensor(motionSensor);
    
    // instantiate and add relay switch to the system
    deviceId = 0x52;
    localId = 0xAD;
    Sensor relay = new BinarySensor(houseId, deviceId, localId);
    sh.addSensor(relay);
    
    // instantiate communication between the sensors
    SensorComm comm = new SensorComm();
  }
}
