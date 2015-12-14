package KALENDER_PROJEKT;

	import java.io.File;
import java.io.FileInputStream;
	import java.io.FileNotFoundException;
	import java.io.FileOutputStream;
	import java.io.IOException;
	import java.io.OutputStream;
	import java.util.Scanner;

	public class InstanciatorCalendar {
		
		private static Scanner scanna = new Scanner(System.in);
		private static Meny main;
		private String name;
		private TheCalendar calendar;
		
		public InstanciatorCalendar(){
			main = add();
			main.execute();
		}
		
		/*
		*CALENDER*.add(new AbstractCalendarItem("Avsluta") {
			public void execute() { 
				//gör inget
				 * NÅGOT.excecute()
			} });
		*/
		
		
		public  Meny add(){
			Meny myCalendar = new Meny("KALENDER");
			Meny main = new Meny("HUVUDMENY");
			Meny befintlig = new Meny("KALENDRAR"); 
			Meny activities = new Meny("AKTIVITETER"); 
			Meny delete = new Meny("RADERA AKTIVITET"); 

			//ALLA UNDERRUBRIKER TILL VÄLKOMMEN AKA MAIN
			main.add(new AbstractCalendarItem("Öppna befintlig kalender") {
				public void execute() { 
					boolean isOK = true;
					FileInputStream fil;
					do{
						name = befintlig();
						try{
							fil = new FileInputStream(name+".txt");
							isOK=true;
							calendar = new TheCalendar(name);
							calendar.load(fil); 	////LADDDAR BEFINTLIG KALENDER	
						}catch(IOException f){
							System.out.println("Ojoj, den kalendern fanns ej!");
							isOK=false;
						}
					}while(!isOK);
					calendar.keepUpdated();
					myCalendar.execute();
				} });

			main.add(new AbstractCalendarItem("Skapa ny kalender") {//SPARAR OCH SKAPAR EN NY KALENDER
				public void execute() { 
					System.out.println("Vad vill du att din kalender ska heta?");
					String chosenName = scanna.nextLine();
					name = chosenName;
					TheCalendar c = new TheCalendar(name);
					calendar=c;
					calendar.newFile();
					System.out.println("Toppen! nu öppnar vi din kalender");
					myCalendar.execute();//BORDE VARA NGT NYTT
				} });
			
			main.add(new AbstractCalendarItem("Avsluta") {			
				public void execute() { 
					OutputStream output;
					boolean isOK=true;
						
					do{
						try {
							output = new FileOutputStream(name+".txt");
							calendar.save(output);
							isOK=true;
						} catch (IOException e) {
							isOK=false;
							e.printStackTrace();
						}
					}while(!isOK);
				} });
			
			
			
			
			//INNE I KALENDER [NAMN + KALENDER]
			myCalendar.add(new AbstractCalendarItem("Hitta aktiviteter") {
				public void execute() { 
					activities.execute();
				} });	
			
			myCalendar.add(new AbstractCalendarItem("Visa datum") {
				public void execute() { 
					calendar.todaysDate();
					calendar.showDates();											
					myCalendar.execute();
				} });	
			
			myCalendar.add(new AbstractCalendarItem("Skapa ny aktivitet") {
				public void execute() { 
					calendar.newActivity();
					myCalendar.execute();		//öppnar myCalendar menyn
				} });	
			
			myCalendar.add(new AbstractCalendarItem("Skapa ny, återkommande aktivitet") {
				public void execute() { 
					calendar.repeatingActivity();
					myCalendar.execute();		//öppnar myCalendar menyn
				} });	
			
			myCalendar.add(new AbstractCalendarItem("Radera Aktiviteter") {
				public void execute() { 
					delete.execute();
				} });	
			
			myCalendar.add(new AbstractCalendarItem("Statistik") {			//obs obs
				public void execute() { 
					calendar.statistik();
					myCalendar.execute();
				} });	
			
			myCalendar.add(new AbstractCalendarItem("Växla användare") {
				public void execute() { 
					File fil = new File(name+"txt");
					fil.delete();
					OutputStream output;
					try {
						output = new FileOutputStream(name+".txt");
						calendar.save(output);
					} catch (IOException e) {
						e.printStackTrace();
					}
					main.execute(); 	//går tbx till huvudmeny
				} });		
			
			myCalendar.add(new AbstractCalendarItem("Avsluta") {
				public void execute() { 
					OutputStream output;
					try {
						output = new FileOutputStream(name+".txt");
						calendar.save(output);
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					//gör inget, slutar
				} });	
					
			
			//MENYER TILL SÖK AKTIVITET
			activities.add(new AbstractCalendarItem("Sök aktivitet på datum") {
				public void execute() { 
					calendar.searchDate();
					activities.execute();
				} });	
			
			activities.add(new AbstractCalendarItem("Sök aktivitet pånamn") {
				public void execute() { 		
						calendar.searchName();
						activities.execute();
				} });	
			
			activities.add(new AbstractCalendarItem("Visa alla befintliga aktiviteter") {
				public void execute() {
					calendar.showAllActivities();
					activities.execute(); //går tillbaka till din kalenders huvudmeny
				} });	
			
			activities.add(new AbstractCalendarItem("Radera aktivitet") {
				public void execute() { 		
					delete.execute();
				} });	
			
			activities.add(new AbstractCalendarItem("Tillbaka till huvudmeny") {
				public void execute() { 
					myCalendar.execute(); //går tillbaka till din kalenders huvudmeny
				} });		
			
			
			
			

			
		
			//ALLA UNDERRUBRIKER TILL RADERA
			delete.add(new AbstractCalendarItem("Sök efter namn") {
				public void execute() { 
					calendar.nameDelete();
					myCalendar.execute();
				} });
			
			delete.add(new AbstractCalendarItem("Radera återkommande aktivitet") {
				public void execute() { 
					calendar.repeatDelete();
					myCalendar.execute();
				} });
			
			delete.add(new AbstractCalendarItem("Rensa dag") {
				public void execute() { 
					calendar.dayDelete();
					myCalendar.execute();
				} });
			
			delete.add(new AbstractCalendarItem("Tillbaka") {
				public void execute() { 
					myCalendar.execute();
				} });
			
			return(main);
			
		}//END ADD
		
		
		private void controllSave(){
			
		}
		
		private static String befintlig(){
			for(int i = 0; i<=20; i++)
				System.out.print("=");
			System.out.println("\nBEFINTLIGA KALENDRAR");
			for(int i = 0; i<=20; i++)
				System.out.print("=");
			
			System.out.println("\nVems kalender vill du öppna?");
			String file = scanna.nextLine();
														
			return file;
			
		}


	
}
