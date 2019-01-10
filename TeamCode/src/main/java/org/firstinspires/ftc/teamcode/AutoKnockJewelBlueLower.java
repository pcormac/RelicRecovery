package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by Cormac on 9/30/2017.
 */

@Autonomous(name = "Auto: Knock Blue Lower", group = "Main")
public class AutoKnockJewelBlueLower extends AutoFunctions {
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void declareDevices() throws InterruptedException {
        super.declareDevices();
    }

    public void runOpMode() throws InterruptedException {

        declareMap();

        servoJewel.setPosition(1); // upper position

        waitForStart();

        servoGrabberLeft.setPosition(.6);
        servoGrabberRight.setPosition(.4);
        sleep(1000);

        moveElevatorUsingEncoder(1, 1375);

        servoJewel.setPosition(jewelDown); // slice the balls
        // color sensor is on the left (from the robot arm's view) of the slicer, robot is backwards
        getColor();

        sleep(1000);

        hitColor("Blue");

        servoJewel.setPosition(1);

        //runForTime(-.45, .45, 750);
        turnUsingEncoders("left", 250);
        sleep(1000);

        //runForTime(1, 1, 800);
        runUsingEncoders(.5, .5, 1325);

        sleep(1000);

        servoGrabberLeft.setPosition(.3);
        servoGrabberRight.setPosition(.7);
        sleep(1000);

        //runForTime(-1, -1, 200);
        runUsingEncoders(-.5, -.5, 150);
        sleep(500);

        // force stop
        leftMotor.setPower(0);
        rightMotor.setPower(0);

        telemetry.addData("Left Encoder: ", leftMotor.getCurrentPosition());
        telemetry.addData("Right Encoder: ", rightMotor.getCurrentPosition());
        telemetry.addData("Elevator Encoder: ", elevator.getCurrentPosition());
        telemetry.update();
        sleep(5000);
    }
}

