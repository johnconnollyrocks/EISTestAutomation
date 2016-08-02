package common;

import static org.monte.media.FormatKeys.EncodingKey;
import static org.monte.media.FormatKeys.FrameRateKey;
import static org.monte.media.FormatKeys.KeyFrameIntervalKey;
import static org.monte.media.FormatKeys.MIME_QUICKTIME;
import static org.monte.media.FormatKeys.MediaTypeKey;
import static org.monte.media.FormatKeys.MimeTypeKey;
import static org.monte.media.VideoFormatKeys.CompressorNameKey;
import static org.monte.media.VideoFormatKeys.DepthKey;
import static org.monte.media.VideoFormatKeys.ENCODING_QUICKTIME_JPEG;
import static org.monte.media.VideoFormatKeys.QualityKey;

import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

import org.monte.media.Format;
import org.monte.media.FormatKeys.MediaType;
import org.monte.media.math.Rational;
import org.monte.screenrecorder.ScreenRecorder;

public class TestRecorder {

	private ScreenRecorder screenRecorder;

	private boolean captureRecording =EISConstants.SCREEN_RECORDING;
	public void StartRecording() throws Exception {

		if (captureRecording) {

			File file = new File(System.getProperty("user.dir")+"//videos");

			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			int width = screenSize.width;
			int height = screenSize.height;

			Rectangle captureSize = new Rectangle(0,0, width, height);

			// !! Function to get the graphic configuration of the screen, Required for ScreenRecorder class as arguments.	 !!
			GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice()
					.getDefaultConfiguration();


			// !! Create a instance of ScreenRecorder with the required configurations !!
			//			screenRecorder = new ScreenRecorder(gc, new Format(MediaTypeKey,MediaType.FILE, MimeTypeKey, MIME_QUICKTIME),
			//screenRecorder = new SpecializedScreenRecorder(gc,captureSize, new Format(MediaTypeKey,MediaType.FILE, MimeTypeKey, MIME_QUICKTIME),
			screenRecorder = new SpecializedScreenRecorder(gc, captureSize ,new Format(MediaTypeKey,MediaType.FILE, MimeTypeKey, MIME_QUICKTIME),
					//screenRecorder = new SpecializedScreenRecorder(gc, captureSize, new Format(MediaTypeKey,MediaType.FILE, MimeTypeKey, MIME_QUICKTIME),
					new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey,ENCODING_QUICKTIME_JPEG, CompressorNameKey, 
							ENCODING_QUICKTIME_JPEG, DepthKey, (int) 24,FrameRateKey, Rational.valueOf(15), QualityKey, 1.0f,
							KeyFrameIntervalKey, (int) (15 * 60)),
							new Format(MediaTypeKey,MediaType.VIDEO, EncodingKey, "white", FrameRateKey,Rational.valueOf(30)),
							null,file,"video");

			// !! Call the start method of ScreenRecorder to begin recording !!
			screenRecorder.start();
		}
	}

	// !! Call the stop method of ScreenRecorder to end the recording !!		
	public void StopRecording(){
		if (captureRecording) {
			try {
				screenRecorder.stop();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}



