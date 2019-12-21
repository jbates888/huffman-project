package assign4;

import java.io.*;
import java.util.LinkedList;
public class HuffmanInputStream {
//add additional private variables and methods as needed
//DO NOT modify the public method signatures or add public methods
	private String tree;
	private int totalChars;
	private DataInputStream d;
	private LinkedList<Integer> ll;
	
	public HuffmanInputStream(String filename) {
		try {
				d = new DataInputStream(new FileInputStream(filename));
				tree = d.readUTF();
				totalChars = d.readInt();
				//create a linkedList of ints
				ll = new LinkedList<Integer>();
		}
	    catch (IOException e) {
	    	System.out.println("error");
	    }
	 }
	
	public int readBit() throws IOException {
	//returns the next bit is the file the value returned will be either a 0 or a 1
		//the the list is empty, set val to the nect byte in file
		if(ll.size() == 0) {
			int val = d.readUnsignedByte();
			//pass that int to covnert it to binary
			getBinary(val);
		}
		return ll.remove();
	}
	//private method that take in an int of base 10 and converts it to binary
	private void getBinary(int val) {
		int i = 0;
		//loop 8 times
		while(i < 8) {
			//set int to val mod 2
			int binary = val % 2;
			//add that int to the list
			ll.add(binary);
			//divide val by two
			val = val / 2;
			i++;
		}
	}
	
	public String getTree() {
	//return the tree representation read from the file
		return tree;
		
	}
	
	public int getTotalChars() {
	//return the character count read from the file
		return totalChars;
	}
}