package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import java.lang.annotation.Target;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

@TeleOp(group="manualDrive")

public class TableForOnePlease extends OpMode {
    
    DcMotor FrontRight;
    DcMotor FrontLeft;
    DcMotor BackLeft;
    DcMotor BackRight;
    Servo ServoZero;
    Servo ServoOne;
    Servo ServoTwo;
    
    double ZeroPosition;
    double OnePosition;
    double TwoPosition;
    double SSpeed;
        
    double vertical;
    double horizontal;
    double rotation;
    
    @Override
    public void init() {
        FrontRight = hardwareMap.dcMotor.get("FrontRight");
        FrontLeft = hardwareMap.dcMotor.get("FrontLeft");
        BackLeft = hardwareMap.dcMotor.get("BackLeft");
        BackRight = hardwareMap.dcMotor.get("BackRight");
        ServoZero = hardwareMap.servo.get("ServoZero");
        ServoOne = hardwareMap.servo.get("ServoOne");
        ServoTwo = hardwareMap.servo.get("ServoTwo");
        
        FrontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        BackLeft.setDirection(DcMotorSimple.Direction.FORWARD); // was reverse
        BackRight.setDirection(DcMotorSimple.Direction.REVERSE); // didn't exist
    }
    @Override
    public void start() {
    }
    
    @Override
    public void stop() {
        ServoZero.setPosition(0.2);
        ServoTwo.setPosition(1);
    }
    
    @Override
    public void loop() {
        
        vertical = gamepad1.left_stick_y;
        horizontal = -gamepad1.right_stick_x;
        rotation = -gamepad1.left_stick_x;
        
        telemetry.addData("ServoZero", ZeroPosition);
        telemetry.addData("ServoTwo", TwoPosition);
        telemetry.update();
        
        if (gamepad1.right_trigger > 0) {
            // Half speed
            FrontRight.setPower((-rotation + (vertical - horizontal)) * 0.5);
            BackRight.setPower((-rotation + vertical + horizontal) * 0.5);
            FrontLeft.setPower((rotation + vertical + horizontal) * 0.5);
            BackLeft.setPower((rotation + (vertical - horizontal)) * 0.5);
            telemetry.update();
        } else {
            FrontRight.setPower(-rotation + (vertical - horizontal));
            BackRight.setPower(-rotation + vertical + horizontal);
            FrontLeft.setPower(rotation + vertical + horizontal);
            BackLeft.setPower(rotation + (vertical - horizontal));
            telemetry.update();
        }
        
        //Claw
        if (gamepad1.a) {
            //right trigger to close
            ServoZero.setPosition(0.2);
            telemetry.update();
        }
        if (gamepad1.b) {
            //left trigger to open
            ServoZero.setPosition(1);
            telemetry.update();
        }
       //Lift/drop
        if (gamepad1.left_trigger > 0) {
            ServoTwo.setPosition(0.5);
            telemetry.update();
        } else {
            ServoTwo.setPosition(1);  //0.8322 //0.887
            telemetry.update();
        }
    }
}