/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package smarthouse1.pkg0;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author nursultan.kabylkas
 */
public class BinarySensor extends Sensor {
    public BinarySensor(int house_id, int device_id, int local_id) {
        super(house_id, device_id, local_id);
    }
    
    @Override
    public List<Integer> decide(List<Integer> data) {
      List<Integer> result = new LinkedList();
      // Binary sensors have only one byte of data 
      //(this might change as system grows)
      // extract the byte
      int payload = data.get(0);
      
      // according to protocol 0x54 is a trigger signal
      // if triggered send ON signal
      if (payload == 0x54) {
        result.add(0x4f);
      } else {
        result.add(0x00);
      }
      return result;
    }
}
