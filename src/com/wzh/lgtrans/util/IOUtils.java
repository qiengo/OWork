/*
 * Copyright (C) 2014 Zhihua
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.wzh.lgtrans.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Environment;
import android.util.Log;

/**
 * 输入输出工具类，涉及SD文件的操作。<br>
 * This class contains various utilities to manipulate I/O. The methods of this
 * class, although static, are not thread safe and cannot be invoked by several
 * threads at the same time. Synchronization is required by the caller.
 * 
 * @author 王植桦 ewangzhihua@yeah.net
 * @version 创建时间：2014年3月10日
 *
 */
public final class IOUtils {
	
	public static final int DEFAULT_BUFFER_SIZE = 8 * 1024;
	public static final int BYTES_ZERO_LENGTH = 10;
    private static final String LOG_TAG = "IOUtils";
    private static final byte[] BYTES_ZERO = new byte[BYTES_ZERO_LENGTH];

    /**
     * Opens a {@link FileInputStream} for the specified file, providing better
     * error messages than simply calling <code>new FileInputStream(file)</code>
     * .
     * <p>
     * At the end of the method either the stream will be successfully opened,
     * or an exception will have been thrown.
     * <p>
     * An exception is thrown if the file does not exist. An exception is thrown
     * if the file object exists but is a directory. An exception is thrown if
     * the file exists but cannot be read.
     * 
     * @param file the file to open for input, must not be <code>null</code>
     * @return a new {@link FileInputStream} for the specified file
     * @throws FileNotFoundException if the file does not exist
     * @throws IOException if the file object is a directory
     * @throws IOException if the file cannot be read
     */
    public static FileInputStream openInputStream(File file) throws IOException {
        if (file.exists()) {
            if (file.isDirectory()) {
                throw new IOException("File '" + file + "' exists but is a directory");
            }
            if (file.canRead() == false) {
                throw new IOException("File '" + file + "' cannot be read");
            }
        } else {
            throw new FileNotFoundException("File '" + file + "' does not exist");
        }
        return new FileInputStream(file);
    }

    // -----------------------------------------------------------------------
    /**
     * Opens a {@link FileOutputStream} for the specified file, checking and
     * creating the parent directory if it does not exist.
     * <p>
     * At the end of the method either the stream will be successfully opened,
     * or an exception will have been thrown.
     * <p>
     * The parent directory will be created if it does not exist. The file will
     * be created if it does not exist. An exception is thrown if the file
     * object exists but is a directory. An exception is thrown if the file
     * exists but cannot be written to. An exception is thrown if the parent
     * directory cannot be created.
     * 
     * @param file the file to open for output, must not be <code>null</code>
     * @return a new {@link FileOutputStream} for the specified file
     * @throws IOException if the file object is a directory
     * @throws IOException if the file cannot be written to
     * @throws IOException if a parent directory needs creating but that fails
     */
    public static FileOutputStream openOutputStream(File file) throws IOException {
    	if (file.exists()) {
            if (file.isDirectory()) {
                throw new IOException("File '" + file + "' exists but is a directory");
            }
            if (file.canWrite() == false) {
                throw new IOException("File '" + file + "' cannot be written to");
            }
        } else {
            File parent = file.getParentFile();
            if (parent != null && parent.exists() == false) {
                if (parent.mkdirs() == false) {
                    throw new IOException("File '" + file + "' could not be created");
                }
            }
        }
        return new FileOutputStream(file);
    }

    /**
     * Copy the content of the input stream into the output stream, using a
     * temporary byte array buffer whose size is defined by
     * {@link #DEFAULT_BUFFER_SIZE}.
     * 
     * @param in The input stream to copy from.
     * @param out The output stream to copy to.
     * @throws java.io.IOException If any error occurs during the copy.
     */
    public static void copy(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }

    /**
     * Copy the content of the input stream into the output stream, using a
     * temporary byte array buffer whose size is defined by
     * {@link #DEFAULT_BUFFER_SIZE}.
     * 
     * @param in The input stream to copy from.
     * @param out The output stream to copy to.
     * @param length number of bytes to copy
     * @throws java.io.IOException If any error occurs during the copy.
     */
    public static void copy(InputStream in, OutputStream out, long length) throws IOException {
        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
        int read;
        while ((read = in.read(buffer)) != -1 && length > 0) {
            out.write(buffer, 0, read);
            length -= read;
        }
    }

