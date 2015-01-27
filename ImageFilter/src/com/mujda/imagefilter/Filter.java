package com.mujda.imagefilter;

import android.graphics.Bitmap;
import android.util.Log;

public class Filter {

	public static Bitmap applyMeanFilter(Bitmap image, int size) {
		
		int width = image.getWidth();
		int height = image.getHeight();
		System.out.println(height);
		System.out.println(width);
		int[] pixels = new int[height*width];
		image.getPixels(pixels, 0, width, 0, 0, width, height);
		
		int rsum, gsum, bsum, index, index1;
		int[] r = new int[height*width];
		int[] g = new int[height*width];
		int[] b = new int[height*width];
		
		for (int y = 0; y < height; y++) {    
		    for (int x = 0; x < width; x++) {
		        index = y * width + x;
		        r[index] = (pixels[index] >> 16) & 0xff;
		        g[index] = (pixels[index] >> 8) & 0xff;
		        b[index] = pixels[index] & 0xff;
		    }
		}
		
		System.out.println("done first");
		
		for (int y = 0; y < height; y++) {    
		    for (int x = 0; x < width; x++) {
		        index = y * width + x;
		        
		        rsum=0;
		        bsum=0;
		        gsum=0;
		        
		        for (int y1=y-size/2; y1<=y+size/2; y++) {
		        	for (int x1=x-size/2; x1<=x+size/2; x++) {
		        		System.out.println(y1);
		        		try {
		        			index1 = y1 * width + x1;
		        			rsum += r[index1];
		        			bsum += b[index1];
		        			gsum += g[index1];
		        		}
		        		catch (IndexOutOfBoundsException e) {
		        			break;
		        		}
		        	}
		        }
		        
		        pixels[index] = 0xff000000 | ((rsum/(size*size)) << 16) | ((gsum/(size*size)) << 8) | (bsum/(size*size));
		    }
		}
		
		System.out.println("done second");
		
		Bitmap newImage = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		newImage.setPixels(pixels, 0, width, 0, 0, width, height); 	

		if (null != image) {
		    image.recycle();
		}
		return newImage;
	}
	
	
}
