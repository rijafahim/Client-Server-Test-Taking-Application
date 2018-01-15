package Assignment4;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Rija Fahim
 */
public class studentThread extends Thread
{
    private String studentName = null;
    private DataInputStream in = null;
    private PrintStream out = null;
    private Socket studentSocket = null;
    private static BufferedReader input = null;
    private BufferedWriter bw = null;
    private studentThread[] threads;
    private int STUDENT_LIMIT;
    public static ObservableList<String> Students = FXCollections.observableArrayList();
    public static ObservableList<String> Students_Status = FXCollections.observableArrayList();
    static String Test_PATH = System.getProperty("user.dir") +  System.getProperty ("file.separator");
    studentThread(Socket studentSocket, studentThread[] threads)
    {
        this.studentSocket =  studentSocket;
        //equates sockets
        this.threads = threads;
        //threads
        STUDENT_LIMIT = threads.length;
        //limit no
    }
   
    public void run()
    {
        String temp = null;
        int STUDENT_LIMIT = this.STUDENT_LIMIT;
        studentThread[] threads = this.threads;
        File FilePath = null;
        FileWriter output = null;
        try
        {
            in = new DataInputStream(studentSocket.getInputStream());
            out =  new PrintStream(studentSocket.getOutputStream());
            while(true)
            {
                String incomingInput;
                incomingInput = in.readLine().trim();
                System.out.println(incomingInput);
                if(incomingInput.contains("#r#"))
                {
                    incomingInput = incomingInput.replace("#r#", "");
                    Students.add(incomingInput);
                    temp = incomingInput;
                }
                else if(incomingInput.contains("#t#"))
                {
                    incomingInput = incomingInput.replace("#t#", "");
                    System.out.println(Test_PATH);
                    File TestDir = new File(Test_PATH);
                    if (TestDir.isDirectory())
                    {
                        try
                        {
                            FilePath = new File(TestDir.getAbsolutePath()+ System.getProperty("file.separator") + temp + ".txt");
                            FilePath.createNewFile();
                            output = new FileWriter(FilePath);
                            bw = new BufferedWriter(output);
                            bw.write(incomingInput, 0, incomingInput.length());
                            Students_Status.add(("SUBMITTED"));
                            bw.flush();
                        }
                        finally
                        {
                            if (output!=null)
                            {
                                output.close();
                            }
                        }
                    }
                }
            }
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(studentThread.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    public void exit() throws IOException
    {
        for (int i=0; i<STUDENT_LIMIT; i++)
        {
            if (threads[i] == this)
            {
                threads[i] = null;
            }
        }
        in.close();
        out.close();
        studentSocket.close();
        
    }
    public void sendAlarm()
    {
        if (!Students_Status.isEmpty())
        {
            Students_Status.remove(0);
        }
        String signal = "alarm";
        out.println(signal);
    }

}


