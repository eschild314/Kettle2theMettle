package org.firstinspires.ftc.teamcode.ultimate_goal_autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.Servo;
import java.lang.annotation.Target;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;

import java.util.ArrayList;
import java.util.List;

@Autonomous(group ="ultimateGoal")

public class Red extends LinearOpMode {
    // Servos
    Servo tilt0;
    Servo arm0;
    Servo claw0;
    
    // Power for motors while moving
    double flPower;
    double frPower;
    double blPower;
    double brPower;
    int footTime;
    int rotTime;

    // Drive motors
    private DcMotor BackRight;
    private DcMotor FrontRight;
    private DcMotor FrontLeft;
    private DcMotor BackLeft;

    // Vuforia setup
    private static final String TFOD_MODEL_ASSET = "UltimateGoal.tflite";
    private static final String LABEL_FIRST_ELEMENT = "Quad";
    private static final String LABEL_SECOND_ELEMENT = "Single";
    
    private static final String VUFORIA_KEY =
            "KEY";

    private VuforiaLocalizer vuforia;
    private TFObjectDetector tfod;
    int ringNum;
    
    @Override public void runOpMode() {
        frPower = 0.5; //0.6 (last year's values are commented out)
        flPower = 0.5; //0.5
        brPower = 0.555; //0.5
        blPower = 0.555; //0.52
        footTime = 650; //385: changed to 650 in 2021 for 18 inches per instruction
        rotTime = 410; //356: changed to 410 for 2021 90-degree rotation
        
        // Initialize motors and servos
        tilt0 = hardwareMap.servo.get("tilt0");
        arm0 = hardwareMap.servo.get("arm0");
        claw0 = hardwareMap.servo.get("claw0");
        
        tilt0.setPosition(0.5);
        arm0.setPosition(0);
        claw0.setPosition(1);
        
        FrontRight = hardwareMap.dcMotor.get("FrontRight");
        FrontLeft = hardwareMap.dcMotor.get("FrontLeft");
        BackLeft = hardwareMap.dcMotor.get("BackLeft");
        BackRight = hardwareMap.dcMotor.get("BackRight");
        
        FrontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        BackLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        BackRight.setDirection(DcMotorSimple.Direction.REVERSE);
        
        initVuforia();
        initTfod();
        ringNum = 0;
     
        if (tfod != null) {
            tfod.activate();
            //tfod.setZoom(2.5, 1.78);
        }
        
        telemetry.addData(">", "Everything is initialized.");
        telemetry.update();
        waitForStart();
        
        move_left();
        detectRings();
        stop2();
        telemetry.addData("ringNum", ringNum);
        telemetry.update();
        move();
    }
    
    private void move() {
        for(int i=0; i<7; i++) {
            move_left();
        }
        tilt_left();
        stop2();
        tilt_reset();
        brPower = 0.53;
        blPower = 0.53;
        if (ringNum == 0) {
            move_right();
            move_right();
            stop2();
            rot_counterclock();
            stop2();
            rot_counterclock();
            stop2();
            arm_up();
            claw_open();
            stop2();
            arm_reset();
            claw_close();
            move_right();
        } else if (ringNum == 1) {
            move_right();
            arm_up();
            claw_open();
            stop2();
            arm_reset();
            claw_close();
            move_right();
            move_right();
        } else if (ringNum == 4) {
            move_right(); // move back to prevent rotating into the wall
            rot_counterclock();
            stop2();
            rot_counterclock();
            stop2();
            brPower = 0.555;
            blPower = 0.555;
            move_left();
            brPower = 0.53;
            blPower = 0.53;
            arm_up();
            claw_open();
            stop2();
            arm_reset();
            claw_close();
            move_right();
            move_right();
            move_right();
        }
    }
    
