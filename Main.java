import java.io.*;
import java.util.*;

public class Main {	
	public static void main(String[] args) {
		try {
			File theFile = new File(args[0]); // The input file is taken as the first argument.
			Scanner theReader = new Scanner (theFile); // It will be read by the scanner.
			FileWriter bstWriter = new FileWriter(args[1] + "_BST.txt"); // Then, writer is taken as the second argument.
			FileWriter avlWriter = new FileWriter(args[1] + "_AVL.txt"); // Then, writer is taken as the third argument.
			
			
			// Trees and roots are created and set to null. Below, they will be assigned their values.
			
			IPBinarySearchTree bstTree = null;
			IPAVLTree avlTree = null;
			IPDevice bstRoot = null;
			IPDevice avlRoot = null;
			
			
			while (theReader.hasNextLine()) {
				String data = theReader.nextLine(); // This reveals that the input will be taken line by line.
				String[] splittedData = data.split("\\s+"); // Since the letters in the beginning and numbers between blanks
				// has its specific meaning, the data is splitted and each element between blanks are assigned to its variable.
				
				// If the length of the splittedData is exactly one, this means that the value is the root and the roots of avlTree and bstTree
				// are created according to the value. Then trees are created and the root of avlTree is set.
				
				if (splittedData.length == 1) {
					avlRoot = new IPDevice(null, null, splittedData[0], null);
					bstRoot = new IPDevice(null, null, splittedData[0], null);
					bstTree = new IPBinarySearchTree();
					avlTree = new IPAVLTree();
					avlTree.assignRoot(avlRoot);
				}
				
				// If the length of splittedData is two, there are two possible operations. If there is an addition or deletion, the value is used 
				// to create nodes for BST and AVL; then, the methods are called and their log messages are printed. 
				
				if (splittedData.length == 2) {
					if (splittedData[0].equals("ADDNODE")) {
						IPDevice bstchild = new IPDevice(null, null, splittedData[1], null);
						IPDevice avlchild = new IPDevice(null, null, splittedData[1], null);
						bstWriter.write(bstTree.addIPDevice(bstRoot, bstchild, ""));
						avlWriter.write(avlTree.addIPDevice(avlRoot, avlchild, ""));						
					}
					if (splittedData[0].equals("DELETE")){
						IPDevice bstremovingDevice = new IPDevice (null, null, splittedData[1], null);
						IPDevice avlremovingDevice = new IPDevice (null, null, splittedData[1], null);
						bstWriter.write(bstTree.deleteIPDevice(bstRoot, bstremovingDevice, ""));
						if (splittedData[1].equals(avlRoot.getIPAdress()))
							continue; // This if statement prevents the root deletion, which gives an error.
						avlWriter.write(avlTree.deleteIPDevice(avlRoot, avlremovingDevice, ""));				
					}
				}
				
				// If the length is three, this means that there will be a send operation. First, sender and receiver nodes are created for both AVL
				// and BST, then sending logs are printed. After that, the methods are called for transmission. At the end, receiving logs are printed.
				
				if (splittedData.length == 3) {
					if (splittedData[0].equals("SEND")) {
						IPDevice bstsender = new IPDevice (null, null, splittedData[1], null);
						IPDevice bstreceiver = new IPDevice (null, null, splittedData[2], null);
						IPDevice avlsender = new IPDevice (null, null, splittedData[1], null);
						IPDevice avlreceiver = new IPDevice (null, null, splittedData[2], null);
						bstWriter.write(splittedData[1] + ": Sending message to: " + splittedData[2] + "\n");
						avlWriter.write(splittedData[1] + ": Sending message to: " + splittedData[2] + "\n");
						bstWriter.write(bstTree.sendMessage(bstRoot, bstsender, bstreceiver, ""));
						avlWriter.write(avlTree.sendMessage(avlRoot, avlsender, avlreceiver, ""));
						bstWriter.write(splittedData[2] + ": Received message from: " + splittedData[1] + "\n");
						avlWriter.write(splittedData[2] + ": Received message from: " + splittedData[1] + "\n");
					}
				}
				
				if (avlTree.getRoot().getIPAdress().equals(avlRoot.getIPAdress()) == false)
					avlRoot = avlRoot.getParentDevice(); // This changes the avlRoot if root in avlTree changes.
				

			}
			
			// At the end, the reader and writers are closed.
			
			theReader.close();
			bstWriter.close();
			avlWriter.close();
		}
		catch (FileNotFoundException e){
			e.printStackTrace();  // This is called if there is a problem in reading. 
		}
		catch (IOException e) {
			e.printStackTrace(); // This is called if there is a problem in writing. 
		}
	}
}
