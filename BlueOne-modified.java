package org.firstinspires.ftc.teamcode;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.SwitchableLight;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

@Autonomous(name="Blue One", group="Pushbot")
public class Blue1 extends LinearOpMode {

    /* Declare OpMode members. */

    private ElapsedTime     runtime = new ElapsedTime();

    double     FORWARD_SPEED = 0.35;

    static final double     COUNTS_PER_MOTOR_REV    = 2040 ;    // eg: TETRIX Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = 1.0 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 3.5 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double     DRIVE_SPEED             = 0.6;
    static final double     TURN_SPEED              = 0.5;
    private DcMotor leftDrive = null;
    private DcMotor rightDrive = null;
    //private Servo servo1;
    //private Servo servo2;
    private DcMotor armMotor;
    static final double     FINAL_DRIVE_SPEED_LEFT       = 0.40;



    private Servo jewelArmServo = null;

    private Servo jewelServo = null;

    private boolean isRedAlliance = true;
    // private boolean isBlueAlliance = false;
    private static final double ARM_IN = .5;
    private static final double ARM_OUT = 1.0;
    private static final double JEWEL_LEFT = 1.0;
    private static final double JEWEL_RIGHT = 0;
    private static final double JEWEL_CENTER = 0.5;
    private ColorSensor colorsensor1;




    @Override
    public void runOpMode() {


        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).


        leftDrive  = hardwareMap.get(DcMotor.class, "motor1");
        rightDrive = hardwareMap.get(DcMotor.class, "motor0");
        //servo1 = hardwareMap.get(Servo.class, "servo1");
        //servo2 = hardwareMap.get(Servo.class, "servo2");
        jewelArmServo = hardwareMap.get( Servo.class , "servo4");
        jewelServo =  hardwareMap.get( Servo.class , "servo3");
        armMotor = hardwareMap.get(DcMotor.class, "motor2");
        colorsensor1 = hardwareMap.get(ColorSensor.class, "colorsensor1");

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Resetting Encoders");    //
        telemetry.update();

        leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Send telemetry message to indicate successful Encoder reset
        telemetry.addData("Path0",  "Starting at %7d :%7d",
                leftDrive.getCurrentPosition(),
                rightDrive.getCurrentPosition());
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // closing arm
        //rvo1.setPosition(0.1);
        //rvo2.setPosition(0.9);
       // wait for arm to close
        wait(2.0);
        // stop arm from clossing
        //rvo2.setPosition(0.5);
        //rvo1.setPosition(0.5);
        
        raiseArm(.5);
        telemetry.addData("Path", "Complete");
        telemetry.update();
        
         //move jewel arm down
        jewelArmServo.setDirection(Servo.Direction.FORWARD);
         jewelArmServo.setPosition(0.61);
        


     //  waiting for jewel arm to go down
        wait(1.0);

      // reading the pictograph
      
        RelicRecoveryVuMark vuMark = getVuMark();


        if(!isJewelColorBlue()){


            encoderDrive(.4,.4, -2.2, 2.2, 1 );//move backward as we are in blue alliance
            jewelArmServo.setDirection(Servo.Direction.FORWARD);
            jewelArmServo.setPosition(0.0);
            //moveBackward(0.01);
            encoderDrive(.6, .6, 2.2, -2.2, 1 );//move forward as we have to come back to the original position

            //sleep(1000);
//moveForward(0.01);

        }
        else{


            encoderDrive(.4,.4, 2.2, -2.2, 1 );//move forward as we are in blue alliance
            jewelArmServo.setDirection(Servo.Direction.FORWARD);
            jewelArmServo.setPosition(0.0);
            //moveBackward(0.01);
            encoderDrive(.6, .6, -2.2, 2.2,1);//move backward as we have come back to original position

            //  sleep(1000);
            //  moveBackward(0.005);
        }



        sleep(1000);


