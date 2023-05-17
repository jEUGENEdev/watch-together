package org.jeugenedev.watch.model;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.jeugenedev.watch.entity.Session;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class SessionModel {
    private final static Map<String, Session> sessions = new HashMap<>();

    private final String UUID = "creator_uuid";

    public Map<String, Session> getSessions() {
        return sessions;
    }

    private void deleteSession(UUID uuid) {
        sessions.remove(uuid.toString());
    }

    private Optional<Session> getSession(UUID uuid) {
        return Optional.ofNullable(sessions.getOrDefault(uuid.toString(), null));
    }

    private Optional<Session> getSessionByCreatorId(UUID uuid) {
        AtomicReference<Session> session = new AtomicReference<>();
        sessions.forEach((id, ss) -> {
            if(uuid.toString().equals(ss.getCreatorSessionUUID())) {
                session.set(ss);
            }
        });
        return Optional.ofNullable(session.get());
    }

    private Optional<UUID> getRoomIdByCreatorId(UUID creatorId) {
        AtomicReference<java.util.UUID> uuid = new AtomicReference<>();
        sessions.forEach((id, ss) -> {
            if(creatorId.toString().equals(ss.getCreatorSessionUUID())) {
                uuid.set(java.util.UUID.fromString(id));
            }
        });
        return Optional.ofNullable(uuid.get());
    }

    public Session createSession(HttpServletResponse response, Cookie[] cookies, UUID creatorUUID, String videoName) {
        Arrays.stream(cookies).filter(cookie -> UUID.equals(cookie.getName()))
                .forEach(cookie -> {
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                    java.util.UUID uuid = getRoomIdByCreatorId(java.util.UUID.fromString(cookie.getValue())).orElseThrow();
                    deleteSession(uuid);
                });
        response.addCookie(new Cookie(UUID, creatorUUID.toString()));
        Session session = new Session(creatorUUID.toString(), videoName);
        sessions.put(java.util.UUID.randomUUID().toString(), session);
        return session;
    }

    public Session getSession(String roomId) {
        return getSession(java.util.UUID.fromString(roomId)).orElseThrow();
    }

    public Session setTimeSession(Cookie[] cookies, long timeMS) {
        String creatorUUID = Arrays.stream(cookies).filter(cookie -> UUID.equals(cookie.getName())).findFirst().orElseThrow().getValue();
        Session session = getSessionByCreatorId(java.util.UUID.fromString(creatorUUID)).orElseThrow();
        session.setTimeMS(timeMS);
        return session;
    }
}
