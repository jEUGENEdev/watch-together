package org.jeugenedev.watch.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Session {
    @JsonIgnore
    private final String creatorSessionUUID;
    private final String videoName;
    private long timeMS;

    public Session(String creatorSessionUUID, String videoName) {
        this.creatorSessionUUID = creatorSessionUUID;
        this.videoName = videoName;
    }

    public String getCreatorSessionUUID() {
        return creatorSessionUUID;
    }

    public String getVideoName() {
        return videoName;
    }

    public long getTimeMS() {
        return timeMS;
    }

    public void setTimeMS(long timeMS) {
        this.timeMS = timeMS;
    }
}
