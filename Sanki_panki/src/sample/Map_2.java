import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.*;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import static javafx.application.Application.launch;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.util.Duration;


import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Spliterator;

import static java.math.RoundingMode.DOWN;
import static java.math.RoundingMode.UP;
import static javafx.application.Application.launch;

public class Map_2 extends Application implements Serializable {

    public Tank.Tank_fx tank;
    private int x;
    private int y;
    public int direction = 3;
    double bu_x;
    double bu_y;

    int live = 4  ;
    int c_live = 4 ;

    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    public Tank.Tank_fx client_tank;
    public Map m;
    public Pane pane;



    @Override
    public void start(Stage primaryStage) throws InvalidMapException, IOException, InterruptedException {
        Scanner scan = new Scanner(new File("C:\\Users\\User\\Desktop\\Project_2 photo\\map.txt"));
        this.m = new Map(scan);


        this.pane = new Pane();

        m.print();


        Rectangle based = new Rectangle(100, 100, 50 * m.getSize(), 50 * m.getSize());
        based.setFill(Color.BLACK);
        based.setStroke(Color.GREEN);
        based.setTranslateZ(20);
        pane.getChildren().add(based);


        fill(pane, m);


        pane.getChildren().add(tank);

        new Thread( ()->{
            tank.tank_img.setOnKeyPressed(e -> {

                double cor_x =  ( tank.tank_img.getX()-100)/50;
                double cor_y =  ( tank.tank_img.getY()-100)/50;

                double cor_x_1 =  ( tank.tank_img.getX()-100)/50;
                double cor_y_1 =  ( tank.tank_img.getY()-100)/50;

                double cor_x_2 =  ( tank.tank_img.getX()-100 + 40)/50;
                double cor_y_2 =  ( tank.tank_img.getY()-100)/50;

                double cor_x_3 =  ( tank.tank_img.getX()-100)/50;
                double cor_y_3 =  ( tank.tank_img.getY()-100 + 40)/50;

                double cor_x_4 =  ( tank.tank_img.getX()-100 + 40)/50;
                double cor_y_4 =  ( tank.tank_img.getY()-100 + 40)/50;

                double tank_kar_x = (cor_x_1 + cor_x_4)/2;
                double tank_kar_y = (cor_y_1 + cor_y_4)/2;

                System.out.print  ("(" + cor_x_1 + " ; " + cor_y_1 + ") ");
                System.out.println("(" + cor_x_2 + " ; " + cor_y_2 + ") ");
                System.out.print  ("(" + cor_x_3 + " ; " + cor_y_3 + ") ");
                System.out.println("(" + cor_x_4 + " ; " + cor_y_4 + ") ");
                System.out.println();
                switch (e.getCode()) {


                    case DOWN:

                        direction = 1;
                        bu_x = tank.tank_img.getX() + 40/2;
                        bu_y = tank.tank_img.getY() + 40/2;

                        System.out.println((int)tank_kar_y + " " + (int)tank_kar_x);


                        m.setValueAt( (int)tank_kar_x, (int)tank_kar_y, "P" );
                        if((int)tank_kar_y  == 0 ){
                            m.setValueAt( (int)tank_kar_x, (int)tank_kar_y, "P" );
                        }else if(Character.compare(m.getValueAt( (int)tank_kar_x, (int)tank_kar_y-1 ),'P' ) ==0 ){
                            m.setValueAt( (int)tank_kar_x, (int)tank_kar_y-1, "0" );
                        }
//                        String[][] example = m.m_karta;

//                        for(int i=0; i<m.getSize(); i++){
//                            for(int j=0; j<m.getSize(); j++){
//                                System.out.print(example[i][j]+" ");
//                            }
//                            System.out.println();
//                        }
                        m.print();

                        if((int)cor_y == m.getSize()-1){
                            if(tank.tank_img.getY() < 100 + 50*m.getSize() -40){
                                tank.tank_img.setY(tank.tank_img.getY() + 5);
                            }
                        }
                        else if ( Character.compare( m.getValueAt((int)cor_x , (int)cor_y+1), 'W' ) == 0 || Character.compare( m.getValueAt((int)cor_x_2 , (int)cor_y+1), 'W' ) == 0){
                            System.out.println("fin Down");
                            if(tank.tank_img.getY() < (100 + 50*((int)cor_y+1) - 40)){
                                tank.tank_img.setY(tank.tank_img.getY() + 5);
                            }
                        }
                        else if ( Character.compare( m.getValueAt((int)cor_x , (int)cor_y+1), 'S' ) == 0 || Character.compare( m.getValueAt((int)cor_x_2 , (int)cor_y+1), 'S' ) == 0){
                            System.out.println("fin Down");
                            if(tank.tank_img.getY() < (100 + 50*((int)cor_y+1) - 40)){
                                tank.tank_img.setY(tank.tank_img.getY() + 5);
                            }
                        }
                        else if ( Character.compare( m.getValueAt((int)cor_x , (int)cor_y+1), 'B' ) == 0 || Character.compare( m.getValueAt((int)cor_x_2 , (int)cor_y+1), 'B' ) == 0){
                            System.out.println("fin Down");
                            if(tank.tank_img.getY() < (100 + 50*((int)cor_y+1) - 40)){
                                tank.tank_img.setY(tank.tank_img.getY() + 5);
                            }
                        }
                        else if ( Character.compare( m.getValueAt((int)cor_x , (int)cor_y+1), 'C' ) == 0 || Character.compare( m.getValueAt((int)cor_x_2 , (int)cor_y+1), 'C' ) == 0){
                            System.out.println("fin Down");
                            if(tank.tank_img.getY() < (100 + 50*((int)cor_y+1) - 40)){
                                tank.tank_img.setY(tank.tank_img.getY() + 5);
                            }
                        }
                        else if ( Character.compare( m.getValueAt((int)cor_x , (int)cor_y+1), 'T' ) == 0 ){
                            System.out.println("fin Down");
                            Image trees_into_1 = null;
                            try {
                                trees_into_1 = new Image(new FileInputStream("C:\\Users\\User\\Downloads\\tree.jpeg"));
                            } catch (FileNotFoundException fileNotFoundException) {
                                fileNotFoundException.printStackTrace();
                            }
                            ImageView trees_img_1 = new ImageView(trees_into_1);

                            trees_img_1.setX(100 + 50*(int)cor_x);
                            trees_img_1.setY(100 + 50*((int)cor_y+1));

                            trees_img_1.setFitHeight(50);
                            trees_img_1.setFitWidth(50);

                            pane.getChildren().add(trees_img_1);

                            tank.tank_img.setY(tank.tank_img.getY() + 5);

                        }
                        else if(tank.tank_img.getY() < 100 + 50*m.getSize() -40){
                            tank.tank_img.setY(tank.tank_img.getY() + 5);

                        }




                        tank.tank_img.isSmooth();
                        tank.tank_img.setRotate(180);
                        break;
                    case UP:
                        direction = 3;
                        bu_x = tank.tank_img.getX() + 40/2;
                        bu_y = tank.tank_img.getY() + 40/2;

                        m.setValueAt( (int)tank_kar_x, (int)tank_kar_y, "P" );
                        if((int)tank_kar_y-m.getSize()+1  == 0 ){
                            m.setValueAt( (int)tank_kar_x, (int)tank_kar_y, "P" );
                        }else if(Character.compare(m.getValueAt( (int)tank_kar_x, (int)tank_kar_y+1 ),'P' ) ==0 ){
                            m.setValueAt( (int)tank_kar_x, (int)tank_kar_y+1, "0" );
                        }
                        m.print();

                        if((int)cor_y == 0){
                            if(tank.tank_img.getY() > 100){
                                tank.tank_img.setY(tank.tank_img.getY() - 5);
                            }
                        }
                        else if ( Character.compare( m.getValueAt((int)cor_x , (int)cor_y-1), 'W' ) == 0 || Character.compare( m.getValueAt((int)cor_x_2 , (int)cor_y-1), 'W' ) == 0){
                            System.out.println("fin UP");
                            if(tank.tank_img.getY() > (100 + 50*((int)cor_y))){
                                tank.tank_img.setY(tank.tank_img.getY() - 5);
                            }
                        }
                        else if ( Character.compare( m.getValueAt((int)cor_x , (int)cor_y-1), 'S' ) == 0 || Character.compare( m.getValueAt((int)cor_x_2 , (int)cor_y-1), 'S' ) == 0){
                            System.out.println("fin UP");
                            if(tank.tank_img.getY() > (100 + 50*((int)cor_y))){
                                tank.tank_img.setY(tank.tank_img.getY() - 5);
                            }
                        }
                        else if ( Character.compare( m.getValueAt((int)cor_x , (int)cor_y-1), 'B' ) == 0 || Character.compare( m.getValueAt((int)cor_x_2 , (int)cor_y-1), 'B' ) == 0){
                            System.out.println("fin UP");
                            if(tank.tank_img.getY() > (100 + 50*((int)cor_y))){
                                tank.tank_img.setY(tank.tank_img.getY() - 5);
                            }
                        }
                        else if ( Character.compare( m.getValueAt((int)cor_x , (int)cor_y-1), 'C' ) == 0 || Character.compare( m.getValueAt((int)cor_x_2 , (int)cor_y-1), 'C' ) == 0){
                            System.out.println("fin UP");
                            if(tank.tank_img.getY() > (100 + 50*((int)cor_y))){
                                tank.tank_img.setY(tank.tank_img.getY() - 5);
                            }
                        }
                        else if ( Character.compare( m.getValueAt((int)cor_x , (int)cor_y-1), 'T' ) == 0 ){
                            System.out.println("fin Down");
                            Image trees_into_2 = null;
                            try {
                                trees_into_2 = new Image(new FileInputStream("C:\\Users\\User\\Downloads\\tree.jpeg"));
                            } catch (FileNotFoundException fileNotFoundException) {
                                fileNotFoundException.printStackTrace();
                            }
                            ImageView trees_img_2 = new ImageView(trees_into_2);

                            trees_img_2.setX(100 + 50*(int)cor_x);
                            trees_img_2.setY(100 + 50*((int)cor_y-1));

                            trees_img_2.setFitHeight(50);
                            trees_img_2.setFitWidth(50);

                            pane.getChildren().add(trees_img_2);

                            tank.tank_img.setY(tank.tank_img.getY() - 5);
                        }
                        else if(tank.tank_img.getY() > 100){
                            tank.tank_img.setY(tank.tank_img.getY() - 5);
                        }


                        tank.tank_img.isSmooth();
                        tank.tank_img.setRotate(0);
                        break;
                    case LEFT:

                        direction = 2;
                        bu_x = tank.tank_img.getX() + 40/2;
                        bu_y = tank.tank_img.getY() + 40/2;

                        m.setValueAt( (int)tank_kar_x, (int)tank_kar_y, "P" );
                        if((int)tank_kar_x-m.getSize()+1  == 0 ){
                            m.setValueAt( (int)tank_kar_x, (int)tank_kar_y, "P" );
                        }else if(Character.compare(m.getValueAt( (int)tank_kar_x+1, (int)tank_kar_y ),'P' ) ==0 ){
                            m.setValueAt( (int)tank_kar_x+1, (int)tank_kar_y, "0" );
                        }
                        m.print();
                        if((int)cor_x == 0){
                            if(tank.tank_img.getX() > 100){
                                tank.tank_img.setX(tank.tank_img.getX() - 5);
                            }
                        }
                        else if ( Character.compare( m.getValueAt((int)cor_x - 1, (int)cor_y), 'W' ) == 0 || Character.compare( m.getValueAt((int)cor_x - 1, (int)cor_y_3), 'W' ) == 0 ){
                            System.out.println("fin Left");
                            if(tank.tank_img.getX() > (100 + 50*((int)cor_x))){
                                tank.tank_img.setX(tank.tank_img.getX() - 5);
                            }
                        }
                        else if ( Character.compare( m.getValueAt((int)cor_x - 1, (int)cor_y), 'S' ) == 0 || Character.compare( m.getValueAt((int)cor_x - 1, (int)cor_y_3), 'S' ) == 0 ){
                            System.out.println("fin Left");
                            if(tank.tank_img.getX() > (100 + 50*((int)cor_x))){
                                tank.tank_img.setX(tank.tank_img.getX() - 5);
                            }
                        }
                        else if ( Character.compare( m.getValueAt((int)cor_x - 1, (int)cor_y), 'B' ) == 0 || Character.compare( m.getValueAt((int)cor_x - 1, (int)cor_y_3), 'B' ) == 0 ){
                            System.out.println("fin Left");
                            if(tank.tank_img.getX() > (100 + 50*((int)cor_x))){
                                tank.tank_img.setX(tank.tank_img.getX() - 5);
                            }
                        }
                        else if ( Character.compare( m.getValueAt((int)cor_x - 1, (int)cor_y), 'C' ) == 0 || Character.compare( m.getValueAt((int)cor_x - 1, (int)cor_y_3), 'C' ) == 0 ){
                            System.out.println("fin Left");
                            if(tank.tank_img.getX() > (100 + 50*((int)cor_x))){
                                tank.tank_img.setX(tank.tank_img.getX() - 5);
                            }
                        }
                        else if ( Character.compare( m.getValueAt((int)cor_x - 1, (int)cor_y), 'T' ) == 0 ){
                            System.out.println("fin Down");
                            Image trees_into_3 = null;
                            try {
                                trees_into_3 = new Image(new FileInputStream("C:\\Users\\User\\Downloads\\tree.jpeg"));
                            } catch (FileNotFoundException fileNotFoundException) {
                                fileNotFoundException.printStackTrace();
                            }
                            ImageView trees_img_3 = new ImageView(trees_into_3);

                            trees_img_3.setX(100 + 50*((int)cor_x - 1));
                            trees_img_3.setY(100 + 50*((int)cor_y));

                            trees_img_3.setFitHeight(50);
                            trees_img_3.setFitWidth(50);

                            pane.getChildren().add(trees_img_3);

                            tank.tank_img.setX(tank.tank_img.getX() - 5);
                        }
                        else if(tank.tank_img.getX() > 100){
                            tank.tank_img.setX(tank.tank_img.getX() - 5);
                        }

                        tank.tank_img.setRotate(270);
                        tank.tank_img.isSmooth();
                        break;
                    case RIGHT:

                        direction = 4;
                        bu_x = tank.tank_img.getX() + 40/2;
                        bu_y = tank.tank_img.getY() + 40/2;

                        m.setValueAt( (int)tank_kar_x, (int)tank_kar_y, "P" );
                        if((int)tank_kar_x  == 0 ){
                            m.setValueAt( (int)tank_kar_x, (int)tank_kar_y, "P" );
                        }else if(Character.compare(m.getValueAt( (int)tank_kar_x-1, (int)tank_kar_y ),'P' ) ==0 ){
                            m.setValueAt( (int)tank_kar_x-1, (int)tank_kar_y, "0" );
                        }
                        m.print();

                        if((int)cor_x == m.getSize()-1){
                            if(tank.tank_img.getX() < (100 + 50*m.getSize() -40)){
                                tank.tank_img.setX(tank.tank_img.getX() + 5);
                            }
                        }
                        else if ( Character.compare( m.getValueAt((int)cor_x +1, (int)cor_y), 'W' ) == 0 || Character.compare( m.getValueAt((int)cor_x +1, (int)cor_y_3), 'W' ) == 0){
                            System.out.println("fin Right");
                            if(tank.tank_img.getX() < (100 + 50*((int)cor_x+1) - 40)){
                                tank.tank_img.setX(tank.tank_img.getX() + 5);
                            }
                        }
                        else if ( Character.compare( m.getValueAt((int)cor_x +1, (int)cor_y), 'S' ) == 0 || Character.compare( m.getValueAt((int)cor_x +1, (int)cor_y_3), 'S' ) == 0){
                            System.out.println("fin Right");
                            if(tank.tank_img.getX() < (100 + 50*((int)cor_x+1) - 40)){
                                tank.tank_img.setX(tank.tank_img.getX() + 5);
                            }
                        }

                        else if ( Character.compare( m.getValueAt((int)cor_x +1, (int)cor_y), 'B' ) == 0 || Character.compare( m.getValueAt((int)cor_x +1, (int)cor_y_3), 'B' ) == 0){
                            System.out.println("fin Right");
                            if(tank.tank_img.getX() < (100 + 50*((int)cor_x+1) - 40)){
                                tank.tank_img.setX(tank.tank_img.getX() + 5);
                            }
                        }
                        else if ( Character.compare( m.getValueAt((int)cor_x +1, (int)cor_y), 'C' ) == 0 || Character.compare( m.getValueAt((int)cor_x +1, (int)cor_y_3), 'C' ) == 0){
                            System.out.println("fin Right");
                            if(tank.tank_img.getX() < (100 + 50*((int)cor_x+1) - 40)){
                                tank.tank_img.setX(tank.tank_img.getX() + 5);
                            }
                        }
                        else if ( Character.compare( m.getValueAt((int)cor_x +1, (int)cor_y), 'T' ) == 0){
                            System.out.println("fin Down");
                            Image trees_into_4 = null;
                            try {
                                trees_into_4 = new Image(new FileInputStream("C:\\Users\\User\\Downloads\\tree.jpeg"));
                            } catch (FileNotFoundException fileNotFoundException) {
                                fileNotFoundException.printStackTrace();
                            }
                            ImageView trees_img_4 = new ImageView(trees_into_4);

                            trees_img_4.setX(100 + 50*((int)cor_x + 1));
                            trees_img_4.setY(100 + 50*((int)cor_y));

                            trees_img_4.setFitHeight(50);
                            trees_img_4.setFitWidth(50);


                            pane.getChildren().add(trees_img_4);

                            tank.tank_img.setX(tank.tank_img.getX() + 5);
                        }
                        else if(tank.tank_img.getX() < (100 + 50*m.getSize() -40)){
                            tank.tank_img.setX(tank.tank_img.getX() + 5);
                        }

                        tank.tank_img.setRotate(90);
                        tank.tank_img.isSmooth();
                        break;
                    case SPACE:
                        String who = "P";
                        Bullet bu = new Bullet(bu_x, bu_y, direction, m, who);

                        System.out.println("Space");
                        boolean ok = m.getOk();
                        System.out.println("Live: " + live);
                        System.out.println("Main: " +ok);
                        if (ok == true){
                            System.out.println("Input");

                            System.out.println();
                            if (live == 0){
                                pane.getChildren().remove(client_tank);
                                m.setOk(false);
                            }
                            live--;
                            m.setOk(false);
                        }

                        pane.getChildren().add(bu);

                        pane.getChildren().remove(tank);
                        pane.getChildren().add(tank);

                        break;

                }
            });
        } ).start();


        Random rand = new Random();

        int[] dfsa = generate();
        client_tank = new Tank.Tank_fx(dfsa[0], dfsa[1]);
        m.setValueAt((int)((client_tank.tank_img.getX()-100)/50), (int)((client_tank.tank_img.getY()-100)/50), "C");
        System.out.println();
        m.print();
        pane.getChildren().add(client_tank);

//        bot_repat();
//        new Thread( ()->{
//            while (true){
//                Platform.runLater( ()->{
//                    System.out.println("On_it");
//                } );
//                try {
//                    pane.getChildren().add( new Bot(generate()[0], generate()[1],m) );
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//                try {
//                    Thread.sleep(10);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        } ).start();
        Bot bot = new Bot(generate()[0], generate()[1],m);
        pane.getChildren().add( bot );




        new Thread( ()->{
            client_tank.tank_img.setOnKeyPressed(e ->{

                        double cor_x =  ( client_tank.tank_img.getX()-100)/50;
                        double cor_y =  ( client_tank.tank_img.getY()-100)/50;

                        double cor_x_1 =  ( client_tank.tank_img.getX()-100)/50;
                        double cor_y_1 =  ( client_tank.tank_img.getY()-100)/50;

                        double cor_x_2 =  ( client_tank.tank_img.getX()-100 + 40)/50;
                        double cor_y_2 =  ( client_tank.tank_img.getY()-100)/50;

                        double cor_x_3 =  ( client_tank.tank_img.getX()-100)/50;
                        double cor_y_3 =  ( client_tank.tank_img.getY()-100 + 40)/50;

                        double cor_x_4 =  ( client_tank.tank_img.getX()-100 + 40)/50;
                        double cor_y_4 =  ( client_tank.tank_img.getY()-100 + 40)/50;

                        double client_kar_x = (cor_x_1 + cor_x_4)/2;
                        double client_kar_y = (cor_y_1 + cor_y_4)/2;

                        System.out.print  ("(" + cor_x_1 + " ; " + cor_y_1 + ") ");
                        System.out.println("(" + cor_x_2 + " ; " + cor_y_2 + ") ");
                        System.out.print  ("(" + cor_x_3 + " ; " + cor_y_3 + ") ");
                        System.out.println("(" + cor_x_4 + " ; " + cor_y_4 + ") ");
                        System.out.println();
                        switch (e.getCode()) {


                            case S:

                                direction = 1;
                                bu_x = client_tank.tank_img.getX() + 40/2;
                                bu_y = client_tank.tank_img.getY() + 40/2;

                                m.setValueAt( (int)client_kar_x, (int)client_kar_y, "C" );
                                if((int)client_kar_y  == 0 ){
                                    m.setValueAt( (int)client_kar_x, (int)client_kar_y, "C" );
                                }else if(Character.compare(m.getValueAt( (int)client_kar_x, (int)client_kar_y-1 ),'C' ) ==0 ){
                                    m.setValueAt( (int)client_kar_x, (int)client_kar_y-1, "0" );
                                }
                                m.print();

                                if((int)cor_y == m.getSize()-1){
                                    if(client_tank.tank_img.getY() < 100 + 50*m.getSize() -40){
                                        client_tank.tank_img.setY(client_tank.tank_img.getY() + 5);
                                    }
                                }
                                else if ( Character.compare( m.getValueAt((int)cor_x , (int)cor_y+1), 'W' ) == 0 || Character.compare( m.getValueAt((int)cor_x_2 , (int)cor_y+1), 'W' ) == 0){
                                    System.out.println("fin Down");
                                    if(client_tank.tank_img.getY() < (100 + 50*((int)cor_y+1) - 40)){
                                        client_tank.tank_img.setY(client_tank.tank_img.getY() + 5);
                                    }
                                }
                                else if ( Character.compare( m.getValueAt((int)cor_x , (int)cor_y+1), 'S' ) == 0 || Character.compare( m.getValueAt((int)cor_x_2 , (int)cor_y+1), 'S' ) == 0){
                                    System.out.println("fin Down");
                                    if(client_tank.tank_img.getY() < (100 + 50*((int)cor_y+1) - 40)){
                                        client_tank.tank_img.setY(client_tank.tank_img.getY() + 5);
                                    }
                                }
                                else if ( Character.compare( m.getValueAt((int)cor_x , (int)cor_y+1), 'B' ) == 0 || Character.compare( m.getValueAt((int)cor_x_2 , (int)cor_y+1), 'B' ) == 0){
                                    System.out.println("fin Down");
                                    if(client_tank.tank_img.getY() < (100 + 50*((int)cor_y+1) - 40)){
                                        client_tank.tank_img.setY(client_tank.tank_img.getY() + 5);
                                    }
                                }
                                else if ( Character.compare( m.getValueAt((int)cor_x , (int)cor_y+1), 'P' ) == 0 || Character.compare( m.getValueAt((int)cor_x_2 , (int)cor_y+1), 'P' ) == 0){
                                    System.out.println("fin Down");
                                    if(client_tank.tank_img.getY() < (100 + 50*((int)cor_y+1) - 40)){
                                        client_tank.tank_img.setY(client_tank.tank_img.getY() + 5);
                                    }
                                }
                                else if ( Character.compare( m.getValueAt((int)cor_x , (int)cor_y+1), 'T' ) == 0 ){
                                    System.out.println("fin Down");
                                    Image trees_into_1 = null;
                                    try {
                                        trees_into_1 = new Image(new FileInputStream("C:\\Users\\User\\Downloads\\tree.jpeg"));
                                    } catch (FileNotFoundException fileNotFoundException) {
                                        fileNotFoundException.printStackTrace();
                                    }
                                    ImageView trees_img_1 = new ImageView(trees_into_1);

                                    trees_img_1.setX(100 + 50*(int)cor_x);
                                    trees_img_1.setY(100 + 50*((int)cor_y+1));

                                    trees_img_1.setFitHeight(50);
                                    trees_img_1.setFitWidth(50);

                                    pane.getChildren().add(trees_img_1);

                                    client_tank.tank_img.setY(client_tank.tank_img.getY() + 5);

                                }
                                else if(client_tank.tank_img.getY() < 100 + 50*m.getSize() -40){
                                    client_tank.tank_img.setY(client_tank.tank_img.getY() + 5);

                                }




                                client_tank.tank_img.isSmooth();
                                client_tank.tank_img.setRotate(180);
                                break;
                            case W:
                                direction = 3;
                                bu_x = client_tank.tank_img.getX() + 40/2;
                                bu_y = client_tank.tank_img.getY() + 40/2;

                                m.setValueAt( (int)client_kar_x, (int)client_kar_y, "C" );
                                if((int)client_kar_y - m.getSize() + 1  == 0 ){
                                    m.setValueAt( (int)client_kar_x, (int)client_kar_y, "C" );
                                }else if(Character.compare(m.getValueAt( (int)client_kar_x, (int)client_kar_y+1 ),'C' ) ==0 ){
                                    m.setValueAt( (int)client_kar_x, (int)client_kar_y+1, "0" );
                                }
                                m.print();

                                if((int)cor_y == 0){
                                    if(client_tank.tank_img.getY() > 100){
                                        client_tank.tank_img.setY(client_tank.tank_img.getY() - 5);
                                    }
                                }
                                else if ( Character.compare( m.getValueAt((int)cor_x , (int)cor_y-1), 'W' ) == 0 || Character.compare( m.getValueAt((int)cor_x_2 , (int)cor_y-1), 'W' ) == 0){
                                    System.out.println("fin UP");
                                    if(client_tank.tank_img.getY() > (100 + 50*((int)cor_y))){
                                        client_tank.tank_img.setY(client_tank.tank_img.getY() - 5);
                                    }
                                }
                                else if ( Character.compare( m.getValueAt((int)cor_x , (int)cor_y-1), 'S' ) == 0 || Character.compare( m.getValueAt((int)cor_x_2 , (int)cor_y-1), 'S' ) == 0){
                                    System.out.println("fin UP");
                                    if(client_tank.tank_img.getY() > (100 + 50*((int)cor_y))){
                                        client_tank.tank_img.setY(client_tank.tank_img.getY() - 5);
                                    }
                                }
                                else if ( Character.compare( m.getValueAt((int)cor_x , (int)cor_y-1), 'B' ) == 0 || Character.compare( m.getValueAt((int)cor_x_2 , (int)cor_y-1), 'B' ) == 0){
                                    System.out.println("fin UP");
                                    if(client_tank.tank_img.getY() > (100 + 50*((int)cor_y))){
                                        client_tank.tank_img.setY(client_tank.tank_img.getY() - 5);
                                    }
                                }
                                else if ( Character.compare( m.getValueAt((int)cor_x , (int)cor_y-1), 'P' ) == 0 || Character.compare( m.getValueAt((int)cor_x_2 , (int)cor_y-1), 'P' ) == 0){
                                    System.out.println("fin UP");
                                    if(client_tank.tank_img.getY() > (100 + 50*((int)cor_y))){
                                        client_tank.tank_img.setY(client_tank.tank_img.getY() - 5);
                                    }
                                }
                                else if ( Character.compare( m.getValueAt((int)cor_x , (int)cor_y-1), 'T' ) == 0 ){
                                    System.out.println("fin Down");
                                    Image trees_into_2 = null;
                                    try {
                                        trees_into_2 = new Image(new FileInputStream("C:\\Users\\User\\Downloads\\tree.jpeg"));
                                    } catch (FileNotFoundException fileNotFoundException) {
                                        fileNotFoundException.printStackTrace();
                                    }
                                    ImageView trees_img_2 = new ImageView(trees_into_2);

                                    trees_img_2.setX(100 + 50*(int)cor_x);
                                    trees_img_2.setY(100 + 50*((int)cor_y-1));

                                    trees_img_2.setFitHeight(50);
                                    trees_img_2.setFitWidth(50);

                                    pane.getChildren().add(trees_img_2);

                                    client_tank.tank_img.setY(client_tank.tank_img.getY() - 5);
                                }
                                else if(client_tank.tank_img.getY() > 100){
                                    client_tank.tank_img.setY(client_tank.tank_img.getY() - 5);
                                }


                                client_tank.tank_img.isSmooth();
                                client_tank.tank_img.setRotate(0);
                                break;
                            case A:

                                direction = 2;
                                bu_x = client_tank.tank_img.getX() + 40/2;
                                bu_y = client_tank.tank_img.getY() + 40/2;

                                m.setValueAt( (int)client_kar_x, (int)client_kar_y, "C" );
                                if((int)client_kar_x - m.getSize() + 1  == 0 ){
                                    m.setValueAt( (int)client_kar_x, (int)client_kar_y, "C" );
                                }else if(Character.compare(m.getValueAt( (int)client_kar_x+1, (int)client_kar_y ),'C' ) ==0 ){
                                    m.setValueAt( (int)client_kar_x+1, (int)client_kar_y, "0" );
                                }
                                m.print();

                                if((int)cor_x == 0){
                                    if(client_tank.tank_img.getX() > 100){
                                        client_tank.tank_img.setX(client_tank.tank_img.getX() - 5);
                                    }
                                }
                                else if ( Character.compare( m.getValueAt((int)cor_x - 1, (int)cor_y), 'W' ) == 0 || Character.compare( m.getValueAt((int)cor_x - 1, (int)cor_y_3), 'W' ) == 0 ){
                                    System.out.println("fin Left");
                                    if(client_tank.tank_img.getX() > (100 + 50*((int)cor_x))){
                                        client_tank.tank_img.setX(client_tank.tank_img.getX() - 5);
                                    }
                                }
                                else if ( Character.compare( m.getValueAt((int)cor_x - 1, (int)cor_y), 'S' ) == 0 || Character.compare( m.getValueAt((int)cor_x - 1, (int)cor_y_3), 'S' ) == 0 ){
                                    System.out.println("fin Left");
                                    if(client_tank.tank_img.getX() > (100 + 50*((int)cor_x))){
                                        client_tank.tank_img.setX(client_tank.tank_img.getX() - 5);
                                    }
                                }
                                else if ( Character.compare( m.getValueAt((int)cor_x - 1, (int)cor_y), 'B' ) == 0 || Character.compare( m.getValueAt((int)cor_x - 1, (int)cor_y_3), 'B' ) == 0 ){
                                    System.out.println("fin Left");
                                    if(client_tank.tank_img.getX() > (100 + 50*((int)cor_x))){
                                        client_tank.tank_img.setX(client_tank.tank_img.getX() - 5);
                                    }
                                }
                                else if ( Character.compare( m.getValueAt((int)cor_x - 1, (int)cor_y), 'P' ) == 0 || Character.compare( m.getValueAt((int)cor_x - 1, (int)cor_y_3), 'P' ) == 0 ){
                                    System.out.println("fin Left");
                                    if(client_tank.tank_img.getX() > (100 + 50*((int)cor_x))){
                                        client_tank.tank_img.setX(client_tank.tank_img.getX() - 5);
                                    }
                                }
                                else if ( Character.compare( m.getValueAt((int)cor_x - 1, (int)cor_y), 'T' ) == 0 ){
                                    System.out.println("fin Down");
                                    Image trees_into_3 = null;
                                    try {
                                        trees_into_3 = new Image(new FileInputStream("C:\\Users\\User\\Downloads\\tree.jpeg"));
                                    } catch (FileNotFoundException fileNotFoundException) {
                                        fileNotFoundException.printStackTrace();
                                    }
                                    ImageView trees_img_3 = new ImageView(trees_into_3);

                                    trees_img_3.setX(100 + 50*((int)cor_x - 1));
                                    trees_img_3.setY(100 + 50*((int)cor_y));

                                    trees_img_3.setFitHeight(50);
                                    trees_img_3.setFitWidth(50);

                                    pane.getChildren().add(trees_img_3);

                                    client_tank.tank_img.setX(client_tank.tank_img.getX() - 5);
                                }
                                else if(client_tank.tank_img.getX() > 100){
                                    client_tank.tank_img.setX(client_tank.tank_img.getX() - 5);
                                }

                                client_tank.tank_img.setRotate(270);
                                client_tank.tank_img.isSmooth();
                                break;
                            case D:

                                direction = 4;
                                bu_x = client_tank.tank_img.getX() + 40/2;
                                bu_y = client_tank.tank_img.getY() + 40/2;

                                m.setValueAt( (int)client_kar_x, (int)client_kar_y, "C" );
                                if((int)client_kar_x  == 0 ){
                                    m.setValueAt( (int)client_kar_x, (int)client_kar_y, "C" );
                                }else if(Character.compare(m.getValueAt( (int)client_kar_x-1, (int)client_kar_y ),'C' ) ==0 ){
                                    m.setValueAt( (int)client_kar_x-1, (int)client_kar_y, "0" );
                                }
                                m.print();

                                if((int)cor_x == m.getSize()-1){
                                    if(client_tank.tank_img.getX() < (100 + 50*m.getSize() -40)){
                                        client_tank.tank_img.setX(client_tank.tank_img.getX() + 5);
                                    }
                                }
                                else if ( Character.compare( m.getValueAt((int)cor_x +1, (int)cor_y), 'W' ) == 0 || Character.compare( m.getValueAt((int)cor_x +1, (int)cor_y_3), 'W' ) == 0){
                                    System.out.println("fin Right");
                                    if(client_tank.tank_img.getX() < (100 + 50*((int)cor_x+1) - 40)){
                                        client_tank.tank_img.setX(client_tank.tank_img.getX() + 5);
                                    }
                                }
                                else if ( Character.compare( m.getValueAt((int)cor_x +1, (int)cor_y), 'S' ) == 0 || Character.compare( m.getValueAt((int)cor_x +1, (int)cor_y_3), 'S' ) == 0){
                                    System.out.println("fin Right");
                                    if(client_tank.tank_img.getX() < (100 + 50*((int)cor_x+1) - 40)){
                                        client_tank.tank_img.setX(client_tank.tank_img.getX() + 5);
                                    }
                                }

                                else if ( Character.compare( m.getValueAt((int)cor_x +1, (int)cor_y), 'B' ) == 0 || Character.compare( m.getValueAt((int)cor_x +1, (int)cor_y_3), 'B' ) == 0){
                                    System.out.println("fin Right");
                                    if(client_tank.tank_img.getX() < (100 + 50*((int)cor_x+1) - 40)){
                                        client_tank.tank_img.setX(client_tank.tank_img.getX() + 5);
                                    }
                                }
                                else if ( Character.compare( m.getValueAt((int)cor_x +1, (int)cor_y), 'P' ) == 0 || Character.compare( m.getValueAt((int)cor_x +1, (int)cor_y_3), 'P' ) == 0){
                                    System.out.println("fin Right");
                                    if(client_tank.tank_img.getX() < (100 + 50*((int)cor_x+1) - 40)){
                                        client_tank.tank_img.setX(client_tank.tank_img.getX() + 5);
                                    }
                                }
                                else if ( Character.compare( m.getValueAt((int)cor_x +1, (int)cor_y), 'T' ) == 0){
                                    System.out.println("fin Down");
                                    Image trees_into_4 = null;
                                    try {
                                        trees_into_4 = new Image(new FileInputStream("C:\\Users\\User\\Downloads\\tree.jpeg"));
                                    } catch (FileNotFoundException fileNotFoundException) {
                                        fileNotFoundException.printStackTrace();
                                    }
                                    ImageView trees_img_4 = new ImageView(trees_into_4);

                                    trees_img_4.setX(100 + 50*((int)cor_x + 1));
                                    trees_img_4.setY(100 + 50*((int)cor_y));

                                    trees_img_4.setFitHeight(50);
                                    trees_img_4.setFitWidth(50);


                                    pane.getChildren().add(trees_img_4);

                                    client_tank.tank_img.setX(client_tank.tank_img.getX() + 5);
                                }
                                else if(client_tank.tank_img.getX() < (100 + 50*m.getSize() -40)){
                                    client_tank.tank_img.setX(client_tank.tank_img.getX() + 5);
                                }

                                client_tank.tank_img.setRotate(90);
                                client_tank.tank_img.isSmooth();
                                break;
                            case B:
                                String who = "C";
                                Bullet bu = new Bullet(bu_x, bu_y, direction, m,who);

                                boolean ok = m.getOk();
                                System.out.println("H");
                                System.out.println("Live: " + c_live);
                                System.out.println("Main: " +ok);
                                if (ok){

                                    if (c_live == 0){
                                        pane.getChildren().remove(tank);
                                        m.setOk(false);
                                    }
                                    c_live--;
                                }

                                pane.getChildren().add(bu);

                                pane.getChildren().remove(client_tank);
                                pane.getChildren().add(client_tank);

                                break;

                            default:

                        }
                    }
            );
        } ).start();





//        new Thread( ()->{
//            try {
//                ServerSocket serverSocket = new ServerSocket(1234
//                );
//
//                Platform.runLater( ()->{
//                    System.out.println("On");
//                });
//
//                Socket socket = serverSocket.accept();
//
//                outputStream = new ObjectOutputStream(socket.getOutputStream());
//                inputStream = new ObjectInputStream(socket.getInputStream());
//                try {
//                    Object object = inputStream.readObject();
////                    client_tank = (ImageView) object;
//
//                    pane.getChildren().add(client_tank);
//                }catch (Exception e){
//                    System.out.println(e.getMessage());
//                }
//
//
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        } ).start();