        if( vuMark == RelicRecoveryVuMark.LEFT )
        {
            telemetry.addData("VuMark", "Found Left");
            encoderDrive(DRIVE_SPEED,3.65, -3.65, 10);
            encoderDrive(TURN_SPEED, 0.738, 0.738, 10);
          
//            encoderDrive(TURN_SPEED,3.9, 3.9, 1.4);
//            encoderDrive(DRIVE_SPEED,1.2, -1.2, 1.9 );
//            encoderDrive(TURN_SPEED, -3.9, -3.9, 3);
//            encoderDrive(DRIVE_SPEED,1.2, -1.2, 2.0 );
            telemetry.update();

            telemetry.addData("leftDrive.getCurrentPosition()",  leftDrive.getCurrentPosition());
            telemetry.addData("rightDrive.getCurrentPosition()",  rightDrive.getCurrentPosition());

            telemetry.update();
        }

        else if ( vuMark == RelicRecoveryVuMark.RIGHT)

        {
            telemetry.addData("VuMark", "Found Right");
            encoderDrive(DRIVE_SPEED, 4, -4, 10 );
            encoderDrive(TURN_SPEED,2.1, 2.1, 10);
            encoderDrive(DRIVE_SPEED,3.7, -3.5, 10 );
            encoderDrive(TURN_SPEED, - 2.35, -2.35, 10);
            //encoderDrive(DRIVE_SPEED,2.1, -2.1, 10 );
            telemetry.update();

        }

        else if ( vuMark ==RelicRecoveryVuMark.CENTER )
        {
            telemetry.addData("VuMark", "Found Center");
            //telemetry.update();
            encoderDrive(DRIVE_SPEED, 4, -4, 10 );
            encoderDrive(TURN_SPEED,2.1, 2.1, 10);
            //encoderDrive(DRIVE_SPEED,2.1, -2.1, 10 );
            encoderDrive(DRIVE_SPEED,2.6, -2.6, 10 );
            encoderDrive(TURN_SPEED, - 2.1, -2.1, 10);
          //  encoderDrive(DRIVE_SPEED,0.85, -0.85, 10 );
            telemetry.update();
        }
        //  center
        else
        {
            if( vuMark ==RelicRecoveryVuMark.UNKNOWN )
            {
                encoderDrive(DRIVE_SPEED, 4, -4, 10 );
                encoderDrive(TURN_SPEED,2, 2, 10);
                encoderDrive(DRIVE_SPEED,2.15, -2.15, 10 );
                encoderDrive(TURN_SPEED, - 2.0, -2.0, 10);
                //encoderDrive(DRIVE_SPEED,2.5, -2.5, 10 );
                telemetry.addData("VuMark", "Could Not aquire target new");
                telemetry.update();
            }

        }


        //telemetry.update();

        //sleep(2000);
        //jewelArmMoveDown();

     /*   boolean isblueJewel = isJewelBlue();
        if(  isblueJewel && isRedAlliance  ||
                !isblueJewel && ! isRedAlliance )
        {
            jewelServo.setPosition(JEWEL_LEFT);
            //jewelServoMoveForward();
        }
        else
        {
            jewelServo.setPosition(JEWEL_RIGHT);
           // jewelServoMoveBackward();
        }
        jewelServo.setPosition(JEWEL_CENTER);*/

        //this.jewelArmMoveUp();



        // Step through each leg of the path,
        // Note: Reverse movement is obtained by setting a negative distance (not speed)

        lowerArm(0.6);

       //ervo2.setPosition(0.1);
       //ervo1.setPosition(0.9);
//        runtime.reset();
//        while(runtime.seconds()>2){
//            servo2.setPosition(0.0);
//            servo1.setPosition(1.0);
//        }
        wait(0.8);
        //servo2.setPosition(0.5);
        //servo1.setPosition(0.5);
     //   encoderDrive(.3,.3, -1.5, 1.5, 1.3 );//come back
     encoderDrive(DRIVE_SPEED,1.6, -1.6, 10 );
       // encoderDrive(.3, .3, 5.0, -5.0,2.0);//move forward
      //  encoderDrive(.3,.3, -.8, .8, 0.7 );//come back
        encoderDrive(DRIVE_SPEED,-.6, .6, 10 );
        telemetry.addData("Path", "Complete");
        telemetry.update();
    }