    private void detectRings() {
            if (tfod != null) {
                // getUpdatedRecognitions() will return null if no new information is available since
                // the last time that call was made.
                List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                if (updatedRecognitions != null) {
                    int i = 0;
                    for (Recognition recognition : updatedRecognitions) {
                        //telemetry.addData(String.format("label (%d)", i), recognition.getLabel());
                        if (recognition.getLabel().equals("Quad")) {
                            ringNum = 4;
                        } else if (recognition.getLabel().equals("Single")) {
                            ringNum = 1;
                        } else {
                            ringNum = 0;
                        }
                    }
                }
                tfod.shutdown();
            }
        }
    
    private void arm_reset() {
        arm0.setPosition(0);
    }
    
    private void arm_down() {
        arm0.setPosition(0.89);
    }
    
    private void arm_up() {
        arm0.setPosition(0.6);
    }
    
    private void claw_open() {
        claw0.setPosition(0.75);
    }
    
    private void claw_close() {
        claw0.setPosition(1);
    }

    private void tilt_right() {
        tilt0.setPosition(0.7);
    }

    private void tilt_left() {
        tilt0.setPosition(0.3);
    }
    
    private void tilt_reset() {
        tilt0.setPosition(0.5);
    }
    
    private void move_forward() {
        FrontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        FrontLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        BackLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        BackRight.setDirection(DcMotorSimple.Direction.REVERSE);
        FrontRight.setPower(frPower);
        FrontLeft.setPower(flPower);
        BackLeft.setPower(blPower);
        BackRight.setPower(brPower);
        sleep(footTime);
    }
    
    private void move_backward() {
        FrontRight.setDirection(DcMotorSimple.Direction.FORWARD);
        FrontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        BackLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        BackRight.setDirection(DcMotorSimple.Direction.FORWARD);
        FrontRight.setPower(frPower);
        FrontLeft.setPower(flPower);
        BackLeft.setPower(blPower);
        BackRight.setPower(brPower);
        sleep(footTime);
    }
    
    private void move_left() {
        FrontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        FrontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        BackLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        BackRight.setDirection(DcMotorSimple.Direction.FORWARD);
        FrontRight.setPower(frPower);
        FrontLeft.setPower(flPower);
        BackLeft.setPower(blPower);
        BackRight.setPower(brPower);
        sleep(footTime);
    }

    private void move_right() {
        FrontRight.setDirection(DcMotorSimple.Direction.FORWARD);
        FrontLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        BackLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        BackRight.setDirection(DcMotorSimple.Direction.REVERSE);
        FrontRight.setPower(frPower);
        FrontLeft.setPower(flPower);
        BackLeft.setPower(blPower);
        BackRight.setPower(brPower);
        sleep(footTime);
    }
  
    private void stop2() {
        FrontRight.setPower(0);
        FrontLeft.setPower(0);
        BackLeft.setPower(0);
        BackRight.setPower(0);
        sleep(footTime);
    }
    
    private void rot_clock() {
        FrontLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        BackRight.setDirection(DcMotorSimple.Direction.FORWARD);
        BackLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        FrontRight.setDirection(DcMotorSimple.Direction.FORWARD);
        FrontRight.setPower(frPower);
        FrontLeft.setPower(flPower);
        BackLeft.setPower(blPower);
        BackRight.setPower(brPower);
        sleep(rotTime);
    }
  
    private void rot_counterclock() {
        FrontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        BackRight.setDirection(DcMotorSimple.Direction.REVERSE);
        BackLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        FrontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        FrontRight.setPower(0.69);
        FrontLeft.setPower(0.69);
        BackLeft.setPower(0.6);
        BackRight.setPower(0.6);
        sleep(rotTime);
    }
    
    /**
     * Initialize the Vuforia localization engine.
     */
    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraName = hardwareMap.get(WebcamName.class, "Megapixel Webcam");

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

    }

    /**
     * Initialize the TensorFlow Object Detection engine.
     */
    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
            "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
       tfodParameters.minResultConfidence = 0.8f;
       tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
       tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_FIRST_ELEMENT, LABEL_SECOND_ELEMENT);
    }
}
