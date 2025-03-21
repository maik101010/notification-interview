package org.example.service;

import org.example.events.GameEvents;
import org.example.events.SocialEvents;
import org.example.exceptions.InvalidInputException;
import org.example.model.Notification;
import org.example.observers.Subject;
import org.example.observers.SubjectImpl;
import org.example.observers.ObserverImpl;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Michael Garcia on 17/03/25
 */
public class NotificationServiceTest
{

    public static final String ALICE = "Alice";
    public static final String BOB = "Bob";
    public static final String CHARLIE = "Charlie";

    @Test
    public void testLevelUpNotificationOnlySentToSubscribedUser() throws InvalidInputException
    {
        Subject subject = new SubjectImpl();
        NotificationService service = new NotificationService(subject);
        ObserverImpl alice = new ObserverImpl(ALICE);
        ObserverImpl bob = new ObserverImpl(BOB);

        subject.registerObserver(alice);
        subject.registerObserver(bob);

        alice.enableNotification(GameEvents.LEVEL_UP);
        bob.disableNotification(GameEvents.LEVEL_UP);
        bob.enableNotification(GameEvents.LEVEL_UP);

        service.playerLeveledUp(ALICE);
        service.playerLeveledUp(BOB);

        assertFalse(alice.getReceivedNotifications().isEmpty());
        assertFalse(bob.getReceivedNotifications().isEmpty());
        assertEquals(GameEvents.LEVEL_UP, alice.getReceivedNotifications().get(0).type());

    }

    @Test
    public void testFriendRequestNotificationRespectsPreferences() throws InvalidInputException
    {
        Subject subject = new SubjectImpl();
        NotificationService service = new NotificationService(subject);
        ObserverImpl alice = new ObserverImpl(ALICE);
        ObserverImpl bob = new ObserverImpl(BOB);

        subject.registerObserver(alice);
        subject.registerObserver(bob);

        bob.enableNotification(SocialEvents.FRIEND_REQUEST);


        service.friendRequestSent(ALICE, BOB);

        Notification notification = bob.getReceivedNotifications().get(0);
        assertEquals(SocialEvents.FRIEND_REQUEST, notification.type());
        assertTrue(notification.message().contains("Alice has sent you a friend request"));
        assertTrue(alice.getReceivedNotifications().isEmpty());
    }

    @Test
    public void testLevelUpNotificationSentToAllPlayers() throws InvalidInputException
    {
        Subject subject = new SubjectImpl();
        NotificationService service = new NotificationService(subject);
        ObserverImpl alice = new ObserverImpl(ALICE);
        ObserverImpl bob = new ObserverImpl(BOB);
        ObserverImpl charlie = new ObserverImpl(CHARLIE);

        subject.registerObserver(alice);
        subject.registerObserver(bob);
        subject.registerObserver(charlie);

        alice.enableNotification(GameEvents.LEVEL_UP);
        bob.enableNotification(GameEvents.LEVEL_UP);
        charlie.enableNotification(GameEvents.LEVEL_UP);

        service.playerLeveledUp(ALICE);

        assertFalse(alice.getReceivedNotifications().isEmpty());
        assertFalse(bob.getReceivedNotifications().isEmpty());
        assertFalse(charlie.getReceivedNotifications().isEmpty());

        assertEquals("Alice leveled up!", alice.getReceivedNotifications().get(0).message());
        assertEquals("Alice leveled up!", bob.getReceivedNotifications().get(0).message());
        assertEquals("Alice leveled up!", charlie.getReceivedNotifications().get(0).message());
    }

    @Test
    public void testFriendRequestAcceptedNotification()
    {
        Subject subject = new SubjectImpl();
        NotificationService service = new NotificationService(subject);
        ObserverImpl alice = new ObserverImpl(ALICE);
        ObserverImpl bob = new ObserverImpl(BOB);
        ObserverImpl charlie = new ObserverImpl(CHARLIE);

        subject.registerObserver(alice);
        subject.registerObserver(bob);
        subject.registerObserver(charlie);

        alice.enableNotification(SocialEvents.FRIEND_REQUEST_IS_ACCEPTED);

        service.friendRequestAccepted(BOB, ALICE);
        Notification notification = alice.getReceivedNotifications().get(0);
        assertEquals(SocialEvents.FRIEND_REQUEST_IS_ACCEPTED, notification.type());
        assertTrue(notification.message().contains("Bob accepted your friend request"));
        assertTrue(bob.getReceivedNotifications().isEmpty());
    }

