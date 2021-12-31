package frc.robot;

public class RedundantGyro {

    private enum SelectedGyroEnum { eNavx, eBNO055 }; 
    private SelectedGyroEnum mSelectedGyro;

    private final NaxX_Gyro mNaxX_Gyro;

    public RedundantGyro(){
        mNaxX_Gyro = NaxX_Gyro.getInstance();

    }

    public void RedundantGyroInit(){
        mNaxX_Gyro.NavxInit();
    }

    public void RedundantGyroPeriodic(){
        mNaxX_Gyro.NavxPeriodic();
    }

    public double getAngle() {
        switch (mSelectedGyro) {
            case eNavx:
                return (mNaxX_Gyro.getHeading());
            case eBNO055:
                return (0.0);
            default:
                return (0.0);
        }
    }

}
