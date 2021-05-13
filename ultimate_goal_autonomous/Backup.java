package org.firstinspires.ftc.teamcode.ultimate_goal_autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(group="ultimateGoal")

public class Backup extends LinearOpMode {
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
    
    @Override public void runOpMode() {
        frPower = 0.5; //0.6 (last year's values are commented out)
        flPower = 0.5; //0.5
        brPower = 0.555; //0.5
        blPower = 0.555; //0.52
        footTime = 650; //385: changed to 650 in 2021 for 18 inches per instruction
        rotTime = 410; //356: changed to 410 for 2021 90-degree rotation
        
        FrontRight = hardwareMap.dcMotor.get("FrontRight");
        FrontLeft = hardwareMap.dcMotor.get("FrontLeft");
        BackLeft = hardwareMap.dcMotor.get("BackLeft");
        BackRight = hardwareMap.dcMotor.get("BackRight");
        
        FrontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        BackLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        BackRight.setDirection(DcMotorSimple.Direction.REVERSE);
        
        waitForStart();
        
        for (int i=0; i<4; i++) {
            move_left();
        }
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
}