package dk.dtu.software.group8.GUI;

import dk.dtu.software.group8.Exceptions.AlreadyAssignedProjectManagerException;
import dk.dtu.software.group8.Exceptions.WrongDateException;
import dk.dtu.software.group8.PManagementSystem;
import dk.dtu.software.group8.Project;
import dk.dtu.software.group8.ProjectActivity;
import dk.dtu.software.group8.YearWeek;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Locale;

/**
 * Created by Tobias
 */
public class Driver extends Application {

    private PManagementSystem pms;
    private PrimaryStage primaryStage;

    /**
     * Created by Morten
     */
    public static void main(String[] args) {

        //Set the date format to UK
        Locale.setDefault(Locale.UK);

        Application.launch(args);
}

    /**
     * Created by Tobias
     */
    public void start(Stage loginStage) {

        //Create an instance of the pms

        try {
            this.pms = new PManagementSystem();
        } catch (IOException e) {
            Alert error = new Alert(Alert.AlertType.ERROR, "Couldn't load database! Execution aborted!");
            error.showAndWait();
        }

        beforeDemo();

        //Create the login stage
        loginStage = new LoginStage(this, pms);

        //To start just show the loginstage.
        loginStage.show();
    }

    /**
     * Created by Marcus
     */
    public void startPrimaryStage() {

        demo();

        primaryStage = new PrimaryStage(pms);

        primaryStage.show();
    }

    /**
     * Created by Morten
     */
    private void beforeDemo() {

        try {
            pms.signIn("huba");
            Project project = pms.createProject(LocalDate.now(), LocalDate.now().plusMonths(10));
            pms.assignManagerToProject(project);

            for (int i = 0; i < 5; i++) {
                try {
                    ProjectActivity activity = pms.createActivityForProject(project,
                            "Implementation",
                            YearWeek.fromDate(LocalDate.now().plusDays(10)),
                            YearWeek.fromDate(LocalDate.now().plusDays(40)),
                            20);

                    pms.addEmployeeToActivity(activity, pms.getCurrentEmployee());

                } catch (Exception e) {
                    Alert error = new ErrorPrompt(Alert.AlertType.INFORMATION, e.getMessage());
                    error.showAndWait();
                }
            }


        } catch (Exception e) {
            Alert error = new ErrorPrompt(Alert.AlertType.INFORMATION, e.getMessage());
            error.showAndWait();
        }

        for (int i = 0; i < 10; i++) {
            try {
                pms.createProject(LocalDate.now(), LocalDate.now().plusMonths(10));
            } catch (WrongDateException e) {
                Alert error = new ErrorPrompt(Alert.AlertType.INFORMATION, e.getMessage());
                error.showAndWait();
            }
        }
    }

    /**
     * Created by Tobias
     */
    private void demo() {
        Project project = null;
        try {
            project = pms.createProject(LocalDate.now(), LocalDate.now().plusMonths(10));
        } catch (WrongDateException e) {
            Alert error = new ErrorPrompt(Alert.AlertType.INFORMATION, e.getMessage());
            error.showAndWait();
        }

        try {
            pms.assignManagerToProject(project);
        } catch (AlreadyAssignedProjectManagerException e) {
            Alert error = new ErrorPrompt(Alert.AlertType.INFORMATION, e.getMessage());
            error.showAndWait();
        }

        for (int i = 0; i < 5; i++) {
            try {
                ProjectActivity activity = pms.createActivityForProject(project,
                        "Implementation",
                        YearWeek.fromDate(LocalDate.now().plusDays(10)),
                        YearWeek.fromDate(LocalDate.now().plusDays(40)),
                        20);

                pms.addEmployeeToActivity(activity, pms.getCurrentEmployee());

            } catch (Exception e) {
                Alert error = new ErrorPrompt(Alert.AlertType.INFORMATION, e.getMessage());
                error.showAndWait();
            }
        }
    }
}