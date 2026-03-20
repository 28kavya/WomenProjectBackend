package Women.Project.Service;

import Women.Project.Models.AnalysisResultDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.file.Path;

@Service
public class VoiceService {

    @Autowired
    private RestTemplate restTemplate;

    private static final String PYTHON_URL = "http://127.0.0.1:5000/analyze"; // ✅ change this

    public AnalysisResultDTO analyzeAudio(Path filePath) throws Exception {

        FileSystemResource resource = new FileSystemResource(filePath.toFile());

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", resource);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String, Object>> request =
                new HttpEntity<>(body, headers);

        ResponseEntity<String> response =
                restTemplate.postForEntity(
                        PYTHON_URL,
                        request,
                        String.class
                );

        System.out.println("RAW RESPONSE FROM PYTHON: " + response.getBody());

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(response.getBody(), AnalysisResultDTO.class);
    }
}
