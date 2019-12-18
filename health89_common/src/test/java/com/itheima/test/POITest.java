package com.itheima.test;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class POITest {

    @Test
    public void demo1() throws IOException {

        //创建工作簿实例
        XSSFWorkbook workbook = new XSSFWorkbook("D:\\hello.xlsx");
        //获取工作表
        XSSFSheet sheet = workbook.getSheetAt(0);
        //遍历工作表中的行
        for (Row row : sheet) {

            //遍历每一行中的每一个单元格
            for (Cell cell : row) {

                System.out.println(cell.getStringCellValue());

            }
        }
        //释放资源
        workbook.close();

    }

    @Test
    public void demo2() throws IOException {
        //创建工作簿实例
        XSSFWorkbook workbook = new XSSFWorkbook("D:\\hello.xlsx");
        //获取工作表
        XSSFSheet sheet = workbook.getSheetAt(0);
        //获取当前工作表的最后一行的索引号
        int lastRowNum = sheet.getLastRowNum();
        //循环遍历每一行
        for (int i = 0; i <= lastRowNum; i++) {

            //获取每一行
            XSSFRow row = sheet.getRow(i);

            //通过行获取当前行中的最后一个单元格的索引号
            short lastCellNum = row.getLastCellNum();
            //遍历当前行中的所有的单元格
            for (short j = 0; j < lastCellNum; j++) {
                //获取每一个单元格
                XSSFCell cell = row.getCell(j);
                System.out.println(cell.getStringCellValue());
            }

        }
        workbook.close();
    }

    @Test
    public void demo3() throws Exception {

        //创建工作簿实例
        XSSFWorkbook workbook = new XSSFWorkbook();
        //创建一个工作表
        XSSFSheet sheet = workbook.createSheet("itcast");
        //创建行
        XSSFRow row = sheet.createRow(0);
        //创建单元格
        row.createCell(0).setCellValue("序号");
        row.createCell(1).setCellValue("名字");
        row.createCell(2).setCellValue("性别");
        row.createCell(3).setCellValue("年龄");

        for (int i = 1; i <= 10; i++) {
            row = sheet.createRow(i);
            row.createCell(0).setCellValue(i);
            row.createCell(1).setCellValue("admin"+i);
            row.createCell(2).setCellValue("男");
            row.createCell(3).setCellValue(18+i);

        }

        FileOutputStream fileOutputStream = new FileOutputStream(new File("d:\\itheima.xlsx"));

        workbook.write(fileOutputStream);

        workbook.close();

    }
}
