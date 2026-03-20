package Women.Project.Service;

import Women.Project.Models.Contact;
import Women.Project.Repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmergencyService {

    @Autowired
    private ContactRepository repository;

    @Autowired
    private SmsService smsService;

    public void triggerEmergency(String reason, String riskLevel,
                                 double lat, double lon, Long userId) {

        System.out.println("🚨 triggerEmergency called");

        List<Contact> contacts = repository.findByUser_Id(userId);

        System.out.println("Contacts found: " + contacts.size());

        if (contacts == null || contacts.isEmpty()) {
            System.out.println("❌ No contacts found for userId: " + userId);
            return;
        }

        String message = "🚨 EMERGENCY ALERT!\n"
                + "Reason: " + reason + "\n"
                + "Risk: " + riskLevel + "\n"
                + "Location: https://maps.google.com/?q=" + lat + "," + lon + "\n";

        for (Contact contact : contacts) {
            System.out.println("📤 Sending SMS to: " + contact.getPhone());

            smsService.sendSms(contact.getPhone(), message);
        }

        System.out.println("✅ Emergency SMS Sent");
    }
}