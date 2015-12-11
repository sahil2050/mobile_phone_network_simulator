import java.util.*;
import java.lang.*;

public class ExchangeList{
	public LinkedListNode<Exchange> head;
	ExchangeList(LinkedListNode<Exchange> a){
		head=a;
	}
	public void add(Exchange a){
		LinkedListNode<Exchange> newNode=new LinkedListNode(a);
		newNode.next=head;
		head=newNode;
	}
}