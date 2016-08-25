package utility;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import utility.HelperUtility;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

public class FileCollect {
	public static Logger log = Logger.getRootLogger();
	private HelperUtility helper;
	
	
	static public String currentTime() {

			String currenttime = "";
	        Calendar cal = Calendar.getInstance();
	        SimpleDateFormat sdf = new SimpleDateFormat("MMddHHmm");
	        currenttime = sdf.format(cal.getTime()) ;
	        System.out.println( currenttime );
	        currenttime = currenttime.replace(":", "");
	        return currenttime;

	}
	
	static public void cleanPicFile(String directory) throws IOException {
		File dire = new  File(directory);
			
		FileUtils.cleanDirectory(dire); 

}
	
	
	
	static public boolean copyfile(String currentTime){
		File source = new File("screenshots");
		File dest = new File("L:\\johnL\\apptest\\test" + currentTime + "\\screenshots\\");
		try {
		    FileUtils.copyDirectory(source, dest);
		} catch (IOException e) {
		    e.printStackTrace();
		}
		
		return true;
		
	}
	
	static public boolean writefile(String beginTime, String finishTime, LinkedHashMap<String,String> fileMap,
			SortedMap<String,String> dataMapInit,SortedMap<String,String> dataMapAfter,WebDriver driver,String payFrom, String payAmount, String transferFrom, String transferTo,String transferAmount
			){
		
		
		try {
			
			String content = "This is the content to write into file";

			File file = new File("L:\\johnL\\apptest\\test" + beginTime + "\\appTestReport.html");
			
			
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("<!DOCTYPE html>");
			
			bw.write("<html>");
			bw.write("<head><STYLE TYPE='text/css'> td{font-family: Arial; font-size: 10pt;}</STYLE></head>");
			bw.write("<body>");
			bw.write("<p>");
			
			bw.write("<font size='10'>" +  "Portcullis App Test "  +  "</font>" );
			bw.write("<br>" );
			
			bw.write("<font size='6'>" +  "Begin at " + beginTime  +  "</font>" );
			bw.write("<br>" );
			bw.write("<font size='6'>" +  "end at " + finishTime  +  "</font>" );
			bw.write("<br>" );
			bw.write("</p>");
			
			int i = 1;
			for (Entry<String, String> entry : fileMap.entrySet()){	
				String k = entry.getKey();
				String v = entry.getValue();
				
				try {
					bw.write("<p>");
				
					bw.write("<font size='6'>" + i + "." + v +  "." +  "</font>" );
					
					bw.write("</p>");
					log.info("Key : " + k + " Value : " + v);
					bw.write("<p>");
					bw.write("<img src='L:/johnL/apptest/test" + beginTime + "/screenshots/" + k  + ".png'" +  "height='640' width='360'>");
					bw.write("</p>");
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				i++;
			}
			bw.write("<table border='1' style='width:80%'>");
			bw.write("<tr>");
			bw.write("<td>" + "tranfer from account:" + "</td>" );
			bw.write("<td>" + transferFrom + "</td>" );
			bw.write("</tr>");
			
			bw.write("<tr>");
			bw.write("<td>" + "before tranfer have :" + "</td>" );
			bw.write("<td>" +  dataMapInit.get(transferFrom) + "</td>" );
			bw.write("</tr>");
			
			bw.write("<tr>");
			bw.write("<td>" + "tranfer amount:" + "</td>" );
			bw.write("<td>" + "-" + transferAmount + "</td>" );
			bw.write("</tr>");
			
			bw.write("<tr>");
			bw.write("<td>" + "after tranfer have :" + "</td>" );
			bw.write("<td>"  + dataMapAfter.get(transferFrom) + "</td>" );
			bw.write("</tr>");
			
			
			bw.write("<tr></tr>");
			bw.write("<tr>");
			bw.write("<td>" + "tranfer to account:" + "</td>" );
			bw.write("<td>" + transferTo + "</td>" );
			bw.write("</tr>");
			
			bw.write("<tr>");
			bw.write("<td>" + "before tranfer have :" + "</td>" );
			bw.write("<td>"  + dataMapInit.get(transferTo) + "</td>" );
			bw.write("</tr>");
			
			bw.write("<tr>");
			bw.write("<td>" + "tranfer amount:" + "</td>" );
			bw.write("<td>" + "+" + transferAmount + "</td>" );
			bw.write("</tr>");
			
			bw.write("<tr>");
			bw.write("<td>" + "after tranfer have :" + "</td>" );
			bw.write("<td>"  + dataMapAfter.get(transferTo) + "</td>" );
			bw.write("</tr>");
			
			bw.write("<tr></tr>");
			bw.write("<tr>");
			bw.write("<td>" + "pay from account:" + "</td>" );
			bw.write("<td>" + payFrom + "</td>" );
			bw.write("</tr>");
			
			bw.write("<tr>");
			bw.write("<td>" + "before pay have :" + "</td>" );
			bw.write("<td>"  + dataMapInit.get(payFrom) + "</td>" );
			bw.write("</tr>");
			
			bw.write("<tr>");
			bw.write("<td>" + "payment amount:" + "</td>" );
			bw.write("<td>" + "-" + payAmount + "</td>" );
			bw.write("</tr>");
			
			bw.write("<tr>");
			bw.write("<td>" + "after tranfer have :" + "</td>" );
			bw.write("<td>"  + dataMapAfter.get(payFrom) + "</td>" );
			bw.write("</tr>");
			bw.write("</table>");
			
			
			
			bw.write("</body>");
			bw.write("</html>");
			
			bw.close();

			System.out.println("Done");

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return true;
		
	}
	
	
	
	
	public  static void outputhtml(String beginTime, String finishTime,LinkedHashMap<String,String> fileMap,SortedMap<String,String> dataMapInit,SortedMap<String,String> dataMapAfter,WebDriver driver,String payFrom, String payAmount, String transferFrom, String transferTo,String transferAmount
			) {		
		
		if(copyfile(beginTime)){
		writefile(beginTime,finishTime,fileMap,dataMapInit, dataMapAfter, driver,
				payFrom, payAmount, transferFrom, transferTo,
				transferAmount );
			
		}
		
		
		
		
    }
}
