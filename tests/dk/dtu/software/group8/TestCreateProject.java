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

@SuppressWarnings("unused")
public class TestCreateProject {
	PManagementSystem pms;
	DatabaseManager db;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

	@Before
	public void setup() {
		pms = new PManagementSystem();

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
	}


//
//	@Test //Correct start and end.
//	public void createProjectA() {
//        Calendar startDate = new GregorianCalendar(2016, Calendar.MAY, 10);
//        Calendar endDate = new GregorianCalendar(2016, Calendar.JUNE, 10);
//
//        //Create the project
//		Project project = pms.createProject(startDate, endDate);
//
//        //Check that the project was added.
//		assertEquals(pms.getProjects().size(), 1);
//
//        //Test that date is set correct.
//        assertEquals(project.getStartDate(), startDate);
//        assertEquals(project.getEndDate(), endDate);

//        assertThat(pms.getProjects()[0], instanceOf(Project.class));
//		assertThat(pms.getProjects()[0].getID(), RegexMatcher.matches("^[0-9]{1,6}$"));
//	}

//
//    @Test //Correct start and end.
//    public void createProjectB() {
//        Calendar startDate = new GregorianCalendar(2016, Calendar.MAY, 10);
//        Calendar endDate = new GregorianCalendar(2016, Calendar.MAY, 10);
//
//        //Create the project
//        Project project = pms.createProject(startDate, endDate);
//
//        //Check that the project was added.
//        assertEquals(pms.getProjects().size(), 1);
//
//        //Test that date is set correct.
//        assertEquals(project.getStartDate(), startDate);
//        assertEquals(project.getEndDate(), endDate);

//      assertThat(pms.getProjects()[0], instanceOf(Project.class));
//		assertThat(pms.getProjects()[0].getID(), RegexMatcher.matches("^[0-9]{1,6}$"));
//    }
//
//    @Test //End date before start.
//    public void createProjectC() {
//        Calendar startDate = new GregorianCalendar(2016, Calendar.MAY, 10);
//        Calendar endDate = new GregorianCalendar(2016, Calendar.MAY, 3);
//
//        //Create the project
//        Project project = pms.createProject(startDate, endDate);
//
//        //Check correct exception
//        expectedEx.expect(WrongDateException.class);
//        expectedEx.expectMessage("End date is before start date.");
//    }
//
//    @Test //Start date in the past, correct end date
//    public void createProjectD() {
//        Calendar startDate = new GregorianCalendar(2016, Calendar.APRIL, 25);
//        Calendar endDate = new GregorianCalendar(2016, Calendar.MAY, 10);
//
//        //Create the project
//        Project project = pms.createProject(startDate, endDate);
//
//        //Check correct exception
//        expectedEx.expect(WrongDateException.class);
//        expectedEx.expectMessage("Date is in the past.");
//    }
//
//    @Test //Incorrect start date, incorrect end date. (Both in past)
//    public void createProjectE() {
//        Calendar startDate = new GregorianCalendar(2016, Calendar.APRIL, 25);
//        Calendar endDate = new GregorianCalendar(2016, Calendar.APRIL, 30);
//
//        //Create the project
//        Project project = pms.createProject(startDate, endDate);
//
//        //Check correct exception
//        expectedEx.expect(WrongDateException.class);
//        expectedEx.expectMessage("Date is in the past.");
//    }
//
//    @Test //No dates.
//    public void createProjectF() {
//
//        //Create the project with no dates.
//        Project project = pms.createProject(null, null);
//
//        //Check correct exception
//        expectedEx.expect(WrongDateException.class);
//        expectedEx.expectMessage("Missing date(s).");
//
//        //Check that the project was null.
//        assertNull(project);
//
//        //Check that the project wasn't added.
//        assertEquals(pms.getProjects().size(), 0);
//    }


}
