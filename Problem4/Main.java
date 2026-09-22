class PatientProfile {

    private String patientId;
    private String name;
    private boolean discharged;
    private String lockerPinHash;

    // No-arg constructor
    public PatientProfile() {
        this(null, null);
    }

    // Name-only constructor
    public PatientProfile(String name) {
        this(null, name);
    }

    // ID + name constructor
    public PatientProfile(String patientId, String name) {
        this.patientId = patientId;
        this.name = name;
        this.discharged = false;
    }

    // Patient ID
    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String id) {
        if (this.patientId == null) {
            this.patientId = id;
        }
    }

    // Name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Discharged
    public boolean isDischarged() {
        return discharged;
    }

    public void setDischarged(boolean discharged) {
        this.discharged = discharged;
    }

    // Locker PIN
    public void setLockerPin(String pin) {

        if (pin != null && pin.matches("\\d{4,6}")) {
            lockerPinHash = Integer.toHexString(pin.hashCode());
        }
    }
}


public class Main {

    public static void main(String[] args) {

        // Name-only constructor
        PatientProfile patient1 = new PatientProfile("Karthik");

        System.out.println("Patient 1 name: " + patient1.getName());
        System.out.println("Patient 1 ID: " + patient1.getPatientId());

        // ID + name constructor
        PatientProfile patient2 =
                new PatientProfile("MT2026-0142", "Karthik");

        System.out.println("Patient 2 name: " + patient2.getName());
        System.out.println("Patient 2 ID: " + patient2.getPatientId());

        // setPatientId should work only once
        patient1.setPatientId("MT001");
        patient1.setPatientId("MT002");

        System.out.println("Patient 1 ID after two sets: "
                + patient1.getPatientId());

        // Boolean JavaBean
        patient1.setDischarged(true);

        System.out.println("Discharged: "
                + patient1.isDischarged());

        // Locker PIN
        patient1.setLockerPin("1234");

        System.out.println("Locker PIN stored securely.");
    }
}