package assign4;

import java.io.*;
import java.util.*;


public class HuffmanEncode {

	private BinaryHeap<Integer, HuffmanTree> freq;
	private BufferedReader br;
	char non;
	int total;
	
	public static void main(String args[]) throws IOException {
		//args[0] is the name of the file whose contents should be compressed
		//args[1] is the name of the output file that will hold the compressed
		//content of the input file
		new HuffmanEncode(args[0], args[1]);
		//do not add anything here
	}
	
	public HuffmanEncode(String in, String out) throws IOException {
		//Implements the Huffman encoding algorithm
		//Add private methods and instance variables as needed
		
		br = new BufferedReader(new FileReader(in));
		freq = new BinaryHeap<Integer, HuffmanTree>();
		non = (char) 128;
		
		//call get freq to make array list of frequencys
		getFreq();
		//build a new huffmantree
		HuffmanTree tree = buildTree();
		//call write to write the tree to the file
		write(out, tree, in);
	}
	
	//private method that returns a linkedlist of the characters frequencies
	private void getFreq() throws IOException {
		//make an array list for frequency
		ArrayList<Integer> freqList = new ArrayList<Integer>();
		
		//add 128 spaces into the array, the amount of possible chars
		for(int j = 0; j < 128; j++) {
			freqList.add(0);
		}
		//set next to the next int in the file
		int next = br.read();
		//while next is not at the end of the file
		while(next != -1) {
			//add to the total number of characters used
			total++;
			//set the value in the arraylist to the current value plus 1 to add to its frequncy
			freqList.set(next, freqList.get(next) + 1);
			//advance down file
			next = br.read();
		}
		br.close();
		
		//loop through array list 
		for(int i = 0; i < freqList.size(); i++) {
			//check to see if the count is more then 0, which means its in the file
			if(freqList.get(i) > 0) {
				//create a single node huffmantree with that char
				HuffmanTree h = new HuffmanTree((char) i);
				//put into binary heap
				freq.insert(freqList.get(i), h);
			}
		}
	}
	//a private method that writes the compressed data to the file
	private void write(String out, HuffmanTree tree, String in) throws IOException {
		//make a string array and set it to the trees paths made in pathsToLeavs
		String p[] = tree.pathsToLeaves();
		//new buffered reader
		BufferedReader br = new BufferedReader(new FileReader(in));
		//new hufman output stream
		HuffmanOutputStream hos = new HuffmanOutputStream(out, tree.toString(), total);
		
		//a = the next in in the file
		int a = br.read();
		//while a is not at the end of the file
		while(a != -1) {
			//convert that ascii value to its char
			char c = (char) a;
			//while i < the length of paths[]
			for(int i = 0; i < p.length; i++) {
				//create an array of chars for each char in paths[i]
				char charsArray[] = p[i].toCharArray();
				//if the cuurent char is the first in arr
				if(c == charsArray[0]) {
					for(int j = 1; j < p[i].length(); j++) {
						//write each bit of arr[] to the file
						hos.writeBit(charsArray[j]);
					}
				}
			}
			//advance a to next int
			a = br.read();
		}
		hos.close();
		br.close();
	}
	
	//private method to build a huffman tree
	private HuffmanTree buildTree() {
		//while the binary heap's size is more then 1
		while(freq.size > 1){
			//set a tree to the heaps next value
			HuffmanTree leftTree = freq.getMinOther();
			//get the frequency of that key
			int FreqL = freq.getMinKey();
			//remove that value from heap
			freq.removeMin();
			//set a tree to the heaps next value
			HuffmanTree rightTree = freq.getMinOther();
			//get the frequency of that key
			int FreqR = freq.getMinKey();
			//remove that value from heap
			freq.removeMin();
			//create a tree out of those two trees and put it back into the heap
			freq.insert(FreqR + FreqL, new HuffmanTree(leftTree, rightTree, non)); 
		}
		//return the last value in the heap
		return freq.getMinOther();
	}
	
}

