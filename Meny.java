package KALENDER_PROJEKT;



import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Meny implements MenyItem {
	
	public Scanner scanna = new Scanner(System.in);
    
	public String title;
	
	/**
     * Skapar en tom meny med den givna rubriken.
     */
    public Meny(String title){
    	this.title = title;
    }//END MENU
    
    

    
    private ArrayList<MenyItem> menyVal = new ArrayList<MenyItem>();
    
    /**
     * L ̈agger till ett menyval till menyn. 
     * */
    public void add(MenyItem item){
    	menyVal.add(item);
    }//END ADD
    
    public String getTitle(){
    	return title;
    }//END GET TITLE
    /**
     * Exekverar menyn enligt loopen definierad i punkterna (1) till (4).
* (1)(A) Skriver ut menyns rubrik med stora bokst ̈aver understruket med ’=’.
* (B)D ̈arefter f ̈oljer en numrerad lista  ̈over alla menyelement i denna
* 		meny, numrerade fr ̊an 0. *
* (2) Anv ̈andaren f ̊ar sedan v ̈alja ett av alterntiven genom att ange
* talet framf ̈or menyvalet. Vad h ̈ander om man inte anger ett
* giltigt tal? Anv ̈andarens menyval exekveras.
* (3) Om menyval 0 valts s ̊a returnerar metoden. 0 motsvarar
* allts ̊a alltid av avsluta/tillbaka/ ̊aterg ̊a. *
* (4) g ̊a till (1) */
    public void execute(){
    	//START(1A);
    	String  titel = getTitle();
    	for(int i = 0; i<=titel.length(); i++)
    		System.out.print("=");
    	System.out.println("\n"+titel.toUpperCase());
    	for(int i = 0; i<=titel.length(); i++)
    		System.out.print("=");
    	System.out.println("\n");
    	//END:(1A); START:(1B);
    	Iterator<MenyItem> it = menyVal.iterator();
		int i=0;
    	for(;it.hasNext(); i++){
    		System.out.println(i + ": " + it.next().getTitle());
    	}//END:(1B); START:(2);
    	i--; // kmr bli en för mycket 
    	int val = controll(i);
    	
    	MenyItem choice = menyVal.get(val);
    	choice.execute();
 
    }//END EXECUTE
    
    //controls if it is a positive integer, 0>>max number of choices or not
    private int controll(int max){
    int nr = 0;
    boolean numberOK = true;
    
    do{
    	System.out.println("välj alternativ 0-" + max + " för att fortsätta, tack:");
    	String in = scanna.nextLine();
    	
    	try{
    		nr = Integer.parseInt(in);
    		numberOK = true;
    		if(0<=nr && nr<=max){
    			return nr;
    		}else{
    			System.out.println(in + " är inget alternativ, ju!");
    			numberOK = false;
    		}
    		}catch (NumberFormatException e){
    		System.out.println(in + " är i nget alternativ, ju!");
    		numberOK = false; 
    	}
    }while(!numberOK);

    return nr;
    }//END CONTROLL
    
    
    
}//END CLASS

