/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package smarthouse1.pkg0;

import java.util.List;

/**
 *
 * @author nursultan.kabylkas
 */
public class SensorComm {
    private Sensor tx_sensor;
    private Sensor rx_sensor;
    private Package pack_to_send;
    
    private Package constructPack(List<Integer> payload) {
        int house_id = this.tx_sensor.getHouseId();
        int device_id = this.rx_sensor.getDeviceId();
        return new Package(3+payload.size(), house_id, device_id, this.rx_sensor.getLocalId(), payload);
    }
    
    public SensorComm (Sensor tx_sensor, Sensor rx_sensor, List<Integer> payload){
        this.tx_sensor = tx_sensor;
        this.rx_sensor = rx_sensor;
        this.pack_to_send = this.constructPack(payload);
    }
    
    public Package getPackToSend() {
        return this.pack_to_send;
    }
    
}
