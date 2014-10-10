package smarthouse1.pkg0;

import java.io.IOException;
import java.util.*;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PackageReader implements Runnable {

  private final List<Package> incomingPacks;
  private final InputStream inStream;

  public PackageReader(List incomingPacks, InputStream inStream) {
    this.incomingPacks = incomingPacks;
    this.inStream = inStream;
  }

  PackageReader() {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public void run() {
    Package tempPack = null;
    int state = 0, byteCount = 0;
    char b;
    while (true) {
      synchronized (inStream) {
        try {
          if (inStream.available() > 0) {
            while (inStream.available() > 0) {
              if (state == 0) { // syncronize
                //System.out.println("Syncing");
                b = (char) inStream.read();
                //System.out.print(b + " ");
                if (b == 'S') {
                  b = (char) inStream.read();
                  //System.out.print(b + " ");
                  if (b == 'Y') {
                    b = (char) inStream.read();
                    //System.out.print(b + " ");
                    if (b == 'N') {
                      b = (char) inStream.read();
                      //System.out.println(b + " ");
                      if (b == 'C') {
                        state = 1;
                        byteCount = 0;
                        tempPack = new Package();
                      }
                    }
                  }
                }
              } else if (state == 1) { // construct packet
                //System.out.println("Synced. Constructing pack");
                if (tempPack != null) {
                  int d = inStream.read();
                  //System.out.println(String.format("0x%8s", Integer.toHexString(d)).replace(' ', '0') + " ");

                  switch (byteCount) {
                    case 0:
                      byteCount++;
                      tempPack.setPackSize(d);
                      break;
                    case 1:
                      byteCount++;
                      tempPack.setDeviceId(d);
                      break;
                    case 2:
                      byteCount++;
                      tempPack.setHouseId(d);
                      break;
                    case 3:
                      byteCount++;
                      tempPack.setLocalId(d);
                      break;
                    default:
                      tempPack.getData().add(d);
                      if (byteCount == tempPack.getPackSize()) {
                        state = 2;
                      }
                      byteCount++;
                      break;
                  }
                }
              } else if (state == 2) { //add packet into incomings
                //System.out.println("Checking for the last bytes");
                //check if the received packet is good
                if (tempPack != null) {
                  // packet's data size must be bigger than 0
                  // because each packet should have 'E' 'N' 'D' char-sequence 
                  // at the end of it
                  int dataSize = tempPack.getData().size();
                  if (dataSize > 2) {
                    // get last 3 bytes
                    int d = (Integer) tempPack.getData().get(dataSize - 1);
                    int n = (Integer) tempPack.getData().get(dataSize - 2);
                    int e = (Integer) tempPack.getData().get(dataSize - 3);
                    // check for correctness of the packet
                    if (e == (int) 'E' && n == (int) 'N' && d == (int) 'D') {

                      System.out.println("Packet recieved");
                      // remove END sequence
                      tempPack.getData().remove(dataSize - 1);
                      tempPack.getData().remove(dataSize - 2);
                      tempPack.getData().remove(dataSize - 3);

                      // decrease packet size by 3
                      tempPack.setPackSize(tempPack.getPackSize() - 3);
                      synchronized (incomingPacks) {
                        if (!incomingPacks.add(tempPack)) {
                          System.out.println("Error: failed to add packet into incomings.");
                        } else {
                          state = 0;
                        }
                      }
                    } else {
                      state = 0;
                      System.out.println("Warn: not good packet. END sequence did not match.");
                    }
                  } else {
                    state = 0;
                    System.out.println("Warn: not good packet. Packet size less than 3.");
                  }
                }
              }
            }
          }
        } catch (IOException ex) {
          Logger.getLogger(PackageReader.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
    }
  }
}
