package com.sun.admin;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;

import java.io.*;
import java.util.*;

public class ExcelWrite  {
    @Test
    public void Writeo3() throws FileNotFoundException,IOException{
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        Row row = sheet.createRow(0);
        Cell cell1 = row.createCell(0);
        cell1.setCellValue("星期几");

        Cell cell2= row.createCell(1);
        cell2.setCellValue("星期五");

        Row row1 = sheet.createRow(1);
        Cell cell21 = row1.createCell(0);
        cell21.setCellValue("日期");

        Cell cell22= row1 .createCell(1);
        String s = new DateTime().toString("YYYY-MM-DD HH:MM:SS");
        cell22.setCellValue(s);

        OutputStream fileOutputStream = new FileOutputStream(new File("D:\\test.xls"));
        workbook.write(fileOutputStream);
        fileOutputStream.close();
        System.out.println("生成完毕");



    }

    @Test
    public void TestBigFIle()throws FileNotFoundException,IOException{
        long l = System.currentTimeMillis();
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        for (int i = 0; i <10000 ; i++) {
            Row row = sheet.createRow(i);
            for (int j = 0; j <10 ; j++) {
                Cell cell = row.createCell(j);
                cell.setCellValue(j);
            }
        }

        FileOutputStream fileOutputStream = new FileOutputStream("D:\\test.xlsx");
        workbook.write(fileOutputStream);
        fileOutputStream.close();
        long l1 = System.currentTimeMillis();
        System.out.println(l+"====="+l1+"=========="+(l1-l));
    }

    /**
     * 测试读取表数据
     */
    @Test
    public void TestExcelRead()throws FileNotFoundException,IOException{
        List<Object> list = new ArrayList<>();
        HashMap<Object, Object> map = new HashMap<>();

        File file = new File("D:\\test.xlsx");

        if (!file.exists()){
            System.out.println("不存在");
            return;
        }

        //获取Excel输入流
        FileInputStream inputStream = new FileInputStream(file);

        //生成表对象
        Workbook workbook = new XSSFWorkbook(inputStream);
        //生成Sheet页
        Sheet sheet = workbook.getSheetAt(0);
        for (int i = 0; i <10 ; i++) {
            list.clear();
            Row row = sheet.getRow(i);
            for (int j = 0; j <10 ; j++) {
                Cell cell = row.getCell(j);
                list.add(cell.getNumericCellValue());
            }
            System.out.println(list);
            map.put(i,((ArrayList<Object>) list).clone());

        }
        System.out.println(map.toString());
        inputStream.close();
    }

    /**
     * 测试单元格类型
     * @throws FileNotFoundException
     * @throws IOException
     */
    @Test
    public void ExcelReadType()throws FileNotFoundException,IOException{
        File file = new File("D:\\test.xls");
        if (!file.exists()){
            System.out.println("not exists");
            return;
        }
        FileInputStream inputStream = new FileInputStream(file);
        Workbook workbook = new HSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);
        //创建列表接受表数据
        List<Object> list = new ArrayList<>();
        //创建StringBuffer
        StringBuffer stringBuffer=new StringBuffer();
        //获取表头
        Row rowTitle = sheet.getRow(0);
        if (rowTitle!=null){
            //一定要掌握,获取该行有多少列
            int cells = rowTitle.getPhysicalNumberOfCells();
            System.out.println(cells);
            for (int cellNumber = 0; cellNumber <cells ; cellNumber++) {
                //获取列的类型
                Cell cell = rowTitle.getCell(cellNumber);
                int cellType = cell.getCellType();
                if(cell!=null){
                    list.add(cell.getStringCellValue());
                    stringBuffer.append(cell.getStringCellValue()+"-");
                }
            }
        }
        System.out.println(stringBuffer);
        //获取表数据

        //获取表中有多少行
        int numberOfRows = sheet.getPhysicalNumberOfRows();


        for (int i = 1; i < numberOfRows; i++) {
            Row row = sheet.getRow(i);
            //情况stringBuffer里的数据，重新进行使用
            stringBuffer.delete(0, stringBuffer.length());
            if (row!=null){
                int numberOfCells = rowTitle.getPhysicalNumberOfCells();
                for (int j = 0; j <numberOfCells ; j++) {
                    Cell cell = row.getCell(j);
                    String stringCellValue = cell.getStringCellValue();
                    stringBuffer.append(stringCellValue+"-");
                }
                stringBuffer.delete(stringBuffer.length()-1, stringBuffer.length());
                System.out.println(stringBuffer);

            }

        }

        inputStream.close();
    }
}
