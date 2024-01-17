/* Welcome to light detection rover, Rice Cooker 600! Here is what the rover does:
 * 1. Button will switch between autonomous and manual control
 * 2. Autonomous will:
      - Detect if there is a bright light and move towards it
      - Detect if there are obstacles in the way. If yes, turn and move in
        opposite direction.
      - If none of the above are detected, spin slowly and wait
 * 3. Manual control will:
     - Allow for thumbstick to control speed and direction of rover
 */

package rover;

// Add Phidgets Library
import com.phidget22.*;

public class RiceCooker6000 {

    // @param args the command line arguments
    public static void main(String[] args) throws Exception {
        
        // Connect to wireless rover
        Net.addServer("", "192.168.100.1", 5661, "", 0);

        // Create motors
        DCMotor leftMotors = new DCMotor();
        DCMotor rightMotors = new DCMotor();
        
        // Create thumbstick axes
        VoltageRatioInput vAxis = new VoltageRatioInput(); 
        VoltageRatioInput hAxis = new VoltageRatioInput(); 
        
        // Create light sensor
        Light lightSensor = new Light();
        
        // Create sonar sensor
        DistanceSensor sonar = new DistanceSensor();
        
        // Create button
        Button butt = new Button(5, 673700);

        // Set addresses
        leftMotors.setChannel(0);
        rightMotors.setChannel(1);
        vAxis.setChannel(0);
        hAxis.setChannel(1);

        // Open
        leftMotors.open(5000);
        rightMotors.open(5000);
        vAxis.open(5000);
        hAxis.open(5000);
        sonar.open(5000);

        // Increase acceleration (thumbstick)
        leftMotors.setAcceleration(leftMotors.getMaxAcceleration());
        rightMotors.setAcceleration(rightMotors.getMaxAcceleration());

        // Initiate motor speed variables
        double leftMotorsSpeed = 0;
        double rightMotorsSpeed = 0;
        
        // Initiate autonomous variable. Rover starts in manual control state
        boolean autonomous = false;
        
        // 
        while(true){
            // Check current button state and change autonomous variable accordingly
            autonomous = butt.state(autonomous);
            
            // Autonomous state
            if (autonomous){
                
                // Object detected
                if (sonar.getDistance() < 400) {
                        // Stop motors
                        leftMotors.setTargetVelocity(0);
                        rightMotors.setTargetVelocity(0);

                        Thread.sleep(250);
                        
                        // Turn clockwise
                        leftMotors.setTargetVelocity(-0.5);
                        rightMotors.setTargetVelocity(0.5);

                        Thread.sleep(1500);
                        
                        // Move forward
                        leftMotors.setTargetVelocity(-0.5);
                        rightMotors.setTargetVelocity(-0.5);
                        
                        Thread.sleep(1000);
                } // end if object detected
                
                // Light detected
                else if (lightSensor.getLight() > 50){
                    // Move forward (towards light)
                    leftMotorsSpeed = -0.5;
                    rightMotorsSpeed = -0.5;
                } // end else if light detected
                
                // No light or object detected
                else{
                    // Spin slowly clockwise
                    leftMotorsSpeed = -0.4;
                    rightMotorsSpeed = 0.4;
                } // end else
                
            } // end Autonomous
            
            // Manual control
            else{

                // Get data from vertical and horizontal axis (values between -1 and 1)
                double verticalAxis = vAxis.getVoltageRatio();
                double horizontalAxis = hAxis.getVoltageRatio();

                // Use thumbstick data to figure how each side of rover should move
                leftMotorsSpeed = verticalAxis + horizontalAxis;
                rightMotorsSpeed = verticalAxis - horizontalAxis;

                // Limit values to between -1 and 1
                if(leftMotorsSpeed > 0.75) leftMotorsSpeed = 1;
                if(leftMotorsSpeed < -0.75) leftMotorsSpeed = -1;
                if(rightMotorsSpeed > 0.75) rightMotorsSpeed = 1;
                if(rightMotorsSpeed < -0.75) rightMotorsSpeed = -1;

                // Wait for 100 milliseconds
                Thread.sleep(100);
            }
            
            // Apply values  from thumbstick to motors
            leftMotors.setTargetVelocity(leftMotorsSpeed);
            rightMotors.setTargetVelocity(rightMotorsSpeed);
            
        } // End while
        
    } // End main method
    
} // End main class
