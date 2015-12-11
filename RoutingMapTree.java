import java.util.*;
import java.lang.*;

public class RoutingMapTree{
	Exchange root;
	RoutingMapTree(){
		root=new Exchange(0);
	}
	RoutingMapTree(Exchange node){
		root=node;
	}
	public boolean containsNode(Exchange rootNode,Exchange node){
		if (rootNode==node)return true;
		Iterator<Exchange> it=rootNode.children.iterator();
		while(it.hasNext()){
			if(containsNode(it.next(),node))return true;
		}
		return false;
	}
	public boolean containsNode(Exchange node){
		return containsNode(root,node);
	}
	public Exchange containsNodeID(Exchange rootNode,int id){
		if (rootNode.exchangeID==id)return rootNode;
		Iterator<Exchange> it=rootNode.children.iterator();
		while(it.hasNext()){
			Exchange temp=containsNodeID(it.next(),id);
			if(temp!=null)return temp;
		}
		return null;
	}
	public Exchange containsNodeID(int id){
		return containsNodeID(root,id);
	}
	public void switchOn(MobilePhone a,Exchange b){
		if(!a.status()){
			a.switchOn();
			a.station=b;
			while(b!=null){
				b.residents.add(a);
				b=b.parent();
			}
		}
	}
	public void switchOff(MobilePhone a){
		if(a.status()){
			Exchange b=a.location();
			a.switchOff();
			while(b!=null){
				b.residents.remove(a);
				b=b.parent();
			}
		}
	}
	
	public Exchange findPhone(MobilePhone m) throws IllegalStateException{
		return m.location();
	}
	
	public Exchange lowestRouter(Exchange a,Exchange b){
		while(a.depth!=b.depth){
			if(a.depth<b.depth)
				b=b.Parent;
			else 
				a=a.Parent;
		}
		while(a!=b){
			a=a.Parent;
			b=b.Parent;
		}
		return a;
	}
	
	public ExchangeList routeCall(MobilePhone a,MobilePhone b){
		ExchangeList ans=new ExchangeList(null);
		Exchange baseStation1=a.location();
		Exchange baseStation2=b.location();
		Exchange lowestAncestor=lowestRouter(baseStation1,baseStation2);
		Vector<Exchange> routeUp=new Vector<Exchange>();
		while(baseStation1!=lowestAncestor){
			routeUp.add(baseStation1);
			baseStation1=baseStation1.Parent;
		}
		Vector<Exchange> routeDown=new Vector<Exchange>();
		while(baseStation2!=lowestAncestor){
			routeDown.add(baseStation2);
			baseStation2=baseStation2.Parent;
		}
		for(int i=0;i<routeDown.size();i++)
			ans.add(routeDown.get(i));
		ans.add(lowestAncestor);
		for(int i=routeUp.size()-1;i>=0;i--)
			ans.add(routeUp.get(i));
		return ans;
	}
	
	public void movePhone(MobilePhone a,Exchange b){
		if(a.status()){
			switchOff(a);
			switchOn(a,b);
		}
	}
	
