package hauptfeld.bsa.gestures;

import hauptfeld.bsa.gestures.visualisation.CanvasController;
import org.bytedeco.javacpp.RealSense;
import org.bytedeco.javacpp.RealSense.context;
import org.bytedeco.javacpp.RealSense.device;
import org.bytedeco.javacpp.indexer.UByteBufferIndexer;
import org.bytedeco.javacpp.indexer.UByteRawIndexer;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.javacv.RealSenseFrameGrabber;

import javax.swing.*;
import java.awt.*;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Leonhard on 22.11.2017.
 * Most Awesome of files
 */

public class RecognitionTest {
    public static void main(String[] args){
        System.out.println("Hello RealSense!");

        //RealSense.context rContext = new RealSense.context();
        //System.out.println("Connected cams: " + rContext.get_device_count());
        //device rdev = rContext.get_device(0);
        //System.out.println(rdev.get_serial());
        final RealSenseFrameGrabber grabber = new RealSenseFrameGrabber(0);
        //System.out.println("Connected cams: " + grabber.getRealSenseDevice().get_name().toString());

        System.out.println("Cam data:");

        //grabber.setImageWidth(1280);
        //grabber.setImageHeight(720);

        grabber.enableColorStream();
        grabber.enableDepthStream();
        grabber.enableIRStream();


        System.out.println("Color Stream: " + grabber.getImageWidth() + "x" + grabber.getImageHeight() + " " + grabber.getFrameRate() + "FPS");
        System.out.println("Depth Stream: " + grabber.getDepthImageWidth() + "x" + grabber.getDepthImageHeight() + " " + grabber.getDepthFrameRate() + "FPS");
        System.out.println("IR Stream: " + grabber.getIRImageWidth() + "x" + grabber.getIRImageHeight() + " " + grabber.getIRFrameRate() + "FPS");

        /*CanvasFrame canvas = new CanvasFrame("Video Image");
        canvas.setCanvasSize(grabber.getDepthImageWidth(), grabber.getDepthImageHeight());
        canvas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);*/

        final CanvasController cc = new CanvasController(0.6);

        System.out.println("Starting grabbing");

        try {

            RealSenseFrameGrabber.tryLoad();
            grabber.start();

            grabber.setDefaultPreset();
            grabber.setColorEnableAutoExposure(255);
            grabber.setColorEnableAutoWhiteBalance(255);
            //grabber.setColorExposure(500);
        }catch(Exception ex) {
            ex.printStackTrace();
        }

        opencv_core.IplImage videoImg;
        opencv_core.IplImage depthImg;
        opencv_core.IplImage irImg;
        final OpenCVFrameConverter.ToIplImage a = new OpenCVFrameConverter.ToIplImage();

        ScheduledThreadPoolExecutor scheduler = new ScheduledThreadPoolExecutor(1);
        scheduler.scheduleAtFixedRate(new Runnable() {
            public void run() {
                try {
                    grabber.grab();
                    cc.passFrames(
                            a.convert(grabber.grabVideo()),
                            a.convert(grabber.grabDepth()),
                            a.convert(grabber.grabIR()));
                } catch (FrameGrabber.Exception e) {
                    e.printStackTrace();
                }

            }
        }, 0, (long) 1000 / 30, TimeUnit.MILLISECONDS);
    }
}
