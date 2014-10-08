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
    packageHandler ph = new packageHandler("COM3");

    ph.startReading();
    ph.startSending();

    List<Integer> tempData = new LinkedList<>();
    Package tempPack = new Package(3, 0x22, 0x11, 0xAA, tempData);;
    System.out.print("Enter the stream of commands: ");
    while (true) {
        boolean send = true;
        
        try {
        char inChar = (char) System.in.read();
        if (inChar == '1') {
          //construct packet 1
          tempData = new LinkedList<>();
          tempPack = new Package(3, 0x22, 0x11, 0xAA, tempData);
        } else if (inChar == '2') {
          //construct packet 2
          tempData = new LinkedList<>();
          tempPack = new Package(3, 0x22, 0x11, 0x55, tempData);
        } else if (inChar == '3') {
          //construct packet 3
          tempData = new LinkedList<>();
          tempData.add(23);
          tempData.add(25);
          tempData.add(12);
          tempPack = new Package(3 + tempData.size(), 0x22, 0x11, 0xAA, tempData);
        } else {
          send = false;
        }
      } catch (IOException e) {
        System.out.println("Error reading from user");
      }
      if (send) {
        synchronized (ph.getOutPacks()) {
          ph.getOutPacks().add(tempPack);
        }
      }
      /*ph.outAllPacks();
       try {
       TimeUnit.SECONDS.sleep(2);
       } catch (InterruptedException e) {
       System.out.println(e.toString());
       }*/
    }
  }
}
