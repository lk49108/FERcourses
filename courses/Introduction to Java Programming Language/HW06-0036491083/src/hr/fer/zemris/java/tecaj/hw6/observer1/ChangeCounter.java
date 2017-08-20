/**
 * 
 */
package hr.fer.zemris.java.tecaj.hw6.observer1;

import java.util.HashMap;
import java.util.Map;

/**
 * This class inherits
 * IntegerStorageObserver interface and implements
 * its only method. Instance of this class is used for counting how many changes
 * have been triggered on Subject since this same instance of this class (observer)
 * has been added to observe Subject.
 * @author Leonardo Kokot
 * @version 1.0
 *
 */
public class ChangeCounter implements IntegerStorageObserver{

	//This internal map is made because more
	//than just one Subject can have the same observer stored in its
	//internal list of observers. This solves the problem.
	private Map<IntegerStorage, Integer> mapa;
	
	/**
	 * This constructor creates an instance of this class.
	 * It also initializes instance variable mapa.
	 */
	public ChangeCounter() {
		mapa = new HashMap<>();
	}
	
	@Override
	public void valueChanged(IntegerStorage istorage) {
		if(!(mapa.containsKey(istorage))){
			mapa.put(istorage, 1);
		}
		System.out.println("Number of value changes since tracking: " + mapa.get(istorage));
		mapa.put(istorage, mapa.get(istorage) + 1);
	}

}
