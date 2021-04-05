public class Main {

    public static void main(String[]args) {
        startGA();
        startKnapsack();
    }

    public static void startGA() {
        System.out.println("----- Start GA -----");

        String goal = "Hello World. It is me! How are you? I would like to stop this dumb game...";

        GeneticAlgo ga = new GeneticAlgo(150, 0.01, goal);
        ga.start();

        System.out.println();
        System.out.println("----- End GA -----");
    }

    public static void startKnapsack() {
        Item computer = new Item("Computer", 9, 7);
        Item phone = new Item("Phone", 7, 3);
        Item bottle = new Item("Bottle", 4, 4);
        Item jacket = new Item("Jacket", 5, 6);
        Item keys = new Item("Keys", 8, 2);
        Item headphone = new Item("Headphone", 3, 4);
        Item shoes = new Item("Shoes", 7, 6);

        Knapsack knapsack = new Knapsack(200, 200, 0.7, 0.01, 20, new Item[]{computer,phone,bottle,jacket,keys,headphone,shoes});
        knapsack.start();
    }
}
