package dk.dtu.software.group8.GUI;

import dk.dtu.software.group8.Exceptions.NoAccessException;
import dk.dtu.software.group8.PManagementSystem;
import dk.dtu.software.group8.Project;
import dk.dtu.software.group8.ProjectActivity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class ProjectPane extends StandardPane {

    private ProjectActivityPane projectActivityPane;
    private ListView activitiesListView;
    private ManageProjectPane manageProjectPane;
    private CreateProjectActivityPane createProjectActivityPane;
    private Project project;

    private ProjectsPane projectsPane;
    private ObservableList<ProjectActivity> obsActivities;


    public ProjectPane(ProjectActivityPane projectActivityPane, PManagementSystem pms) {
        super(pms, true);

        this.projectActivityPane = projectActivityPane;

        manageProjectPane = new ManageProjectPane(pms);
        createProjectActivityPane = new CreateProjectActivityPane(pms, this);

        activitiesListView = new ListView();
        activitiesListView.setOnMouseClicked(e -> manageActivity(e));

        Button extractWorkReportBtn = new Button("Extract work report for last week");
        extractWorkReportBtn.setOnAction(e -> extractWorkReport());

        rightContainer.getChildren().addAll(manageProjectPane, createProjectActivityPane, extractWorkReportBtn);

        addTitleToCenterContainer("Activities");
        addNewExpandingChildToCenterContainer(activitiesListView);

    }

    private void extractWorkReport() {
        try {
            pms.extractWorkReport(project);
        } catch (IOException e) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setHeaderText("Something went wrong!");
            error.setContentText(e.getMessage());

            error.showAndWait();
        } catch (NoAccessException e) {
            Alert error = new ErrorPrompt(Alert.AlertType.INFORMATION, e.getMessage());
            error.showAndWait();
        }
    }


    public void setProject(Project project) {

        this.project = project;

        createProjectActivityPane.setProject(project);

        refresh();

    }

    public void show() {
        update();
        this.toFront();
    }


    private void update() {
        this.titlePane.setText("Project Id: " + project.getId());
        this.manageProjectPane.setProject(project);

        refresh();
    }

    protected void close() {
        this.toBack();
        projectsPane.refresh();
    }

    private void manageActivity(MouseEvent e) {
        if (e.getClickCount() == 2) {
            ProjectActivity projectActivity = (ProjectActivity) activitiesListView.getSelectionModel().getSelectedItem();
            if (projectActivity != null) {
                projectActivityPane.setProjectPane(this);
                projectActivityPane.setProjectActivity(projectActivity);
                projectActivityPane.toFront();
            }
        }
    }


    public void setProjectsPane(ProjectsPane projectsPane) {
        this.projectsPane = projectsPane;
    }

    public void refresh() {
        obsActivities = FXCollections.observableList(project.getActivities());
        activitiesListView.setItems(obsActivities);

        activitiesListView.refresh();
    }
}
