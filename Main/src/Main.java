public class Main {

    public static void main(String[]args) {

        System.out.println("----- Start -----");

        GeneticAlgo ga = new GeneticAlgo(150, 0.01, "Hello World. It is me! How are you? I would like to stop this dumb game...");
        ga.start();

        System.out.println();
        System.out.println("----- End -----");
    }
}
