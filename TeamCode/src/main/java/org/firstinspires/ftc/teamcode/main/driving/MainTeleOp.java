package org.firstinspires.ftc.teamcode.main.driving;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.libraries.hardware.Launcher;
import org.firstinspires.ftc.teamcode.libraries.hardware.WheelMotors;
import org.firstinspires.ftc.teamcode.libraries.implementations.CollectorImpl;
import org.firstinspires.ftc.teamcode.libraries.implementations.GeneralInitImpl;
import org.firstinspires.ftc.teamcode.libraries.software.WheelControl;

import java.util.ArrayList;
import java.util.List;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;

@TeleOp(name = "Main", group = "main")

public class MainTeleOp extends LinearOpMode {

    int viteza = 0;
    double power = 0.8;
    double halfPower = 0.5;

    enum ControlType {
        TOGGLE,
        HOLD
    }

    ElapsedTime runTime = new ElapsedTime();

    static final String LAUNCHER_MOTOR = "launcher";
    static final String TURRET_MOTOR = "turret";
    static final String LIFT_MOTOR = "lift";
    static final String LOADING_SERVO = "loader";

    Launcher launcherInit = new Launcher();

    private DcMotor frontLeft, frontRight, backLeft, backRight;
    List<DcMotor> driveMotors = new ArrayList<>();

    private DcMotor liftMotor, turretMotor, launcherWheelMotor, collectorMotor;

    private Servo loadServo;

