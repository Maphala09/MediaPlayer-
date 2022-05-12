package com.example.demo7;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    @FXML
    MediaView mediaView;
    @FXML
    private Button play,stop,pause;

    MediaPlayer player;
    @FXML
    private Slider Slidergress,volume;
    @FXML
    private Media media;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
            String video = getClass().getResource("bolo.mp4").toExternalForm();
            media = new Media(video);
            player = new MediaPlayer(media);
            mediaView.setMediaPlayer(player);
            player.currentTimeProperty().addListener(new ChangeListener<Duration>() {
                @Override
                public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                    Slidergress.setValue(newValue.toSeconds());

                }
            });
        Slidergress.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                player.seek(Duration.seconds(Slidergress.getValue()));
            }
        });
        Slidergress.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                player.seek(Duration.seconds(Slidergress.getValue()));
            }
        });
        player.setOnReady(new Runnable() {
            @Override
            public void run() {
                Duration total = media.getDuration();
                Slidergress.setMax(total.toSeconds());
            }
        });
        volume.setValue(player.getVolume()*100);
        volume.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                player.setVolume(volume.getValue()/100);
            }
        });
    }
    public void play(){
        player.play();
        player.setRate(1);
    }
    public void pause(){
        player.pause();
    }
    public void stop(){
        player.stop();
    }
    public void slowRate(){
        player.setRate(0.5);
    }
    public void fastForward(){
        player.setRate(2);
    }
    public void skip(){
        player.seek(player.getCurrentTime().add(Duration.seconds(10)));
    }
    public void back(){
        player.seek(player.getCurrentTime().add(Duration.seconds(-10)));
    }
}
