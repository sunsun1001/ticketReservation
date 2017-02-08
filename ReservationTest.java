import org.junit.runners.MethodSorters;
import org.junit.FixMethodOrder;
import org.junit.Test;
import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ReservationTest {

  @Test
  public void testRun1() {
    Reservation reserve = new Reservation();
    int availTickets = reserve.getTickets();
    assertEquals(10, availTickets);
  }

  @Test
  public void testRun2() {
    Reservation reserve = new Reservation();
    Person thread1 = new Person(reserve, 9);
    Person thread2 = new Person(reserve, 2); // this should not be allocated
    thread1.start();
    thread2.start();
    int availTickets = reserve.getTickets();
    assertEquals(1, availTickets); // only 9 bought, so 1 left
  }
}
