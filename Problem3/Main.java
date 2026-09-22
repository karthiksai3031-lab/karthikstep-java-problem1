    class PatientVitals {

    private double[] readings;
    private int count;

    public PatientVitals(double[] initialReadings) {
        readings = new double[500];
        count = 0;

        for (double reading : initialReadings) {
            recordReading(reading);
        }
    }

    public void recordReading(double reading) {
        if (reading <= 0 || reading > 45) {
            return;
        }

        readings[count] = reading;
        count++;
    }

    public double getAverage() {
        if (count == 0) {
            return 0;
        }

        double sum = 0;

        for (int i = 0; i < count; i++) {
            sum += readings[i];
        }

        return sum / count;
    }

    public double[] getAllReadings() {
        double[] result = new double[count];

        for (int i = 0; i < count; i++) {
            result[i] = readings[i];
        }

        return result;
    }
}


public class Main {

    public static void main(String[] args) {

        PatientVitals patient =
                new PatientVitals(new double[]{36.5, -2, 37.1});

        System.out.println("Readings:");

        for (double reading : patient.getAllReadings()) {
            System.out.println(reading);
        }

        System.out.println("Average: " + patient.getAverage());

        // Defensive copy test
        double[] readings = patient.getAllReadings();
        readings[0] = 99.9;

        System.out.println("After modifying returned array:");

        for (double reading : patient.getAllReadings()) {
            System.out.println(reading);
        }
    }
}