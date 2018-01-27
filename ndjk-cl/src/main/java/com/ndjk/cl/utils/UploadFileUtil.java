package com.ndjk.cl.utils;

import com.ndjk.cl.utils.dto.UploadFileRes;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

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


    public void repayconfirmUploadImg(MultipartFile file, String urlPath) throws Exception {
        Map<String, Object> result=new HashMap<>();
        String fileName=file.getOriginalFilename();
        //对文件类型校验
        if (!(fileName.endsWith(".png") || fileName.endsWith(".jpg")|| fileName.endsWith(".jpeg"))) {
            return;
        }
        SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
        String s=File.separator;
        String fileNamePath=urlPath;
        if (s.equals("\\")) {
            fileNamePath="D:" + fileNamePath;
        }
        File files=new File(fileNamePath);
        if (!files.exists())
            files.mkdirs();
        String[] strs=fileName.split(".");
        //  fileName = StringUtil.generationUuid()+strs[strs.length-1];
        fileName=StringUtil.generationUuid() + "." + FilenameUtils.getExtension(fileName);
        FileOutputStream fouts=new FileOutputStream(new File(fileNamePath, fileName));
        InputStream upins=file.getInputStream();
        IOUtils.copy(upins, fouts);
        fouts.close();
        upins.close();
    }

    public static void saveMultipartFile(List<UploadFileRes> list, MultipartFile file,String filePath) {
        if (!file.isEmpty()) {
            UploadFileRes model = save(file,filePath);
            list.add(model);
        }
    }
    private static UploadFileRes save(MultipartFile file,String filePath) {
        UploadFileRes model = new UploadFileRes();
        model.setCreateTime(DateUtil.getNow());

        // 文件名称
//        String picName = file.getOriginalFilename();

        CommonsMultipartFile cf = (CommonsMultipartFile) file;
        DiskFileItem fi = (DiskFileItem) cf.getFileItem();
        File newFile = (File) fi.getStoreLocation();
        logger.info("图片----------" + newFile);
        // 文件格式
        String fileType = FileTypeUtil.getFileType(newFile);
        if (StringUtil.isBlank(fileType) || !FileTypeUtil.isImage(newFile, fileType)) {
            model.setErrorMsg("图片格式错误或内容不规范");
            return model;
        }
        // 校验图片大小
        Long picSize = file.getSize();
        if (picSize.compareTo(20971520L) > 0) {
            model.setErrorMsg("文件超出20M大小限制");
            return model;
        }
        // 保存文件
        String s = File.separator;
//        String filePath = s + "data" + s + "image" + s + fileType + s + System.currentTimeMillis() + s + picName;
        if (s.equals("\\")) {
            filePath = "D:" + filePath;
        }
        File files = new File(filePath);
        if (!files.exists()) {
            try {
                files.mkdirs();
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                model.setErrorMsg("文件目录不存在");
                return model;
            }
        }
        try {
            file.transferTo(files);
        } catch (IllegalStateException | IOException e) {
            logger.error(e.getMessage(), e);
        }
        // 转存文件
        model.setResPath(filePath);
//        model.setFileName(picName);
        model.setFileFormat(fileType);
        model.setFileSize(new BigDecimal(picSize));
        return model;
    }
}
