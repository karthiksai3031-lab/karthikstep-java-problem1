class AccessRuleEngine {

    static String classifyAccess(String fieldModifier, String accessorContext) {

        if (fieldModifier.equals("private")) {
            if (accessorContext.equals("SAME_CLASS")) {
                return "ALLOWED";
            }
            return "DENIED";
        }

        if (fieldModifier.equals("default")) {
            if (accessorContext.equals("SAME_CLASS") ||
                accessorContext.equals("SAME_PACKAGE")) {
                return "ALLOWED";
            }
            return "DENIED";
        }

        if (fieldModifier.equals("protected")) {
            if (accessorContext.equals("SAME_CLASS") ||
                accessorContext.equals("SAME_PACKAGE")) {
                return "ALLOWED";
            }
            return "DENIED";
        }

        if (fieldModifier.equals("public")) {
            return "ALLOWED";
        }

        return "DENIED";
    }

    static String summarizeBatch(String[][] attempts) {

        int allowed = 0;
        int denied = 0;

        for (String[] attempt : attempts) {

            String result = classifyAccess(attempt[0], attempt[1]);

            if (result.equals("ALLOWED")) {
                allowed++;
            } else {
                denied++;
            }
        }

        return "Allowed: " + allowed + " | Denied: " + denied;
    }
}


class PatientRecord {

    private String patientId;
    String wardCode;                  // default
    protected double vitalsScore;
    public String facilityName;

    public PatientRecord(String patientId, String wardCode,
                         double vitalsScore, String facilityName) {

        if (patientId == null ||
            patientId.trim().isEmpty() ||
            patientId.length() < 4) {

            throw new IllegalArgumentException("Invalid patientId");
        }

        this.patientId = patientId;
        this.wardCode = wardCode;
        this.vitalsScore = vitalsScore;
        this.facilityName = facilityName;
    }
}


public class Main {

    public static void main(String[] args) {

        // Test 1
        System.out.println(
            AccessRuleEngine.classifyAccess(
                "private", "SAME_CLASS"
            )
        );

        // Test 2
        System.out.println(
            AccessRuleEngine.classifyAccess(
                "default", "DIFFERENT_PACKAGE"
            )
        );

        // Test 3
        String[][] attempts = {
            {"protected", "SAME_PACKAGE"},
            {"protected", "DIFFERENT_PACKAGE"},
            {"public", "DIFFERENT_PACKAGE"}
        };

        System.out.println(
            AccessRuleEngine.summarizeBatch(attempts)
        );

        // Test 4 - Valid PatientRecord
        PatientRecord patient = new PatientRecord(
            "MT94",
            "W3",
            98.2,
            "MediTrack Central"
        );

        System.out.println("Patient record created successfully.");

        // Test 5 - Invalid PatientRecord
        try {

            PatientRecord invalidPatient = new PatientRecord(
                "MT9",
                "W3",
                98.2,
                "MediTrack Central"
            );

        } catch (IllegalArgumentException e) {

            System.out.println("construction rejected");
        }
    }
}