//        new Thread( ()->{
//
//
//        } ).start();



        Scene scene = new Scene(pane, 800, 800);
        primaryStage.setTitle("ShowEllipse"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show();

        change(tank.tank_img);
        change(client_tank.tank_img);
    }
    private void bot_repat(){
        new Thread( ()->{
            Platform.runLater(()->{
                System.out.println("Bot_on");
            });
            try {
                pane.getChildren().add( new Bot(generate()[0], generate()[1],m) );

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(100000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            bot_repat();
        } ).start();
    }

    private int[] generate(){

        while (true){
            Random random = new Random();
            int[] inti = new int[2];
            inti[0] = random.nextInt(13);
            inti[1] = random.nextInt(13);
            String soem = String.valueOf(m.getValueAt( inti[0], inti[1]));
            if (soem.equals("0")){
                return inti;
            }
        }
    }

    private void change(ImageView here) {
        Platform.runLater(() -> {
            if (!here.isFocused()) {
                here.requestFocus();
                change(here);
            }
        });
    }

    private void fill(Pane pane, Map m) throws FileNotFoundException, InvalidMapException {
        for (int i=0; i<m.getSize(); i++){
            for (int j = 0; j<m.getSize(); j++){
                Image stell = new Image(new FileInputStream( "C:\\Users\\User\\Downloads\\steel.jpeg" ));
                ImageView stell_img = new ImageView(stell);

                stell_img.setX(100 + 50*i);
                stell_img.setY(100 + 50*j);

                stell_img.setFitHeight(50);
                stell_img.setFitWidth(50);

                Image brick = new Image(new FileInputStream("C:\\Users\\User\\Downloads\\brick.jpeg"));
                ImageView brick_img = new ImageView(brick);

                brick_img.setX(100 + 50*i);
                brick_img.setY(100 + 50*j);

                brick_img.setFitHeight(50);
                brick_img.setFitWidth(50);

                Image water = new Image(new FileInputStream("C:\\Users\\User\\Downloads\\water.jpeg"));
                ImageView water_img = new ImageView(water);

                water_img.setX(100 + 50*i);
                water_img.setY(100 + 50*j);

                water_img.setFitHeight(50);
                water_img.setFitWidth(50);


                Image trees = new Image(new FileInputStream("C:\\Users\\User\\Downloads\\tree.jpeg"));
                ImageView trees_img = new ImageView(trees);

                trees_img.setX(100 + 50*i);
                trees_img.setY(100 + 50*j);

                trees_img.setFitHeight(50);
                trees_img.setFitWidth(50);
                trees_img.translateZProperty().setValue(-200);
//                System.out.println(trees_img.scaleZProperty());

                Rectangle r = new Rectangle(100 + 50*i, 100 +50*j, 50, 50);


                if (Character.compare(m.getValueAt(i,j), '0') == 0){
                    r.setFill(Color.BLACK);
                    pane.getChildren().add(r);
                }else if( Character.compare(m.getValueAt(i,j), 'S') == 0 ){
                    pane.getChildren().add(stell_img);
                }else if( Character.compare(m.getValueAt(i,j), 'B') == 0 ){

                    pane.getChildren().add(brick_img);
                }else if( Character.compare(m.getValueAt(i,j), 'W') == 0 ){

                    pane.getChildren().add(water_img);
                }else if( Character.compare(m.getValueAt(i,j), 'T') == 0 ){
                    trees_img.setTranslateZ(20);
                    pane.getChildren().add(trees_img);
                }
                else if( Character.compare(m.getValueAt(i,j), 'P') == 0 ){
                    x = i;
                    y = j;
                    tank  = new Tank.Tank_fx(i,j);
                }
            }
        }

    }

    public static void main(String[] args) {
        launch(args);
    }


}

class Bullet extends Pane  implements Serializable{
    public double center_x;
    public double center_y;
    Circle circle_obj;
    final int radius = 5;
    Timeline animation;
    Map m;
    String[][] arr_k;
    int size ;
    String who;


    public Bullet(double bu_x, double bu_y, int d, Map m,String who) {
        this.center_x = bu_x;
        this.center_y = bu_y;
        this.m = m;
        size = m.getSize();
        this.who = who;

        circle_obj = new Circle(center_x, center_y, radius);
        circle_obj.setFill(Color.GREEN);
        getChildren().add(circle_obj);

        int direction = d;
        arr_k = m.m_karta;

        animation = new Timeline( new KeyFrame(Duration.millis(5), e -> on_bu(circle_obj, direction)));
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();
    }

    private void on_bu(Circle circle_obj, int direction) {
        double center_x = circle_obj.getCenterX();
        double center_y = circle_obj.getCenterY();
        int c_x = (int) ((center_x -100)/50);
        int c_y = (int) ((center_y -100)/50);

        switch (direction){
            case 1:
                if ( arr_k[c_x][c_y].equals("B") ){


                    m.setFire_kar(c_x, c_y);

                    if (m.fire_kar[c_x][c_y] == 4){
                        m.setValueAt(c_x, c_y, "0");
                        System.out.println("OK");
                        Rectangle re = new Rectangle(100 + c_x*50, 100 + c_y*50, 50, 50);


                        re.setFill(Color.rgb(0,0,0,1));
                        re.opacityProperty();

                        getChildren().add( re );
                    }
                    for (int i=0; i<m.getSize(); i++){
                        for (int j=0; j<m.getSize(); j++){
                            System.out.print(m.fire_kar[i][j]+" ");
                        }
                        System.out.println();
                    }


                    getChildren().remove(circle_obj);

                    animation.pause();
                }
                else if ( arr_k[c_x][c_y].equals("C") && who.equals("P")){

                    System.out.println("Live_OK: " + m.ok);
                    m.setOk(true);
                    m.setValueAt(c_x, c_y, "0");
                    System.out.println("Live_OK: " + m.ok);
                    getChildren().remove(circle_obj);

                    animation.pause();
                }
                else if ( arr_k[c_x][c_y].equals("P") && who.equals("C")){

                    System.out.println("Live_OK: " + m.ok);
                    m.setOk(true);
                    m.setValueAt(c_x, c_y, "0");
                    System.out.println("Live_OK: " + m.ok);
                    getChildren().remove(circle_obj);

                    animation.pause();
                }
                else if ( arr_k[c_x][c_y].equals("S") ){

                    getChildren().remove(circle_obj);

                    animation.pause();
                }
                else if (center_y < 100 + 50*size - 5){
                    center_y += 1;


                    System.out.println ((int)(center_x-200)/50 + ", " + (int)(center_y-200)/50 );

                    System.out.println();



                }
                else if (center_y >= 100 + 50*size - 5){
                    center_x += 0;
                    center_y += 0;
                    getChildren().remove(circle_obj);
                }
                break;
            case 2:
                if ( arr_k[c_x][c_y].equals("B") ){

                    m.setFire_kar(c_x, c_y);

                    if (m.fire_kar[c_x][c_y] == 4){
                        m.setValueAt(c_x, c_y, "0");
                        System.out.println("OK");
                        Rectangle re = new Rectangle(100 + c_x*50, 100 + c_y*50, 50, 50);


                        re.setFill(Color.rgb(0,0,0,1));
                        re.opacityProperty();

                        getChildren().add( re );
                    }
                    for (int i=0; i<m.getSize(); i++){
                        for (int j=0; j<m.getSize(); j++){
                            System.out.print(m.fire_kar[i][j]+" ");
                        }
                        System.out.println();
                    }


                    getChildren().remove(circle_obj);

                    animation.pause();
                }
                else if ( arr_k[c_x][c_y].equals("C") && who.equals("P")){
                    System.out.println("Live_OK: " + m.ok);
                    m.setOk(true);
                    m.setValueAt(c_x, c_y, "0");
                    System.out.println("Live_OK: " + m.ok);
                    getChildren().remove(circle_obj);

                    animation.pause();
                }
                else if ( arr_k[c_x][c_y].equals("P") && who.equals("C") ){

                    System.out.println("Live_OK: " + m.ok);
                    m.setOk(true);
                    m.setValueAt(c_x, c_y, "0");
                    System.out.println("Live_OK: " + m.ok);
                    getChildren().remove(circle_obj);

                    animation.pause();
                }
                else if ( arr_k[c_x][c_y].equals("S") ){

                    getChildren().remove(circle_obj);

                    animation.pause();
                }
                else if (center_x > 100 + 5){
                    center_x -= 1;

                    System.out.println ((int)(center_x-200)/50 + ", " + (int)(center_y-200)/50 );

                    System.out.println();



                }
                else if (center_x <= 100 + 5){
                    center_x += 0;
                    center_y += 0;
                    getChildren().remove(circle_obj);
                }
                break;
            case 3:
                if ( arr_k[c_x][c_y].equals("B") ){

                    m.setFire_kar(c_x, c_y);

                    if (m.fire_kar[c_x][c_y] == 4){
                        m.setValueAt(c_x, c_y, "0");
                        System.out.println("OK");
                        Rectangle re = new Rectangle(100 + c_x*50, 100 + c_y*50, 50, 50);


                        re.setFill(Color.rgb(0,0,0,1));
                        re.opacityProperty();

                        getChildren().add( re );
                    }
                    for (int i=0; i<m.getSize(); i++){
                        for (int j=0; j<m.getSize(); j++){
                            System.out.print(m.fire_kar[i][j]+" ");
                        }
                        System.out.println();
                    }


                    getChildren().remove(circle_obj);

                    animation.pause();
                }
                else if ( arr_k[c_x][c_y].equals("C") && who.equals("P") ){

                    System.out.println("Live_OK: " + m.ok);
                    m.setOk(true);
                    m.setValueAt(c_x, c_y, "0");
                    System.out.println("Live_OK: " + m.ok);

                    getChildren().remove(circle_obj);

                    animation.pause();
                }
                else if ( arr_k[c_x][c_y].equals("P") && who.equals("C")){

                    System.out.println("Live_OK: " + m.ok);
                    m.setOk(true);
                    m.setValueAt(c_x, c_y, "0");
                    System.out.println("Live_OK: " + m.ok);
                    getChildren().remove(circle_obj);

                    animation.pause();
                }
                else if ( arr_k[c_x][c_y].equals("S") ){

                    getChildren().remove(circle_obj);

                    animation.pause();
                }
                else if (center_y > 100 + 5){
                    center_y -= 1;
                    System.out.println ((int)(center_x-200)/50 + ", " + (int)(center_y-200)/50 );

                    System.out.println();

                }
                else if (center_y <= 100 + 5){
                    center_x += 0;
                    center_y += 0;
                    getChildren().remove(circle_obj);
                }
                break;
            case 4:
                if ( arr_k[c_x][c_y].equals("B") ){

                    m.setFire_kar(c_x, c_y);

                    if (m.fire_kar[c_x][c_y] == 4){
                        m.setValueAt(c_x, c_y, "0");
                        System.out.println("OK");
                        Rectangle re = new Rectangle(100 + c_x*50, 100 + c_y*50, 50, 50);


                        re.setFill(Color.rgb(0,0,0,1));
                        re.opacityProperty();

                        getChildren().add( re );
                    }
                    for (int i=0; i<m.getSize(); i++){
                        for (int j=0; j<m.getSize(); j++){
                            System.out.print(m.fire_kar[i][j]+" ");
                        }
                        System.out.println();
                    }


                    getChildren().remove(circle_obj);

                    animation.pause();
                }
                else if ( arr_k[c_x][c_y].equals("C") && who.equals("P")){

                    System.out.println("Live_OK: " + m.ok);
                    m.setOk(true);
                    m.setValueAt(c_x, c_y, "0");
                    System.out.println("Live_OK: " + m.ok);
                    getChildren().remove(circle_obj);

                    animation.pause();
                }
                else if ( arr_k[c_x][c_y].equals("P") && who.equals("C")){

                    System.out.println("Live_OK: " + m.ok);
                    m.setOk(true);
                    m.setValueAt(c_x, c_y, "0");
                    System.out.println("Live_OK: " + m.ok);
                    getChildren().remove(circle_obj);

                    animation.pause();
                }
                else if ( arr_k[c_x][c_y].equals("S") ){

                    getChildren().remove(circle_obj);

                    animation.pause();
                }
                else if (center_x < 100 + 50*size - 5){
                    center_x += 1;
                    System.out.println ((int)(center_x-200)/50 + ", " + (int)(center_y-200)/50 );

                    System.out.println();



                }
                else if (center_x >= 100 + 50*size - 5){
                    center_x += 0;
                    center_y += 0;
                    getChildren().remove(circle_obj);
                }
                break;
        }

        circle_obj.setCenterX(center_x);
        circle_obj.setCenterY(center_y);
    }

    @Override
    public Node getStyleableNode() {
        return null;
    }
}

class Tank extends MyPlayer implements Serializable{
    static class Tank_fx extends Pane  implements Serializable{
        public final ImageView tank_img;
        private Timeline animation;
        private int x;
        private int y;

        public Tank_fx(int i, int j) throws FileNotFoundException, InvalidMapException {
            Map m = new Map();
            Image tank = new Image(new FileInputStream("C:\\Users\\User\\Downloads\\tank.jpeg"));
            tank_img = new ImageView(tank);
            this.x = i;
            this.y = j;
            tank_img.setX(100 + 50*i);
            tank_img.setY(100 + 50*j);
            tank_img.setFitHeight(40);
            tank_img.setFitWidth(40);



            getChildren().add(tank_img);
        }

    }
}

class Bot extends Pane{
    private int x;
    private int y;
    private ImageView scrv_bot;
    private Timeline animation;

    Bot(int x, int y, Map m) throws FileNotFoundException {
        this.x = x;
        this.y = y;
        Image scr_bot = new Image(new FileInputStream("C:\\Users\\User\\Desktop\\Типа мипа\\tank-enemy.jpg"));
        scrv_bot = new ImageView(scr_bot);
        scrv_bot.setX(100 + 50*x);
        scrv_bot.setY(100 + 50*y);
        scrv_bot.setFitHeight(40);
        scrv_bot.setFitWidth(40);

        getChildren().add(scrv_bot);

        new Thread( ()->{
            animation = new Timeline( new KeyFrame(Duration.millis(50), e -> find_player_bot(x,y,m)));
            animation.setCycleCount(Timeline.INDEFINITE);
            animation.play();
        } ).start();
    }

    private void find_player_bot(int x, int y, Map m) {

    }


}


/*
Enter your code here.
Create all the necessary classes and methods as per the requirements.
*/
/*
DO NOT MODIFY THIS PART!!!
Input and Output has been done for you.
Various conditions are meant to check individual classes separately.
You still need to implement completely all the necessary classes with their corresponding methods,
but the correctness for each class is checked individually.
In other words, you already get some partial points
for implementing some classes completely and correctly,
even if other classes are complete but still may not work properly.
*/
//import java.util.Scanner;



class Solution{

    public static void main(String[] args) {
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
            catch(Exception e){ // custom exception
                System.out.println(e.getMessage());
                System.exit(0);
            }

            game.addPlayer(player);

            // Make the player move based on the commands given in the input
            String moves = input.next();
            char move;
//            System.out.println(moves);
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
            System.out.println("Row: " + playerPosition.getX());
            System.out.println("Col: " + playerPosition.getY());
        }
    }
}

