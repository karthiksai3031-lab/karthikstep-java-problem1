class DischargeSummary {

    private final String patientId;
    private final String[] medicationCodes;

    // One-time shared initialization
    static {
        System.out.println("Discharge Summary system initialized.");
    }

    public DischargeSummary(String patientId, String[] medicationCodes) {

        if (patientId == null || medicationCodes == null) {
            throw new IllegalArgumentException("Invalid input");
        }

        // Validate every medication code first
        for (String code : medicationCodes) {
            if (code == null || !code.matches("MED-[A-Z]")) {
                throw new IllegalArgumentException(
                        "Invalid medication code: " + code);
            }
        }

        // Defensive copy
        this.patientId = patientId;
        this.medicationCodes = medicationCodes.clone();
    }

    public String getPatientId() {
        return patientId;
    }

    // Defensive copy on output
    public String[] getMedicationCodes() {
        return medicationCodes.clone();
    }

    // Returns a brand-new object
    public DischargeSummary withCorrectedMedication(
            int index, String newCode) {

        if (index < 0 || index >= medicationCodes.length) {
            throw new IndexOutOfBoundsException("Invalid index");
        }

        if (newCode == null || !newCode.matches("MED-[A-Z]")) {
            throw new IllegalArgumentException(
                    "Invalid medication code");
        }

        String[] corrected = medicationCodes.clone();
        corrected[index] = newCode;

        return new DischargeSummary(patientId, corrected);
    }

    // Nightly batch processor
    public static String processNightlyBatch(
            DischargeSummary[] summaries) {

        int processed = 0;
        int nullSkipped = 0;
        int criticalCare = 0;
        int routine = 0;

        if (summaries == null) {
            return "0 processed | 0 null skipped | 0 critical-care | 0 routine";
        }

        for (DischargeSummary summary : summaries) {

            if (summary == null) {
                nullSkipped++;
                continue;
            }

            processed++;

            if (summary instanceof CriticalCareDischargeSummary) {
                criticalCare++;
            } else {
                routine++;
            }
        }

        return processed + " processed | "
                + nullSkipped + " null skipped | "
                + criticalCare + " critical-care | "
                + routine + " routine";
    }
}


// Critical-care subclass
class CriticalCareDischargeSummary extends DischargeSummary {

    private final int icuDays;

    public CriticalCareDischargeSummary(
            String patientId,
            String[] medicationCodes,
            int icuDays) {

        super(patientId, medicationCodes);
        this.icuDays = icuDays;
    }

    public int getIcuDays() {
        return icuDays;
    }
}


public class Main {

    public static void main(String[] args) {

        // Critical-care summary
        CriticalCareDischargeSummary critical =
                new CriticalCareDischargeSummary(
                        "MT001",
                        new String[]{"MED-X"},
                        4);

        // Routine summary
        DischargeSummary routine =
                new DischargeSummary(
                        "MT002",
                        new String[]{"MED-Y"});

        // Defensive copy test
        String[] medications = routine.getMedicationCodes();

        medications[0] = "MED-Z";

        System.out.println(
                "Original medication: "
                + routine.getMedicationCodes()[0]);

        // with-style correction
        DischargeSummary corrected =
                routine.withCorrectedMedication(
                        0, "MED-A");

        System.out.println(
                "Corrected medication: "
                + corrected.getMedicationCodes()[0]);

        // Nightly batch
        DischargeSummary[] batch = {
            critical,
            null,
            routine
        };

        System.out.println(
                DischargeSummary.processNightlyBatch(batch));
    }
}