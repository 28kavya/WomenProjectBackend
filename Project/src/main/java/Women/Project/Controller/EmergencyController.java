package Women.Project.Controller;

import Women.Project.Models.EmergencyRequest;
import Women.Project.Service.EmergencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/emergency")
@CrossOrigin(origins = "*")
public class EmergencyController {

    @Autowired
    private EmergencyService emergencyService;

    @PostMapping("/trigger")
    public void trigger(@RequestBody EmergencyRequest request) {

        emergencyService.triggerEmergency(
                request.getReason(),
                request.getRiskLevel(),
                request.getLat(),
                request.getLon(),
                request.getUserId()
        );
    }
}
