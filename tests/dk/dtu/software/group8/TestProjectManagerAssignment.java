package dk.dtu.software.group8;

import dk.dtu.software.group8.Exceptions.AlreadyAssignedProjectManagerException;
import dk.dtu.software.group8.Exceptions.InvalidEmployeeException;
import dk.dtu.software.group8.Exceptions.NoAccessException;
import dk.dtu.software.group8.Exceptions.WrongDateException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Tobias
 */
public class TestProjectManagerAssignment {
	
	PManagementSystem pms;
    DatabaseManager db;
    Project project;
	
    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    /**
     * Created by Morten
     */
    @Before
    public void setup() throws WrongDateException, NoAccessException, IOException {
        pms = new PManagementSystem();
        db = new DatabaseManager("Employees.txt");

        //Set current date to the may 9th 2016.
        DateServer mockDateServer = mock(DateServer.class);
        pms.setDateServer(mockDateServer);

        LocalDate date = LocalDate.parse("2016-05-09");
        when(pms.getDate()).thenReturn(date);

        //Login a user
        pms.signIn(db.getEmployees().get(0).getId());
        assertTrue(pms.userLoggedIn());


        //Check the project base is empty.
        assertThat(pms.getProjects().isEmpty(), is(true));

        //Create a project
        LocalDate startDate = LocalDate.parse("2016-05-10");
        LocalDate endDate = LocalDate.parse("2016-06-10");
        project = pms.createProject(startDate, endDate);

        assertThat(pms.getProjects().size(), is(1));
    }

    /**
     * Created by Tobias
     */
    @Test
    public void testIsEmployee() throws InvalidEmployeeException, AlreadyAssignedProjectManagerException {
        String empId = db.getEmployees().get(0).getId();
        Employee emp = pms.getEmployeeFromId(empId);

        pms.assignManagerToProject(project);
        assertThat(project.getProjectManager(), is(emp));
    }

    /**
     * Created by Marcus
     */
    @Test
    public void testProjectHasManager() throws AlreadyAssignedProjectManagerException, InvalidEmployeeException{
        expectedEx.expect(AlreadyAssignedProjectManagerException.class);
        expectedEx.expectMessage("The project already has a Project Manager.");

        String empId = db.getEmployees().get(0).getId();
        Employee emp = pms.getEmployeeFromId(empId);

        pms.assignManagerToProject(project);
        assertThat(project.getProjectManager(), is(emp));

        String secondEmpId = db.getEmployees().get(2).getId();
        pms.signIn(secondEmpId);
        pms.assignManagerToProject(project);
    }
}