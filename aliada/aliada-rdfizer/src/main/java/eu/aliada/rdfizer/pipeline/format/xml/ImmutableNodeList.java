// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-rdfizer
// Responsible: ALIADA Consortiums
package eu.aliada.rdfizer.pipeline.format.xml;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.common.collect.AbstractIterator;

/**
 * A {@link List} adapter for {@link NodeList}.
 * Sadly, {@link NodeList} is not an {@link Iterable} so cannot be used in 
 * a Velocity template. 
 * This class simply decorates a {@link NodeList} adding *only* the behaviour required for iteration.
 * 
 * As the name suggests: the list is supposed to be immutable so each method that tries to modify its content
 * throw {@link UnsupportedOperationException}.
 * 
 * As an additional note, this is not a complete "List" adapter...following a "TSTTCPW" approach :D a lot of methods 
 * (those we don't need at the moment) throw {@link UnsupportedOperationException}.
 * 
 * @author Andrea Gazzarini
 * @since 1.0
 */
public class ImmutableNodeList implements List<Node> {

	private final NodeList wrapped;
	
	/**
	 * Buidls an immutable nodelist wrapper.
	 * 
	 * @param wrapped the nodelist.
	 */
	ImmutableNodeList(final NodeList wrapped) {
		this.wrapped = wrapped;
	}
	
	@Override
	public int size() {
		return wrapped.getLength();
	}

	@Override
	public boolean isEmpty() {
		return wrapped.getLength() == 0;
	}

	@Override
	public boolean contains(final Object o) {
		if (!(o instanceof Node)) {
			return false;
		}
		
		for (int i = 0; i < wrapped.getLength(); i++) {
			if (wrapped.item(i).equals(o)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Iterator<Node> iterator() {
		return new AbstractIterator<Node>() {
			int index = 0;

			@Override
			protected Node computeNext() {
				final Node item = wrapped.item(index++);
				return item != null ? item : endOfData();
			}
		};
	}

	@Override
	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> T[] toArray(final T[] a) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean add(final Node e) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean remove(final Object o) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean containsAll(final Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addAll(final Collection<? extends Node> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addAll(final int index, final Collection<? extends Node> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeAll(final Collection<?> c) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public boolean retainAll(final Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clear() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Node get(final int index) {
		return wrapped.item(index);
	}

	@Override
	public Node set(final int index, final Node element) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void add(final int index, final Node element) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Node remove(final int index) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int indexOf(final Object o) {
		for (int i = 0; i < wrapped.getLength(); i++) {
			if (wrapped.item(i).equals(o)) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public int lastIndexOf(final Object o) {
		throw new UnsupportedOperationException();
	}

	@Override
	public ListIterator<Node> listIterator() {
		throw new UnsupportedOperationException();
	}

	@Override
	public ListIterator<Node> listIterator(final int index) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<Node> subList(final int fromIndex, final int toIndex) {
		throw new UnsupportedOperationException();
	}
}