class Position  implements Serializable{
    int a;
    int b;

    Position(){

    }

    Position(int a, int b){
        this.a = a;
        this.b = b;
    }

    public void setX(int a) {
        this.a = a;
    }

    public void setY(int b) {
        this.b = b;
    }

    public int getX() {
        return a;
    }

    public int getY() {
        return b;
    }

    public boolean equals(Position p){
        if(a == p.getX() && b == p.getY()){
            return true;
        }
        return false;
    }

    public String toString(){
        return "("+a + "," + b + ")";
    }

}


class Map  implements Serializable{
    public Scanner gone;
    public int width;
    public int[][] fire_kar;

    public String[] m_karta_whole;
    public String[][] m_karta;

    public boolean ok;

    Map(Scanner gone) throws InvalidMapException {
        this.gone = gone;
        kar_activate();
        karta();
    }

    public Map() {

    }

    int getSize(){
        return width;
    }

    char getValueAt(int a, int b){
        return m_karta[a][b].charAt(0);
    }

    public void setValueAt(int a, int b, String ruin){
        this.m_karta[a][b] = ruin;
    }

    public void setFire_kar(int a, int b) {
        fire_kar[a][b]++;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }
    public boolean getOk(){
        return ok;
    }
    //    void setPlayers(int a, int b,String player){ this.m_karta[a][b] = player; }

