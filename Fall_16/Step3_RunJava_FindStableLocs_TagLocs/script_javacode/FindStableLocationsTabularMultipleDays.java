import java.util.*;
import java.io.*;

public class FindStableLocationsTabularMultipleDays {
	
	public static String dayTag;
	public static String locTag;
	public static long duration; 
	
	public static void tagLocation(String stDt, String endDt, String startHr, String endHr, long duration, long startSecs, long prevSecs){
		
		int stHr = Integer.parseInt(startHr);
		int edHr = Integer.parseInt(endHr);
		long dayTimeDuration = 0;
		long nightDuration = 0;
		String stDtArr[] = stDt.split("-");
		String endDtArr[] = endDt.split("-");
		
		// within a day duration makes sense; hence used in 1 day part
		// in multiple days or overnight stay, duration value passed is incorrect; need to consider nightDuration+DayTimeDuration > 30 mins 
				
		// overnight scenario
		if(stDtArr[2].equalsIgnoreCase(endDtArr[2]) == false){
			//dayTag = "5";
			
			// Considering Start Time 
			if(stHr <= 6){ // start in night before day
				dayTimeDuration = 12 * 3600; // 7 am to 19 PM
				
				nightDuration = 5 * 3600 + (7*3600-startSecs); // night after day till midnight + night before day
			}
			else if(stHr >= 7 && stHr <= 18){ // start in day
				dayTimeDuration = (19*3600) - startSecs; 
				
				nightDuration = 5 * 3600; // night after day till midnight 
			}
			else if(stHr >= 19){ // start in night after day
				dayTimeDuration = 0; // no day time duration for 1st day
				
				nightDuration = (24*3600) - startSecs;
			}
			
			// Considering End Time
			if(edHr <= 6){ // end in night before day
				nightDuration += prevSecs; // adds only to the night duration and no addition to the dayTimeDuration
			}
			else if(edHr >= 7 && edHr <=18){ // ends in day time
				nightDuration += 7 * 3600; // addition of whole night before day
				
				dayTimeDuration += prevSecs - (7*3600);	
			}
			else if(edHr >= 19){ // ends in night after day (or may even extend to the next day)
				dayTimeDuration += 12*3600; // 7 am to 19 pm
				
				nightDuration += (7*3600) + (prevSecs - (19*3600)); // entire night before day + night after day
			}
			
			FindStableLocationsTabularMultipleDays.duration = dayTimeDuration + nightDuration;
			
			System.out.println("day: "+dayTimeDuration+" night: "+nightDuration);
			
			// tagging the stay point
			if(dayTimeDuration + nightDuration <= 30 * 60){ // less than or equal to 30 mins
				dayTag = "on trip break";
			} else if(dayTimeDuration > nightDuration) 
				dayTag = "1";
			else if(nightDuration > dayTimeDuration)
				dayTag = "2";
				
			
			return;
		}		
		
		// 1 day part
		if (stHr >= 7 && edHr <= 18 && duration > 30) // starts and ends in day time
			dayTag = "1";
		else if(duration > 30)
			{
				if(stHr >= 0 && stHr <=6 && edHr >= 7 && edHr <=18) // starts in night and ends in day-----
				{
					/*System.out.println(startSecs +","+ prevSecs);
					System.out.println(7*3600-startSecs);
					System.out.println(prevSecs - 7*3600);
					
					Boolean flag = (7*3600-startSecs) > (prevSecs - 7*3600);
					System.out.println(flag);*/
					
					if((7*3600-startSecs) > (prevSecs - 7*3600)) // night time more than day time
						dayTag = "2";
					else	// day time more than night time
						dayTag = "1";
				}
						
				else if(stHr >= 0 && stHr <=6 && edHr > 18 && edHr < 24) // start in the night and ends in the night after the day
				{
					dayTimeDuration = 12 * 3600;
					
					nightDuration = (7*3600 - startSecs) + (prevSecs - 19*3600); // checked
					
					if(dayTimeDuration > nightDuration)
						dayTag = "1";
					else
						dayTag = "2";
				}
					
				else if((stHr >= 0 && edHr <=6) || (stHr > 18 && edHr < 24)) // start in night before day and end in the night before day OR start in the night after day and end in the night after day
					dayTag = "2"; // checked
				
				else if(stHr >= 7 && stHr <=18 && edHr > 18 && edHr < 24) // start in the day and ends in the night-----------
				{
					dayTimeDuration = 19 * 3600 - startSecs ;
									
					nightDuration = prevSecs - 19 * 3600;
					
					//System.out.println(startSecs +","+ prevSecs);
					
					if(dayTimeDuration > nightDuration)
						dayTag = "1";
					else
						dayTag = "2";
				}
				
			}
		else dayTag = "on trip break";
	}
	
