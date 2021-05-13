package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

@TeleOp(group="manualDrive")

public class ShooterWithDrive extends OpMode {
    // Tilt servo
    Servo tilt0;
    
    // Arm servos
    Servo arm0;
    Servo claw0;
    
    // Shooter/loader motors and servos
    /*DcMotor loader0;
    DcMotor leftMotor;
    DcMotor rightMotor;
    Servo hold0;*/

    // Drive motors    
    DcMotor FrontRight;
    DcMotor FrontLeft;
    DcMotor BackLeft;
    DcMotor BackRight;
    
    double horizontal;
    double vertical;
    double rotation;
    
    boolean available;
    double speed;
    double tiltpos;
    
    @Override
    public void init() {
        tilt0 = hardwareMap.servo.get("tilt0");
        tilt0.setPosition(0.5);
        tiltpos = 0.5;
        
        arm0 = hardwareMap.servo.get("arm0");
        claw0 = hardwareMap.servo.get("claw0");
        arm0.setPosition(0);
        claw0.setPosition(1);
        
        /*leftMotor = hardwareMap.dcMotor.get("motor0");
        rightMotor = hardwareMap.dcMotor.get("motor1");
        loader0 = hardwareMap.dcMotor.get("loader0");
        hold0 = hardwareMap.servo.get("hold0");
        leftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        rightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        loader0.setDirection(DcMotorSimple.Direction.REVERSE);
        hold0.setPosition();*/
        
        FrontRight = hardwareMap.dcMotor.get("FrontRight");
        FrontLeft = hardwareMap.dcMotor.get("FrontLeft");
        BackLeft = hardwareMap.dcMotor.get("BackLeft");
        BackRight = hardwareMap.dcMotor.get("BackRight");
        FrontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        BackLeft.setDirection(DcMotorSimple.Direction.FORWARD); 
        BackRight.setDirection(DcMotorSimple.Direction.REVERSE);
    }
    
    @Override
    public void loop() {
        vertical = -gamepad1.left_stick_y;
        horizontal = gamepad1.left_stick_x;
        rotation = gamepad1.right_stick_x;
        
        // Regular driving
        FrontRight.setPower(-rotation + (vertical - horizontal));
        BackRight.setPower(-rotation + vertical + horizontal);
        FrontLeft.setPower(rotation + vertical + horizontal);
        BackLeft.setPower(rotation + (vertical - horizontal));
        
        // Tilt 
        if (!gamepad1.right_bumper && !gamepad1.left_bumper) {
            available = true;
            telemetry.addData("At Rest", true);
            telemetry.update();
        }
        
        speed = 0.0625;
        if (gamepad1.right_bumper && available && tiltpos < (1+speed)) {
            tiltpos += speed;
            tilt0.setPosition(tiltpos);
            available = false;
        } 
        
        if (gamepad1.left_bumper && available && tiltpos > (0-speed)) {
            tiltpos -= speed;
            tilt0.setPosition(tiltpos);
            available = false;
        }
        
        if (gamepad1.dpad_up) {
            tilt0.setPosition(0.5);
            tiltpos = 0.5;
        }
        
        // Arm lift up
        if (gamepad1.y) {
            arm0.setPosition(0.6);
        }
        // Arm lift down
        if (gamepad1.a) {
            arm0.setPosition(0.89);
        }
        
        if (gamepad1.dpad_down) {
            arm0.setPosition(0);
        }
        
        // Open claw
        if (gamepad1.x) {
            claw0.setPosition(0.75);
        }
        // Close claw
        if (gamepad1.b) {
            claw0.setPosition(1);
        }
        
        /* Loader detached
        // Load rings
        if (gamepad1.right_trigger > 0) {
            loader0.setPower(1);
        }
        
        // Shoot rings or hold if not shooting
        if (gamepad1.left_trigger > 0) {
            //hold0.setPosition(NOT HOLDING);
            leftMotor.setPower(1);
            rightMotor.setPower(1);
        } else if (gamepad1.left_trigger == 0) {
            //hold0.setPosition(HOLDING);
        }*/
    }
}