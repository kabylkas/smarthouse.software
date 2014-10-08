/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smarthouse1.pkg0;

import java.io.IOException;
import java.util.*;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PackageSender implements Runnable {

  private List<Package> outcomingPacks;
  private OutputStream outStream;

  public PackageSender(List outcomingPacks, OutputStream outStream) {
    this.outcomingPacks = outcomingPacks;
    this.outStream = outStream;
  }

  PackageSender() {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public void run() {
    while (true) {
      synchronized (outcomingPacks) {
        if (!outcomingPacks.isEmpty()) {
          //get the first packet in the list
          Package toSend = outcomingPacks.get(0);
          System.out.print("Sending packet: " + toSend);
          // send the data through serial
          try {
            outStream.write((byte) toSend.getPackSize());
            outStream.write((byte) toSend.getDeviceId());
            outStream.write((byte) toSend.getHouseId());
            outStream.write((byte) toSend.getLocalId());
            while (!toSend.getData().isEmpty()) {
              outStream.write((byte)((int)toSend.getData().get(0)));
              toSend.getData().remove(0);
            }
            outStream.write('E');
            outStream.write('N');
            outStream.write('D');
            outcomingPacks.remove(0);
            System.out.println("... Sent.");
          } catch (IOException e) {
            System.out.println(e.toString());
          }
        }
      }
    }
  }
}
