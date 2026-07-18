package springboot_deep_drive.spring_core.impl.notification;

 // import org.springframework.stereotype.Component;

// @Component
public class NotificationServiceImpl implements NotificationService {

    @Override
    public void sendNotification() {

        System.out.println("Order confirmation notification sent.");
    }
}