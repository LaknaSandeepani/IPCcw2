package lk.com.ipccw2;

import com.google.firebase.Timestamp;

public class SensorData {
    private String sensorId, speed, accelerationx,accelerationy,accelerationz, rotationx, rotationy,rotationz;
    private Timestamp createdAt;


    public SensorData(String sensorId, String speed, String accelerationx, String accelerationy, String accelerationz, String rotationx, String rotationy, String rotationz, Timestamp createdAt) {
        this.sensorId = sensorId;
        this.speed = speed;
        this.accelerationx = accelerationx;
        this.accelerationy = accelerationy;
        this.accelerationz = accelerationz;
        this.rotationx = rotationx;
        this.rotationy = rotationy;
        this.rotationz = rotationz;
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

    public String getAccelerationx() {
        return accelerationx;
    }
    public String getAccelerationy() {
        return accelerationy;
    }

    public String getAccelerationz() {
        return accelerationz;
    }
    public String getRotationx() {
        return rotationx;
    }

    public String getRotationy() {
        return rotationy;
    }

    public String getRotationz() {
        return rotationz;
    }

}
