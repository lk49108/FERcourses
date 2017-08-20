package hr.fer.zemris.java.tecaj.hw1;

/**
 * Program manipulates with linked
 * list, implements nodes into link, 
 * sorts list and prints data from list.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class ProgramListe {

	static class CvorListe {
		CvorListe sljedeci;
		String podatak;
	}
	/**
	 * Method from which program starts its execution.
	 * @param args Arguments from command line.
	 * None is expected.
	 */
	public static void main(String[] args){
		CvorListe cvor = null;
		
		cvor = ubaci(cvor, "Jasna");
		cvor = ubaci(cvor, "Ana");
		cvor = ubaci(cvor, "Ivana");
		
		System.out.println("Ispisujem listu uz originalni poredak:");
		ispisiListu(cvor);
		
		cvor = sortirajListu(cvor);
		
		System.out.println("Ispisujem listu nakon sortiranja:");
		ispisiListu(cvor);
		
		int vel = velicinaListe(cvor);
		System.out.println("Lista sadrzi elemenata: "+vel);
	}
	
	/**
	 * Calculates the size of a linked list.
	 * @param cvor Root node of a linked list.
	 * @return Size of a linked list.
	 */
	private static int velicinaListe(CvorListe cvor) {
		if(cvor == null)
			return 0;
		return 1+ velicinaListe(cvor.sljedeci);
	}
	
	/**
	 * Inserts a node with its data
	 * at the end of a linked list.
	 * @param prvi Root node of a linked list.
	 * @param podatak String type data that is part of a node.
	 * @return Root node of a linked list.
	 */
	private static CvorListe ubaci(CvorListe prvi, String podatak) {
		if(prvi == null){
			prvi = new CvorListe();
			prvi.podatak = podatak;
			prvi.sljedeci = null;
			return prvi;
		}
		CvorListe pom = prvi;
		while(pom.sljedeci != null){
			pom = pom.sljedeci;
		}
		pom.sljedeci = new CvorListe();
		pom = pom.sljedeci;
		pom.podatak = podatak;
		pom.sljedeci = null;
		return prvi;
	}
	
	/**
	 * Prints out linked lists data.
	 * @param cvor Root node of a linked list.
	 */
	private static void ispisiListu(CvorListe cvor) {
			if(cvor == null)
				return;
			System.out.println(cvor.podatak);
			ispisiListu(cvor.sljedeci);
	}
	
	/**
	 * Sorts linked list on the basis of its String
	 * type data in increasing order.
	 * @param cvor Root node of a linked list.
	 * @return Root node of a linked list.
	 */
	private static CvorListe sortirajListu(CvorListe cvor) {
			if(velicinaListe(cvor) < 2)
				return cvor;
			CvorListe help1 = cvor;
			CvorListe help2;
			for(int i = 0; i < velicinaListe(cvor)-1; ++i) {
				help2 = help1.sljedeci;
				while(help2 != null) {
					if (help2.podatak.compareTo(help1.podatak) < 0) {
						String exchange = help2.podatak;
						help2.podatak = help1.podatak;
						help1.podatak = exchange;
					}
					help2 = help2.sljedeci;
				}
				help1 = help1.sljedeci;
			}
			return cvor;
	}
}
