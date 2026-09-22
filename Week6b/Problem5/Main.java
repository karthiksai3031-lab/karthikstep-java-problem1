class EventTicket {

    private static int ticketsIssued = 0;

    private final String ticketId;
    protected double basePrice;
    protected double amountPaid;

    public EventTicket(double basePrice) {

        ticketsIssued++;

        this.ticketId =
                "TCK-" + (1000 + ticketsIssued);

        this.basePrice = basePrice;
        this.amountPaid = 0;
    }

    public void pay(double amount) {
        amountPaid += amount;
    }

    public void pay(double amount, String mode) {
        pay(amount);
    }

    public static boolean isValidPromoCode(String code) {

        if (code == null || code.length() != 5) {
            return false;
        }

        if (code.charAt(0) != 'F') {
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

    public static int getTicketsIssued() {
        return ticketsIssued;
    }

    public double getBalanceDue() {
        return basePrice - amountPaid;
    }

    public String getTicketId() {
        return ticketId;
    }
}


class GroupTicket extends EventTicket {

    private int groupSize;

    public GroupTicket(
            double basePrice,
            int groupSize) {

        super(basePrice);
        this.groupSize = groupSize;
    }
}


public class Main {

    public static String processNightlySettlement(
            EventTicket[] tickets) {

        int processed = 0;
        int nullSkipped = 0;
        int group = 0;
        int individual = 0;

        for (EventTicket ticket : tickets) {

            if (ticket == null) {
                nullSkipped++;
                continue;
            }

            processed++;

            if (ticket instanceof GroupTicket) {
                group++;
            } else {
                individual++;
            }
        }

        return processed + " processed | "
                + nullSkipped + " null skipped | "
                + group + " group | "
                + individual + " individual";
    }


    public static void main(String[] args) {

        EventTicket t1 =
                new EventTicket(500);

        System.out.println(
                "Ticket ID: " + t1.getTicketId());

        System.out.println(
                "Tickets Issued: "
                + EventTicket.getTicketsIssued());

        System.out.println(
                "F123A: "
                + EventTicket.isValidPromoCode("F123A"));

        System.out.println(
                "F12A: "
                + EventTicket.isValidPromoCode("F12A"));

        System.out.println(
                "X123A: "
                + EventTicket.isValidPromoCode("X123A"));

        t1.pay(200);
        t1.pay(200, "UPI");

        System.out.println(
                "Balance: " + t1.getBalanceDue());

        EventTicket[] tickets = {
            new GroupTicket(2000, 5),
            null,
            new EventTicket(500)
        };

        System.out.println(
                processNightlySettlement(tickets));
    }
}