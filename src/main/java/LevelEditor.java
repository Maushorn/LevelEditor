import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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

        //Setting up working area
//        Canvas canvas = new Canvas(500,500);
//        Group canvasGroup = new Group(canvas);

        canvas = new Pane();
        canvas.setPrefSize(500,500);

        URI imageURI = Paths.get("C:\\myrepos\\FencingPrincess\\Entwürfe\\Princess-1_big.png").toUri();
        Image testImage = new Image(imageURI.toString());

        URI imageURI2 = Paths.get("C:\\myrepos\\FencingPrincess\\Entwürfe\\Princess-1_big.png").toUri();
        Image testImage2 = new Image(imageURI2.toString());


        Scene canvasScene = new Scene(canvas);
        canvasScene.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.SHIFT)
                shiftPressed = true;
        });
        canvasScene.setOnKeyReleased(event -> {
            if(event.getCode() == KeyCode.SHIFT)
                shiftPressed = false;
        });

        ImageView imageView = new ImageView(testImage);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(256);
        canvas.getChildren().addAll(imageView);
        imageView.setX(-50);

        addToDragObjects(imageView);

//        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
//      TODO This works for resizing the image:
//      graphicsContext.drawImage(testImage, 0, 0, testImage.getWidth()*2, testImage.getHeight()*2);


        Scene mainMenuScene = new Scene(vBox);
        primaryStage.setScene(mainMenuScene);

        startButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                primaryStage.setScene(canvasScene);
            }
        });


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

        //TODO
//        canvas.setOnDragDropped(new EventHandler<DragEvent>() {
//            public void handle(DragEvent event) {
//                event.getS
//            }
//        });

        primaryStage.show();
    }

    private void addToDragObjects(ImageView imageView) {
        imageView.setOnDragDetected(event -> {
            if(shiftPressed)
                copyDrag = true;
            else
                copyDrag = false;
            currentlyDraggedImageView = imageView;
            dragStartX = imageView.getX();
            dragStartY = imageView.getY();
            imageView.setStyle("-fx-opacity: 0.5;");

            Dragboard dragboard = imageView.startDragAndDrop(TransferMode.ANY);
            ClipboardContent clipboardContent = new ClipboardContent();
            clipboardContent.putImage(imageView.getImage());
            dragboard.setContent(clipboardContent);
            event.consume();
        });

    }

}
