class EventTicket {

    protected String attendeeId;
    protected double basePrice;
    protected double amountPaid;

    public EventTicket(String attendeeId, double basePrice) {

        String trimmedId = attendeeId == null
                ? ""
                : attendeeId.trim();

        if (trimmedId.isEmpty() || trimmedId.length() < 4) {
            throw new IllegalArgumentException("Invalid attendee ID");
        }

        this.attendeeId = trimmedId;
        this.basePrice = basePrice;
        this.amountPaid = 0;
    }

    public void pay(double amount) {
        amountPaid += amount;
    }

    public double getBalanceDue() {
        return basePrice - amountPaid;
    }

    public static String registerBatch(
            String[] attendeeIds,
            double basePrice) {

        int registered = 0;
        int rejected = 0;

        for (String id : attendeeIds) {

            try {
                new EventTicket(id, basePrice);
                registered++;
            } catch (IllegalArgumentException e) {
                rejected++;
            }
        }

        return "Registered: " + registered
                + " | Rejected: " + rejected;
    }
}


class WorkshopTicket extends EventTicket {

    private String track;

    public WorkshopTicket(
            String attendeeId,
            double basePrice,
            String track) {

        super(attendeeId, basePrice);
        this.track = track;
    }
}


public class Main {

    public static void main(String[] args) {

        try {
            new EventTicket("ST1", 500);
        } catch (IllegalArgumentException e) {
            System.out.println("Construction rejected");
        }

        WorkshopTicket w =
                new WorkshopTicket(
                        "STU2",
                        1200,
                        "AI/ML");

        w.pay(500);

        System.out.println(
                "Balance: " + w.getBalanceDue());

        String[] attendeeIds = {
            "STU1",
            "ST1",
            "STU2",
            " ",
            "STU3"
        };

        System.out.println(
                EventTicket.registerBatch(
                        attendeeIds,
                        500));
    }
}