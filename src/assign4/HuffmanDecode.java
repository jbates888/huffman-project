package assign4;

import java.io.*;

public class HuffmanDecode {
	char non;
	int numChar;
	int count;
	
	public static void main(String args[]) throws IOException {
		//args[0] is the name of a input file (a file created by Huffman Encode)
		//args[1] is the name of the output file for the uncompressed file
		new HuffmanDecode(args[0], args[1]);
		//new HuffmanDecode("comp.bin", "test.txt");
		//do not add anything here
	}
	
	public HuffmanDecode(String in, String out) throws IOException {
		//implements the Huffman Decode Algorithm Add private methods and instance variables as needed
		//create a huffman input stream
		HuffmanInputStream his = new HuffmanInputStream(in);
		//set the nonChar value to 128
		non = (char) 128;
		//create a new tree with the post order string of the file in
		HuffmanTree tree = new HuffmanTree(his.getTree(), non);
		//set the number of characters
		numChar = his.getTotalChars();
		//create a new printwriter
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(new File(out))));
		//set count to 0
		count = 0;
		//while count is less then the number of chars in tree
		while(count < numChar) { 
			//s is the next bit in the file
			int s = his.readBit();
			//if the bit is 1, move right
			if(s == 1) {
				tree.moveToRight();
				//if at a leaf, print char to file
				if(tree.atLeaf()) {
					char c = tree.current();
					pw.write(Character.toString(c));
					tree.moveToRoot();
					count++;;
				}
			} 
			//if not 1 the bit must be 0 so move left
			else {
				tree.moveToLeft();
				//if at a leaf, print char to file
				if(tree.atLeaf()) {
					char c = tree.current();
					pw.write(Character.toString(c));
					tree.moveToRoot();
					count++;
				}
			}
			
		}
		pw.flush();
		pw.close();
	}
	
	
}