    @Test
    public void testChallengingQuestMissionNotification() throws InvalidInputException
    {
        Subject subject = new SubjectImpl();
        NotificationService service = new NotificationService(subject);
        ObserverImpl alice = new ObserverImpl(ALICE);
        ObserverImpl bob = new ObserverImpl(BOB);
        ObserverImpl charlie = new ObserverImpl(CHARLIE);

        subject.registerObserver(alice);
        subject.registerObserver(bob);
        subject.registerObserver(charlie);

        alice.enableNotification(GameEvents.CHALLENGE_COMPLETED);
        bob.enableNotification(GameEvents.CHALLENGE_COMPLETED);
        charlie.enableNotification(GameEvents.CHALLENGE_COMPLETED);

        service.challengingQuestMission(ALICE);
        service.challengingQuestMission(BOB);

        assertFalse(alice.getReceivedNotifications().isEmpty());
        assertFalse(bob.getReceivedNotifications().isEmpty());
        assertFalse(charlie.getReceivedNotifications().isEmpty());

        assertEquals("Alice unlocked a new mission", alice.getReceivedNotifications().get(0).message());
        assertEquals("Alice unlocked a new mission", bob.getReceivedNotifications().get(0).message());
        assertEquals("Alice unlocked a new mission", charlie.getReceivedNotifications().get(0).message());
    }

    @Test
    public void testItemAcquiredNotificationSentToAllPlayers() throws InvalidInputException
    {
        Subject subject = new SubjectImpl();
        NotificationService service = new NotificationService(subject);
        ObserverImpl alice = new ObserverImpl(ALICE);
        ObserverImpl bob = new ObserverImpl(BOB);
        ObserverImpl charlie = new ObserverImpl(CHARLIE);

        subject.registerObserver(alice);
        subject.registerObserver(bob);
        subject.registerObserver(charlie);

        alice.enableNotification(GameEvents.ITEM_ACQUIRED);
        bob.enableNotification(GameEvents.ITEM_ACQUIRED);
        charlie.disableNotification(GameEvents.ITEM_ACQUIRED);

        service.itemAcquired(ALICE, "SwordOfAzeroth");
        service.itemAcquired(BOB, "SwordOfAzeroth");
        service.itemAcquired(CHARLIE, "SwordOfAzeroth");

        assertFalse(alice.getReceivedNotifications()
                .isEmpty());
        assertFalse(bob.getReceivedNotifications()
                .isEmpty());
        assertEquals(GameEvents.ITEM_ACQUIRED, alice.getReceivedNotifications()
                .get(0)
                .type());
        assertEquals(GameEvents.ITEM_ACQUIRED, bob.getReceivedNotifications()
                .get(0)
                .type());
    }

    @Test
    public void testItemAcquiredNotificationSentToAllAndOneLevelUp() throws InvalidInputException
    {
        Subject subject = new SubjectImpl();
        NotificationService service = new NotificationService(subject);
        ObserverImpl alice = new ObserverImpl(ALICE);
        ObserverImpl bob = new ObserverImpl(BOB);
        ObserverImpl charlie = new ObserverImpl(CHARLIE);

        subject.registerObserver(alice);
        subject.registerObserver(bob);
        subject.registerObserver(charlie);

        alice.enableNotification(GameEvents.ITEM_ACQUIRED);
        alice.enableNotification(GameEvents.LEVEL_UP);
        bob.enableNotification(GameEvents.ITEM_ACQUIRED);
        charlie.enableNotification(GameEvents.LEVEL_UP);
        charlie.disableNotification(GameEvents.ITEM_ACQUIRED);

        service.itemAcquired(ALICE, "SwordOfAzeroth");
        service.itemAcquired(BOB, "Dragon");
        service.itemAcquired(CHARLIE, "Digimon");
        service.playerLeveledUp(ALICE);
        service.playerLeveledUp(CHARLIE);

        assertFalse(alice.getReceivedNotifications()
                .isEmpty());
        assertFalse(bob.getReceivedNotifications()
                .isEmpty());
        assertEquals(GameEvents.ITEM_ACQUIRED, alice.getReceivedNotifications()
                .get(0)
                .type());
        assertEquals(GameEvents.ITEM_ACQUIRED, bob.getReceivedNotifications()
                .get(0)
                .type());

    }

