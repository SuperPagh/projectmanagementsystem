package dk.dtu.software.group8;

import dk.dtu.software.group8.Exceptions.WrongDateException;

import java.util.Calendar;

public class Project {

    private String iD;
    private String name;
    private Calendar startDate;
    private Calendar endDate;
    private Employee projectManager;
    private DateServer dateServer;

    public Project(DateServer dateServer,
                   Calendar startDate,
                   Calendar endDate,
                   String iD) throws WrongDateException {

        this.dateServer = dateServer;

        if(startDate == null || endDate == null) {
            throw new WrongDateException("Missing date(s).");
        }

        setEndDate(endDate);
        setStartDate(startDate);

        this.iD = iD;
    }

    public String extractReport() {
        return null;
    }

    public void assignProjectManager(Employee employee) {
        this.projectManager = employee;
    }

    public Calendar getEndDate() {
        return endDate;
    }

    public void setEndDate(Calendar endDate) throws WrongDateException {
        if (endDate.before(startDate)) {
            throw new WrongDateException("End date is before start date.");
        }
        this.endDate = endDate;
    }

    public Calendar getStartDate() {
        return startDate;
    }

    public void setStartDate(Calendar startDate) throws WrongDateException{
        if (startDate.after(endDate)) {
            throw new WrongDateException("End date is before start date.");
        } else if (startDate.before(dateServer.getCalendar())) {
            throw new WrongDateException("Date is in the past.");
        }
        this.startDate = startDate;
    }

    public String getId() {
        return iD;
    }

    public Employee getProjectManager() {
        return projectManager;
    }

    public Calendar getDate() {
        return dateServer.getCalendar();
    }
}
