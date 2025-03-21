# **Notification and Social Interaction System**

## **📌 Project Overview**
This project is a **Notification and Social Interaction System** designed for an online game. It allows users to:
- **Send and receive friend requests.**
- **Accept friend requests and track friendships.**
- **Receive notifications for game-related events** (level-up, item acquisition, challenges, PvP, etc.).
- **Manage notification preferences** for game and social events.

---

## **⚙️ System Architecture**
### **🔹 High-Level Structure**
```plaintext
+---------------------+           +---------------------+
|     User (CLI)      | <-------> | NotificationService |
+---------------------+           +---------------------+
                                       |  |  |  |  |  
                                       v  v  v  v  v  
+-------------------------+           +----------------------+
| Observer & ObserverImpl |  <------> | Subject & SubjectImpl |
+-------------------------+           +----------------------+
                                       |  |  |  |  |  
                                       v  v  v  v  v  
+---------------------+          +----------------------+
|   Event Types      | <-------> |  Notification Model  |
+---------------------+          +----------------------+
```

### **🔹 Key Components**
- **User Interface (CLI):** Handles user interaction.
- **NotificationService:** Manages notifications and friend requests.
- **Observer & ObserverImpl:** Implements the Observer Pattern to track user subscriptions.
- **Subject & SubjectImpl:** Manages observer registration and notification dispatch.
- **Event Types:** Defines different types of events (e.g., LEVEL_UP, PVP, FRIEND_REQUEST).
- **Notification Model:** Stores notification details such as message, sender, recipient, and event type.

---

## **🚀 Features**
### **🔹 Social Event Notifications**
- Users can send and accept friend requests.
- System ensures users cannot send duplicate requests or request an existing friend.
- Users can be followed by others users.

### **🔹 Game Event Notifications**
- Users receive notifications when they level up, acquire items, complete challenges, achieve goals, or engage in PvP.
- Notifications are only sent if the user has enabled them in preferences.

### **🔹 Notification Preferences**
- Users can enable or disable game/social event notifications individually.

---

## **💡 How It Works**
### **🔹 Unit Testing Coverage**
1. The project includes **unit tests for almost every class in the architecture**.
2. These tests cover **most methods** ensuring reliability and correctness.
3. The test suite is implemented using **JUnit 5**.
### **🔹Register Users**
1. The system must register at least two users.
2. The user enters a unique ID.
3. If the ID is already taken, an error message is displayed.
4. If the ID is valid, the user is registered successfully.
### **🔹 Level Up Notification**
1. The user enters their ID.
2. If the user exists, they receive a **level-up notification**.
3. If the user does not exist, an error message is shown.

### **🔹 Item Acquisition Notification**
1. The user enters their ID.
2. If the user exists, they enter the **item name** they acquired.
3. A notification is sent confirming the acquisition.
4. If the user does not exist, an error message is shown.

### **🔹 Sending Friend Requests**
1. The sender enters their ID.
2. The receiver's ID is entered.
3. If both users exist the request is sent.
4. If the sender tries to send a request to themselves, an error message is displayed.

### **🔹 Accepting Friend Requests**
1. The accepter enters their ID.
2. The sender’s ID is entered.
3. If a request exists, the friendship is confirmed.
4. If there is no pending request, an error message is displayed.

### **🔹 Updating Notification Preferences**
1. The user enters their ID.
2. They select **game** or **social** notifications.
3. They choose to enable or disable notifications.
4. The system updates their preference.

### **🔹 Challenging Quest Notification**
1. The user enters their ID.
2. If they exist, they receive a **challenge completed** notification.

### **🔹 Achievement Completion Notification**
1. The user enters their ID.
2. If they exist, they receive a **daily goal completion** notification.

### **🔹 PvP Attack Notification**
1. The attacker enters their ID.
2. The defender's ID is entered.
3. If both users exist and are different, a notification is sent.
4. If a user tries to attack themselves, an error message is displayed.

### **🔹 PvP Defeat Notification**
1. The defeated player enters their ID.
2. The attacker’s ID is entered.
3. If both users exist and are different, a defeat notification is sent.
4. If a player tries to defeat themselves, an error message is displayed.

### **🔹 New Follower Notification**
1. The follower enters their ID.
2. The followed player's ID is entered.
3. If both exist and are different, a **new follower notification** is sent.
4. If a user tries to follow themselves, an error message is displayed.

### **🔹 Handling Invalid Input for Numbers**
1. The system prompts for a numeric value.
2. If the input is not a valid number, an error message is displayed.
3. The system retries the input request.


---

## **🛠️ Technologies Used**
- **Java 17+** - Main programming language.
- **JUnit 4** - Used for unit testing.
- **Observer Pattern** - Implements event-driven notifications.
- **Maven** - Build automation and dependency management.

---

## **📌 Installation & Usage**
### **🔹 Prerequisites**
- **Java 17+** installed.
- **Maven** installed (for dependency management).

### **🔹 Installation Steps**
```sh
# Clone the repository
git clone https://github.com/maik101010/notification-interview
cd notification-interview
```

### **🔹 Running the Application**
```sh
mvn clean install
```

## **📌 Future Enhancements**
- Implement **database storage** for persistent users and friendships information.
- Introduce a **GUI** for improved user experience, maybe using a FE tool such as react or angular.
- Use **WebSockets** for real-time notifications, or we can use real tools such as kafka, rabbit mq for handling the subscriptions and notifications in real time.
---
