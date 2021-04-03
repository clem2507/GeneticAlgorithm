public class Item {

    private String genotype;
    private double fitness;
    private int rangeStart;
    private int rangeEnd;

    public Item(String genotype) {
        this.genotype = genotype;
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
