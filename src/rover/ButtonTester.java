package rover;

//Add Phidgets Library 
import com.phidget22.*;

public class ButtonTester {
    //Handle Exceptions 
    public static void main(String[] args) throws Exception{
        
        //Create 
        DigitalInput redButton = new DigitalInput();

        //Address 
        redButton.setHubPort(4);
        redButton.setIsHubPortDevice(true);

        //Open 
        redButton.open(1000);

        //Use your Phidgets 
        while(true){
            System.out.println("Button State: " + redButton.getState());
            Thread.sleep(150);
        }
    }
}