    public void print(){
        for (int i=0; i<width; i++){
            for (int j=0; j<width; j++){
                System.out.print(m_karta[i][j]+ " ");
            }
            System.out.println();
        }
    }

    void kar_activate() throws InvalidMapException {
        this.width = gone.nextInt();

        this.fire_kar = new int[width][width];

        if(width <= 0) {
            throw new InvalidMapException("Map size can not be zero");
        }
        String[] ka = new String[width];
        gone.nextLine();

        int count = 0;
        String[][] a = new String[width][width];

        while (count != width){
            ka[count] = gone.nextLine();
            if(ka[count].contains("L") || ka[count].contains("R") || ka[count].contains("D")|| ka[count].contains("U")){
                throw new InvalidMapException("Not enough map elements");
            }
            a[count] = ka[count].split(" ");
            if(a[count].length < width){
                throw new InvalidMapException("Not enough map elements");
            }
            count++;
        }

        String[] karta_whole = new String[count];

        for (int i=0; i<count; i++){
            karta_whole[i] = ka[i];
//            System.out.println(karta_whole[i]);
        }

        m_karta_whole = karta_whole;
    }

    void karta(){
        m_karta = new String[getSize()][getSize()];
        for(int i=0; i<getSize(); i++){
            m_karta[i] = split(m_karta_whole[i]);
        }

        this.m_karta = m_karta;
    }

