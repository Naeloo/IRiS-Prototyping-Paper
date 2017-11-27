package hauptfeld.bsa.gestures.visualisation;


import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.Frame;

import javax.swing.*;

/**
 * Created by root on 11/27/17.
 * I Am Root
 */
public class CanvasController {
    CanvasFrame videoFrame;
    CanvasFrame depthFrame;
    CanvasFrame irFrame;

    int[] lastVideoSize = new int[]{0,0};
    int[] lastDepthSize = new int[]{0,0};
    int[] lastIrSize =    new int[]{0,0};

    private CanvasFrame[] frames;
    private int[][] sizes;

    double scale;

    public CanvasController(double scale){
        videoFrame = new CanvasFrame("Video");
        depthFrame = new CanvasFrame("Depth");
        irFrame = new CanvasFrame("Infrared");

        frames = new CanvasFrame[]{videoFrame, depthFrame, irFrame};
        sizes = new int[][]{lastVideoSize, lastDepthSize, lastIrSize};
        this.scale = scale;

        for(CanvasFrame cv : frames) {
            cv.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        }
    }

    public void passFrames(Frame videoFrame, Frame depthFrame, Frame irFrame) {
        passVideoFrame(videoFrame);
        passDepthFrame(depthFrame);
        passIrFrame(irFrame);
    }

    public void passVideoFrame(Frame f) {
        passInternalFrame(0, f);
    }
    public void passDepthFrame(Frame f) {
        passInternalFrame(1, f);
    }
    public void passIrFrame(Frame f) {
        passInternalFrame(2, f);
    }

    private void passInternalFrame(int index, Frame f) {
        if(sizes[index][0] != f.imageWidth || sizes[index][1] != f.imageHeight) {
            frames[index].setCanvasSize(f.imageWidth, f.imageHeight);
            alignCanvases();
            sizes[index][0] = f.imageWidth;
            sizes[index][1] = f.imageHeight;
        }
        frames[index].showImage(f);
    }
    private void alignCanvases(){
        int xOffset = 0;
        int yOffset = 0;
        System.out.println("Re-aligning canvases");
        for(CanvasFrame cv : frames) {
            cv.setLocation(xOffset, yOffset);
            cv.setCanvasScale(this.scale);
            xOffset += cv.getWidth();

        }
    }

}
