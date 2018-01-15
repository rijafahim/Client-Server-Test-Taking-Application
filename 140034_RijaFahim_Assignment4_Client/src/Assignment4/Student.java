
package Assignment4;

import java.io.DataInputStream;
import java.io.PrintStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import Assignment4.Student_InterfaceController;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Rija Fahim
 */
public class Student extends Application implements Runnable
{
    private static Socket studentSocket = null;
    private static PrintStream out = null;
    private static DataInputStream in = null;
    private static BufferedReader input = null;
    public String studentName = null;
    public String section = null;
    public String roll_no = null;
    public String password = null;
    //private static boolean closed = false;
    static int port_Number = 2222;
    static String host = "localhost";
    String testContent = null;
    static boolean isAlarm = false;
    public static ObservableList<String> AlarmMessage = FXCollections.observableArrayList();
    @Override
    public void run() 
    {
        String serverSignal; 
        while(true)
        {
            try
            {
                serverSignal = in.readLine().trim();
                if(serverSignal.equals("alarm"))
                {
                    Alarm();
                }
                System.out.println(serverSignal);
            }
            catch(IOException Ex)
            {
                Ex.printStackTrace();
            } catch (NoSuchMethodException ex) {
                Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvocationTargetException ex) {
                Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public void getRollNo (String rollno)
    {
        this.roll_no = rollno;
        System.out.println(this.roll_no);
        out.println("#r#"+this.roll_no);
    }
    public void getTest (String testContent)
    {
        this.testContent = testContent;
        System.out.println(this.testContent);
        out.println("#t#"+this.testContent);
    }
    public void newStudent () throws IOException
    {
        
        studentSocket = new Socket(host, port_Number);
        input = new BufferedReader(new InputStreamReader(System.in));
        out = new PrintStream(studentSocket.getOutputStream());
        in = new DataInputStream(studentSocket.getInputStream());
        new Thread(new Student()).start();
    }
    @Override
    public void start(Stage stage) throws Exception 
    {
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        Task<Void> task = new Task<Void>() 
            {
                @Override
                protected Void call() throws Exception
                {
                    newStudent();
                    return null;
                }
            };
            
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
        
    }
    public void Alarm() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
    {
        AlarmMessage.add("TIME UP! SUBMIT YOUR ANSWERS NOW");
        Alarm.isAlarm = true;
        Media sound= new Media(new File("src/Assignment4/alarm.mp3").toURI().toString());
        MediaPlayer MP = new MediaPlayer(sound);
        MP.play();
    }
    public static void main(String[]args) throws IOException
    {
        launch(args);
    }
    
}
