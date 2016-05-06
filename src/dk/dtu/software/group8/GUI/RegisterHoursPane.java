package dk.dtu.software.group8.GUI;

import dk.dtu.software.group8.Activity;
import dk.dtu.software.group8.Exceptions.NegativeHoursException;
import dk.dtu.software.group8.Exceptions.NoAccessException;
import dk.dtu.software.group8.Exceptions.TooManyHoursException;
import dk.dtu.software.group8.PManagementSystem;
import dk.dtu.software.group8.ProjectActivity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;

import java.time.LocalDate;

/**
 * Created by Morten on 25/04/16.
 */
public class RegisterHoursPane extends StandardPane {


    private final ListView activitiesList;
    private ControlHoursPane controlHoursPane;
    private ObservableList<Activity> obsActivitiesOnDay;
    private final DatePicker datePicker;

    public RegisterHoursPane(PManagementSystem pms) {
        super(pms, false);

        this.getStyleClass().add("RegisterHoursPane");

        titlePane.setText("Register Hours");

        //Create the center items. First two containers.
        HBox datePickContainer = new HBox();
        datePickContainer.getStyleClass().add("Container");
        datePickContainer.setAlignment(Pos.CENTER_LEFT);

        //Create the datepicker
        Label datePickLbl = new Label("Choose a day:");
        datePicker = new DatePicker();
        datePicker.setValue(pms.getDate());
        datePickContainer.getChildren().addAll(datePickLbl, datePicker);

        //Create the listview and make sure it fills the height.
        activitiesList = new ListView();

        //Create the right view for adding hours and viewing total hours.
        controlHoursPane = new ControlHoursPane(pms, this);

        obsActivitiesOnDay = FXCollections.observableList(pms.getCurrentEmployee().getActivitiesOnDate(datePicker.getValue()));
        activitiesList.setItems(obsActivitiesOnDay);

        //Add listener to the date picker.
        datePicker.setOnAction(e -> updateDate());

        //Add listener for selection.
        activitiesList.getSelectionModel().selectedItemProperty().addListener(e -> setShownActivity());


        //Add all children to the center and right container.
        centerContainer.getChildren().add(datePickContainer);
        addNewExpandingChildToCenterContainer(activitiesList);
        rightContainer.getChildren().add(controlHoursPane);


        updateDate();

    }

    private void updateDate() {

        LocalDate chosenDay = datePicker.getValue();

        obsActivitiesOnDay = FXCollections.observableList(pms.getCurrentEmployee()
                .getActivitiesOnDate(chosenDay));

        activitiesList.setItems(obsActivitiesOnDay);

        updateTotalMinutesOnDay(chosenDay);

    }

    private void setShownActivity() {
        ProjectActivity projectActivity = (ProjectActivity) activitiesList.getSelectionModel().getSelectedItem();
        LocalDate chosenDay = datePicker.getValue();

        int totalMinutesOnDayAndActivity = pms.getCurrentEmployee()
                .getTotalRegisteredMinutesOnDayAndActivity(chosenDay, projectActivity);


        controlHoursPane.setTotalMinutesOnActivity(totalMinutesOnDayAndActivity);

    }


    @Override
    protected void close() {
        toBack();
    }

    public void registerTimeOnActivity(String[] time) {
        ProjectActivity projectActivity = (ProjectActivity) activitiesList.getSelectionModel().getSelectedItem();
        LocalDate chosenDay = datePicker.getValue();

        try {

            int minutes = Integer.parseInt(time[0]) * 60 + Integer.parseInt(time[1]);

            pms.getCurrentEmployee().registerWorkHours(projectActivity, minutes, chosenDay);

        } catch (NoAccessException | TooManyHoursException | NegativeHoursException e) {
            Alert error = new ErrorPrompt(Alert.AlertType.INFORMATION, e.getMessage());
            error.showAndWait();
        } catch (Exception e) {
            Alert error = new ErrorPrompt(Alert.AlertType.INFORMATION, "Something went wrong converting your input.");
            error.showAndWait();
        }

        updateTotalMinutesOnDay(chosenDay);
    }

    private void updateTotalMinutesOnDay(LocalDate day) {
        controlHoursPane.setTotalMinutesOnDay(pms.getCurrentEmployee().getTotalRegisteredMinutesOnDay(day));

    }
}
