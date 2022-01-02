package frc.robot;

public class RedundantGyro {

    // Make this a singleton, since there will only be on NavX on the RoboRIO's SPI bus.  
    private static RedundantGyro mInstance;

    private enum SelectedGyroEnum { eNavx, eBNO055 }; 
    private SelectedGyroEnum mSelectedGyro = SelectedGyroEnum.eNavx;

    private final NaxX_Gyro mNaxX_Gyro;
    private final BNO055_Gyro mBNO055_Gyro;

    private RedundantGyro() {
        mNaxX_Gyro = NaxX_Gyro.getInstance();
//        mBNO055_Gyro = BNO055_Gyro.getInstance(BNO055_Gyro.opmode_t.OPERATION_MODE_IMUPLUS,
        mBNO055_Gyro = BNO055_Gyro.getInstance(BNO055_Gyro.opmode_t.OPERATION_MODE_NDOF,
		BNO055_Gyro.vector_type_t.VECTOR_EULER);
    }

    public void RedundantGyroInit() {
        mNaxX_Gyro.NavxInit();
        mBNO055_Gyro.BNO055_Init();
    }

    public void RedundantGyroPeriodic(){
        mNaxX_Gyro.NavxPeriodic();
        mBNO055_Gyro.BNO055_Periodic();
    }

    public double getHeading() {
        if (!mNaxX_Gyro.mNavx.isConnected() || mNaxX_Gyro.mNavx.isCalibrating()) {
            mSelectedGyro = SelectedGyroEnum.eBNO055;
            System.out.println(" ");
            System.out.println("ERROR: NAVX FAIL: Switched to BNO055 gyro.");
            System.out.println(" ");
            if (!mBNO055_Gyro.isSensorPresent() || !mBNO055_Gyro.isInitialized() || mBNO055_Gyro.isCalibrated() ) {
                System.out.println(" ");
                System.out.println("ERROR: Both NAVX and BNO055 gyros not ready! Using BNO055. (pres/init/cal): " + mBNO055_Gyro.isSensorPresent() + ", " + mBNO055_Gyro.isInitialized() + " , " + mBNO055_Gyro.isCalibrated() ) ;
                System.out.println(" ");
                //TODO: Put better error handling here. 
            }
        } else {
            mSelectedGyro = SelectedGyroEnum.eNavx;
        }
        switch (mSelectedGyro) {
            case eNavx:
                return (mNaxX_Gyro.getHeading());
            case eBNO055:
                return (mBNO055_Gyro.getHeadingRangedLikeNavX());
            default:
                return (0.0);
        }
    }

    public void showBothHeadings() {
        System.out.println("Headings: NavX: " + mNaxX_Gyro.getHeading() + " , BNO055: " + mBNO055_Gyro.getHeadingRangedLikeNavX() + " , unified call: " + getHeading());
    }

    public static RedundantGyro getInstance() {
        if(mInstance == null)
            mInstance = new RedundantGyro();
        return mInstance;
    }
    
}
