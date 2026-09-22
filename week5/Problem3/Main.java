class BookInventory {

    private int copiesTotal;
    private int copiesAvailable;

    public BookInventory(int copiesTotal) {

        if (copiesTotal <= 0) {
            throw new IllegalArgumentException(
                    "Copies total must be positive");
        }

        this.copiesTotal = copiesTotal;
        this.copiesAvailable = copiesTotal;
    }

    public void checkOut() {

        if (copiesAvailable > 0) {
            copiesAvailable--;
        }
    }

    public void checkIn() {

        if (copiesAvailable < copiesTotal) {
            copiesAvailable++;
        }
    }

    public int getCopiesAvailable() {
        return copiesAvailable;
    }
}


public class Main {

    public static void main(String[] args) {

        // Valid inventory
        BookInventory b = new BookInventory(3);

        // Four checkouts
        b.checkOut();
        b.checkOut();
        b.checkOut();
        b.checkOut();

        System.out.println(
                "After checkouts: "
                + b.getCopiesAvailable());

        // Four check-ins
        b.checkIn();
        b.checkIn();
        b.checkIn();
        b.checkIn();

        System.out.println(
                "After check-ins: "
                + b.getCopiesAvailable());

        // Invalid construction
        try {
            new BookInventory(0);
        } catch (IllegalArgumentException e) {
            System.out.println("Construction rejected");
        }
    }
}