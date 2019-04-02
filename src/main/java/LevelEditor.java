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

import java.net.URI;
import java.nio.file.Paths;


public class LevelEditor extends Application {

    double dragStartX;
    double dragStartY;
    ImageView currentlyDraggedImageView;
    Pane canvas;
    boolean shiftPressed;
    boolean copyDrag;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage primaryStage) {

        //Setting up main menu
        Button startButton = new Button("Start");
        startButton.setDefaultButton(true);
        VBox vBox = new VBox(5);
        vBox.setPadding(new Insets(10));
        vBox.getChildren().add(startButton);

        canvas = new Pane();
        canvas.setPrefSize(500,500);



        Scene canvasScene = new Scene(canvas);
        canvasScene.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.SHIFT)
                shiftPressed = true;
        });
        canvasScene.setOnKeyReleased(event -> {
            if(event.getCode() == KeyCode.SHIFT)
                shiftPressed = false;
        });

        //TODO: remove these 2 lines when importing from files is implemented
        URI imageURI = Paths.get("C:\\myrepos\\FencingPrincess\\Entwürfe\\Princess-1_big.png").toUri();
        Image testImage = new Image(imageURI.toString());
        ImageView imageView = new ImageView(testImage);
        canvas.getChildren().addAll(imageView);
        addToDragObjects(imageView);

        //Configuration for Start Menu
        Scene mainMenuScene = new Scene(vBox);
        startButton.setOnAction(event -> primaryStage.setScene(canvasScene));

        canvas.setOnDragOver(event -> event.acceptTransferModes(TransferMode.COPY_OR_MOVE));
        canvas.setOnDragDropped(event -> {
            if(event.getDragboard().hasImage()) {
                ImageView tempImageView = new ImageView(event.getDragboard().getImage());
                //Since the Drop gesture determines the upper left corner of the destination the image has to be placed
                //half its height higher and half its width left of the mouse cursor.
                tempImageView.setX(event.getX() - event.getDragboard().getImage().getWidth()/2);
                tempImageView.setY(event.getY() - event.getDragboard().getImage().getHeight()/2);
                canvas.getChildren().add(tempImageView);
                addToDragObjects(tempImageView);
                currentlyDraggedImageView.setStyle("-fx-opacity: 1.0");
                if(!copyDrag)
                    canvas.getChildren().remove(currentlyDraggedImageView);
                //TODO: remove this line
                System.out.println(canvas.getChildren().size());
            }
        });

        primaryStage.setScene(mainMenuScene);
        primaryStage.show();
    }


    private void addToDragObjects(@NonNull ImageView imageView) {
        imageView.setOnDragDetected(event -> {
            if(shiftPressed)
                copyDrag = true;
            else
                copyDrag = false;
            currentlyDraggedImageView = imageView;
            dragStartX = imageView.getX();
            dragStartY = imageView.getY();
            imageView.setStyle("-fx-opacity: 0.5;");
            Dragboard dragboard = imageView.startDragAndDrop(TransferMode.COPY_OR_MOVE);
            ClipboardContent clipboardContent = new ClipboardContent();
            clipboardContent.putImage(imageView.getImage());
            dragboard.setContent(clipboardContent);
            event.consume();
        });
        imageView.setOnDragDone(event -> {
            imageView.setStyle("-fx-opacity: 1.0;");
        });

    }

}