    String[] split(String a){
        return a.split(" ");
    }

    String search(){
        for (int i=0; i<width; i++){
            for (int j=0; j<width; j++){
                if(m_karta[i][j].equals("P")){
                    return i + " " + j;
                }
            }
        }
        return "0 0";
    }



}

interface Player {
    public void moveRight();
    public void moveLeft();
    public void moveUp();
    public void moveDown();
    public Position getPosition();
    public void setMap(Map kar);
}

class MyPlayer implements Player ,Serializable{
    Map kar;
    private Position p;
    int pa;
    int pb;

    public void setMap(Map kar) {
        this.kar = kar;
        another();
//        System.out.println(pa);
//        System.out.println(pb);

        this.p = new Position(pa,pb);
    }

    void another(){
        String[] daa = new String[2];
        String word = kar.search();
        daa = word.split(" ");
        this.pa = Integer.parseInt(daa[0]);
        this.pb = Integer.parseInt(daa[1]);

    }

    public void moveRight(){
        int x = p.getX();
        int y = p.getY();

//        kar.print();
//        System.out.println();

        if( y == kar.getSize()-1 ){
            p.setY(y);
        }else if (kar.m_karta[x][y+1].equals("1")){
            p.setY(y);
        } else {
            p.setY(y+1);
        }
    }

