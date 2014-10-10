/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package smarthouse1.pkg0;

/**
 *
 * @author nursultan.kabylkas
 */
public class Sensor {
    protected int device_id;
    protected int local_id;
    protected int house_id;
    protected SensorComm comm;
    
    public Sensor(int house_id, int device_id, int local_id, SensorComm comm){
        this.house_id = house_id;
        this.device_id = device_id;
        this.local_id = local_id;
        this.comm = comm;
    }
    
    public int getDeviceId() {
        return this.device_id;
    }
    
    public int getHouseId() {
        return this.house_id;
    }
    
    public int getLocalId() {
        return this.local_id;
    }
}
