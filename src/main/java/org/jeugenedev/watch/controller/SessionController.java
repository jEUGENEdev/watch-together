package org.jeugenedev.watch.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jeugenedev.watch.entity.Session;
import org.jeugenedev.watch.model.SessionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

@RestController
public class SessionController {
    private final SessionModel sessionModel;

    @Autowired
    public SessionController(SessionModel sessionModel) {
        this.sessionModel = sessionModel;
    }

    @RequestMapping(value = "/createSession", method = {RequestMethod.GET, RequestMethod.POST})
    public Session createSession(HttpServletRequest request, HttpServletResponse response,
                                 @RequestParam("creator") UUID creatorUUID,
                                 @RequestParam("video_name") String videoName) {
        return this.sessionModel.createSession(response, request.getCookies() == null ? new Cookie[] {} : request.getCookies(), creatorUUID, videoName);
    }

    @RequestMapping(value = "/getSession", method = {RequestMethod.GET, RequestMethod.POST})
    public Session getSession(@RequestParam("room_id") String roomId) {
        return this.sessionModel.getSession(roomId);
    }

    @RequestMapping(value = "/setTimeSession", method = {RequestMethod.GET, RequestMethod.POST})
    public Session setTimeSession(HttpServletRequest request, @RequestParam("time_ms") long timeMS) {
        return this.sessionModel.setTimeSession(
                request.getCookies() == null ? new Cookie[] {} : request.getCookies(),
                timeMS
        );
    }

    @RequestMapping(value = "/allRooms", method = {RequestMethod.GET, RequestMethod.POST})
    public Map<String, Session> allRooms() {
        return this.sessionModel.getSessions();
    }
}
