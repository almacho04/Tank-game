package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import sample.Player;
import sample.Tank;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class BotPlayer extends Tank implements Player {


    static class Into_BotPlayer extends Pane{
        public int bot_x;
        public int bot_y;

        public int HEIGHT = 100;
        public int WIDTH = 100;

        private int Bot_Width_1 = 40;
        private int Bot_Height_1 = 40;

        private ImageView imgv_bot;
        Timeline animation;

        public Into_BotPlayer(int bot_x, int bot_y) throws FileNotFoundException {
            this.bot_x = bot_x;
            this.bot_y = bot_y;

            Image img_bot = new Image(new FileInputStream("C:\\Users\\User\\Desktop\\Типа мипа\\tank-enemy.jpg"));
            imgv_bot = new ImageView(img_bot);
            imgv_bot.setX(WIDTH + 50*bot_x);
            imgv_bot.setY(HEIGHT + 50*bot_y);
            imgv_bot.setFitHeight(Bot_Width_1);
            imgv_bot.setFitWidth(Bot_Height_1);

            getChildren().add(imgv_bot);

            new Thread( ()->{
                animation = new Timeline( new KeyFrame(Duration.millis(50), e -> move_bot(imgv_bot)));
                animation.setCycleCount(Timeline.INDEFINITE);
                animation.play();
            } ).start();

        }
    }

    public static void move_bot(ImageView img_bot){
        if (img_bot.getX() < 1000 && img_bot.getX()> 100){
            img_bot.setX( img_bot.getX() + 5 );
        }else if (img_bot.getX() > 1000 || img_bot.getX() < 100){
            img_bot.setX( img_bot.getX() - 5 );
        }

    }

//    @Override
//    public void move_tank(String code, int[][] count) {
//
//    }
}
