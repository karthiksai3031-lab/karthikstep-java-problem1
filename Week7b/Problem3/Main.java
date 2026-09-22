interface Auditable {
    String auditRecord();
}


abstract class StaffMember {

    private double baseSalary;
    protected double bonusRate;

    public StaffMember(double baseSalary) {
        this(baseSalary, 0.10);
    }

    public StaffMember(double baseSalary, double bonusRate) {
        if (baseSalary < 0) {
            throw new IllegalArgumentException("Salary cannot be negative");
        }

        this.baseSalary = baseSalary;
        this.bonusRate = bonusRate;
    }

    public double getSalary() {
        return baseSalary;
    }

    public void setSalary(double baseSalary) {
        if (baseSalary >= 0) {
            this.baseSalary = baseSalary;
        }
    }

    public abstract double calculateBonus();
}


class TeamLead extends StaffMember implements Auditable {

    private int teamSize;

    public TeamLead(double baseSalary, int teamSize) {
        super(baseSalary);
        this.teamSize = teamSize;
    }

    public TeamLead(double baseSalary, double bonusRate, int teamSize) {
        super(baseSalary, bonusRate);
        this.teamSize = teamSize;
    }

    @Override
    public double calculateBonus() {
        return getSalary() * bonusRate;
    }

    @Override
    public String auditRecord() {
        return "TeamLead audit: "
                + teamSize
                + " team members, salary $"
                + getSalary();
    }
}


public class Main {

    public static String getAuditIfApplicable(StaffMember s) {

        if (s instanceof Auditable) {
            Auditable a = (Auditable) s;
            return a.auditRecord();
        }

        return "No audit required";
    }


    public static void main(String[] args) {

        TeamLead lead1 =
                new TeamLead(60000, 5);

        TeamLead lead2 =
                new TeamLead(60000, 0.20, 5);

        System.out.println(
                lead1.calculateBonus());

        System.out.println(
                lead2.calculateBonus());

        lead1.setSalary(-5000);

        System.out.println(
                lead1.getSalary());

        System.out.println(
                getAuditIfApplicable(lead1));
    }
}