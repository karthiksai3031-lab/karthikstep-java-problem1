class AccessChecker {

    static String classifyAccess(String fieldModifier, String accessorContext) {

        if (fieldModifier.equals("private")) {
            return accessorContext.equals("SAME_CLASS")
                    ? "ALLOWED" : "DENIED";
        }

        if (fieldModifier.equals("default")) {
            return accessorContext.equals("SAME_CLASS")
                    || accessorContext.equals("SAME_PACKAGE")
                    ? "ALLOWED" : "DENIED";
        }

        if (fieldModifier.equals("protected")) {
            return accessorContext.equals("SAME_CLASS")
                    || accessorContext.equals("SAME_PACKAGE")
                    ? "ALLOWED" : "DENIED";
        }

        if (fieldModifier.equals("public")) {
            return "ALLOWED";
        }

        return "DENIED";
    }


    static String summarizeByModifier(String[][] attempts) {

        int privateAllowed = 0;
        int privateDenied = 0;

        int defaultAllowed = 0;
        int defaultDenied = 0;

        int protectedAllowed = 0;
        int protectedDenied = 0;

        int publicAllowed = 0;
        int publicDenied = 0;

        for (String[] attempt : attempts) {

            String modifier = attempt[0];
            String context = attempt[1];

            String result = classifyAccess(modifier, context);

            if (modifier.equals("private")) {
                if (result.equals("ALLOWED")) {
                    privateAllowed++;
                } else {
                    privateDenied++;
                }

            } else if (modifier.equals("default")) {
                if (result.equals("ALLOWED")) {
                    defaultAllowed++;
                } else {
                    defaultDenied++;
                }

            } else if (modifier.equals("protected")) {
                if (result.equals("ALLOWED")) {
                    protectedAllowed++;
                } else {
                    protectedDenied++;
                }

            } else if (modifier.equals("public")) {
                if (result.equals("ALLOWED")) {
                    publicAllowed++;
                } else {
                    publicDenied++;
                }
            }
        }

        return "private: " + privateAllowed + " allowed / "
                + privateDenied + " denied | "
                + "default: " + defaultAllowed + " allowed / "
                + defaultDenied + " denied | "
                + "protected: " + protectedAllowed + " allowed / "
                + protectedDenied + " denied | "
                + "public: " + publicAllowed + " allowed / "
                + publicDenied + " denied";
    }
}


class LibraryMember {

    private String membershipId;
    private String branchCode;
    private double finesOwed;
    private String displayName;

    public LibraryMember(
            String membershipId,
            String branchCode,
            double finesOwed,
            String displayName) {

        String trimmedId = membershipId == null
                ? ""
                : membershipId.trim();

        if (trimmedId.isEmpty() || trimmedId.length() < 4) {
            throw new IllegalArgumentException(
                    "Invalid membership ID");
        }

        this.membershipId = trimmedId;
        this.branchCode = branchCode;
        this.finesOwed = finesOwed;
        this.displayName = displayName;
    }
}


public class Main {

    public static void main(String[] args) {

        String[][] attempts = {
            {"private", "SAME_CLASS"},
            {"private", "SAME_PACKAGE"},
            {"default", "SAME_PACKAGE"},
            {"default", "DIFFERENT_PACKAGE"},
            {"protected", "SAME_PACKAGE"},
            {"protected", "SAME_CLASS"},
            {"public", "DIFFERENT_PACKAGE"}
        };

        System.out.println(
                AccessChecker.summarizeByModifier(attempts)
        );


        System.out.println(
                AccessChecker.classifyAccess(
                        "private", "SAME_CLASS")
        );

        System.out.println(
                AccessChecker.classifyAccess(
                        "protected", "DIFFERENT_PACKAGE")
        );


        // Valid construction
        LibraryMember member = new LibraryMember(
                "LB94",
                "BR1",
                0,
                "Priya Nair"
        );

        System.out.println("Valid member created.");


        // Invalid construction test
        try {
            new LibraryMember(
                    "LB9",
                    "BR1",
                    0,
                    "Priya Nair"
            );
        } catch (IllegalArgumentException e) {
            System.out.println("Construction rejected");
        }
    }
}