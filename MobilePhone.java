import java.util.*;
import java.lang.*;

public class MobilePhone{
	int id;
	boolean state;
	Exchange station;
	
	MobilePhone(int num){
		id=num;
		state=false;
		station=null;
	}
	
	public int number(){
		return id;
	}
	
	public boolean status(){
		return state;
	}
	
	public void switchOn(){
		state=true;
	}
	
	public void switchOff(){
		state=false;
	}
	
	public Exchange location()throws IllegalStateException{
		try{
		if(status())
			return station;
		else throw new IllegalStateException("phone is switched off");}
		catch (IllegalStateException e){
			System.out.println(e.getMessage());
			return null;
		}
	}
}