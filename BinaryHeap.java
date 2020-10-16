import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BinaryHeap<T extends Comparable<T>> {

	//The number of elements inside the heap
	private int heapSize = 0;

	//The internal capacity of the heap
	private int heapCapacity = 0;

	//A dynamic list to track the elements inside the heap
	private List<T> heap = null;

	//Construct and initiallize an empty priority queue
	public BinaryHeap() {
		this(1);
	}

	//Construct a priority queue with an initial capacity
	public BinaryHeap(int sz) {
		heap = new ArrayList<> (sz);
	}

	/*Construct a priority queue using heapify in O(n) time, heapify is the process of
	 converting a binary tree into a heap data structure. A heap has all rows 
	 complete, save say the last in some cases. Also satisfies the heap invariant*/
	public BinaryHeap(T[] elems) {
	 	heapSize = heapCapacity = elems.length;
	 	heap = new ArrayList<T>(heapCapacity);

	 	//Place all elements in heap
	 	for(int i = 0; i < heapSize; i++) heap.add(elems[i]);

 		//Heapify process, O(n)
 		for(int i = Math.max(0, (heapSize / 2) - 1); i >= 0; i--) sink(i);
 			/*Math.max(x, y) returns the maximum of the two arguments
 			(heapSize / 2) - 1 ensures that only nodes with children undergo sink()*/
	 } 

	// Priority queue construction, O(nlog(n))
	public BinaryHeap(Collection<T> elems) {
		this(elems.size());
		for (T elem : elems) add(elem);
  	}

  	//Returns true/false depending on if the priority queue is empty
  	public boolean isEmpty() {
  		return heapSize == 0;
  	}

  	// Clears everything inside the heap, O(n)
  	public void clear() {
    	for (int i = 0; i < heapCapacity; i++) heap.set(i, null);
    	heapSize = 0;
  	}

  	// Return the size of the heap
  	public int size() {
    	return heapSize;
  	} 

  	/*Returns the value of the element with the highest priority in this PQ.
  	If the PQ is empty, null is returned*/
  	public T peek() {
  		if(isEmpty()) return null;
  		return heap.get(0);
  	}

  	/*Removes the root of the heap, O(log(n)) since you have to swap with the one at
  	the insetion point before actually removing the element that was at the root.
  	One may also have to bubble down the new root so as to satisfy the heap invariant*/
  	public T poll() {
  		return removeAt(0);
  	}

  	//Test if an element is in the heap, O(n)
  	public boolean contains(T elem) {
  		//Linear scan to check containment
  		for(int i = 0; i < heapSize; i++) if(heap.get(i).equals(elem)) return true;
		return false;
  	}

  	/*Adds an eleemnt to the priority queue, the element must not be null,
  	O(log(n))*/
  	public void add(T elem) {
  		if(elem == null) throw new IllegalArgumentException();

  		if(heapSize < heapCapacity) {
  			heap.set(heapSize, elem);
  		} else {
  			heap.add(elem);
  			heapCapacity++;
  		}

  		swim(heapSize);
  		heapSize++;
  	}

  	/*Tests if the value of node i <= node j
  	This method assumes i & j are valid indices, O(1)*/
  	private boolean less(int i, int j) {
  		T node1 = heap.get(i);
  		T node2 = heap.get(j);
  		return node1.compareTo(node2) <= 0;
  		/*The compareTo method returns:
  		0 if the integer equals the argument
  		1 if the integer is greater than the argument
  		-1 if the integer is less than the argument*/
  	}

  	//Perforn a bottom up swim, O(log(n))
  	private void swim(int k) {

  		//WRT - With Respect To
  		//Grab the index of the next parent node WRT to k
  		int parent = (k - 1) / 2;
  		/*Keep swimming while we have not reached the root and while we're less
  		than our parent*/
  		while(k > 0 && less(k, parent)) {
  			//Exchange k with the parent
  			swap(parent, k);
  			k = parent;

  			//Greb the index of the next parent node WRT to k
  			parent = (k - 1) / 2;
  		}
  	}

  	//Top down sink, O(log(n))
  	private void sink(int k) {
  		while(true) {
  			int left = 2 * k + 1; //Left node
  			int right = 2 * k + 2; //Right node
  			int smallest = left; //Assume left is the smallest of the two children

  			/*Find which is smaller, left or right and if right is smaller, set
  			smallest to be right*/
  			if(right < heapSize && less(right, left)) smallest = right;

  			/*Stop if we are outside the bounds of the tree of stop early if we 
  			cannot sink k anymore*/
  			if(left >= heapSize || less(k, smallest)) break;

  			//Move down the tree follosing the smallest node
  			swap(smallest, k);
  			k = smallest; 
  		}
  	}

  	//Swap two nodes. Assumes i & j are valid, O(1)
  	private void swap(int i, int j) {
  		T elem_i = heap.get(i);
  		T elem_j = heap.get(j);

  		heap.set(i, elem_j);
  		heap.set(j, elem_i);
  	}

  	//Removes a particular element in the heap, O(n)
  	public boolean remove(T element) {
  		if(element == null) return false;
  		//Linear removal via search, O(n)
  		for(int i = 0; i < heapSize; i++){
  			if(element.equals(heap.get(i))) {
  				removeAt(i);
  				return true;
  			}
  		}
  		return false;
  	}

  	//Romoves a node at a particular index, O(log(n))
  	private T removeAt(int i) {
  		if(isEmpty()) return null;

  		heapSize--;
  		T removed_data = heap.get(i);
  		swap(i, heapSize);

  		//Obliterate the value we removing
  		heap.set(heapSize, null);

  		//Check if it is the last element that was removed
  		if(i == heapSize) return removed_data;

  		T elem = heap.get(i);

  		//Try sinking the element
  		sink(i);

  		//If sinking never worked try swimming instead
  		if(heap.get(i).equals(elem))swim(i);

  		return removed_data;
  	}

  	/*Recursively checks if this heap is a min heap. Just a testing method to make 
  	sure the heap invariant is still being maintained. Call the method with k = 0
  	to start at the root*/
  	public boolean isMinHeap(int k) {
  		//If we are outside the bounds of the heap return true
  		if(k >= heapSize) return true;

  		int left = 2 * k + 1;
  		int right = 2 * k + 2;

  		/*Make sure that the current node k is less than both of its children if
  		they exist. Otherwise return false to indicate an invalid heap*/
  		if(left < heapSize && !less(k, left)) return false;
  		if(right < heapSize && !less(k, right)) return false;

  		//Recurse on both children to make sure they are also valid heaps
  		return isMinHeap(left) && isMinHeap(right);
  	}

  	@Override
  	public String toString() {
  		return heap.toString();
  	}

}
