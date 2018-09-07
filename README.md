# Prefix-Tree-Assignment

<h2>Programming Assignment 3</h2>
<h2>PrefixTree</h2>
<h3>
In this assignment you will implement a tree structure called PrefixTree.
</h3>
<h3>Worth 100 points (10% of course grade)</h3> 
<h3>Posted Mon, July 2</h3>
<h3>Due Tue, July 17, 01:15 AM (WARNING!! NO GRACE PERIOD)</h3>
<h3><font color="red">There is NO extended deadline!!</font>
</h3></center>
<hr>

<ul>
<li>You will work <b>individually</b> on this assignment. Read the
<a href="http://www.cs.rutgers.edu/academic-integrity/programming-assignments">
DCS Academic Integrity Policy for Programming Assignments</a> - you are responsible
for this. In particular, note that <b>"All Violations of the Academic
Integrity Policy will be reported by the instructor to the appropriate Dean".</b>


</li><li><h3>IMPORTANT - READ THE FOLLOWING CAREFULLY!!!</h3>

<p><font color="red">Assignments emailed to the instructor or TAs will
be ignored--they will NOT be accepted for grading. <br>
We will only grade submissions in Sakai.</font><br>

</p><p><font color="red">If your program does not compile, you will not get any credit.</font> 

</p><p>Most compilation errors occur for two reasons: 
</p><ol>
<li> You 
are programming outside Eclipse, and you delete the "package" statement at the top of the file. 
If you do this, you are changing the program structure, and it will not compile when we
test it.
</li><li> You make some last minute 
changes, and submit without compiling. 
</li></ol>

<h3>To avoid these issues, (a) START EARLY, and
give yourself plenty of time to work through the assignment, and (b) Submit a version well
before the deadline so there is at least something in Sakai for us to grade. And you can
keep submitting later versions - we will 
accept the LATEST version.</h3>
</li></ul>

<p></p><hr>

<ul>
<li><a href="#sum">Summary</a>
</li><li><a href="#tree">PrefixTree Structure</a>
</li><li><a href="#datastruct">Data Structure</a>
</li><li><a href="#impl">Implementation</a>
</li><li><a href="#presearch">Prefix Search</a>
</li><li><a href="#test">Testing</a>
</li><li><a href="#submit">Submission</a>
</li><li><a href="#grading">Grading</a>
</li></ul>

<p></p><hr><p>

<a name="sum"></a></p><h3>Summary</h3>

<p>You will write an application to build a tree structure called PrefixTree
for a dictionary of English words, and use the PrefixTree to 
generate completeWordList(list of words starting with the given prefix) for string searches.

</p><hr>

<a name="tree"></a><p></p><h3>PrefixTree Structure</h3>

<p>A PrefixTree is a general tree, in that each node can have <em>any</em> number of 
children. It is used to store a dictionary (list) of words that can be searched
on, in a manner that allows for efficient generation of completeWordList.

</p><p>The word list is originally stored in an array, and the PrefixTree is built off of
this array. Here are some word lists, the corresponding PrefixTrees, followed by 
an explanation of the structure and its correspondence to the word list.

