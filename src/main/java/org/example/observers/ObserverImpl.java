package org.example.observers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.example.events.EventType;
import org.example.exceptions.InvalidInputException;
import org.example.model.Notification;

/**
 * Created by Michael Garcia on 17/03/25
 */
public class ObserverImpl implements Observer
{

  private final String userId;
  private final Set<EventType> enabledNotifications = new HashSet<>();
  private final List<Notification> receivedNotifications = new ArrayList<>();

  public ObserverImpl(String userId)
  {
    this.userId = userId;
  }
  public String getUserId() {
    return userId;
  }

  public void enableNotification(EventType type) {
    enabledNotifications.add(type);
  }

  public void disableNotification(EventType type) {
    enabledNotifications.remove(type);
  }

  public boolean isNotificationEnabled(EventType type) {
    return enabledNotifications.contains(type);
  }

  @Override
  public void showNotification(Notification notification) {
    receivedNotifications.add(notification);
    System.out.println("User " + userId + " received notification: " + notification.message());
  }

  public List<Notification> getReceivedNotifications() {
    return receivedNotifications;
  }
}
