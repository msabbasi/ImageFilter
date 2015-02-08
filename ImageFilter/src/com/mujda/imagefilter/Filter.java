package com.mujda.imagefilter;

import java.util.Arrays;

import android.graphics.Bitmap;
import android.util.Log;

public class Filter {

	public static Bitmap applyMeanFilter(Bitmap image, int size) {
		
		int width = image.getWidth();
		int height = image.getHeight();
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
		
		for (int y = 0; y < height; y++) {    
		    for (int x = 0; x < width; x++) {
		        index = y * width + x;
		        
		        rsum=0;
		        bsum=0;
		        gsum=0;
		        
		        for (int y1=y-size/2; y1<=y+size/2; y1++) {
		        	if (y1<0) {
		        		continue;
		        	}
		        	if (y1>height-1) {
		        		break;
		        	}
		        	for (int x1=x-size/2; x1<=x+size/2; x1++) {
		        		if (x1<0) {
			        		continue;
			        	}
			        	if (x1>width-1) {
			        		break;
			        	}
			        	//System.out.println(y1 + " " + x1 + " " + index);
			        	index1 = y1 * width + x1;
			        	rsum += r[index1];
			        	bsum += b[index1];
			        	gsum += g[index1];
		        	}
		        }
		        
		        pixels[index] = 0xff000000 | ((rsum/(size*size)) << 16) | ((gsum/(size*size)) << 8) | (bsum/(size*size));
		    }
		}
				
		Bitmap newImage = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		newImage.setPixels(pixels, 0, width, 0, 0, width, height); 	

		if (null != image) {
		    image.recycle();
		}
		return newImage;
	}
	
	public static Bitmap applyMedianFilter(Bitmap image, int size) {
		
		int width = image.getWidth();
		int height = image.getHeight();
		int[] pixels = new int[height*width];
		image.getPixels(pixels, 0, width, 0, 0, width, height);
		
		int i, index, index1;
		int[] array = new int[size*size];
		int[] resultant = new int[height*width];
		
		for (int y = 0; y < height; y++) {    
		    for (int x = 0; x < width; x++) {
		        index = y * width + x;
		        
		        i=0;
		        
		        for(int j=0;j<size*size;j++){
		        	array[j]=0;
		        }
		        
		        for (int y1=y-size/2; y1<=y+size/2; y1++) {
		        	if (y1<0) {
		        		continue;
		        	}
		        	if (y1>height-1) {
		        		break;
		        	}
		        	for (int x1=x-size/2; x1<=x+size/2; x1++) {
		        		if (x1<0) {
			        		continue;
			        	}
			        	if (x1>width-1) {
			        		break;
			        	}
			        	//System.out.println(y1 + " " + x1 + " " + index);
			        	index1 = y1 * width + x1;
			        	array[i]=pixels[index1];
			        	i++;
		        	}
		        }
		        int median;
		        Arrays.sort(array);
		        int middle = array.length-i+(i)/2;
		        if(i%2==0){
		        	median=(array[middle]+array[middle-1])/2;
		        }
		        else {
		        	median=array[middle+1];
		        }
		        
		        
		        resultant[index] = median;
		    }
		}
				
		Bitmap newImage = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		newImage.setPixels(resultant, 0, width, 0, 0, width, height); 	

		if (null != image) {
		    image.recycle();
		}
		return newImage;
	}
	
	

}


