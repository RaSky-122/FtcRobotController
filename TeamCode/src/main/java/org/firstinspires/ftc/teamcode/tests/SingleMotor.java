package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "Single Motor Test", group = "test")

public class SingleMotor extends LinearOpMode {

    private DcMotor motor;

    @Override
    public void runOpMode() throws InterruptedException{

        motor = hardwareMap.get(DcMotor.class, "motor");

        while(!opModeIsActive() && !isStopRequested()) {
            telemetry.addData("Waiting for start", "");
            telemetry.update();
        }

        motor.setDirection(DcMotorSimple.Direction.FORWARD);
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        boolean isPressed = false;
        double motorPower = 0;

        while(opModeIsActive()){
            
            if(gamepad1.dpad_up && motor.getPower() < 1 && !isPressed){
                motorPower += 0.1;
                motor.setPower(motorPower);
                isPressed = true;
            }
            else if (gamepad1.dpad_down && motor.getPower() > -1 && !isPressed){
                motorPower -= 0.1;
                motor.setPower(motorPower);
                isPressed = true;
            }
            else if(!gamepad1.dpad_down && !gamepad1.dpad_up)
                isPressed = false;

            if(gamepad1.dpad_left){
                motor.setPower(0);
                motorPower = 0;
            }

            telemetry.addData("Set power: ", motorPower);
            telemetry.addData("Actual power: ", motor.getPower());
            telemetry.update();
        }
    }
}
