package org.firstinspires.ftc.teamcode.ultimate_goal_autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
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
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.YZX;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.BACK;

@Autonomous(group ="ultimateGoal")
@Disabled
public class JustMoving extends LinearOpMode {
    // Power for motors while moving
    double flPower;
    double frPower;
    double blPower;
    double brPower;
    int footTime;
    int rotTime;

    // Launcher motors
    DcMotor leftMotor;
    DcMotor rightMotor;

    // Drive motors
    private DcMotor BackRight;
    private DcMotor FrontRight;
    private DcMotor FrontLeft;
    private DcMotor BackLeft;
    
    @Override public void runOpMode() {
        frPower = 0.5; //0.6 (last year's values are commented out)
        flPower = 0.5; //0.5
        brPower = 0.5; //0.5
        blPower = 0.5; //0.52
        footTime = 385; //385
        rotTime = 348; //356: changed to 348 for 2021 90-degree rotation
        
        // Initialize motors
        leftMotor = hardwareMap.dcMotor.get("motor0");
        rightMotor = hardwareMap.dcMotor.get("motor1");
        
        leftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        rightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        
        FrontRight = hardwareMap.dcMotor.get("FrontRight");
        FrontLeft = hardwareMap.dcMotor.get("FrontLeft");
        BackLeft = hardwareMap.dcMotor.get("BackLeft");
        BackRight = hardwareMap.dcMotor.get("BackRight");
        
        FrontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        BackLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        BackRight.setDirection(DcMotorSimple.Direction.REVERSE);
        
        waitForStart();
        
        // Test the movement methods
        move_forward();
        move_forward();
        move_right();
        move_right();
        move_backward();
        move_backward();
        move_left();
        move_left();
        stop2();
        rot_clock();
        stop2();
        rot_counterclock();
        
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
        
        FrontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        BackRight.setDirection(DcMotorSimple.Direction.REVERSE);
        BackLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        FrontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        FrontRight.setPower(frPower);
        FrontLeft.setPower(flPower);
        BackLeft.setPower(blPower);
        BackRight.setPower(brPower);
        sleep(rotTime);
    }
  
    private void rot_counterclock() {
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
}
