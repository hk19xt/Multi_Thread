package Assignment1;
import java.util.concurrent.locks.ReentrantLock;
import java.util.*;

/* Assignment1
 * Student Name: Hyejin Kim
 * Student #6823116
 * Date: 2/26/2021
 *
 * This program is ordering foods (4 menus in total) along with the inputs from a user.
   Each food is cooked by each thread based on their recipes. The recipes include several resources, such as
   eggs, oven, stove, flour, and so on. If a certain food already use a resource, then other food cannot use the same
   kind of resource for a while. For this reason, the following food should be waited until the already ordered food
   is completed.

 * In order to implement this program,
   first, a user inputs the number of menus among four foods.
   Second, the user inputs the amount of the food that he or she wants to order.
   Third, the ordered foods are concurrently cooked depending on their recipes and resources. */

    public class FoodOrder {

        private ReentrantLock lock; // This reentrant lock will be used in order for a thread to lock accessing the shared resources.
        // These below resource will be used in each food class later.
        private ReentrantLock flour;
        private ReentrantLock tomatoHamper;
        private ReentrantLock eggs;
        private ReentrantLock saltMallet;
        private ReentrantLock prepCounter;
        private ReentrantLock oven;
        private ReentrantLock stove;
        private ReentrantLock sink;
        private ReentrantLock output;
        public int value; // This value will be used as scanner input from the user. (the amount of pasta)
        public int value2; // This value means the amount of pizza.
        public int value3; // This value will be used as the amount of frozen pizza.
        public int value4; // This value will be used as the amount of chicken.
        public int total = 0; // This total means the sum amount of orders which the user ordered in total.
        public Thread pastaThread; // This thread is implemented for pasta runnable private class.
        public Thread pizzaThread; // This thread is implemented for pizza runnable private class.
        public Thread frozenPizzaThread; // This thread is implemented for frozen pizza runnable private class.
        public Thread chickenThread; // This thread is implemented for chicken runnable private class.
        public int finishedPasta = 0; // This value will calculate the amount of pasta whenever each thread run.
        public int finishedPizza = 0; // This value will calculate the amount of pizza whenever each thread run.
        public int finishedFrozenPizza = 0; // This value will calculate the amount of frozen pizza whenever each thread run.
        public int finishedChicken = 0; // This value will calculated the amount of chicken whenever each thread run.

        public FoodOrder() {

            lock = new ReentrantLock(); // Initialize lock in order for using in each four food runnable class.
            // Initialize all resources for using in each thread and not accessing the shared resources from other foods.
            flour = new ReentrantLock();
            tomatoHamper = new ReentrantLock();
            eggs = new ReentrantLock();
            saltMallet = new ReentrantLock();
            prepCounter = new ReentrantLock();
            oven = new ReentrantLock();
            stove = new ReentrantLock();
            sink = new ReentrantLock();
            output= new ReentrantLock();

            System.out.println("Menu: 1) pasta, 2) pizza, 3) frozen pizza, 4) chicken");
            System.out.println("Enter the number of menus. \n(For example, enter 24 if you only want 2) pizza and 4) chicken. Enter 1234 if you want all menus.)");
            Scanner in = new Scanner(System.in);
            int number = in.nextInt(); // Input value of the menu number from the user.
            // Go to each case based on the input number from the user.
            switch(number){
                case 1: // If the user only wants pasta, then run the pastaThread.
                    System.out.println("Okay. You chose pasta.");
                    Amount1(); // Enter the amount of pasta order from the user.
                    pastaThread = new Thread(new pasta(value)); // Make a new thread for cooking pasta with the amount of order.
                    pastaThread.start(); // Run the pastaThread, but not run(), we use start() in this case.
                    try{
                        // run the pastaThread.
                        pastaThread.join();
                    }catch(InterruptedException ie){}
                    break; // After finishing running the thread, then break the switch loop.
                case 2: // If the user only wants pizza, then run the pizzaThread.
                    System.out.println("Okay. You chose pizza.");
                    Amount2(); // Enter the amount of pizza order from the user.
                    pizzaThread = new Thread(new pizza(value2));  // Make a new thread for cooking pizza with the amount of order.
                    pizzaThread.start(); // Run the pizzaThread, but not run(), we use start() in this case.
                    try{
                        // run the pizzaThread.
                        pizzaThread.join();
                    }catch(InterruptedException ie){}
                    break; // After finishing running the thread, then break the switch loop.
                case 3: // If the user only wants frozen pizza, then run the pizzaThread.
                    System.out.println("Okay. You chose frozen pizza.");
                    Amount3(); // Enter the amount of frozen pizza order from the user.
                    frozenPizzaThread = new Thread(new frozenPizza(value3)); // Make a new thread for cooking frozen pizza with the amount of order.
                    frozenPizzaThread.start(); // Run the frozenPizzaThread, but not run(), we use start() in this case.
                    try{
                        // run the frozenPizzaThread.
                        frozenPizzaThread.join();
                    }catch(InterruptedException ie){}
                    break;  // After finishing running the thread, then break the switch loop.
                case 4: // If the user only wants chicken, then run the chickenThread.
                    System.out.println("Okay. You chose chicken.");
                    Amount4(); // Enter the amount of chicken order from the user.
                    chickenThread = new Thread(new chicken(value4)); // Make a new thread for chicken with the amount of order.
                    chickenThread.start(); // Run the chickenThread, but not run(), we use start() in this case.
                    try{
                        // run the chickenThread.
                        chickenThread.join();
                    }catch(InterruptedException ie){}
                    break;  // After finishing running the thread, then break the switch loop.
                case 12: // If the user wants pasta and pizza, then run the pastaThread and pizzaThread.
                    System.out.println("Okay. You chose pasta and pizza.");
                    Amount1(); // Enter the amount of pasta order from the user.
                    Amount2(); // Enter the amount of pizza order from the user.
                    pastaThread = new Thread(new pasta(value)); // Make a new thread for cooking pasta with the amount of order.
                    pizzaThread = new Thread(new pizza(value2)); // Run the pizzaThread, but not run(), we use start() in this case.
                    pastaThread.start(); // Run the pastaThread, but not run(), we use start() in this case.
                    pizzaThread.start(); // Run the mPizzaThread, but not run(), we use start() in this case.
                    try{
                        // run the pastaThread and pizzaThread at the same time.
                        pastaThread.join();
                        pizzaThread.join();
                    }catch(InterruptedException ie){}
                    break;  // After finishing running the thread, then break the switch loop.
                case 13: // If the user wants pasta and frozen pizza, then run the pastaThread and frozenPizzaThread.
                    System.out.println("Okay. You chose pasta and frozen pizza.");
                    Amount1(); // Enter the amount of pasta order from the user.
                    Amount3(); // If the user only wants frozen pizza, then run the pizzaThread.
                    pastaThread = new Thread(new pasta(value)); // Make a new thread for cooking pasta with the amount of order.
                    frozenPizzaThread = new Thread(new frozenPizza(value3)); // Make a new thread for cooking frozen pizza with the amount of order.
                    pastaThread.start(); // Run the pastaThread, but not run(), we use start() in this case.
                    frozenPizzaThread.start(); // Run the frozenPizzaThread, but not run(), we use start() in this case.
                    try{
                        // run the pastaThread and frozenPizzaThread at the same time.
                        pastaThread.join();
                        frozenPizzaThread.join();
                    }catch(InterruptedException ie){}
                    break;  // After finishing running the thread, then break the switch loop.
                case 14: // If the user wants pasta and chicken, then run the pastaThread and chickenThread.
                    System.out.println("Okay. You chose pasta and chicken.");
                    Amount1(); // Enter the amount of pasta order from the user.
                    Amount4(); // Enter the amount of chicken order from the user.
                    pastaThread = new Thread(new pasta(value)); // Make a new thread for cooking pasta with the amount of order.
                    chickenThread = new Thread(new chicken(value4));
                    pastaThread.start(); // Run the pastaThread, but not run(), we use start() in this case.
                    chickenThread.start(); // Run the chickenThread, but not run(), we use start() in this case.
                    try{
                        // run the pastaThread and chickenThread at the same time.
                        pastaThread.join();
                        chickenThread.join();
                    }catch(InterruptedException ie){}
                    break;  // After finishing running the thread, then break the switch loop.
                case 23: // If the user wants pizza and frozen pizza, then run the pizzaThread and frozenPizzaThread.
                    System.out.println("Okay. You chose pizza and frozen pizza.");
                    Amount2(); // Enter the amount of pizza order from the user.
                    Amount3(); // If the user only wants frozen pizza, then run the pizzaThread.
                    pizzaThread = new Thread(new pizza(value2));
                    frozenPizzaThread = new Thread(new frozenPizza(value3)); // Make a new thread for cooking frozen pizza with the amount of order.
                    pizzaThread.start(); // Run the pizzaThread, but not run(), we use start() in this case.
                    frozenPizzaThread.start(); // Run the frozenPizzaThread, but not run(), we use start() in this case.
                    try{
                        // run the pizzaThread and frozenPizzaThread at the same time.
                        pizzaThread.join();
                        frozenPizzaThread.join();
                    }catch(InterruptedException ie){}
                    break;  // After finishing running the thread, then break the switch loop.
                case 24: // If the user wants frozen pizza and chicken, then run the frozenPizzaThread and chickenThread.
                    System.out.println("Okay. You chose pizza and chicken.");
                    Amount2(); // Enter the amount of pizza order from the user.
                    Amount4(); // Enter the amount of chicken order from the user.
                    pizzaThread = new Thread(new frozenPizza(value2)); // Make a new thread for cooking pizza with the amount of order.
                    chickenThread = new Thread(new chicken(value4));
                    pizzaThread.start(); // Run the pizzaThread, but not run(), we use start() in this case.
                    chickenThread.start(); // Run the chickenThread, but not run(), we use start() in this case.
                    try{
                        // run the pizzaThread and chickenThread at the same time.
                        pizzaThread.join();
                        chickenThread.join();
                    }catch(InterruptedException ie){}
                    break;  // After finishing running the thread, then break the switch loop.
                case 34: // If the user wants frozen pizza and chicken, then run the frozenPizzaThread and chickenThread.
                    System.out.println("Okay. You chose frozen pizza and chicken.");
                    Amount3(); // If the user only wants frozen pizza, then run the pizzaThread.
                    Amount4(); // Enter the amount of chicken order from the user.
                    frozenPizzaThread = new Thread(new frozenPizza(value3)); // Make a new thread for cooking frozen pizza with the amount of order.
                    chickenThread = new Thread(new chicken(value4));
                    frozenPizzaThread.start(); // Run the frozenPizzaThread, but not run(), we use start() in this case.
                    chickenThread.start(); // Run the chickenThread, but not run(), we use start() in this case.
                    try{
                        // run the frozenPizzaThread and chickenThread at the same time.
                        frozenPizzaThread.join();
                        chickenThread.join();
                    }catch(InterruptedException ie){}
                    break;  // After finishing running the thread, then break the switch loop.
                case 123: // If the user wants pasta, pizza and frozen pizza, then run the pastaThread, pizzaThread and frozenPizzaThread.
                    System.out.println("Okay. You chose pasta, pizza and frozen pizza.");
                    Amount1(); // Enter the amount of pasta order from the user.
                    Amount2(); // Enter the amount of pizza order from the user.
                    Amount3(); // If the user only wants frozen pizza, then run the pizzaThread.
                    pastaThread = new Thread(new pasta(value)); // Make a new thread for cooking pasta with the amount of order.
                    pizzaThread = new Thread(new pizza(value2)); // Make a new thread for cooking pizza with the amount of order.
                    frozenPizzaThread = new Thread(new frozenPizza(value3));
                    pastaThread.start(); // Run the pastaThread, but not run(), we use start() in this case.
                    pizzaThread.start(); // Run the pizzaThread, but not run(), we use start() in this case.
                    frozenPizzaThread.start(); // Run the frozenPizzaThread, but not run(), we use start() in this case.
                    try{
                        // run the pastaThread, pizzaThread, and frozenPizzaThread at the same time.
                        pastaThread.join();
                        pizzaThread.join();
                        frozenPizzaThread.join();
                    }catch(InterruptedException ie){}
                    break;  // After finishing running the thread, then break the switch loop.
                case 124: // If the user wants pasta, pizza and chicken, then run the pastaThread, pizzaThread and chickenThread.
                    System.out.println("Okay. You chose pasta, pizza and chicken.");
                    Amount1(); // Enter the amount of pasta order from the user.
                    Amount2(); // Enter the amount of pizza order from the user.
                    Amount4(); // Enter the amount of chicken order from the user.
                    pastaThread = new Thread(new pasta(value)); // Make a new thread for cooking pasta with the amount of order.
                    pizzaThread = new Thread(new pizza(value2)); // Make a new thread for cooking pizza with the amount of order.
                    chickenThread = new Thread(new chicken(value4));
                    pastaThread.start(); // Run the pastaThread, but not run(), we use start() in this case.
                    pizzaThread.start(); // Run the pizzaThread, but not run(), we use start() in this case.
                    chickenThread.start(); // Run the chickenThread, but not run(), we use start() in this case.
                    try{
                        // run the pastaThread, pizzaThread, and chickenThread at the same time.
                        pastaThread.join();
                        pizzaThread.join();
                        chickenThread.join();
                    }catch(InterruptedException ie){}
                    break;  // After finishing running the thread, then break the switch loop.
                case 234: // If the user wants pizza, frozen pizza and chicken, then run the pizzaThread, frozenPizzaThread and chickenThread.
                    System.out.println("Okay. You chose pizza, frozen pizza and chicken.");
                    Amount2(); // Enter the amount of pizza order from the user.
                    Amount3(); // If the user only wants frozen pizza, then run the pizzaThread.
                    Amount4(); // Enter the amount of chicken order from the user.
                    pizzaThread = new Thread(new pizza(value2)); // Make a new thread for cooking pizza with the amount of order.
                    frozenPizzaThread = new Thread(new frozenPizza(value3)); // Make a new thread for cooking frozen pizza with the amount of order.
                    chickenThread = new Thread(new chicken(value4)); // Make a new thread for chicken with the amount of order.
                    pizzaThread.start();// Run the PizzaThread, but not run(), we use start() in this case.
                    frozenPizzaThread.start(); // Run the frozenPizzaThread, but not run(), we use start() in this case.
                    chickenThread.start(); // Run the chickenThread, but not run(), we use start() in this case.
                    try{
                        // run the pizzaThread, frozenPizzaThread, and chickenThread at the same time.
                        pizzaThread.join();
                        frozenPizzaThread.join();
                        chickenThread.join();
                    }catch(InterruptedException ie){}
                    break;  // After finishing running the thread, then break the switch loop.
                case 1234: // If the user wants all four menus, then run each Threads of pasta, pizza, frozenPizza and chicken.
                    System.out.println("Okay. You chose pasta, pizza, frozen pizza and chicken.");
                    Amount1(); // Enter the amount of pasta order from the user.
                    Amount2(); // Enter the amount of pizza order from the user.
                    Amount3(); // If the user only wants frozen pizza, then run the pizzaThread.
                    Amount4(); // Enter the amount of chicken order from the user.
                    pastaThread = new Thread(new pasta(value)); // Make a new thread for cooking pasta with the amount of order.
                    pizzaThread = new Thread(new pizza(value2)); // Make a new thread for cooking pizza with the amount of order.
                    frozenPizzaThread = new Thread(new frozenPizza(value3)); // Make a new thread for cooking frozen pizza with the amount of order.
                    chickenThread = new Thread(new chicken(value4)); // Make a new thread for chicken with the amount of order.
                    pastaThread.start(); // Run the pastaThread, but not run(), we use start() in this case.
                    pizzaThread.start(); // Run the pizzaThread, but not run(), we use start() in this case.
                    frozenPizzaThread.start(); // Run the frozenPizzaThread, but not run(), we use start() in this case.
                    chickenThread.start(); // Run the chickenThread, but not run(), we use start() in this case.

                    try{
                        // run the pastaThread, pizzaThread, frozenPizzaThread, and chickenThread at the same time.
                        pastaThread.join();
                        pizzaThread.join();
                        frozenPizzaThread.join();
                        chickenThread.join();
                    }catch(InterruptedException ie){}

                    break;  // After finishing running the thread, then break the switch loop.
            }
            total = finishedChicken + finishedPasta + finishedFrozenPizza + finishedPizza; // The sum of the total amount of finished orders.
            System.out.println("In total, " + total + " were cooked.");

        }//FoodOrder

        /*This class is pasta thread implemening the runnable interface */
        private class pasta implements Runnable {
            private int amount; // The amount of orders that the input from the user.

            public pasta(int amount) { // Constructor for using the amount in the for loop in the run() function below.
                this.amount = amount;
            }
            public void run() { // This run function is called by start() in the above food order class.
                for (int i = 0; i < amount; i++) { // loop until the amount of order is cooked completely.
                    try {
                        lock.lock(); // This lock is used in order for locking the access of the shared resources.
                        // The below resources are locked when they are used so that other food cannot access the same resource.
                        flour.lock(); // First, pasta use flour to make a small hill
                        prepCounter.lock(); // The flour should be on the prep counter.
                        flour.unlock(); // After using the resource, it should be released in order for other foods to use this resource.
                        eggs.lock(); // The eggs are mixed with the flour.
                        prepCounter.unlock(); // After step 4, the prep counter is not used anymore.
                        eggs.unlock(); // The eggs are not used anymore.
                        sink.lock(); // The water is used to fill the pot.
                        sink.unlock(); // After using the water, then sink should be released.
                        saltMallet.lock(); // The salt mallet is used to add into the water.
                        saltMallet.unlock(); // After adding it, the salt mallet is not necessary.
                        stove.lock(); // The stove is used to boil the water.
                        stove.unlock(); // The stove should be released after using it.
                        tomatoHamper.lock(); // The tomatoes are used to cover the pasta.
                        tomatoHamper.unlock(); // After using the tomato hamper, then it should be released.
                        output.lock(); // The output is ready for cooked pasta.
                        output.unlock(); // The output is also released for other foods cooked.
                        finishedPasta++; // Increase the amount of value whenever it finishes cooking pasta.
                    } finally {
                        if(finishedPasta % 1000000 == 0 && finishedPasta!=0){ // Print out whenever the amount of pasta cooked is million.
                            System.out.println("The amount of " +
                                    finishedPasta + " pastas were cooked so far.");}
                        if(finishedPasta == value){ // Print out after finishing the total order.
                            System.out.println("The total amount of " + finishedPasta + " pastas were completely cooked.");
                        }
                        lock.unlock(); // After finishing all the amount of orders, then release the thread for other foods to use resources.
                    }
                }
            }
        }//pasta

        /*This class is pizza thread implemening the runnable interface */
        private class pizza implements Runnable {
            private int amount; // The amount of orders that the input from the user.

            public pizza(int amount) { // Constructor for using the amount in the for loop in the run() function below.
                this.amount = amount;
            }
            public void run() { // This run function is called by start() in the above food order class.
                for (int i = 0; i < amount; i++) { // loop until the amount of order is completely cooked.
                    try {
                        lock.lock(); // This lock is used in order for locking the access of the shared resources.
                        // The below resources are locked when they are used so that other food cannot access the same resource.
                        flour.lock(); // First, the flour is used for making dough.
                        saltMallet.lock(); // And, salt is added into the flour.
                        saltMallet.unlock(); // The salt is released for making other foods.
                        sink.lock(); // The water is needed for making dough.
                        sink.unlock(); // The water is released for making other foods.
                        flour.unlock(); // After using the flour, then release the flour for making other foods.
                        prepCounter.lock(); // The dough should be spread on the prep counter.
                        tomatoHamper.lock(); // The tomatoes are used to spread on top of the dough.
                        tomatoHamper.unlock(); // The tomatoes are released for making other foods.
                        prepCounter.unlock(); // The prep counter is not needed anymore.
                        oven.lock(); // The dough is into the heated oven.
                        oven.unlock(); // After the crust becomes golden, then the oven is released.
                        stove.lock(); // The stove is used for crisping the bottom of the dough.
                        stove.unlock(); // After using the stove, it should be released for making other foods.
                        output.lock(); // The output is ready for cooked pizza.
                        output.unlock(); // The output is also released for other foods cooked.
                        finishedPizza++; // Increase the amount of value whenever it finishes cooking pizza.
                    } finally {
                        if(finishedPizza % 1000000==0 && finishedPizza!=0){ // Print out whenever the amount of pizza cooked is million.
                            System.out.println("The amount of " +
                                    finishedPizza + " pizzas were cooked so far.");}
                        if(finishedPizza == value2){ // Print out after finishing the total order.
                            System.out.println("The total amount of " + finishedPizza + " pizzas were completely cooked.");
                        }
                        lock.unlock(); // After finishing all the amount of orders, then release the thread for other foods to use resources.
                    }

                }
            }
        }//pizza

        /*This class is frozenPizza thread implemening the runnable interface */
        private class frozenPizza implements Runnable {
            private int amount; // The amount of orders that the input from the user.

            public frozenPizza(int amount) {  // Constructor for using the amount in the for loop in the run() function below.
                this.amount = amount;
            }
            public void run() { // This run function is called by start() in the above food order class.
                for (int i = 0; i < amount; i++) {// loop until the amount of order is completely cooked.
                    try {
                        lock.lock();// This lock is used in order for locking the access of the shared resources.
                        // The below resources are locked when they are used so that other food cannot access the same resource.
                        oven.lock(); // First, frozen pizza is into the oven.
                        oven.unlock(); // After finishing thawing the pizza, then release the oven for making other foods.
                        output.lock(); // The output is ready for cooked frozenPizza.
                        output.unlock(); // The output is also released for other foods cooked.
                        finishedFrozenPizza++; // Increase the amount of value whenever it finishes cooking frozenPizza.
                    } finally {
                        if(finishedFrozenPizza % 1000000==0 && finishedFrozenPizza!=0){ // Print out whenever the amount of frozen pizza cooked is million.
                            System.out.println("The amount of " +
                                    finishedFrozenPizza + " frozen pizzas were cooked so far.");}
                        if(finishedFrozenPizza == value3){ // Print out after finishing the total order.
                            System.out.println("The total amount of " + finishedFrozenPizza + " frozen pizzas were completely cooked.");
                        }
                        lock.unlock(); // After finishing all the amount of orders, then release the thread for other foods to use resources.
                    }

                }
            }
        }//frozenPizza

        /*This class is chicken thread implemening the runnable interface */
        private class chicken implements Runnable {
            private int amount; // The amount of orders that the input from the user.

            public chicken(int amount) { // Constructor for using the amount in the for loop in the run() function below.
                this.amount = amount;
            }
            public void run() { // This run function is called by start() in the above food order class.
                for (int i = 0; i < amount; i++) { // loop until the amount of order is completely cooked.
                    try {
                        lock.lock(); // This lock is used in order for locking the access of the shared resources.
                        saltMallet.lock(); // First, the salt mallet is used for driving out the moisture and season the chicken.
                        saltMallet.unlock(); // After using the salt mallet, it should be released for making other foods.
                        eggs.lock(); // The eggs are used for setting them aside chicken.
                        flour.lock(); // The flour is used for the same reason above.
                        eggs.unlock(); // After using eggs, they should be released for making other foods.
                        flour.unlock(); // After using flour, it should be released for making other foods.
                        sink.lock(); // The water is used for washing my hands.
                        sink.unlock(); // After using the water, the sink should be released for making other foods.
                        stove.lock(); // The stove is used for heating the chicken.
                        stove.unlock(); // After heating the chicken, then the stove should be released for making other foods.
                        output.lock();  // The output is ready for cooked chicken.
                        output.unlock(); // The output is also released for other foods cooked.
                        finishedChicken++; // Increase the amount of value whenever it finishes cooking chicken.
                    } finally {
                        if(finishedChicken % 1000000==0 && finishedChicken!=0){ // Print out whenever the amount of frozen pizza cooked is million.
                            System.out.println("The amount of " +
                                    finishedChicken + " chickens were cooked so far.");}
                        if(finishedChicken == value4){ // Print out after finishing the total order.
                            System.out.println("The total amount of " + finishedChicken + " chickens were completely cooked.");
                        }
                        lock.unlock(); // After finishing all the amount of orders, then release the thread for other foods to use resources.
                    }

                }
            }
        }//chicken

        /*This function is asking the amount of order of pasta to the user. */
        public void Amount1(){
            Scanner scanner = new Scanner(System.in);
            System.out.print(" How many pasta do you want?");
            value = scanner.nextInt();
        }//Amount1

        /*This function is asking the amount of order of pizza to the user. */
        public void Amount2(){
            Scanner scanner = new Scanner(System.in);
            System.out.print(" How many pizza do you want?");
            value2 = scanner.nextInt();
        }//Amount2

        /*This function is asking the amount of order of frozen pizza to the user. */
        public void Amount3(){
            Scanner scanner = new Scanner(System.in);
            System.out.print(" How many frozen pizza do you want?");
            value3 = scanner.nextInt();
        }//Amount3

        /*This function is asking the amount of order of chicken to the user. */
        public void Amount4(){
            Scanner scanner = new Scanner(System.in);
            System.out.print(" How many chicken do you want?");
            value4 = scanner.nextInt();
            scanner.close();
        }//Amount4

        public static void main(String args[]) {
            new FoodOrder();
        }//main
    }//FoodOrder

