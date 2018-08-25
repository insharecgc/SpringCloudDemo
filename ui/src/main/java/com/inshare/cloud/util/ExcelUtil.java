package com.inshare.cloud.util;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Excel 导入导出工具类
 * @author Guichao
 * @since 2018/8/24
 */
public class ExcelUtil {

    private final static String excel2003L = ".xls";    // 2003- 版本的excel
    private final static String excel2007U = ".xlsx";   // 2007+ 版本的excel

    private final static DecimalFormat df = new DecimalFormat("0"); // 格式化number String字符
    private final static DecimalFormat df2 = new DecimalFormat("0.00"); // 格式化数字
    private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 日期格式化
    private final static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"); // 日期时间格式化

    /**
     * 导出 Excel xls 版本的
     * @param sheetName sheet名称
     * @param titles 第一行标题数组
     * @param values 内容
     * @return
     */
    public static HSSFWorkbook getHSSFWorkbook(String sheetName, String[] titles,
                                               List<Object[]> values){

        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();

        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(sheetName);

        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        HSSFRow row = sheet.createRow(0);

        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
//        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

        //声明列对象
        HSSFCell cell = null;

        //创建标题
        for(int i=0;i<titles.length;i++){
            cell = row.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(style);
        }

        //创建内容
        for(int i=0;i<values.size();i++){
            row = sheet.createRow(i + 1);
            Object[] rowDatas = values.get(i);
            for(int j=0;j<rowDatas.length;j++){
                //将内容按顺序赋给对应的列对象
                if(!"".equals(rowDatas[j]) && rowDatas[j] != null) {
                    row.createCell(j).setCellValue(rowDatas[j].toString());
                }
            }
        }
        return wb;
    }

    /**
     * 导入 Excel 文件
     * @param in Excel 文件流
     * @param fileName 文件名称
     * @return 返回 Excel 文件中的内容
     */
    public static List<List<Object>> getListByExcel(InputStream in, String fileName) throws Exception {
        List<List<Object>> list = null;
        Workbook workbook = getWorkbook(in, fileName);
        if (null == workbook) {
            throw new Exception("创建Excel工作簿为空");
        }
        Sheet sheet = null;
        Row row = null;
        Cell cell = null;

        list = new ArrayList<>();

        // 遍历 Excel 中所有的sheet
        for (int i=0; i<workbook.getNumberOfSheets(); i++) {
            sheet = workbook.getSheetAt(i);
            if (sheet == null) {
                continue;
            }
            // 遍历当前 sheet 中的所有行，跳过第一行标题栏
            for (int j=sheet.getFirstRowNum() + 1; j<=sheet.getLastRowNum(); j++) {
                row = sheet.getRow(j);
                List<Object> li = new ArrayList<>();
                for (int k=row.getFirstCellNum(); k<row.getLastCellNum(); k++) {
                    cell = row.getCell(k);
                    li.add(getCellValue(cell));
                }
                list.add(li);
            }
        }

        return list;
    }

    private static Workbook getWorkbook(InputStream inStr, String fileName) throws Exception {
        Workbook wb = null;
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        if (excel2003L.equals(fileType)) {
            wb = new HSSFWorkbook(inStr); // 2003-
        } else if (excel2007U.equals(fileType)) {
            wb = new XSSFWorkbook(inStr); // 2007+
        } else {
            throw new Exception("解析的文件格式有误！");
        }
        return wb;
    }

    private static Object getCellValue(Cell cell) {
        Object value = null;
        if (cell == null) {
            return "";
        }
        switch (cell.getCellTypeEnum()) {
            case STRING:    // 字符串类型
                value = cell.getRichStringCellValue().getString();
                break;
            case NUMERIC:   // 数值型
                if ("General".equals(cell.getCellStyle().getDataFormatString())) {
                    value = df.format(cell.getNumericCellValue());
                } else if ("m/d/yy".equals(cell.getCellStyle().getDataFormatString())) {
                    value = sdf.format(cell.getDateCellValue());
                } else {
                    value = df2.format(cell.getNumericCellValue());
                }
                break;
            case BOOLEAN:   // 布尔型
                value = cell.getBooleanCellValue();
                break;
            case BLANK:     // 空值
                value = "";
                break;
            default:
                break;
        }
        return value;
    }
}
