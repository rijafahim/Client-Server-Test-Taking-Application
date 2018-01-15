/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Assignment4;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import com.jfoenix.controls.JFXTextField;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author Rija Fahim
 */
public class ServerController implements Initializable 
{
    private Server S;
    public static boolean exitPressed = false; 
    @FXML
    private JFXButton exit;
    @FXML
    private Label course_label;


    @FXML
    private Label Test_label;

    @FXML
    private ImageView logo;

    @FXML
    private JFXButton endtimer;

    @FXML
    private JFXTextField course;

    @FXML
    private JFXListView<String> activeStudents;
    @FXML
    private JFXListView<String> activeStudentsstatus;
    @FXML
    private JFXTextField test_name;
     @FXML
    private JFXButton uploadtest;


    @FXML
    void setCourse(ActionEvent event) 
    {
        
    }

    @FXML
    void setTest(ActionEvent event) 
    {

    }


    @FXML
    void stopAlarm(ActionEvent event) 
    {
        S.SignalForAlarm();
    }
    
    @FXML
    void exitServer(ActionEvent event) throws IOException 
    {
        exitPressed = true;
        Stage stage = (Stage)exit.getScene().getWindow();
        stage.close();
    }
    @FXML
    void MaketestDirectory(ActionEvent event) 
    {
        if (test_name.getText().isEmpty() && course.getText().isEmpty())
        {
            Test_label.setText("* Enter Test Name");
            course_label.setText("* Enter Course Title");
        }
        else if (test_name.getText().isEmpty() && !course.getText().isEmpty())
        {
            Test_label.setText("* Enter Test Name");
        }
        else if (!test_name.getText().isEmpty() && course.getText().isEmpty())
        {
            course_label.setText("* Enter Course Title");
        }
        else
        {
            test_name.setDisable(true);
            course.setDisable(true);
            endtimer.setDisable(false);
            uploadtest.setDisable(true);
            String temp = test_name.getText();
            S.setTest(temp);
            temp = course.getText();
            S.setCourse(temp);
            S.makeDirectory();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        endtimer.setDisable(true);
        S = new Server();
        BufferedImage BufferedImg = null;
        String file = System.getProperty("user.dir") +  System.getProperty ("file.separator")+ "src"+System.getProperty ("file.separator") + "Assignment4" + System.getProperty ("file.separator")+ "images" + System.getProperty ("file.separator") + "a.png";
        System.out.println(file);
        try {
            BufferedImg = ImageIO.read(new File(file));
        } catch (IOException ex) {
            Logger.getLogger(ServerController.class.getName()).log(Level.SEVERE, null, ex);
        }


        WritableImage wr = null;
        if (BufferedImg!=null)
        {
            wr = new WritableImage(BufferedImg.getWidth(),BufferedImg.getHeight());
            PixelWriter px = wr.getPixelWriter();
            for (int i=0; i<BufferedImg.getWidth(); i++)
            {
                for (int j=0; j<BufferedImg.getHeight(); j++)
                {
                    px.setArgb(i, j, BufferedImg.getRGB(i,j));
                }
            }
             
        }
        logo.setImage(wr);
        activeStudents.setItems(studentThread.Students);

        Platform.runLater(new Runnable() {
            @Override
            public void run()
            {
                activeStudents.setItems(studentThread.Students);
            }
        });
        
                Platform.runLater(new Runnable() {
            @Override
            public void run()
            {
                activeStudentsstatus.setItems(studentThread.Students_Status);
            }
        });
  
    
        
    }    
    
}
