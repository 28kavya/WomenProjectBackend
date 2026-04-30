package Women.Project.Models;

import lombok.Data;
import lombok.Data;
import java.util.List;

@Data
public class AnalysisResultDTO {

    private String alert_reason;
    private boolean alert_triggered;
    private String overall_risk_level;

    private String transcription;              // ✅ FIXED
    private List<YamnetLabel> yamnet_labels;   // ✅ FIXED
}
