import static org.junit.Assert.assertEquals;

import com.example.Doctor;
import com.example.HRApp;
import com.example.Nurse;
import com.example.Staff;

import org.junit.Test;

public class HRAppTest {

    HRApp hrApp = new HRApp();

    public HRAppTest() {
        Doctor doctor = new Doctor(1, "John", "Male", 31, "1011", 20000.00, "Sheffield",
                "Family physicians", "Registrars");
        hrApp.add(doctor);
    }

    @Test
    public void testAdd() {
        Nurse nurse = new Nurse(2, "Julia", "Female", 31, "1012",
                20000.00, "London", "Rehabilitation Nurses", "nurse unit manager");
        hrApp.add(nurse);

        Staff staff = new Staff(3, "jennie", "Female", 32, "333000333",
                20000.00, "Sheffield", "porters");
        hrApp.add(staff);
        assertEquals(3, hrApp.getAll().size());
    }

    @Test
    public void testUpdate() {
        Doctor doctorNew = new Doctor(1, "John", "Male", 22, "1011", 20000.00, "Sheffield",
                "Family physicians", "Registrars");
        hrApp.update(0, doctorNew);
        assertEquals(22, hrApp.get(0).getAge());
    }

    @Test
    public void testGet() {
        assertEquals(31, hrApp.get(0).getAge());
    }

    @Test
    public void testGetAll() {
        assertEquals(1, hrApp.getAll().size());
    }

    @Test
    public void testRemove() {
        hrApp.remove(0);
        assertEquals(0, hrApp.getAll().size());
    }

}