package prefixTree;

import java.util.ArrayList;

/**
 * This class implements a PrefixTree.
 * 
 * @author Sesh Venugopal
 *
 */
public class PrefixTree {

	// prevent instantiation
	private PrefixTree() {
	}
	
	private static ArrayList<PrefixTreeNode> convertFileWordsToPrefixTreeNodes(String[] words) {
		ArrayList<PrefixTreeNode> wordsConvertedToPrefixNodesList = new ArrayList<PrefixTreeNode>();
		
		int indexOfWordInFile = 0;
		for(String word :words) {
			short startOfWord = 0;
			short endOfWord = (short) (word.length()-1);
			Indices indicesOfWordInFile = new Indices(indexOfWordInFile, startOfWord, endOfWord);
			PrefixTreeNode node = new PrefixTreeNode(indicesOfWordInFile,null,null);
			wordsConvertedToPrefixNodesList.add(node);
			indexOfWordInFile++;
			
		}		
		return wordsConvertedToPrefixNodesList;
	}
	
	private static int[] findIndexOfPatterenBetweenTwoWords(String word1, String word2) {
		int[] indexRangeOfPattern = new int[2];
		int index0 = -1;
		int index1 = -1;
		int slowPtr = 0;
		int fastPtr = 0;
		
		for(slowPtr = 0; slowPtr < word1.length(); slowPtr++) {
			if(fastPtr >= word2.length()) {
				break;
			}
			for(  ; fastPtr < word2.length();  ) {
				if(word1.charAt(slowPtr) == word2.charAt(fastPtr)) {					
					if(index0 == -1 && index1 == -1) {
						index0 = slowPtr;
						index1 = slowPtr;
						fastPtr++;
						break;
					}				
						index1 = fastPtr;
						fastPtr++;
						break;
					
				} else {
					fastPtr++;
					break;
				}				
			}
			if(word1.charAt(slowPtr) != word2.charAt(slowPtr)) {
				break;
			}
			
		}
		
		indexRangeOfPattern[0] = index0;
		indexRangeOfPattern[1] = index1;
		
		return indexRangeOfPattern;
	}
	
	private static PrefixTreeNode createNewPrefixTreeNode(int wordIndex, int startIndex, int endIndex) {
		Indices indexes = new Indices(wordIndex, (short) startIndex, (short) endIndex);
		PrefixTreeNode node = new PrefixTreeNode(indexes, null, null);
		return node;
	}	
	
	private static String convertPrefixTreeNodeToString(PrefixTreeNode prefix, String[] allWords) {
		
		int indexOfPrefixNodeInArray = prefix.substr.wordIndex;
		String itemAtPrefixWordIndex = allWords[indexOfPrefixNodeInArray];
		int startIndex = prefix.substr.startIndex;
		int endIndex = prefix.substr.endIndex;
		
		String StringfromtStartToEndIndex = itemAtPrefixWordIndex.substring(startIndex, endIndex+1);
		return StringfromtStartToEndIndex;
	}
	
