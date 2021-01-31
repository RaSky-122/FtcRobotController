package org.firstinspires.ftc.teamcode.libraries.interfaces;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.jetbrains.annotations.NotNull;

public interface Collector {

    /**
     * Returns DcMotor object with parameters DcMotor.ZeroPowerBehavior == BRAKE,
     * DcMotorSimple.Direction == FORWARD, DcMotor.RunMode == RUN_WITHOUT_ENCODER
     * @param hardwareMap passed by the OpMode calling this method
     * @return DcMotor object initialised by initCollector
     */
    DcMotor initCollector(@NotNull HardwareMap hardwareMap);

    /**
     * Returns DcMotor object with parameters DcMotor.ZeroPowerBehavior == USER_DEFINED,
     * DcMotorSimple.Direction == FORWARD, DcMotor.RunMode == RUN_WITHOUT_ENCODER
     * @param hardwareMap passed by the OpMode calling this method
     * @param zeroBehavior the new behavior of the motor when power zero is applied
     * @return DcMotor object initialised by initCollect
     */
     DcMotor initCollector (@NotNull HardwareMap hardwareMap, DcMotor.ZeroPowerBehavior zeroBehavior);

    /**
     * Returns DcMotor object with parameters DcMotor.ZeroPowerBehavior == BRAKE,
     * DcMotorSimple.Direction == USER_DEFINED, DcMotor.RunMode == RUN_WITHOUT_ENCODER
     * @param hardwareMap passed by the OpMode calling this method
     * @param direction the direction to set for this motor
     * @return DcMotor object initialised by initCollect
     */
    public DcMotor initCollector (@NotNull HardwareMap hardwareMap, DcMotorSimple.Direction direction);

    /**
     * Returns DcMotor object with parameters DcMotor.ZeroPowerBehavior == USER_DEFINED,
     * DcMotorSimple.Direction == USER_DEFINED, DcMotor.RunMode == RUN_WITHOUT_ENCODER
     * @param hardwareMap passed by the OpMode calling this method
     * @param zeroBehavior the new behavior of the motor when power zero is applied
     * @param direction the direction to set for this motor
     * @return DcMotor object initialised by initCollect
     */
    public DcMotor initCollector (@NotNull HardwareMap hardwareMap, DcMotor.ZeroPowerBehavior zeroBehavior, DcMotorSimple.Direction direction);
}
