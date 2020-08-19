package com.cdac.iafralley.services;

import java.awt.image.BufferedImage;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.cdac.iafralley.entity.RalleyCandidateDetails;

@Service
@PropertySource({"classpath:applicantfilepath.properties"})
public class StoreImageFiles {
	
	@Value("${applicant.certificate}")
	private  String FILE_PATH;
	
	private static final Logger logger = LoggerFactory.getLogger(StoreImageFiles.class);
	
	//function to change,filename,store in disk and file 
	public RalleyCandidateDetails storeImage(RalleyCandidateDetails c,MultipartFile XMarksheet,MultipartFile XIIMarksheet) {
		
		try {
			Files.createDirectories(Paths.get(FILE_PATH + "/"+c.getRalleyregistrationNo()));
			logger.info("in after create f ");
		if (XMarksheet.isEmpty() || XIIMarksheet.isEmpty()) {
	           logger.info("Please select a file to upload.");
	            
	        }
			else {
				logger.info("in else ");
				String fileName1 = StringUtils.cleanPath(XMarksheet.getOriginalFilename());
				logger.info("in else ");
				String fileName2 = StringUtils.cleanPath(XIIMarksheet.getOriginalFilename());
				logger.info("in else ");
				fileName1=c.getRalleyregistrationNo()+"_X_marksheet"+"."+fileName1.substring(XMarksheet.getOriginalFilename().lastIndexOf(".")+1);
				logger.info("in else ");
				fileName2=c.getRalleyregistrationNo()+"_XII_marksheet"+"."+fileName2.substring(XIIMarksheet.getOriginalFilename().lastIndexOf(".")+1);
				//set ralleycandidate path in object and sanitize it also...
				//logger.info(XMarksheet.getContentType());
				logger.info("in else ");
				/*
				 * String mime1=XMarksheet.getContentType(); String
				 * mime2=XIIMarksheet.getContentType(); if ((mime1 != null &&
				 * mime1.split("/")[0].equals("image")) || (mime2 != null &&
				 * mime2.split("/")[0].equals("image"))) { System.out.println("it is an image");
				 * } if (mime1 != null && ((mime1.split("/")[1].equals("jpeg")) ||
				 * (mime1.split("/")[1].equals("jpg"))) || (mime1.split("/")[1].equals("png")))
				 * { System.out.println("it is valid an image"); } if (mime2 != null &&
				 * ((mime2.split("/")[1].equals("jpeg")) || (mime2.split("/")[1].equals("jpg")))
				 * || (mime2.split("/")[1].equals("png"))) {
				 * System.out.println("it is an valid image"); }
				 */
				
				boolean validimage=isValidImage(XMarksheet.getInputStream());
				try {
					analyzeImage(XMarksheet.getInputStream());
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				logger.info("yes or no: "+validimage);
				logger.info("file1:"+fileName1+" file2:"+fileName2);
				Files.copy(XMarksheet.getInputStream(), Paths.get(FILE_PATH + "/"+c.getRalleyregistrationNo()).resolve(fileName1) , StandardCopyOption.REPLACE_EXISTING);
				Files.copy(XIIMarksheet.getInputStream(), Paths.get(FILE_PATH + "/"+c.getRalleyregistrationNo()).resolve(fileName2) , StandardCopyOption.REPLACE_EXISTING);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}
		
		
		
		return c;
		
	}
	
	public boolean isValidImage(InputStream inputStream) {
		  boolean isValid = true;
		  try {
		    ImageIO.read(inputStream).flush();
		  } catch (Exception e) {
		    isValid = false;
		  }
		  return isValid;
		}
	
	public  void analyzeImage(InputStream inputStream)
	        throws NoSuchAlgorithmException, IOException {
	   

	    final InputStream digestInputStream = inputStream;
	    try {
	        final ImageInputStream imageInputStream = ImageIO
	                .createImageInputStream(digestInputStream);
	        final Iterator<ImageReader> imageReaders = ImageIO
	                .getImageReaders(imageInputStream);
	        if (!imageReaders.hasNext()) {
	            logger.info("false image cant read");
	           
	        }
	        final ImageReader imageReader = imageReaders.next();
	        imageReader.setInput(imageInputStream);
	        final BufferedImage image = imageReader.read(0);
	        if (image == null) {
	        	 logger.info("false image cant read");
	        }
	        image.flush();
	        if (imageReader.getFormatName().equals("JPEG")) {
	            imageInputStream.seek(imageInputStream.getStreamPosition() - 2);
	            final byte[] lastTwoBytes = new byte[2];
	            imageInputStream.read(lastTwoBytes);
	            if (lastTwoBytes[0] != (byte)0xff || lastTwoBytes[1] != (byte)0xd9) {
	            	 logger.info("set truncated t");
	            } else {
	            	 logger.info("set truncated f");
	            }
	        }
	        logger.info("set image valid");;
	    } catch (final IndexOutOfBoundsException e) {
	    	 logger.info("set truncated t");
	    } catch (final IIOException e) {
	        if (e.getCause() instanceof EOFException) {
	        	 logger.info("set truncated t");
	        }
	    } finally {
	        digestInputStream.close();
	    }
	   
	}

}
