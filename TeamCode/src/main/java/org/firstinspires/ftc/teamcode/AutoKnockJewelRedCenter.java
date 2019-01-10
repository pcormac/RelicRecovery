package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Created by Cormac on 11/6/2017.
 */
@Autonomous(name = "Auto: Knock Red Center", group = "Main")
public class AutoKnockJewelRedCenter extends AutoFunctions {
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

            hitColor("Red");

            servoJewel.setPosition(1);

            //runForTime(-.45, .45, 750);
            turnUsingEncoders("right", 500);
            sleep(1000);

            //runForTime(1, 1, 800);
            runUsingEncoders(.5, .5, 1000);

            sleep(1000);

            turnUsingEncoders("right", 500);

            runForTime(.5, .5, 500);

            servoGrabberLeft.setPosition(.3);
            servoGrabberRight.setPosition(.7);
            sleep(1000);

            //runForTime(-1, -1, 200);
            runUsingEncoders(-.5, -.5, 50);
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


