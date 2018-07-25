package com.test;

import java.io.FileInputStream;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @author moksh
 *
 */
public class ExcelReader
{

   private String fileName = null;

   private final String XLS = "xls";

   private final String XLSX = "xlsx";

   /**
    * For Testing only
    * 
    * @param args
    */
   public static void main(String[] args)
   {
//      String fileName = "input/input.xlsx";
//      System.out.println(new ExcelReader(fileName).get2DArray());
   }

   public ExcelReader(String fileName)
   {
      this.fileName = fileName;
   }

   public String[][] getKeywordsArray()
   {
      Row row;
      Cell cell;
      String[][] value = null;
      try
      {
         FileInputStream inputStream = new FileInputStream(fileName);

         Workbook workbook = getWorkBookInstance(inputStream);

         // get sheet number
         int sheetCn = workbook.getNumberOfSheets();

         for (int cn = 0; cn < sheetCn; cn++)
         {
            // get 0th sheet data
            Sheet sheet = workbook.getSheetAt(0);

            // get number of rows from sheet
            int rows = sheet.getPhysicalNumberOfRows();

            // get number of cell from row
            int cells = 2; //sheet.getRow(cn).getPhysicalNumberOfCells();

            value = new String[rows][cells];

            for (int r = 0; r < rows; r++)
            {
               if (r == 0)  //skip headers
                  continue;

               row = sheet.getRow(r); // bring row
               if (row != null)
               {
                  for (int c = 0; c < cells; c++)
                  {
                     cell = row.getCell(c);
                     if (cell != null)
                     {

                        switch (cell.getCellTypeEnum())
                        {
                        case FORMULA:
                           // do nothing
                           System.out.println("WARNING: Function Cell Found.");
                           break;

                        case NUMERIC:
                           value[r][c] = "" + cell.getNumericCellValue();
                           break;

                        case STRING:
                           value[r][c] = "" + cell.getStringCellValue();
                           break;

                        case BLANK:
                           value[r][c] = "";
                           break;

                        case ERROR:
                           // do nothing
                           break;
                        default:
                        }
                     }
                  } // for(c)
               }
            } // for(r)
         }
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }

      return value;
   }

   public String[][] getURLArray()
   {
      Row row;
      Cell cell;
      String[][] value = null;
      try
      {
         FileInputStream inputStream = new FileInputStream(fileName);

         Workbook workbook = getWorkBookInstance(inputStream);

         // get sheet number
         int sheetCn = workbook.getNumberOfSheets();

         for (int cn = 0; cn < sheetCn; cn++)
         {
            // get 0th sheet data
            Sheet sheet = workbook.getSheetAt(0);

            // get number of rows from sheet
            int rows = sheet.getPhysicalNumberOfRows();

            // get number of cell from row
            int cells = sheet.getRow(cn).getPhysicalNumberOfCells();

            value = new String[rows][cells];

            for (int r = 0; r < rows; r++)
            {
               if (r == 0)  //skip headers
                  continue;

               row = sheet.getRow(r); // bring row
               if (row != null)
               {
                  for (int c = 0; c < cells; c++)
                  {
                     cell = row.getCell(c);
                     if (cell != null)
                     {
                        switch (cell.getCellTypeEnum())
                        {
                        case FORMULA:
                           // do nothing
                           System.out.println("WARNING: Function Cell Found.");
                           break;

                        case NUMERIC:
                           value[r][c] = "" + cell.getNumericCellValue();
                           break;

                        case STRING:
                           value[r][c] = "" + cell.getStringCellValue();
                           break;

                        case BLANK:
                           value[r][c] = "";
                           break;

                        case ERROR:
                           // do nothing
                           break;
                        default:
                        }
                     }
                  } // for(c)
               }
            } // for(r)
         }
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }

      return value;
   }
   
   private Workbook getWorkBookInstance(FileInputStream inputStream) throws Exception
   {
      String ext = getFileExtension(fileName);
      Workbook workbook = null;
      if (XLS.equalsIgnoreCase(ext))
      {
         workbook = new HSSFWorkbook(inputStream);
      }
      else if (XLSX.equalsIgnoreCase(ext))
      {
         workbook = new XSSFWorkbook(inputStream);
      }
      return workbook;
   }

   private String getFileExtension(String fileName)
   {
      if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
         return fileName.substring(fileName.lastIndexOf(".") + 1);
      else
         return "";
   }

}
