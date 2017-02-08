import java.io.IOException;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;

public class TicketReservation {

    public static void main(String[] args) {
        Reservation reserve = new Reservation();
        Person thread1 = new Person(reserve, 5);
        thread1.start();
        Person thread2 = new Person(reserve, 3);
        thread2.start();
        Person thread3 = new Person(reserve, 3);
        thread3.start();
        Person thread4 = new Person(reserve, 3);
        thread4.start();
    }
}

class Reservation {

    static int availTickets;

    public Reservation() {
        availTickets = 0;

      	try {
            File fac = new File("tickets.txt");
            if (!fac.exists()) {
              fac.createNewFile();
            }

          Scanner scanner = new Scanner(fac);

          while (scanner.hasNextInt()) {
            try {
              availTickets = scanner.nextInt();
              //System.out.println("int: " + availTickets);
            } catch (java.util.InputMismatchException e) {
              scanner.close();
              e.printStackTrace();
            }
          }
        } catch (IOException e) {
          e.printStackTrace();
        }
    }

    synchronized void reserveTicket(int requestedTickets) {
        System.out.println("Welcome to the Movies: " + Thread.currentThread().getName());

        System.out.println("We have " + availTickets + " ticket(s) available");
        System.out.println("You are requesting "  + requestedTickets + " ticket(s)");

        if (availTickets >= requestedTickets && requestedTickets > 0) {
            System.out.println("Ticket(s) Available. Purchasing!");
            try {
              availTickets -= requestedTickets;
              File fac = new File("tickets.txt");
              FileWriter wr = new FileWriter(fac);
              wr.write(availTickets + "");
              wr.flush();
              wr.close();

            } catch(IOException e) {
            	e.printStackTrace();
            }
          	try {
                Thread.sleep(100);
            }
            catch (InterruptedException e) {
              if (Thread.interrupted()) {
                Thread.currentThread().interrupt();
                System.out.println("Thread interrupted" + e.getMessage());
                throw new RuntimeException(e);
              }
            }
            System.out.println(requestedTickets + " ticket(s) reserved.");
        }
        else {
            System.out.println("Sorry, requested number of tickets are not available");
        }
        System.out.println(Thread.currentThread().getName() + " leaving.");
        System.out.println("----------------------------------------------");
    }

    public int getTickets() {
      return availTickets;
    }
}

class Person extends Thread {

    Reservation reserve;
    int requestedTickets;

    public Person(Reservation reserve, int requestedTickets) {
        this.reserve = reserve;
        this.requestedTickets = requestedTickets;
    }

    @Override
    public void run() {
        reserve.reserveTicket(requestedTickets);
    }
}
