package org.firstinspires.ftc.teamcode.libraries.software;

import com.qualcomm.robotcore.hardware.DcMotor;

import java.util.List;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;

public class WheelControl {

    private DcMotor frontLeft, frontRight, backLeft, backRight;

    int viteza = 0;
    double power = 0.8;
    double halfPower = 0.5;

    public void smoothMovement(List<DcMotor> wheelMotors) throws InterruptedException {

        frontLeft = wheelMotors.get(0);
        frontRight = wheelMotors.get(1);
        backLeft = wheelMotors.get(2);
        backRight = wheelMotors.get(3);

        if(gamepad1.right_bumper && viteza == 0){
            viteza = 1;
            Thread.sleep(200);
        }
        else
        if(gamepad1.right_bumper && viteza == 1) {
            viteza = 0;
            Thread.sleep(200);
        }

        if(viteza == 0){
            if(gamepad1.dpad_up){
                frontLeft.setPower(power-gamepad1.right_stick_x/2);
                frontRight.setPower(power+gamepad1.right_stick_x/2);
                backRight.setPower(power+gamepad1.right_stick_x/2);
                backLeft.setPower(power-gamepad1.right_stick_x/2);
            }else
            if(gamepad1.dpad_down){
                frontLeft.setPower(-power-gamepad1.right_stick_x/2);
                frontRight.setPower(-power+gamepad1.right_stick_x/2);
                backRight.setPower(-power+gamepad1.right_stick_x/2);
                backLeft.setPower(-power-gamepad1.right_stick_x/2);
            }else
            if(gamepad1.dpad_right){
                frontLeft.setPower(power-gamepad1.right_stick_x/2);
                frontRight.setPower(-power+gamepad1.right_stick_x/2);
                backRight.setPower(power+gamepad1.right_stick_x/2);
                backLeft.setPower(-power-gamepad1.right_stick_x/2);
            }else
            if(gamepad1.dpad_left){
                frontLeft.setPower(-power-gamepad1.right_stick_x/2);
                frontRight.setPower(power+gamepad1.right_stick_x/2);
                backRight.setPower(-power+gamepad1.right_stick_x/2);
                backLeft.setPower(power-gamepad1.right_stick_x/2);
            }else{
                frontLeft.setPower(-gamepad1.right_stick_x+frontLeft.getPower()/4);
                frontRight.setPower(gamepad1.right_stick_x+frontRight.getPower()/4);
                backRight.setPower(gamepad1.right_stick_x+backRight.getPower()/4);
                backLeft.setPower(-gamepad1.right_stick_x+backLeft.getPower()/4);
            }
        }
        else if(viteza == 1){
            if(gamepad1.dpad_up){
                frontLeft.setPower(halfPower-gamepad1.right_stick_x/2);
                frontRight.setPower(halfPower+gamepad1.right_stick_x/2);
                backRight.setPower(halfPower+gamepad1.right_stick_x/2);
                backLeft.setPower(halfPower-gamepad1.right_stick_x/2);
            }else
            if(gamepad1.dpad_down){
                frontLeft.setPower(-halfPower-gamepad1.right_stick_x/2);
                frontRight.setPower(-halfPower+gamepad1.right_stick_x/2);
                backRight.setPower(-halfPower+gamepad1.right_stick_x/2);
                backLeft.setPower(-halfPower-gamepad1.right_stick_x/2);
            }else
            if(gamepad1.dpad_right){
                frontLeft.setPower(halfPower-gamepad1.right_stick_x/2);
                frontRight.setPower(-halfPower+gamepad1.right_stick_x/2);
                backRight.setPower(halfPower+gamepad1.right_stick_x/2);
                backLeft.setPower(-halfPower-gamepad1.right_stick_x/2);
            }else
            if(gamepad1.dpad_left){
                frontLeft.setPower(-halfPower-gamepad1.right_stick_x/2);
                frontRight.setPower(halfPower+gamepad1.right_stick_x/2);
                backRight.setPower(-halfPower+gamepad1.right_stick_x/2);
                backLeft.setPower(halfPower-gamepad1.right_stick_x/2);
            }else{
                frontLeft.setPower(-gamepad1.right_stick_x/2 + frontLeft.getPower()/2);
                frontRight.setPower(gamepad1.right_stick_x/2 + frontRight.getPower()/2);
                backRight.setPower(gamepad1.right_stick_x/2 + backRight.getPower()/2);
                backLeft.setPower(-gamepad1.right_stick_x/2 + backLeft.getPower()/2);
            }
        }

    }

}
