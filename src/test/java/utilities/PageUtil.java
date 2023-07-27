



package utilities;
import java.text.DateFormatSymbols;
import java.time.ZonedDateTime;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;

public class PageUtil {
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
