class RaceEntry {

    protected String bibNumber;
    protected double entryFee;
    protected double amountPaid;

    private final int entryCode;

    private static int bibCounter = 0;

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

        bibCounter++;
        entryCode = bibCounter;
    }

    public void pay(double amount) {
        amountPaid += amount;
    }

    public void pay(double amount, String mode) {
        pay(amount);
        System.out.println("Paying via " + mode);
    }

    public double getBalanceDue() {
        return entryFee - amountPaid;
    }

    public static boolean isValidDiscountCode(String code) {

        if (code == null || code.length() != 5) {
            return false;
        }

        if (code.charAt(0) != 'M') {
            return false;
        }

        if (!Character.isDigit(code.charAt(1))
                || !Character.isDigit(code.charAt(2))
                || !Character.isDigit(code.charAt(3))) {
            return false;
        }

        if (!Character.isUpperCase(code.charAt(4))) {
            return false;
        }

        return true;
    }

    public static int getBibCounter() {
        return bibCounter;
    }

    public static String settleNight(RaceEntry[] entries) {

        int processed = 0;
        int nullSkipped = 0;
        int relay = 0;
        int individual = 0;

        for (RaceEntry entry : entries) {

            if (entry == null) {
                nullSkipped++;
                continue;
            }

            processed++;

            if (entry instanceof RelayTeamEntry) {
                relay++;
            } else {
                individual++;
            }
        }

        return processed + " processed | "
                + nullSkipped + " null skipped | "
                + relay + " relay | "
                + individual + " individual";
    }
}


class RunnerEntry extends RaceEntry {

    protected String category;

    public RunnerEntry(
            String bibNumber,
            double entryFee,
            String category) {

        super(bibNumber, entryFee);
        this.category = category;
    }
}


class EliteRunnerEntry extends RunnerEntry {

    private double sponsorBonus;

    public EliteRunnerEntry(
            String bibNumber,
            double entryFee,
            String category,
            double sponsorBonus) {

        super(bibNumber, entryFee, category);
        this.sponsorBonus = sponsorBonus;
    }
}


class RelayTeamEntry extends RaceEntry {

    private int teamSize;

    public RelayTeamEntry(
            String bibNumber,
            double entryFee,
            int teamSize) {

        super(bibNumber, entryFee);
        this.teamSize = teamSize;
    }
}


public class Main {

    public static void main(String[] args) {

        System.out.println(
                RaceEntry.isValidDiscountCode("M123A"));

        System.out.println(
                RaceEntry.isValidDiscountCode("M12A"));

        System.out.println(
                RaceEntry.isValidDiscountCode("X123A"));

        EliteRunnerEntry eliteEntry =
                new EliteRunnerEntry(
                        "BIB3001",
                        150,
                        "Elite Full Marathon",
                        500);

        RelayTeamEntry relayEntry =
                new RelayTeamEntry(
                        "BIB4001",
                        300,
                        4);

        eliteEntry.pay(10, "UPI");

        RaceEntry[] entries = {
            eliteEntry,
            null,
            relayEntry
        };

        System.out.println(
                RaceEntry.settleNight(entries));

        System.out.println(
                "Bib Counter: "
                + RaceEntry.getBibCounter());
    }
}