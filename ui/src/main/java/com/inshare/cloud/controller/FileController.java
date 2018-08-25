package com.inshare.cloud.controller;

import com.inshare.cloud.util.ExcelUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * description:
 * author: Inshare
 * date: 2018-8-24
 */
@Controller
public class FileController {

    private static String UPLOADED_FOLDER = "D:/Spring Boot Projects/uploadFile/";

    @PostMapping("/fileUpload")
    @ResponseBody
    public String singleFileUpload(@RequestParam("fileName") MultipartFile file) {
        if (uploadFile(file)) {
            return "上传成功";
        }
        return "上传失败";
    }

    @PostMapping("/excelUpload")
    @ResponseBody
    public List<List<Object>> excelUpload(@RequestParam("fileName") MultipartFile file) throws Exception {
        if(uploadFile(file)) {
            String fileName = file.getOriginalFilename();
            InputStream ins = new FileInputStream(UPLOADED_FOLDER + fileName);
            List<List<Object>> data = ExcelUtil.getListByExcel(ins, fileName);
            return data;
        }
        return null;
    }

    /**
     * 导出Excel文件
     * @param request
     * @param response
     */
    @PostMapping("/createExcel")
    public void createExcel(HttpServletRequest request, HttpServletResponse response) {
        String fileName = "学生信息.xls";
        String sheetName = "软件工程1班";
        String[] titles = {"姓名", "年龄", "邮箱", "生日", "体重"};
        List<Object[]> values = new ArrayList<>();
        Object[] objs1 = {"蔡贵超", 28, "5421453@qq.com", "1989-09-27", 52.5};
        Object[] objs2 = {"huangguohai", 36, "5421dgd3@qq.com", "1982-04-27", 61.5};
        values.add(objs1);
        values.add(objs2);
        HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(sheetName, titles, values);

        //响应到客户端，生成excel并让浏览器下载
        try {
            this.setResponseHeader(response, fileName);
            OutputStream os = response.getOutputStream();
            wb.write(os);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //发送响应流方法
    private void setResponseHeader(HttpServletResponse response, String fileName) {
        try {
            try {
                fileName = new String(fileName.getBytes(), "ISO8859-1");
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            response.setContentType("application/octet-stream;charset=ISO8859-1");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private boolean uploadFile(MultipartFile file) {
        if (file.isEmpty()) {
            return false;
        }
        String fileName = file.getOriginalFilename();
        int size = (int) file.getSize();
        System.out.println(fileName + "-->" + size);

        File dest = new File(UPLOADED_FOLDER + fileName);
        if (!dest.getParentFile().exists()) { //判断文件父目录是否存在
            dest.getParentFile().mkdir();
        }
        try {
            file.transferTo(dest); //保存文件
            return true;
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }
}
