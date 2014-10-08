package smarthouse1.pkg0;

import java.util.*;


public class Package {
  private int packSize;
  private int deviceId;
  private int houseId;
  private int localId;
  private List<Integer> data;
  
  public Package() {
    packSize = 0;
    houseId = 0;
    deviceId = 0;
    data = new LinkedList();
  }
  
  public Package(int packSize, int houseId, int deviceId, int localId, List<Integer> data) {
    this.packSize = packSize;
    this.houseId = houseId;
    this.deviceId = deviceId;
    this.localId = localId;
    this.data = data;
  }

  public int getHouseId() {
    return houseId;
  }
  
  public int getDeviceId() {
    return deviceId;
  }

  public List getData() {
    return data;
  }

  public int getPackSize() {
    return packSize;
  }
  
  public int getLocalId() {
    return localId;
  }
  
  public void setHouseId(int houseId) {
    this.houseId = houseId;
  }
  
  public void setDeviceId(int deviceId) {
    this.deviceId = deviceId;
  }

  public void setData(List data) {
    this.data = data;
  }
  
  public void setPackSize(int packSize) {
    this.packSize = packSize;
  }
  
  public void setLocalId(int localId) {
    this.localId = localId;
  }
  
  @Override
  public String toString() {
    return "[" + packSize + " " + deviceId + " " + houseId  + " " + localId  + " " + data.toString() + "]";    
  }
}