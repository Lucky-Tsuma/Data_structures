public class IntArray implements Iterable<Integer> {

	private static final int DEFAULT_CAP = 1 <<3;
	/*
	<< left shift operator. As used above, 1 will be shifted 3 steps to the left and
		the resulting space filled with zeros i.e 100. Thats in binary. Which translates to 
		 8 in decimal
	 >> right shift operator works the same way as above. Shifting the operand to the 
	 	right this time*/

 	public int[] arr;
 	public int len = 0;
 	private int capacity = 0;

 	//initialize array with default capacity
 	public IntArray() {
 		this(DEFAULT_CAP);
 	}

 	//initialize the array with a certain capacity
 	public IntArray(int capacity) {
 		if(capacity < 0) throw new IllegalArgumentException("Illegal Capacity: " + capacity);
 		this.capacity = capacity;
 		arr = new int[capacity];
 	}

 	//Given an array make it a dynamic array!
 	public IntArray(int[] array) {
 		if(array == null) throw new IllegalArgumentException("Array cannot be null");
 		arr = java.util.Arrays.copyOf(array, array.length);
 		capacity = len = array.length;
 		/*array - array to be copied
 		  array.length - the lenght we want of the new array. 
 		  The above method will truncate or padd the new array with zeros if neccesary to fit the length we specified*/

 	}

 	//Returns the size of the array
 	public int size() {
 		return len;
 	}

 	//Returns true or false on whether the array is empty
 	public boolean isEmpty() {
 		return len == 0;
 	}

 	//get and set methods
 	public int get(int index) {
    return arr[index];
  	}

  	public void set(int index, int elem) {
    	arr[index] = elem;
  	}

  	//Add an element to this Dynamic Array
  	public void add(int elem) {
  		if(len + 1 >= capacity) {
  			if(capacity == 0) capacity = 1;
  			else capacity *=2; //double the array capacity here

  			arr = java.util.Arrays.copyOf(arr, capacity);// pads with extra null elements
  		}
  		arr[len++] = elem;
  	}

  	//Remove the element at the specified index in the list;
  	//Takes O(n) time as it involves reconstructing the array
  	public void removeAt(int rm_index) {
  		System.arraycopy(arr,rm_index+1, arr,rm_index, len-rm_index-1);
  		/*
  		arr - array to copy frm(source array)
  		rm_index+1 - index to start copying frm the source array
  		arr - array to copy to(destination array
  		rm_index - index to start copying to on the destination array(since we startin at the rm_indexx position, its a clever hack to remove
  					the element at rm index
		len-rm_index-1 - the length of the new array(that calculation results to the length of the array without the removed element*/
		--len;
		--capacity;
  	}

  	//search and remove an element if it is found in the array
  	public boolean remove(int elem) {
  		for(int i = 0; i<len; i++){
  			if(arr[i] == elem) {
  				removeAt(i);
  				return true;
  			}
  		}
  		return false;
  	}

   // Reverse the contents of this array
  public void reverse() {
  	//tmp variable has been used for swapping
    for (int i = 0; i < len / 2; i++) {
      int tmp = arr[i];
      arr[i] = arr[len - i - 1];
      arr[len - i - 1] = tmp;
    }
  }

  // Perform a binary search on this array to find an element in O(log(n)) time
  // The array should however be sorted.(Make sure of that)! Returns a value < 0 if item is not found
  public int binarySearch(int key) {
    int index = java.util.Arrays.binarySearch(arr, 0, len, key);
    return index;
  }

  // Sort this array
  public void sort() {
    java.util.Arrays.sort(arr, 0, len);
  }

  // Iterator is still fast but not as fast as iterative for loop
  @Override
  public java.util.Iterator<Integer> iterator() {
    return new java.util.Iterator<Integer>() {
      int index = 0;

      public boolean hasNext() {
        return index < len;
      }

      public Integer next() {
        return arr[index++];
      }

      public void remove() {
        throw new UnsupportedOperationException();
      }
    };
  }

  @Override
  public String toString() {
    if (len == 0) return "[]";
    else {
      StringBuilder sb = new StringBuilder(len).append("[");
      for (int i = 0; i < len - 1; i++) sb.append(arr[i] + ", ");
      return sb.append(arr[len - 1] + "]").toString();
    }
  }


//The main method 
  // Example usage
  public static void main(String[] args) {

    IntArray ar = new IntArray(50);
    ar.add(3);
    ar.add(7);
    ar.add(6);
    ar.add(-2);

    ar.sort(); // [-2, 3, 6, 7]

    // Prints [-2, 3, 6, 7]
    for (int i = 0; i < ar.size(); i++) System.out.println(ar.get(i));

    // Prints [-2, 3, 6, 7]
    System.out.println(ar);
  }

}