//        public void moveServo( double left, double right)
//        {
//            servo1.setPosition(left);
//            servo2.setPosition(right);
//        }

//        public void openServoArms()
//        {
//            //while (opModeIsActive()){
//                runtime.reset();
//                while (runtime.seconds() < 2)  {
//                    servo2.setPosition(0.1);
//                    servo1.setPosition(0.6);
//
//                    // Display it for the driver.
//                   // telemetry.update();
//                }
//                servo1.setPosition(0.5);
//                servo2.setPosition(0.5);
//            }
    //}
public void raiseArm(double seconds)
{
    runtime.reset();
    while(opModeIsActive() &&(runtime.seconds() < seconds )) {
        armMotor.setPower(.65);
        //armMotor.setPower(0);
    }
    armMotor.setPower(0);
}

public void lowerArm( double seconds)
{
    runtime.reset();
    while(opModeIsActive() &&(runtime.seconds() < seconds )) {
        armMotor.setPower(-.65);
        armMotor.setPower(0);
    }
}
    public RelicRecoveryVuMark getVuMark()
    {
        VuforiaLocalizer vuforia;
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        parameters.vuforiaLicenseKey = "AZPK02n/////AAAAGTBcTiMAzU+tk4CtqUn3U9pZBiC6puyvVfFfi/GMP6GGvoPf7iVDkAPXCudRuXMnHZiNuFgnkHj+5p7JQJnqwj2TyVoe48xIBQP2d/kxtJAsyT76FTrcULBiUhtohOn8Uig4MbxJJVHZCw/W7K+MHnYE421IkUxHRZzbnjpt/od8loxGnLh8vH3KR6G+k3XdZKLRoHTs2qRxlUizrj2JRQkLi/e14V29ceq4eQIG+PAulYuW33g6IEvJ5guotk3GlwpVVZp/7pqbsdvKLBA6ZpMcROdlSo1ogW4UuSN4QM7a2lq6xEscPy8keRQx7IZEdndoaYyEzP3g1bZPLF/MxEZBnbeWe8vMYJxR6x6tgdeM";
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        vuforia = ClassFactory.createVuforiaLocalizer(parameters);
        VuforiaTrackables relicTrackables = vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate"); // can help in debugging; otherwise not necessary
        relicTrackables.activate();


        runtime.reset();
        RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);
        while (runtime.seconds() <2 )
        {
            vuMark = RelicRecoveryVuMark.from(relicTemplate);
            if ( vuMark == RelicRecoveryVuMark.RIGHT ||
                 vuMark == RelicRecoveryVuMark.CENTER ||
                 vuMark == RelicRecoveryVuMark.LEFT )
              break;
        }

        return vuMark;

    }
    public boolean isJewelBlue()
    {
        NormalizedColorSensor colorSensor = hardwareMap.get(NormalizedColorSensor.class, "colorsensor1");

        if (colorSensor instanceof SwitchableLight) {
            ((SwitchableLight)colorSensor).enableLight(true);
        }

        NormalizedRGBA colors = colorSensor.getNormalizedColors();
        boolean isBlue = false;

        if( colors.blue > colors.red )
        {
            isBlue = true;
        }

        if (colorSensor instanceof SwitchableLight) {
            ((SwitchableLight)colorSensor).enableLight(false);
        }
        return isBlue;
    }

    private void moveForward(double seconds){
        // Step 1:  Drive forward for 3 seconds
        leftDrive.setPower(FORWARD_SPEED);
        rightDrive.setPower(-FORWARD_SPEED);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < seconds)) {
            telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }
    }
    private void moveBackward(double seconds){
        // Step 3:  Drive Backwards for 1 Second
        leftDrive.setPower(-FORWARD_SPEED);
        rightDrive.setPower(FORWARD_SPEED);

        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < seconds)) {
            telemetry.addData("Path", "Leg 3: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }
    }


    public void jewelArmMoveUp()
    {

        this.jewelArmServo.setPosition(ARM_IN);

    }

    public void jewelArmMoveDown()
    {
        this.jewelArmServo.setPosition(ARM_OUT);
    }

    private void wait(double seconds){
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < seconds)) {
            telemetry.addData("Waiting", "Time: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }
    }

    public void jewelServoMoveForward()
    {
        jewelServo.setPosition(JEWEL_LEFT);
    }
    public void jewelServoMoveBackward()
    {
        jewelServo.setPosition(JEWEL_RIGHT);
    }


    private boolean isJewelColorBlue(){
        boolean blueJewel = false;

        // hsvValues is an array that will hold the hue, saturation, and value information.
        float hsvValues[] = {0F,0F,0F};

        // values is a reference to the hsvValues array.
        final float values[] = hsvValues;

        // get a reference to the RelativeLayout so we can change the background
        // color of the Robot Controller app to match the hue detected by the RGB sensor.
        int relativeLayoutId = hardwareMap.appContext.getResources().getIdentifier("RelativeLayout", "id", hardwareMap.appContext.getPackageName());
        final View relativeLayout = ((Activity) hardwareMap.appContext).findViewById(relativeLayoutId);

        // bPrevState and bCurrState represent the previous and current state of the button.
        boolean bPrevState = false;
        boolean bCurrState = false;

        // bLedOn represents the state of the LED.
        boolean bLedOn = true;

        // Set the LED in the beginning
        colorsensor1.enableLed(bLedOn);

        // convert the RGB values to HSV values.
        Color.RGBToHSV(colorsensor1.red() * 8, colorsensor1.green() * 8, colorsensor1.blue() * 8, hsvValues);

        // send the info back to driver station using telemetry function.

        telemetry.addData("Red  ", colorsensor1.red());
        telemetry.addData("Blue ", colorsensor1.blue());

        if(colorsensor1.blue() > colorsensor1.red()){
            blueJewel = true;
        }
        return blueJewel;

    }
    /*
     *  Method to perfmorm a relative move, based on encoder counts.
     *  Encoders are not reset as the move is based on the current position.
     *  Move will stop if any of three conditions occur:
     *  1) Move gets to the desired position
     *  2) Move runs out of time
     *  3) Driver stops the opmode running.
     */

    public void encoderDrive(double speed,
                             double leftInch, double rightInch,
                             double timeoutS)
    {
        encoderDrive( speed, speed, leftInch, rightInch,timeoutS ) ;
    }
    public void encoderDrive(double speedleft,double speedright,
                             double leftInch, double rightInch,
                             double timeoutS) {
        int newLeftTarget;
        int newRightTarget;

        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newLeftTarget = leftDrive.getCurrentPosition() + (int)(leftInch * COUNTS_PER_INCH);
            newRightTarget = rightDrive.getCurrentPosition() + (int)(rightInch * COUNTS_PER_INCH);
            leftDrive.setTargetPosition(newLeftTarget);
            rightDrive.setTargetPosition(newRightTarget);

            // Turn On RUN_TO_POSITION
            leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            leftDrive.setPower(Math.abs(speedleft));
            rightDrive.setPower(Math.abs(speedright));

            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (leftDrive.isBusy() && rightDrive.isBusy())) {

                // Display it for the driver.
                telemetry.addData("Path1",  "Running to %7d :%7d", newLeftTarget,  newRightTarget);
                telemetry.addData("Path2",  "Running at %7d :%7d",
                        leftDrive.getCurrentPosition(),
                        rightDrive.getCurrentPosition());
                telemetry.update();
            }

            // Stop all motion;
            leftDrive.setPower(0);
            rightDrive.setPower(0);


            // Turn off RUN_TO_POSITION
            leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            //  sleep(250);   // optional pause after each move
        }
    }
}
