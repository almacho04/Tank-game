package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.util.Random;

public class newClient extends Application implements Serializable{
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private transient ImageView imageView = null;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException{
        Image  img = new Image(new FileInputStream("C:\\Users\\User\\Downloads\\tank.jpeg"));
        imageView = new ImageView(img);

        Random rand = new Random();

        imageView.setX(100 + 50*(rand.nextInt(12)));
        imageView.setY(100 + 50*(rand.nextInt(12)));
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);

        new Thread(()->{
            Socket socket = null;
            try {
                socket = new Socket("localhost", 1234);
                outputStream =  new ObjectOutputStream(socket.getOutputStream());
                inputStream = new ObjectInputStream(socket.getInputStream());
                try {
                    outputStream.writeObject(imageView);
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }

                Platform.runLater(()->{
                    System.out.println("client_on");
                });
            } catch (IOException e) {
                e.printStackTrace();
            }

        }).start();




        Pane pane = new Pane();

        pane.getChildren().add(imageView);

        Scene scene = new Scene(pane, 800, 800);
        primaryStage.setTitle("ShowEllipse"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show();
    }
}
