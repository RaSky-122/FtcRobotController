package org.firstinspires.ftc.teamcode.tests;

import android.view.KeyEvent;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.libraries.implementations.CollectorImpl;
import org.firstinspires.ftc.teamcode.libraries.hardware.Launcher;
import org.firstinspires.ftc.teamcode.libraries.hardware.WheelMotors;
import org.firstinspires.ftc.teamcode.libraries.software.WheelControl;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;

@Disabled

public class TestHardware extends LinearOpMode {

    List<DcMotor> movementMotors = new ArrayList<>();
    List<HardwareDevice> launcher = new ArrayList<>();

    DcMotor frontLeft, frontRight, backLeft, backRight;
    DcMotor liftMotor, rotaryMotor, launcherMotor;
    Servo loadServo;

    @Override
    public void runOpMode() throws InterruptedException {

        movementMotors = new WheelMotors().initWheels(hardwareMap, DcMotor.ZeroPowerBehavior.FLOAT,
                DcMotorSimple.Direction.FORWARD, DcMotor.RunMode.RUN_USING_ENCODER);

        frontLeft = movementMotors.get(0);
        frontRight = movementMotors.get(1);
        backLeft = movementMotors.get(2);
        backRight = movementMotors.get(3);

        liftMotor = (DcMotor) launcher.get(0);
        rotaryMotor = (DcMotor) launcher.get(1);
        launcherMotor = (DcMotor) launcher.get(2);
        loadServo = (Servo) launcher.get(3);

        DcMotor collector = new CollectorImpl().initCollector(hardwareMap,
                DcMotor.ZeroPowerBehavior.BRAKE, DcMotorSimple.Direction.FORWARD);

        while(!opModeIsActive() && !isStopRequested()){
            telemetry.update();
            telemetry.addData("Waiting for start","");
        }

        new WheelMotors().resetEncoders(movementMotors, DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        new WheelControl().smoothMovement(movementMotors);

    }

}
