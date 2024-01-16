/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rover;

//Add Phidgets Library
import com.phidget22.*;

/**
 *
 * @author 16088504
 */

public class Light {
    private LightSensor lightSensor;
    
    public Light() throws Exception{
        lightSensor = new LightSensor();
        
        //Open
        lightSensor.open(1000);
    }
    
    public double getLight() throws Exception{
        return lightSensor.getIlluminance();
    }
}