    @Test
    public void testAchievementNotification() throws InvalidInputException
    {
        Subject subject = new SubjectImpl();
        NotificationService service = new NotificationService(subject);
        ObserverImpl alice = new ObserverImpl(ALICE);
        ObserverImpl bob = new ObserverImpl(BOB);
        ObserverImpl charlie = new ObserverImpl(CHARLIE);

        subject.registerObserver(alice);
        subject.registerObserver(bob);
        subject.registerObserver(charlie);

        alice.enableNotification(GameEvents.ACHIEVEMENT_COMPLETED);
        bob.enableNotification(GameEvents.ACHIEVEMENT_COMPLETED);
        charlie.enableNotification(GameEvents.ACHIEVEMENT_COMPLETED);

        service.achievement(ALICE);
        service.achievement(BOB);

        assertFalse(alice.getReceivedNotifications().isEmpty());
        assertFalse(bob.getReceivedNotifications().isEmpty());
        assertFalse(charlie.getReceivedNotifications().isEmpty());

        assertEquals("Alice finished the daily goals", alice.getReceivedNotifications().get(0).message());
        assertEquals("Alice finished the daily goals", bob.getReceivedNotifications().get(0).message());
        assertEquals("Alice finished the daily goals", charlie.getReceivedNotifications().get(0).message());
    }

    @Test
    public void testPlayerIsAttackedNotification() throws InvalidInputException
    {
        Subject subject = new SubjectImpl();
        NotificationService service = new NotificationService(subject);
        ObserverImpl alice = new ObserverImpl(ALICE);
        ObserverImpl bob = new ObserverImpl(BOB);
        ObserverImpl charlie = new ObserverImpl(CHARLIE);

        subject.registerObserver(alice);
        subject.registerObserver(bob);
        subject.registerObserver(charlie);

        alice.enableNotification(GameEvents.PVP);
        bob.enableNotification(GameEvents.PVP);
        charlie.disableNotification(GameEvents.PVP);

        service.playerIsAttacked(ALICE, BOB);

        assertFalse(alice.getReceivedNotifications().isEmpty());
        assertEquals("Alice is being attacked by Bob", alice.getReceivedNotifications().get(0).message());

        assertTrue(charlie.getReceivedNotifications().isEmpty());
    }

    @Test
    public void testPlayerIsDefeatedNotification() throws InvalidInputException
    {
        Subject subject = new SubjectImpl();
        NotificationService service = new NotificationService(subject);
        ObserverImpl alice = new ObserverImpl(ALICE);
        ObserverImpl bob = new ObserverImpl(BOB);

        subject.registerObserver(alice);
        subject.registerObserver(bob);

        alice.enableNotification(GameEvents.PVP);

        service.playerIsDefeated(ALICE, BOB);

        assertFalse(alice.getReceivedNotifications().isEmpty());
        assertEquals("Alice has been defeated by Bob", alice.getReceivedNotifications().get(0).message());

        assertTrue(bob.getReceivedNotifications().isEmpty());
    }

    @Test
    public void testGotNewFollowerNotification() throws InvalidInputException
    {
        Subject subject = new SubjectImpl();
        NotificationService service = new NotificationService(subject);
        ObserverImpl alice = new ObserverImpl(ALICE);
        ObserverImpl bob = new ObserverImpl(BOB);

        subject.registerObserver(alice);
        subject.registerObserver(bob);

        alice.enableNotification(SocialEvents.NEW_FOLLOWER);
        bob.enableNotification(GameEvents.LEVEL_UP);

        service.gotNewFollower(ALICE, BOB);
        service.playerLeveledUp(BOB);

        assertFalse(alice.getReceivedNotifications().isEmpty());
        assertEquals("Bob is now following you", alice.getReceivedNotifications().get(0).message());
    }

    @Test
    public void testGetUserDoesNotExist()
    {
        Subject subject = new SubjectImpl();
        NotificationService service = new NotificationService(subject);

        Exception exception = assertThrows(InvalidInputException.class, () -> {
            service.getUser(ALICE);
        });

        assertEquals("User does not exist", exception.getMessage(), exception.getMessage());
    }

    @Test
    public void testUserExist() throws InvalidInputException
    {
        Subject subject = new SubjectImpl();
        NotificationService service = new NotificationService(subject);

        ObserverImpl alice = new ObserverImpl(ALICE);
        ObserverImpl bob = new ObserverImpl(BOB);

        subject.registerObserver(alice);
        subject.registerObserver(bob);

        service.registerUser(ALICE);

        assertTrue(service.userExist(ALICE));
        assertFalse(service.userExist(BOB));
    }

