public class Item {

    private String genotype;
    private String name;
    private double fitness;
    private double weight;
    private int value;
    private int rangeStart;
    private int rangeEnd;

    public Item(String genotype) {
        this.genotype = genotype;
    }

    public Item(String name, int value, double weight) {
        this.name = name;
        this.value = value;
        this.weight = weight;
    }

    public String getGenotype() {
        return genotype;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public double getFitness() {
        return fitness;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public double getWeight() {
        return weight;
    }

    public void setRangeStart(int rangeStart) {
        this.rangeStart = rangeStart;
    }

    public int getRangeStart() {
        return rangeStart;
    }

    public void setRangeEnd(int rangeEnd) {
        this.rangeEnd = rangeEnd;
    }

    public int getRangeEnd() {
        return rangeEnd;
    }
}
