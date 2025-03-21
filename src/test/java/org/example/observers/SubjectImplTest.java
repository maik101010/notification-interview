package org.example.observers;
import static org.junit.Assert.*;
import org.example.events.GameEvents;
import org.example.model.Notification;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Michael Garcia on 18/03/25
 */
public class SubjectImplTest
{
    private SubjectImpl subject;
    private ObserverImpl observer1;
    private ObserverImpl observer2;
    @Before
    public void setUp()
    {
        subject = new SubjectImpl();
        observer1 = new ObserverImpl("player1");
        observer2 = new ObserverImpl("player2");
        subject.registerObserver(observer1);
        subject.registerObserver(observer2);
    }

    @Test
    public void testObserversRegistered() {
        assertTrue(observer1.getReceivedNotifications().isEmpty());
        assertTrue(observer2.getReceivedNotifications().isEmpty());
    }

    @Test
    public void testNotifyObserverForSpecificRecipient() {
        observer1.enableNotification(GameEvents.CHALLENGE_COMPLETED);

        Notification notification = new Notification(GameEvents.CHALLENGE_COMPLETED, "Mission unlocked!", "system", "player1", false);
        subject.notifyObservers(notification);

        assertEquals(1, observer1.getReceivedNotifications().size());
        assertEquals(0, observer2.getReceivedNotifications().size());
    }

    @Test
    public void testNotifyObserversForGlobalEvent() {
        observer1.enableNotification(GameEvents.LEVEL_UP);
        observer2.enableNotification(GameEvents.LEVEL_UP);

        Notification notification = new Notification(GameEvents.LEVEL_UP, "Global level up!", "system", "player1", true);
        subject.notifyObservers(notification);

        assertEquals(1, observer1.getReceivedNotifications().size());
        assertEquals(1, observer2.getReceivedNotifications().size());
    }

    @Test
    public void testObserverIgnoresDisabledNotifications() {
        observer1.enableNotification(GameEvents.LEVEL_UP);

        Notification notification = new Notification(GameEvents.LEVEL_UP, "You leveled up!", "system", "player1", false);
        subject.notifyObservers(notification);

        assertEquals(1, observer1.getReceivedNotifications().size());
        assertEquals(0, observer2.getReceivedNotifications().size());
    }

}
