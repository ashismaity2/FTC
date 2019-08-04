//made by sai k
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
//made by sai

@TeleOp(name="Java TeleOp", group="Linear OpMode")
public class TeleOpJava extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftDriveRear = null;
    private DcMotor rightDriveRear = null;
    private DcMotor armMotor;
    private DcMotor armMotorTwo;
    private Servo servo1;
    private Servo servo2;
    private Servo servo3;
    static final double     COUNTS_PER_MOTOR_REV    = 1440 ;    // eg: TETRIX Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = 2.0 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_MM       = 3.5 ;     // For figuring circumference
    private static final double ARM_MOVE_FORWARD = .4;
    private static final double ARM_MOVE_BACKWARD = 0.4;

//NEW CODE FOR SETTING THE POWER WHILE GOING UP FOR MOTOR1 
    public static final double ARM_UP_POWER = 0.60;
//NEW CODE FOR SETTING THE POWER WHILE GOING DOWN FOR MOTOR1 
    public static final double ARM_DOWN_POWER = -0.60;


    double power = .5;
    static final double     COUNTS_PER_MM         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_MM * 3.1415);

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        leftDriveRear  = hardwareMap.get(DcMotor.class, "motor0");
        rightDriveRear = hardwareMap.get(DcMotor.class, "motor1");
        servo1 = hardwareMap.get(Servo.class, "servo1");
        servo2 = hardwareMap.get(Servo.class, "servo2");
        armMotor = hardwareMap.get(DcMotor.class, "motor2");
        armMotorTwo = hardwareMap.get(DcMotor.class, "motor3");

        servo3 = hardwareMap.get(Servo.class, "servo3");



        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        leftDriveRear.setDirection(DcMotor.Direction.REVERSE);
        rightDriveRear.setDirection(DcMotor.Direction.FORWARD);
        armMotor.setDirection(DcMotor.Direction.REVERSE);
        armMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        armMotorTwo.setDirection(DcMotorSimple.Direction.REVERSE);
        armMotorTwo.setDirection(DcMotorSimple.Direction.FORWARD);




        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();


        telemetry.addData("leftDrive.getCurrentPosition()",  rightDriveRear.getCurrentPosition());
        telemetry.addData("rightDrive.getCurrentPosition()",  leftDriveRear.getCurrentPosition());
        telemetry.update();
        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            // Setup a variable for each drive wheel to save power level for telemetry
            double rearPower;
            double frontPower;
//                double armPower;


            // Choose to drive using either Tank Mode, or POV Mode
            // Comment out the method that's not used.  The default below is POV.

            // POV Mode uses left stick to go forward, and right stick to turn.
            // - This uses basic math to combine motions and is easier to drive straight.
            double drive = -gamepad1.left_stick_y;
            double turn  =  gamepad1.right_stick_x;
            frontPower  = Range.clip(drive + turn, -1.0, 1.0) ;
            rearPower   = Range.clip(drive - turn, -1.0, 1.0) ;
//          armPower    = Range.clip(drive + turn, -1.0, 1.0);



    //new code for raising/lowering arm with motor 1
    //Use gamepad buttons to move the arm up (Y) and down (A)

            if (gamepad2.y)
                armMotor.setPower(ARM_UP_POWER);
            else if (gamepad2.a )
                armMotor.setPower(ARM_DOWN_POWER );
            else
                armMotor.setPower(0.0);
    

    // new code for raising/lowering arm with motor 2
     //Use gamepad buttons to move the arm up (Y) and down (A)


        if (gamepad2.x)
                armMotorTwo.setPower(ARM_UP_POWER);
            else if (gamepad2.b )
                armMotorTwo.setPower(ARM_DOWN_POWER );
            else
                armMotorTwo.setPower(0.0);




         //armMotor.setPower(+gamepad2.right_trigger);
            //armMotor.setPower(-gamepad2.left_trigger);
            //armMotorTwo.setPower(+gamepad1.left_trigger);
            //armMotorTwo.setPower(-gamepad1.right_trigger);

            if (gamepad1.a)
            {
                servo1.setPosition(0.2);
                servo2.setPosition(.8);

            }
            if (gamepad1.b)
            {
                servo1.setPosition(.8);
                servo2.setPosition(.2);

            }


            if (gamepad2.start)
            {
                servo1.setPosition(.5);
                servo2.setPosition(.5);
            }
            if (gamepad1.start)
            {
                servo1.setPosition(.5);
                servo2.setPosition(.5);
            }
             
            //{
            //    servo2.setPosition(0);
            //    }
            //  if (gamepad1.y)
            //{
            //  servo2.setPosition(.8);
            //}
            idle();



            // Tank Mode uses one stick to control each wheel.
            // - This requires no math, but it is hard to drive forward slowly and keep straight.
            // leftPower  = -gamepad1.left_stick_y ;
            // rightPower = -gamepad1.right_stick_y ;

            //Range.clip(value,  minimum value, maximum value) is the syntax, but we wrote our own method to scale our input, manage deadzones, and make sure we don't burn out our neverest-40's which have a max scalar power of .78, usage scale(value). We encourage writing this type of method, but using Range.clip also works
            /*frontright.setPower(scale(y+x-z));
            frontleft.setPower(scale(y-x+z));
            backright.setPower(scale(y-x-z));frontPower
            backleft.setPower(scale(y+x+z));rearPower
    */

            // Send calculated power to wheels
            leftDriveRear.setPower(rearPower);
            rightDriveRear.setPower(frontPower);
//                armMotor.setPower(armPower);

            // Show the elapsed game time and wheel power.
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Motors", "left (%.2f), right (%.2f)", rearPower, frontPower);
            telemetry.update();

        }
    }

    public void encoderDrive(double speed,
                             double leftMM, double rightMM,
                             double timeoutS) {
        int newLeftTarget;
        int newRightTarget;

        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newLeftTarget = leftDriveRear.getCurrentPosition() + (int)(leftMM * COUNTS_PER_MM);
            newRightTarget = rightDriveRear.getCurrentPosition() + (int)(rightMM * COUNTS_PER_MM);
            leftDriveRear.setTargetPosition(newLeftTarget);
            rightDriveRear.setTargetPosition(newRightTarget);

            // Turn On RUN_TO_POSITION
            leftDriveRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightDriveRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            leftDriveRear.setPower(Math.abs(speed));
            rightDriveRear.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (leftDriveRear.isBusy() && rightDriveRear.isBusy())) {

                // Display it for the driver.
                telemetry.addData("Path1",  "Running to %7d :%7d", newLeftTarget,  newRightTarget);
                telemetry.addData("Path2",  "Running at %7d :%7d",
                        leftDriveRear.getCurrentPosition(),
                        rightDriveRear.getCurrentPosition());
                telemetry.update();
            }

            // Stop all motion;
            leftDriveRear.setPower(0);
            rightDriveRear.setPower(0);

            // Turn off RUN_TO_POSITION
            leftDriveRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rightDriveRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            //  sleep(250);   // optional pause after each move
        }
    }
}
