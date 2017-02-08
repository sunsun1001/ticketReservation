public class Person extends Thread {

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
