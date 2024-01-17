/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package rover;

//Add Phidgets Library
import com.phidget22.*;

/**
 *
 * @author 16088504
 */
public class RiceCooker6000 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        
        //Connect to wireless rover
        Net.addServer("", "192.168.100.1", 5661, "", 0);

        //Create
        DCMotor leftMotors = new DCMotor();
        DCMotor rightMotors = new DCMotor();
        VoltageRatioInput vAxis = new VoltageRatioInput(); 
        VoltageRatioInput hAxis = new VoltageRatioInput(); 
        Light lightSensor = new Light();
        DistanceSensor sonar = new DistanceSensor();
        //Button greenButton = new Button();

        //Address
        leftMotors.setChannel(0);
        rightMotors.setChannel(1);
        vAxis.setChannel(0);
        hAxis.setChannel(1);

        //Open
        leftMotors.open(5000);
        rightMotors.open(5000);
        vAxis.open(5000);
        hAxis.open(5000);
        sonar.open(5000);

        //Increase acceleration 
        leftMotors.setAcceleration(leftMotors.getMaxAcceleration());
        rightMotors.setAcceleration(rightMotors.getMaxAcceleration());

        double leftMotorsSpeed = 0;
        double rightMotorsSpeed = 0;
        
        boolean autonomous = false;
        Button butt = new Button(5, 673700);
        
        //Use your Phidgets
        while(true){
            autonomous = butt.state(autonomous);
            //System.out.println(autonomous);
            //System.out.println(lightSensor.getLight());
            
            if (autonomous){
                if (sonar.getDistance() < 400) {
                        //Object detected! Stop motors
                        leftMotors.setTargetVelocity(0);
                        rightMotors.setTargetVelocity(0);

                        Thread.sleep(250);

                        leftMotors.setTargetVelocity(-0.5);
                        rightMotors.setTargetVelocity(0.5);

                        Thread.sleep(1500);
                        
                        leftMotors.setTargetVelocity(-0.5);
                        rightMotors.setTargetVelocity(-0.5);
                        
                        Thread.sleep(1000);
                }
                else if (lightSensor.getLight() > 50){
                    leftMotorsSpeed = -0.5;
                    rightMotorsSpeed = -0.5;
                }
                else{
                    leftMotorsSpeed = -0.4;
                    rightMotorsSpeed = 0.4;
                }
            }
            
            else{

                //Get data from vertical and horizontal axis (values between -1 and 1)
                double verticalAxis = vAxis.getVoltageRatio();
                double horizontalAxis = hAxis.getVoltageRatio();

                //Use thumbstick data to figure how each side of rover should move
                leftMotorsSpeed = verticalAxis + horizontalAxis;
                rightMotorsSpeed = verticalAxis - horizontalAxis;

                //Limit values to between -1 and 1
                if(leftMotorsSpeed > 0.75) leftMotorsSpeed = 1;
                if(leftMotorsSpeed < -0.75) leftMotorsSpeed = -1;
                if(rightMotorsSpeed > 0.75) rightMotorsSpeed = 1;
                if(rightMotorsSpeed < -0.75) rightMotorsSpeed = -1;



                //Wait for 100 milliseconds
                Thread.sleep(100);
            }
            
            //Apply values 
            leftMotors.setTargetVelocity(leftMotorsSpeed);
            rightMotors.setTargetVelocity(rightMotorsSpeed);
        }
    }
}
