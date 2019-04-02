import javafx.scene.image.ImageView;
import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Assert;
import org.junit.Rule;
import org.testng.annotations.Test;

public class LevelEditorTest extends EasyMockSupport {

    @Rule
    public EasyMockRule rule = new EasyMockRule(this);

    @Mock
    private ImageView imageView;

    @TestSubject
    private LevelEditor levelEditor = new LevelEditor();

    @Test
    public void addToDragObjectsTest(ImageView imageView){
        imageView.setOnDragDetected(event -> {});
        imageView.setOnDragDone(event -> {});
        replayAll();
//        levelEditor.addToDragObjects(imageView);
        verifyAll();
    }

}
