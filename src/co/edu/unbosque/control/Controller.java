package co.edu.unbosque.control;
import co.edu.unbosque.model.MatrixMultiplier;
import co.edu.unbosque.view.MenuIO;

public class Controller {
        private MatrixMultiplier mm;
        private MenuIO mpu;
    public Controller(){
        //Initialize stuff
        mm = new MatrixMultiplier();
        mpu = new MenuIO();

        //Intro
        mpu.printLogo();
        mpu.printHelpTip();
        mpu.cmdSwitch();
    }

}
