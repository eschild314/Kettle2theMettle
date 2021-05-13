package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gyroscope;
import com.qualcomm.robotcore.hardware.TouchSensor;
import java.util.Locale;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.ColorSensor;
import android.graphics.Color;

import java.util.Locale;

@TeleOp(group="tests")

public class SensorTest extends OpMode{
    ColorSensor sensorColor;
    DistanceSensor sensorDistance;
    DigitalChannel sensorTouch;
    
    // hsvValues is an array that will hold the hue, saturation, and value information.
    float hsvValues[] = {0F, 0F, 0F};
    // values is a reference to the hsvValues array.
    final float values[] = hsvValues;
    // sometimes it helps to multiply the raw RGB values with a scale factor
    // to amplify/attentuate the measured values.
    final double SCALE_FACTOR = 255;
    
    @Override
    public void init() {
        sensorColor = hardwareMap.get(ColorSensor.class, "cRS");
        sensorDistance = hardwareMap.get(DistanceSensor.class, "cRS");
        
        sensorTouch = hardwareMap.get(DigitalChannel.class, "ts");
        sensorTouch.setMode(DigitalChannel.Mode.INPUT);
    }
    
    @Override
    public void loop() {
         Color.RGBToHSV((int) (sensorColor.red() * SCALE_FACTOR),
                    (int) (sensorColor.green() * SCALE_FACTOR),
                    (int) (sensorColor.blue() * SCALE_FACTOR),
                    hsvValues);

        telemetry.addData("Distance (cm)",
                String.format(Locale.US, "%.02f", sensorDistance.getDistance(DistanceUnit.CM)));
        telemetry.addData("Alpha", sensorColor.alpha());
        telemetry.addData("Red  ", sensorColor.red());
        telemetry.addData("Green", sensorColor.green());
        telemetry.addData("Blue ", sensorColor.blue());
        telemetry.addData("Hue", hsvValues[0]);
        
        double hue = hsvValues[0];
        String color;
        
        if (hue >= 330 || hue <= 10) {
            color = "Red";
        } else if (hue > 10 && hue < 45) {
            color = "Orange";
        } else if (hue >= 45 && hue < 75) {
            color = "Yellow";
        } else if (hue >= 75 && hue < 135) {
            color = "Green";
        } else if (hue >= 135 && hue < 255) {
            color = "Blue";
        } else if (hue >= 255 && hue < 330) {
            color = "Purple";
        } else {
            color = "Unknown";
        }
        telemetry.addData("Possible Color", color);
        
        // Touch Sensor
        if (sensorTouch.getState() == true) {
            telemetry.addData("Touch Sensor", "Not Pressed");
        } else {
            telemetry.addData("Touch Sensor", "Pressed");
        }
        
        telemetry.update();
    }
}