    public void moveLeft(){
        int x = p.getX();
        int y = p.getY();
        if( y == 0 ){
            p.setY(y);
        }else if (kar.m_karta[x][y-1].equals("1")){
            p.setY(y);
        } else {
            p.setY(y-1);
        }

    }

    public void moveUp(){
        int x = p.getX();
        int y = p.getY();
        if( x == 0 ){
            p.setX(x);
        }else if (kar.m_karta[x-1][y].equals("1")){
            p.setX(x);
        } else {
            p.setX(x-1);
        }

    }

    public void moveDown(){
        int x = p.getX();
        int y = p.getY();
        if( x == kar.getSize()-1 ){
            p.setX(x);
        }else if (kar.m_karta[x+1][y].equals("1")){
            p.setX(x);
        } else {
            p.setX(x+1);
        }

    }

    public Position getPosition() {
        return p;
    }
}

class Game  implements Serializable{
    Map kar;
    Player play;

    public Game(Map kar){
        this.kar = kar;
    }

    public void setMap(Map kar) {
        this.kar = kar;

    }

    public void addPlayer(Player player){
        this.play = player;
        kar.search();
        play.setMap(kar);
    }
}

class InvalidMapException extends Exception  implements Serializable{
    String except;
    public InvalidMapException(String except){
        this.except = except;
    }

    @Override
    public String getMessage() {
        return except;
    }
}
