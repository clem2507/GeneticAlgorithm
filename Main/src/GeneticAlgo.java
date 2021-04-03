import java.util.ArrayList;
import java.util.Random;

public class GeneticAlgo {

    private char[] characters = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',' ',',','.','!','?'};
    private int genotypeSize;
    private int populationSize;
    private int finalRangeNumber;
    private int totalGenerationNumber;
    private double mutationRate;
    private double populationFitnessAverage;
    private String goalPattern;
    private Item[] population;
    private ArrayList<Item> matingPool;
    private Random random;

    public GeneticAlgo(int populationSize, double mutationRate, String goalPattern) {
        this.genotypeSize = goalPattern.length();
        this.populationSize = populationSize;
        this.mutationRate = mutationRate;
        this.goalPattern = goalPattern;
        this.population = new Item[populationSize];
        this.random = new Random();
    }

    public void start() {
        initializePopulation();
        while (!isGoalReached()) {
            totalGenerationNumber++;
            matingPool = selection();
            reproduction(matingPool);
            System.out.println("--> best population genotype = " + getBestItemGenotype());
            System.out.println("--> average population fitness = " + (int) (populationFitnessAverage*100) + "%");
            System.out.println("--> generation = " + totalGenerationNumber);
            System.out.println();
            System.out.println("----------------------------");
        }
        System.out.println();
        System.out.println("--> number of generations = " + totalGenerationNumber);
    }

    public void initializePopulation() {
        for (int i = 0; i < populationSize; i++) {
            Item item = new Item(createRandomGenotype());
            population[i] = item;
        }
    }

    public String createRandomGenotype() {
        String genotype = String.format("%1$"+genotypeSize+"s", "");
        char[] genotypeChars = genotype.toCharArray();
        for (int i = 0; i < genotype.length(); i++) {
            genotypeChars[i] = characters[(random.nextInt(characters.length))];
        }
        genotype = String.valueOf(genotypeChars);
        return genotype;
    }

    public void computeFitness() {
        populationFitnessAverage = 0;
        for (Item item : population) {
            double count = 0;
            for (int i = 0; i < item.getGenotype().length(); i++) {
                if (item.getGenotype().charAt(i) == goalPattern.charAt(i)) {
                    count++;
                }
            }
            item.setFitness(count/item.getGenotype().length());
            populationFitnessAverage+=(count/item.getGenotype().length());
        }
        populationFitnessAverage/=populationSize;
    }

    public ArrayList<Item> selection() {
        computeFitness();
        this.matingPool = new ArrayList<>();
        int previousRangeEnd = 0;
        double penalty = 0.0001;
        System.out.println();
        for (Item item : population) {
            if (item.getFitness() >= populationFitnessAverage-penalty) {
                int currentRangeEnd = (int) Math.round(item.getFitness()*100);
                matingPool.add(item);
                System.out.println(item.getGenotype());
                item.setRangeStart(previousRangeEnd);
                item.setRangeEnd(currentRangeEnd+previousRangeEnd);
                previousRangeEnd+=currentRangeEnd+1;
            }
        }
        System.out.println();
        this.finalRangeNumber = previousRangeEnd;
        return matingPool;
    }

    public void reproduction(ArrayList<Item> matingPool) {
        population = new Item[populationSize];
        for (int i = 0; i < populationSize; i++) {
            Item parent1 = null;
            int r1 = random.nextInt(finalRangeNumber);
            for (Item item : matingPool) {
                if (item.getRangeStart() <= r1 && item.getRangeEnd() >= r1) {
                    parent1 = item;
                }
            }
            Item parent2 = null;
            int r2 = random.nextInt(finalRangeNumber);
            for (Item item : matingPool) {
                if (item.getRangeStart() <= r2 && item.getRangeEnd() >= r2) {
                    parent2 = item;
                }
            }
            Item child = crossover(parent1, parent2);
            population[i] = child;
        }
        computeFitness();
    }

    public Item crossover(Item parent1, Item parent2) {
        String childGenotype = String.format("%1$"+genotypeSize+"s", "");
        char[] genotypeCharsChild = childGenotype.toCharArray();
        for (int i = 0; i < genotypeSize; i++) {
            if (random.nextInt(2)==0) {
                genotypeCharsChild[i] = parent1.getGenotype().charAt(i);
            }
            else {
                genotypeCharsChild[i] = parent2.getGenotype().charAt(i);
            }
        }
        return mutation(genotypeCharsChild);
    }

    public Item mutation(char[] genotypeCharsChild) {
        for (int i = 0; i < genotypeSize; i++) {
            if (random.nextInt(100) < mutationRate*100) {
                genotypeCharsChild[i] = characters[(random.nextInt(characters.length))];
            }
        }
        return new Item(String.valueOf(genotypeCharsChild));
    }

    public boolean isGoalReached() {
        for (Item item : population) {
            if (item.getGenotype().equals(goalPattern)) {
                return true;
            }
        }
        return false;
    }

    public String getBestItemGenotype() {
        String out = "";
        double max = -1;
        for (Item item : population) {
            if (item.getFitness() > max) {
                max = item.getFitness();
                out = item.getGenotype();
            }
        }
        return out;
    }
}
