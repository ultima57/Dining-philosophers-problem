import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class task3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Philosopher[] philosophers = new Philosopher[5];
        Thread[] threads = new Thread[5];
        int moves = 20;
        Fork[] forks = new Fork[5];
        for (int i = 0; i < forks.length; i++) {

            forks[i] = new Fork();
            forks[i].numberF = i;

        }
        for (int i = 0; i < philosophers.length - 1; i++) {
            philosophers[i] = new Philosopher(forks[i], forks[i + 1], i);
        }
        philosophers[4] = new Philosopher(forks[4], forks[0], 4);
        for (int i = 0; i < philosophers.length; i++) {
            threads[i] = new Thread(philosophers[i]);
            threads[i].start();
            System.out.println("begin");
            forks[i].start();
        }
        scanner.nextLine();
        for (int i = 0; i < philosophers.length; i++) {
            philosophers[i].setSteal(false);
            philosophers[i].isHungry = false;

        }

        for (int i = 0; i < moves; i++) {

        }
        for (int i = 0; i < philosophers.length; i++) {
            philosophers[i].setSteal(false);
            forks[i].running = false;
        }
        scanner.close();
    }
}

class Fork extends Thread {
    int numberF;
    boolean isTaken = false;
    FileWriter writerF;
    boolean running = true;

    public Fork() {
        this.isTaken = false;
    }

    public void setTaken(boolean isTaken, Philosopher p) {
        this.isTaken = isTaken;

    }

    public void run() {

        System.out.println(this.numberF);

        System.out.println(" created " + Thread.currentThread().getName());

    }

}

class Philosopher implements Runnable {
    Fork leftFork;
    Fork rightFork;
    int number;
    boolean leftForkTakenByMe = false;
    boolean rightForkTakenByMe = false;
    boolean isHungry = true;
    boolean isEating = false;
    boolean isThinking;
    boolean steal = true;

    public void thinking() {

    }

    public Philosopher() {
    }

    public void setSteal(boolean steal) {
        this.steal = steal;
    }

    public Philosopher(Fork leftFork, Fork rightFork, int number) {

        this.leftFork = leftFork;
        this.rightFork = rightFork;
        this.number = number;

    }

    @Override
    public void run() {
        try (FileWriter writer = new FileWriter("Log" + number + ".txt", false);

        ) {

            String text = "Start!";
            writer.write(text);

            try {
                while (steal) {

                    while (isHungry) {
                        if (leftForkTakenByMe && rightForkTakenByMe) {

                            System.out.println("Philosov № " + this.number + " is eating");
                            writer.write("Philosov № " + this.number + " is eating\n");
                            Thread.currentThread().sleep((int) (Math.random() * 5000) + 5000);
                            System.out.println("Philosov № " + this.number + " put forks on a table");
                            writer.write("Philosov № " + this.number + " put forks on a table\n");

                            leftFork.setTaken(false, this);
                            this.leftForkTakenByMe = false;
                            rightFork.setTaken(false, this);
                            this.rightForkTakenByMe = false;

                            System.out.println("Philosov № " + this.number + " is thinking");
                            writer.write("Philosov № " + this.number + " is thinking\n");
                            Thread.currentThread().sleep((int) (Math.random() * 5000) + 2000);
                            System.out.println("Philosov № " + this.number + " hungry");
                            writer.write("Philosov № " + this.number + " hungry\n");

                        }
                        if (!leftFork.isTaken) {

                            leftFork.setTaken(true, this);
                            this.leftForkTakenByMe = true;
                            System.out.println("Philosov № " + this.number + " took left fork");

                            writer.write("Philosov № " + this.number + " took left fork\n");

                        }
                        if (!rightFork.isTaken) {
                            rightFork.setTaken(true, this);
                            this.rightForkTakenByMe = true;
                            System.out.println("Philosov № " + this.number + " took right fork");
                            writer.write("Philosov № " + this.number + " took right fork\n");
                        }
                        Thread.currentThread().sleep((int) (Math.random() * 1000) + 100);

                    }

                }
                writer.close();

            } catch (Exception ignored) {
                System.out.println(ignored.getMessage());
            }
        } catch (IOException ex) {

            System.out.println(ex.getMessage());
        }
    }
}