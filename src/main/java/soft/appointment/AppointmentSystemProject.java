/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package soft.appointment;

import com.formdev.flatlaf.FlatDarculaLaf;
import soft.appointment.presentation.MainLoginGui;

/**
 *
 * @author user
 */
public class AppointmentSystemProject {

    public static void main(String[] args) {
        FlatDarculaLaf.setup();
        MainLoginGui mine = new MainLoginGui();
        mine.setVisible(true);

    }
}
