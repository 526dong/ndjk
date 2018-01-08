package com.ndjk.cl.utils;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by wl on 2018/1/6 0006.
 */
public class UploadFileUtil {
    public static final Logger logger = LoggerFactory.getLogger(UploadFileUtil.class);

    public void saveImage(InputStream instream, String fileName, String path) {
        File tempDir = new File(File.separator + "data" + File.separator + "img" + File.separator + path);
        if (!tempDir.exists())
            tempDir.mkdirs();
        FileOutputStream fout = null;
        try {
            fout = new FileOutputStream(new File(tempDir, fileName));
            IOUtils.copy(instream, fout);
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            if (instream != null) {
                try {
                    instream.close();
                    instream = null;
                } catch (IOException e) {
                }
            }
            if (fout != null) {
                try {
                    fout.close();
                } catch (IOException e) {
                }
            }
        }
    }
}
