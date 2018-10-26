package de.ews.server.dedicated;

import java.io.File;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.junit.BeforeClass;
import org.junit.Test;

import de.ews.server.exception.DoubleEntryException;
import de.ews.server.exception.NoAvailableBedsException;
import de.ews.server.exception.PatientAlreadyExistsException;
import de.ews.server.patient.Patient;

public class DedicatedServerTest {

    private static DedicatedServer testee;

    @BeforeClass
    public static void setUp() {

        File file = new File("server.sqlite3");
        file.delete();

        try {
            testee = new DedicatedServer();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test(expected = NoAvailableBedsException.class)
    public void testAddPatientTooMany()
            throws NoAvailableBedsException, PatientAlreadyExistsException, DoubleEntryException {
        List<Patient> list = new LinkedList<>();

        for (int i = 0; i < 105; i++) {
            String forename = getForename();
            String name = getName();
            String birthday = getBirthday();

            try {
                list.add(new Patient(forename, name, birthday, String.valueOf(i)));
                System.out.println("Erstellt :" + i);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }

        int i = 0;
        for (Patient patient : list) {
            DedicatedServer.addPatient(patient);
            System.out.println("Eingefügt: " + i++);
        }

    }

    public static String getForename() {
        String[] forenames = { "Allison", "Arthur", "Ana", "Alex", "Arlene", "Alberto", "Barry", "Bertha", "Bill",
                "Bonnie", "Bret", "Beryl", "Chantal", "Cristobal", "Claudette", "Charley", "Cindy", "Chris", "Dean",
                "Dolly", "Danny", "Danielle", "Dennis", "Debby", "Erin", "Edouard", "Erika", "Earl", "Emily", "Ernesto",
                "Felix", "Fay", "Fabian", "Frances", "Franklin", "Florence", "Gabielle", "Gustav", "Grace", "Gaston",
                "Gert", "Gordon", "Humberto", "Hanna", "Henri", "Hermine", "Harvey", "Helene", "Iris", "Isidore",
                "Isabel", "Ivan", "Irene", "Isaac", "Jerry", "Josephine", "Juan", "Jeanne", "Jose", "Joyce", "Karen",
                "Kyle", "Kate", "Karl", "Katrina", "Kirk", "Lorenzo", "Lili", "Larry", "Lisa", "Lee", "Leslie",
                "Michelle", "Marco", "Mindy", "Maria", "Michael", "Noel", "Nana", "Nicholas", "Nicole", "Nate",
                "Nadine", "Olga", "Omar", "Odette", "Otto", "Ophelia", "Oscar", "Pablo", "Paloma", "Peter", "Paula",
                "Philippe", "Patty", "Rebekah", "Rene", "Rose", "Richard", "Rita", "Rafael", "Sebastien", "Sally",
                "Sam", "Shary", "Stan", "Sandy", "Tanya", "Teddy", "Teresa", "Tomas", "Tammy", "Tony", "Van", "Vicky",
                "Victor", "Virginie", "Vince", "Valerie", "Wendy", "Wilfred", "Wanda", "Walter", "Wilma", "William",
                "Kumiko", "Aki", "Miharu", "Chiaki", "Michiyo", "Itoe", "Nanaho", "Reina", "Emi", "Yumi", "Ayumi",
                "Kaori", "Sayuri", "Rie", "Miyuki", "Hitomi", "Naoko", "Miwa", "Etsuko", "Akane", "Kazuko", "Miyako",
                "Youko", "Sachiko", "Mieko", "Toshie", "Junko" };

        return forenames[new Random().nextInt(forenames.length)];
    }

    public static String getName() {
        String[] forenames = { "Müller", "Meyer", "Eilers", "Jansesn", "Schröder", "Ahlers", "Burns", "Behrens",
                "Harms", "Albers", "Kramer", "Schmidt", "Oltmanns", "Make", "Kruse", "Becker", "Cordes", "Janßens",
                "Schwarting", "Schütte", "Frerichs", "Deters", "Meinsers", "Menke", "Lange", "Olberding", "Bartelme",
                "Durban", "Holmes", "Herrel", "Britz", "Christmann", "Grüßinger", "Eppinger", "Großkreuz", "Hilberts",
                "McConner", "Onkh", "Weil", "Guggenheim", "Kahn", "Neuburger", "Foucher", "Einstein", "Moss", "Bloch",
                "Rosenthal", "Mayer", "Maier", "Kohm", "Rothschild", "Schwarz", "Rot", "Levi", "Landauer", "Rosenbauer",
                "Dräger", "Levy", "Steiner", "Hirsch", "Stern", "Kyle", "Reichenbach", "Low", "Peter", "Bernheim",
                "Brunner", "Adler", "Hirschfeld" };

        return forenames[new Random().nextInt(forenames.length)];
    }

    public static String getBirthday() {
        GregorianCalendar gc = new GregorianCalendar();

        int yearInBetween = randBetween(1900, 2010);

        gc.set(Calendar.YEAR, yearInBetween);

        int dayOfYear = randBetween(1, gc.getActualMaximum(Calendar.DAY_OF_YEAR));

        gc.set(Calendar.DAY_OF_YEAR, dayOfYear);

        String year = "" + gc.get(Calendar.YEAR);
        String month = String.format("%02d", gc.get(Calendar.MONTH) + 1);
        String day = String.format("%02d", gc.get(Calendar.DAY_OF_MONTH));

        return year + "-" + month + "-" + day;
    }

    public static int randBetween(int start, int end) {
        return start + (int) Math.round(Math.random() * (end - start));
    }

    @Test
    public void testDismissPatientByID() throws Exception {
        throw new RuntimeException("not yet implemented");
    }

}
