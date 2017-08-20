package hr.fer.zemris.java.tecaj_13.dao.jpa;

import javax.persistence.EntityManagerFactory;

/**
 * Class which allows other classes and their methods to obtain 
 * connection to database.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class JPAEMFProvider {

	/**
	 * {@link EntityManagerFactory} property.
	 */
	public static EntityManagerFactory emf;
	
	/**
	 * Getter for property.
	 * @return emf.
	 */
	public static EntityManagerFactory getEmf() {
		return emf;
	}
	
	/**
	 * Setter for property.
	 * @param emf {@link EntityManagerFactory} instance.
	 */
	public static void setEmf(EntityManagerFactory emf) {
		JPAEMFProvider.emf = emf;
	}
}