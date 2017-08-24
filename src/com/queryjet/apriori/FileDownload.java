package com.queryjet.apriori;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Created by Hyundai-HCC on 2016-03-14.
 */
public class FileDownload {

    public static String imgFile(String localPath, String urls, String name) throws IOException {
        String imgUrl = urls;
        URL url = new URL(imgUrl);
        String ext = imgUrl.substring( imgUrl.lastIndexOf('.')+1, imgUrl.length() );  // 이미지 확장자 추출
        BufferedImage img = ImageIO.read(url);
        String savedImage = name+"."+ext;
        ImageIO.write(img, ext, new File(localPath+"/"+name+"."+ext));
        return savedImage;

    }

}
