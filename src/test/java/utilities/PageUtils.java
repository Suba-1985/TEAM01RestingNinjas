package utilities;

import java.io.IOException;
import java.text.DateFormatSymbols;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;


public class PageUtils {
	static Properties prop;
	public static String[] getProgramFieldsfromExcel(String sheetname, int rownumber) throws IOException, org.apache.poi.openxml4j.exceptions.InvalidFormatException {
		ExcelReader reader = new ExcelReader();
		String arrayinput[]=new String[3];
		System.out.println(sheetname + rownumber);
		System.out.println(Config_Reader.excelpath());
		List<Map<String, String>> testdata = reader.getData(Config_Reader.excelpath(), sheetname);
		String programdescription = testdata.get(rownumber).get("programDescription");	
		String programname=testdata.get(rownumber).get("programName");
		String programstatus=testdata.get(rownumber).get("programStatus");
		System.out.println("inside excel");
		arrayinput[0]=programdescription;
		arrayinput[1]=programname;
		arrayinput[2]=programstatus;
		return arrayinput;			

	}
	
	public static String getcurrentDateTime() {
		LocalDateTime Time = LocalDateTime.now();
		String date = String.valueOf(Time);
		return date;
	}
	
	public static String batchName() {
		return String.format("%s%s-%s-%s-%s", 
				ZonedDateTime.now().getDayOfMonth(), 
				new DateFormatSymbols().getShortMonths()[ZonedDateTime.now().getMonth().getValue()-1],
				"RestingNinjas",
				"SDET","SDET"+RandomStringUtils.randomNumeric(2));
	}
	
	public static String programName() {
		return String.format("%s%s-%s-%s-%s", 
				ZonedDateTime.now().getDayOfMonth(), 
				new DateFormatSymbols().getShortMonths()[ZonedDateTime.now().getMonth().getValue()-1],
				"RestingNinjas",
				"SDET",RandomStringUtils.randomNumeric(3));
	}
	public static long phoneNum() {
	    Random random = new Random();
	    StringBuilder sb = new StringBuilder();

	    // first not 0 digit
	    sb.append(random.nextInt(9) + 1);
	    // rest of 9 digits
	    for (int i = 0; i < 9; i++) {
	        sb.append(random.nextInt(10));
	    }

	    return Long.valueOf(sb.toString()).longValue();
	}
	

}
