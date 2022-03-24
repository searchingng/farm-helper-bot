package com.company.utils;

import com.company.Info;
import com.company.Manager;
import com.company.repository.RepositoryDB;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.IndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelUtil {

    public static void setRow(Info info, Sheet sheet, int rowCount, CellStyle style){
        Row row = sheet.createRow(rowCount);
        Cell cell;
        for (int i = 0; i < 4; i++){
            cell = row.createCell(i);
            cell.setCellStyle(style);
        }

        row.getCell(0).setCellValue(info.getNameWithoutHTML());
        row.getCell(1).setCellValue(Manager.DTOS(info.getQuantity()) + " " + info.getUnit());
        row.getCell(2).setCellValue(Manager.DTOS(info.getAmount()) + " so'm");
        row.getCell(3).setCellValue(info.getStringDate());
    }

    public static File creatingXLSX(Long userId){
        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("List");

        Row header = sheet.createRow(0);

        CellStyle style = getCellStyle(workbook);
        CellStyle headerstyle = getHeaderStyle(workbook);
        Cell cell;

        for (int i = 0; i < 4; i++){
            header.createCell(i);
            header.getCell(i).setCellStyle(headerstyle);
        }
        sheet.setColumnWidth(0, 12000);
        sheet.setColumnWidth(1, 5000);
        sheet.setColumnWidth(2, 6000);
        sheet.setColumnWidth(3, 7000);

        header.getCell(0).setCellValue("Xarajat Nomi");
        header.getCell(1).setCellValue("Miqdori");
        header.getCell(2).setCellValue("Summasi");
        header.getCell(3).setCellValue("Sanasi");


        int rowCount = 1;
        Double sum = 0d;
        for (Info info : RepositoryDB.getInfoListById(userId)){
            sum += info.getAmount();
            setRow(info, sheet, rowCount++, style);
        }

        header = sheet.createRow(rowCount + 1);
        for (int i = 0; i < 4; i++){
            header.createCell(i);
            header.getCell(i).setCellStyle(style);
        }
        header.getCell(0).setCellValue("Jami");
        header.getCell(2).setCellValue(Manager.DTOS(sum) + " so'm");

        try {
            FileOutputStream file = new FileOutputStream("calculation.xlsx");
            workbook.write(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        File file = new File("calculation.xlsx");
        return file;
    }

    public static CellStyle getHeaderStyle(XSSFWorkbook workbook){
        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setShrinkToFit(true);
        style.setBorderBottom(BorderStyle.MEDIUM);
        style.setBorderLeft(BorderStyle.MEDIUM);
        style.setBorderRight(BorderStyle.MEDIUM);
        style.setBorderTop(BorderStyle.MEDIUM);

        XSSFFont font  = workbook.createFont();
        font.setFontName("Jetbrains Mono");
        font.setColor(IndexedColors.GREEN.getIndex());
        font.setFontHeightInPoints((short) 18);
        font.setBold(true);
        style.setFont(font);

        return style;
    }

    public static CellStyle getCellStyle(XSSFWorkbook workbook){
        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setShrinkToFit(true);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);

        XSSFFont font  = workbook.createFont();
        font.setFontName("Century Gothic");
        font.setColor(IndexedColors.BLACK.getIndex());
        font.setFontHeightInPoints((short) 14);
        style.setFont(font);

        return style;
    }
}
