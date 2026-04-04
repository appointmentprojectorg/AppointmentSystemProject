/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package soft.appointment.service;

import java.util.ArrayList;
import java.util.List;
import soft.appointment.domain.User;

/**
 *
 * @author user
 */
public class NotificationManager {
    private List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer o) {
        observers.add(o);
    }

    public void notifyAll(User user, String msg) {
        for (Observer o : observers) {
            o.notify(user, msg);
        }
    }
}