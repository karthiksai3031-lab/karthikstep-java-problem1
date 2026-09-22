interface Alertable {
    String sendAlert(String message);
}


class SecuritySensor {

    private String zoneName;

    public SecuritySensor(String zoneName) {
        this.zoneName = zoneName;
    }

    public String getZoneName() {
        return zoneName;
    }
}


class MotionSensor extends SecuritySensor implements Alertable {

    public MotionSensor(String zoneName) {
        super(zoneName);
    }

    @Override
    public String sendAlert(String message) {
        return "[" + getZoneName() + "] " + message;
    }
}


class DualZoneMotionSensor extends MotionSensor {

    private String secondZoneName;

    public DualZoneMotionSensor(String zoneName, String secondZoneName) {
        super(zoneName);
        this.secondZoneName = secondZoneName;
    }

    @Override
    public String sendAlert(String message) {
        return super.sendAlert(message)
                + " [also covering " + secondZoneName + "]";
    }
}


class SmokeDetector implements Alertable {

    private String deviceId;

    public SmokeDetector(String deviceId) {
        this.deviceId = deviceId;
    }

    @Override
    public String sendAlert(String message) {
        return "[" + deviceId + "] " + message;
    }
}


public class Main {

    public static void broadcastAll(
            Alertable[] devices,
            String message) {

        for (Alertable device : devices) {
            System.out.println(device.sendAlert(message));
        }
    }


    public static String getZoneIfMotionSensor(Alertable a) {

        if (a instanceof MotionSensor) {
            MotionSensor sensor = (MotionSensor) a;
            return sensor.getZoneName();
        }

        return "Not a motion sensor";
    }


    public static void main(String[] args) {

        MotionSensor motion =
                new MotionSensor("Living Room");

        DualZoneMotionSensor dual =
                new DualZoneMotionSensor("Hallway", "Stairwell");

        SmokeDetector smoke =
                new SmokeDetector("SD-01");

        System.out.println(
                motion.sendAlert("Motion detected"));

        System.out.println(
                dual.sendAlert("Motion detected"));

        System.out.println(
                smoke.sendAlert("Smoke detected"));

        Alertable[] devices = {
            motion,
            dual,
            smoke
        };

        broadcastAll(devices, "Security alert");

        System.out.println(
                getZoneIfMotionSensor(motion));

        System.out.println(
                getZoneIfMotionSensor(smoke));
    }
}