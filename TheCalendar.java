
 package KALENDER_PROJEKT;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;





public class TheCalendar {
	
private Scanner scanna = new Scanner(System.in);
	
	private List<String> theCalendar = new ArrayList<String>(); 
	
	private String namn;
	
	public TheCalendar(String namn){
		this.namn = namn;
	}
	
	/*loads the chosen file and adds
	 * it to the list with activities
	 */
	 public void load(InputStream is){
    	 BufferedReader in = new BufferedReader(new InputStreamReader(is));
    	 try {
			while(in.ready()){//läser in vald fil, ord splitas med =
				 String next = in.readLine();
				 theCalendar.add(next);
			 }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }//END LOAD
	
	public void newFile() {
		this.namn=namn;
		try {
			PrintWriter writer = new PrintWriter(namn+".txt", "UTF-8");
		} catch (FileNotFoundException e) {//skaparej filen om man 
			System.out.println("ojoj, den filen fanns ej\n============");
		}catch (UnsupportedEncodingException e) {
			System.out.println("ojoj, den filen fanns ej\n============");
		}
	}//END new file

	
	//Denna metod sparar listan till vald textfil.
	public void save(OutputStream os) throws IOException{ 			///WHY IOEXEPTION?
		
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(os));

		
		
		Iterator<String> itWrite = theCalendar.iterator();	
		String writeThis;
		
		while(itWrite.hasNext()){
			writeThis = itWrite.next();
			out.write(writeThis+"\n");//SKRIVER TILL FIL
		}
		out.close();
	}
	
	//prints todays date
	public void todaysDate(){
		String datum = String.valueOf(LocalDateTime.now());
		String[] splitDate = datum.split("T");//this will create one array w 0=date and 1=time
		String[] splitTime = splitDate[1].split(":");//this will split the time array to: [hh][mm][ss.msms]
		for(int i = 0; i<25;i++)
			System.out.print("=");
		System.out.println("\nDagens datum är "+splitDate[0]+" och klockan är "+splitTime[0]+":"+splitTime[1]);
	}

	
	public void repeatingActivity(){
		for(int i = 0;i <=30;i++)
			System.out.print("=");
		System.out.println("\nSKAPA NY ÅTERKOMMANDE AKTIVITET");
		for(int i = 0;i <=30;i++)
			System.out.print("=");
		
		System.out.println("\nVad heter aktiviteten?");
		 	String activity = scanna.nextLine();
		 	
		System.out.println("Vilket datum vill du ha den första aktiviteten på? (YYYY-MM-DD)");
			String startDate = dateControll();
			String[] startArrayS = startDate.split("-");//creates new array w element [0]=y,[1]=mm,[2]=dd
			int startYear = Integer.valueOf(startArrayS[0]);//creates int value of the start date
			int startMonth = Integer.valueOf(startArrayS[1]);//why?  to use in the for-loop
			int startDay = Integer.valueOf(startArrayS[2]);
		
			//gets a string value of when we want to start/end the activity
		System.out.println("Vilken starttid har aktiviteten? (hh:mm)");
			 String startTime = timeControll();		
		System.out.println("Vilken sluttid har aktiviteten? (hh:mm)");
			String endTime = timeControll();
		
		//kollar hur ofta man vill att aktiviteten skall upprepas, ex. var 7e dag
		System.out.println("Hur många dagar är det mellan varje aktivitet?");
			int every = intControll(); 
		//aktiviteten ska inte upprepas för alltid, kanske! therefore = ask for how long/no of repeat months
		System.out.println("Hur många gånger skall aktiviteten upprepas?");
			int timesRepeat = intControll();
			

		System.out.println("Då lägger jag till " + activity + " klockan " 
				+ startTime + "-" + endTime + " den " + startDate + "\noch därefter var "
				+ every + " dag " + timesRepeat+" gånger.");
		
		int timesAdded=0;
		for(int date=startDay; timesAdded<=timesRepeat;date=date+every){
			
			if(date>31){//if we have a new month/year; we need to correct this!
				date=1;
				startMonth++;
					if(startMonth>12){
						startMonth=1;	
						startYear++;
					}
			}//end if
			
			//the int value of for example "08" is "8", 
			//therefore we need to correct this error to match the list
			String month ="", day="";
			if(startMonth<10){
				month = "0"+startMonth;
			}else{month = String.valueOf(startMonth);}
			if(date<10){
				 day = "0"+date; 
			}else{day=String.valueOf(date);}
			

			
			//add the things to the calendar
			theCalendar.add(startYear+"-"+month+"-"+day+"_"+startTime+"_"+endTime+"_"+activity);
			
			timesAdded++;//every time we go to the loop; add ''times added''
		}//end for loop
	}
	

	
	
	
	
	
	public void newActivity(){
		for(int i = 0;i <=16;i++)
			System.out.print("=");
		System.out.println("\nSKAPA NY AKTIVITET");
		for(int i = 0;i <=16;i++)
			System.out.print("=");
		
		System.out.println("\nVad heter aktiviteten?");
		 String activity = scanna.nextLine();
	//kollar datum	
		System.out.println("Vilket datum vill du ha aktiviteten på? (YYYY-MM-DD)");
		String date = dateControll();
	//kollar starttid
		System.out.println("Vilken starttid? Skriv enl. (hh:mm)");
		 String start = timeControll();
	//kollar sluttid		
		System.out.println("Vilken sluttid?");
		String end = timeControll();
		
		System.out.println("Då lägger jag till " + activity + "klockan " 
				+ start + "-" + end + " den " + date);
		
		theCalendar.add(date+"_"+start+"_"+end+"_"+activity);
		
	}//END NEW ACTIVITY
	
	
	
