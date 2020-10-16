public class Queue<T> implements Iterable<T> {

	private java.util.LinkedList<T> list = new java.util.LinkedList<T>();

	public Queue() {}

	public Queue(T firstElem) {
		offer(firstElem);
	}

	public int size() {
    	return list.size();
  	}

  	public boolean isEmpty() {
    	return size() == 0;
  	}

  	// Peek the element at the front of the queue
  	public T peek() {
    	if (isEmpty()) throw new RuntimeException("Queue Empty");
    		return list.peekFirst();
  	}

  	// Poll an element from the front of the queue
  	public T poll() {
    	if (isEmpty()) throw new RuntimeException("Queue Empty");
    		return list.removeFirst();
  	}

  	// Add an element to the back of the queue
  	public void offer(T elem) {
    	list.addLast(elem);
  	}

  	@Override
  	public java.util.Iterator<T> iterator() {
    	return list.iterator();
  	}
}