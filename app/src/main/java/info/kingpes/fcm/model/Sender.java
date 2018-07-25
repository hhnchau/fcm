package info.kingpes.fcm.model;

/**
 * Created by Chau Huynh on 7/24/2018.
 */

public class Sender {
    public Notification notification;
    public String to;

    public Sender() {
    }

    public Sender(String to, Notification notification) {
        this.notification = notification;
        this.to = to;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
