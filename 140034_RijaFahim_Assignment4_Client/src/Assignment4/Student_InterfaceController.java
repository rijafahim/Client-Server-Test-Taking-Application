/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Assignment4;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import Assignment4.Student;
import java.awt.image.BufferedImage;
import java.io.File;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javax.imageio.ImageIO;
import com.jfoenix.controls.JFXListView;
//import javafx.scene.media.MediaPlayer;

/**
 * FXML Controller class
 *
 * @author Rija Fahim
*/
public class Student_InterfaceController implements Initializable 
{
    private Student S;
    
    @FXML
    private JFXListView<String> warning;

    @FXML
    private JFXButton submit;

    @FXML
    private ImageView logo;

    @FXML
    private JFXTextArea text;

    public static boolean isAlarm= false;

 
    @FXML
    void writeExam(ActionEvent event) 
    {
        String temp = text.getText();
        S.getTest(temp);
    }
    
    @FXML
    public void submitExam(ActionEvent event) 
    {
        String temp = text.getText();
        System.out.println(temp);
        S.getTest(temp);
        Alarm.isAlarm = false;
        Student.AlarmMessage.remove(0);
        text.setDisable(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        S = new Student();
        BufferedImage BufferedImg = null;
        String file = System.getProperty("user.dir") +  System.getProperty ("file.separator")+ "src"+System.getProperty ("file.separator") + "Assignment4" + System.getProperty ("file.separator")+ "images" + System.getProperty ("file.separator") + "a.png";
        System.out.println(file);
        try {
            BufferedImg = ImageIO.read(new File(file));
        } catch (IOException ex) {
            Logger.getLogger(Student_InterfaceController.class.getName()).log(Level.SEVERE, null, ex);
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
        
        Platform.runLater(new Runnable() {
            @Override
            public void run()
            {
                warning.setItems(Student.AlarmMessage);

            }
        });
        
        Task task = new Task<Void>() 
        {
            @Override
            protected Void call() throws Exception
            {
                while(true)
                {
                    Platform.runLater(new Runnable() 
                    {
                        @Override
                        public void run()
                        {
                            if(Alarm.isAlarm)
                            {
                                text.setDisable(true);
                            }
                        }
                    });
                    Thread.sleep(2000);
                }
                //return null;
            }
        };
            
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }    
    
    
    
    
  

}
    
    
    
