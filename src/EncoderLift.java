package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.TouchSensor;

public class EncoderLift extends BlockOpModeCompanion {
  static CRServo slideMotor; // Spark Mini Motor 80:1 gear ratio
  private TouchSensor slideTouch; // Rev Robotics Touch Sensor
  private DcMotor slideEncoder; // Mecanum Motor Port for encoder.
  static double slideSpeed = 0.3;
  static double slideUpperLimit = 5000;
  static double[] slideLevel = {1350, 3000};
}

@ExportToBlocks (
    heading = "Encoder Lift Init",
    color = 255,
    comment = "Initiation of Lift Motors and Homing the Slide and Encoder.",
    parameterLabels = {"Lift Motor", "Encoder Motor", "Limit Switch"}
  )

  public static void encoderLiftInit(String liftMotor, String encoderMotor, String touchSensor) {
    /* Mapping Motors to Input */
    slideMotor = hardwareMap.get(CRServo.class, liftMotor); 
    slideTouch = hardwareMap.get(TouchSensor.class, touchSensor);
    slideEncoder = hardwareMap.get(DcMotor.class, encoderMotor);
    /* Homing the Linear Slide */
    homeSlide();
  }

@ExportToBlocks (
    heading = "Stop Slide Motion",
    color = 255,
    comment = "Completly stopping the slide"
  )

  public static boolean stopSlideMotion() {
    slideMotor.setPower(0);
}

@ExportToBlocks (
  heading = "Home and Zero Slide Encoder",
  color = 255;
  comment = "Moves the slide down until it hits the touch sensor and zeros the encoder"
)

  public static boolean homeSlide() {
    while (!slideTouch.isPressed()) {
        slideMotor.setDirection(DcMotorSimple.Direction.REVERSE); // Running the slide until it hits the touch sensor
        slideMotor.setPower(slideSpeed);
      }
    slideMotor.setPower(0); // Stopping the Slide
    slideEncoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); // Reseting the encoder to 0
    telemetry.addData("Encoder Zero", slideEncoder.getCurrentPosition()); // Adding the encoder data to telemetry for debugging.
    return true;
}

@ExportToBlocks (
    heading = "Move Slide Up",
    color = 255,
    comment = "Moving the Slide Directly Up."
  )

  public static boolean moveSlideUp() {
    if (slideEncoder.getCurrentPosistion < slideUpperLimit) { // Setting limits in code to stop the slide from breaking itself.
      slideMotor.setDirection(DcMotorSimple.Direction.FORWARD); // Sets direction of the motor to forward
      slideMotor.setPower(slideSpeed); // Setting the power to the speed specified above
      return true;
    }
    else {
      return false; // If it reaches the upper limit it will return false meaning it cannot go up higher
    }
    stopSlideMotion(); // Setting the slide power to 0
}

@ExportToBlocks (
    heading = "Move Slide Down",
    color = 255,
    comment = "Moving the Slide Directly Down."
  )

  public static boolean moveSlideDown() {
    if (!slideTouch.isPressed) { // Setting limits in code to stop the slide from breaking itself.
      slideMotor.setDirection(DcMotorSimple.Direction.REVERSE); // Sets direction of the motor to reverse
      slideMotor.setPower(slideSpeed); // Setting the power to the speed specified above
      return true;
    }
    else {
      return false; // If the touch sensor is pressed it will return false meaning it cannot go down further
    }
    stopSlideMotion(); // Setting the slide power to 0
}

