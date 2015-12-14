package KALENDER_PROJEKT;

public abstract class AbstractCalendarItem implements MenyItem {

	
	    private String title;
	    
	    public AbstractCalendarItem(String title) {
	        this.title = title;
	    }
	    
	    public abstract void execute();
	    
	    public String getTitle() {
	        return title;
	} 
}