    @Test
    public void testBuildFriendRequestKey()
    {
        Subject subject = new SubjectImpl();
        NotificationService service = new NotificationService(subject);

        String key = service.buildFriendRequestKey(ALICE, BOB);

        assertEquals(ALICE + "->" + BOB, key, ALICE + "->" + BOB);
    }

    @Test
    public void testAddFriendRequest()
    {
        Subject subject = new SubjectImpl();
        NotificationService service = new NotificationService(subject);

        service.addFriendRequest(ALICE, BOB);

        assertTrue("Error: Friend request should be added but was not found.", service.friendRequestExists(ALICE, BOB));
    }

    @Test
    public void testRemoveFriendRequest()
    {
        Subject subject = new SubjectImpl();
        NotificationService service = new NotificationService(subject);

        service.addFriendRequest(ALICE, BOB);

        service.removeFriendRequest(ALICE, BOB);

        assertFalse("Error: Friend request should be removed but still exists.", service.friendRequestExists(ALICE, BOB));
    }

    @Test
    public void testEnableGameEventNotifications() throws InvalidInputException
    {
        Subject subject = new SubjectImpl();
        NotificationService service = new NotificationService(subject);

        ObserverImpl observer = new ObserverImpl(ALICE);

        service.enableGameEventNotifications(observer);

        assertTrue("LEVEL_UP should be enabled", observer.isNotificationEnabled(GameEvents.LEVEL_UP));
        assertTrue("CHALLENGE_COMPLETED should be enabled", observer.isNotificationEnabled(GameEvents.CHALLENGE_COMPLETED));
        assertTrue("ACHIEVEMENT_COMPLETED should be enabled", observer.isNotificationEnabled(GameEvents.ACHIEVEMENT_COMPLETED));
        assertTrue("ITEM_ACQUIRED should be enabled", observer.isNotificationEnabled(GameEvents.ITEM_ACQUIRED));
        assertTrue("PVP should be enabled", observer.isNotificationEnabled(GameEvents.PVP));
    }

    @Test
    public void testDisableGameEventNotifications() throws InvalidInputException
    {
        Subject subject = new SubjectImpl();
        NotificationService service = new NotificationService(subject);

        ObserverImpl observer = new ObserverImpl(ALICE);

        service.disableGameEventNotifications(observer);

        assertFalse("LEVEL_UP should be enabled", observer.isNotificationEnabled(GameEvents.LEVEL_UP));
        assertFalse("CHALLENGE_COMPLETED should be enabled", observer.isNotificationEnabled(GameEvents.CHALLENGE_COMPLETED));
        assertFalse("ACHIEVEMENT_COMPLETED should be enabled", observer.isNotificationEnabled(GameEvents.ACHIEVEMENT_COMPLETED));
        assertFalse("ITEM_ACQUIRED should be enabled", observer.isNotificationEnabled(GameEvents.ITEM_ACQUIRED));
        assertFalse("PVP should be enabled", observer.isNotificationEnabled(GameEvents.PVP));
    }

    @Test
    public void testEnableSocialEventNotifications() throws InvalidInputException
    {
        Subject subject = new SubjectImpl();
        NotificationService service = new NotificationService(subject);

        ObserverImpl observer = new ObserverImpl(ALICE);

        service.enableSocialEventNotifications(observer);

        assertTrue("NEW_FOLLOWER should be enabled", observer.isNotificationEnabled(SocialEvents.NEW_FOLLOWER));
        assertTrue("FRIEND_REQUEST should be enabled", observer.isNotificationEnabled(SocialEvents.FRIEND_REQUEST));
        assertTrue("FRIEND_REQUEST_IS_ACCEPTED should be enabled", observer.isNotificationEnabled(SocialEvents.FRIEND_REQUEST_IS_ACCEPTED));

    }

    @Test
    public void testDisableSocialEventNotifications() throws InvalidInputException
    {
        Subject subject = new SubjectImpl();
        NotificationService service = new NotificationService(subject);

        ObserverImpl observer = new ObserverImpl(ALICE);

        service.disableSocialEventNotifications(observer);

        assertFalse("NEW_FOLLOWER should be enabled", observer.isNotificationEnabled(SocialEvents.NEW_FOLLOWER));
        assertFalse("FRIEND_REQUEST should be enabled", observer.isNotificationEnabled(SocialEvents.FRIEND_REQUEST));
        assertFalse("FRIEND_REQUEST_IS_ACCEPTED should be enabled", observer.isNotificationEnabled(SocialEvents.FRIEND_REQUEST_IS_ACCEPTED));
    }


}
