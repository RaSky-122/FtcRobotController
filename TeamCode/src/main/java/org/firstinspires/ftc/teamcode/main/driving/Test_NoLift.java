package org.firstinspires.ftc.teamcode.main.driving;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.libraries.hardware.Gyroscope;
import org.firstinspires.ftc.teamcode.libraries.hardware.WheelMotors;
import org.firstinspires.ftc.teamcode.libraries.implementations.GeneralInitImpl;

import java.util.ArrayList;
import java.util.List;

@TeleOp(name = "Driving", group = "main")

public class Test_NoLift extends LinearOpMode {


    int viteza = 0;
    double power = 0.8;
    double halfPower = 0.5;

    ElapsedTime runTime = new ElapsedTime();

    static final double INITIAL_ANGLE = 45;

    static final String COLLECTOR_MOTOR = "collector";
    static final String LAUNCHER_MOTOR = "launcher";
    static final String TURRET_MOTOR = "turret";
    static final String LIFT_MOTOR = "lift";
    static final String LOADING_SERVO = "loader";

    private GeneralInitImpl init = new GeneralInitImpl();

    private DcMotor frontLeft, frontRight, backLeft, backRight;
    List<DcMotor> driveMotors = new ArrayList<>();

    private DcMotor liftMotor, turretMotor, collectorMotor;
    private DcMotorEx launcherWheelMotor;

    private Servo loadServo;

    private BNO055IMU imu;

    @Override
    public void runOpMode() throws InterruptedException {

        imu = new Gyroscope().initIMU(hardwareMap);

        turretMotor = init.initMotor(hardwareMap,
                TURRET_MOTOR,
                DcMotor.ZeroPowerBehavior.BRAKE,
                DcMotorSimple.Direction.FORWARD,
                DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        launcherWheelMotor = init.initExMotor(hardwareMap,
                LAUNCHER_MOTOR,
                DcMotor.ZeroPowerBehavior.FLOAT,
                DcMotorSimple.Direction.FORWARD,
                DcMotor.RunMode.RUN_USING_ENCODER);
        launcherWheelMotor.setVelocityPIDFCoefficients(700, 0.7,130, 5);

        loadServo = init.initServo(hardwareMap,
                LOADING_SERVO,
                Servo.Direction.FORWARD);
        loadServo.setPosition(INITIAL_ANGLE/180.0);

        collectorMotor = init.initMotor(hardwareMap,
                COLLECTOR_MOTOR,
                DcMotor.ZeroPowerBehavior.BRAKE,
                DcMotorSimple.Direction.REVERSE,
                DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        driveMotors = new WheelMotors().initWheels(hardwareMap,
                DcMotor.ZeroPowerBehavior.BRAKE,
                DcMotorSimple.Direction.FORWARD,
                DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontLeft = driveMotors.get(0); frontRight = driveMotors.get(1);
        backLeft = driveMotors.get(2); backRight = driveMotors.get(3);

        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        while(!opModeIsActive() && !isStopRequested()){
            telemetry.addData("Waiting for start", "");
            telemetry.update();
        }

        while(opModeIsActive()) {

            telemetry.addData("~~~~~~~~~~~~~~~~~~~~~~~~", "");

            smoothMovement(false); /* uses gamepad1: dpad, right stick */
            launcherRun(true); /* uses gamepad1: right trigger */
            loadRing(false); /* uses gamepad1: right bumper */
            collectorRun(false); /* uses gamepad1: a */

            telemetry.update();

        }
    }

    public void launcherRun(boolean getLogs){

        if(gamepad1.right_trigger >= 0.7 && launcherWheelMotor.getPower() == 0){
            //launcherWheelMotor.setVelocity(700); //1500 driving
            launcherWheelMotor.setPower(0.4);
            sleep(200);
        }
        else if(gamepad1.right_trigger >= 0.7 && launcherWheelMotor.getPower() != 0){
            //launcherWheelMotor.setVelocity(0);
            launcherWheelMotor.setPower(0);
            sleep(200);
        }

        if(getLogs){
            telemetry.addData("Launcher velocity ", launcherWheelMotor.getVelocity());
        }
    }

    public void loadRing(boolean getLogs){

        if(gamepad1.x && loadServo.getPosition() > 40/180.0){
            runTime.reset();
            loadServo.setPosition(25/180.0);
            sleep(200);
        }
        else if (loadServo.getPosition() < 40/180.0 && runTime.milliseconds() > 350){
            loadServo.setPosition(INITIAL_ANGLE/180.0);
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
        else if(gamepad1.a && collectorMotor.getPower() == 1){
            collectorMotor.setPower(-1);
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

    public void smoothMovement(boolean getLogs) throws InterruptedException {

        if(getLogs){
            telemetry.addData("~~~~~~~~~~~~~~~~~~~~", "");
            telemetry.addData("Front left ", frontLeft.getCurrentPosition());
            telemetry.addData("Front right ", frontRight.getCurrentPosition());
            telemetry.addData("Back left ", backLeft.getCurrentPosition());
            telemetry.addData("Back right ", backRight.getCurrentPosition());
            telemetry.addData("~~~~~~~~~~~~~~~~~~~~", "");
        }

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
