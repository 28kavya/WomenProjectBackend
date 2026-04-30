package Women.Project.Controller;


import Women.Project.Models.SSEEmitterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/api/sse")
@CrossOrigin(origins = "*")
public class SseController {

    @Autowired
    private SSEEmitterRegistry registry;

    @GetMapping(value = "/subscribe/{userId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe(@PathVariable Long userId) {
        System.out.println("User connected to SSE: " + userId);
        return registry.register(userId);
    }
}
