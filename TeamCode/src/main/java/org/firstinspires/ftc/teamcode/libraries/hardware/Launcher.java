package org.firstinspires.ftc.teamcode.libraries.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipError;

public class Launcher {

    final static String LAUNCHER_NAME = "launcher";
    final static String TURRET_MOTOR = "turret";
    final static String LIFT_MOTOR = "lift";

    public List<HardwareDevice> initLauncherFull(@NotNull HardwareMap hardwareMap) {

        DcMotor liftMotor = hardwareMap.get(DcMotor.class, LIFT_MOTOR);
        DcMotor rotaryMotor = hardwareMap.get(DcMotor.class, TURRET_MOTOR);
        DcMotor launcherMotor = hardwareMap.get(DcMotor.class, LAUNCHER_NAME);

        Servo loadServo = hardwareMap.get(Servo.class, "loader");

        liftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        rotaryMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        launcherMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        loadServo.setDirection(Servo.Direction.FORWARD);

        liftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rotaryMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        launcherMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rotaryMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        launcherMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        List<HardwareDevice> launcherMotors = new ArrayList<>();

        launcherMotors.add(liftMotor);
        launcherMotors.add(rotaryMotor);
        launcherMotors.add(launcherMotor);
        launcherMotors.add(loadServo);

        return launcherMotors;

    }

    public DcMotor initLauncherWheel(@NotNull HardwareMap hwMap,
                                     String name,
                                     DcMotor.ZeroPowerBehavior zeroBehavior,
                                     DcMotorSimple.Direction direction){

        DcMotor launcher = hwMap.dcMotor.get(name);

        launcher.setZeroPowerBehavior(zeroBehavior);
        launcher.setDirection(direction); //REVERSE
        launcher.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        return launcher;
    }

    public DcMotor initLift(@NotNull HardwareMap hwMap,
                            String name,
                            DcMotor.ZeroPowerBehavior zeroBehavior,
                            DcMotorSimple.Direction direction,
                            DcMotor.RunMode mode){

        DcMotor lift = hwMap.dcMotor.get(name);

        lift.setZeroPowerBehavior(zeroBehavior);
        lift.setDirection(direction);
        lift.setMode(mode);

        return lift;
    }

    public DcMotor initTurret(@NotNull HardwareMap hwMap,
                              String name,
                              DcMotor.ZeroPowerBehavior zeroBehavior,
                              DcMotorSimple.Direction direction,
                              DcMotor.RunMode mode){

        DcMotor turret = hwMap.dcMotor.get(name);

        turret.setZeroPowerBehavior(zeroBehavior);
        turret.setDirection(direction);
        turret.setMode(mode);

        return turret;
    }

}
