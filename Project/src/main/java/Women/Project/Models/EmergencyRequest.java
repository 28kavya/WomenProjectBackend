package Women.Project.Models;

import lombok.Data;

@Data
public class EmergencyRequest {

    private Long userId;
    private String reason;
    private String riskLevel;
    private double lat;
    private double lon;

    // getters and setters
}