    @Override
    public void runOpMode() throws InterruptedException {

        liftMotor = launcherInit.initLift(hardwareMap,
                LIFT_MOTOR,
                DcMotor.ZeroPowerBehavior.BRAKE,
                DcMotorSimple.Direction.REVERSE,
                DcMotor.RunMode.RUN_USING_ENCODER);

        turretMotor = launcherInit.initTurret(hardwareMap,
                TURRET_MOTOR,
                DcMotor.ZeroPowerBehavior.BRAKE,
                DcMotorSimple.Direction.FORWARD,
                DcMotor.RunMode.RUN_USING_ENCODER);

        launcherWheelMotor = launcherInit.initLauncherWheel(hardwareMap,
                LAUNCHER_MOTOR,
                DcMotor.ZeroPowerBehavior.FLOAT,
                DcMotorSimple.Direction.REVERSE);

        loadServo = new GeneralInitImpl().initServo(hardwareMap,
                LOADING_SERVO,
                Servo.Direction.FORWARD,
                1-0.55, 1-0.08);
        loadServo.setPosition(1);

        collectorMotor = new CollectorImpl().initCollector(hardwareMap,
                DcMotor.ZeroPowerBehavior.BRAKE,
                DcMotorSimple.Direction.REVERSE);

        driveMotors = new WheelMotors().initWheels(hardwareMap, DcMotor.ZeroPowerBehavior.FLOAT, DcMotorSimple.Direction.FORWARD, DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontLeft = driveMotors.get(0); frontRight = driveMotors.get(1);
        backLeft = driveMotors.get(2); backRight = driveMotors.get(3);

        while(!opModeIsActive() && !isStopRequested()){
            telemetry.addData("Waiting for start", "");
            telemetry.update();
        }

        while(opModeIsActive()) {

            smoothMovement(); /* uses gamepad1: dpad, right stick */
            launcherRun(true); /* uses gamepad1: right trigger */
            loadRing(true); /* uses gamepad1: right bumper */
            collectorRun(true); /* uses gamepad1: a */
            liftRun(true); /* uses gamepad1: left stick */

            telemetry.update();
        }
    }

    public void launcherRun(boolean getLogs){

        if(gamepad1.right_trigger >= 0.4 && launcherWheelMotor.getPower() == 0){
            launcherWheelMotor.setPower(0.6);
            sleep(200);
        }
        else if(gamepad1.right_trigger >= 0.4 && launcherWheelMotor.getPower() != 0){
            launcherWheelMotor.setPower(0);
            sleep(200);
        }

        if(getLogs){
            telemetry.addData("Motor power level ", launcherWheelMotor.getPower());
        }
    }

    public void loadRing(boolean getLogs){

        if(gamepad1.x && loadServo.getPosition() != 0){
            runTime.reset();
            loadServo.setPosition(0);
            sleep(200);
        }
        else if (loadServo.getPosition() != 1 && runTime.milliseconds() > 500){
            loadServo.setPosition(1);
        }

        if(getLogs){
            telemetry.addData("Load servo position ", loadServo.getPosition());
        }
    }

    public void collectorRun(boolean getLogs){

        if(gamepad1.a && collectorMotor.getPower() == 0){
            collectorMotor.setPower(1);
            sleep(200);
        }
        else if(gamepad1.a && collectorMotor.getPower() != 0){
            collectorMotor.setPower(0);
            sleep(200);
        }

        if(getLogs){
            telemetry.addData("Collector power ", collectorMotor.getPower());
        }
    }

    public void liftRun(boolean getLogs){

        if(-gamepad1.left_stick_y > 0.3){
            liftMotor.setPower(1);
            sleep(200);
        }
        else if(-gamepad1.left_stick_y < -0.3){
            liftMotor.setPower(-1);
            sleep(200);
        }
        else liftMotor.setPower(0);

        if(getLogs){
            telemetry.addData("Lift power ", liftMotor.getPower());
            telemetry.addData("Lift encoder ", liftMotor.getCurrentPosition());
        }
    }

    public void smoothMovement() throws InterruptedException {

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
                frontLeft.setPower(power+gamepad1.right_stick_x/2);
                frontRight.setPower(power-gamepad1.right_stick_x/2);
                backRight.setPower(power-gamepad1.right_stick_x/2);
                backLeft.setPower(power+gamepad1.right_stick_x/2);
            }else
            if(gamepad1.dpad_down){
                frontLeft.setPower(-power+gamepad1.right_stick_x/2);
                frontRight.setPower(-power-gamepad1.right_stick_x/2);
                backRight.setPower(-power-gamepad1.right_stick_x/2);
                backLeft.setPower(-power+gamepad1.right_stick_x/2);
            }else
            if(gamepad1.dpad_right){
                frontLeft.setPower(power+gamepad1.right_stick_x/2);
                frontRight.setPower(-power-gamepad1.right_stick_x/2);
                backRight.setPower(power-gamepad1.right_stick_x/2);
                backLeft.setPower(-power+gamepad1.right_stick_x/2);
            }else
            if(gamepad1.dpad_left){
                frontLeft.setPower(-power+gamepad1.right_stick_x/2);
                frontRight.setPower(power-gamepad1.right_stick_x/2);
                backRight.setPower(-power-gamepad1.right_stick_x/2);
                backLeft.setPower(power+gamepad1.right_stick_x/2);
            }else{
                frontLeft.setPower(+gamepad1.right_stick_x+frontLeft.getPower()/4);
                frontRight.setPower(-gamepad1.right_stick_x+frontRight.getPower()/4);
                backRight.setPower(-gamepad1.right_stick_x+backRight.getPower()/4);
                backLeft.setPower(+gamepad1.right_stick_x+backLeft.getPower()/4);
            }
        }
        else if(viteza == 1){
            if(gamepad1.dpad_up){
                frontLeft.setPower(halfPower+gamepad1.right_stick_x/2);
                frontRight.setPower(halfPower-gamepad1.right_stick_x/2);
                backRight.setPower(halfPower-gamepad1.right_stick_x/2);
                backLeft.setPower(halfPower+gamepad1.right_stick_x/2);
            }else
            if(gamepad1.dpad_down){
                frontLeft.setPower(-halfPower+gamepad1.right_stick_x/2);
                frontRight.setPower(-halfPower-gamepad1.right_stick_x/2);
                backRight.setPower(-halfPower-gamepad1.right_stick_x/2);
                backLeft.setPower(-halfPower+gamepad1.right_stick_x/2);
            }else
            if(gamepad1.dpad_right){
                frontLeft.setPower(halfPower+gamepad1.right_stick_x/2);
                frontRight.setPower(-halfPower-gamepad1.right_stick_x/2);
                backRight.setPower(halfPower-gamepad1.right_stick_x/2);
                backLeft.setPower(-halfPower+gamepad1.right_stick_x/2);
            }else
            if(gamepad1.dpad_left){
                frontLeft.setPower(-halfPower+gamepad1.right_stick_x/2);
                frontRight.setPower(halfPower-gamepad1.right_stick_x/2);
                backRight.setPower(-halfPower-gamepad1.right_stick_x/2);
                backLeft.setPower(halfPower+gamepad1.right_stick_x/2);
            }else{
                frontLeft.setPower(+gamepad1.right_stick_x/2 + frontLeft.getPower()/2);
                frontRight.setPower(-gamepad1.right_stick_x/2 + frontRight.getPower()/2);
                backRight.setPower(-gamepad1.right_stick_x/2 + backRight.getPower()/2);
                backLeft.setPower(+gamepad1.right_stick_x/2 + backLeft.getPower()/2);
            }
        }

    }

    /*
     * possible control scheme:
     *      - Wheels: dpad, right stick
     *      - Servo: right bumper
     *      - LauncherWheel: right trigger
     *      - Collector: a
     *      Lift: left stick
     */
}
