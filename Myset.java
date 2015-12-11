import java.util.*;
import java.lang.*;

class LinkedListNode<Item> {
	Item data;
	LinkedListNode<Item> next;
	LinkedListNode(Item o){
		data=o;
		next=null;
	}
}

public class Myset <Item> {
	
	public LinkedListNode<Item> set;
	
	Myset(LinkedListNode<Item> a){
		set=a;
	}
	
	public boolean isEmpty(){
		return (set==null);
	}
	public boolean isMember(Item o){
		LinkedListNode<Item> list=set;
		while(list!=null){
			if(o==list.data)return true;
			list=list.next;
		}
		return false;
	}
	
	public void insert(Item o){
		if(isMember(o)==false){
			LinkedListNode<Item> newNode=new LinkedListNode(o);
			if(set==null){
				set=newNode;
				return;
			}
			LinkedListNode<Item> list=set;
			while(list.next!=null){
				list=list.next;
			}
			list.next=newNode;
		}
	}
	public void delete(Item o){
		if(isMember(o)==false){
			System.out.println("Object not present in set");
		}
		LinkedListNode<Item> list=set;
		if(list.data==o){
			set=set.next;
			return;
		}
		while(list.next.data!=o){
			list=list.next;
		}
		list.next=list.next.next;
	}
	public Myset union(Myset<Item> a){
		Myset<Item> ret = new Myset(null);
		LinkedListNode<Item> list=a.set;
		while(list!=null){
			ret.insert(list.data);
			list=list.next;
		}
		list=set;
		while(list!=null){
			ret.insert(list.data);
			list=list.next;
		}
		return ret;
	}
	public Myset<Item> intersection(Myset<Item> a){
		Myset<Item> ret = new Myset<Item>(null);
		LinkedListNode<Item> list=set;
		while(list!=null){
			if(a.isMember(list.data))
			ret.insert(list.data);
			list=list.next;
		}
		return ret;
	}
	public void print(){
		LinkedListNode<Item> list=set;
		while(list!=null){
			System.out.printf("%d ",list.data);
			list=list.next;
		}
		System.out.printf("\n");
	}
	public static void main(String[] args){
		Myset<Integer> set1=new Myset(null);
		set1.insert(1);
		set1.insert(2);
		set1.print();
		Myset<Integer> set2=new Myset(null);
		set2.insert(3);
		set2.insert(4);
		(set1=set1.union(set2)).print();
		Myset<Integer> set3=new Myset(null);
		set3.insert(2);
		set3.insert(3);
		set1.intersection(set3).print();
	}
}