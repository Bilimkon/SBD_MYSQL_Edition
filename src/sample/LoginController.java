package sample;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.Core.User;
import sample.DAO.UserDao;
import sample.Utils.Utils;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class LoginController implements Initializable {

    public Button btnKirish;
    public TextField textIsm;
    public PasswordField textPassword;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void btnActionKirish() throws IOException {

        String ismText = textIsm.getText().toString();//getting string  values
        String passText = textPassword.getText().toString();//getting string  values
        Parent root = null;
        Stage stage = new Stage();

        if (isUserExists(ismText, passText)) {

            try {
                root = FXMLLoader.load(getClass().getResource("views/MainPage.fxml"));

                stage.setTitle("SBD boshqaruv tizimi");
                stage.setResizable(true);
                stage.setOnCloseRequest(event -> Main.is_clock_alive = false);
                Screen screen = Screen.getPrimary();
                Rectangle2D bounds = screen.getVisualBounds();
                stage.setScene(new Scene(root));
                stage.initStyle(StageStyle.UNDECORATED);
                stage.show();
                stage.setOnCloseRequest(e-> Platform.exit());
                stage.setOnCloseRequest(e->System.exit(0));
                stage.getIcons().add(new Image(Main.class.getResourceAsStream("style/Images/SBD-logo.png")));
                stage.setMinHeight(720);
                stage.setMinWidth(1080);
                stage.setResizable(true);
                stage.setMaximized(true);


            // Hide this current window (if this is what you want)
                this.btnKirish.getScene().getWindow().hide();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        else if(ismText.equals("1")&&passText.equals("1")){

            try {
                root = FXMLLoader.load(getClass().getResource("views/AdminPart.fxml"));

                stage.setTitle("SBD admin tizimi");
                stage.setResizable(true);

                Screen screen = Screen.getPrimary();
                Rectangle2D bounds = screen.getVisualBounds();
                stage.setScene(new Scene(root));
                stage.show();
                stage.setOnCloseRequest(e-> Platform.exit());
                stage.setOnCloseRequest(e-> System.exit(0));
                stage.getIcons().add(new Image(Main.class.getResourceAsStream("style/Images/SBD-logo.png")));
                stage.setMinWidth(1080);
                stage.setMinHeight(720);
                stage.setResizable(true);
                // Hide this current window (if this is what you want)
                this.btnKirish.getScene().getWindow().hide();

             } catch (IOException e) {
                e.printStackTrace();
                Utils.ErrorAlert("Error","Xatolik"+e, "Xatolik bor shu yerda");
            }
        }
       else {
            Utils.ErrorAlert("Login", "Xatolik bor", "E-mail yoki parol xato");
        }
    }
    public static User currentUser;

    private boolean isUserExists(String name, String password) {
        try {
            UserDao userDao = new UserDao();
            currentUser = userDao.getUser(name, password);

            if (currentUser == null) return false;
            userDao.closeConnection();
            return true;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
