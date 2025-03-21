package org.example;

import org.example.exceptions.InvalidInputException;
import org.example.observers.SubjectImpl;
import org.example.service.NotificationService;

import java.util.Scanner;

public class Main
{
    private static final NotificationService service = new NotificationService(new SubjectImpl());
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args)
    {
        while (true)
        {
            System.out.println("\n===== Notification System Menu =====");
            System.out.println("1. Register User");
            System.out.println("2. Notify Level Up");
            System.out.println("3. Notify Item Acquired");
            System.out.println("4. Send Friend Request");
            System.out.println("5. Accept Friend Request");
            System.out.println("6. Enable/Disable Notifications");
            System.out.println("7. Challenging Quest Mission");
            System.out.println("8. Achievements");
            System.out.println("9. Player Is Attacked");
            System.out.println("10. Player Is Defeated");
            System.out.println("11. Player Got A New Follower");
            System.out.println("12. Exit");
            System.out.print("Select an option: ");


            int choice = getIntInput();
            switch (choice)
            {
                case 1 -> registerUser();
                case 2 -> notifyLevelUp();
                case 3 -> notifyItemAcquired();
                case 4 -> notifyFriendRequest();
                case 5 -> notifyFriendAccepted();
                case 6 -> updateNotificationPreferences();
                case 7 -> notifyChallengingQuestMission();
                case 8 -> notifyAchievement();
                case 9 -> notifyPlayerIsAttacked();
                case 10 -> notifyPlayerIsDefeated();
                case 11 -> notifyGotNewFollower();
                case 12 ->
                {
                    System.out.println("Exiting program...");
                    return;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void registerUser()
    {
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();
        try
        {
            service.registerUser(userId);
        } catch (InvalidInputException e)
        {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void notifyLevelUp()
    {
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();
        if (!service.userExist(userId))
        {
            System.out.println("User does not exist");
            return;
        }
        service.playerLeveledUp(userId);

    }

    private static void notifyItemAcquired()
    {
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();
        if (!service.userExist(userId))
        {
            System.out.println("User does not exist");
            return;
        }
        System.out.print("Enter Item Name: ");
        String itemName = scanner.nextLine();
        service.itemAcquired(userId, itemName);

    }

    private static void notifyFriendRequest()
    {
        System.out.print("Enter Sender ID: ");
        String senderId = scanner.nextLine();
        if (!service.userExist(senderId)){
            System.out.println("User does not exist");
            return;
        }
        System.out.print("Enter Receiver ID: ");
        String receiverId = scanner.nextLine();
        if(!service.userExist(receiverId)){
            System.out.println("User does not exist");
            return;
        }
        if (senderId.equalsIgnoreCase(receiverId)) {
            System.out.println("Error: A player can not send request themselves!");
            return;
        }
        service.friendRequestSent(senderId, receiverId);
        service.addFriendRequest(senderId, receiverId);
    }

    private static void notifyFriendAccepted()
    {
        System.out.print("Enter Accepter ID: ");
        String acceptedId = scanner.nextLine();
        if (!service.userExist(acceptedId)){
            System.out.println("User does not exist");
            return;
        }
        System.out.print("Enter Receiver ID: ");
        String requesterId = scanner.nextLine();
        if(!service.userExist(acceptedId)){
            System.out.println("User does not exist");
            return;
        }
        if (!service.friendRequestExists(requesterId, acceptedId)) {
            System.out.println("Error: No friend request found from " + requesterId + " to " + acceptedId);
            return;
        }
        service.friendRequestAccepted(acceptedId, requesterId);
        service.removeFriendRequest(requesterId, acceptedId);
    }

    private static void updateNotificationPreferences()
    {
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();
        if (!service.userExist(userId))
        {
            System.out.println("User does not exist");
            return;
        }
        String category;
        while (true) {
            System.out.print("Enter Event Category (game/social): ");
            category = scanner.nextLine().toLowerCase();

            if (category.equals("game") || category.equals("social")) {
                break;
            }

            System.out.println("Error: Invalid category! Please enter 'game' or 'social'.");
        }
        boolean isEnabled;
        while (true) {
            System.out.print("Enable notifications for this category? (yes/no): ");
            String input = scanner.nextLine().toLowerCase();

            if (input.equals("yes")) {
                isEnabled = true;
                break;
            } else if (input.equals("no")) {
                isEnabled = false;
                break;
            }

            System.out.println("Error: Invalid input! Please enter 'yes' or 'no'.");
        }
        try
        {
            service.setNotificationPreference(userId, category, isEnabled);
            System.out.println("Notification preference updated.");
        } catch (InvalidInputException e)
        {
            System.out.println(e.getMessage());
        }
    }

    private static void notifyChallengingQuestMission(){
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();
        if (!service.userExist(userId))
        {
            System.out.println("User does not exist");
            return;
        }
        service.challengingQuestMission(userId);

    }

    private static void notifyAchievement(){
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();
        if (!service.userExist(userId))
        {
            System.out.println("User does not exist");
            return;
        }
        service.achievement(userId);
    }

    private static void notifyPlayerIsAttacked()
    {
        System.out.print("Enter Who Is Being Attacked ID: ");
        String toPlayer = scanner.nextLine();
        if (!service.userExist(toPlayer)){
            System.out.println("User does not exist");
            return;
        }
        System.out.print("Enter Who Is Attacking ID: ");
        String fromPlayer = scanner.nextLine();
        if(!service.userExist(fromPlayer)){
            System.out.println("User does not exist");
            return;
        }
        if (toPlayer.equalsIgnoreCase(fromPlayer)) {
            System.out.println("Error: A player can not attack themselves!");
            return;
        }
        service.playerIsAttacked(toPlayer, fromPlayer);
    }

    private static void notifyPlayerIsDefeated()
    {
        System.out.print("Enter Who Was Defeated ID: ");
        String toPlayer = scanner.nextLine();
        if (!service.userExist(toPlayer)){
            System.out.println("User does not exist");
            return;
        }
        System.out.print("Enter Who Was Attacking ID: ");
        String fromPlayer = scanner.nextLine();
        if(!service.userExist(fromPlayer)){
            System.out.println("User does not exist");
            return;
        }
        if (toPlayer.equalsIgnoreCase(fromPlayer)) {
            System.out.println("Error: A player can not defeat themselves!");
            return;
        }
        service.playerIsDefeated(toPlayer, fromPlayer);
    }

    private static void notifyGotNewFollower()
    {
        System.out.print("Enter Follower ID: ");
        String followerId = scanner.nextLine();
        if (!service.userExist(followerId))
        {
            System.out.println("User does not exist");
            return;
        }
        System.out.print("Enter Player Followed: ");
        String playerFollowed = scanner.nextLine();
        if(!service.userExist(playerFollowed)){
            System.out.println("User does not exist");
            return;
        }
        if (followerId.equalsIgnoreCase(playerFollowed)) {
            System.out.println("Error: A player can not follow themselves!");
            return;
        }
        service.gotNewFollower(playerFollowed, followerId);
    }

    private static int getIntInput()
    {
        try
        {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e)
        {
            System.out.println("Invalid input, please enter a number.");
            return -1;
        }
    }
}
