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
    private Sensor sender_sensor;
    private int receiever_id;
    private Package pack_to_send;
    
    private Package constructPack(List<Integer> payload) {
        int house_id = this.sender_sensor.getHouseId();
        int device_id = this.sender_sensor.getDeviceId();
        return new Package(3+payload.size(), house_id, device_id, this.receiever_id, payload);
    }
    
    public SensorComm (Sensor sender_sensor, int receiever_id, List<Integer> payload){
        this.sender_sensor = sender_sensor;
        this.receiever_id = receiever_id;
        this.pack_to_send = this.constructPack(payload);
    }
    
    public Package getPackToSend() {
        return this.pack_to_send;
    }
    
}
