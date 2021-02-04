package org.firstinspires.ftc.teamcode.main.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.libraries.hardware.WheelMotors;
import org.firstinspires.ftc.teamcode.libraries.implementations.GeneralInitImpl;

import java.util.ArrayList;
import java.util.List;

@Autonomous(name = "Wall 3 ring auton", group = "test")

public class Auton3Rings_Experimental extends LinearOpMode {

    private Servo loadServo;

    private DcMotorEx launcherWheelMotor;
    private DcMotor liftMotor;

    private DcMotor frontLeft, frontRight, backLeft, backRight;
    private List<DcMotor> driveMotors = new ArrayList<>();

    @Override
    public void runOpMode() throws InterruptedException {

        loadServo = new GeneralInitImpl().initServo(hardwareMap, "loader", Servo.Direction.FORWARD);

        launcherWheelMotor = new GeneralInitImpl().initExMotor(hardwareMap, "launcher", DcMotor.ZeroPowerBehavior.FLOAT,
                DcMotorSimple.Direction.REVERSE, DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        launcherWheelMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        launcherWheelMotor.setVelocityPIDFCoefficients(700, 0.7,130, 0);

        liftMotor = new GeneralInitImpl().initMotor(hardwareMap, "lift", DcMotor.ZeroPowerBehavior.BRAKE,
                DcMotorSimple.Direction.REVERSE, DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftMotor.setTargetPosition(4400);
        liftMotor.setPower(0);
        liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        driveMotors = new WheelMotors().initWheels(hardwareMap, DcMotor.ZeroPowerBehavior.BRAKE,
                DcMotorSimple.Direction.FORWARD, DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft = driveMotors.get(0); frontRight = driveMotors.get(1);
        backLeft = driveMotors.get(2); backRight = driveMotors.get(3);

        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        while(!opModeIsActive() && !isStopRequested()){
            telemetry.addData("Waiting for ", "start");
            telemetry.update();
        }

        if(opModeIsActive()){

            loadServo.setPosition(9/180.0);

            liftMotor.setPower(1);
            while(liftMotor.isBusy()) {
                liftMotor.setPower(1);
            }

            liftMotor.setPower(0);

            //move
            while(overallWheelEnc() <= 710 && !isStopRequested()) {
                sideways(1);
            }

            wheelStop();

            launcherWheelMotor.setVelocity(1500);

            front(1, 3550);

            wheelStop();

            while(overallWheelEnc() <= 150 && !isStopRequested()) {
                sideways(1);
            }

            wheelStop();

            for(int i = 0; i <= 6; i++) {
                loadServo.setPosition(33 / 180.0);
                sleep(800);
                loadServo.setPosition(7 / 180.0);
                sleep(500);
            }
            launcherWheelMotor.setPower(0);
            loadServo.setPosition(9/180.0);

            liftMotor.setTargetPosition(25);
            liftMotor.setPower(1);
            while(liftMotor.isBusy()){
                liftMotor.setPower(1);
            }
            liftMotor.setPower(0);

            //move
            front(1, 800);

            wheelStop();

        }
    }

    private void sideways(double power) {

        backLeft.setPower(-power);
        frontRight.setPower(-power);
        backRight.setPower(power);
        frontLeft.setPower(power);
    }

    private double overallWheelEnc(){
        double average =
                Math.abs(frontLeft.getCurrentPosition()) + Math.abs(frontRight.getCurrentPosition())
                        + Math.abs(backLeft.getCurrentPosition()) + Math.abs(backRight.getCurrentPosition());
        return average/4;
    }

    private void resetWheelEnoders(){

        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    private void wheelStop(){
        frontRight.setPower(0);
        frontLeft.setPower(0);
        backRight.setPower(0);
        backLeft.setPower(0);

        resetWheelEnoders();
    }

    private void front(double power, long target){

        while (((Math.abs(overallWheelEnc())) <= Math.abs(target) || target == 0) && !isStopRequested()) {

            frontRight.setPower(power);
            frontLeft.setPower(power);
            backRight.setPower(power);
            backLeft.setPower(power);
        }
    }
}