	public static void main(String args[]){
		try {
			FileReader f = new FileReader(args[0]);
			
			BufferedReader bf = new BufferedReader(f);
			
			PrintWriter pw = new PrintWriter(args[1]);
			
			String currLine = "";
			String prevLine = "";
			double currLat = 0.0;
			double currLon = 0.0;
			double prevLat = 0.0;
			double prevLon = 0.0; 
			String[] column = new String[10];
			int oCnt = 0;
			String start = "";
			
			while((currLine = bf.readLine()) != null){
				
				column = currLine.split(",");
				
				currLat = Double.parseDouble(column[1]);
				currLon = Double.parseDouble(column[2]);
				
				if(prevLat == 0.0 && prevLon == 0.0){
					
					prevLat = currLat;
					prevLon = currLon;
					prevLine = new String(currLine);
					start = currLine;
					
				}else if(prevLat == currLat && prevLon == currLon){
					oCnt++;
					prevLine = currLine;
				}else{
					if(oCnt != 0){
						
						String[] startSplit = start.split(",");
						String[] prevSplit = prevLine.split(",");
						
						String[] startDateTime = startSplit[7].split(" ");
						String[] prevDateTime = prevSplit[7].split(" ");
						
						String[] startTime = startDateTime[1].split(":");
						String[] prevTime = prevDateTime[1].split(":");
						
						long startSecs = Long.parseLong(startTime[0]) * 60 * 60 + Long.parseLong(startTime[1]) * 60 + Long.parseLong(startTime[2]);
						long prevSecs = Long.parseLong(prevTime[0]) * 60 * 60 + Long.parseLong(prevTime[1]) * 60 + Long.parseLong(prevTime[2]);
						
						duration = prevSecs - startSecs;
						
						if(duration >= 600){ // Threshold: 600 secs = 10 mins
						
							/*System.out.println(start);
							System.out.println(prevLine);
							System.out.println(oCnt+1);*/
							tagLocation(startDateTime[0],prevDateTime[0],startTime[0],prevTime[0],duration/60,startSecs,prevSecs);
							
							String toPrint = startSplit[0] + "," + prevLat + "," + prevLon + "," + startSplit[7] + "," + prevSplit[7] + "," + (duration / 60) + "," + dayTag + "\n"; 
							//System.out.println(toPrint);
							pw.write(toPrint);
						}
						oCnt = 0;
					}
					start = currLine;
					prevLat = currLat;
					prevLon = currLon;
				}
				
			}
			
			if(oCnt != 0){
				
				String[] startSplit = start.split(",");
				String[] prevSplit = prevLine.split(",");
				
				String[] startDateTime = startSplit[7].split(" ");
				String[] prevDateTime = prevSplit[7].split(" ");
				
				String[] startTime = startDateTime[1].split(":");
				String[] prevTime = prevDateTime[1].split(":");
				
				long startSecs = Long.parseLong(startTime[0]) * 60 * 60 + Long.parseLong(startTime[1]) * 60 + Long.parseLong(startTime[2]);
				long prevSecs = Long.parseLong(prevTime[0]) * 60 * 60 + Long.parseLong(prevTime[1]) * 60 + Long.parseLong(prevTime[2]);
				
				duration = prevSecs - startSecs;
				
				if(duration >= 600){ // Threshold: 600 secs = 10 mins
				
					/*System.out.println(start);
					System.out.println(prevLine);
					System.out.println(oCnt+1);*/
					tagLocation(startDateTime[0],prevDateTime[0],startTime[0],prevTime[0],duration/60,startSecs,prevSecs);
					
					String toPrint = startSplit[0] + "," + prevLat + "," + prevLon + "," + startSplit[7] + "," + prevSplit[7] + "," + (duration / 60) + "," + dayTag + "\n"; 
					//System.out.println(toPrint);
					pw.write(toPrint);
				}
				
			}
			
			bf.close();
			pw.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}

