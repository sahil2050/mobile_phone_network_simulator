import java.util.*;
import java.lang.*;

public class Exchange{
	int exchangeID;
	int depth;
	boolean isBaseExchange;
	public Exchange Parent;
	public Vector<Exchange> children;
	public MobilePhoneSet residents;
	Exchange(int num){
		exchangeID=num;
		depth=0;
		isBaseExchange=true;
		Parent=null;
		children=new Vector<Exchange>();
		residents=new MobilePhoneSet(null);
	}
	public Exchange parent(){
		return Parent;
	}
	public int numChildren(){
		return children.size();
	}
	
	public Exchange child(int i)throws IllegalStateException{
		try{
		if(i<numChildren())
		return children.get(i);
		else throw new IllegalStateException("Exchange don't have "+String.valueOf(i)+"th child");}
		catch (IllegalStateException e){
			System.out.println(e.getMessage());
			return null;
		}
	}
	public boolean isRoot(){
		return (Parent==null);
	}
	
	public RoutingMapTree subtree(int i) throws IllegalStateException{
		try{
		if(i<numChildren()){
		RoutingMapTree ans=new RoutingMapTree(children.get(i));
		return ans;}
		else throw new IllegalStateException("Exchange don't have "+String.valueOf(i)+"th child");}
		catch (IllegalStateException e){
			System.out.println(e.getMessage());
			return null;
		}
	}
	public MobilePhoneSet residentSet(){
		return residents;
	}
	public MobilePhone find(int id){
		if(residents==null)return null;
		if(residents.Phone_Set==null)return null;
		LinkedListNode<MobilePhone> temp=residents.Phone_Set.set;
		while(temp!=null){
			if(temp.data.id==id)return temp.data;
			temp=temp.next;
		}
		return null;
	}
}