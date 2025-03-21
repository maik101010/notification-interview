package org.example.observers;

import org.example.model.Notification;

/**
 * Created by Michael Garcia on 17/03/25
 */
public interface Observer {

  void showNotification(Notification notification);
}
