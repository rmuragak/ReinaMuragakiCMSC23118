package assignment4;

public class Plot {
    private int x, y, width, depth;

    public Plot() {
        this(0, 0, 1, 1);
    }

    public Plot(int x, int y, int width, int depth) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.depth = depth;
    }

    public Plot(Plot otherPlot) {
        this(otherPlot.x, otherPlot.y, otherPlot.width, otherPlot.depth);
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return width; }
    public int getDepth() { return depth; }

    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    public void setWidth(int width) { this.width = width; }
    public void setDepth(int depth) { this.depth = depth; }

    public boolean overlaps(Plot p) {
        return !(p.x >= x + width || p.x + p.width <= x || p.y >= y + depth || p.y + p.depth <= y);
    }

    public boolean encompasses(Plot p) {
        return (p.x >= x && p.y >= y && p.x + p.width <= x + width && p.y + p.depth <= y + depth);
    }

    public String toString() {
        return x + "," + y + "," + width + "," + depth;
    }
}
