package Women.Project.Service;

import Women.Project.Models.Contact;
import Women.Project.Models.User;
import Women.Project.Repository.AuthRepository;
import Women.Project.Repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmergencyService {

    @Autowired
    private ContactRepository repository;
    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private SmsService smsService;

    @Transactional
    public void triggerEmergency(String reason, String riskLevel,
                                 double lat, double lon, Long userId) {

        System.out.println("🚨 triggerEmergency called");

        List<Contact> contacts = repository.findByUser_Id(userId);

        System.out.println("Contacts found: " + contacts.size());

        if (contacts == null || contacts.isEmpty()) {
            System.out.println("❌ No contacts found for userId: " + userId);
            return;
        }

        User user = authRepository.findById(userId).orElse(null);

        if (user == null) {
            System.out.println("❌ User not found");
            return;
        }
        String userName = user.getName();
        String message = "🚨 EMERGENCY ALERT!\n"
                +"user name: " + userName + "\n"
                + "Reason: " + reason + "\n"
                + "Risk: " + riskLevel + "\n"
                + "Location: https://maps.google.com/?q=" + lat + "," + lon + "\n";

        for (Contact contact : contacts) {

            smsService.sendSms(contact.getPhone(), message);
        }

        System.out.println("✅ Emergency SMS Sent");
    }
}