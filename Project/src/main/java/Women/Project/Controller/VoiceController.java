package Women.Project.Controller;

import Women.Project.Models.AnalysisResultDTO;
import Women.Project.Service.EmergencyService;
import Women.Project.Service.VoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class VoiceController {

    @Autowired
    private VoiceService voiceService;

    @Autowired
    private EmergencyService emergencyService;

    @PostMapping("/analyze")
    public ResponseEntity<?> uploadAudio(
            @RequestParam("file") MultipartFile file,
            @RequestParam("lat") double lat,
            @RequestParam("lon") double lon,
            @RequestParam("userId") Long userId
    ) {

        Path filePath = null;

        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "File is empty"));
            }

            File dir = new File("uploads/");
            if (!dir.exists()) dir.mkdirs();

            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            filePath = Paths.get("uploads/" + fileName);

            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // 🔥 Call Python / AI
            AnalysisResultDTO result = voiceService.analyzeAudio(filePath);

            // 🚨 Trigger SMS
            if (result != null && result.isAlert_triggered()) {

                emergencyService.triggerEmergency(
                        result.getAlert_reason(),
                        result.getOverall_risk_level(),
                        lat,
                        lon,
                        userId
                );
            }

            return ResponseEntity.ok(result);

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "error", "Analysis failed",
                            "details", e.getMessage()
                    ));

//        } finally {
//            if (filePath != null && Files.exists(filePath)) {
//                try {
//                    Files.delete(filePath);
//                } catch (IOException e) {
//                    System.err.println("Delete failed");
//                }
//            }
        }
    }
}