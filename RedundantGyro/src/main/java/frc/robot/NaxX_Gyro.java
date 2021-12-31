package frc.robot;

//import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.SPI;
import com.kauailabs.navx.frc.AHRS;

public class NaxX_Gyro {

  // Make this a singleton, since there will only be on NavX on the RoboRIO's SPI bus.  
  private static NaxX_Gyro mInstance;

  // Uncomment ony the corresponding import above.
  //  public final AHRS mNavx = new AHRS(Port.kMXP);
  public final AHRS mNavx = new AHRS(SPI.Port.kMXP);

  private NaxX_Gyro() {
    super(); 
  }
  
  public void NavxInit(){
    System.out.println("--NavxInit--NAVX(firmware version): " + mNavx.getFirmwareVersion() + " ------");
    
    System.out.println("--NavxInit--NAVX(calibrating,conected): " + mNavx.isCalibrating() + " , " + mNavx.isConnected() + " ------");
    //Timer.delay(5);
    mNavx.calibrate();
    System.out.println("--NavxInit--NAVX(calibrating,conected): " + mNavx.isCalibrating() + " , " + mNavx.isConnected() + " ------");
    //Timer.delay(5);
    System.out.println("--NavxInit--NAVX(calibrating,conected): " + mNavx.isCalibrating() + " , " + mNavx.isConnected() + " ------");
    System.out.println("-NavxInit--NAVX calibration should be complete now.");
    System.out.println("--NavxInit--NAVX(calibrating,conected,angle): " + mNavx.isCalibrating() + " , " + mNavx.isConnected() + " , " + mNavx.getAngle());  
    mNavx.reset();
    System.out.println("--NavxInit--NAVX(calibrating,conected,angle): " + mNavx.isCalibrating() + " , " + mNavx.isConnected() + " , " + mNavx.getAngle());  
  }

  public void NavxPeriodic(){
    System.out.println("--NavxPeriodic--NAVX(calibrating,conected,angle): " + mNavx.isCalibrating() + " , " + mNavx.isConnected() + " , " + mNavx.getAngle());  
  }

  public static NaxX_Gyro getInstance() {
    if(mInstance == null)
        mInstance = new NaxX_Gyro();
    return mInstance;
  }

  public double getHeading() {
    final double heading = mNavx.getYaw();
    if(heading == 0.0) {
        return 0.0; //prevents a -0.0 reading
    }
    return -heading;
  }

  public void zero() {
    mNavx.zeroYaw();
  }

}
