package smarthouse1.pkg0;

import gnu.io.*;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

public class packageHandler {

  private CommPortIdentifier portId;
  private SerialPort serialPort;
  private InputStream inStream;
  private OutputStream outStream;
  private List<Package> incomingPacks;
  private List<Package> outcomingPacks;

  public packageHandler(String portName) {
    incomingPacks = new LinkedList<>();
    outcomingPacks = new LinkedList<>();

    Enumeration portList = CommPortIdentifier.getPortIdentifiers();
    while (portList.hasMoreElements()) {
      portId = (CommPortIdentifier) portList.nextElement();
      System.out.println("Checking port "+portId.getName()+" ");
      if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
        if (portId.getName().equals(portName)) {
          try {
            // check if port is busy or owned
            // if (owned...)

            //open the port
            serialPort = (SerialPort) portId.open("ComControl", 2000);

            //get input and output streams
            inStream = serialPort.getInputStream();
            outStream = serialPort.getOutputStream();

            // set parameters
            serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

            // all set, good to exit
            break;
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      }
    }
    if (inStream == null) {
      System.out.println("Error:"+ portName+" port not found!");
      System.exit(0);
    }
  }

  public void startReading() {
    Thread reader = new Thread(new PackageReader(incomingPacks, inStream));
    reader.start();
  }

  public void startSending() {
    Thread sender = new Thread(new PackageSender(outcomingPacks, outStream));
    sender.start();
  }

  public List<Package> getOutPacks() {
    synchronized (this.outcomingPacks) {
      return this.outcomingPacks;
    }
  }

  public void outAllPacks() {
    synchronized (incomingPacks) {
      System.out.println(incomingPacks.toString());
      incomingPacks.notify();
    }
  }
}
