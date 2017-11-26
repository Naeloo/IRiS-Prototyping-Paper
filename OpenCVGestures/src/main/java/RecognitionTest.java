import org.bytedeco.javacpp.RealSense;
import org.bytedeco.javacpp.RealSense.context;
import org.bytedeco.javacpp.RealSense.device;

/**
 * Created by Leonhard on 22.11.2017.
 */

public class RecognitionTest {
    public static void main(String[] args){
        System.out.println("Hello Maven!");

        RealSense.context rContext = new RealSense.context();
        System.out.println("Connected cams: " + rContext.get_device_count());
    }
}
