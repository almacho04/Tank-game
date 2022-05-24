package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

import javafx.scene.control.Button;
import javafx.util.Duration;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import org.w3c.dom.ls.LSOutput;

public class Main extends Application implements Serializable{
    public int[][] count;
    private int size;
    public int block_HEIGHT = 50;
    public int block_WIDTH = 50;

    private final int main_rec_X = 100;
    private final int main_rec_Y = 100;
    public Map map;

    public static transient Pane p;

    private Tank.Into into;
    private Tank.Into client_tank;


    public int bot_x;
    public int bot_y;

    public int HEIGHT = 100;
    public int WIDTH = 100;

    private int Bot_Width_1 = 40;
    private int Bot_Height_1 = 40;

    private ImageView imgv_bot;

    int client_x;
    int client_y;

    transient ObjectOutputStream fout;
    ObjectInputStream in;

    public int heart = 3;
    public int c_heart = 3;



//    public Main(){
//        start();
//    }


    @Override
    public void start(Stage primaryStage) throws InvalidMapException, FileNotFoundException {

        p = new Pane();

        Scanner go = new Scanner( new File("C:\\Users\\User\\Desktop\\Project_2 photo\\map.txt") );
        map = new Map(go);

        count = new int[map.getSize()][map.getSize()];

        map.print();
        for (int i=0; i<map.getSize(); i++){
            for (int j=0; j<map.getSize(); j++){
                count[i][j] = 0;
            }
        }



        this.size = map.getSize();

        Rectangle main_rec = new Rectangle(main_rec_X, main_rec_Y, size*block_HEIGHT, size*block_WIDTH);
        main_rec.setFill(Color.BLACK);
        p.getChildren().add(main_rec);

        draw(p, map);
        System.out.println();


        Random random = new Random();
        int bot_x = random.nextInt(size);
        int bot_y = random.nextInt(size);
        boolean sdk = true;

        while (sdk){
            if (Character.compare( map.getValueAt(bot_x, bot_y), '0' ) == 0 && Character.compare( map.getValueAt(bot_x, bot_y), 'P' ) != 0){
                sdk = false;
            }else {
                bot_x = random.nextInt(size);
                bot_y = random.nextInt(size);
            }
        }
        BotPlayer.Into_BotPlayer bot = new BotPlayer.Into_BotPlayer(bot_x, bot_y);

        p.getChildren().add(bot);



//        Client client = new Client(p, map);
//        client.start(primaryStage);
        Button btn = new Button();
        btn.setText("add");
        final Integer[] k = {0};
        btn.setOnAction( e-> {
            k[0] =1;
            System.out.println("clicked");
            System.out.println(k[0]);

        });
        System.out.println("how");

        System.out.println(k[0]);

        btn.setLayoutX(850);
        btn.setLayoutY(220);

//        p.getChildren().add(btn);


        Game game  = new Game(map);
        Player player = new MyPlayer();
        game.addPlayer(player);

        Random rand = new Random();
        client_x = rand.nextInt(size);
        client_y = rand.nextInt(size);
        boolean sdf = true;

        while (sdf){
            if (Character.compare( map.getValueAt(client_x, client_y), '0' ) == 0 && Character.compare( map.getValueAt(client_x, client_y), 'P' ) != 0){
                sdf = false;
            }else {
                client_x = rand.nextInt(size);
                client_y = rand.nextInt(size);
            }
        }


        try {
            client_tank = new Tank.Into(client_x, client_y ,map);
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
        p.getChildren().add(client_tank);



        new Thread( ()-> {
            client_tank.setOnKeyPressed( e -> { client_tank.move_client_tank(e.getCode(), count);
                System.out.println("click: " + map.getClick());
                System.out.println("Halai");
                if (map.getClick() == 1){
                    System.out.println("ho");
                    if (c_heart == 0){
                        System.out.println("solo");
                        map.setValueAt((int)client_tank.tank_cor_x, (int)client_tank.tank_cor_y, "0");
                        p.getChildren().remove(client_tank);

                    }
                    c_heart--;
                    map.setClick(0);
                }

            });
        }).start();





//        client_tank = new Tank.Into(client_x, client_y ,map);
//        Image img_bot = new Image(new FileInputStream("C:\\Users\\User\\Desktop\\Типа мипа\\tank-enemy.jpg"));
//        client_tank = new ImageView(img_bot);
//        client_tank.setX(WIDTH + 50*bot_x);
//        client_tank.setY(HEIGHT + 50*bot_y);
//        client_tank.setFitHeight(Bot_Width_1);
//        client_tank.setFitWidth(Bot_Height_1);
//

//        getChildren().add(imgv_bot);

//        p.getChildren().add(bot);

        p.getChildren().add(into);
        new Thread( ()-> {
            into.setOnKeyPressed( e -> {
                into.move_tank(e.getCode(), count, client_tank);

                System.out.println("click: " + map.getClick());
                System.out.println("Halai");
                if (map.getClick() == 1){
                    System.out.println("ho");
                    if (heart == 0){
                        System.out.println("solo");
                        map.setValueAt((int)into.tank_cor_x, (int)into.tank_cor_y, "0");
                        p.getChildren().remove(into);

                    }
                    heart--;
                    map.setClick(0);
                }
            });

        }).start();





        Scene scene = new Scene(p, 1000, 800);
        primaryStage.setTitle("Project_2"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show();
//        text.requestFocus();

        repeatFocus(client_tank);
        repeatFocus(into);



    }

    private void repeatFocus(Tank.Into node) {
        Platform.runLater(() -> {
            if (!node.isFocused()) {
                node.requestFocus();
                repeatFocus(node);
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    public Map getMap() {
        return map;
    }

    public Pane getP() {
        return p;
    }

    public static void main(String[] args) {
        launch(args);
    }
    public void delete(int x, int y) throws FileNotFoundException {
        map.setValueAt(x,y, "0");
        System.out.println();
        System.out.println();
        System.out.println("change");
        map.print();

        draw(p, map);
    }

    public void draw(Pane p, Map map) throws FileNotFoundException {
        for (int i=0; i< size; i++){
            for (int j = 0; j< size; j++){
                Image S = new Image(new FileInputStream("C:\\Users\\User\\Desktop\\Project_2 photo\\steel.jpg"));
                ImageView S_ph = new ImageView(S);

                S_ph.setFitHeight(block_HEIGHT);
                S_ph.setFitWidth(block_WIDTH);

                Image B = new Image(new FileInputStream("C:\\Users\\User\\Desktop\\Project_2 photo\\brick.jpg"));
                ImageView B_ph = new ImageView(B);

                B_ph.setFitHeight(block_HEIGHT);
                B_ph.setFitWidth(block_WIDTH);

                Image W = new Image(new FileInputStream("C:\\Users\\User\\Desktop\\Project_2 photo\\water.jpg"));
                ImageView W_ph = new ImageView(W);

                W_ph.setFitHeight(block_HEIGHT);
                W_ph.setFitWidth(block_WIDTH);

                Image T = new Image(new FileInputStream("C:\\Users\\User\\Desktop\\Project_2 photo\\tree.jpg"));
                ImageView T_ph = new ImageView(T);

                T_ph.setFitHeight(block_HEIGHT);
                T_ph.setFitWidth(block_WIDTH);

                S_ph.setX(main_rec_X + block_WIDTH*i);
                S_ph.setY(main_rec_Y + block_HEIGHT*j);

                B_ph.setX(main_rec_X + block_WIDTH*i);
                B_ph.setY(main_rec_Y + block_HEIGHT*j);

                W_ph.setX(main_rec_X + block_WIDTH*i);
                W_ph.setY(main_rec_Y + block_HEIGHT*j);

                T_ph.setX(main_rec_X + block_WIDTH*i);
                T_ph.setY(main_rec_Y + block_HEIGHT*j);


                Rectangle unit = new Rectangle(main_rec_X + block_WIDTH*i, main_rec_Y + block_HEIGHT*j, block_HEIGHT, block_WIDTH);

                String letter = String.valueOf(map.getValueAt(i,j));


                switch (letter) {
                    case ("0"):
                        unit.setFill(Color.BLACK);
                        p.getChildren().add(unit);
                        break;
                    case "P":
                        into = new Tank.Into(i, j ,map);
//                        p.getChildren().add(into);
                        break;
                    case "S":
                        p.getChildren().add(S_ph);
                        break;
                    case "B":
                        p.getChildren().add(B_ph);
                        break;
                    case "W":
                        p.getChildren().add(W_ph);
                        break;
                    case("T"):
                        p.getChildren().add(T_ph);
                        break;
                }
            }
        }
    }


//    @Override
//    public void run() {
//        try {
//            client_tank = new Tank.Into(client_x, client_y ,map);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        p.getChildren().add(client_tank);
//        client_tank.setOnKeyPressed( e -> { client_tank.move_tank(e.getCode(), count); });
//
//        client_tank.requestFocus();
//    }
}





abstract class Tank extends MyPlayer implements Serializable {


//    public abstract void move_tank(String code, int[][] count);

    static class Into extends Pane{
        private int x;
        private int y;
        private ImageView t_ph;
        public int HEIGHT = 100;
        public int WIDTH = 100;
        public int tank_width = 40;
        public int tank_height = 40;
        public int block_HEIGHT = 50;
        public int block_WIDTH = 50;
        public String direction = "up";

        private double arr_x_1;
        private double arr_y_1;
        private double arr_x_2;
        private double arr_y_2;

        private double arr_c_x_1;
        private double arr_c_y_1;
        private double arr_c_x_2;
        private double arr_c_y_2;

        public double tank_cor_x;
        public double tank_cor_y;


        //        private String dirttion = "down";
        private double bullet_x;
        private double bullet_y;
        private Timeline animation;

        private int dx=1;
        private int dy=1;

        private Circle b = new Circle(bullet_x, bullet_y, 10);

        public static int[][] count;

        Map map;

        public int heart = 3;
        public int c_heart = 3;




        public Into(int x, int y, Map map) throws FileNotFoundException {
            Image t = new Image(new FileInputStream("C:\\Users\\User\\Downloads\\tank.jpeg"));
            t_ph = new ImageView(t);

            t_ph.setX(WIDTH + 50*x);
            t_ph.setY(HEIGHT + 50*y);
            t_ph.setFitHeight(tank_height);
            t_ph.setFitWidth(tank_width);


            this.x = x;
            this.y = y;
            this.map = map;

            bullet_x = t_ph.getX() + tank_width/2;
            bullet_y = t_ph.getY();


            getChildren().add(t_ph);
            getChildren().add(b);
            activate_constant();
        }

        private void activate_constant() {
            arr_x_1 = (t_ph.getX()-WIDTH)/block_WIDTH;
            arr_y_1 = (t_ph.getY()-HEIGHT)/block_HEIGHT;
            arr_x_2 = (t_ph.getX()-WIDTH + tank_width)/block_WIDTH;
            arr_y_2 = (t_ph.getY()-HEIGHT + tank_height)/block_HEIGHT;

            tank_cor_x = (arr_x_1 + arr_x_2)/2;
            tank_cor_y = (arr_y_1 + arr_y_2)/2;

            arr_c_x_1 = (t_ph.getX()-WIDTH)/block_WIDTH;
            arr_c_y_1 = (t_ph.getY()-HEIGHT)/block_HEIGHT;
            arr_c_x_2 = (t_ph.getX()-WIDTH + tank_width)/block_WIDTH;
            arr_c_y_2 = (t_ph.getY()-HEIGHT + tank_height)/block_HEIGHT;
//            if((arr_x_1 + arr_x_2)/2)
        }

        public ImageView getT_ph() {
            return t_ph;
        }

        public void move_tank(KeyCode code, int[][] count, Tank.Into client_tank) {
            activate_constant();
            this.count = count;
            System.out.println();
            System.out.print  ("(" + arr_x_1 + " ; " + arr_y_1 + ") ");
            System.out.println("(" + arr_x_2 + " ; " + arr_y_2 + ") ");
            System.out.println();

            System.out.println("tank_cor_x: "+ tank_cor_x);
            System.out.println("tank_cor_y: "+ tank_cor_y);



//            System.out.println();
//            System.out.println(t_ph.getX());
//            System.out.println(t_ph.getY());


            switch (code){
                case RIGHT:
//                    String r1 = String.valueOf( map.getValueAt((int)arr_x_1 + 1, (int)arr_y_1) );
//                    String r2 = String.valueOf( map.getValueAt((int)arr_x_1 + 1, (int)arr_y_2) );
                    direction = "right";
                    bullet_x = t_ph.getX() + tank_width;
                    bullet_y = t_ph.getY() + tank_width/2;
//                    if ( (int)(tank_cor_x+0.1) - (int)tank_cor_x > 0){
//                        map.setValueAt((int)tank_cor_y, (int)tank_cor_x, "P");
//                        map.setValueAt((int)tank_cor_y, (int)tank_cor_x-1, "0");
//                        map.print();
//                        map.print();
//                    }
                    map.setValueAt((int)tank_cor_x, (int)tank_cor_y, "P");
                    if((int)tank_cor_x == 0  ){
                        map.setValueAt((int)tank_cor_x, (int)tank_cor_y, "P");
                    }else if ( Character.compare( map.getValueAt( (int)tank_cor_x-1, (int)tank_cor_y), 'P' ) ==0  ){
                        map.setValueAt((int)tank_cor_x-1, (int)tank_cor_y, "0");
                        map.print();
                    }


                    if((int)arr_x_1 == map.getSize() - 1){
                        if( t_ph.getX() - WIDTH - block_WIDTH*map.getSize() +tank_width < 0 ){
                            this.t_ph.setX(t_ph.getX() + 5);
                        }
                    }
//                    else if (client_x - tank_width > t_ph.getX()){
//                        if( client_x - tank_width - t_ph.getX() <= 0 ){
//                            System.out.println("client");
//                            this.t_ph.setX(t_ph.getX() + 0);
//                        }
//                    }
                    else if ( Character.compare( map.getValueAt((int)arr_x_1 + 1, (int)arr_y_1) , 'C' ) == 0 || Character.compare( map.getValueAt((int)arr_x_1 + 1, (int)arr_y_2) , 'C' ) == 0 ){
                        if( t_ph.getX() - WIDTH - block_WIDTH*( (int)arr_x_1 + 1 ) +tank_width < 0 ){
                            this.t_ph.setX(t_ph.getX() + 5);
                        }
                    }

                    else if ( Character.compare( map.getValueAt((int)arr_x_1 + 1, (int)arr_y_1) , 'S' ) == 0 || Character.compare( map.getValueAt((int)arr_x_1 + 1, (int)arr_y_2) , 'S' ) == 0 ){
                        if( t_ph.getX() - WIDTH - block_WIDTH*( (int)arr_x_1 + 1 ) +tank_width < 0 ){
                            this.t_ph.setX(t_ph.getX() + 5);
                        }
                    }
                    else if ( Character.compare( map.getValueAt((int)arr_x_1 + 1, (int)arr_y_1) , 'B' ) == 0 || Character.compare( map.getValueAt((int)arr_x_1 + 1, (int)arr_y_2) , 'B' ) == 0 ){
                        if( t_ph.getX() - WIDTH - block_WIDTH*( (int)arr_x_1 + 1 ) +tank_width < 0 ){
                            this.t_ph.setX(t_ph.getX() + 5);
                        }
                    }
                    else if ( Character.compare( map.getValueAt((int)arr_x_1 + 1, (int)arr_y_1) , 'W' ) == 0 || Character.compare( map.getValueAt((int)arr_x_1 + 1, (int)arr_y_2) , 'W' ) == 0 ){
                        if( t_ph.getX() - WIDTH - block_WIDTH*( (int)arr_x_1 + 1 ) +tank_width < 0 ){
                            this.t_ph.setX(t_ph.getX() + 5);
                        }
                    }
                    else if ( Character.compare( map.getValueAt((int)arr_x_1 + 1, (int)arr_y_1) , 'T' ) == 0 ){
                        Image trees_into_4 = null;
                        try {
                            trees_into_4 = new Image(new FileInputStream("C:\\Users\\User\\Desktop\\Project_2 photo\\tree.jpg"));
                        } catch (FileNotFoundException fileNotFoundException) {
                            fileNotFoundException.printStackTrace();
                        }
                        ImageView trees_img_4 = new ImageView(trees_into_4);

                        trees_img_4.setX(WIDTH + block_WIDTH*((int)arr_x_1 + 1));
                        trees_img_4.setY(HEIGHT + block_HEIGHT*((int)arr_y_1));

                        trees_img_4.setFitHeight(50);
                        trees_img_4.setFitWidth(50);


                        getChildren().add(trees_img_4);

                        t_ph.setX(t_ph.getX() + 5);
                    }

                    else if( t_ph.getX() - WIDTH - block_WIDTH*(int)map.getSize() +tank_width < 0 ){
                        this.t_ph.setX(t_ph.getX() + 5);
                    }

                    t_ph.setRotate(90);
                    break;
                case LEFT:
//                    System.out.println("L");
//                    String l1 = String.valueOf( map.getValueAt((int)arr_x_1 - 1, (int)arr_y_1) );
//                    String l2 = String.valueOf( map.getValueAt((int)arr_x_1 - 1, (int)arr_y_2) );
                    direction = "left";
                    bullet_x = t_ph.getX();
                    bullet_y = t_ph.getY() + tank_width/2;

//                    if ( (int)(tank_cor_x-0.1) - (int)tank_cor_x < 0 ){
//                        map.setValueAt((int)tank_cor_x, (int)tank_cor_y, "P");
//                        map.setValueAt((int)tank_cor_x+1, (int)tank_cor_y, "0");
//                        map.print(    );
//                    }
                    map.setValueAt((int)tank_cor_x, (int)tank_cor_y, "P");
                    if((int)tank_cor_x == map.getSize()-1  ){
                        map.setValueAt((int)tank_cor_x, (int)tank_cor_y, "P");
                    }else if ( Character.compare( map.getValueAt( (int)tank_cor_x+1, (int)tank_cor_y), 'P' ) ==0  ){
                        map.setValueAt((int)tank_cor_x+1, (int)tank_cor_y, "0");
                        map.print();
                    }

                    System.out.println();

                    if((int)arr_x_1 == 0){
                        if( t_ph.getX() - WIDTH > 0 ){
                            this.t_ph.setX(t_ph.getX() - 5);
                        }
                    }

//                    else if(  t_ph.getX() - client_x - tank_width  <= 0 ){
//                        System.out.println("client");
//                        this.t_ph.setX(t_ph.getX() + 0);
//                    }
//                    else if (client_x < t_ph.getX()){
//                        if( t_ph.getX() + tank_width - client_x >= 0 ){
//                            System.out.println("client");
//                            this.t_ph.setX(t_ph.getX() + 0);
//                        }
//                    }
                    else if (Character.compare( map.getValueAt((int)arr_x_1 - 1, (int)arr_y_1) , 'C' ) == 0 || Character.compare(map.getValueAt((int)arr_x_1 - 1, (int)arr_y_2) , 'C' ) == 0){
                        if( t_ph.getX() - WIDTH - block_WIDTH * (int)arr_x_1 > 0 ){
                            this.t_ph.setX(t_ph.getX() - 5);
                        }
                    }

                    else if (Character.compare( map.getValueAt((int)arr_x_1 - 1, (int)arr_y_1) , 'S' ) == 0 || Character.compare(map.getValueAt((int)arr_x_1 - 1, (int)arr_y_2) , 'S' ) == 0){
                        if( t_ph.getX() - WIDTH - block_WIDTH * (int)arr_x_1 > 0 ){
                            this.t_ph.setX(t_ph.getX() - 5);
                        }
                    }
                    else if (Character.compare( map.getValueAt((int)arr_x_1 - 1, (int)arr_y_1) , 'B' ) == 0 || Character.compare(map.getValueAt((int)arr_x_1 - 1, (int)arr_y_2) , 'B' ) == 0){
                        if( t_ph.getX() - WIDTH - block_WIDTH * (int)arr_x_1 > 0 ){
                            this.t_ph.setX(t_ph.getX() - 5);
                        }
                    }
                    else if (Character.compare( map.getValueAt((int)arr_x_1 - 1, (int)arr_y_1) , 'W' ) == 0 || Character.compare(map.getValueAt((int)arr_x_1 - 1, (int)arr_y_2) , 'W' ) == 0){
                        if( t_ph.getX() - WIDTH - block_WIDTH * (int)arr_x_1 > 0 ){
                            this.t_ph.setX(t_ph.getX() - 5);
                        }
                    }
                    else if (Character.compare( map.getValueAt((int)arr_x_1 - 1, (int)arr_y_1) , 'T' ) == 0 ){
                        Image trees_into_3 = null;
                        try {
                            trees_into_3 = new Image(new FileInputStream("C:\\Users\\User\\Desktop\\Project_2 photo\\tree.jpg"));
                        } catch (FileNotFoundException fileNotFoundException) {
                            fileNotFoundException.printStackTrace();
                        }
                        ImageView trees_img_3 = new ImageView(trees_into_3);

                        trees_img_3.setX(WIDTH + block_WIDTH*((int)arr_x_1 - 1));
                        trees_img_3.setY(HEIGHT + block_HEIGHT*((int)arr_y_1));

                        trees_img_3.setFitHeight(50);
                        trees_img_3.setFitWidth(50);


                        getChildren().add(trees_img_3);

                        t_ph.setX(t_ph.getX() - 5);

                    }
                    else if( t_ph.getX() - WIDTH > 0 )
                        this.t_ph.setX(t_ph.getX() - 5);

                    t_ph.setRotate(270);

                    break;
                case DOWN:
//                    System.out.println("D");
//                    String d1 = String.valueOf( map.getValueAt((int)arr_x_1, (int)arr_y_1 + 1) );
//                    String d2 = String.valueOf( map.getValueAt((int)arr_x_2, (int)arr_y_1 + 1) );
                    direction = "down";
                    bullet_x = t_ph.getX() + tank_width/2;
                    bullet_y = t_ph.getY() + tank_height;

                    map.setValueAt((int)tank_cor_x, (int)tank_cor_y, "P");
                    if((int)tank_cor_y == 0  ){
                        map.setValueAt((int)tank_cor_x, (int)tank_cor_y, "P");
                    }
                    else if ( Character.compare( map.getValueAt( (int)tank_cor_x, (int)tank_cor_y-1), 'P' ) ==0  ){
                        map.setValueAt((int)tank_cor_x, (int)tank_cor_y-1, "0");
                        map.print();
                    }

                    if((int)arr_y_1 == map.getSize() - 1){
                        if( t_ph.getY() - HEIGHT - block_HEIGHT*map.getSize() + tank_width < 0 ){
                            this.t_ph.setY(t_ph.getY() + 5);
                        }
                    }
                    else if ( Character.compare( map.getValueAt((int)arr_x_1, (int)arr_y_1 + 1) , 'C' ) == 0 || Character.compare( map.getValueAt((int)arr_x_2, (int)arr_y_1 + 1) , 'C' ) == 0 ){
                        System.out.println("Down");
                        if( (t_ph.getY() - HEIGHT - block_HEIGHT*((int)arr_y_1 +1) + tank_width) < 0 ){
                            this.t_ph.setY(t_ph.getY() + 5);
                        }
                    }
                    else if ( Character.compare( map.getValueAt((int)arr_x_1, (int)arr_y_1 + 1) , 'S' ) == 0 || Character.compare( map.getValueAt((int)arr_x_2, (int)arr_y_1 + 1) , 'S' ) == 0 ){
                        System.out.println("Down");
                        if( (t_ph.getY() - HEIGHT - block_HEIGHT*((int)arr_y_1 +1) + tank_width) < 0 ){
                            this.t_ph.setY(t_ph.getY() + 5);
                        }
                    }
//                    else if ( Character.compare( map.getValueAt((int)arr_x_1, (int)arr_y_1 + 1) , 'P' ) == 0 || Character.compare( map.getValueAt((int)arr_x_2, (int)arr_y_1 + 1) , 'P' ) == 0 ){
//                        System.out.println("Down");
//                        if( (t_ph.getY() - HEIGHT - block_HEIGHT*((int)arr_y_1 +1) + tank_width) < 0 ){
//                            this.t_ph.setY(t_ph.getY() + 5);
//                        }
//                    }
                    else if ( Character.compare( map.getValueAt((int)arr_x_1, (int)arr_y_1 + 1) , 'B' ) == 0 || Character.compare( map.getValueAt((int)arr_x_2, (int)arr_y_1 + 1) , 'B' ) == 0 ){
                        System.out.println("Down");
                        if( (t_ph.getY() - HEIGHT - block_HEIGHT*((int)arr_y_1 +1) + tank_width) < 0 ){
                            this.t_ph.setY(t_ph.getY() + 5);
                        }
                    }
                    else if ( Character.compare( map.getValueAt((int)arr_x_1, (int)arr_y_1 + 1) , 'W' ) == 0 || Character.compare( map.getValueAt((int)arr_x_2, (int)arr_y_1 + 1) , 'W' ) == 0 ){
                        System.out.println("Down");
                        if( (t_ph.getY() - HEIGHT - block_HEIGHT*((int)arr_y_1 +1) + tank_width) < 0 ){
                            this.t_ph.setY(t_ph.getY() + 5);
                        }
                    }
                    else if ( Character.compare( map.getValueAt((int)arr_x_1, (int)arr_y_1 + 1) , 'T' ) == 0){
                        Image trees_into_2 = null;
                        try {
                            trees_into_2 = new Image(new FileInputStream("C:\\Users\\User\\Desktop\\Project_2 photo\\tree.jpg"));
                        } catch (FileNotFoundException fileNotFoundException) {
                            fileNotFoundException.printStackTrace();
                        }
                        ImageView trees_img_2 = new ImageView(trees_into_2);

                        trees_img_2.setX(WIDTH + block_WIDTH*((int)arr_x_1 ));
                        trees_img_2.setY(HEIGHT + block_HEIGHT*((int)arr_y_1 + 1));

                        trees_img_2.setFitHeight(50);
                        trees_img_2.setFitWidth(50);


                        getChildren().add(trees_img_2);

                        t_ph.setY(t_ph.getY() + 5);
                    }
                    else if( t_ph.getY() - WIDTH - block_HEIGHT*map.getSize() + tank_width < 0 )
                        this.t_ph.setY(t_ph.getY() + 5);

                    t_ph.setRotate(180);

                    break;
                case UP:
//                    System.out.println("U");

                    direction = "up";
                    bullet_x = t_ph.getX() + tank_width/2;
                    bullet_y = t_ph.getY();

                    map.setValueAt((int)tank_cor_x, (int)tank_cor_y, "P");
                    if((int)tank_cor_y == map.getSize() - 1){
                        map.setValueAt((int)tank_cor_x, (int)tank_cor_y, "P");
                    }else if ( Character.compare( map.getValueAt( (int)tank_cor_x, (int)tank_cor_y+1), 'P' ) ==0  ){
                        map.setValueAt((int)tank_cor_x, (int)tank_cor_y+1, "0");
                        map.print();
                    }

                    if((int)arr_y_1 == 0){
                        if( t_ph.getY() - WIDTH > 0 ){
                            this.t_ph.setY(t_ph.getY() - 5);
                        }
                    }
                    else if (Character.compare( map.getValueAt((int)arr_x_1 , (int)arr_y_1 - 1) , 'S' ) == 0 || Character.compare(map.getValueAt((int)arr_x_2, (int)arr_y_1 - 1) , 'S' ) == 0){
                        System.out.println("S");
                        if( t_ph.getY() - HEIGHT - block_HEIGHT*(int)arr_y_1 > 0 ){
                            this.t_ph.setY(t_ph.getY() - 5);
                        }
                    }
                    else if (Character.compare( map.getValueAt((int)arr_x_1 , (int)arr_y_1 - 1) , 'C' ) == 0 || Character.compare(map.getValueAt((int)arr_x_2, (int)arr_y_1 - 1) , 'C' ) == 0){
                        System.out.println("P");
                        if( t_ph.getY() - HEIGHT - block_HEIGHT*(int)arr_y_1 > 0 ){
                            this.t_ph.setY(t_ph.getY() - 5);
                        }
                    }
                    else if (Character.compare( map.getValueAt((int)arr_x_1 , (int)arr_y_1 - 1) , 'B' ) == 0 || Character.compare(map.getValueAt((int)arr_x_2, (int)arr_y_1 - 1) , 'B' ) == 0){
                        if( t_ph.getY() - WIDTH - block_WIDTH*((int)arr_y_1) > 0 ){
                            this.t_ph.setY(t_ph.getY() - 5);
                        }
                    }
                    else if (Character.compare( map.getValueAt((int)arr_x_1 , (int)arr_y_1 - 1) , 'W' ) == 0 || Character.compare(map.getValueAt((int)arr_x_2, (int)arr_y_1 - 1) , 'W' ) == 0){
                        System.out.println("W");
                        if( t_ph.getY() - WIDTH - block_WIDTH*(int)arr_y_1 > 0 ){
                            this.t_ph.setY(t_ph.getY() - 5);
                        }
                    }
                    else if (Character.compare( map.getValueAt((int)arr_x_1 , (int)arr_y_1 - 1) , 'T' ) == 0){
                        Image trees_into_1 = null;
                        try {
                            trees_into_1 = new Image(new FileInputStream("C:\\Users\\User\\Desktop\\Project_2 photo\\tree.jpg"));
                        } catch (FileNotFoundException fileNotFoundException) {
                            fileNotFoundException.printStackTrace();
                        }
                        ImageView trees_img_1 = new ImageView(trees_into_1);

                        trees_img_1.setX(WIDTH + block_WIDTH*((int)arr_x_1));
                        trees_img_1.setY(HEIGHT + block_HEIGHT*((int)arr_y_1 - 1));

                        trees_img_1.setFitHeight(50);
                        trees_img_1.setFitWidth(50);


                        getChildren().add(trees_img_1);

                        t_ph.setY(t_ph.getY() - 5);
                    }
                    else if( t_ph.getY() - WIDTH > 0 )
                        this.t_ph.setY(t_ph.getY() - 5);

                    t_ph.setRotate(0);

                    break;

                case SPACE:
                    System.out.println("Space");
                    Bullet bullet = new Bullet(bullet_x, bullet_y, direction, map, count, "P");
//                    count[bul_ex_x][bullet_ex_y]++;
                    System.out.println("Here");
                    for (int i=0; i<map.getSize(); i++){
                        for (int j=0; j<map.getSize(); j++){
                            System.out.print(map.arena[i][j] + " ");
                            if ( map.arena[ i ][ j ] >= 4){
                                System.out.println(i + " " + j);
                                System.out.println("on_into");
                                map.setValueAt( j, i, "0" );
                                Rectangle re = new Rectangle(WIDTH + j*block_WIDTH, WIDTH + i*block_WIDTH, block_WIDTH, block_WIDTH);
                                re.setFill(Color.BLACK);
                                re.opacityProperty();
                                getChildren().add( re );
                                System.out.println("kort");
                            }
                        }
                        System.out.println();
                    }
//                    System.out.println("Halai");
//                    if (map.getClick() == 1){
//                        System.out.println("ho");
//                        if (heart == 3){
//                            System.out.println("solo");
//                            getChildren().remove(client_tank);
//                        }
//                        heart--;
//                        map.setClick(0);
//                    }





                    getChildren().add(bullet);

                    getChildren().remove(t_ph);

                    getChildren().add(t_ph);
                    break;
            }
        }



        public void move_client_tank(KeyCode code, int[][] count) {
            activate_constant();
            this.count = count;
//            System.out.print  ("(" + arr_x_1 + " ; " + arr_y_1 + ") ");
//            System.out.println("(" + arr_x_2 + " ; " + arr_y_2 + ") ");
//            System.out.println();
//            System.out.println(t_ph.getX());
//            System.out.println(t_ph.getY());


            switch (code){
                case D:
//                    String r1 = String.valueOf( map.getValueAt((int)arr_x_1 + 1, (int)arr_y_1) );
//                    String r2 = String.valueOf( map.getValueAt((int)arr_x_1 + 1, (int)arr_y_2) );
                    direction = "right";
                    bullet_x = t_ph.getX() + tank_width;
                    bullet_y = t_ph.getY() + tank_width/2;

                    map.setValueAt((int)tank_cor_x, (int)tank_cor_y, "C");
                    if((int)tank_cor_x == 0  ){
                        map.setValueAt((int)tank_cor_x, (int)tank_cor_y, "C");
                    }else if ( Character.compare( map.getValueAt( (int)tank_cor_x-1, (int)tank_cor_y), 'C' ) ==0  ){
                        map.setValueAt((int)tank_cor_x-1, (int)tank_cor_y, "0");
                        map.print();
                    }

                    if((int)arr_x_1 == map.getSize() - 1){
                        if( t_ph.getX() - WIDTH - block_WIDTH*map.getSize() +tank_width < 0 ){
                            this.t_ph.setX(t_ph.getX() + 5);
                        }
                    }
                    else if ( Character.compare( map.getValueAt((int)arr_x_1 + 1, (int)arr_y_1) , 'S' ) == 0 || Character.compare( map.getValueAt((int)arr_x_1 + 1, (int)arr_y_2) , 'S' ) == 0 ){
                        if( t_ph.getX() - WIDTH - block_WIDTH*( (int)arr_x_1 + 1 ) +tank_width < 0 ){
                            this.t_ph.setX(t_ph.getX() + 5);
                        }
                    }
                    else if ( Character.compare( map.getValueAt((int)arr_x_1 + 1, (int)arr_y_1) , 'P' ) == 0 || Character.compare( map.getValueAt((int)arr_x_1 + 1, (int)arr_y_2) , 'P' ) == 0 ){
                        if( t_ph.getX() - WIDTH - block_WIDTH*( (int)arr_x_1 + 1 ) +tank_width < 0 ){
                            this.t_ph.setX(t_ph.getX() + 5);
                        }
                    }
                    else if ( Character.compare( map.getValueAt((int)arr_x_1 + 1, (int)arr_y_1) , 'B' ) == 0 || Character.compare( map.getValueAt((int)arr_x_1 + 1, (int)arr_y_2) , 'B' ) == 0 ){
                        if( t_ph.getX() - WIDTH - block_WIDTH*( (int)arr_x_1 + 1 ) +tank_width < 0 ){
                            this.t_ph.setX(t_ph.getX() + 5);
                        }
                    }
                    else if ( Character.compare( map.getValueAt((int)arr_x_1 + 1, (int)arr_y_1) , 'W' ) == 0 || Character.compare( map.getValueAt((int)arr_x_1 + 1, (int)arr_y_2) , 'W' ) == 0 ){
                        if( t_ph.getX() - WIDTH - block_WIDTH*( (int)arr_x_1 + 1 ) +tank_width < 0 ){
                            this.t_ph.setX(t_ph.getX() + 5);
                        }
                    }
                    else if ( Character.compare( map.getValueAt((int)arr_x_1 + 1, (int)arr_y_1) , 'T' ) == 0 ){
                        Image trees_into_4 = null;
                        try {
                            trees_into_4 = new Image(new FileInputStream("C:\\Users\\User\\Desktop\\Project_2 photo\\tree.jpg"));
                        } catch (FileNotFoundException fileNotFoundException) {
                            fileNotFoundException.printStackTrace();
                        }
                        ImageView trees_img_4 = new ImageView(trees_into_4);

                        trees_img_4.setX(WIDTH + block_WIDTH*((int)arr_x_1 + 1));
                        trees_img_4.setY(HEIGHT + block_HEIGHT*((int)arr_y_1));

                        trees_img_4.setFitHeight(50);
                        trees_img_4.setFitWidth(50);


                        getChildren().add(trees_img_4);

                        t_ph.setX(t_ph.getX() + 5);
                    }

                    else if( t_ph.getX() - WIDTH - block_WIDTH*(int)map.getSize() +tank_width < 0 ){
                        this.t_ph.setX(t_ph.getX() + 5);
                    }

                    t_ph.setRotate(90);
                    break;
                case A:
//                    System.out.println("L");
//                    String l1 = String.valueOf( map.getValueAt((int)arr_x_1 - 1, (int)arr_y_1) );
//                    String l2 = String.valueOf( map.getValueAt((int)arr_x_1 - 1, (int)arr_y_2) );
                    direction = "left";
                    bullet_x = t_ph.getX();
                    bullet_y = t_ph.getY() + tank_width/2;

                    map.setValueAt((int)tank_cor_x, (int)tank_cor_y, "C");
                    if((int)tank_cor_x == map.getSize()-1  ){
                        map.setValueAt((int)tank_cor_x, (int)tank_cor_y, "C");
                    }else if ( Character.compare( map.getValueAt( (int)tank_cor_x+1, (int)tank_cor_y), 'C' ) ==0  ){
                        map.setValueAt((int)tank_cor_x+1, (int)tank_cor_y, "0");
                        map.print();
                    }

                    if((int)arr_x_1 == 0){
                        if( t_ph.getX() - WIDTH > 0 ){
                            this.t_ph.setX(t_ph.getX() - 5);
                        }
                    }
                    else if (Character.compare( map.getValueAt((int)arr_x_1 - 1, (int)arr_y_1) , 'S' ) == 0 || Character.compare(map.getValueAt((int)arr_x_1 - 1, (int)arr_y_2) , 'S' ) == 0){
                        if( t_ph.getX() - WIDTH - block_WIDTH * (int)arr_x_1 > 0 ){
                            this.t_ph.setX(t_ph.getX() - 5);
                        }
                    }
                    else if (Character.compare( map.getValueAt((int)arr_x_1 - 1, (int)arr_y_1) , 'P' ) == 0 || Character.compare(map.getValueAt((int)arr_x_1 - 1, (int)arr_y_2) , 'P' ) == 0){
                        if( t_ph.getX() - WIDTH - block_WIDTH * (int)arr_x_1 > 0 ){
                            this.t_ph.setX(t_ph.getX() - 5);
                        }
                    }
                    else if (Character.compare( map.getValueAt((int)arr_x_1 - 1, (int)arr_y_1) , 'B' ) == 0 || Character.compare(map.getValueAt((int)arr_x_1 - 1, (int)arr_y_2) , 'B' ) == 0){
                        if( t_ph.getX() - WIDTH - block_WIDTH * (int)arr_x_1 > 0 ){
                            this.t_ph.setX(t_ph.getX() - 5);
                        }
                    }
                    else if (Character.compare( map.getValueAt((int)arr_x_1 - 1, (int)arr_y_1) , 'W' ) == 0 || Character.compare(map.getValueAt((int)arr_x_1 - 1, (int)arr_y_2) , 'W' ) == 0){
                        if( t_ph.getX() - WIDTH - block_WIDTH * (int)arr_x_1 > 0 ){
                            this.t_ph.setX(t_ph.getX() - 5);
                        }
                    }
                    else if (Character.compare( map.getValueAt((int)arr_x_1 - 1, (int)arr_y_1) , 'T' ) == 0 ){
                        Image trees_into_3 = null;
                        try {
                            trees_into_3 = new Image(new FileInputStream("C:\\Users\\User\\Desktop\\Project_2 photo\\tree.jpg"));
                        } catch (FileNotFoundException fileNotFoundException) {
                            fileNotFoundException.printStackTrace();
                        }
                        ImageView trees_img_3 = new ImageView(trees_into_3);

                        trees_img_3.setX(WIDTH + block_WIDTH*((int)arr_x_1 - 1));
                        trees_img_3.setY(HEIGHT + block_HEIGHT*((int)arr_y_1));

                        trees_img_3.setFitHeight(50);
                        trees_img_3.setFitWidth(50);


                        getChildren().add(trees_img_3);

                        t_ph.setX(t_ph.getX() - 5);

                    }
                    else if( t_ph.getX() - WIDTH > 0 )
                        this.t_ph.setX(t_ph.getX() - 5);

                    t_ph.setRotate(270);

                    break;
                case S:
//                    System.out.println("D");
//                    String d1 = String.valueOf( map.getValueAt((int)arr_x_1, (int)arr_y_1 + 1) );
//                    String d2 = String.valueOf( map.getValueAt((int)arr_x_2, (int)arr_y_1 + 1) );
                    direction = "down";
                    bullet_x = t_ph.getX() + tank_width/2;
                    bullet_y = t_ph.getY() + tank_height;

                    map.setValueAt((int)tank_cor_x, (int)tank_cor_y, "C");
                    if((int)tank_cor_y == 0  ){
                        map.setValueAt((int)tank_cor_x, (int)tank_cor_y, "C");
                    }
                    else if ( Character.compare( map.getValueAt( (int)tank_cor_x, (int)tank_cor_y-1), 'C' ) ==0  ){
                        map.setValueAt((int)tank_cor_x, (int)tank_cor_y-1, "0");
                        map.print();
                    }

                    if((int)arr_y_1 == map.getSize() - 1){
                        if( t_ph.getY() - HEIGHT - block_HEIGHT*map.getSize() + tank_width < 0 ){
                            this.t_ph.setY(t_ph.getY() + 5);
                        }
                    }
                    else if ( Character.compare( map.getValueAt((int)arr_x_1, (int)arr_y_1 + 1) , 'S' ) == 0 || Character.compare( map.getValueAt((int)arr_x_2, (int)arr_y_1 + 1) , 'S' ) == 0 ){
                        System.out.println("Down");
                        if( (t_ph.getY() - HEIGHT - block_HEIGHT*((int)arr_y_1 +1) + tank_width) < 0 ){
                            this.t_ph.setY(t_ph.getY() + 5);
                        }
                    }
                    else if ( Character.compare( map.getValueAt((int)arr_x_1, (int)arr_y_1 + 1) , 'P' ) == 0 || Character.compare( map.getValueAt((int)arr_x_2, (int)arr_y_1 + 1) , 'P' ) == 0 ){
                        System.out.println("Down");
                        if( (t_ph.getY() - HEIGHT - block_HEIGHT*((int)arr_y_1 +1) + tank_width) < 0 ){
                            this.t_ph.setY(t_ph.getY() + 5);
                        }
                    }
                    else if ( Character.compare( map.getValueAt((int)arr_x_1, (int)arr_y_1 + 1) , 'B' ) == 0 || Character.compare( map.getValueAt((int)arr_x_2, (int)arr_y_1 + 1) , 'B' ) == 0 ){
                        System.out.println("Down");
                        if( (t_ph.getY() - HEIGHT - block_HEIGHT*((int)arr_y_1 +1) + tank_width) < 0 ){
                            this.t_ph.setY(t_ph.getY() + 5);
                        }
                    }
                    else if ( Character.compare( map.getValueAt((int)arr_x_1, (int)arr_y_1 + 1) , 'W' ) == 0 || Character.compare( map.getValueAt((int)arr_x_2, (int)arr_y_1 + 1) , 'W' ) == 0 ){
                        System.out.println("Down");
                        if( (t_ph.getY() - HEIGHT - block_HEIGHT*((int)arr_y_1 +1) + tank_width) < 0 ){
                            this.t_ph.setY(t_ph.getY() + 5);
                        }
                    }
                    else if ( Character.compare( map.getValueAt((int)arr_x_1, (int)arr_y_1 + 1) , 'T' ) == 0){
                        Image trees_into_2 = null;
                        try {
                            trees_into_2 = new Image(new FileInputStream("C:\\Users\\User\\Desktop\\Project_2 photo\\tree.jpg"));
                        } catch (FileNotFoundException fileNotFoundException) {
                            fileNotFoundException.printStackTrace();
                        }
                        ImageView trees_img_2 = new ImageView(trees_into_2);

                        trees_img_2.setX(WIDTH + block_WIDTH*((int)arr_x_1 ));
                        trees_img_2.setY(HEIGHT + block_HEIGHT*((int)arr_y_1 + 1));

                        trees_img_2.setFitHeight(50);
                        trees_img_2.setFitWidth(50);


                        getChildren().add(trees_img_2);

                        t_ph.setY(t_ph.getY() + 5);
                    }
                    else if( t_ph.getY() - WIDTH - block_HEIGHT*map.getSize() + tank_width < 0 )
                        this.t_ph.setY(t_ph.getY() + 5);

                    t_ph.setRotate(180);

                    break;
                case W:
//                    System.out.println("U");

                    direction = "up";
                    bullet_x = t_ph.getX() + tank_width/2;
                    bullet_y = t_ph.getY();

                    map.setValueAt((int)tank_cor_x, (int)tank_cor_y, "C");
                    if((int)tank_cor_y == map.getSize() - 1){
                        map.setValueAt((int)tank_cor_x, (int)tank_cor_y, "C");
                    }else if ( Character.compare( map.getValueAt( (int)tank_cor_x, (int)tank_cor_y+1), 'C' ) ==0  ){
                        map.setValueAt((int)tank_cor_x, (int)tank_cor_y+1, "0");
                        map.print();
                    }

                    if((int)arr_y_1 == 0){
                        if( t_ph.getY() - WIDTH > 0 ){
                            this.t_ph.setY(t_ph.getY() - 5);
                        }
                    }
                    else if (Character.compare( map.getValueAt((int)arr_x_1 , (int)arr_y_1 - 1) , 'S' ) == 0 || Character.compare(map.getValueAt((int)arr_x_2, (int)arr_y_1 - 1) , 'S' ) == 0){
                        System.out.println("S");
                        if( t_ph.getY() - HEIGHT - block_HEIGHT*(int)arr_y_1 > 0 ){
                            this.t_ph.setY(t_ph.getY() - 5);
                        }
                    }
                    else if (Character.compare( map.getValueAt((int)arr_x_1 , (int)arr_y_1 - 1) , 'P' ) == 0 || Character.compare(map.getValueAt((int)arr_x_2, (int)arr_y_1 - 1) , 'P' ) == 0){
                        System.out.println("P");
                        if( t_ph.getY() - HEIGHT - block_HEIGHT*(int)arr_y_1 > 0 ){
                            this.t_ph.setY(t_ph.getY() - 5);
                        }
                    }
                    else if (Character.compare( map.getValueAt((int)arr_x_1 , (int)arr_y_1 - 1) , 'B' ) == 0 || Character.compare(map.getValueAt((int)arr_x_2, (int)arr_y_1 - 1) , 'B' ) == 0){
                        if( t_ph.getY() - WIDTH - block_WIDTH*((int)arr_y_1) > 0 ){
                            this.t_ph.setY(t_ph.getY() - 5);
                        }
                    }
                    else if (Character.compare( map.getValueAt((int)arr_x_1 , (int)arr_y_1 - 1) , 'W' ) == 0 || Character.compare(map.getValueAt((int)arr_x_2, (int)arr_y_1 - 1) , 'W' ) == 0){
                        System.out.println("W");
                        if( t_ph.getY() - WIDTH - block_WIDTH*(int)arr_y_1 > 0 ){
                            this.t_ph.setY(t_ph.getY() - 5);
                        }
                    }
                    else if (Character.compare( map.getValueAt((int)arr_x_1 , (int)arr_y_1 - 1) , 'T' ) == 0){
                        Image trees_into_1 = null;
                        try {
                            trees_into_1 = new Image(new FileInputStream("C:\\Users\\User\\Desktop\\Project_2 photo\\tree.jpg"));
                        } catch (FileNotFoundException fileNotFoundException) {
                            fileNotFoundException.printStackTrace();
                        }
                        ImageView trees_img_1 = new ImageView(trees_into_1);

                        trees_img_1.setX(WIDTH + block_WIDTH*((int)arr_x_1));
                        trees_img_1.setY(HEIGHT + block_HEIGHT*((int)arr_y_1 - 1));

                        trees_img_1.setFitHeight(50);
                        trees_img_1.setFitWidth(50);


                        getChildren().add(trees_img_1);

                        t_ph.setY(t_ph.getY() - 5);
                    }
                    else if( t_ph.getY() - WIDTH > 0 )
                        this.t_ph.setY(t_ph.getY() - 5);

                    t_ph.setRotate(0);

                    break;

                case C:
                    System.out.println("Space");
                    Bullet bullet = new Bullet(bullet_x, bullet_y, direction, map, count, "C");

                    getChildren().remove(t_ph);

                    getChildren().add(bullet);

                    getChildren().add(t_ph);

                    break;
            }
        }
    }
}





class Bullet extends Pane {
    double x;
    double y;
    String direction;
    Timeline animation;
    Circle c1;
    Map map;
    Main main;

    int HEIGHT = 100;
    int block_HEIGHT = 50;
    int WIDTH = 100;
    int block_WIDTH = 50;

    public static int[][] count;
    String[][] karta;
    public String from;

    public Bullet(double x, double y, String direction, Map map, int[][] count, String from){
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.map = map;
        this.main = main;
        this.count = count;
        this.from = from;


        c1 = new Circle(x, y, 10);
        c1.setFill(Color.YELLOW);
        getChildren().add( c1 );
        String direct = direction;

        this.count = new int[map.getSize()][map.getSize()];
        for (int i=0; i<map.getSize(); i++){
            for (int j=0; j<map.getSize(); j++){
                System.out.print(map.arena[i][j] + " ");
            }
            System.out.println();
        }
        karta = map.getArr_map();

        System.out.println("Lets start");
        for (int i=0; i<map.getSize(); i++){
            for (int j=0; j<map.getSize(); j++){
                System.out.print(karta[i][j] + " ");
            }
            System.out.println();
        }

        animation = new Timeline( new KeyFrame(Duration.millis(3), e -> move_bullet(c1, direct)));
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play(); // Start animation

    }

    public void pause() {
        animation.pause();
    }

    protected void move_bullet(Circle c1, String direction) {
        // Check boundaries
        double x = c1.getCenterX();
        double y = c1.getCenterY();
        double arr_x_1 = (x - WIDTH )/block_WIDTH;
        double arr_y_1 = (y - HEIGHT )/block_HEIGHT;
        double arr_x_2 = (x - WIDTH + 10)/block_WIDTH;
        double arr_y_2 = (y - HEIGHT + 10)/block_HEIGHT;





        switch (direction) {
            case "right" :
                try {
                    if ( karta[ (int)(y-HEIGHT)/block_WIDTH ][ (int)(x-WIDTH)/block_WIDTH ].equals("B") ){
                        map.setArena((int)(x-WIDTH)/block_WIDTH , (int)(y-HEIGHT)/block_WIDTH);
                        System.out.println("Arena");
                        for (int i=0; i<map.getSize(); i++){
                            for (int j=0; j<map.getSize(); j++){
                                System.out.print(map.arena[i][j] + " ");
                            }
                            System.out.println();
                        }

                        getChildren().remove(c1);
                        pause();

                        System.out.println("Arena index" + map.arena[ (int)(x-WIDTH)/block_WIDTH*block_WIDTH ][ (int)(y-WIDTH)/block_WIDTH*block_WIDTH ]);


                        System.out.println( );




//                        main.map.setValueAt((int)(y-WIDTH)/block_WIDTH,(int)(x-WIDTH)/block_WIDTH, "0" );

//                        main.delete((int)(x-WIDTH)/block_WIDTH, (int)(y-WIDTH)/block_WIDTH);
//                        map.print();

                    }
                    else if ( karta[ (int)(y-WIDTH)/block_WIDTH ][ (int)(x-WIDTH)/block_WIDTH ].equals("C") && from.equals("P")){
                        System.out.println("bull click: " + map.getClick());
                        map.setClick(1);
                        System.out.println("bull click: " + map.getClick());
                        map.setValueAt((int)(y-HEIGHT)/block_WIDTH,(int)(x-WIDTH)/block_WIDTH, "0");

                        getChildren().remove(c1);
                        pause();

                        System.out.println("Arena index" + map.arena[ (int)(x-WIDTH)/block_WIDTH*block_WIDTH ][ (int)(y-WIDTH)/block_WIDTH*block_WIDTH ]);


                        System.out.println( );
                    }
                    else if ( karta[ (int)(y-WIDTH)/block_WIDTH ][ (int)(x-WIDTH)/block_WIDTH ].equals("P") && from.equals("C")){
                        System.out.println("bull click: " + map.getClick());
                        map.setClick(1);
                        System.out.println("bull click: " + map.getClick());
                        map.setValueAt((int)(y-HEIGHT)/block_WIDTH,(int)(x-WIDTH)/block_WIDTH, "0");

                        getChildren().remove(c1);
                        pause();

                        System.out.println("Arena index" + map.arena[ (int)(x-WIDTH)/block_WIDTH*block_WIDTH ][ (int)(y-WIDTH)/block_WIDTH*block_WIDTH ]);


                        System.out.println( );
                    }


                    else if ( karta[ (int)(y-WIDTH)/block_WIDTH ][ (int)(x-WIDTH)/block_WIDTH ].equals("S") ){

                        getChildren().remove(c1);
                        pause();
                    }
                    else if (x < WIDTH + block_WIDTH* map.getSize() - 10){
                        x +=1;
//                        System.out.println(arr_x_1 + "  , " + arr_y_1);
//                        System.out.println(arr_x_2 + "  ,  " + arr_y_2);
                        System.out.println ((int)(x-WIDTH)/block_WIDTH + ", " + (int)(y-WIDTH)/block_WIDTH );
                        System.out.println(karta[ (int)(y-WIDTH)/block_WIDTH ][ (int)(x-WIDTH)/block_WIDTH ]);
                        System.out.println();

                    }
                    else if (x >= WIDTH + block_WIDTH* map.getSize() - 10){
                        x += 0;
                        y += 0;
                        getChildren().remove(c1);
                    }
                }catch (Exception e){
                    System.out.println( (int)(x-WIDTH)/block_WIDTH + ", " + (int)(y-WIDTH)/block_WIDTH);
                }
//                if ( (int)arr_x_2 == map.getSize() -1 ){
//                    if( x - WIDTH - block_WIDTH*map.getSize() + 10 < 0){
//                        x++;
//                    }
//
//                }
//                if (Character.compare(map.getValueAt( (int)arr_x_1 + 1, (int)arr_y_1 ), 'B') == 0){
//                    System.out.println("Right");
//                    if( x - WIDTH - block_WIDTH*map.getSize() +11 < 0){
//                        x++;
//                    }
//                }
                break;
            case "left" :
                try {
                    if ( karta[ (int)(y-WIDTH)/block_WIDTH ][ (int)(x-WIDTH)/block_WIDTH ].equals("B") ){
                        map.setArena((int)(x-WIDTH)/block_WIDTH , (int)(y-HEIGHT)/block_WIDTH);
                        System.out.println("Arena");
                        for (int i=0; i<map.getSize(); i++){
                            for (int j=0; j<map.getSize(); j++){
                                System.out.print(map.arena[i][j] + " ");
                            }
                            System.out.println();
                        }

                        getChildren().remove(c1);
                        pause();

                        System.out.println("Arena index" + map.arena[ (int)(x-WIDTH)/block_WIDTH*block_WIDTH ][ (int)(y-WIDTH)/block_WIDTH*block_WIDTH ]);


                        System.out.println( );
                    }

                    else if ( karta[ (int)(y-WIDTH)/block_WIDTH ][ (int)(x-WIDTH)/block_WIDTH ].equals("C") && from.equals("P")){
                        System.out.println("bull click: " + map.getClick());
                        map.setClick(1);
                        System.out.println("bull click: " + map.getClick());
                        map.setValueAt((int)(y-HEIGHT)/block_WIDTH,(int)(x-WIDTH)/block_WIDTH, "0");

                        getChildren().remove(c1);
                        pause();

                        System.out.println("Arena index" + map.arena[ (int)(x-WIDTH)/block_WIDTH*block_WIDTH ][ (int)(y-WIDTH)/block_WIDTH*block_WIDTH ]);


                        System.out.println( );
                    }
                    else if ( karta[ (int)(y-WIDTH)/block_WIDTH ][ (int)(x-WIDTH)/block_WIDTH ].equals("P") && from.equals("C")){
                        System.out.println("bull click: " + map.getClick());
                        map.setClick(1);
                        System.out.println("bull click: " + map.getClick());
                        map.setValueAt((int)(y-HEIGHT)/block_WIDTH,(int)(x-WIDTH)/block_WIDTH, "0");

                        getChildren().remove(c1);
                        pause();

                        System.out.println("Arena index" + map.arena[ (int)(x-WIDTH)/block_WIDTH*block_WIDTH ][ (int)(y-WIDTH)/block_WIDTH*block_WIDTH ]);


                        System.out.println( );
                    }

                    else if ( karta[ (int)(y-WIDTH)/block_WIDTH ][ (int)(x-WIDTH)/block_WIDTH ].equals("S") ){

                        getChildren().remove(c1);
                        pause();
                    }
                    else if (x > WIDTH + 10){
                        x -= 1;
                    }
                    else if (x <= WIDTH + 10){
                        x = +0;
                        y = +0;
                        getChildren().remove(c1);

                    }

                }catch (Exception e){
//                    System.out.println("norm left");
                }
                break;

            case "down" :
                try {
                    if ( karta[ (int)(y-WIDTH)/block_WIDTH ][ (int)(x-WIDTH)/block_WIDTH ].equals("B") ){
                        map.setArena((int)(x-WIDTH)/block_WIDTH , (int)(y-HEIGHT)/block_WIDTH);
                        System.out.println("Arena");
                        for (int i=0; i<map.getSize(); i++){
                            for (int j=0; j<map.getSize(); j++){
                                System.out.print(map.arena[i][j] + " ");
                            }
                            System.out.println();
                        }

                        getChildren().remove(c1);
                        pause();

                        System.out.println("Arena index" + map.arena[ (int)(x-WIDTH)/block_WIDTH*block_WIDTH ][ (int)(y-WIDTH)/block_WIDTH*block_WIDTH ]);


                        System.out.println( );
                    }
                    else if ( karta[ (int)(y-WIDTH)/block_WIDTH ][ (int)(x-WIDTH)/block_WIDTH ].equals("C") && from.equals("P")){
                        System.out.println("bull click: " + map.getClick());
                        map.setClick(1);
                        System.out.println("bull click: " + map.getClick());
                        map.setValueAt((int)(y-HEIGHT)/block_WIDTH,(int)(x-WIDTH)/block_WIDTH, "0");

                        getChildren().remove(c1);
                        pause();

                        System.out.println("Arena index" + map.arena[ (int)(x-WIDTH)/block_WIDTH*block_WIDTH ][ (int)(y-WIDTH)/block_WIDTH*block_WIDTH ]);


                        System.out.println( );
                    }
                    else if ( karta[ (int)(y-WIDTH)/block_WIDTH ][ (int)(x-WIDTH)/block_WIDTH ].equals("P") && from.equals("C")){
                        System.out.println("bull click: " + map.getClick());
                        map.setClick(1);
                        System.out.println("bull click: " + map.getClick());
                        map.setValueAt((int)(y-HEIGHT)/block_WIDTH,(int)(x-WIDTH)/block_WIDTH, "0");

                        getChildren().remove(c1);
                        pause();

                        System.out.println("Arena index" + map.arena[ (int)(x-WIDTH)/block_WIDTH*block_WIDTH ][ (int)(y-WIDTH)/block_WIDTH*block_WIDTH ]);


                        System.out.println( );
                    }
                    else if ( karta[ (int)(y-WIDTH)/block_WIDTH ][ (int)(x-WIDTH)/block_WIDTH ].equals("S") ){

                        getChildren().remove(c1);
                        pause();
                    }
                    else if (y < WIDTH + block_WIDTH* map.getSize() - 10){
                        y +=1;
                    }
                    else if (y >= WIDTH + block_WIDTH* map.getSize() -10){
                        x = +0;
                        y = +0;
                        getChildren().remove(c1);
                    }
                }catch (Exception e){
//                    System.out.println("norm down");
                }

                break;
            case "up" :
                try {
                    if ( karta[ (int)(y-WIDTH)/block_WIDTH ][ (int)(x-WIDTH)/block_WIDTH ].equals("B") ){
                        map.setArena((int)(x-WIDTH)/block_WIDTH , (int)(y-HEIGHT)/block_WIDTH);
                        System.out.println("Arena");
                        for (int i=0; i<map.getSize(); i++){
                            for (int j=0; j<map.getSize(); j++){
                                System.out.print(map.arena[i][j] + " ");
                            }
                            System.out.println();
                        }

                        getChildren().remove(c1);
                        pause();

                        System.out.println("Arena index" + map.arena[ (int)(x-WIDTH)/block_WIDTH*block_WIDTH ][ (int)(y-WIDTH)/block_WIDTH*block_WIDTH ]);


                        System.out.println( );
                    }
                    else if ( karta[ (int)(y-WIDTH)/block_WIDTH ][ (int)(x-WIDTH)/block_WIDTH ].equals("C" ) && from.equals("P") ){
                        System.out.println("bull click: " + map.getClick());
                        map.setClick(1);
                        System.out.println("bull click: " + map.getClick());
                        map.setValueAt((int)(x-HEIGHT)/block_WIDTH,(int)(y-WIDTH)/block_WIDTH, "0");

                        getChildren().remove(c1);
                        pause();

                        System.out.println("Arena index" + map.arena[ (int)(x-WIDTH)/block_WIDTH*block_WIDTH ][ (int)(y-WIDTH)/block_WIDTH*block_WIDTH ]);


                        System.out.println( );
                    }
                    else if ( karta[ (int)(y-WIDTH)/block_WIDTH ][ (int)(x-WIDTH)/block_WIDTH ].equals("P") && from.equals("C")){
                        System.out.println("bull click: " + map.getClick());
                        map.setClick(1);
                        System.out.println("bull click: " + map.getClick());
                        map.setValueAt((int)(x-HEIGHT)/block_WIDTH,(int)(y-WIDTH)/block_WIDTH, "0");

                        getChildren().remove(c1);
                        pause();

                        System.out.println("Arena index" + map.arena[ (int)(x-WIDTH)/block_WIDTH*block_WIDTH ][ (int)(y-WIDTH)/block_WIDTH*block_WIDTH ]);


                        System.out.println( );
                    }

                    else if ( karta[ (int)(y-WIDTH)/block_WIDTH ][ (int)(x-WIDTH)/block_WIDTH ].equals("S") ){



                        getChildren().remove(c1);
                        pause();
                    }
                    else if (y > WIDTH ){
                        y -=1;
                    }
                    else if (y <= WIDTH ){
                        x = +0;
                        y = +0;
                        getChildren().remove(c1);
                    }
                }catch (Exception e){
//                    System.out.println("norm up");
                }


                break;
        }

        // Adjust ball position

        c1.setCenterX(x);
        c1.setCenterY(y);
    }

    public double getX() {
        return c1.getCenterX();
    }

    public double getY() {
        return c1.getCenterY();
    }
}

/*
Enter your code here.
Create all the necessary classes and methods as per the requirements.
*/
/*
Input and Output has been done for you.
Various conditions are meant to check individual classes separately.
You still need to implement completely all the necessary classes with their corresponding methods,
but the correctness for each class is checked individually.
In other words, you already get some partial points
for implementing some classes completely and correctly,
even if other classes are complete but still may not work properly.
*/

class Solution{

    public static void main(String[] args){
        Scanner input = new Scanner(System.in);


        String className = input.nextLine();

        // Checking the implementation of the Position class
        if(className.equals("Position")){


            Position p1 = new Position(input.nextInt(), input.nextInt());
            System.out.println(p1);
            p1.setX(5);
            System.out.println(p1.getX());

            Position p2 = new Position(5, 10);
            System.out.println(p1.equals(p2));
        }

        // Checking the implementation of the Map class
        else if(className.equals("Map")){
            try{
                Map map = new Map(input);
                map.print();
                int size = map.getSize();
                System.out.println(size);
                System.out.println(map.getValueAt(size/2, size/2));
            }
            catch(Exception e){}
        }

        // Checking the Player interface and the MyPlayer class
        else if(className.equals("Player")){
            Player player = new MyPlayer();
            System.out.println(Player.class.isInterface());
            System.out.println(player instanceof Player);
            System.out.println(player instanceof MyPlayer);
        }

        // Checking the InvalidMapException class
        else if(className.equals("Exception")){
            try{
                throw new InvalidMapException("Some message");
            }
            catch(InvalidMapException e){
                System.out.println(e.getMessage());
            }
        }

        // Checking the Game class and all of its components
        else if(className.equals("Game")){

            // Initialize player, map, and the game
            Player player = new MyPlayer();
            Game game = null;

            try{
                game = new Game(new Map(input));
            }
            catch(InvalidMapException e){ // custom exception
                System.out.println(e.getMessage());
                System.exit(0);
            }

            game.addPlayer(player);

            // Make the player move based on the commands given in the input
            String moves = input.next();
            char move;
            for(int i=0; i<moves.length(); i++){
                move = moves.charAt(i);
                switch(move){
                    case 'R':
                        player.moveRight();
                        break;
                    case 'L':
                        player.moveLeft();
                        break;
                    case 'U':
                        player.moveUp();
                        break;
                    case 'D':
                        player.moveDown();
                        break;
                }
            }

            // Determine the final position of the player after completing all the moves above
            Position playerPosition = player.getPosition();
            System.out.println("Player final position");
            System.out.println("Row: " + playerPosition.getY());
            System.out.println("Col: " + playerPosition.getX());
        }
    }
}

class Position {
    private int x;
    private int y;

    Position(){
    }

    public Position(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean equals(Position p){
        if(x == p.getX() && y == p.getY()){
            return true;
        }
        return false;
    }

    public String toString(){
        return "("+x + "," + y + ")";
    }
}

class Map{
    private Scanner scan;
    private String s;
    private int size;
    private String[] arr_all;
    private String[][] arr_map;
    public int arena[][];

    int x;
    int y;
    private int check;

    int click;

    Map(){

    }

    public Map(Scanner scan) throws InvalidMapException{
        this.scan = scan;
        all();
    }

    private void all() throws InvalidMapException{
        this.size = scan.nextInt();

        click = 0;

        if(size ==0 ){
            throw new InvalidMapException("Map size can not be zero");
        }

        this.arena = new int[size][size];




        int i=0;
        String[] lines = new String[size];
        scan.nextLine();

        int k=size;

        String[][] ex = new String[size][size];

        while (k>0){
            lines[i] = scan.nextLine();

            if(lines[i].contains("U")){
                throw new InvalidMapException("Not enough map elements");
            }else if(lines[i].contains("D")){
                throw new InvalidMapException("Not enough map elements");
            }else if(lines[i].contains("R")){
                throw new InvalidMapException("Not enough map elements");
            }else if(lines[i].contains("L")){
                throw new InvalidMapException("Not enough map elements");
            }

            ex[i] = lines[i].split(" ");

            i++;
            k--;
        }

        this.check = k;

        if(ex[0].length < size){
            throw new InvalidMapException("Not enough map elements");
        }

        this.arr_all = lines;

        convert_arr();

    }

    private void find_p(){
        for (int j=0; j<arr_map.length; j++){
            for (int k=0; k<arr_map.length; k++){
                if(arr_map[j][k].equals("P")){
                    this.x = k;
                    this.y = j;
                }
            }
        }
    }

    private void convert_arr(){

        String[][] d = new String[size][size];

        for (int j=0;j<size;j++){

            d[j] = convert(arr_all[j]);
        }

        this.arr_map = d;
        find_p();



    }

    private String[] convert(String in){

        String[] con = in.split(" ");
        return con;
    }



    public int getSize(){
        return size;
    }

    public void print(){


        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                System.out.print(arr_map[i][j] + " ");
            }
            System.out.println();
        }


    }

    public char getValueAt(int x, int y){
        return arr_map[y][x].charAt(0);
    }

    public String[][] getArr_map() {
        return arr_map;
    }

    public void setValueAt(int x, int y, String ds){
        this.arr_map[y][x] = ds;
    }

    public void setArena(int x, int y) {
        this.arena[y][x]++;
    }

    public int getClick() {
        return click;
    }

    public void setClick(int click) {
        this.click = click;
    }
}

class MyPlayer implements Player{
    private Map map;
    private Position position;

    public void setMap(Map map) {
        this.map = map;
        this.position = new Position(map.x, map.y);
    }



    public void moveRight(){
        int x = position.getX();
        int y = position.getY();



        if( x == map.getSize()-1 ){
            position.setX(x);
        }else if (Character.compare('1',map.getValueAt(x+1,y)) == 0){
            position.setX(x);
        } else {
            position.setX(x+1);
        }
    }

    public void moveLeft(){
        int x = position.getX();
        int y = position.getY();

        if( x == 0 ){
            position.setX(x);
        }else if (Character.compare('1',map.getValueAt(x-1,y)) == 0){
            position.setX(x);
        } else {
            position.setX(x-1);
        }
    }

    public void moveUp(){

        int x = position.getX();
        int y = position.getY();

        if( y == 0 ){
            position.setY(y);
        }else if (Character.compare('1',map.getValueAt(x,y-1)) == 0){
            position.setY(y);
        } else {
            position.setY(y-1);
        }
    }

    public void moveDown(){
        int x = position.getX();
        int y = position.getY();
        if( y == map.getSize()-1 ){
            position.setY(y);
        }else if (Character.compare('1',map.getValueAt(x,y+1)) == 0){
            position.setY(y);
        } else {
            position.setY(y+1);
        }

    }

    public Position getPosition() {
        return position;
    }
}

class InvalidMapException extends Exception{
    private String s;

    public InvalidMapException(String s){
        this.s = s;
    }

    public String getMessage() {
        return s;
    }

}

class Game{
    private Map map;
    private Player player;

    public Game(Map map){
        this.map = map;


    }

    public void setMap(Map map) {
        this.map = map;
    }

    public void addPlayer(Player player){
        this.player = player;
        player.setMap(map);
    }
}

interface Player {
    public void moveRight();

    public void moveLeft();

    public void moveUp();

    public void moveDown();

    public default void setMap(Map map) {
    }

    public Position getPosition();

}
