package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import java.util.Locale;

import static android.R.attr.left;
import static com.sun.tools.doclint.Entity.le;

/**
 * Created by FireStormers on 10/22/2017.
 */
@TeleOp
public class ProgramingROBOT20172018 extends LinearOpMode {
    private DigitalChannel digitalTouch;
    private DistanceSensor sensorColorRange;
    private Servo servoTest;
    private ColorSensor sensorColor;
    private DcMotor stormymotor;

    @Override
    public void runOpMode() {
        digitalTouch = hardwareMap.get(DigitalChannel.class, "digitalTouch");
        sensorColorRange = hardwareMap.get(DistanceSensor.class, "sensorColorRange");
        servoTest = hardwareMap.get(Servo.class, "servoTest");
        sensorColor = hardwareMap.get(ColorSensor.class, "sensorColorRange");
        stormymotor = hardwareMap.get(DcMotor.class, "StormyMotor");
        double motorpower = 0;

        //hsvValues
        float hsvValues[] ={0F, 0F, 0F};

        // set digital channel to input mode
        digitalTouch.setMode(DigitalChannel.Mode.INPUT);

        servoTest.setPosition(0);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        //StormyMotor
        stormymotor.setPower(0);
        stormymotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()){
            telemetry.addData("Status", "Running");
            telemetry.update();
            telemetry.addData("Distance (cm)", sensorColorRange.getDistance(DistanceUnit.CM));


            motorpower = -gamepad1.left_stick_y;
            stormymotor.setPower(motorpower);
            telemetry.addData("motorpower", "0.2f", motorpower);

            //Color sensor stuff
            Color.RGBToHSV((int) (sensorColor.red()),
                    (int) (sensorColor.green()),
                    (int) (sensorColor.blue()),
                    hsvValues);
            //send the info back to driver station using telemetry function.
            telemetry.addData("Alpha", sensorColor.alpha());
            telemetry.addData("Red  ", sensorColor.red());
            telemetry.addData("Green", sensorColor.green());
            telemetry.addData("Blue ", sensorColor.blue());
            telemetry.addData("Hue", hsvValues[0]);


            //run until the end of the match (driver presses STOP)
            //double tgtpower = 0
                //tgtpower = -this.gamepad1.left_stick_y
                //motorTest.setPower(tgtpower)
            // check to see if we need to move the servo.
            if(gamepad1.y) {
                //move to 0 degrees.
                servoTest.setPosition(0);
            } else if (gamepad1.x) {
                //move to 90 degrees.
                servoTest.setPosition(0.5);
            } else if (gamepad1.a) {
                //move to 180 degrees.
                servoTest.setPosition(1);
            }

            // is button pressed?
            if (digitalTouch.getState() == false) {
                // button is pressed.
                telemetry.addData("Button", "PRESSED");
            } else {
                // button is not pressed.
                telemetry.addData("Button", "NOT PRESSED");
            }

            // If we see the blue jewel we will move forward and hit it off.
            telemetry.addData("teamcode", "red");
            if (sensorColor.blue() > sensorColor.red()) {
                //
                telemetry.addData("move direction", "forward");
            } else {
                // button is not pressed.
                telemetry.addData("move direction", "backwards");
            }


            telemetry.addData("Servo Position", servoTest.getPosition());
        }
    }
}


