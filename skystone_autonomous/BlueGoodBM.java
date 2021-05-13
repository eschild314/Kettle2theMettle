package org.firstinspires.ftc.teamcode.skystone_autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotorSimple.Direction;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous(name = "BlueGoodBM", group = "skystone")
@Disabled
public class BlueGoodBM extends LinearOpMode {

  private DcMotor BackRight;
  private DcMotor FrontRight;
  private DcMotor FrontLeft;
  private DcMotor BackLeft;
  private Servo ServoZero;
  private Servo ServoOne;
  private Servo ServoTwo;
    
  double ZeroPosition;
  double OnePosition;
  double TwoPosition;
  double SSpeed;
  
  double flPower;
  double frPower;
  double blPower;
  double brPower;
  int footTime;
  int rotTime;

  @Override
  public void runOpMode() {
    BackRight = hardwareMap.dcMotor.get("BackRight");
    FrontRight = hardwareMap.dcMotor.get("FrontRight");
    FrontLeft = hardwareMap.dcMotor.get("FrontLeft");
    BackLeft = hardwareMap.dcMotor.get("BackLeft");
    ServoZero = hardwareMap.servo.get("ServoZero");
    ServoOne = hardwareMap.servo.get("ServoOne");
    ServoTwo = hardwareMap.servo.get("ServoTwo");

    frPower = 0.6; //0.5 for all, then 0.75
    flPower = 0.5;
    brPower = 0.5;
    blPower = 0.52;
    footTime = 385; //250 with 0.75
    rotTime = 356; //2850 divided by 8 (356.25)
    
    waitForStart();
    
    drop_bm();
    open_claw();
    
    /*rotTime = 75;
    rot_counterclock();
    stop2();
    rot_clock();
    stop2();*/
    
    
    /*lift_bm();
    stop2();
    move_right();
    move_right();
    move_right();
    move_right();
    move_right();
    move_right();
    move_right();*/
    
    
    
    steps();
    
  }
  
  private void steps() {
    move_forward();
    move_forward();
    move_forward();
    move_forward();
    stop2();
    close_claw();
    stop2();
    lift_bm();
    stop2();
    move_backward();
    move_backward();
    stop2();
    move_left();
    move_left();
    move_left();
    move_left();
    move_left();
    move_left();
    move_left();
    move_left();
    move_left(); 
    footTime = 125;
    move_left();
    
    move_left();
    move_forward();
    footTime = 75;
    move_forward();
    rotTime = 75;
    rot_counterclock();
    stop2();
    rot_clock();
    stop2();
  
    stop2();
    footTime = 225;
    move_forward();
    footTime = 385; //reset
    stop2();
    drop_bm();
    stop2();
    footTime = 60;
    move_right();
    move_left();
    stop2();
    open_claw();
    stop2();
    stop2();
    stop2();
    
    rot_counterclock();
    stop2();
    rot_clock();
    stop2();
    
    move_backward();
    move_backward();
    rotTime = 356;
    footTime = 385;
    move_backward();
    move_backward();
    rot_counterclock();
    move_backward();
    footTime = 150;
    move_left();
    footTime = 385;
    stop2();
    move_backward();
    move_backward();
    
    stop2();
    lift_bm();
    stop2();
    move_right();
    move_right();
    move_right();
    move_right();
    move_right();
    move_right();
    footTime = 75;
    move_right();
    
  }
  
  private void close_claw() {
    ServoZero.setPosition(0.2);
    sleep(footTime);
  }
  
  private void open_claw() {
    ServoZero.setPosition(1); //0.8322
    sleep(footTime);
  }
  
  private void lift_bm() {
    ServoTwo.setPosition(0.5);
    sleep(footTime);
  }
  
  private void drop_bm() {
    ServoTwo.setPosition(1);
    sleep(footTime);    
  }
  
private void move_backward() {
    FrontRight.setDirection(DcMotorSimple.Direction.REVERSE);
    FrontLeft.setDirection(DcMotorSimple.Direction.FORWARD);
    BackLeft.setDirection(DcMotorSimple.Direction.REVERSE);
    BackRight.setDirection(DcMotorSimple.Direction.FORWARD);
    FrontRight.setPower(frPower);
    FrontLeft.setPower(flPower);
    BackLeft.setPower(blPower);
    BackRight.setPower(brPower);
    sleep(footTime);
  }
  
  private void move_forward() {
    FrontRight.setDirection(DcMotorSimple.Direction.FORWARD);
    FrontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
    BackLeft.setDirection(DcMotorSimple.Direction.FORWARD);
    BackRight.setDirection(DcMotorSimple.Direction.REVERSE);
    FrontRight.setPower(frPower);
    FrontLeft.setPower(flPower);
    BackLeft.setPower(blPower);
    BackRight.setPower(brPower);
    sleep(footTime);
  }

  private void move_left() {
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

  private void move_right() {
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
  
  //NOT UPDATED
  private void north_east() {
    FrontLeft.setDirection(DcMotorSimple.Direction.FORWARD);
    BackRight.setDirection(DcMotorSimple.Direction.REVERSE);
    FrontRight.setPower(0);
    FrontLeft.setPower(flPower);
    BackLeft.setPower(0);
    BackRight.setPower(brPower);
    sleep(footTime);
  }
  
  private void south_east() {
    BackLeft.setDirection(DcMotorSimple.Direction.REVERSE);
    FrontRight.setDirection(DcMotorSimple.Direction.FORWARD);
    FrontRight.setPower(frPower);
    FrontLeft.setPower(0);
    BackLeft.setPower(blPower);
    BackRight.setPower(0);
    sleep(footTime);
  }
  
  private void north_west() {
    BackLeft.setDirection(DcMotorSimple.Direction.FORWARD);
    FrontRight.setDirection(DcMotorSimple.Direction.REVERSE);
    FrontRight.setPower(frPower);
    FrontLeft.setPower(0);
    BackLeft.setPower(blPower);
    BackRight.setPower(0);
    sleep(footTime);
  }
  
  private void south_west() {
    FrontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
    BackRight.setDirection(DcMotorSimple.Direction.FORWARD);
    FrontRight.setPower(0);
    FrontLeft.setPower(flPower);
    BackLeft.setPower(0);
    BackRight.setPower(brPower);
    sleep(footTime);
  }
}
