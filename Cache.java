//import java.util.Iterator;
//import java.util.ListIterator;
//import java.util.NoSuchElementException;
//import java.util.ConcurrentModificationException;
//
///**
// *  Cache
// *
// * @author Luke Grice
// *
// * @param <T> - class of objects stored in the list
// */
//public class Cache<T> {
//	private Sequence<T> head, tail;
//	private int size, maxsize, modCount, references, hits;
//
//	//constructor
//	public Cache(int input_size) {
//		head = tail = null;
//		size = 0;
//		maxsize = input_size;
//		modCount = 0;
//		references = 0;
//		hits = 0;
//	}
//
//    /**
//     * Adds the specified element to the front of this list.
//     *
//     * @param element the element to be added to the front of this list
//     */
//    private void addToFront(T element) {
//    	Sequence<T> newNode = new Sequence<T>(element);
//    	newNode.setNext(head);
//    	if(isEmpty())
//    		tail = newNode;
//    	else
//    		head.setPrev(newNode);
//
//    	head = newNode;
//    	size++;
//    	modCount++;
//    }
//
//    /**
//     * Adds the specified element to the rear of this list.
//     *
//     * @param element the element to be added to the rear of this list
//     */
//    private void addToRear(T element) {
//    	Sequence<T> newNode = new Sequence<T>(element);
//    	newNode.setPrev(tail);
//    	if(isEmpty())
//    		head = newNode;
//    	else
//    		tail.setNext(newNode);
//
//    	tail = newNode;
//    	size++;
//    	modCount++;
//    }
//
//    /**
//     * Adds the specified element to the rear of this list.
//     *
//     * @param element  the element to be added to the rear of the list
//     */
//    public void addObject(T element) {
//    	addToFront(element);
//
//		if (size > maxsize) {
//			removeLast();
//		}
//    }
//
//    /**
//     * Adds the specified element after the specified target.
//     *
//     * @param element the element to be added after the target
//     * @param target  the target is the item that the element will be added after
//     * @throws NoSuchElementException if target element is not in this list
//     */
//    private void addAfter(T element, T target) {
//    	if (isEmpty())
//    		throw new NoSuchElementException();
//
//    	Sequence<T> newNode = new Sequence<T>(element);
//    	Sequence<T> cur = head;
//
//    	while (!cur.getElement().equals(target) && cur.getNext() != null)
//    		cur = cur.getNext();
//
//    	if (!cur.getElement().equals(target))
//    		throw new NoSuchElementException();
//
//    	else {
//    		newNode.setNext(cur.getNext());
//    		newNode.setPrev(cur);
//    		cur.setNext(newNode);
//    		size++;
//    		modCount++;
//    	}
//
//    	if (newNode.getNext() == null)
//    		tail = newNode;
//
//    }
//
//    /**
//     * Inserts the specified element at the specified index.
//     *
//     * @param index   the index into the array to which the element is to be inserted.
//     * @param element the element to be inserted into the array
//     * @throws IndexOutOfBoundsException if the index is out of range (index < 0 || index > size)
//     */
//    private void add(int index, T element) {
//    	ListIterator<T> lit = listIterator(index);
//
//    	lit.add(element);
//    }
//
//    /**
//     * Removes and returns the first element from this list.
//     *
//     * @return the first element from this list
//     * @throws NoSuchElementException if list contains no elements
//     */
//    private T removeFirst() {
//    	if (isEmpty())
//    		throw new NoSuchElementException();
//
//    	T temp = head.getElement();
//    	head = head.getNext();
//    	if (head == null)
//    		tail = null;
//    	else
//    		head.setPrev(null);
//
//    	size--;
//    	modCount++;
//
//    	return temp;
//    }
//
//    /**
//     * Removes and returns the last element from this list.
//     *
//     * @return the last element from this list
//     * @throws NoSuchElementException if list contains no elements
//     */
//    private T removeLast() {
//    	if (isEmpty())
//    		throw new NoSuchElementException();
//
//    	T temp = tail.getElement();
//    	tail = tail.getPrev();
//    	if (tail == null)
//    		head = null;
//    	else
//    		tail.setNext(null);
//
//    	size--;
//    	modCount++;
//
//    	return temp;
//    }
//
//    /**
//     * Removes and returns the specified element from this list.
//     *
//     * @param element the element to be removed from the list
//     * @return removed element
//     * @throws NoSuchElementException if element is not in this list
//     */
//    public int getObject(T element) {
//     	Sequence<T> cur = head;
//		references++;
//		int index = 0;
//
//    	while (cur != null && !cur.getElement().equals(element)){
//    		cur = cur.getNext();
//			index++;
//		}
//
//    	if (cur == null)
//    		throw new NoSuchElementException();
//    	if (cur == head){
//    		hits++;
//			return 0;
//		}
//    	else if (cur == tail){
//    		removeLast();
//		}
//    	else {
//    	cur.getNext().setPrev(cur.getPrev());
//    	cur.getPrev().setNext(cur.getNext());
//    	size--;
//    	modCount++;
//    	}
//
//		hits++;
//		addObject(element);
//		return index;
//    }
//
//	/**
//     * Removes and returns the specified element from this list.
//     *
//     * @param element the element to be removed from the list
//     * @return removed element
//     * @throws NoSuchElementException if element is not in this list
//     */
//    public T removeObject(T element) {
//     	Sequence<T> cur = head;
//
//    	while (cur != null && !cur.getElement().equals(element)){
//    		cur = cur.getNext();
//		}
//
//    	if (cur == null)
//    		throw new NoSuchElementException();
//    	if (cur == head){
//			removeFirst();
//			return element;
//		}
//    	else if (cur == tail){
//    		removeLast();
//		}
//    	else {
//    	cur.getNext().setPrev(cur.getPrev());
//    	cur.getPrev().setNext(cur.getNext());
//    	size--;
//    	modCount++;
//    	}
//
//		return element;
//    }
//
//    /**
//     * Removes  and returns the element at the specified index.
//     *
//     * @param index the index of the element to be retrieved
//     * @return the element at the given index
//     * @throws IndexOutOfBoundsException if the index is out of range (index < 0 || index >= size)
//     */
//    public T remove(int index) {
//    	if (index == size)
//    		throw new IndexOutOfBoundsException();
//
//    	ListIterator<T> lit = listIterator(index);
//    	T temp = lit.next();
//    	lit.remove();
//    	return temp;
//    }
//
//    /**
//     * Sets the element at the specified index.
//     *
//     * @param index   the index into the array to which the element is to be set
//     * @param element the element to be set into the list
//     * @throws IndexOutOfBoundsException if the index is out of range (index < 0 || index >= size)
//     */
//    private void set(int index, T element) {
//    	if (isEmpty() || index == size || index < 0)
//    		throw new IndexOutOfBoundsException();
//
//    	ListIterator<T> lit = listIterator(index);
//		lit.next();
//    	lit.set(element);
//    }
//
//    /**
//     * Returns a reference to the element at the specified index.
//     *
//     * @param index  the index to which the reference is to be retrieved from
//     * @return the element at the specified index
//     * @throws IndexOutOfBoundsException if the index is out of range (index < 0 || index >= size)
//     */
//    public T get(int index) {
//    	if (index == size)
//    		throw new IndexOutOfBoundsException();
//
//    	ListIterator<T> lit = listIterator(index);
//    	return lit.next();
//    }
//
//    /**
//     * Returns the index of the specified element.
//     *
//     * @param element  the element for the index is to be retrieved
//     * @return the integer index for this element or -1 if element is not in the list
//     */
//    private int indexOf(T target) {
//    	ListIterator<T> lit = listIterator();
//    	boolean FoundIt = false;
//
//    	while (lit.hasNext() && !FoundIt)
//    		FoundIt = lit.next().equals(target);
//
//    	if (FoundIt)
//    		return lit.nextIndex()-1;
//    	else
//    		return -1;
//    }
//
//    /**
//     * Returns a reference to the first element in this list.
//     *
//     * @return a reference to the first element in this list
//     * @throws NoSuchElementException if list contains no elements
//     */
//    public T first() {
//    	if (isEmpty())
//    		throw new NoSuchElementException();
//
//    	return head.getElement();
//    }
//
//    /**
//     * Returns a reference to the last element in this list.
//     *
//     * @return a reference to the last element in this list
//     * @throws NoSuchElementException if list contains no elements
//     */
//    private T last() {
//    	if (isEmpty())
//    		throw new NoSuchElementException();
//    return tail.getElement();
//    }
//
//    /**
//     * Returns true if this list contains the specified target element.
//     *
//     * @param target the target that is being sought in the list
//     * @return true if the list contains this element, else false
//     */
//    public boolean contains(T target) {
//    	return (indexOf(target) != -1);
//    }
//
//    /**
//     * Returns true if this list contains no elements.
//     *
//     * @return true if this list contains no elements
//     */
//    public boolean isEmpty() {
//    return (size == 0);
//    }
//
//    /**
//     * Returns the number of elements in this list.
//     *
//     * @return the integer representation of number of elements in this list
//     */
//    public int size() {
//    return size;
//    }
//
//    /**
//     * Returns a string representation of this list.
//     *
//     * @return a string representation of this list
//     */
//    public String toString() {
//    	Iterator<T> iter = iterator();
//    	StringBuilder str = new StringBuilder();
//
//    	str.append("[");
//    	while ( iter.hasNext()) {
//    		str.append(iter.next());
//    		str.append(", ");
//    	}
//
//    	if (size > 0)
//    		str.delete(str.length()-2, str.length());
//
//    	str.append("]");
//
//    	return str.toString();
//    }
//
//	/**
//	 * clears the cache by resetting the cache to the beginning state.
//	 * Like calling the constructor again.
//	 */
//	public void clearCache(){
//		head = tail = null;
//		size = 0;
//		modCount = 0;
//		references = 0;
//		hits = 0;
//	}
//
//	/**
//	 * returns the value of references
//	 * @return references
//	 */
//	public int getReferences(){
//		return references;
//	}
//
//	/**
//	 * returns the number of hits
//	 * @return hits
//	 */
//	public int getHits(){
//		return hits;
//	}
//
//	/**
//	 * increments hits
//	 */
//	public void incHits(){
//		hits++;
//	}
//
//	/**
//	 * increments references
//	 */
//	public void incReferences(){
//		references++;
//	}
//
//	/**
//	 * @return maxsize - the maximum length of the 'cache'
//	 */
//	public int maxsize(){
//		return maxsize;
//	}
//
//    /**
//     * Returns an Iterator for the elements in this list.
//     *
//     * @return an Iterator over the elements in this list
//     */
//    public Iterator<T> iterator() {
//    	return listIterator();
//    }
//
//    /**
//     * Returns a ListIterator for the elements in this list.
//     *
//     * @return a ListIterator over the elements in this list
//     *
//     * @throws UnsupportedOperationException if not implemented
//     */
//    public ListIterator<T> listIterator() {
//    	return new DLLIterator();
//
//    }
//
//    /**
//     * Returns a ListIterator for the elements in this list, with
//     * the iterator positioned before the specified index.
//     *
//     * @return a ListIterator over the elements in this list
//     *
//     * @throws UnsupportedOperationException if not implemented
//     */
//    public ListIterator<T> listIterator(int startingIndex) {
//    	return new DLLIterator(startingIndex);
//    }
//
//
//    /**
//     * Inner Class for the Iterator
//     *
//     * @author Luke Grice
//     */
//    private class DLLIterator implements ListIterator<T> {
//    	private Sequence<T> nextNode;
//    	private Sequence<T> lastRetNode;
//    	private int INIT_MODCOUNT;
//    	private int nextIndex;
//
//    	//constructors
//    	public DLLIterator() {
//    		nextNode = head;
//    		lastRetNode = null;
//    		INIT_MODCOUNT = modCount;
//    		nextIndex = 0;
//    	}
//
//    	public DLLIterator(int startingIndex) {
//    		if (startingIndex < 0 || startingIndex > size)
//    			throw new IndexOutOfBoundsException();
//
//    		lastRetNode = null;
//    		nextNode = head;
//    		INIT_MODCOUNT = modCount;
//    		nextIndex = startingIndex;
//
//    		for (int i = 0; i < startingIndex; i++){
//    			nextNode = nextNode.getNext();
//    		}
//    	}
//
//    	/**
//    	 * Returns a boolean value: true if there is another element available
//    	 * in front of the iterators current position.
//    	 *
//    	 * @return true if there is a nextNode, or false if the nextNode
//    	 * reference is false
//    	 *
//    	 * @throws ConcurrentModificationException if something other than this
//    	 * iterator makes a change to the list
//    	 */
//    	public boolean hasNext() {
//    		if (INIT_MODCOUNT != modCount)
//    			throw new ConcurrentModificationException();
//    		else
//    		return (nextNode != null);
//    	}
//
//    	/**
//    	 * Returns the element stored in the next node in front of the iterator's
//    	 * current position
//    	 *
//    	 * @return temp -- the element stored
//    	 *
//    	 * @throws NoSuchElementException if called when there is no next element
//    	 */
//    	public T next() {
//    		if (!hasNext())
//    			throw new NoSuchElementException();
//
//    		T temp =  nextNode.getElement();
//    		lastRetNode = nextNode;
//    		nextNode = nextNode.getNext();
//    		nextIndex++;
//    		return temp;
//    	}
//
//    	/**
//    	 * Returns a boolean value: true if there is another element available
//    	 * behind the iterators current position.
//    	 *
//    	 * @return true if there is a prevNode, or false if the prevNode
//    	 * reference is false
//    	 *
//    	 * @throws ConcurrentModificationException if something other than this
//    	 * iterator makes a change to the list
//    	 */
//    	public boolean hasPrevious() {
//    		if (INIT_MODCOUNT != modCount)
//    			throw new ConcurrentModificationException();
//
//    		return (nextNode!= head);
//    	}
//
//    	/**
//    	 * Returns the element behind the iterators current position and moves
//    	 * it backwards
//    	 *
//    	 * @return temp -- the element stored in prevNode
//    	 *
//    	 * @throws NoSuchElementException if there is no prevNode
//    	 */
//    	public T previous() {
//    		if (!hasPrevious())
//    			throw new NoSuchElementException();
//
//    		if (nextNode == null)
//    			nextNode = tail;
//    		else
//    			nextNode = nextNode.getPrev();
//
//    		nextIndex--;
//    		lastRetNode = nextNode;
//    		return nextNode.getElement();
//    	}
//
//    	/**
//    	 * Adds an element in the iterator's current position (in front
//    	 * of prevNode and before nextNode)
//    	 *
//    	 * @throws ConcurrentModificationException if something other than this
//    	 * iterator makes a change to the list
//    	 */
//    	public void add(T element) {
//    		if (INIT_MODCOUNT != modCount)
//    			throw new ConcurrentModificationException();
//
//      		//if at tail
//    		if (nextNode == null) {
//    			addToRear(element);
//    		}
//
//    		//if at head
//    		else if (nextNode.getPrev() == null) {
//    			addToFront(element);
//    		}
//
//    		else {
//    			Sequence<T> newNode = new Sequence<T>(element);
//    			newNode.setNext(nextNode);
//   				newNode.setPrev(nextNode.getPrev());
//   				nextNode.getPrev().setNext(newNode);
//   				nextNode.setPrev(newNode);
//   				size++;
//   				modCount++;
//    		}
//
//    		INIT_MODCOUNT++;
//    		lastRetNode = null;
//    	}
//
//    	/**
//    	 * Removes the last returned element whether the last move was a
//    	 * call to previous or to next.
//    	 *
//    	 * @throws ConcurrentModificationException if something other than this
//    	 * iterator makes a change to the list
//    	 *
//    	 * @throws IllegalStateException if there is no last returned node
//    	 */
//    	public void remove() {
//    		if (INIT_MODCOUNT != modCount)
//    			throw new ConcurrentModificationException();
//
//    		if (lastRetNode == null)
//    			throw new IllegalStateException();
//
//    		if (lastRetNode != nextNode)  //last move was next
//    			nextIndex--;
//    		else	//last move prev
//    			nextNode = nextNode.getNext();
//
//    		if (lastRetNode == head)
//    			head = head.getNext();
//    		else
//    			lastRetNode.getPrev().setNext(lastRetNode.getNext());
//
//    		if (lastRetNode == tail)
//    			tail = tail.getPrev();
//    		else
//    			lastRetNode.getNext().setPrev(lastRetNode.getPrev());
//
//    		size--;
//    		modCount++;
//    		INIT_MODCOUNT++;
//    		lastRetNode = null;
//    	}
//
//    	/**
//    	 * Replaces the element in the last returned node whether the last move
//    	 * was previous or next
//    	 *
//    	 * @throws ConcurrentModificationException if something other than this
//    	 * iterator makes a change to the list
//    	 *
//    	 * @throws IllegalStateException if there was no last returned node
//    	 */
//    	public void set(T element) {
//    		if (INIT_MODCOUNT != modCount)
//    			throw new ConcurrentModificationException();
//
//    		if(lastRetNode == null)
//    			throw new IllegalStateException();
//
//    		lastRetNode.setElement(element);
//    		INIT_MODCOUNT++;
//    		modCount++;
//
//    	}
//
//    	/**
//    	 * Returns an integer representing the location of the element behind
//    	 * the iterator's current position
//    	 *
//    	 * @return nextIndex - 1
//    	 *
//    	 * @throws ConcurrentModificationException if something other than this
//    	 * iterator makes a change to the list
//    	 */
//    	public int previousIndex() {
//    		if (INIT_MODCOUNT != modCount)
//    			throw new ConcurrentModificationException();
//
//    		return nextIndex-1;
//    	}
//
//    	/**
//    	 * Returns an integer representing the location of the element in front
//    	 * of the iterator's current position
//    	 *
//    	 * @return nextIndex
//    	 *
//    	 * @throws ConcurrentModificationException if something other than this
//    	 * iterator makes a change to the list
//    	 */
//    	public int nextIndex() {
//    		if (INIT_MODCOUNT != modCount)
//    			throw new ConcurrentModificationException();
//
//    		return nextIndex;
//    	}
//    }	//inner class end
//}	//class end
