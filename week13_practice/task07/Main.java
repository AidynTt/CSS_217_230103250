package week13_practice.task07;

interface DeliveryChannelStrategy {
    String getChannel();
    boolean send(String contact, String message);
}

class EmailDelivery implements DeliveryChannelStrategy {
    public String getChannel() {
        return "Email";
    }

    public boolean send(String contact, String message) {
        System.out.println("Email to: " + contact);
        System.out.println("Message: " + message);
        return true;
    }
}

class SmsDelivery implements DeliveryChannelStrategy {
    public String getChannel() {
        return "SMS";
    }

    public boolean send(String contact, String message) {
        System.out.println("SMS to: " + contact);
        System.out.println("Message: " + message);
        return true;
    }
}

class PushNotificationDelivery implements DeliveryChannelStrategy {
    public String getChannel() {
        return "Push";
    }

    public boolean send(String contact, String message) {
        System.out.println("Push notification to: " + contact);
        System.out.println("Message: " + message);
        return true;
    }
}

class UserPreferenceStore {
    public String getContact(String userId, String channel) {
        if (channel.equals("Email")) {
            return userId + "@example.com";
        } else if (channel.equals("SMS")) {
            return "+77001234567";
        } else {
            return "device_" + userId;
        }
    }
}

class TemplateFormatter {
    public String format(String messageKey, String payload) {
        if (messageKey.equals("welcome")) {
            return "Welcome, " + payload + "!";
        } else if (messageKey.equals("order")) {
            return "Your order " + payload + " is ready.";
        } else {
            return payload;
        }
    }
}

class DeliveryLogger {
    public void log(String userId, String channel, boolean success) {
        System.out.println("User: " + userId);
        System.out.println("Channel: " + channel);
        System.out.println("Status: " +
                (success ? "Delivered" : "Failed"));
    }
}

class NotificationFacade {
    private DeliveryChannelStrategy strategy;

    private final UserPreferenceStore preferences =
            new UserPreferenceStore();

    private final TemplateFormatter formatter =
            new TemplateFormatter();

    private final DeliveryLogger logger =
            new DeliveryLogger();

    public NotificationFacade(DeliveryChannelStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(DeliveryChannelStrategy strategy) {
        this.strategy = strategy;
    }

    public void sendAlert(String userId,
                          String messageKey,
                          String payload) {

        String channel = strategy.getChannel();

        String contact =
                preferences.getContact(userId, channel);

        String message =
                formatter.format(messageKey, payload);

        boolean success = strategy.send(contact, message);

        logger.log(userId, channel, success);
    }
}

public class Main {
    public static void main(String[] args) {
        NotificationFacade facade =
                new NotificationFacade(new EmailDelivery());

        System.out.println("Email Notification:");
        facade.sendAlert("user01", "welcome", "Alex");

        System.out.println("\nSMS Notification:");
        facade.setStrategy(new SmsDelivery());
        facade.sendAlert("user01", "order", "123");

        System.out.println("\nPush Notification:");
        facade.setStrategy(new PushNotificationDelivery());
        facade.sendAlert("user01", "welcome", "Alex");
    }
}
