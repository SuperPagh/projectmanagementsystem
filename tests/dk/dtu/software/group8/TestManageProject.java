package dk.dtu.software.group8;

import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestManageProject extends TestCreateProject {

    //TODO: This class should inherit from TestCreateProject.
    PManagementSystem pms;
    DatabaseManager db;
    Project project;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Before
    public void setup() {
        pms = new PManagementSystem();
        db = new DatabaseManager();

        //Set current date to the may 9th 2016.
        DateServer mockDateServer = mock(DateServer.class);
        pms.setDateServer(mockDateServer);

        Calendar cal = new GregorianCalendar(2016, Calendar.MAY,9);
        when(pms.getDate()).thenReturn(cal);

        //Login a user
        pms.signIn(db.getEmployees()[0]);
        assertTrue(pms.userLoggedIn());

        //Check the project base is empty.
        //TODO: could we do this smarter?
        assertEquals(pms.getProjects().size(), 0);

        //Create a project
        Calendar startDate = new GregorianCalendar(2016, Calendar.MAY, 10);
        Calendar endDate = new GregorianCalendar(2016, Calendar.JUNE, 10);
        project = pms.createProject(startDate, endDate);

        //TODO: We need to add a project manager here. and there should be tests for that before?
        assertEquals(pms.getProjects().size(), 1);
    }

//    @Test //Test for string
//    public void testChangeNameA() {
//        String name = "Anders";
//
//        project.setName(name);
//        assertEquals(project.getName(), name);
//    }
//
//    @Test //Test for Integer
//    public void testChangeNameB() {
//        String name = "42";
//
//        project.setName(name);
//        assertEquals(project.getName(), name);
//    }
//
//    @Test //Test for float/special char.
//    public void testChangeNameC() {
//        String name = "101.2";
//
//        project.setName(name);
//        expectedEx.expect(InvalidNameException.class);
//        expectedEx.expectMessage("Special characters are not allowed in name.");
//    }
//
//    @Test //Test for special char.
//    public void testChangeNameD() {
//        String name = "Anders' ide";
//
//        project.setName(name);
//        expectedEx.expect(InvalidNameException.class);
//        expectedEx.expectMessage("Special characters are not allowed in name.");
//    }
//
//    @Test //Test for null
//    public void testChangeNameE() {
//        String name = null;
//
//        project.setName(name);
//        expectedEx.expect(InvalidNameException.class);
//        expectedEx.expectMessage("Name cannot be null.");
//    }
//
//    @Test //Test for duplicate name
//    public void testChangeNameF() {
//        String name = "Test";
//        project.setName(name);
//        assertEquals(project.getName(), name);
//
//        //Create a second project.
//        Calendar startDate = new GregorianCalendar(2016, Calendar.MAY, 10);
//        Calendar endDate = new GregorianCalendar(2016, Calendar.JUNE, 10);
//        Project secondProject = pms.createProject(startDate, endDate);
//
//        secondProject.setName(name);
//
//        expectedEx.except(InvalidNameException.class);
//        expectedEx.expectMessage("Name is already assigned to another project.");
//    }
//
//    @Test //User is not admin.
//    public void testChangeNameG() {
//        //TODO: How should we do this?
//    }
//
//    @Test //Test for too long name
//    public void testChangeNameH() {
//        String name = "MortenTellingTobiasLindstrømLouiseJustesenMarcusPagh ";
//
//        project.setName(name);
//        expectedEx.expect(InvalidNameException.class);
//        expectedEx.expectMessage("Name can only be 30 charachters long.");
//    }


}