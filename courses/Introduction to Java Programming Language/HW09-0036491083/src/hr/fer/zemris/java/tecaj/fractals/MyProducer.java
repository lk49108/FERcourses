package hr.fer.zemris.java.tecaj.fractals;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;

/**
 * This class implements {@link IFractalProducer}.
 * It implements produce method which is used for
 * production of {@link WorkingJob} instances and to delegate
 * them to {@link ExecutorService}. 
 * @author Leonardo Kokot
 * @version 1.0
 */
public class MyProducer implements IFractalProducer {

	/**
	 * {@link ExecutorService} instance.
	 */
	ExecutorService pool;
	
	/**
	 * Constructor which creates instance of this class
	 * and initializes pool instance variable.
	 * @param pool {@link ExecutorService} instance.
	 */
	public MyProducer(ExecutorService pool){
		this.pool = pool;
	}
			
	@Override
	public void produce(double reMin, double reMax, double imMin, double imMax, int width, int height, long requestNo,
			IFractalResultObserver observer) {
		
		short[] data = new short[width * height];
		
		//Number of jobs == 8 * available processors.
		final int brojTraka = 8 * Runtime.getRuntime().availableProcessors();
		
		int brojYPoTraci = height / brojTraka;
		
		List<Future<Void>> rezultati = new ArrayList<>();
		
		for(int i = 0; i < brojTraka; i++) {
			int yMin = i*brojYPoTraci;
			int yMax = (i+1)*brojYPoTraci-1;
			if(i==brojTraka-1) {
				yMax = height-1;
			}
			WorkingJob posao = new WorkingJob(reMin, reMax, imMin, imMax, width, height, yMin, yMax, data);
			rezultati.add(pool.submit(posao));
		}
		for(Future<Void> posao : rezultati) {
			try {
				posao.get();
			} catch (InterruptedException | ExecutionException e) {
			}
		}

		System.out.println("Racunanje gotovo. Idem obavijestiti promatraca tj. GUI!");
		observer.acceptResult(data, (short) (Newton_Raphson.getPOLYNOMIAL().toComplexPolynom().order() + 1), requestNo);
	}

}
