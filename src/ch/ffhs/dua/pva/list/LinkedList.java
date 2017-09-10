package ch.ffhs.dua.pva.list;

import java.util.Iterator;
import java.util.List;

public class LinkedList<E> extends ListBasic<E> implements List<E> {
	private int size = 0;
	private Node<E> anker = new Node<E>();
	
	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		if(anker.next==null){
			return true;
		}
		return false;
	}

	@Override
	public E get(int index) {
		if(size > 0){
			Node<E> n = findIndexElement(index); 
			if(n!=null){
				return n.element;
			}
		}
		return null;
	}

	@Override
	public E set(int index, E element) {
		Node<E> n = findIndexElement(index);
		n.element = element;
		return element;
	}

	@Override
	public boolean add(E element) {
		Node<E> n = new Node<E>();
		n.element = element;
		if(anker.next==null){
			anker.next = n;
		}else{
			n.prev = anker.prev;
			n.prev.next = n;
		}
		anker.prev = n;
		size++;
		return true;
	}

	@Override
	public void add(int index, E element){
		if(index>=size){
			throw new IndexOutOfBoundsException();
		}
		Node<E> n = new Node<E>();
		n.element = element;
		Node<E> insertNode = findIndexElement(index);
		n.prev = insertNode.prev;
		n.next = insertNode;		
		if(n.prev==null){
			anker.next = n;
		}else{
			n.prev.next = n;
		}
		if(n.next == null){
			anker.prev = n;
		}
		insertNode.prev = n;
		size++;
	}

	/**@author Mirko Eberlein
	 * add a function to find the index Element
	 * @return Element mit dem Index welcher ersetzt werden soll. 
	 * */
	private Node<E> findIndexElement(int index){
		if(size > 0){
			if(size/2 > index){
				Node<E> n = anker.next;
				int count = 0;
				while(n!=null){
					if(count==index){
						return n;
					}
					count++;
					n = n.next;
				}
			}else{
				Node<E> n = anker.prev;
				int count = size-1;
				while(n!=null){
					if(count==index){
						return n;
					}
					n=n.prev;
				}
			}
		}
		
		if(anker.next != null){
			Node<E> toFind = anker.next;
			int count = 0;
			while(toFind.next!=null){
				if(count==index){
					return toFind;
				}else{
					count++;
					toFind = toFind.next;
				}
			}
		}
		return null;
	}
	
	public boolean contains(Object o) {
		
		if(anker.next != null){
			Node<E> n = anker.next;
			while(n!=null){
				if(checkType(o,n)){
					return true;
				}
				n = n.next;
			}
		}
		return false;
	}

	/**
	 * @author Mirko Eberlein
	 * @return true if className is object Class Name
	 * */
	private boolean checkType(Object o,Node<E> n) {
		return((n.element == null 
				&& o == null)
				|| 
				(n.element!=null && o!=null && 
				n.element.getClass().getName().equals(o.getClass().getName())));
	}

	@Override
	public E remove(int index) {
		Node<E> toRemove = findIndexElement(index);
		if(toRemove!=null){
			if(toRemove.prev==null){
				anker.next = toRemove.next;
			}else{
				toRemove.prev.next = toRemove.next;
			}
			if(toRemove.next==null){
				anker.prev = toRemove.prev;
			}else{
				toRemove.next.prev = toRemove.prev;
			}
		}		
		size--;
		return toRemove.element;
	}

	@Override
	public boolean remove(Object o) {
		if(anker.next!=null){
			Node<E> n = anker.next;
			while(n!=null){
				if(checkType(o,n)){
					n.prev.next = n.next;
					n.next.prev = n.prev;
					size--;
					return true;
				}
				n = n.next;
			}
		}
		return false;
	}

	@Override
	public void clear() {
		anker.next = null;
		anker.prev = null;
				
	}

	@Override
	public Iterator<E> iterator() {	
		return new LinkedListIterator(this);
	}

	///////////////////////////////////////////////////

	private static class Node<E> {
		private E element;
		private Node<E> next;
		private Node<E> prev;
	}

	private class LinkedListIterator implements Iterator<E> {
		private  Object[] arrayList;
		private int currentSize=0;
		private int currentIndex = 0;
		private int deleteIndex = 1;
		private LinkedList<E> ll;
		public LinkedListIterator(LinkedList<E> ll){
			this.ll = ll;
			this.currentSize = ll.size;
			arrayList = new Object[this.currentSize];
			Node<E> n = ll.anker.next;
			int i = 0;
			while(n!=null){
				arrayList[i]=n.element;
				n = n.next;
				i++;
			}
		}
		@Override
		public boolean hasNext() {
			return currentIndex < currentSize && arrayList[currentIndex] != null;
		}

		@SuppressWarnings("unchecked")
		@Override
		public E next() {
			 return (E) arrayList[currentIndex++];
		}

		@Override
		public void remove() {
			//Der neue index wird automatisch hochgezählt beim holen daher beim löchen eins zurück setzen. 
			//Jedes löschen verringert den index in der Linked List darum zählen wir diesen herunter
			//Wir löschen absichtlich über Index identische Objecte könnten sonst zu verwirrungen sorgen.
			ll.remove(currentIndex-deleteIndex);
			deleteIndex++;
		}
	}

}
