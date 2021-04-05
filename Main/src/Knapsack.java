import java.util.ArrayList;
import java.util.Random;

public class Knapsack {

    private int stopCondition;
    private int populationSize;
    private int totalGenerationNumber;
    private double crossoverRate;
    private double mutationRate;
    private double populationFitnessAverage;
    private Item[] itemList;
    private Item[] population;
    private Bag bag;
    private Random random;

    public Knapsack(int stopCondition, int populationSize, double crossoverRate, double mutationRate, double rucksackCapacity, Item[] itemList) {
        this.stopCondition = stopCondition;
        this.populationSize = populationSize;
        this.crossoverRate = crossoverRate;
        this.mutationRate = mutationRate;
        this.itemList = itemList;
        this.population = new Item[populationSize];
        this.bag = new Bag("Suitcase", rucksackCapacity);
        this.random = new Random();
    }

    public void start() {
        System.out.println("----- Start Knapsack -----");
        System.out.println();
        initializePopulation();
        int i = 0;
        while (i<stopCondition) {
            totalGenerationNumber++;
            tournamentSelection();
            System.out.println("--> best population genotype = " + getBestItemInPopulation().getGenotype());
            System.out.println("--> best genotype fitness = " + getBestItemInPopulation().getFitness());
            System.out.println("--> average population fitness = " + (populationFitnessAverage));
            System.out.println("--> generation = " + totalGenerationNumber);
            System.out.println();
            System.out.println("----------------------------");
            System.out.println();
            i++;
        }
        System.out.println("--> number of generations = " + totalGenerationNumber);
        System.out.println();
        System.out.println("--> you should bring in your " + getBag().getType() + ":\n\n" + getBestItemInList());
        System.out.println("----- End Knapsack -----");
    }

    public void initializePopulation() {
        for (int i = 0; i < populationSize; i++) {
            Item item = new Item(createRandomGenotype());
            population[i] = item;
        }
    }

    public String createRandomGenotype() {
        String genotype = String.format("%1$"+itemList.length+"s", "");
        char[] genotypeChars = genotype.toCharArray();
        for (int i = 0; i < genotype.length(); i++) {
            genotypeChars[i] = (char) (random.nextInt(2)+'0');
        }
        genotype = String.valueOf(genotypeChars);
        return genotype;
    }

    public void computeFitness() {
        populationFitnessAverage = 0;
        for (Item item : population) {
            double weightSum = 0;
            double valueSum = 0;
            for (int i = 0; i < item.getGenotype().length(); i++) {
                if (item.getGenotype().charAt(i) == '1') {
                    weightSum+=itemList[i].getWeight();
                    valueSum+=itemList[i].getValue();
                }
            }
            if (weightSum <= bag.getCapacity()) {
                item.setFitness(valueSum);
                populationFitnessAverage+=valueSum;
            }
        }
        populationFitnessAverage/=populationSize;
    }

    public void tournamentSelection() {
        computeFitness();
        ArrayList<Item> nextGeneration = new ArrayList<>();
        while (nextGeneration.size() < populationSize) {
            Item[] parents = new Item[2];
            for (int i = 0; i < parents.length; i++) {
                int r1 = random.nextInt(populationSize);
                int r2 = random.nextInt(populationSize);
                if (population[r1].getFitness() > population[r2].getFitness()) {
                    parents[i] = population[r1];
                }
                else {
                    parents[i] = population[r2];
                }
            }
            nextGeneration.add(crossover(parents)[0]);
            nextGeneration.add(crossover(parents)[1]);
        }
        for (int i = 0; i < populationSize; i++) {
            population[i] = nextGeneration.get(i);
        }
        computeFitness();
    }

    public Item[] crossover(Item[] parents) {
        char[] genotypeCharsChild1 = parents[0].getGenotype().toCharArray();
        char[] genotypeCharsChild2 = parents[1].getGenotype().toCharArray();
        char[][] children = {genotypeCharsChild1, genotypeCharsChild2};
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < parents[i].getGenotype().length(); j++) {
                if (random.nextInt(100) < crossoverRate*100) {
                    if (i == 0) {
                        children[i + 1][j] = parents[i].getGenotype().charAt(j);
                    } else {
                        children[i - 1][j] = parents[i].getGenotype().charAt(j);
                    }
                }
            }
        }
        return mutation(children);
    }

    public Item[] mutation(char[][] children) {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < children[i].length; j++) {
                if (random.nextInt(100) < mutationRate*100) {
                    if (children[i][j] == '0') {
                        children[i][j] = '1';
                    }
                    else {
                        children[i][j] = '0';
                    }
                }
            }
        }
        return new Item[]{new Item(String.valueOf(children[0])), new Item(String.valueOf(children[1]))};
    }

    public Item getBestItemInPopulation() {
        Item out = null;
        double max = -1;
        for (Item item : population) {
            if (item.getFitness() > max) {
                max = item.getFitness();
                out = item;
                out.setFitness(item.getFitness());
            }
        }
        return out;
    }

    public String getBestItemInList() {
        String out = "";
        for (int i = 0; i < getBestItemInPopulation().getGenotype().length(); i++) {
            if (getBestItemInPopulation().getGenotype().charAt(i) == '1') {
                out += "\t" + itemList[i].getName() + "\n";
            }
        }
        return out;
    }

    public Bag getBag() {
        return bag;
    }
}
