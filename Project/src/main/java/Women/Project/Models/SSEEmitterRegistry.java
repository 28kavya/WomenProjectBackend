package Women.Project.Models;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
@Component
public class SSEEmitterRegistry {

    private final Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();

    // ✅ Register user
    public SseEmitter register(Long userId) {

        // 🔥 No timeout (or use 0L)
        SseEmitter emitter = new SseEmitter(0L);

        emitters.put(userId, emitter);

        // ✅ Remove on lifecycle events
        emitter.onCompletion(() -> emitters.remove(userId));
        emitter.onTimeout(() -> emitters.remove(userId));
        emitter.onError((e) -> emitters.remove(userId));

        // ✅ Send initial event (IMPORTANT)
        try {
            emitter.send(SseEmitter.event()
                    .name("INIT")
                    .data("connected"));
        } catch (IOException e) {
            emitters.remove(userId);
        }

        return emitter;
    }

    // ✅ Send emergency event
    public void sendToUser(Long userId, Object data) {
        SseEmitter emitter = emitters.get(userId);

        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event()
                        .name("emergency")
                        .data(data));
            } catch (IOException e) {
                emitters.remove(userId);
            }
        }
    }

    // ✅ HEARTBEAT (VERY IMPORTANT)
    @Scheduled(fixedRate = 15000)
    public void sendHeartbeat() {
        emitters.forEach((userId, emitter) -> {
            try {
                emitter.send(SseEmitter.event()
                        .name("PING")
                        .data("keep-alive"));
            } catch (IOException e) {
                emitters.remove(userId);
            }
        });
    }
}