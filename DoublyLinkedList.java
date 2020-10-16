@SuppressWarnings("unchecked")
public class DoublyLinkedList<T> implements Iterable<T> {
	private int size = 0;
	private Node<T> head = null;
	private Node<T> tail = null;
	/*As with all linked lists, doubly linked and singly linked, we maintain 
		a refence to the head and the tail*/

		//Internal node class to represent data
		private static class Node<T> {
			private T data;
			private Node<T> prev, next;
			
				public Node(T data, Node<T> prev, Node<T> next) {
					this.data = data;
					this.prev = prev;
					this.next = next;
					/*Being a doubly linked list, each node holds a refence to the next 
				and the previous node.*/
				}

				@Override
				public String toString() {
					return data.toString();
				}
		}
		/*The variable trav has been used so often in the subsequent parts of 
			this code. It's been used to create pointers*/

		//Empty this linked list, O(n)
			public void clear() {
				/*TECHNIQUE USED HERE
				1. Point to the head
				2. Create a reference to the next node
				3. Empty the node we are pointing to
				4. Transfer pointer to the next node
				5. Repeat process frm 1-4 till we get to the tail*/
				Node<T> trav = head; // Pointing to the head
				while(trav != null) {
					Node<T> next = trav.next; //Reference to the next node b4 we clear the current one
					trav.prev = trav.next = null;
					trav.data = null; // We now cleared the node we pointing to

					trav = next; //Pointer transfered to the next node. Ahead of the one we just cleared
				}
				head = tail = trav = null; //Done clearing the list. Everything set to null
				size = 0;
			}

			public int size() {
				return size;
			}

			public boolean isEmpty() {
				return size == 0;
			}

			//Add an element to the tail of the linked list, O(1)
			public void add(T elem) {
				addLast(elem);
			}

			//Add a node to the tail of the linked list, O(1)
			public void addLast(T elem) {
				if(isEmpty()) {
					head = tail = new Node<T>(elem, null, null);
				} else {
					tail.next = new Node<T>(elem, tail, null); //The node we creating here will referenced as the next node from what is currently the tail
					tail = tail.next; //Make the node we just created the tail. Makes sense, does not point to any next node
				}
				size++;
			}

			//Add an element to the beginning of this linked list, O(1)
			public void addFirst(T elem) {
				if(isEmpty()) {
					head = tail = new Node<T>(elem, null, null);
				} else {
					head.prev = new Node<T>(elem, null, head); //Make what is currently the head point to a previous node. Points to the node we creating now as previous
					head = head.prev; // Make the node we just created the head. Makes sense as it points to no previous node
				}
				size++;
			}

			// Check the value of the first node if it exists, O(1)
			public T peekFirst() {
			    if (isEmpty()) throw new RuntimeException("Empty list");
			    return head.data;
			}

			 // Check the value of the last node if it exists, O(1)
			public T peekLast() {
			    if (isEmpty()) throw new RuntimeException("Empty list");
			    return tail.data;
			}

			//Remove the first value at the head of the linked list, O(1)
			public T removeFirst() {
				if(isEmpty()) throw new RuntimeException("Empty list");

				/*Extract the data at the head and move the head pointer forward by one node*/
				T data = head.data;
				head = head.next;
				--size;

				//If the list is empty set the tail to null
				if(isEmpty()) tail = null;

				//Do a memory cleanup of the previous node
				else head.prev = null; 

				//Return the data that was at the first node we just removed
				return data;
			}

			//Remove the last value at the tail of the linked list, O(1)
			public T removeLast() {
				if(isEmpty()) throw new RuntimeException("Empty list");

				/*Extract the data at the tail and move the tail pointer backwards by one node*/
				T data = tail.data;
				tail = tail.prev;
				--size;

				//If the list is now empty set the head to null
				if(isEmpty()) head = null;

				//Do a memory cleanup of the node that was just removed
				else tail.next = null;

				//Return the data that was in the last noed we just removed
				return data;
			}

			//Remove an arbitrary node from the linked list, O(1)
			private T remove(Node <T> node) {
				/*Nodes at the tail and head will be handled independently*/
				if(node.prev == null) return removeFirst();
				if(node.next == null) return removeLast();

				// Make the pointers of adjacent nodes skip over 'node'
    			node.next.prev = node.prev;
    			node.prev.next = node.next;

    			// Temporarily store the data we want to return
    			T data = node.data;

    			// Memory cleanup
    			node.data = null;
    			node = node.prev = node.next = null;

    			--size;

    			// Return the data in the node we just removed
    			return data;
			}

			//Remove a node at a particular index, O(n)
			public T removeAt(int index) {
				//Make sure the index provided is valid
				if(index < 0 || index >= size) {
					throw new IllegalArgumentException();
				}

				int i;
    			Node<T> trav;

				// Search from the front of the list
			    if (index < size / 2) {
			      for (i = 0, trav = head; i != index; i++) {
			        trav = trav.next;
			      }
			      // Search from the back of the list
			    } else
			      for (i = size - 1, trav = tail; i != index; i--) {
			        trav = trav.prev;
			      }

			    return remove(trav);
			}

			// Remove a particular value in the linked list, O(n)
			public boolean remove(Object obj) {
			    Node<T> trav = head;

			    // Support searching for null
			    if (obj == null) {
			      for (trav = head; trav != null; trav = trav.next) {
			        if (trav.data == null) {
			          remove(trav);
			          return true;
			        }
			      }
			      // Search for non null object
			    } else {
			      for (trav = head; trav != null; trav = trav.next) {
			        if (obj.equals(trav.data)) {
			          remove(trav);
			          return true;
			        }
			      }
			    }
			    return false;
			  }

			// Find the index of a particular value in the linked list, O(n)
		    public int indexOf(Object obj) {
		    	int index = 0;
		    	Node<T> trav = head;

		    	// Support searching for null
		    	if (obj == null) {
		      		for (; trav != null; trav = trav.next, index++) {
		        		if (trav.data == null) {
		         			 return index;
		       			}
		     		}
		      		// Search for non null object
		    	} else
		      		for (; trav != null; trav = trav.next, index++) {
		        		if (obj.equals(trav.data)) {
		          			return index;
		        		}
		      		}

		    	return -1;
		    }

		    // Check is a value is contained within the linked list
  			public boolean contains(Object obj) {
    			return indexOf(obj) != -1;
  			}

  			@Override
			public java.util.Iterator<T> iterator() {
			    return new java.util.Iterator<T>() {
			      private Node<T> trav = head;

			      @Override
			      public boolean hasNext() {
			        return trav != null;
			      }

			      @Override
			      public T next() {
			        T data = trav.data;
			        trav = trav.next;
			        return data;
			      }

			      @Override
			      public void remove() {
			        throw new UnsupportedOperationException();
			      }
			    };
			}

			@Override
			public String toString() {
			    StringBuilder sb = new StringBuilder();
			    sb.append("[ ");
			    Node<T> trav = head;
			    while (trav != null) {
			      sb.append(trav.data + ", ");
			      trav = trav.next;
			    }
			    sb.append(" ]");
			    return sb.toString();
			}


			public static void main(String [] args) {

				DoublyLinkedList<String> langs = new DoublyLinkedList<>();

				langs.addLast("Java");
				langs.addLast("Kotlin");
				langs.addLast("php");

				System.out.println(langs.toString());
				System.out.println(langs.peekFirst());
				System.out.println(langs.peekLast());
			} 

}