class RaceEntry {

    protected String bibNumber;
    protected double entryFee;
    protected double amountPaid;

    public RaceEntry(String bibNumber, double entryFee) {

        String trimmedBib = bibNumber == null
                ? ""
                : bibNumber.trim();

        if (trimmedBib.isEmpty() || trimmedBib.length() < 4) {
            throw new IllegalArgumentException(
                    "Invalid bib number");
        }

        this.bibNumber = trimmedBib;
        this.entryFee = entryFee;
        this.amountPaid = 0;
    }

    public void pay(double amount) {
        amountPaid += amount;
    }

    public double getBalanceDue() {
        return entryFee - amountPaid;
    }

    public static String registerBatch(
            String[] bibNumbers,
            double entryFee) {

        int registered = 0;
        int rejected = 0;

        for (String bib : bibNumbers) {

            try {
                new RaceEntry(bib, entryFee);
                registered++;
            } catch (IllegalArgumentException e) {
                rejected++;
            }
        }

        return "Registered: " + registered
                + " | Rejected: " + rejected;
    }
}


class RunnerEntry extends RaceEntry {

    private String category;

    public RunnerEntry(
            String bibNumber,
            double entryFee,
            String category) {

        super(bibNumber, entryFee);
        this.category = category;
    }
}


public class Main {

    public static void main(String[] args) {

        try {
            new RaceEntry("B1", 50);
        } catch (IllegalArgumentException e) {
            System.out.println("Construction rejected");
        }

        RunnerEntry r = new RunnerEntry(
                "BIB2001",
                80,
                "Open 10K");

        r.pay(30);

        System.out.println(
                "Balance: " + r.getBalanceDue());

        String[] bibNumbers = {
            "BIB1",
            "B1",
            "BIB2"
        };

        System.out.println(
                RaceEntry.registerBatch(
                        bibNumbers,
                        80));
    }
}