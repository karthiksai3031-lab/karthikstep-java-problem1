class EventTicket {

    protected double basePrice;
    protected double amountPaid;

    public EventTicket(String attendeeId, double basePrice) {
        this.basePrice = basePrice;
        this.amountPaid = 0;
    }

    public void pay(double amount) {
        amountPaid += amount;
    }

    public double getBalanceDue() {
        return basePrice - amountPaid;
    }

    public void printTicket() {
        System.out.print(
                "Standard | Balance: "
                + getBalanceDue());
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

    public String getTrack() {
        return track;
    }

    @Override
    public void printTicket() {
        System.out.print(
                "Workshop | Track: "
                + track
                + " | Balance: "
                + getBalanceDue());
    }
}


public class Main {

    public static String batchPrint(
            EventTicket[] tickets) {

        StringBuilder report =
                new StringBuilder();

        for (EventTicket ticket : tickets) {

            ticket.printTicket();

            report.append(
                    ticket instanceof WorkshopTicket
                            ? "Workshop | Track: "
                            + ((WorkshopTicket) ticket).getTrack()
                            + " | Balance: "
                            + ticket.getBalanceDue()
                            : "Standard | Balance: "
                            + ticket.getBalanceDue());

            report.append(
                    " | ");

            if (ticket instanceof WorkshopTicket) {

                WorkshopTicket workshop =
                        (WorkshopTicket) ticket;

                report.append(
                        "[Track via downcast: "
                        + workshop.getTrack()
                        + "] | ");
            }
        }

        return report.toString();
    }


    public static void main(String[] args) {

        EventTicket standard =
                new EventTicket(
                        "STU1",
                        500);

        WorkshopTicket workshop =
                new WorkshopTicket(
                        "STU2",
                        1200,
                        "AI/ML");

        EventTicket[] tickets = {
            standard,
            workshop
        };

        System.out.println(
                batchPrint(tickets));
    }
}