package lk.com.ipccw2;

import com.google.firebase.Timestamp;

public class SensorData {
    private String sensorId, speed;
    private Timestamp createdAt;

    public SensorData(String sensorId, String speed, Timestamp createdAt) {
        this.sensorId = sensorId;
        this.speed = speed;
        this.createdAt = createdAt;
    }

    public String getSensorId() {
        return sensorId;
    }

    public String getSpeed() {
        return speed;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }
}
