import java.util.Arrays;

class EventTicket {

    protected double basePrice;
    protected double amountPaid;

    private double[] lateFeeHistory;
    private int feeCount;

    public EventTicket(String attendeeId, double basePrice) {
        this.basePrice = basePrice;
        this.amountPaid = 0;
        this.lateFeeHistory = new double[10];
        this.feeCount = 0;
    }

    public void pay(double amount) {
        amountPaid += amount;
    }

    public double getBalanceDue() {
        return basePrice - amountPaid;
    }

    protected void applyLateFee(double amount) {
        amountPaid -= amount;
        lateFeeHistory[feeCount] = amount;
        feeCount++;
    }

    public double[] getLateFeeHistory() {
        return Arrays.copyOf(lateFeeHistory, feeCount);
    }
}


class WorkshopTicket extends EventTicket {

    public WorkshopTicket(
            String attendeeId,
            double basePrice,
            String track) {

        super(attendeeId, basePrice);
    }

    @Override
    protected void applyLateFee(double amount) {
        super.applyLateFee(amount * 2);
    }
}


public class Main {

    public static void main(String[] args) {

        WorkshopTicket ticket =
                new WorkshopTicket(
                        "STU2",
                        1200,
                        "AI/ML");

        ticket.pay(1200);

        ticket.applyLateFee(100);

        System.out.println(
                "Balance: " + ticket.getBalanceDue());

        double[] history =
                ticket.getLateFeeHistory();

        System.out.println(
                "History: " + Arrays.toString(history));

        // Test defensive copy
        history[0] = 9999;

        double[] newHistory =
                ticket.getLateFeeHistory();

        System.out.println(
                "Internal History: "
                + Arrays.toString(newHistory));
    }
}