package ch.ffhs.dua.pva.list;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Test;

public class ListTest 
{

	@Test
	public void basicTest() 
	{
		LinkedList<String> ll = new LinkedList<>();
		assertSame(0, ll.size());
		ll.add("B");
		ll.add("C");
		ll.add("E");
		assertEquals("B", ll.get(0));
		assertEquals("C", ll.get(1));
		assertEquals("E", ll.get(2));
		ll.add(2,"D");
		assertEquals("D", ll.get(2));
		assertEquals("E", ll.get(3));
		ll.add(0, "AA");
		assertEquals("AA", ll.get(0));
		assertEquals("B", ll.get(1));
		assertEquals(5, ll.size());
		ll.set(0, "A");
		assertEquals("A", ll.get(0));
		assertEquals(5, ll.size());
		String str = ll.remove(3);
		assertEquals("D", str);;
		assertSame(4, ll.size());
		assertTrue(ll.contains("B"));		
		assertFalse(ll.contains(null));
		ll.add(null);
		assertTrue(ll.contains(null));
	}

	
	@Test
	public void testIterator()
	{
		LinkedList<String> ll = new LinkedList<>();
		ll.add("A");
		ll.add("B");
		ll.add("C");
		Iterator<String> iter = ll.iterator();
		assertTrue(iter.hasNext());
		assertEquals("A", iter.next());
		assertEquals("B", iter.next());
		assertTrue(iter.hasNext());
		iter.remove();
		assertTrue(iter.hasNext());
		assertEquals("C", iter.next());
		assertFalse(iter.hasNext());
		assertSame(2, ll.size());
		assertEquals("A", ll.get(0));
		assertEquals("C", ll.get(1));
	}

}
