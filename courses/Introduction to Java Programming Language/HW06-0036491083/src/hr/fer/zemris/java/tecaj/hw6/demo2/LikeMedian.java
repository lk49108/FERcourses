package hr.fer.zemris.java.tecaj.hw6.demo2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * This generic class is used for calculating
 * Median of Objects added to instance of this class.
 * Median is calculated using natural 
 * order. This means class is able to work with only
 * type of Objects which do have this natural order sorting 
 * attribute.
 * @author Leonardo Kokot
 * @version 1.0
 *
 * @param <T> Type of data instance of this class works with.
 */
public class LikeMedian<T> implements Iterable<T>{

	/**
	 * This instance variable holds data stored in instance of this class in
	 * natural order. It is initialized through constructor.
	 */
	private List<T> tree;
	
	/**
	 * This instance variable holds data stored in instance of this class in order
	 * that they were obtained. It is initialized through constructor.
	 */
	private List<T> order;

	/**
	 * This constructor creates instance of this class
	 * and initializes tree instance variable.
	 */
	public LikeMedian() {
		this.tree = new ArrayList<>();
		this.order = new ArrayList<>();
	}

	/**
	 * This method is used for adding data into local 
	 * set.
	 * @param value T type value which is being added to
	 * internal storage of values.
	 * @throws IllegalArgumentException if null is provided
	 * as an argument.
	 */
	public void add(T value){
		if(value == null){
			throw new IllegalArgumentException("Null value is not allowed.");
		}
		tree.add(value);
		order.add(value);
	}
	
	/**
	 * This method is used for obtaining 
	 * median of values stored in this instance.
	 * If user adds an odd number of elements, 
	 * the method returns median element.
	 * If user provides an even number of elements,
	 * the method returns the smaller from the two 
	 * elements which would usually be used to calculate median element.
	 * If internal storage of data is empty, appropriate Optional object is returned.
	 * @return Appropriate Optional object.
	 */
	public Optional<T> get(){
		tree.sort(null);
		if(tree.isEmpty()){
			return Optional.empty();
		}
		if(tree.size() % 2 == 1){
			int i = 0;
			for(T element : tree){
				if(i == tree.size() / 2){
					return Optional.of(element);
				}
				i++;
			}
		}
		//If execution comes to this point that means that there is 
		//even number of values stored in a tree.
		int i = 0;
		T returnElem = null;
		for(T element : tree){
			if(i == tree.size() / 2 - 1){
				returnElem = element;
				break;
			}
			i++;
		}
		return Optional.of(returnElem);
	}

	@Override
	public Iterator<T> iterator() {
		return order.iterator();
	}
	
} 
