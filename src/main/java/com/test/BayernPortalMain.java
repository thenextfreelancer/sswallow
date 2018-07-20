package com.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class BayernPortalMain
{
   public static WebDriver driver;
   
   private OutputStreamWriter output;
   
   private PrintStream out = null;

   @BeforeClass(alwaysRun = true)
   public void setUp() throws Exception
   {
      out = new PrintStream(System.out, true, "UTF-8");
   }

   @Test
   public void initSuite() throws Exception
   {
      out.println("Reading keywords to be seached!!");
      String keywordFilePath = "data" + File.separatorChar + "List_of_Database_Names_SEARCH_TERMS_20180711.xlsx";
      String[][] searchKeywords = new ExcelReader(keywordFilePath).getKeywordsArray();
      out.println("Done!!");

      out.println(" ");

      out.println("Reading URLs to search on!!");
      String urlFilePath = "data" + File.separatorChar + "List_of_library_subscriptions_WEBPAGES_TO_SEARCH_ON_20180711.xlsx";
      String[] searchUrls = new ExcelReader(urlFilePath).getURLArray();
      out.println("Done!!");

      out.println(" ");

      out.println("Opening chrome!!");
      out.println(" ");

      //Remove .exe for Mac
      System.setProperty("webdriver.chrome.driver", "lib" + File.separatorChar + "chromedriver.exe");
      
      ChromeOptions options = new ChromeOptions();
      options.addArguments("--headless");
      options.addArguments("--window-size=1200x800");
      options.addArguments("--disable-gpu");
      driver = new ChromeDriver(options);
      
      driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
      driver.manage().window().maximize();

      out.println("Starting search...");
      out.println(" ");
      String file = "output//output.csv";
      for (int urlCount = 0; urlCount < searchUrls.length; urlCount++)
      {
         String url = searchUrls[urlCount];
         if (url != null)
         {
            driver.get(url);
            Util.scrollWindow(driver);
            String pageSource = driver.getPageSource();
            StringBuilder contentToWrite = new StringBuilder("");
            for (int keywordCount = 0; keywordCount < searchKeywords.length; keywordCount++)
            {
               String keyword1 = searchKeywords[keywordCount][0];
               String keyword2 = searchKeywords[keywordCount][1];

               if(keywordCount == 26)
               out.println("hello");
                  
               if (keyword1 != null)
               {
                  int count = 0;
                  if (!"".equals(keyword1))
                  {
                     count = getKeywordCountTemp(url, pageSource, keyword1);
                     //count = getKeywordCount(url, keyword1);
                     contentToWrite.append(keyword1 + "," + url + "," + count+ System.lineSeparator());
                  }

                  if (count == 0 && !"".equals(keyword2))
                  {
                     count = getKeywordCountTemp(url, pageSource, keyword2);
//                     count = getKeywordCount(url, keyword2);
                     contentToWrite.append(keyword2 + "," + url + "," + count+ System.lineSeparator());
                  }
                  
               }
            }
            writeOutput(file, contentToWrite.toString());
         }
      }
   }

   // Old Method
//   public int getKeywordCount(String url, String keyword)
//   {
//      List<WebElement> elementList = driver.findElements(By.xpath("//*[contains(text(),'" + keyword + "')]"));
//      out.println(keyword + " word count: " + elementList.size() + " in URL:" + url);
//      return elementList.size();
//   }
   
   public int getKeywordCountTemp(String url, String source, String keyword) {
      int i = 0;
      Pattern p = Pattern.compile(keyword);
      Matcher m = p.matcher(source);
      while (m.find()) {
          i++;
      }
      out.println(keyword + " word count: " + i + " in URL:" + url);
      return i;
   }

   public void writeOutput(String file, String content)
   {
      try 
      {
         output = new OutputStreamWriter(new FileOutputStream(file, true), Charset.forName("UTF-8").newEncoder());
         output.write(content);
      }
      catch (IOException e)
      {
         // exception handling left as an exercise for the reader
      }
   }

   @AfterClass(alwaysRun = true)
   public void tearDown() throws InterruptedException
   {
      driver.quit();
   }

}