	private static PrefixTreeNode attach(PrefixTreeNode root, PrefixTreeNode parent, PrefixTreeNode newWord, ArrayList<PrefixTreeNode> wordsAsPrefixNodes, String[] allWords, ArrayList<String> patternsCameAcross) {		
		if(root == null) {
			return null;
		}
		//Convert the root and the new word to a string 
		String rootAsString = convertPrefixTreeNodeToString(root, allWords);
//			System.out.print("Root as String: "+rootAsString);
//			System.out.println();
		String newWordAsString = convertPrefixTreeNodeToString(newWord, allWords);
//			System.out.print("newWord as String: "+newWordAsString);
//			System.out.println();
		//find pattern of root and new word, if [-1,-1] pattern doesn't exist
		int[] numericalPattern = findIndexOfPatterenBetweenTwoWords(rootAsString, newWordAsString);
//			System.out.print("Pattern is at: ("+numericalPattern[0]+","+numericalPattern[1]+")");
//			System.out.println();
			
		boolean thereIsAPattern = numericalPattern[0] != -1 && numericalPattern[1] != -1;
//			System.out.print("thereIsAPattern = "+ thereIsAPattern);
//			System.out.println();
		
		if(thereIsAPattern) {
			String letteredPattern = rootAsString.substring(numericalPattern[0],numericalPattern[1]+1);
//			System.out.println("The pattern is: " + letteredPattern);
			//if the pattern already exists
			if(patternsCameAcross.contains(letteredPattern)) {
//				System.out.println("The pattern '" + letteredPattern + "' already exsists");
				//TODO: traverse to the childm and then the siblings
//				System.out.println("************ 1.) New recursive call *************");
				newWord.substr.startIndex = numericalPattern[1]+1;
				root.firstChild = attach(root.firstChild, root, newWord, wordsAsPrefixNodes, allWords, patternsCameAcross);
				return root;
			}
			//if the pattern doesn't already exist
			else {				
//				System.out.println("Adding '" + letteredPattern + "' to patternsCameAcross");
				patternsCameAcross.add(letteredPattern);
				//add the prefix attatched to the word
				if(parent.substr != null) {
					String combo = allWords[root.substr.wordIndex].substring(0, numericalPattern[1]+root.substr.startIndex+1);
//					System.out.println("Adding '"+ combo+ "' full pattern to patternsCameAcross");
					patternsCameAcross.add(combo);
				}
			}
			
			//if the two words have a pattern, change the root's start and end index to reflect the pattern
			int originalRootEndIndex = root.substr.endIndex;
//			System.out.println("originalRootEndIndex: "+originalRootEndIndex);
			int lastIndexOfNewWord = newWord.substr.endIndex;
//			System.out.println("lastIndexOfNewWord: "+lastIndexOfNewWord);
			//Update start and end indexes of original root, this relies on numbericalPatterns[]

			if(parent.substr == null ) { 
				if(patternsCameAcross.contains(rootAsString)) {
					PrefixTreeNode newFirstChild = createNewPrefixTreeNode(root.substr.wordIndex, numericalPattern[0], numericalPattern[1]);
					newFirstChild.firstChild = root;
					parent.firstChild = newFirstChild;
					root = newFirstChild;
				}
				root.substr.startIndex = numericalPattern[0];
				root.substr.endIndex = numericalPattern[1];
//				System.out.println("Parent: null");
//				System.out.println("root: " + root.toString() + ", " + convertPrefixTreeNodeToString(root,allWords));
//				if(root.firstChild != null) {
//					if(root.firstChild != null) {
//						System.out.println("root leftChild: " + root.firstChild.toString() + ", " + convertPrefixTreeNodeToString(root.firstChild,allWords));
//					}
//					if(root.firstChild.sibling != null) {
//						System.out.println("root rightChild: " + root.firstChild.sibling.toString() + ", " + convertPrefixTreeNodeToString(root.firstChild.sibling,allWords));
//					}
//				}
			} else if(parent.substr != null) {
				root.substr.startIndex = parent.substr.endIndex+1;
				root.substr.endIndex = numericalPattern[1]+root.substr.startIndex;
			}
			if(root.firstChild == null) {				
//				if(parent.substr != null) {
//					System.out.println("Parent: " + parent.toString());
//				} else {
//					System.out.println("Parent: null");
//				}
//				System.out.println("root: " + root.toString() + ", " + convertPrefixTreeNodeToString(root,allWords));
				//take the word that was originally root, make it left child
				root.firstChild = createNewPrefixTreeNode(root.substr.wordIndex, root.substr.endIndex+1, originalRootEndIndex);
//				System.out.println("root leftChild: " + root.firstChild.toString() + ", " + convertPrefixTreeNodeToString(root.firstChild,allWords));
				//Make the rightChild the newWord
				root.firstChild.sibling = createNewPrefixTreeNode(newWord.substr.wordIndex, root.substr.endIndex+1, lastIndexOfNewWord);
//				System.out.println("root rightChild: " + root.firstChild.sibling.toString() + ", " + convertPrefixTreeNodeToString(root.firstChild.sibling,allWords));				
			} else {
				PrefixTreeNode ptr = root.firstChild;
				while(ptr.sibling != null) {
					ptr = ptr.sibling;								
					
				}
				ptr.sibling = createNewPrefixTreeNode(newWord.substr.wordIndex, root.substr.endIndex+1, lastIndexOfNewWord);				
			}
		} 
		//if the sibling is not equal to null, keep going until one is
		else if(root.sibling != null) { 
//			System.out.println("************ 2.) New recursive call *************");
			root.sibling = attach(root.sibling, parent, newWord, wordsAsPrefixNodes, allWords, patternsCameAcross);
		}
		//if the words don't have a pattern
		else { 
			root.sibling = newWord;			
			return root;
		}
		
//		System.out.println("-------------End of Method call-------------");
		return root;

	}
	
