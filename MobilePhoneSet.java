import java.util.*;
import java.lang.*;

public class MobilePhoneSet{
	public Myset<MobilePhone> Phone_Set;
	MobilePhoneSet(Myset<MobilePhone> a){
		Phone_Set=a;
	}
	public void add(MobilePhone a){
		if(a==null)return;
		if(Phone_Set!=null)
		Phone_Set.insert(a);
		else {
			LinkedListNode<MobilePhone> temp=new LinkedListNode(a);
			Phone_Set=new Myset(temp);
		}
	}
	public void remove(MobilePhone a){
		if(Phone_Set!=null)
		Phone_Set.delete(a);
	}
}