package Women.Project.Service;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SmsService {

    @Value("${twilio.phone.number}")
    private String fromNumber;

    public void sendSms(String toNumber, String message) {

        try {
            if (!toNumber.startsWith("+")) {
                toNumber = "+91" + toNumber;
            }

            Message.creator(
                    new PhoneNumber(toNumber),
                    new PhoneNumber(fromNumber),
                    message
            ).create();

            System.out.println("✅ SMS sent  " );

        } catch (Exception e) {
            System.out.println("❌ SMS failed ");
            e.printStackTrace();
        }
    }
}