    /**
     * Closes the specified stream.
     * 
     * @param stream The stream to close.
     */
    public static void closeStream(Closeable stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException e) {
                Log.e(LOG_TAG, "Could not close stream", e);
            }
        }
    }

    /**
     * Read file into buffer.
     * 
     * @param file the file to read, must not be null
     * @return the file contents, never null
     * @throws java.io.IOException - in case of an I/O error
     */
    public static byte[] readFileToByteArray(File file) throws java.io.IOException {
        InputStream in = null;
        ByteArrayOutputStream out = null;
        try {
            in = openInputStream(file);
            out = new ByteArrayOutputStream();
            copy(in, out);
            return out.toByteArray();
        } finally {
            closeStream(in);
            closeStream(out);
        }
    }

    /**
     * Read file into buffer.
     * 
     * @param file the file to read, must not be null
     * @param offset position to read from
     * @param length length to read
     * @return the file contents, never null
     * @throws java.io.IOException - in case of an I/O error
     */
    public static byte[] readFileToByteArray(File file, long offset, long length)
            throws java.io.IOException {
        InputStream in = null;
        ByteArrayOutputStream out = null;
        try {
            in = openInputStream(file);
            out = new ByteArrayOutputStream();
            in.skip(offset);
            copy(in, out, length);
            return out.toByteArray();
        } finally {
            closeStream(in);
            closeStream(out);
        }
    }

    /**
     * Read file into buffer and append BYTES_ZERO_LENGTH zero bytes.
     * 
     * @param file the file to read, must not be null
     * @return the file contents, never null
     * @throws java.io.IOException - in case of an I/O error
     */
    public static byte[] readFileToByteArrayWithAppend(File file) throws java.io.IOException {
        InputStream in = null;
        ByteArrayOutputStream out = null;
        try {
            in = openInputStream(file);
            out = new ByteArrayOutputStream();
            copy(in, out);
            out.write(BYTES_ZERO);
            return out.toByteArray();
        } finally {
            closeStream(in);
            closeStream(out);
        }
    }

    /**
     * Reads the contents of a file into a String using the default encoding for
     * the VM.
     * 
     * @param file the file to read, must not be null
     * @param offset position to read from
     * @param length length to read
     * @return the file contents, never null
     * @throws java.io.IOException in case of an I/O error
     */
    public static String readFileToString(File file, long offset, long length)
            throws java.io.IOException {
        InputStream in = null;
        ByteArrayOutputStream out = null;
        try {
            in = openInputStream(file);
            out = new ByteArrayOutputStream();
            in.skip(offset);
            copy(in, out, length);
            return out.toString();
        } finally {
            closeStream(in);
            closeStream(out);
        }
    }

    /**
     * Reads the contents of a file into a String using the default encoding for
     * the VM.
     * 
     * @param file the file to read, must not be null
     * @return the file contents, never null
     * @throws java.io.IOException in case of an I/O error
     */
    public static String readFileToString(File file) throws java.io.IOException {
        InputStream in = null;
        ByteArrayOutputStream out = null;
        try {
            in = openInputStream(file);
            out = new ByteArrayOutputStream();
            copy(in, out);
            return out.toString();
        } finally {
            closeStream(in);
            closeStream(out);
        }
    }

    /**
     * Reads the contents of a file into a String.
     * 
     * @param file
     * @param encoding the encoding to use, null means platform default
     * @return the file contents, never null
     * @throws java.io.IOException in case of an I/O error
     */
    public static String readFileToString(File file, String encoding) throws java.io.IOException {
        InputStream in = null;
        ByteArrayOutputStream out = null;
        try {
            in = openInputStream(file);
            out = new ByteArrayOutputStream();
            copy(in, out);
            return out.toString(encoding);
        } finally {
            closeStream(in);
            closeStream(out);
        }
    }
    /**
     * Reads some segments of file1 into file2.
     * 
     * @param file2 the destination file
     * @param file1 the source file
     * @param offset position to read from
     * @param length length to read
     * @return null
     * @throws java.io.IOException in case of an I/O error
     */
    public static void readFileToFile(File file2,File file1,
    		long offset,long length) throws java.io.IOException {
        InputStream in = null;
        FileOutputStream out = null;
        try {
            in = openInputStream(file1);
            out = openOutputStream(file2);
            in.skip(offset);
            copy(in, out, length);
        } finally {
            closeStream(in);
            closeStream(out);
        }
    }

    /**
     * @param file
     * @param position The position within the file at which the mapped region
     *            is to start; must be non-negative
     * @param size The size of the region to be mapped; must be non-negative and
     *            no greater than java.lang.Integer.MAX_VALUE
     * @return the file contents, never null
     * @throws java.io.IOException in case of an I/O error
     */
    public static byte[] readMappedFileToByteArray(File file, long position, long size)
            throws java.io.IOException {
        FileInputStream in = null;
        FileChannel channel = null;
        try {
            in = openInputStream(file);
            channel = in.getChannel();
            MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_ONLY, position, size);
            byte[] data = new byte[(int)size];
            buffer.get(data, 0, (int)size);
            return data;
        } finally {
            try {
                if (channel != null) {
                    channel.close();
                }
            } catch (Exception e) {
            	Log.e(LOG_TAG, e.getMessage(), e);
            }
            closeStream(in);
        }
    }

    /**
     * @param file
     * @param position The position within the file at which the mapped region
     *            is to start; must be non-negative
     * @param size The size of the region to be mapped; must be non-negative and
     *            no greater than java.lang.Integer.MAX_VALUE
     * @return the file contents, never null
     * @throws java.io.IOException in case of an I/O error
     */
    public static byte[] readMappedFileToByteArrayEx(File file, long position, long size)
            throws java.io.IOException {
        FileInputStream in = null;
        FileChannel channel = null;
        try {
            in = openInputStream(file);
            channel = in.getChannel();
            MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_ONLY, position, size);
            byte[] data = new byte[(int)size + BYTES_ZERO_LENGTH];
            buffer.get(data, 0, (int)size);
            return data;
        } finally {
            try {
                if (channel != null) {
                    channel.close();
                }
            } catch (Exception e) {
            	Log.e(LOG_TAG, e.getMessage(), e);
            }
            closeStream(in);
        }
    }

    /**
     * @param file
     * @param position The position within the file at which the mapped region
     *            is to start; must be non-negative
     * @param size The size of the region to be mapped; must be non-negative and
     *            no greater than java.lang.Integer.MAX_VALUE
     * @return the file contents, never null
     * @throws java.io.IOException in case of an I/O error
     */
    public static String readMappedFileToString(File file, long position, long size)
            throws java.io.IOException {
        return new String(readMappedFileToByteArray(file, position, size));
    }

    /**
     * Writes a byte array to a file creating the file if it does not exist.
     * 
     * @param file the file to write to
     * @param data the content to write to the file
     * @throws IOException
     */
    public static void writeByteArrayToFile(File file, byte[] data) throws IOException {
        FileOutputStream out = null;
        ByteArrayInputStream in = null;
        try {
            in = new ByteArrayInputStream(data);
            out = openOutputStream(file);
            copy(in, out);
        } finally {
            closeStream(in);
            closeStream(out);
        }
    }

    /**
     * Writes a byte array to a file creating the file if it does not exist.
     * 
     * @param file the file to write to
     * @param data the content to write to the file
     * @param position start offset to read
     * @param size bytes length to read
     * @throws IOException
     */
    public static void writeByteArrayToFile(File file, byte[] data, long position, long size)
            throws IOException {
        FileOutputStream out = null;
        ByteArrayInputStream in = null;
        try {
            in = new ByteArrayInputStream(data);
            out = openOutputStream(file);
            in.skip(position);
            copy(in, out, size);
        } finally {
            closeStream(in);
            closeStream(out);
        }
    }

    /**
     * 保存输入流到文件
     * Writes a byte stream to a file creating the file if it does not exist.
     * 
     * @param file the file to write to
     * @param data the content to write to the file
     * @param position start offset to read
     * @param size bytes length to read
     * @throws IOException
     */
    public static void writeByteStreamToFile(File src, long position, long size, File dst)
            throws IOException {
        FileOutputStream out = null;
        FileInputStream in = null;
        try {
            in = openInputStream(src);
            out = openOutputStream(dst);
            in.skip(position);
            copy(in, out, size);
        } finally {
            closeStream(in);
            closeStream(out);
        }
    }

    /**
     * 保存输入流到文件
     * @param input
     * @param file
     * @throws IOException
     */
	public static void writeInputStreamToFile(InputStream input, File file)
			throws IOException {
		try {
			final OutputStream output = new FileOutputStream(file);
			try {
				try {
					final byte[] buffer = new byte[1024];
					int read;
					while ((read = input.read(buffer)) != -1)
						output.write(buffer, 0, read);
					output.flush();
				} finally {
					output.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} finally {
			input.close();
		}
	}

    /**
     * 外部存储是否可用
     * @return
     */
    public static boolean isExternalStorageAvailable() {
        boolean externalStorageAvailable = false;
        boolean externalStorageWriteable = false;
        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            externalStorageAvailable = externalStorageWriteable = true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            externalStorageAvailable = true;
            externalStorageWriteable = false;
        } else {
            externalStorageAvailable = externalStorageWriteable = false;
        }
        return externalStorageAvailable;
    }

    /**
     * 将图片保存到文件
     * @param filename
     * @param bmp
     */
	public static void saveToFile(String filename, Bitmap bmp) {
		try {
			FileOutputStream out = new FileOutputStream(filename);
			bmp.compress(CompressFormat.JPEG, 100, out);
			out.flush();
			out.close();
		} catch (Exception e) {
		}
	}

	/**
	 * 获取外部文件
	 * @param file
	 * @return
	 */
	public static File getExternalFile(String file) {
		return new File(Environment.getExternalStorageDirectory(), file);
	}
	 
	/**
	 * 创建".nomedia"文件，以确保媒体浏览器不会查看该文件夹下的文件
	 * create the nomeida file if not exist.
	 * @return
	 * @throws IOException
	 */
	public static void ensureNonMediaDirectory(File directory)
			throws IOException {
		if (!directory.exists()) {
			directory.mkdirs();
			new File(directory, ".nomedia").createNewFile();
		}
	}
	/**
	 * 确保目录存在，没有的话创建
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static File ensureDirectory(String path)
			throws IOException {
		File dir=getExternalFile(path);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		return dir;
	}
	
	/**
	 * 将字符串转换成适合用作文件名的key。<br>
	 * 获取字符串的MD5码。<br>
	 * A hashing method that changes a string (like a URL) into a hash suitable for using as a
	 * disk filename.
	 * 
	 * @param str
	 * @return
	 */
    public static String hashKeyForDisk(String str) {
        String cacheKey;
        try {
        	//初始化实现MD5算法的 MessageDigest 对象
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(str.getBytes());
            //将数组转换为以0开头的16进制字符
            //convert a byte array to a string of hex digits while keeping leading zeros.
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
        	//直接返回哈希码
            cacheKey = String.valueOf(str.hashCode());
        }
        return cacheKey;
    }
    
    /**
     * 将数组转换为以0开头的16进制字符<br>
     * convert a byte array to a string of hex digits while keeping leading zeros.
     * @param bytes
     * @return
     */
    private static String bytesToHexString(byte[] bytes) {
        // http://stackoverflow.com/questions/332079
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }
    /**
     * 从asset文件中读取string
     * @param ctx
     * @param filename
     * @return
     */
	public static String loadJSONFromAsset(Context ctx, String filename) {
		String json = null;
		try {
			AssetManager manager=ctx.getAssets();
			InputStream is = manager.open(filename);
			int size = is.available();
			byte[] buffer = new byte[size];
			is.read(buffer);
			is.close();
			json = new String(buffer, "UTF-8");
		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		}
		return json;
	}
}
