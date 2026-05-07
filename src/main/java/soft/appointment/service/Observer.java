/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package soft.appointment.service;
import soft.appointment.domain.User;

/**
 *
 * *@author MoumenAbuAyyash1
 * Observer interface for the notification system.
 */

public interface Observer {
    void notify(User user, String message);
}
