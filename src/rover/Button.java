/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rover;

import com.phidget22.*;
/**
 *
 * @author 16088504
 */
public class Button {
    DigitalInput butt;
    
    public Button(int port, int serialNum) throws Exception{
        butt = new DigitalInput();
        butt.setDeviceSerialNumber(serialNum);
        butt.setHubPort(port);
        butt.setIsHubPortDevice(true);

        //Open 
        butt.open(1000);
        
    }
    
    public boolean state(boolean currentState) throws Exception{
        System.out.println("Auto: " + currentState);
        System.out.println("butt: " + butt.getState());
        Thread.sleep(100);
        if (butt.getState()){
            System.out.println("Switch");
            return !currentState;
        }
        return currentState;
    }
}