	public boolean stringEqual(String a,String b){
		if(a.length()!=b.length())return false;
		for(int i=0;i<a.length();i++)
			if(a.charAt(i)!=b.charAt(i))return false;
		return true;
	}
	public void performAction(String actionMessage) throws IllegalStateException {
		String key="";
		int i=0;
		while(actionMessage.charAt(i)!=' '){
			key=key+actionMessage.substring(i,i+1);
			i++;
		}
		i++;
		String query1="addExchange";
		String query2="switchOnMobile";
		String query3="switchOffMobile";
		String query4="queryNthChild";
		String query5="queryMobilePhoneSet";
		String query6="queryFindPhone";
		String query7="lowestRouter";
		String query8="queryFindCallPath";
		String query9="movePhone";
		try{
		if(stringEqual(key,query1)){
			int a=0,b=0;
			while(actionMessage.charAt(i)!=' '){
			a*=10;
			a+=Character.getNumericValue(actionMessage.charAt(i));
			i++;
			}
			i++;
			while(i<actionMessage.length()){
			b*=10;
			b+=Character.getNumericValue(actionMessage.charAt(i));
			i++;
			}
			Exchange newNode=new Exchange(b);
			Exchange nodeA=containsNodeID(a);
			if(nodeA!=null){
				nodeA.children.add(newNode);
				newNode.Parent=nodeA;
				nodeA.isBaseExchange=false;
				newNode.depth=nodeA.depth+1;
			}
			else throw new IllegalStateException(actionMessage+": Error- There is no exchange with identifier "+String.valueOf(a));
		}
		else if(stringEqual(key,query2)){
			int a=0,b=0;
			while(actionMessage.charAt(i)!=' '){
			a*=10;
			a+=Character.getNumericValue(actionMessage.charAt(i));
			i++;
			}
			i++;
			while(i<actionMessage.length()){
			b*=10;
			b+=Character.getNumericValue(actionMessage.charAt(i));
			i++;
			}
			Exchange nodeB=containsNodeID(b);
			if(nodeB==null)throw new IllegalStateException(actionMessage+": Error- There is no exchange with identifier "+String.valueOf(b));
			if(nodeB.isBaseExchange==false) throw new IllegalStateException(actionMessage+": Error- The exchange with identifier "+String.valueOf(b)+" is not a baseExchange");
			else{
				MobilePhone phone=root.find(a);
				if(phone==null)
					phone=new MobilePhone(a);
				switchOn(phone,nodeB);
			}
		}
		else if(stringEqual(key,query3)){
			int a=0;
			while(i<actionMessage.length()){
			a*=10;
			a+=Character.getNumericValue(actionMessage.charAt(i));
			i++;
			}
			MobilePhone phone=root.find(a);
			if(phone==null)throw new IllegalStateException(actionMessage+": Error- There is no mobile with identifier "+String.valueOf(a));
			else
			switchOff(phone);
		}
		else if(stringEqual(key,query4)){
			int a=0,b=0;
			while(actionMessage.charAt(i)!=' '){
			a*=10;
			a+=Character.getNumericValue(actionMessage.charAt(i));
			i++;
			}
			i++;
			while(i<actionMessage.length()){
			b*=10;
			b+=Character.getNumericValue(actionMessage.charAt(i));
			i++;
			}
			Exchange nodeA=containsNodeID(a);
			if(nodeA!=null){
				Exchange node=nodeA.child(b);
				System.out.println("queryNthChild "+a+" "+b+": "+node.exchangeID);
			}
			else throw new IllegalStateException(actionMessage+": Error- There is no exchange with identifier "+String.valueOf(a));
		}
		else if(stringEqual(key,query5)){
			int a=0;
			while(i<actionMessage.length()){
			a*=10;
			a+=Character.getNumericValue(actionMessage.charAt(i));
			i++;
			}
			Exchange nodeA=containsNodeID(a);
			if(nodeA!=null){
				System.out.printf("queryMobilePhoneSet "+a+": ");
				MobilePhoneSet temp=nodeA.residents;
				Myset<MobilePhone> newSet=temp.Phone_Set;
				LinkedListNode<MobilePhone> list=newSet.set;
				while(list.next!=null){
					System.out.printf("%d, ",list.data.id);
					list=list.next;
				}
				System.out.printf("%d ",list.data.id);
				System.out.printf("\n");
			}
			else throw new IllegalStateException(actionMessage+": Error- There is no exchange with identifier "+String.valueOf(a));
		}
		else if(stringEqual(key,query6)){
			int a=0;
			while(i<actionMessage.length()){
			a*=10;
			a+=Character.getNumericValue(actionMessage.charAt(i));
			i++;
			}
			MobilePhone phone=root.find(a);
			if(phone==null)throw new IllegalStateException(actionMessage+": Error - No mobile phone with identifier "+String.valueOf(a)+" found in the network");
			else{
				Exchange e=findPhone(phone);
				System.out.println("queryFindPhone "+a+": "+e.exchangeID);
			}
		}
		else if(stringEqual(key,query7)){
			int a=0,b=0;
			while(actionMessage.charAt(i)!=' '){
			a*=10;
			a+=Character.getNumericValue(actionMessage.charAt(i));
			i++;
			}
			i++;
			while(i<actionMessage.length()){
			b*=10;
			b+=Character.getNumericValue(actionMessage.charAt(i));
			i++;
			}
			Exchange nodeA=containsNodeID(a);
			Exchange nodeB=containsNodeID(b);
			if(nodeA==null) throw new IllegalStateException("queryLowestRouter "+String.valueOf(a)+" "+String.valueOf(b)+": Error- There is no exchange with identifier "+String.valueOf(a));
			if(nodeB==null) throw new IllegalStateException("queryLowestRouter "+String.valueOf(a)+" "+String.valueOf(b)+": Error- There is no exchange with identifier "+String.valueOf(b));
			else{
				Exchange lowestAncestor=lowestRouter(nodeA,nodeB);
				System.out.println("queryLowestRouter "+a+" "+b+": "+lowestAncestor.exchangeID);
			}
		}
		else if(stringEqual(key,query8)){
			int a=0,b=0;
			while(actionMessage.charAt(i)!=' '){
			a*=10;
			a+=Character.getNumericValue(actionMessage.charAt(i));
			i++;
			}
			i++;
			while(i<actionMessage.length()){
			b*=10;
			b+=Character.getNumericValue(actionMessage.charAt(i));
			i++;
			}
			MobilePhone phoneA=root.find(a);
			MobilePhone phoneB=root.find(b);
			if(phoneA==null)throw new IllegalStateException(actionMessage+": Error- There is no available mobile phone with identifier "+String.valueOf(a));
			if(phoneB==null)throw new IllegalStateException(actionMessage+": Error- There is no available mobile phone with identifier "+String.valueOf(b));
			else{
				ExchangeList callRoute=routeCall(phoneA,phoneB);
				LinkedListNode<Exchange> list=callRoute.head;
				System.out.printf("queryFindCallPath %d %d: ",a,b);
				while(list.next!=null){
					System.out.printf("%d, ",list.data.exchangeID);
					list=list.next;
				}
				System.out.printf("%d\n",list.data.exchangeID);
			}
		}
		else if(stringEqual(key,query9)){
			int a=0,b=0;
			while(actionMessage.charAt(i)!=' '){
			a*=10;
			a+=Character.getNumericValue(actionMessage.charAt(i));
			i++;
			}
			i++;
			while(i<actionMessage.length()){
			b*=10;
			b+=Character.getNumericValue(actionMessage.charAt(i));
			i++;
			}
			MobilePhone phoneA=root.find(a);
			Exchange nodeB=containsNodeID(b);
			if(phoneA==null)throw new IllegalStateException("query"+actionMessage+": Error- There is no mobile phone with identifier "+String.valueOf(a));
			if(nodeB==null) throw new IllegalStateException("query"+actionMessage+": Error- There is no exchange with identifier "+String.valueOf(b));
			movePhone(phoneA,nodeB);
		}
		else throw new IllegalStateException(actionMessage+": Error- Unknown query");
		}
		catch (IllegalStateException e){
			System.out.println(e.getMessage());
		}
	}
}