package org.hac.dropbox;


import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;
import org.springframework.stereotype.Component;

@Component
public class ImageResizer {
	
	static ArrayList imageExtentsions = new ArrayList();
	
	static{
		imageExtentsions.add("jpg");
		imageExtentsions.add("jpeg");
		imageExtentsions.add("png");
		imageExtentsions.add("bmp");
		imageExtentsions.add("gif");
		imageExtentsions.add("JPG");
		imageExtentsions.add("JPEG");
	}
	
		public InputStream createResizedCopy(String insrcFile,int pixel) throws IOException{
			 
			//,String _outFile
			InputStream is = null;
			long startTime = System.nanoTime();
			File srcFl = new File(insrcFile);
			
			if(!srcFl.exists()){
				for (int i = 0; i < 5; i++) {
					try {
						Thread.sleep(1000);
						if(srcFl.exists())
							break;
					} catch (InterruptedException e) {						
						e.printStackTrace();
					}
				}
			}
			if(isImage(srcFl) && srcFl.exists()){
				
			BufferedImage srcImage = ImageIO.read(srcFl);
			// Scale the image using the imgscalr library
			BufferedImage scaledImage = org.imgscalr.Scalr.resize(srcImage, pixel);

			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ImageIO.write(scaledImage, "jpg", os);
			is = new ByteArrayInputStream(os.toByteArray());
			long endTime = System.nanoTime();
			long diff = endTime - startTime;
			System.out.print("Resize of"+ srcFl.getName()+" completed in : "+TimeUnit.NANOSECONDS.toSeconds(diff) +"  Secs" );			
			}
			else {
				System.out.print("Not an Image ignoring....");
			}
			return is;
		}

		public boolean isImage(File srcFl){
			String ext = FilenameUtils.getExtension(srcFl.getName());
			if(imageExtentsions.contains(ext)){
				return true;
			}
			else
				return false;
			
		}
		
		public boolean isMp4(File srcFl){
			String ext = FilenameUtils.getExtension(srcFl.getName());
			if(ext.equals("mp4")){
				return true;
			}
			else
				return false;
			
		}
	

}
