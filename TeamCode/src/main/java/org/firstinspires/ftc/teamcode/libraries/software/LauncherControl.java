package org.firstinspires.ftc.teamcode.libraries.software;

import com.qualcomm.robotcore.hardware.DcMotor;

public class LauncherControl {

    public void launcherRun(DcMotor launcher, double pwr, boolean isOn){

        if(isOn){
            launcher.setPower(pwr);
        }
        else launcher.setPower(0);

    }

    public void liftRun(DcMotor lift, double pwr, boolean up, boolean down, boolean logActivity){

        if(up){
            lift.setPower(pwr);
        }
        else if(down){
            lift.setPower(-pwr);
        }

        if(logActivity == true){

        }

    }
}