	private static PrefixTreeNode replaceSingleLetters(PrefixTreeNode root) {
		
		PrefixTreeNode ptr = root.firstChild;
		if(ptr.substr.endIndex == 0) {
			//get the last sibling of firstChild
			PrefixTreeNode ptr2 = ptr.firstChild;
			PrefixTreeNode lastSibling = null;
			while(ptr2.sibling != null && ptr2 != null) {
				ptr2 = ptr2.sibling;
			}
			lastSibling = ptr2;
			// set the ".next" of this sibling to ptr.firstChild.sibling
			lastSibling.sibling = ptr.sibling;

			//set the roots firstChild to ptr.firstChild
			root.firstChild = ptr.firstChild;
			ptr = root.firstChild;
		}	
		
		
		PrefixTreeNode prev = null;
		while(ptr != null) {
			//get the last sibling of firstChild
			PrefixTreeNode ptr2 = ptr.firstChild;
			PrefixTreeNode lastSibling = null;
			if(ptr2!=null) {				
				while(ptr2.sibling != null) {
					ptr2 = ptr2.sibling;
				}
				lastSibling = ptr2;
			}
			//----
			if(ptr.substr.endIndex == 0) {				
				prev.sibling = ptr.firstChild;
				lastSibling.sibling = ptr.sibling;
				ptr = lastSibling;
//				System.out.println("PREV.SIBLING: " + prev.sibling);
			}			
			prev = ptr;
			ptr = ptr.sibling;
//			System.out.println("Prev: " + prev.toString());
			if(ptr != null) {
//				System.out.println("Ptr: " + ptr.toString());
			}
		}
		
		return root;
	}
	
	
	/**
	 * Builds a PrefixTree(Prefixtionary-tree) by inserting all words in the input array, one at a
	 * time, in sequence FROM FIRST TO LAST. (The sequence is IMPORTANT!) The words
	 * in the input array are all lower case.
	 * 
	 * @param allWords
	 *            Input array of words (lowercase) to be inserted.
	 * @return Root of PrefixTree with all words inserted from the input array
	 */
	public static PrefixTreeNode buildPrefixTree(String[] allWords) {
		/** COMPLETE THIS METHOD **/
		/*----------------------------------
		  Show Words in array:
		 ----------------------------------*/
//		ArrayList<String> fileWords = new ArrayList<String>();
//		for(String word : allWords) {
//			fileWords.add(word);
//		}
//		System.out.println("Words in file/array: " + fileWords.toString());
		
		/*-------------------------------------------------------------
		 Create an arrayList of all words converted to PrefixTreeNodes 
		 -------------------------------------------------------------*/
		ArrayList<PrefixTreeNode> wordsAsPrefixNodes = convertFileWordsToPrefixTreeNodes(allWords);
//		System.out.println("wordsAsPrefixNodes: " + wordsAsPrefixNodes.toString());
//		System.out.println("-------------------------------");

		/*-------------------------------------------------------------
		   					MAIN PREFIX TREE CODE: 		  
		 -------------------------------------------------------------*/
				
		PrefixTreeNode root = new PrefixTreeNode(null,null,null);
		ArrayList<String> patternsCameAcross = new ArrayList<String>();
		
		for(PrefixTreeNode word: wordsAsPrefixNodes) {
			//Set roots first child to the first word
			if(root.firstChild == null) {
				root.firstChild = wordsAsPrefixNodes.get(0);
				continue;
			}

			root.firstChild = attach(root.firstChild, root, word, wordsAsPrefixNodes, allWords, patternsCameAcross);

		}						
//		root = replaceSingleLetters(root);
		return root;
		
	}		
	
