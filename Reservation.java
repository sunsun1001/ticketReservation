import java.io.EOFException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Reservation {

    static int availTickets = 5;

    synchronized void reserveTicket(int requestedTickets) {
        System.out.println("Welcome to the Movies: " + Thread.currentThread().getName());
        FileInputStream fis = null;
        int availTickets = 0;

        // Abey lodhu, iss sab ko kisi helper class mein daal
      	try {
        	fis = new FileInputStream("\\Desktop\\Yahoo\\tickets.txt");

          DataInputStream dis = new DataInputStream(fis);

          boolean eof = false;

          while (!eof) {
            try {
              availTickets = dis.readInt();
            } catch (EOFException e) {
              eof = true;
              dis.close();
            }
          }
        } catch (IOException e) {
        	// Do something here
          e.printStackTrace();
        } finally {
          if (fis != null) {
            fis.close();
          }
        }


        System.out.println("We have " + availTickets + " ticket(s) available");
        System.out.println("You are requesting "  + requestedTickets + " ticket(s)");
      	// Make a helper function which gets available tickets from the file

        // Let's say file wasn't there you will add an exception catch there so you create a file if not there lol (agar file hi bana dega toh sense hi kya raha)
        // While reading, you will catch EOF exception and when caught you will close the file


        if (availTickets >= requestedTickets && requestedTickets > 0) {
            System.out.println("Ticket(s) Available. Purchasing!");
            DataOutputStream dos = new DataOutputStream(new FileOutputStream("\\Desktop\\Yahoo\\tickets.txt"));

            dos.writeInt(availTickets - requestedTickets);
          	dos.flush();
            dos.close();

            availTickets -= requestedTickets;
            // Ideally stuff should be done here.
          	try {

                // Here you will decrease the number of available tickets in file
                // You will catch an exception here if happens while writing to file
                Thread.sleep(100);
            }
            catch (InterruptedException e) {
              if (Thread.interrupted()) {
                Thread.currentThread().interrupt();
                System.out.println("Thread interrupted" + e.getMessage());
                throw new RuntimeException(e); // Lol, exception catch karke exception throw kar di #swag
              }
            }
            System.out.println(requestedTickets + " ticket(s) reserved.");
            availTickets = availTickets - requestedTickets;
        }
        else {
            System.out.println("Sorry, requested number of tickets are not available");
        }
        System.out.println(Thread.currentThread().getName() + " leaving.");
        System.out.println("----------------------------------------------");
    }
}