	//här får användaren söka efter alla aktiviteter på ett specifikt datum
	public void searchDate(){
		for(int i=0; i<24;i++)
			System.out.print("=");
		System.out.println("\nSÖK AKTIVITET PÅ DATUM");
		for(int i = 0;i <=24;i++)
			System.out.print("=");
	
		System.out.println("\nVilket datum vill visa? (YYYY-MM-DD):");
		String date = dateControll(); //startdag
		
		findActivity(date);
	}

	//här får användaren söka efter alla aktiviter med ett visst namn
	public void searchName(){
		for(int i=0; i<22;i++)
			System.out.print("=");
		System.out.println("\nSÖK AKTIVITET PÅ NAMN");
		for(int i = 0;i <=22;i++)
			System.out.print("=");
		

		System.out.println("\nVad heter aktiviteten du vill hitta? ");
		String namn = scanna.nextLine();
		
		findActivity(namn);
	}
	
	
	/*Shows you how many activities you have per day the next
	 * seven days
	 */
	public void statistik(){
		
	}
	
	
	/*Denna metod visar alla aktiviteter användaren har planerat
	 * mellan 2 av användaren valda datum
	 */
	public void showDates() {	
		for(int i=0; i<11;i++)
			System.out.print("=");
		System.out.println("\nVISA DATUM");
		for(int i = 0;i <=11;i++)
			System.out.print("=");
	
		System.out.println("\nVilket datum vill starta från? (YYYY-MM-DD):");
		String startString = dateControll(); //startdag 
		Date startDate = Date.valueOf(startString);
		String[] startArrayS = startString.split("-");//här under skapas int value av start- dag,månad,år
		int startYear = Integer.valueOf(startArrayS[0]);
		int startMonth = Integer.valueOf(startArrayS[1]);
		int startDay = Integer.valueOf(startArrayS[2]);
		
		System.out.println("Vilket datum vill du sluta på? (YYYY-MM-DD):");
		String endString = dateControll(); //slutdag
		Date endDate = Date.valueOf(endString);

		
		long startTime = startDate.getTime();
		long slutTime = endDate.getTime();
		
		//counts mili-sec to No. of Days
		long daysDefference = (slutTime-startTime)/(1000*60*60*24);
		long countedDays=0;
		
		for(int date=startDay; countedDays<=daysDefference; date++){//while we still have days to count....
			
			if(date>31){//if we have a new month/year; we need to correct this!
				date=1;
				startMonth++;
					if(startMonth>12){
						startMonth=1;	
						startYear++;
					}
			}//end if

			//the int value of for example "08" is "8", 
			//therefore we need to correct this error to match the list
			String year = String.valueOf(startYear);
			String month ="", day="";
			if(startMonth<10){
				month = "0"+String.valueOf(startMonth);
			}else {
				month=String.valueOf(startMonth);
			}
			
			if(date<10){
				day = "0"+String.valueOf(date);
			}else{
				day=String.valueOf(date);
			}
			
			//searches for activities on the current day
			findActivity(year+"-"+month+"-"+day);
			countedDays++;
		  }//end for-loop
	/*
	 * for(int date=startDay; timesAdded<=timesRepeat;date=date+every){
			

			

			String month ="", day="";
			if(startMonth<10){
				month = "0"+startMonth;
			}else{month = String.valueOf(startMonth);}
			if(date<10){
				 day = "0"+date; 
			}else{day=String.valueOf(date);}
			

			
			//add the things to the calendar
			theCalendar.add(startYear+"-"+month+"-"+day+"_"+startTime+"_"+endTime+"_"+activity);
			
			timesAdded++;//every time we go to the loop; add ''times added''
		}//end for loop
	 * 
	 * 
	 */
	
	
	
	}//END SHOW DATES
	
	
	
	
	
	
	
