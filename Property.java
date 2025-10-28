package assignment4;

public class Property {
    private String propertyName;
    private String city;
    private String owner;
    private double rentAmount;
    private Plot plot;
    private String imageName; // Field for property image

    // Default constructor
    public Property() {
        this("", "", 0.0, "", new Plot(), null);
    }

    // Constructor without plot (uses default plot)
    public Property(String propertyName, String city, double rentAmount, String owner) {
        this(propertyName, city, rentAmount, owner, new Plot(), null);
    }

    // Constructor with plot coordinates
    public Property(String propertyName, String city, double rentAmount, String owner,
                    int x, int y, int width, int depth) {
        this(propertyName, city, rentAmount, owner, new Plot(x, y, width, depth), null);
    }

    // Constructor with plot coordinates and image
    public Property(String propertyName, String city, double rentAmount, String owner,
                    int x, int y, int width, int depth, String imageName) {
        this(propertyName, city, rentAmount, owner, new Plot(x, y, width, depth), imageName);
    }

    // Constructor with Plot object
    public Property(String propertyName, String city, double rentAmount, String owner, Plot plot) {
        this(propertyName, city, rentAmount, owner, plot, null);
    }

    // Constructor with Plot object and image
    public Property(String propertyName, String city, double rentAmount, String owner, Plot plot, String imageName) {
        this.propertyName = propertyName;
        this.city = city;
        this.rentAmount = rentAmount;
        this.owner = owner;
        this.plot = (plot == null) ? new Plot() : new Plot(plot);
        this.imageName = imageName;
    }

    // Copy constructor
    public Property(Property other) {
        if (other == null) {
            this.propertyName = "";
            this.city = "";
            this.owner = "";
            this.rentAmount = 0.0;
            this.plot = new Plot();
            this.imageName = null;
        } else {
            this.propertyName = other.propertyName;
            this.city = other.city;
            this.owner = other.owner;
            this.rentAmount = other.rentAmount;
            this.plot = new Plot(other.plot);
            this.imageName = other.imageName;
        }
    }

    // Getters and setters
    public String getPropertyName() { return propertyName; }
    public void setPropertyName(String propertyName) { this.propertyName = propertyName; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }

    public double getRentAmount() { return rentAmount; }
    public void setRentAmount(double rentAmount) { this.rentAmount = rentAmount; }

    public Plot getPlot() { return new Plot(plot); }
    public void setPlot(Plot plot) { this.plot = (plot == null) ? new Plot() : new Plot(plot); }

    public String getImageName() { return imageName; }
    public void setImageName(String imageName) { this.imageName = imageName; }

    @Override
    public String toString() {
        return propertyName + "," + city + "," + owner + "," + rentAmount;
    }
}
