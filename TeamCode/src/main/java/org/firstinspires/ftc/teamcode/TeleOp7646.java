/*
Copyright (c) 2016 Robert Atkinson

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Robert Atkinson nor the names of his contributors may be used to
endorse or promote products derived from this software without specific prior
written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESSFOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.I2cDevice;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.DigitalChannel;

import org.firstinspires.ftc.robotcontroller.external.samples.ConceptTelemetry;

@TeleOp(name="TeleOp7646", group="Iterative Opmode")
public class TeleOp7646 extends OpMode {
    /* Declare OpMode members. */
    private ElapsedTime runtime = new ElapsedTime();

    // Device declarations
    private DcMotor leftMotor = null;
    private DcMotor rightMotor = null;
    private DcMotor elevator = null;
    private DcMotor escalator = null; // get it?
    private Servo servoGrabberLeft = null;
    private Servo servoGrabberRight = null;
    private Servo servoJewel = null;

    DigitalChannel elevatorTouch = null;
    //TouchSensor escalatorTouch = null;
    ColorSensor colorSensor = null;

    double elevatorPower = 0;
    double escalatorPower = 0;

    String color;

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");
        // get motors
        leftMotor = hardwareMap.dcMotor.get("leftMotor");
        rightMotor = hardwareMap.dcMotor.get("rightMotor");
        elevator = hardwareMap.dcMotor.get("elevator");
        escalator = hardwareMap.dcMotor.get("escalator");

        // get servos
        servoGrabberLeft = hardwareMap.servo.get("servoGrabberLeft");
        servoGrabberRight = hardwareMap.servo.get("servoGrabberRight");

        servoJewel = hardwareMap.servo.get("servoJewel");

        // Reverse the motor that runs backwards when connected directly to the battery
        leftMotor.setDirection(DcMotor.Direction.REVERSE); // Set to REVERSE if using AndyMark motors
        rightMotor.setDirection(DcMotor.Direction.FORWARD);
        elevator.setDirection(DcMotor.Direction.FORWARD);
        escalator.setDirection(DcMotor.Direction.REVERSE);

        leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        elevator.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        elevator.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        leftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        elevator.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        elevatorTouch = hardwareMap.get(DigitalChannel.class, "elevatorTouch");
        //escalatorTouch = hardwareMap.touchSensor.get("escalatorTouch");
        colorSensor = hardwareMap.get(ColorSensor.class, "colorSensor");

        elevatorTouch.setMode(DigitalChannel.Mode.INPUT);

        colorSensor.enableLed(true);

        servoJewel.setPosition(1);
        servoGrabberLeft.setPosition(.35);
        servoGrabberRight.setPosition(.65);

        telemetry.addData("Status", "Initialized");
    }

    @Override
    public void init_loop() {}

    /*
     * Code to run ONCE when the driver hits PLAY
     */

    @Override
    public void start() {
        runtime.reset();
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        telemetry.addData("Status", "Running: " + runtime.toString());


        /* Controls
        Game pad 1
        Left Stick    = Left drive
        Right Stick   = Right Drive
        A             = Grab cube
        B             = Release cube
        Y             =
        X             =
        Left Bumper   = .25 speed
        Right Bumper  =

        Game pad 2
        Left Stick    = Y=Elevator X=Escalator
        Right Stick   =
        A             =
        B             =
        Y             =
        X             =
        Left Bumper   =
        Right Bumper  =
        */


        // Tank drive
        if (gamepad1.left_stick_y < .25 && gamepad1.left_stick_y > 0) {
            leftMotor.setPower(0);
        } else if (gamepad1.left_stick_y > -.25 && gamepad1.left_stick_y < 0) {
            leftMotor.setPower(0);
        } else if (gamepad1.dpad_up) {
            leftMotor.setPower(1);
        }
        else if (gamepad1.dpad_down) {
            leftMotor.setPower(-1);
        }
        else {
            if (gamepad1.left_bumper) {
                leftMotor.setPower(.5*-gamepad1.left_stick_y);
            }
            else {
                leftMotor.setPower(-gamepad1.left_stick_y);
            }
        }

        if (gamepad1.right_stick_y < .25 && gamepad1.right_stick_y > 0) {
            rightMotor.setPower(0);
        } else if (gamepad1.right_stick_y > -.25&& gamepad1.right_stick_y < 0) {
            rightMotor.setPower(0);
        } else if (gamepad1.dpad_up) {
            rightMotor.setPower(1);
        }
        else if (gamepad1.dpad_down) {
            rightMotor.setPower(-1);
        }
        else {
            if (gamepad1.left_bumper) {
                rightMotor.setPower(.5*gamepad1.right_stick_y);
            }
            else {
                rightMotor.setPower(gamepad1.right_stick_y);
            }
        }

        // Manually grab
        if (gamepad1.a) {
            servoGrabberLeft.setPosition(.35);
            servoGrabberRight.setPosition(.65);
        } else if (gamepad1.b) {
            servoGrabberLeft.setPosition(.6);
            servoGrabberRight.setPosition(.4);
        }

        // Jewel kicker
        if (gamepad1.x) {
            servoJewel.setPosition(.3);
        } else {
            servoJewel.setPosition(.95);
        }
        //
        //
        //        JOY 2
        //
        //
        // elevator
        if (elevatorTouch.getState()) {
            // elevator
            elevatorPower = -gamepad2.left_stick_y;
            elevator.setPower(elevatorPower);
        }
        else {
            elevatorPower = .5;
            elevator.setPower(elevatorPower);
        }

        // escalator
        escalatorPower = -gamepad2.left_stick_x;
        escalator.setPower(escalatorPower);



        if ((colorSensor.red() > colorSensor.blue()) && (colorSensor.red() > colorSensor.alpha())) {
            color = "Red";
        }
        else if ((colorSensor.blue() > colorSensor.red()) && (colorSensor.blue() > colorSensor.alpha())) {
            color = "Blue";
        }
        else {
            color = "Alpha";
        }

        // end of code, update telemetry
        telemetry.addData("Right: ", gamepad1.right_stick_y);
        telemetry.addData("Left: ", gamepad1.left_stick_y);
        telemetry.addData("Slow: ", gamepad1.left_bumper);
        telemetry.addData("Elevator: ", elevator.getPower());
        telemetry.addData("Elevator Touch: ", elevatorTouch.getState());
        telemetry.addData("Left Encoder", leftMotor.getCurrentPosition());
        telemetry.addData("Right Encoder", -rightMotor.getCurrentPosition());
        telemetry.addData("Lift Encoder", elevator.getCurrentPosition());
        telemetry.addData("Color: ", color);
        telemetry.update();
    }

    @Override
    public void stop() {
    }

}
