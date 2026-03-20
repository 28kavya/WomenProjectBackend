package Women.Project.Models;

import lombok.Data;
@Data
public class AnalysisResultDTO {

    private String alert_reason;
    private boolean alert_triggered;
    private String overall_risk_level;

    private String text;
    private Object yamnet_predictions;
    private double amplitude;
    private boolean yamnet_detected;
    private boolean text_danger;
}
