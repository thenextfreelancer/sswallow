/**
 * 
 */
package com.test;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * @author Moksh
 *
 */
public class Util
{
   
   public static void giveSpaceInLogs(int count)
   {
      for (int ii = 0; ii < count; ii++)
      {
         System.out.println("  ");
      }

   }
   
   /**
    * Custom fluent wait 
    * 
    * See {@link org.openqa.selenium.support.ui.FluentWait}
    * 
    * @param locator
    * @param driver
    * @param timeout
    * @param polling
    * @return
    * @throws InterruptedException
    */
   public static WebElement fluentWait(final By locator, final WebDriver driver, final int timeout, final int polling) throws InterruptedException {
      int ii = polling;
      while(ii < timeout) {
         List<WebElement> allEle = driver.findElements(locator);
         if (allEle.size() > 0)
         {
            return allEle.get(0);
         }
         Thread.sleep(polling*1000);
         ii += polling;
      }
      return null;
   }
   

   public void writeInFile(String text, String fileLocation)
   {
      FileWriter fw = null;
      try
      {
         fw = new FileWriter(fileLocation, true);
         fw.write(text);
         fw.write("\n");
      }
      catch (IOException ioe)
      {
         System.err.println("IOException: " + ioe.getMessage());
      }
      finally
      {
         try
         {
            fw.close();
         }
         catch (IOException e)
         {
            e.printStackTrace();
         }
      }
   }

   /**
    * Scrolling window down
    * 
    * @param driver
    * @throws InterruptedException
    */
   public static void scrollWindow(final WebDriver driver) throws InterruptedException {
      JavascriptExecutor jse = (JavascriptExecutor) driver;
      jse.executeScript("window.scrollBy(0, document.body.scrollHeight)", "");
      Thread.sleep(1000);
   }
}