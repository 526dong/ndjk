package com.ndjk.api.utiles.controller;

import com.ndjk.cl.utils.GsonUtil;
import com.ndjk.cl.utils.StringUtil;
import com.ndjk.cl.utils.UploadFileUtil;
import com.ndjk.cl.utils.dto.UploadFileRes;
import jdk.nashorn.internal.objects.Global;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by wl on 2018/1/20.
 */
@Controller
public class UploadFileController {
    /**
     * 申请对账-上传图片
     *
     * @param
     * @param
     */
    @RequestMapping(value="/utiles/upload/repayupload.htm")
    @ResponseBody
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
//        IOUtils.copy(upins, fouts);
        fouts.close();
        upins.close();
    }
    @RequestMapping(value="/utiles/upload/upload.htm")
    @ResponseBody
    public void upload(MultipartFile file) throws Exception {
        // 读入 文件
        File files = new File("C:\\Users\\Administrator\\Desktop\\aa.jpg");
        FileInputStream in_file = new FileInputStream(files);

        List<UploadFileRes> list = new LinkedList<>();
        UploadFileUtil.saveMultipartFile(list,file);
    }


    /**
     * 申请对账
     *
     * @param repayConfirm
     */
    /*@RequestMapping(value="/modules/manage/borrow/timeLimit/repayconfirm.htm")
    @RequiresPermission(code="modules:manage:borrow:timeLimit:repayconfirm", name="逾期管理页申请对账")
    public void repayconfirm(RepayConfirm repayConfirm, Long borrrowId, @RequestParam(value="files") MultipartFile[] files) throws Exception {
        Borrow borrow=this.clBorrowService.findByPrimary(borrrowId);
        Map<String, Object> result=new HashMap<String, Object>();

        try {
            if (repayConfirm != null && repayConfirm.getAmount() != null) {
                String a=String.format("%.2f", repayConfirm.getAmount());
                BigDecimal amout=new BigDecimal(a);
                if (amout.compareTo(new BigDecimal("0")) <= 0) {
                    result.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
                    result.put(Constant.RESPONSE_CODE_MSG, "对账金额不能为0");
                    ServletUtils.writeToResponse(response, result);
                    return;
                }
            }
        } catch (Exception e) {
            result.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "对账金额不能为0");
            ServletUtils.writeToResponse(response, result);
            return;
        }

        String[] results=selectIsContinueDeduct(repayConfirm.getUserId(), borrrowId);
        if (Integer.valueOf(results[0]) > 0) {
            result.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "操作过于频繁，稍后再试");
            ServletUtils.writeToResponse(response, result);
            return;
        }
        if (borrow == null) {
            result.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "订单信息不存在");
        } else if (checkRepayconfirmByStatus(borrow.getUserId())) {
            result.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "有待审核对账记录请等待审核");
        } else {
            //多图片上传
            if (files != null && files.length > 0) {
                for (MultipartFile file : files) {
                    if (file != null) {
                        String fileName=file.getOriginalFilename();
                        //对文件类型校验
                        if (!(fileName.endsWith(".png") || fileName.endsWith(".jpg"))) {
                            result.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
                            result.put(Constant.RESPONSE_CODE_MSG, "上传图片类型非法,请上传.png或.jpg图片");
                            ServletUtils.writeToResponse(response, result);
                            return;
                        }
                        logger.info("======文件上传 Start======Upload FileName  " + fileName);
                        SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
                        String s=File.separator;
                        String path= Global.getValue("repayconfirm_img");
                        if (!tool.util.StringUtil.isNotEmpty(path))
                            throw new RuntimeException("=============对账信息未配置文件路径========");
                        String fileNamePath=path;
                        logger.info("filePath=====" + fileNamePath);
                        if (s.equals("\\")) {
                            fileNamePath="D:" + fileNamePath;
                        }
                        File fileTemp=new File(fileNamePath);
                        if (!fileTemp.exists())
                            fileTemp.mkdirs();
                        //文件名称使用UUID
                        fileName= StringUtil.generationUuid() + "." + FilenameUtils.getExtension(fileName);
                        FileOutputStream fouts=new FileOutputStream(new File(fileNamePath, fileName));
                        InputStream upins=file.getInputStream();
                        IOUtils.copy(upins, fouts);
                        fouts.close();
                        upins.close();
                        logger.info("======文件上传 End====FileNamePath  " + fileNamePath + File.separator + fileName);
                        //result.put("evidenceImg", fileNamePath + File.separator + fileName);
                        //设置图片路径
                        String oldPath="".equals(StringUtil.isNull(repayConfirm.getEvidenceImg())) ? "" : repayConfirm.getEvidenceImg() + ",";
                        repayConfirm.setEvidenceImg(oldPath + fileNamePath + File.separator + fileName);//File.separator
                    }
                }
            }
            logger.info("图片地址：" + repayConfirm.getEvidenceImg());
            SysUser sysUser=this.getLoginUser(request);
            // 1审核中2审核通过3审核拒绝4已完成5还款失败
            repayConfirm.setStatus(Integer.valueOf(AuditStatusEnum.AUDITING.getValue()));
            String uuid=StringUtil.generationUuid();
            repayConfirm.setOfflineId(uuid);
            repayConfirm.setBorrowId(borrrowId);
            repayConfirm.setApplyUserId(sysUser.getId());
            repayConfirm.setOperatorId(sysUser.getId());
            repayConfirm.setApplyType(RepayConfirmApplyTypeEnum.PENALTY_AUDIT.getValue());//逾期查账
            int i=this.repayConfirmService.insertRepayConfirmInfo(repayConfirm);
            if (i == 1) {
                result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
                result.put(Constant.RESPONSE_CODE_MSG, "操作成功");
            } else {
                result.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
                result.put(Constant.RESPONSE_CODE_MSG, "操作失败");
            }
        }
        ServletUtils.writeToResponse(response, result);
    }*/
}
