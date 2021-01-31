package org.firstinspires.ftc.teamcode.libraries.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.ArrayList;
import java.util.List;

public class Launcher {

    private DcMotor liftMotor, rotaryMotor, launcherMotor;

    private Servo loadServo;

    public List<HardwareDevice> initLauncher(HardwareMap hardwareMap) {

        liftMotor=hardwareMap.get(DcMotor.class, "lift");
        rotaryMotor=hardwareMap.get(DcMotor.class, "rotary");
        launcherMotor=hardwareMap.get(DcMotor.class, "launcher");

        loadServo=hardwareMap.get(Servo.class, "loader");

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

}
