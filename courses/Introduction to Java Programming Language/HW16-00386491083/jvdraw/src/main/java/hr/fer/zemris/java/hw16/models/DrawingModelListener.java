package hr.fer.zemris.java.hw16.models;

/**
 * Listeners to {@link DrawingModel}.
 * 
 * @author Leonardo Kokot
 * @version 1.0
 */
public interface DrawingModelListener {

    /**
     * Called when the object is added
     * 
     * @param source source on which objects were added
     * @param index0 index0
     * @param index1 index1
     */
    public void objectsAdded(DrawingModel source, int index0, int index1);
    
    /**
     * When object is changed, this method is called.
     * 
     * @param source source on which objects were changed
     * @param index0 index0
     * @param index1 index1
     */
    public void objectsChanged(DrawingModel source, int index0, int index1);
    
    /**
     * This method is called when the object is removed.
     * 
     * @param source source on which objects were removed
     * @param index0 index0
     * @param index1 index1
     */
    public void objectsRemoved(DrawingModel source, int index0, int index1);


}