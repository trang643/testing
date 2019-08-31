package com.voca.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

public class FileDownloader {
	public static String downloadImage(List<String> images, String location, String fileName) throws MalformedURLException, IOException {
		if (images != null) {
			int index = 0;
			while (index <= images.size()) {
				try {
					String imageURL = images.get(index);

					URLConnection urlConnection = new URL(imageURL).openConnection();
					urlConnection.setConnectTimeout(5000);
					InputStream is = urlConnection.getInputStream();

					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					
					
					// Fake code simulating the copy
					// You can generally do better with nio if you need...
					// And please, unlike me, do something about the Exceptions
					// :D
					byte[] buffer = new byte[1024];
					int len;
					while ((len = is.read(buffer)) > -1) {
						baos.write(buffer, 0, len);
					}
					baos.flush();

					// Open new InputStreams using the recorded bytes
					// Can be repeated as many times as you wish
					InputStream is1 = new ByteArrayInputStream(baos.toByteArray());
					InputStream is2 = new ByteArrayInputStream(baos.toByteArray());

					String imageExtension = getImageFormatFromInputStream(is1);

					String fileNameWithExtension = fileName + "." + imageExtension;

					// Save to disk
					File file = new File(location + fileNameWithExtension);

					if (!file.getParentFile().exists()) {
						file.getParentFile().mkdirs();
					}

					if (!file.exists()) {
						file.createNewFile();
					}
					OutputStream os = new FileOutputStream(file);

					byte[] buffer2 = new byte[4096];
					int len2;
					while ((len2 = is2.read(buffer2)) > 0) {
						os.write(buffer2, 0, len2);
					}

					os.close();
					is.close();

					return fileNameWithExtension;
				} catch (Exception e) {
					e.printStackTrace();
					index++;
				}
			}
		}

		return fileName;
	}

	public static String downloadMp3(String url, String location, String fileName) throws MalformedURLException, IOException {
		if (url != null) {
			// Download mp3 file
			URLConnection urlConnection = new URL(url).openConnection();
			InputStream is = urlConnection.getInputStream();

			// Save to disk
			File file = new File(location + fileName);

			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}

			if (!file.exists()) {
				file.createNewFile();
			}
			OutputStream os = new FileOutputStream(location + fileName);

			byte[] buffer = new byte[4096];
			int len;
			while ((len = is.read(buffer)) > 0) {
				os.write(buffer, 0, len);
			}

			os.close();
			is.close();

			return fileName;
		}

		return fileName;
	}

	private static String getImageFormatFromInputStream(InputStream input) throws IOException {
		ImageInputStream stream = ImageIO.createImageInputStream(input);

		Iterator<?> iter = ImageIO.getImageReaders(stream);
		if (!iter.hasNext()) {
			return null;
		}
		ImageReader reader = (ImageReader) iter.next();
		ImageReadParam param = reader.getDefaultReadParam();
		reader.setInput(stream, true, true);
		BufferedImage bi;
		try {
			bi = reader.read(0, param);
			return reader.getFormatName().toLowerCase();
		} finally {
			reader.dispose();
			stream.close();
		}
	}

}
