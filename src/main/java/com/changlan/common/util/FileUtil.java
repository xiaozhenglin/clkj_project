package com.changlan.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

/**
 * Created by DK on 2017/11/14.
 */
public class FileUtil {


    private final static Logger logger = LoggerFactory.getLogger(FileUtil.class);

    public static File setFile(String filename) throws IOException {
        File path = new File(filename.substring(0, filename.lastIndexOf(File.separatorChar) + 1));
        if (!path.exists()) {
            path.mkdirs();
        }
        File file = new File(filename);
//        System.out.println(path.getAbsolutePath());
//        System.out.println(File.separatorChar);
//        System.out.println(filename);
        if (!file.exists()) {
            file.createNewFile();
        }

        return file;
    }

    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     * @param dir 将要删除的文件目录
     * @return boolean Returns "true" if all deletions were successful.
     *                 If a deletion fails, the method stops attempting to
     *                 delete and returns "false".
     */
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }


    public static void empty(File path) {
        logger.debug(path.getAbsolutePath());
        File[] folders = path.listFiles();
        if (folders == null) return;

        for (File folder : folders) {
            folder.delete();
        }
    }

    /**
     * 获取文件夹下所有文件
     *
     * @param strPath
     * @param filelist
     * @return
     */
    public static List<File> getFileList(String strPath, List<File> filelist) {
        File dir = new File(strPath);
        File[] files = dir.listFiles(); // 该文件目录下文件全部放入数组
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) { // 判断是文件还是文件夹
                    getFileList(files[i].getAbsolutePath(), filelist); // 获取文件绝对路径
                } else {
                    filelist.add(files[i]);
                }
            }
        }
        return filelist;
    }

    /**
     * 获取文件后缀名
     *
     * @return
     */
    public static String getFileSuffix(File file) {
        String fileName = file.getName();
        if (fileName.indexOf(".") == -1) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    /**
     * 读取 properties文件
     *
     * @param file
     * @return
     * @throws IOException
     */
    public static Map<String, String> loadProperties(File file) throws IOException {
        Map<String, String> map = new HashMap<>();
        Properties properties = new Properties();
//        InputStream in = new BufferedInputStream(new FileInputStream(file));
//        properties.load(in);
        // 只会解析 k=v  或者 k:v 这种
        properties.load(new InputStreamReader(new BufferedInputStream(new FileInputStream(file)), "UTF-8"));
        Set<Map.Entry<Object, Object>> propertySet = properties.entrySet();
        for (Map.Entry<Object, Object> entry : propertySet) {
            map.put(entry.getKey().toString(), entry.getValue().toString());
        }
        return map;
    }

    /**
     * 获取文件名不带后缀
     *
     * @param file
     * @return
     */
    public static String getFileNameNotSuffix(File file) {
        String fileName = file.getName();
        System.out.println(fileName);
        if (fileName.indexOf(".") == -1) {
            return fileName;
        }
        return fileName.substring(0, fileName.lastIndexOf("."));
    }

    /**
     * 字符流读取文件
     *
     * @param file
     * @return
     * @throws IOException
     */
    public static String readToBuffer(File file) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStream is = new FileInputStream(file);
        String line; // 用来保存每行读取的内容
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        line = reader.readLine(); // 读取第一行
        while (line != null) { // 如果 line 为空说明读完了
            sb.append(line); // 将读到的内容添加到 buffer 中
            line = reader.readLine(); // 读取下一行
        }
        reader.close();
        is.close();
        return sb.toString();
    }

    public static String getFileSuffix(MultipartFile file) {
        if (file == null) {
            return "";
        }
        String originalFilename = file.getOriginalFilename();
        if (originalFilename.indexOf(".") != -1) {
            return originalFilename.substring(originalFilename.lastIndexOf("."));
        }

        return getFileSuffix(file.getContentType());
    }

    /**
     * 这里只处理常见的
     * <url>http://www.runoob.com/http/http-content-type.html</url>
     * 用第二列
     *
     * @param contentType
     * @return
     */
    public static String getFileSuffix(String contentType) {
        if (StringUtil.isBlank(contentType)) {
            return "";
        }
        switch (contentType) {
            case "video/avi":
                return ".avi";
            case "application/x-jpe":
                return ".jpe";
            case "image/jpeg":
                return ".jpg";
            case "application/x-javascript":
                return ".js";
            case "audio/mp3":
                return ".mp3";
            case "image/png":
                return ".png";
            case "application/vnd.ms-powerpoint":
                return ".ppt";
            case "image/gif":
                return ".gif";
            case "image/tiff":
                return ".tif";
            case "image/fax":
                return ".fax";
            case "image/x-icon":
                return ".ico";
            case "image/pnetvue":
                return ".net";
            case "image/vnd.rn-realpix":
                return ".rp";
            case "image/vnd.wap.wbmp":
                return ".wbmp";
            case "text/plain":
                return ".txt";
            default:
                return "";
        }
    }

    public static byte[] fileToByte(File file) throws IOException {
        byte[] buffer = null;
        FileInputStream fis = new FileInputStream(file);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] b = new byte[1024];
        int n;
        while ((n = fis.read(b)) != -1) {
            bos.write(b, 0, n);
        }
        fis.close();
        bos.close();
        buffer = bos.toByteArray();
        return buffer;
    }


    public static void main(String[] args) {
		File file = new File("C:\\Users\\Administrator\\Pictures\\QQ浏览器截图\\20190306145009.png");
		String absolutePath = file.getAbsolutePath(); 
		System.out.println(absolutePath); 
		String path = file.getPath(); 
		System.out.println(path);
		String fileNameNotSuffix = FileUtil.getFileNameNotSuffix(file); 
		System.out.println(fileNameNotSuffix);
		String fileSuffix = FileUtil.getFileSuffix(file);
		System.out.println(fileSuffix); 
	}





}
