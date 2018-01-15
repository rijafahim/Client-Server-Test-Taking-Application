/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Assignment4;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

public class LoginController implements Initializable {
private Student S;
private static String user1 = "A";
private static String user2 = "B";
private static String user3 = "C";
private static String userpassword = "123";
  @FXML
    private JFXTextField password;
 
    @FXML
    private JFXButton submit;
    @FXML
    private Label invalidUP;
    @FXML
    private JFXTextField rollno;

    @FXML
    private ImageView logo;
    
    @FXML
    private Label password_field;
    
    @FXML
    private Label username_field;
    
    @FXML
    void getRollNumber(ActionEvent event) 
    {

    }
    @FXML
    void getPassword(ActionEvent event) 
    {
            
    }
    static boolean ValidateUser(String username)
    {
        System.out.println("Username entered" + username);
        boolean validate = true;
        if (!username.equals(user1) && !username.equals(user2) && !username.equals(user3))
        {
            validate = false;
        }
        return validate;
        
    }
    static boolean ValidatePassword(String upassword)
    {
        System.out.println("Password entered: " + upassword);
        boolean validate = true;
        if (!upassword.equals(userpassword))
        {
            validate = false;
        }
        return validate;
    }
    @FXML
    void EnterServer(ActionEvent event) throws IOException, InterruptedException 
    {
        if (rollno.getText().isEmpty() && password.getText().isEmpty())
        {
            username_field.setText("*Enter Name");
            password_field.setText("*Enter Password");
        }
        else if (rollno.getText().isEmpty() && !password.getText().isEmpty())
        {
             username_field.setText("*Enter Name");
        }
        else if (!rollno.getText().isEmpty() && password.getText().isEmpty())
        {
            password_field.setText("*Enter Password");
        }
        else 
        { 
            if (ValidateUser(rollno.getText()))
            {
                if (ValidatePassword(password.getText()))
                {
                    String temp = rollno.getText();
                    S.getRollNo(temp);
                    System.out.println("Welcome!");
                    Parent home_page = FXMLLoader.load(getClass().getResource("Student_Interface.fxml"));
                    temp = home_page.toString();
                    System.out.println(temp);
                    Scene home_page_scene = new Scene(home_page);
                    Stage S1 = (Stage)((Node)event.getSource()).getScene().getWindow();
                    S1.setScene(home_page_scene);
                    S1.show();
                }
                else
                {
                    invalidUP.setText("INVALID PASSWORD");
                    rollno.setText("");
                    password.setText("");
                }
            }
            else
            {
                invalidUP.setText("UNREGISTERED USER");
                rollno.setText("");
                password.setText("");

            }

        }
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
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
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
    }    
    
}
