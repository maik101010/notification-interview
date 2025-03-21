package org.example.model;

import org.example.events.EventType;

/**
 * Created by Michael Garcia on 17/03/25
 */
public record Notification(EventType type, String message, String sender, String recipient, boolean isBroadcast)
{
    @Override
    public String toString()
    {
        return "[" + type + "] " + message;
    }
}