package assign4;

import java.io.*;
import java.util.LinkedList;

public class HuffmanOutputStream {
 //add additional private variables as needed
 //do not modify the public methods signatures or add public methods

	DataOutputStream d;
	LinkedList<Integer> ll;
	
	public HuffmanOutputStream(String filename, String tree, int totalChars) {
		try {
			 	d = new DataOutputStream(new FileOutputStream(filename));
			 	d.writeUTF(tree);
			 	d.writeInt(totalChars);
			 	//create a linkedlist of ints
			 	ll = new LinkedList<Integer>();
		}
		catch (IOException e) {
			System.out.println("error");
		}
	}
	
	public void writeBit(char bit) throws IOException {
		 //PRE:bit == '0' || bit == '1'
		//add the bit passed in to the linkedlist as an int
		ll.add(Character.getNumericValue(bit));
		//if the list has a size of 8, set output to the bits converted to base 10
		if(ll.size() == 8) {
			int output = bitsToBaseTen();
			//write output to the file
			d.writeByte(output);
		}
    }
	//privte method to convert bits to base 10
	private int bitsToBaseTen() {
		int i = 0;
		int j = ll.size() - 1;
		int sum = 0;
		//loop 8 times
		while(i < 8) {
			//bit is the last value in the linkedlist
			int nextBit = ll.removeLast();
			//if that value is a 1, add 2^j power to summ
			if(nextBit == 1) {
				sum = sum + ((int) Math.pow(2,  j));
			}
			i++;
			j--;
		}
		return sum;
	}
	
	public void close() throws IOException {
	//write final byte (if needed)
	//close the DataOutputStream
		//if the linkedlist is not empty
		if(ll.size() != 0) {
			//set c to 8 - the size of list mode 8
			int c = 8 - ((ll.size()) % 8);
			//loop c times
			for(int i = 0; i < c; i++) {
				//write padding to end of file
				writeBit('0');
			}
		}
		d.close();
	}
}
