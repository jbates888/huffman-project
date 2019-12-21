package assign4;

import java.util.*;
public class BinaryHeap <T1 extends Comparable <? super T1>, T2 extends Object> {
//implements a binary heap where the heap rule is the value of the key in the parent node is less than
//or equal to the values of the keys in the child nodes
	//creat array list of inner class type
	ArrayList<G> pq; 
	int size;
	
	public BinaryHeap() {
		pq = new ArrayList<>();
		//to the first element
		size = 0;
		//add a null characte to first index in arrayList
		pq.add(null);
	}
	
	//store the 2 generic types in this private class
	private class G{
		private T1 key;
		private T2 data;
		//set the first value as the key and the second as the data
		public G(T1 t1, T2 t2) {
			key = t1;
			data = t2;
		}
		//return the key value, the frequency
		public T1 getKey(){
			return key;
		}
		//return the data value, the data
		public T2 getData(){
			return data;
		}
		//print the key then data
		public String toString() {
			return key + " " + data;
		}
	}
	
	public void removeMin() {
	//PRE: size != 0
	//removes the item at the root of the heap
		
		int parent;
		int child;
		//create a Pair object off values at arraylist(size)
		G x = pq.get(size);
		size--;
		child = 2;
		//while child is less the the size of pq
		while (child <= size) {
			parent = child/2;
			//if child is less the pq.size and pq[child + 1] is less then pq[child]
			if (child < size && pq.get(child + 1).getKey().compareTo(pq.get(child).getKey()) < 0) {
				child++;
			}
			//if x is less then pq[child] break
			if (x.getKey().compareTo(pq.get(child).getKey()) < 0) {
				break;
			}
			//set value at parent in list to the child 
			pq.set(parent, pq.get(child));
			child = 2*child;
		 }
		//set value at child/2 in list to value at end of arraylist
		pq.set((child/2), pq.get(size+1));
		//remove the lst value in list
		pq.remove(size+1);
	}

	public T1 getMinKey() {
	//PRE: size != 0
	 //return the priority (key) in the root of the heap
		return pq.get(1).getKey();
	}

	 public T2 getMinOther() {
	 //PRE: size != 0
	 //return the other data in the root of the heap
		 return pq.get(1).getData();
	 }
	 
	 public void insert(T1 k, T2 t) {
		 //insert the priority k and the associated data into the heap
		 //if the arrayList is empty
		 if(pq.size() < 1) {
			 //add passed in values to list
			 pq.add(new G(k, t));
			 return;
		 }
		 //set the first element in arraylist to null
		 pq.add(null);
		 size++;
		 int child = size;
		 int parent = child/2;
		 //set the arraylist at first index to passed in values
		 pq.set(0, new G(k, t));
		 //while pq[parent] > k 
		 while (pq.get(parent).getKey().compareTo(k) > 0) {
			 //pq[child] = pq[parent]
			 pq.set(child, pq.get(parent));
			 child = parent;
			 parent = child/2;
		 }
		 //set pq[child] = k
		 pq.set(child, new G(k, t)); //contents[child] = k; 
	}
	
	 public int getSize() {
	 //return the number of values (key, other) pairs in the heap
		 return size;
	 }
	 
	 public void print() {
		 //loop through arraylist and print each value as a string
		 for(int i = 1; i < pq.size(); i++) {
			 System.out.println(pq.get(i).toString());
		 }
	 }
}