</p><p>
<table>
<tbody><tr><th>PrefixTree 1</th><th>PrefixTree 2</th><th>PrefixTree 3</th></tr>
<tr>
<td><img src="./tree_description_files/tree1.png" width="170"><p>Root node is always empty. Child [0,0,3] of root
stores "data"
in a triplet 0 (for index of word in list), 0 (for position of first character, 'd' in "data") and
3 (for position of last character, 'a')</p></td>
<td><img src="./tree_description_files/tree2.png" width="200"><p>Child (0,0,0) of root stores common prefix "d" of its
children "data" (left child) and "door" (right child), in triplet 0 (index of first word "data" 
in list), 
0 (starting position of prefix "d"), and 0 (ending position of prefix "d").
Internal nodes represent prefixes, leaf nodes represent complete words. The left leaf
node stores triplet 0 (first word in list), 1 (first index past the common prefix "d",
and 3 (last index in word). The right leaf node is stored similarly.
</p></td><td class="rmost"><img src="./tree_description_files/tree3.png" width="200"><p>Like in PrefixTree 2, child of root stores
common prefix "d", but this time left child is "door", and right child is "data", because
"door" appears before "data" in the array.</p></td>
</tr>
</tbody></table>

</p><p>&nbsp;</p><p>
<table>
<tbody><tr><th>PrefixTree 4</th><th>PrefixTree 5</th><th>PrefixTree 6</th></tr>
<tr>
<td><img src="./tree_description_files/tree4.png" width="200"><p><font color="red">A node stores the longest common
prefix among its children</font>. Since "do" is the longest common prefix of all the words
in the list, it is stored in the child of the root node as the triplet (0,0,1). The left
branch points to a subtree that stores "door" and "doom" since they share a common
prefix "doo", while the right branch terminates in the leaf node for "dorm" stored
as the triplet 1 (index of word "dorm"), 2 (starting position of substring "rm" following 
prefix "do"), and 3 (ending position of substring "rm")</p></td>
<td><img src="./tree_description_files/tree5.png" width="200"><p>There is no common prefix in "door" and
"poor", so the root has 2 children, one for each word. (Common suffixes are irrelevant)</p></td>
<td class="rmost"><img src="./tree_description_files/tree6.png" width="300"><p>There is no common prefix among all the
words. But "door" and "doom" have a common prefix "doo", while "pore" and "port" have
a common prefix "por".</p></td>
</tr>
</tbody></table>

</p><p>&nbsp;</p><p>
<table>
<tbody><tr><th>PrefixTree 7</th></tr>
<tr><td class="rmost"><img src="./tree_description_files/tree7_words.png" width="600"><p>
<img src="./tree_description_files/tree7.png" width="600"></p></td>
</tr>
</tbody></table>

</p><p>&nbsp;</p><p>
</p><h3>Special Notes</h3>
<ul>
<li>Every leaf node represents a complete word, and every complete word
is represented by some leaf node. (In other words, internal
nodes do not represent complete words, only proper prefixes.)
<p>
</p></li><li>No node, except for the root, can have a single child. In other words, every
internal node has at least 2 children. Why? It's because an internal node
is a <em>common prefix</em> of several words. Consider these trees, in each of which
an internal node has a single child (incorrect), and the equivalent correct tree:

<p>&nbsp;</p><p>
<table>
<tbody><tr>
<th>One-word PrefixTree<br>Incorrect/Correct</th>
<th>Two-word PrefixTree<br>Incorrect/Correct</th>
</tr>
<tr>
<td><img src="./tree_description_files/treetry1.png" width="250"><p>A single leaf node only</p></td>
<td class="rmost"><img src="./tree_description_files/treetry2.png" width="350"><p>&nbsp;<br>The longest common
prefix of the two words is "bar", so there is one internal<br>node for this,
with two branches for the respective trailing substrings</p></td>
</tr>
</tbody></table>

</p><p></p></li><li>
<font color="red">A PrefixTree does NOT accept two words where one entire word is a prefix of the other,
such as "free" and "freedom". </font> <br>
(You will not come across this situation in any of the test cases for your 
implementation.)

<p>The process to build the tree (described in the <b>Building a PrefixTree</b> section below),
will create a single child
of the root for the longest common prefix "free", and this node will have a single
child, a leaf node for the word "freedom". But this is an incorrect tree because
it will (a) violate the constraint that
no node aside from the root can have a single child, and (b) violate the
requirement that every complete word be a leaf node (the complete word "free" is
not a leaf node). 

</p><p>&nbsp;</p><p><img src="./tree_description_files/preword.png" width="300">

</p><p>&nbsp;</p><p>On the other hand, a tree with two leaf node children off the root node,
one for the word "free" and the other for the word "freedom" will be incorrect
<font color="red">because the longest common prefix MUST be a separate node.</font> 
(This is the basis of completion choices when the user starts typing a word.)
</p></li></ul>

<hr>

<a name="datastruct"></a><p></p><h3>Data Structure</h3>

Since the nodes in a PrefixTree have varying numbers of children, the structure
is built using linked lists in which each node has three fields: 
<ul>
<li><b>substring</b> (which is a triplet of indexes)
</li><li><b>first child</b>, and
</li><li><b>sibling</b>, which is a pointer to the next sibling. 
</li></ul>

<p>Here's a PrefixTree and the corresponding data structure:

</p><p>&nbsp;</p><p>
<table>
<tbody><tr><th>PrefixTree</th><th>Data Structure</th></tr>
<tr>
<td class="rmost"><img src="./tree_description_files/tree4.png" width="250"></td>
<td class="rmost"><img src="./tree_description_files/tree4ds.png" width="300"></td>
</tr>
</tbody></table>

</p><hr>

<a name="insertword"></a><p></p><h3>Building a PrefixTree</h3>

<p>A PrefixTree is built for a given list of words that is stored in array. 
The word list is input to the PrefixTree building algorithm. The PrefixTree starts
out empty, inserting one word at a time.<br>

</p><h4>Example 1</h4>
The following sequence shows the building of the above PrefixTree, one word at a time,
with the complete data structure shown after each word is inserted.

<p><table>
<tbody><tr>
<th>Input and Initial<br>Empty Tree</th>
<th>After Inserting "door"</th>
<th>After Inserting "dorm"</th>
<th>After Inserting "doom"</th>
</tr>
<tr>
<td><img src="./tree_description_files/inword0.png" width="160"><br>
<p>An empty PrefixTree has a single root node with nulls for all the fields.
</p></td>
<td><img src="./tree_description_files/inword1.png" width="180"><br>
<p>When "door" is inserted, a leaf node is created and made the first child of
the root node. The substring triplet is (0,0,3), since "door" is at index 0
of the word list array, and the substring is the entire string, from the first
position 0 to the last position 3.
</p></td>
<td><img src="./tree_description_files/inword2.png" width="250"><br>
<p>When "dorm" is inserted, its prefix "do" is found to match with
prefix "do" in the existing word "door". So the third value in
the triplet for the existing node is changed from 3 to 1, corresponding
to the prefix "do". (The word index--first value in triplet--is left unchanged.)
And two new nodes are made at the next level
for the two trailing substrings, "or" of "door" and "rm" of
"dorm" - <font color="red">The array indexes of these words are in ascending order, i.e.
"door" MUST come before "dorm" in the node sequence.</font>
</p></td>

<td class="rmost"><img src="./tree_description_files/tree4ds.png" width="260">
<p>When "doom" is inserted, its prefix "do" is found to match with the entire substring
stored at the child of the root. Descending further, the subsequent "o" is found to match
with the prefix "o" of the substring "or" at the (0,2,3) node. This results in a 
modification of the (0,2,3) triplet to (0,2,2), and the creation of a new
level for the trailing substrings "r" and "m" of "door" and "doom", respectively,
in that order - <font color="red">"door" (word index 0 in array)
MUST precede "doom" (word index 3).</font>

</p></td>
</tr>
</tbody></table>

</p><h4>Example 2</h4>
This shows the sequence of inserts in building PrefixTree 7 shown earlier.

<p>
<table>
<tbody><tr>
<td class="rmost" colspan="4"><img src="./tree_description_files/tree7_words.png" width="700"><p>&nbsp;</p><p></p></td>
</tr>
<tr>
<th>Empty</th>
<th>After inserting "cat"</th>
<th>After inserting "muscle"</th>
<th>After inserting "pottery"</th>
<th>After inserting "possible"</th>
</tr>
<tr>
<td class="rmost"><img src="./tree_description_files/empty_tree.png" width="35"></td>
<td class="rmost"><img src="./tree_description_files/tree7_0.png" width="60"></td>
<td class="rmost"><img src="./tree_description_files/tree7_1.png" width="140"></td>
<td class="rmost"><img src="./tree_description_files/tree7_2.png" width="220"></td>
<td class="rmost"><img src="./tree_description_files/tree7_3.png" width="250"></td>
</tr>
</tbody></table>

</p><p>
<table>
<tbody><tr>
<th>After inserting "possum"</th>
<th>After inserting "musk"</th>
</tr>
<tr>
<td class="rmost"><img src="./tree_description_files/tree7_4.png" width="300"></td>
<td class="rmost"><img src="./tree_description_files/tree7_5.png" width="370"></td>
</tr>
</tbody></table>

</p><p>
<table>
<tbody><tr>
<th>After inserting "potato"</th>
<th>After inserting "muse"</th>
</tr>
<tr>
<td class="rmost"><img src="./tree_description_files/tree7_6.png" width="420"></td>
<td class="rmost"><img src="./tree_description_files/tree7.png" width="470"></td>
</tr>
</tbody></table>

</p><hr>

<a name="presearch"></a><p></p><h3>Prefix Search</h3>

<p>Once the PrefixTree is set up for a list of words, 
    you can obtain completeWordList(list of words starting with the given prefix) efficienty.</p>

<p>For instance, in the PrefixTree of Example 2 above (cat, muscle, ...),
suppose you wanted to find all words that started with "po" (prefix). The
search would start at the root, and touch the nodes 
<tt>[0,0,2],(1,0,2),(2,0,1),(2,2,2),(3,2,3),<font color="red">[2,3,6],[6,3,5],[3,4,7],[4,4,5]
</font></tt>. The nodes marked in red are the ones that hold words that
begin with the given prefix.

</p><p><font color="red">Note that NOT ALL nodes in the tree are examined</font>. 
In particular, after
examining (1,0,2), the entire subtree rooted at that node is skipped.
This makes the search efficient. (Searching all nodes in the tree would
obviously be very inefficient, you might as well have searched the word
array in that case, why bother building a PrefixTree!)



</p><hr>

<a name="impl"></a><p></p><h3>Implementation</h3>

<p>Download the attached <tt>PrefixTree_project.zip</tt> file to your
computer. DO NOT unzip it. Instead, follow the instructions on the Eclipse page 
under the section "Importing a Zipped Project into Eclipse" to get the entire
project into your Eclipse workspace.

</p><p>You will see a project called <tt>PrefixTree</tt> with 
the following classes in the <tt>PrefixTree</tt> package: <tt>PrefixTreeNode</tt>,
<tt>PrefixTree</tt>, and <tt>PrefixTreeApp</tt>.

</p><p>There are also a number of sample test files of words directly under the project
folder (see the <b>Testing</b> section that follows.)

</p><p>You will implement the following methods
in the <tt>PrefixTree</tt> class:

</p><p>
</p><ul>
<li><b>(65 pts)</b> <tt>buildPrefixTree</tt>: Starting with an empty PrefixTree,
builds it up by inserting words from an input array, one word at a time.
The words in the input array are all lower case, and comprise of
letters ONLY. 
<p></p></li><li><b>(35 pts)</b> <tt>completeWordList</tt>: For a given search
prefix, scans the PrefixTree efficiently, gathers and returns 
an <tt>ArrayList</tt> of references to all <font color="red">leaf</font> 
<tt>PrefixTreeNode</tt>s
that hold words that begin with the search prefix. 

<p>Note: these are references
to leaf nodes that exist in the PrefixTree, so you should not create any new nodes.
Also, the order in which the leaf nodes appear in the returned list is irrelevant.

</p><p><b>You may NOT search the words array directly, since that would defeat the
purpose of building the PrefixTree, which allows for more efficient prefix search. See
the Prefix Search section above.</b>
<br>For instance, in the PrefixTree of Example 2 described in the Prefix Section, 
for prefix "po" your implementation should return a list of references to 
these PrefixTree nodes:
<font color="red">[2,3,6],[6,3,5],[3,4,7],[4,4,5]</font> (in any order).
</p></li></ul>

<p><font color="red">Make sure to read the comments in the code
that precede classes,
fields, and methods for code-specific details that do not appear here.
Also, note that the methods are all <tt>static</tt>, and the <tt>PrefixTree</tt> has a
single private constructor, which means NO <tt>PrefixTree</tt> instances are to be created -
all manipulations are directly done via <tt>PrefixTreeNode</tt> instances.
</font>

</p><p>Observe the following rules while working on <b>PrefixTree.java</b>:
</p><ul>
<li>You may NOT add any <tt>import</tt> statements to the file.
</li><li>You may NOT add any new classes (you will only be submitting <tt>PrefixTree.java</tt>).
</li><li>You may NOT add any fields to the <tt>PrefixTree</tt> class.
</li><li>You may NOT modify the headers of any of the given methods.
</li><li>You may NOT delete any methods.
</li><li>You MAY add helper methods if needed, as long as you make them <tt>private</tt>.
</li></ul>
Also, you may NOT make any changes to the <tt>PrefixTreeNode</tt> class 
(you will only be submitting <tt>PrefixTree.java</tt>). When we test
your submission, we will use the exact same version of <tt>PrefixTreeNode</tt> that
we shipped to you.

<hr>
<a name="test"></a><p></p><h3>Testing</h3>

<p>You can test your program using the supplied <tt>PrefixTreeApp</tt> driver. It first
asks for the name of an input file of words, with which it builds a PrefixTree by
calling the <tt>PrefixTree.buildPrefixTree</tt> method. After the PrefixTree is built,
it asks for search prefixes for which it computes complete word lists, calling
the <tt>PrefixTree.completeWordList</tt> method.

</p><p>Several sample word files are given with the project, directly under the
project folder. (<tt>words0.txt</tt>, <tt>words1.txt</tt>, <tt>words2.txt</tt>,
<tt>words3.txt</tt>, <tt>words4.txt</tt>).
The first line of a word file is the number
of the words, and the subsequent lines are the words, one per line.

</p><p>There's a convenient <tt>print</tt> method implemented in
the <tt>PrefixTree</tt> class that is used by <tt>PrefixTreeApp</tt> to output a
tree for verification and debugging ONLY. Our testing script will NOT
look at this output - see the <b>Grading</b> section below.

</p><p>When we test your program:
</p><ul>
<li>Words will ONLY have letters in the alphabet.
</li><li>All words will be input in lower case. 
</li><li>We will NOT input duplicate words.
</li><li>We will NOT input two words such that one is a prefix of the other, as in "free" 
and "freedom", i.e. a complete word will not be a prefix of another word.
</li></ul>

<p>Here are a couple of examples of running <tt>PrefixTreeApp</tt>:

</p><p>The first run is for <a href="https://www.cs.rutgers.edu/courses/112/classes/fall_2017_venugopal/progs/prog3/words3.txt">words3.txt</a>:

</p><p>
</p><div class="code">
<pre>
Enter words file name =&gt; words3

PrefixTree

 ---root
     |
          doo
     ---(0,0,2)
         |
              door
         ---(0,3,3)
         |
              doom
         ---(3,3,3)
     |
          por
     ---(1,0,2)
         |
              pore
         ---(1,3,3)
         |
              port
         ---(2,3,3)

completeWordList for (enter prefix, or 'quit'): do
door,doom

completeWordList for: quit

</pre>
</div>

<p>The second run is for <a href="https://www.cs.rutgers.edu/courses/112/classes/fall_2017_venugopal/progs/prog3/words4.txt">words4.txt</a>:

</p><div class="code">
<pre>
Enter words file name =&gt; words4

PrefixTree

 ---root
     |
          cat
     ---(0,0,2)
     |
          mus
     ---(1,0,2)
         |
              muscle
         ---(1,3,5)
         |
              musk
         ---(5,3,3)
     |
          po
     ---(2,0,1)
         |
              pot
         ---(2,2,2)
             |
                  pottery
             ---(2,3,6)
             |
                  potato
             ---(6,3,5)
         |
              poss
         ---(3,2,3)
             |
                  possible
             ---(3,4,7)
             |
                  possum
             ---(4,4,5)

completeWordList for (enter prefix, or 'quit'): pos
possible,possum

completeWordList for: mu
muscle,musk

completeWordList for: pot
pottery,potato

completeWordList for: quit

</pre>
</div>

<p>Try these tests with your implementation - your PrefixTree printout
MUST look IDENTICAL to the above. If your tree looks different, either your
program logic is incorrect, or there is something different in the sequence
of word inserts. In either case, you will not get any credit, so make sure
you fix your code. And, as stated before, we do NOT look at the output, that's 
just for verification. See the <b>Grading</b> section below for details.

</p><p>Also try the other sample word files. AND, try with word files of your
own, formatted exactly like the sample word files - first line is number
of words, then one word per subsequent line.

</p><p>Go over the <tt>PrefixTreeApp</tt> code to understand how the <tt>PrefixTree</tt>
methods are called, and how the returned array list from <tt>completeWordList</tt>
is processed to actually print the list of words starting with the given prefix. 

</p><hr>
<a name="submit"></a><p></p><h3>Submission</h3>

Submit your <b>PrefixTree.java</b> file ONLY.

<hr>
<a name="grading"></a><p></p><h3>Grading</h3>

<p>The <tt>buildPrefixTree</tt> method will be graded by 
<em>comparing the tree structure resulting from your
implementation, with the correct tree structure produced by our implementation.</em>
<font color="red">We will NOT be looking at the printout of the tree,</font> 
the <tt>print</tt> method in the <tt>PrefixTree</tt> class (used by <tt>PrefixTreeApp</tt> as
in the above test examples) is for your convenience only.

</p><p>The <tt>completeWordList</tt> method will be graded by inputting prefix strings to 
some of the trees created in <tt>buildPrefixTree</tt>. However, these <em>trees will be
created by our correct implementation of</em> <tt>buildPrefixTree</tt>. In other words, to
test your <tt>completeWordList</tt> implementation, we will NOT use your 
<tt>buildPrefixTree</tt>
implementation at all. This is fully for your benefit, because 
if your <tt>buildPrefixTree</tt> implementation is incorrect, it will 
not adversely affect the credit you get for your <tt>completeWordList</tt> 
implementation. We will also make sure that the nodes you return belong
to the PrefixTree, and not some independent nodes you created outside of
the PrefixTree.
</p>

</body></html>
