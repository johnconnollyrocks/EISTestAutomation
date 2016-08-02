package bornincloud;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;


/**
* This java example will demonstrate getting a random date within
* a given year.
*
* @author Justin Musgrove
* @see <a href='http://www.leveluplunch.com/java/examples/generate-random-date/'>Generate random date</a>
*
*/
public class Random_Date{



	 private Date dMin = null;
	    private Date dMax = null;
	    /** Creates a new instance of RandomDateGenerator */
	    public Random_Date(Date min, Date max) {
	        dMin = min;
	        dMax = max;
	    }
	    
	    public Date generate() {
	        long MILLIS_PER_DAY = 1000*60*60*24;
	        GregorianCalendar s = new GregorianCalendar();
	        s.setTimeInMillis(dMin.getTime());
	        GregorianCalendar e = new GregorianCalendar();
	        e.setTimeInMillis(dMax.getTime());
	        
	        // Get difference in milliseconds
	        long endL   =  e.getTimeInMillis() +  e.getTimeZone().getOffset(e.getTimeInMillis());
	        long startL = s.getTimeInMillis() + s.getTimeZone().getOffset(s.getTimeInMillis());
	        long dayDiff = (endL - startL) / MILLIS_PER_DAY;
	        
	        Calendar cal = Calendar.getInstance();
	        cal.setTime(dMin);
	        cal.add(Calendar.DATE, new Random().nextInt((int)dayDiff));   
	        Date retDate = new Date(cal.getTimeInMillis());
	        return retDate;
	    }
	    
	    // =========
	    // Test it:
	    // =========
	    public static void main(String args[]) {
	       long beginTime = Timestamp.valueOf("2015-01-01 00:00:00").getTime();
	       long endTime = Timestamp.valueOf("2015-12-30 00:00:00").getTime();
	      Date dMin = new Date(beginTime);
	      Date dMax = new Date(endTime);
	      Calendar dispCal = Calendar.getInstance();
	      dispCal.setTime(dMin);
	       System.out.println("Min Date:"+dispCal.getTime());
	       
	       dispCal.setTime(dMax);
	       System.out.println("Max Date:"+dispCal.getTime());
	       SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
	        
	        Random_Date rnd = new Random_Date(dMin, dMax);
	        for(int i=1;i<=10;i++)
	      	          System.out.println("Date = " + sdf.format(rnd.generate()));
	    }
	    
}