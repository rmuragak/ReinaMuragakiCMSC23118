package assignment4;

public class PropertyManagementDriver {
    public static void main(String[] args) {
        // Create the management company
        ManagementCompany mgmt = new ManagementCompany("Railey", "555555555", 10);

        System.out.println("=== Adding Properties ===");

        // Successful property additions
        Property p1 = new Property("Almost Aspen", "Glendale", 4844, "Sammy Smith", 0, 0, 3, 3);
        Property p2 = new Property("Ambiance", "Lakewood", 4114, "Tammy Taylor", 3, 0, 3, 3);
        Property p3 = new Property("Bear Creek Lodge", "Peninsula", 4905, "Bubba Burley", 6, 0, 3, 3);
        Property p4 = new Property("Sunsational", "Beckman", 2613, "BillyBob Wilson", 0, 3, 3, 3);
        Property p5 = new Property("Mystic Cove", "Lakepointe", 5327, "Joey BagODonuts", 3, 3, 3, 3);

        // Add them to the management company
        System.out.println("Add p1: " + mgmt.addProperty(p1));
        System.out.println("Add p2: " + mgmt.addProperty(p2));
        System.out.println("Add p3: " + mgmt.addProperty(p3));
        System.out.println("Add p4: " + mgmt.addProperty(p4));
        System.out.println("Add p5: " + mgmt.addProperty(p5));

        // Try adding a property that overlaps (should fail with -4)
        Property overlapping = new Property("Overlap Test", "Glendale", 3000, "Overlap Owner", 2, 2, 3, 3);
        System.out.println("Add overlapping: " + mgmt.addProperty(overlapping));

        // Try adding a property outside the management plot (should fail with -3)
        Property outside = new Property("Outside Test", "Nowhere", 2000, "No Owner", 20, 20, 3, 3);
        System.out.println("Add outside: " + mgmt.addProperty(outside));

        // Try adding a property when array is full (should fail with -1)
        Property extra = new Property("Extra Property", "ExtraCity", 1500, "Extra Owner", 0, 6, 3, 3);
        System.out.println("Add extra (full array): " + mgmt.addProperty(extra));

        // Print all properties and management fee
        System.out.println("\n=== List of Properties ===");
        System.out.println(mgmt);

        // Print total rent
        System.out.println("\nTotal Rent: " + mgmt.getTotalRent());

        // Print highest rent property
        Property highest = mgmt.getHighestRentProperty();
        System.out.println("Highest Rent Property: " + highest);
    }
}
