package org.example.service;


/**
 * Created by Michael Garcia on 17/03/25
 */


import org.example.events.GameEvents;
import org.example.events.SocialEvents;
import org.example.exceptions.InvalidInputException;
import org.example.model.Notification;
import org.example.observers.ObserverImpl;
import org.example.observers.Subject;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class NotificationService
{
    private final Subject subject;
    private final Map<String, ObserverImpl> users = new HashMap<>();
    private final Set<String> pendingFriendRequests = new HashSet<>();


    public NotificationService(Subject newSubject)
    {
        subject = newSubject;
    }


    public void registerUser(String userId) throws InvalidInputException
    {
        if (users.containsKey(userId))
        {
            System.out.println("User already exists: " + userId);
            return;
        }
        ObserverImpl newUser = new ObserverImpl(userId);
        enableGameEventNotifications(newUser);
        enableSocialEventNotifications(newUser);
        users.put(userId, newUser);
        subject.registerObserver(newUser);


        System.out.println("User registered successfully: " + userId);
    }


    public ObserverImpl getUser(String userId) throws InvalidInputException
    {
        if (!userExist(userId))
        {
            throw new InvalidInputException("The user does not exist");
        }
        return users.get(userId);
    }


    public boolean userExist(String userId)
    {
        return users.get(userId) != null;
    }

    public boolean friendRequestExists(String fromUserId, String toUserId) {
        return pendingFriendRequests.contains(buildFriendRequestKey(fromUserId, toUserId));
    }
    String buildFriendRequestKey(String fromUserId, String toUserId) {
        return fromUserId + "->" + toUserId;
    }

    public void addFriendRequest(String fromUserId, String toUserId) {
        pendingFriendRequests.add(buildFriendRequestKey(fromUserId, toUserId));
    }

    public void removeFriendRequest(String fromUserId, String toUserId) {
        pendingFriendRequests.remove(buildFriendRequestKey(fromUserId, toUserId));
    }


    public void playerLeveledUp(String playerId)
    {
        String msg = playerId + " leveled up!";
        Notification notification = new Notification(GameEvents.LEVEL_UP, msg, playerId, playerId, true);
        subject.notifyObservers(notification);
    }


    public void enableGameEventNotifications(ObserverImpl newUser)
    {
        newUser.enableNotification(GameEvents.LEVEL_UP);
        newUser.enableNotification(GameEvents.CHALLENGE_COMPLETED);
        newUser.enableNotification(GameEvents.ACHIEVEMENT_COMPLETED);
        newUser.enableNotification(GameEvents.ITEM_ACQUIRED);
        newUser.enableNotification(GameEvents.PVP);
    }


    public void disableGameEventNotifications(ObserverImpl newUser)
    {
        newUser.disableNotification(GameEvents.LEVEL_UP);
        newUser.disableNotification(GameEvents.CHALLENGE_COMPLETED);
        newUser.disableNotification(GameEvents.ACHIEVEMENT_COMPLETED);
        newUser.disableNotification(GameEvents.ITEM_ACQUIRED);
        newUser.disableNotification(GameEvents.PVP);
    }


    public void enableSocialEventNotifications(ObserverImpl newUser)
    {
        newUser.enableNotification(SocialEvents.NEW_FOLLOWER);
        newUser.enableNotification(SocialEvents.FRIEND_REQUEST);
        newUser.enableNotification(SocialEvents.FRIEND_REQUEST_IS_ACCEPTED);
    }


    public void disableSocialEventNotifications(ObserverImpl newUser)
    {
        newUser.disableNotification(SocialEvents.NEW_FOLLOWER);
        newUser.disableNotification(SocialEvents.FRIEND_REQUEST);
        newUser.disableNotification(SocialEvents.FRIEND_REQUEST_IS_ACCEPTED);
    }


    public void itemAcquired(String playerId, String itemName)
    {
        String msg = playerId + " acquired a new item: " + itemName;
        Notification notification = new Notification(GameEvents.ITEM_ACQUIRED, msg, playerId, playerId, true);
        subject.notifyObservers(notification);
    }


    public void friendRequestSent(String fromPlayerId, String toPlayerId)
    {
        String msg = fromPlayerId + " has sent you a friend request";
        Notification notification = new Notification(SocialEvents.FRIEND_REQUEST, msg, fromPlayerId, toPlayerId, false);
        subject.notifyObservers(notification);
    }


    public void friendRequestAccepted(String playerId, String requesterId)
    {
        String msg = playerId + " accepted your friend request";
        Notification notification = new Notification(SocialEvents.FRIEND_REQUEST_IS_ACCEPTED, msg, playerId, requesterId, false);
        subject.notifyObservers(notification);
    }


    public void challengingQuestMission(String playerId)
    {
        String msg = playerId + " unlocked a new mission";
        Notification notification = new Notification(GameEvents.CHALLENGE_COMPLETED, msg, playerId, playerId, true);
        subject.notifyObservers(notification);
    }


    public void achievement(String playerId)
    {
        String msg = playerId + " finished the daily goals";
        Notification notification = new Notification(GameEvents.ACHIEVEMENT_COMPLETED, msg, playerId, playerId, true);
        subject.notifyObservers(notification);
    }


    public void playerIsAttacked(String playerId, String fromPlayerId)
    {
        String msg = playerId + " is being attacked by " + fromPlayerId;
        Notification notification = new Notification(GameEvents.PVP, msg, fromPlayerId, playerId, true);
        subject.notifyObservers(notification);
    }


    public void playerIsDefeated(String playerId, String fromPlayerId)
    {
        String msg = playerId + " has been defeated by " + fromPlayerId;
        Notification notification = new Notification(GameEvents.PVP, msg, fromPlayerId, playerId, true);
        subject.notifyObservers(notification);
    }


    public void gotNewFollower(String playerId, String followerId)
    {
        String msg = followerId + " is now following you";
        Notification notification = new Notification(SocialEvents.NEW_FOLLOWER, msg, followerId, playerId, false);
        subject.notifyObservers(notification);
    }


    public void setNotificationPreference(String userId, String category, boolean isEnabled) throws InvalidInputException
    {
        ObserverImpl user = getUser(userId);
        if (category.equalsIgnoreCase("game") && isEnabled)
        {
            enableGameEventNotifications(user);
        }
        else if (category.equalsIgnoreCase("game") && !isEnabled)
        {
            disableGameEventNotifications(user);
        }
        else if (category.equalsIgnoreCase("social") && isEnabled)
        {
            enableSocialEventNotifications(user);
        }
        else if (category.equalsIgnoreCase("social") && !isEnabled)
        {
            disableSocialEventNotifications(user);
        }
    }
}