	//denna metoden raderar en aktivitet baserat på dess namn
	public void nameDelete(){
		for(int i=0; i<38;i++)
			System.out.print("=");
		System.out.println("\nRADERA ALLA AKTIVITETER MED VISST NAMN");
		for(int i=0; i<38;i++)
			System.out.print("=");

		System.out.println("\nVad heter aktiviteten?");
		String activity = scanna.nextLine(); 
		
		delete(activity);
		
	}
	
	
	public void dayDelete(){
		for(int i=0; i<37;i++)
			System.out.print("=");
		System.out.println("\nRADERA ALLA AKTIVITETER PÅ VISS DAG");
		for(int i=0; i<37;i++)
			System.out.print("=");
		
		System.out.println("\nVilken dag vill du rensa?");
		String date = dateControll();
		
		delete(date);
	}//end day delete
	
	public void repeatDelete(){
		for(int i=0; i<37;i++)
			System.out.print("=");
		System.out.println("\nRADERA ALLA AKTIVITETER MED VISST NAMN");
		for(int i=0; i<37;i++)
			System.out.print("=");
		
		System.out.println("\nVilka aktiviteter vill du ta bort?");
		String name = scanna.nextLine();
		
		delete(name);
		
	}
	
	/*This method deletes the specified array, if and only if it contains
	 * the specified Strings
	 */
	private void delete(String findFirst){
		Iterator<String> itFind = theCalendar.iterator();//kmr peka på varje array i listan
		String currentArray;//this string will have the value of the arrray thats pointed at
		int removed = 0;//this int will have the same number as the array the iteratior is pointing at
		while(itFind.hasNext()){
			currentArray = itFind.next();
			//när båda argumenten stämmer clearas det elementet
			if(currentArray.contains(findFirst)){
				itFind.remove();
				removed++;
			}
		}//end for delete
		
		/*om vi inte har lyckats hitta ngra aktiviteter som matchat användarens
		 * argument meddelar vi detta till användaren.
		 */
		if(removed==1){
			System.out.println("_______");
			System.out.println("Raderad!");
			System.out.println("_______");

		}else if(removed>1){
			System.out.println("=================================");
			System.out.println(removed + " aktiviteter raderade!");
			System.out.println("=================================");
		}
		else{
			for(int i=0; i<30;i++)
				System.out.print("=");
			System.out.println("\nInga aktiviteter funna. \nDärför är inga aktiviteter raderade.\n");
			for(int i=0; i<30;i++)
				System.out.print("=");
		}
		
	}//END DELETE
	
	
	public void showAllActivities(){
		for(int i=0; i<30;i++)
			System.out.print("=");
		System.out.println("\n ALLA BEFINTLIGA AKTIVITETER");
		for(int i=0; i<30;i++)
			System.out.print("=");
		System.out.println("");
		
		findActivity("_");
	}