	private static void findAllPrefixRec(PrefixTreeNode root, String[] allWords, String prefix, ArrayList<PrefixTreeNode> list) {		
		
		if(root.firstChild == null && allWords[root.substr.wordIndex].startsWith(prefix)) {
//			System.out.println(root.toString());
			if(!prefix.equals("") && !prefix.equals(" ")) {
				list.add(root);
			}
		}
		if(root.firstChild != null) {
			findAllPrefixRec(root.firstChild, allWords, prefix, list);
		}
		if(root.sibling != null) {
			findAllPrefixRec(root.sibling, allWords, prefix, list);
		}
		if(root.firstChild == null && root.sibling == null) {
			return;
		}
		
	}	

	/**
	 * Given a PrefixTree, returns the "completeWordList" for the given prefix, i.e. all the
	 * leaf nodes in the PrefixTree whose words start with this prefix. For instance,
	 * if the PrefixTree had the words "bear", "bull", "stock", and "bell", the
	 * completeWordList for prefix "b" would be the leaf nodes that hold "bear",
	 * "bull", and "bell"; for prefix "be", the completeWordList would be the leaf nodes
	 * that hold "bear" and "bell", and for prefix "bell", completeWordList would be the
	 * leaf node that holds "bell". (The last example shows that an input prefix can
	 * be an entire word.) The order of returned leaf nodes DOES NOT MATTER. So, for
	 * prefix "be", the returned list of leaf nodes can be either hold [bear,bell]
	 * or [bell,bear].
	 *
	 * @param root
	 *            Root of PrefixTree that stores all words to search on for completeWordList
	 * @param allWords
	 *            Array of words that have been inserted into the PrefixTree
	 * @param prefix
	 *            Prefix to be completed with words in PrefixTree
	 * @return List of all leaf nodes in PrefixTree that hold words that start with the
	 *         prefix, order of leaf nodes does not matter. If there is no word in
	 *         the tree that has this prefix, null is returned.
	 */
	public static ArrayList<PrefixTreeNode> completeWordList(PrefixTreeNode root, String[] allWords, String prefix) {		
		ArrayList<PrefixTreeNode> list = new ArrayList<PrefixTreeNode>();
		findAllPrefixRec(root.firstChild, allWords, prefix, list);
		if(list.isEmpty()) {
			return null;
		}
		return list;
	}

	public static void print(PrefixTreeNode root, String[] allWords) {
		System.out.println("\nPrefixTree\n");
		print(root, 1, allWords);
	}

	private static void print(PrefixTreeNode root, int indent, String[] words) {
		if (root == null) {
			return;
		}
		for (int i = 0; i < indent - 1; i++) {
			System.out.print("    ");
		}

		if (root.substr != null) {
			String pre = words[root.substr.wordIndex].substring(0, root.substr.endIndex + 1);
			System.out.println("      " + pre);
		}

		for (int i = 0; i < indent - 1; i++) {
			System.out.print("    ");
		}
		System.out.print(" ---");
		if (root.substr == null) {
			System.out.println("root");
		} else {
			System.out.println(root.substr);
		}

		for (PrefixTreeNode ptr = root.firstChild; ptr != null; ptr = ptr.sibling) {
			for (int i = 0; i < indent - 1; i++) {
				System.out.print("    ");
			}
			System.out.println("     |");
			print(ptr, indent + 1, words);
		}
	}
}
