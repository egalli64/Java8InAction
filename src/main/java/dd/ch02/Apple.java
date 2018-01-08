package dd.ch02;

public class Apple {
    private final String color;
    private final int weight;

    public Apple(String color, int weight) {
        this.color = color;
        this.weight = weight;
    }
    
    public Apple() {
        this.color = "red";
        this.weight = 100;
    }
    
    public Apple(int weight) {
        this.color = "red";
        this.weight = weight;
    }

    public String getColor() {
        return color;
    }

    public Integer getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return "[" + color + ", " + weight + "]";
    }
}