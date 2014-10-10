/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smarthouse1.pkg0;

import java.util.List;

/**
 *
 * @author nursultan.kabylkas
 */
public class SensorHandler implements Runnable {

  private final int houseId;
  private final List<Package> incomingPacks;
  private List<Sensor> allSensors;

  public SensorHandler(List<Package> incomingPacks, int houseId) {
    this.incomingPacks = incomingPacks;
    this.houseId = houseId;
  }

  public void addSensor(Sensor sensor) {
    allSensors.add(sensor);
  }

  @Override
  public void run() {
    while (true) {
      synchronized (incomingPacks) {
        if (incomingPacks.size() > 0) {
          // 1) read the packet
          Package current = incomingPacks.get(0);

          // 2) get the device info from the packet
          int houseId = current.getHouseId();
          int deviceId = current.getDeviceId();
          int localId = current.getLocalId();

          // 3) find the device in the list of sensors
          if (this.houseId == houseId) { // the house id must match
            // go through all sensors
            for (int i = 0; i < allSensors.size(); i++) { 
              int tempDeviceId = allSensors.get(i).getDeviceId();
              if (tempDeviceId == deviceId) { // if sensor type in the list
                int tempLocalId = allSensors.get(i).getLocalId();
                if (tempLocalId == localId) { // if the local id matches
                  
                }
              }
            }
          }
          incomingPacks.remove(0);
        }
      }
    }
  }
}
