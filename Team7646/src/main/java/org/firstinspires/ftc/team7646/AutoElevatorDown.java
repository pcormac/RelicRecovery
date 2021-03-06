package org.firstinspires.ftc.team7646;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by Cormac on 12/6/2016.
 */
@Autonomous(name="Auto: Elevator Down", group="Main")
public class AutoElevatorDown extends AutoFunctions {
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void declareDevices() throws InterruptedException {
        super.declareDevices();
    }

    static double odsStart;

    public void runOpMode() throws InterruptedException {

        declareMap();

        waitForStart();

        servoGrabberLeft.setPosition(.6);
        servoGrabberRight.setPosition(.4);

        elevatorDown();

        elevator.setPower(0);

        servoGrabberLeft.setPosition(.3);
        servoGrabberRight.setPosition(.7);
        sleep(250);

        telemetry.addData("AutoStatus: ", "Elevator down");
        telemetry.update();
    }

    // new code


}
