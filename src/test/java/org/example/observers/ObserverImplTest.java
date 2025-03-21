package org.example.observers;
import static org.junit.Assert.*;
import org.example.events.GameEvents;
import org.example.exceptions.InvalidInputException;
import org.example.model.Notification;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Created by Michael Garcia on 18/03/25
 */
public class ObserverImplTest
{
    private ObserverImpl observer;

    @Before
    public void setUp() throws InvalidInputException
    {
        observer = new ObserverImpl("player1");
    }

    @Test
    public void testEnableNotification() {
        observer.enableNotification(GameEvents.CHALLENGE_COMPLETED);
        assertTrue(observer.isNotificationEnabled(GameEvents.CHALLENGE_COMPLETED));
    }

    @Test
    public void testDisableNotification() {
        observer.enableNotification(GameEvents.CHALLENGE_COMPLETED);
        observer.disableNotification(GameEvents.CHALLENGE_COMPLETED);
        assertFalse(observer.isNotificationEnabled(GameEvents.CHALLENGE_COMPLETED));
    }

    @Test
    public void testReceiveNotificationWhenEnabled() {
        observer.enableNotification(GameEvents.LEVEL_UP);

        Notification notification = new Notification(GameEvents.LEVEL_UP, "You leveled up!", "system", "player2", false);
        observer.showNotification(notification);

        List<Notification> received = observer.getReceivedNotifications();
        assertEquals(1, received.size());
        assertEquals("You leveled up!", received.get(0).message());
    }

}