	//denna aktiviteten hittar en aktivitet baserat på en string
	private void findActivity(String find){
		//theCalendar = listan
		Iterator<String> itFind = theCalendar.iterator();//kmr peka på varje array i listan
		String currentArray; 
		boolean hasFound = false;
		while(itFind.hasNext()){
			currentArray = itFind.next();
			if(currentArray.contains(find)){
				String[] content = currentArray.split("_");
				System.out.println("Den " + content[0]+ " kl " +content[1]+ " till kl "
						+content[2]+ " är det planerat: " +content[3]+"\n");
				hasFound=true;
			}
		}//end while loop
		
		if(hasFound=false){//say to the user if we havent found anything
			System.out.println("====================");
			System.out.println("Ingen aktivitet hittad.");
		}
		
		
		
	}//END FIND ACTIVITY
	
	public void keepUpdated(){				//TAR BORT AKTIVITETER MER ÄN 2 MÅN GAMLA
		String datum = String.valueOf(LocalDateTime.now());
		String[] timeSplit = datum.split("T");//this will create one array w 0=date and 1=time
		String date = timeSplit[0];
		String[] dateSplit = date.split("-");//this will create one array w 0=y,1=m,2=d
		int year = Integer.valueOf(dateSplit[0]);
		int month = Integer.valueOf(dateSplit[1]);
		int day = Integer.valueOf(dateSplit[2]); 
		
		Iterator<String> itFind = theCalendar.iterator();//kmr peka på varje array i listan
		String currentArray; 
		
		int fromMonth=month-2; //since I want to delete everything 2 months back
		
		int currentElement =-1;//the array we want to remove from the list
		
		for(int i=day;fromMonth<=month && i<=day; i++){//deletes all events more than 2 months old
			String find = year+"-"+month+"-"+i;
			while(itFind.hasNext()){
				currentArray = itFind.next();
				currentElement++;
				if(currentArray.contains(find)){
					theCalendar.remove(currentElement);
				}
			}//end while
			currentElement =-1;
			if(day>31){//ny månad
				day =1;
				fromMonth++;
					if(fromMonth>12){//ev. nytt år
						fromMonth =1;
						year++;
					}
			}
		}//end for
	}
	
	
	public void stats(){
		
	}
	
	
	/*makes sure the number is a positive interger 
	 */
	private  int intControll(){
		int nr = 0;
		boolean numberOK = true;
		do{
		String in = scanna.nextLine();
		try{
			nr = Integer.parseInt(in);
			numberOK = true;
			if(nr<=0){
				System.out.println(in + " detta är inget ok val! Testa igen: (n∈Z+)");
				numberOK = false;
			}
		}catch (NumberFormatException e){
			System.out.println(in + " Detta är inget ok val! Testa igen: (n∈Z+)");
			numberOK = false; 
			}
		}while(!numberOK);
		return nr;
	}
	


	private String dateControll(){ 
		String datum;
		boolean right = true;
		do{ //kollar så att det är ett korrekt inskrivet datum 
			datum = scanna.nextLine();
			try{
				Date.valueOf(datum);
			}catch(IllegalArgumentException ill){
				System.out.println("Det där var inte rätt. Skriv enligt formeln (YYYY-MM-DD):");
				right = false;
			}
		}while(right = false);
		return datum;
	}

	


	private String timeControll(){
		boolean isOK = true;
		String s;
		do{
			s = scanna.nextLine();
			try{
				Time.valueOf(s+":00");
				isOK = true;
			}catch(IllegalArgumentException ill){
				System.out.println("Det där var inte rätt. Skriv enligt formeln (hh:mm):");
				isOK = false;
			}
		}while(!isOK);	
		
		return s;
	}
    	


}