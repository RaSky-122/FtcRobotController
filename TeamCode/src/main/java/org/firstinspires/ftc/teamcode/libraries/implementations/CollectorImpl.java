package org.firstinspires.ftc.teamcode.libraries.implementations;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.libraries.interfaces.Collector;
import org.jetbrains.annotations.NotNull;

public class CollectorImpl implements Collector {

    private DcMotor collector;

    @Override
    public DcMotor initCollector (@NotNull HardwareMap hardwareMap) {

        collector = hardwareMap.get(DcMotor.class, "collector");

        collector.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        collector.setDirection(DcMotorSimple.Direction.FORWARD);
        collector.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        return collector;
    }

    @Override
    public DcMotor initCollector (@NotNull HardwareMap hardwareMap, DcMotor.ZeroPowerBehavior zeroBehavior) {

        collector = hardwareMap.get(DcMotor.class, "collector");

        collector.setZeroPowerBehavior(zeroBehavior);
        collector.setDirection(DcMotorSimple.Direction.FORWARD);
        collector.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        return collector;
    }

    @Override
    public DcMotor initCollector (@NotNull HardwareMap hardwareMap, DcMotorSimple.Direction direction) {

        collector = hardwareMap.get(DcMotor.class, "collector");

        collector.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        collector.setDirection(direction);
        collector.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        return collector;
    }

    @Override
    public DcMotor initCollector (@NotNull HardwareMap hardwareMap, DcMotor.ZeroPowerBehavior zeroBehavior,
                                  DcMotorSimple.Direction direction) {

        collector = hardwareMap.get(DcMotor.class, "collector");

        collector.setZeroPowerBehavior(zeroBehavior);
        collector.setDirection(direction);
        collector.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        return collector;
    }

}
