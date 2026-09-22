class LibraryMember {

    private String membershipId;
    private String name;
    private boolean premiumMember;
    private String securityAnswerHash;

    // No-argument constructor
    public LibraryMember() {
        this(null, null);
    }

    // Name-only constructor
    public LibraryMember(String name) {
        this(null, name);
    }

    // ID + name constructor
    public LibraryMember(String membershipId, String name) {
        this.membershipId = membershipId;
        this.name = name;
        this.premiumMember = false;
    }

    public String getMembershipId() {
        return membershipId;
    }

    public void setMembershipId(String id) {
        if (this.membershipId == null) {
            this.membershipId = id;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPremiumMember() {
        return premiumMember;
    }

    public void setPremiumMember(boolean premium) {
        this.premiumMember = premium;
    }

    public void setSecurityAnswer(String answer) {
        if (answer != null) {
            securityAnswerHash = Integer.toHexString(answer.hashCode());
        }
    }
}


public class Main {

    public static void main(String[] args) {

        LibraryMember member1 =
                new LibraryMember("Priya Nair");

        System.out.println("Name: " + member1.getName());

        System.out.println(
                "Membership ID: "
                + member1.getMembershipId());

        LibraryMember member2 =
                new LibraryMember(
                        "LIB-8841",
                        "Priya Nair");

        System.out.println(
                "Member 2 ID: "
                + member2.getMembershipId());

        LibraryMember member3 =
                new LibraryMember();

        member3.setMembershipId("LIB-8841");
        member3.setMembershipId("FAKE-0000");

        System.out.println(
                "Final membership ID: "
                + member3.getMembershipId());

        member3.setPremiumMember(true);

        System.out.println(
                "Premium member: "
                + member3.isPremiumMember());

        member3.setSecurityAnswer("blue");

        System.out.println("Security answer stored.");
    }
}