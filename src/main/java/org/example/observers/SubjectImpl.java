package org.example.observers;

import org.example.model.Notification;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Michael Garcia on 18/03/25
 */
public class SubjectImpl implements Subject
{
    private final List<Observer> observers = new CopyOnWriteArrayList<>();

    @Override
    public void registerObserver(Observer o)
    {
        observers.add(o);
    }

    @Override
    public void notifyObservers(Notification notification)
    {
        for (Observer observer : observers)
        {
            if (observer instanceof ObserverImpl userObs)
            {
                boolean enabled = userObs.isNotificationEnabled(notification.type());
                boolean isTarget = notification.isBroadcast() || userObs.getUserId().equals(notification.recipient());

                if (enabled && isTarget)
                {
                    userObs.showNotification(notification);
                }
            }
            else
            {
                observer.showNotification(notification);
            }
        }
    }
}
