package com.maushorn.editor;

import com.maushorn.level.Level;
import com.maushorn.level.LevelElement;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.NonNull;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URI;
import java.nio.file.Paths;
import java.util.HashMap;


public class LevelEditor extends Application {

//    double dragStartX; //TODO: unnecessary 2 lines?
//    double dragStartY;
    ImageView lastImageView;
    ImageView currentImageView;
    Image currentImage;
    Level level;
    LevelElement currentLevelElement;
    Pane canvas;
    boolean shiftPressed;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage primaryStage) {

        //TODO: Testing
        level = new Level("Testlevel");
//        TODO: Test xml-binding
//        level.getLevelelements().put(new ImageView(),new LevelElement("Player1"));
//        try {
//            JAXBContext context = JAXBContext.newInstance(Level.class);
//            Marshaller marshaller = context.createMarshaller();
//            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//            marshaller.marshal(level, new FileOutputStream("testlevel.xml"));
//        } catch (JAXBException e) {
//            e.printStackTrace();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
        //TODO: Test xml-binding ENDE

        //Setting up main menu
        Button startButton = new Button("Start");
        startButton.setDefaultButton(true);
        VBox vBox = new VBox(5);
        vBox.setPadding(new Insets(10));
        vBox.getChildren().add(startButton);

        canvas = new Pane();
        canvas.setPrefSize(500,500);

        //Check if SHIFT is pressed
        Scene canvasScene = new Scene(canvas);
        canvasScene.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.SHIFT)
                shiftPressed = true;
        });
        canvasScene.setOnKeyReleased(event -> {
            if(event.getCode() == KeyCode.SHIFT)
                shiftPressed = false;
        });

        //TODO: remove these lines when importing from files is implemented
        URI testImageURI = Paths.get("C:\\myrepos\\FencingPrincess\\Entwürfe\\Princess-1_big.png").toUri();
        Image testImage = new Image(testImageURI.toString());
        ImageView testImageView = new ImageView(testImage);
        canvas.getChildren().add(testImageView);
        currentImageView = testImageView;
        addToDragObjects(testImageView);
        LevelElement testElement = new LevelElement("Test");
        testElement.setSpriteURI(Paths.get("C:\\myrepos\\FencingPrincess\\Entwürfe\\Princess-1_big.png").toUri());
        level.getLevelelements().put(testImageView, testElement);

        //Configuration for Start Menu
        Scene mainMenuScene = new Scene(vBox);
        startButton.setOnAction(event -> primaryStage.setScene(canvasScene));

        canvas.setOnDragOver(event -> event.acceptTransferModes(TransferMode.ANY));
        canvas.setOnDragDropped(event -> {
            //TODO: Maybe implement check on file type.
            if(event.getGestureSource() == null && event.getDragboard().hasFiles()){
                ClipboardContent clipboardContent = new ClipboardContent();
                URI imageURI = Paths.get(event.getDragboard().getFiles().get(0).getPath()).toUri();
                currentImage = new Image(imageURI.toString());
                clipboardContent.putImage(currentImage);
                event.getDragboard().setContent(clipboardContent);
                currentImageView = new ImageView(currentImage);
                level.getLevelelements().put(currentImageView, new LevelElement(Paths.get(imageURI).getFileName().toString()));
            }
            if(event.getDragboard().hasImage()) {
                //Since the Drop gesture determines the upper left corner of the destination the image has to be placed
                //half its height higher and half its width left of the mouse cursor.
                currentImageView.setX(event.getX() - event.getDragboard().getImage().getWidth()/2);
                currentImageView.setY(event.getY() - event.getDragboard().getImage().getHeight()/2);

                currentLevelElement.getCoordinates().setX((float)currentImageView.getX());
                currentLevelElement.getCoordinates().setY((float)currentImageView.getY());

                if (!canvas.getChildren().contains(currentImageView))
                    canvas.getChildren().add(currentImageView);
                addToDragObjects(currentImageView);
                if(!level.getLevelelements().containsKey(currentImageView))
                    level.getLevelelements().put(currentImageView, currentLevelElement);
                }
        });
        primaryStage.setScene(mainMenuScene);
        primaryStage.show();
    }


    private void addToDragObjects(@NonNull ImageView imageView) {
        imageView.setOnDragDetected(event -> {
            lastImageView = imageView;
            lastImageView.setStyle("-fx-opacity: 0.5;");
            currentImage = imageView.getImage();
            currentLevelElement = level.getLevelelements().get(currentImageView);
            Dragboard dragboard;
            if(shiftPressed) {
                dragboard = imageView.startDragAndDrop(TransferMode.COPY);
//                currentImage = new Image(currentLevelElement.getSpriteURI().toString());
                currentImageView = new ImageView(currentImage);
                addToDragObjects(currentImageView);
                //TODO: Implement name input.
                currentLevelElement = new LevelElement("Testname");
            }
            else {
                dragboard = imageView.startDragAndDrop(TransferMode.MOVE);
                currentImage = imageView.getImage();
                currentImageView = imageView;
            }
            ClipboardContent clipboardContent = new ClipboardContent();
            clipboardContent.putImage(currentImage);
            dragboard.setContent(clipboardContent);
            event.consume();
        });
        imageView.setOnDragDone(event -> {
            lastImageView.setStyle("-fx-opacity: 1.0;");
            //TODO: Testoutput
            level.getLevelelements().forEach((image, levelElement) -> System.out.println(levelElement.getName() + " " + levelElement.getCoordinates()));

            event.consume();
        });

    }

}
