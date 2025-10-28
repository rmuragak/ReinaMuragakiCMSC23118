package assignment4;

public class ManagementCompany {
    private String name;
    private String taxID;
    private double mgmtFee; // percentage 0-100
    public static final int MAX_PROPERTY = 5;
    public static final int MGMT_WIDTH = 10;
    public static final int MGMT_DEPTH = 10;

    private Property[] properties;
    private Plot plot; // management company plot
    private int numberOfProperties;

    // default constructors
    public ManagementCompany() {
        this("", "", 0.0, 0, 0, MGMT_WIDTH, MGMT_DEPTH);
    }

    public ManagementCompany(String name, String taxID, double mgmtFee) {
        this(name, taxID, mgmtFee, 0, 0, MGMT_WIDTH, MGMT_DEPTH);
    }

    public ManagementCompany(String name, String taxID, double mgmtFee, int x, int y, int width, int depth) {
        this.name = name;
        this.taxID = taxID;
        this.mgmtFee = mgmtFee;
        this.properties = new Property[MAX_PROPERTY];
        this.plot = new Plot(x, y, width, depth);
        this.numberOfProperties = 0;
    }

    public ManagementCompany(ManagementCompany other) {
        if (other == null) {
            this.name = "";
            this.taxID = "";
            this.mgmtFee = 0.0;
            this.plot = new Plot();
            this.properties = new Property[MAX_PROPERTY];
            this.numberOfProperties = 0;
        } else {
            this.name = other.name;
            this.taxID = other.taxID;
            this.mgmtFee = other.mgmtFee;
            this.plot = new Plot(other.plot);
            this.properties = new Property[MAX_PROPERTY];
            for (int i = 0; i < other.properties.length; i++) {
                if (other.properties[i] != null) {
                    this.properties[i] = new Property(other.properties[i]);
                } else {
                    this.properties[i] = null;
                }
            }
            this.numberOfProperties = other.numberOfProperties;
        }
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getTaxID() { return taxID; }
    public void setTaxID(String taxID) { this.taxID = taxID; }

    public double getMgmtFee() { return mgmtFee; }
    public void setMgmtFee(double mgmtFee) { this.mgmtFee = mgmtFee; }

    public Plot getPlot() { return new Plot(plot); }

    public Property[] getProperties() {
        return properties;
    }

    public int addProperty(Property property) {
        if (isPropertiesFull()) return -1;
        if (property == null) return -2;
        if (!this.plot.encompasses(property.getPlot())) return -3;
        for (int i = 0; i < MAX_PROPERTY; i++) {
            if (properties[i] != null && properties[i].getPlot().overlaps(property.getPlot())) {
                return -4;
            }
        }
        for (int i = 0; i < MAX_PROPERTY; i++) {
            if (properties[i] == null) {
                properties[i] = new Property(property);
                numberOfProperties++;
                return i;
            }
        }
        return -1;
    }

    public int addProperty(String name, String city, double rent, String owner) {
        return addProperty(new Property(name, city, rent, owner));
    }

    public int addProperty(String name, String city, double rent, String owner, int x, int y, int width, int depth) {
        return addProperty(new Property(name, city, rent, owner, x, y, width, depth));
    }

    public double getTotalRent() {
        double total = 0.0;
        for (Property p : properties) {
            if (p != null) total += p.getRentAmount();
        }
        return total;
    }

    public Property getHighestRentProperty() {
        Property highest = null;
        double max = Double.NEGATIVE_INFINITY;
        for (Property p : properties) {
            if (p != null && p.getRentAmount() > max) {
                max = p.getRentAmount();
                highest = p;
            }
        }
        return highest;
    }

    public void removeLastProperty() {
        for (int i = MAX_PROPERTY - 1; i >= 0; i--) {
            if (properties[i] != null) {
                properties[i] = null;
                numberOfProperties--;
                break;
            }
        }
    }

    public boolean isPropertiesFull() {
        return numberOfProperties >= MAX_PROPERTY;
    }

    public int getPropertiesCount() {
        return numberOfProperties;
    }

    public boolean isMangementFeeValid() {
        return (mgmtFee >= 0.0 && mgmtFee <= 100.0);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("List of the properties for ").append(name).append(", taxID: ").append(taxID).append("\n");
        sb.append("______________________________________________________\n");
        for (Property p : properties) {
            if (p != null) sb.append(p.toString()).append("\n");
        }
        sb.append("______________________________________________________\n\n");
        double totalFee = getTotalRent() * (mgmtFee / 100.0);
        sb.append(String.format(" total management Fee: %.2f\n", totalFee));
        return sb.toString();
    }
}
