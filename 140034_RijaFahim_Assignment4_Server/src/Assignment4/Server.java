
package Assignment4;

import Assignment4.studentThread;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

/**
 *
 * @author Rija Fahim
 */
public class Server extends Application
{
    private static ServerSocket serverSocket = null;
    private static Socket studentSocket = null;
    private static int PORT_NO = 2222;
    private static int STUDENT_LIMIT = 12;
    private static int counter = 0;
    public static String test;
    public static String Course_name;
    public static studentThread[] threads = new studentThread[STUDENT_LIMIT];

    
    @Override
    public void start(Stage stage) throws Exception 
    {
        Parent root = FXMLLoader.load(getClass().getResource("Server.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
            Task<Void> task = new Task<Void>() 
            {
                @Override
                protected Void call() throws Exception
                {
                    connectServer();
                    return null;
                }
            };
            
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
        
    }
    public void setTest(String testname)
    {
       test = testname;
    }
    public void setCourse(String coursename)
    {
       Course_name = coursename;

    }
    public void makeDirectory()
    {
        studentThread.Test_PATH = studentThread.Test_PATH + Course_name + "_" + test;
        File file = new File(studentThread.Test_PATH);
        file.mkdir();
    }
    
    public void connectServer() throws IOException
    {
        serverSocket = new ServerSocket(PORT_NO);
        System.out.println("Server running");
        while(true)
        {
            if (counter < STUDENT_LIMIT)
            {
                studentSocket = serverSocket.accept();
                for (int i=0; i<STUDENT_LIMIT; i++)
                {
                    if (threads[i] == null)
                    {
                        System.out.println("New Student!");
                        counter++;
                        (threads[i] = new studentThread(studentSocket, threads)).start();
                        break;
                    }
                }
            }
           
        }
    }
    public void exitServer() throws IOException
    {
        synchronized (this)
        {
            for (int i =0;i<counter; i++)
            {
                threads[i].exit();
            }
        }
        studentSocket.close();
        serverSocket.close();
        
    }
    public void SignalForAlarm()
    {
        synchronized (this)
        {
            for (int i =0;i<counter; i++)
            {
                threads[i].sendAlarm();
            }
        }
    }
    public static void main(String[]args) throws IOException
    {
        launch(args);
    }
      
    
}



