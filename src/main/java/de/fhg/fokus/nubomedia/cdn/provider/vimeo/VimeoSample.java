package de.fhg.fokus.nubomedia.cdn.provider.vimeo;

import java.io.File;

import com.clickntap.vimeo.Vimeo;
import com.clickntap.vimeo.VimeoResponse;

public class VimeoSample {

	private static final String SAMPLE_VIDEO_FILENAME = "sample-video.mp4";
	
	public static void main(String[] args) throws Exception {
		Vimeo vimeo = new Vimeo("149d116abaa5739788e98d34ad412076"); 
		
		//add a video
		boolean upgradeTo1080 = true;
		File file = new File("/sample-video.mp4");
//		if(file = null)
//		{
//			System.out.println("file is null");
//			System.exit(0);
//		}
		String videoEndPoint = vimeo.addVideo(file, upgradeTo1080);
		//get video info
		VimeoResponse info = vimeo.getVideoInfo(videoEndPoint);
		System.out.println(info);

		//edit video
		String name = "Name";
		String desc = "Description";
		String license = ""; //see Vimeo API Documentation
		String privacyView = "disable"; //see Vimeo API Documentation
		String privacyEmbed = "whitelist"; //see Vimeo API Documentation
		boolean reviewLink = false;
		vimeo.updateVideoMetadata(videoEndPoint, name, desc, license, privacyView, privacyEmbed, reviewLink);

		//add video privacy domain
		vimeo.addVideoPrivacyDomain(videoEndPoint, "nubomedia.eu");

		//delete video
		vimeo.removeVideo(videoEndPoint);

	}
}
