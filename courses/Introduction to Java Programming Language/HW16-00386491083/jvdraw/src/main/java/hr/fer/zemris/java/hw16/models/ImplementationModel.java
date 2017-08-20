package hr.fer.zemris.java.hw16.models;


import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw16.components.GeometricalObject;


/**
 * Implements {@link DrawingModel} interface.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class ImplementationModel implements DrawingModel {

	/**
	 * This list holds objects contained in program., i.e. GeometricalObjects which user drawn.
	 */
    private List<GeometricalObject> objects = new ArrayList<>();
    
    /**
     * List of listeners.
     */
    private List<DrawingModelListener> listeners = new ArrayList<>();

    @Override
    public int getSize() {
        return objects.size();
    }

    @Override
    public void add(GeometricalObject obj) {
        int index = getSize();
        
        objects.add(obj);
        
        for (DrawingModelListener l : listeners) {
            l.objectsAdded(this, index, index);
        }
    }

    @Override
    public void remove(GeometricalObject obj) {
        int index = objects.indexOf(obj);
        
        if (index == -1) {
            return;
        }
        
        objects.remove(index);
        
        for (DrawingModelListener l : listeners) {
            l.objectsRemoved(this, index, index);
        }
    }

    @Override
    public GeometricalObject getObject(int index) {
        return objects.get(index);
    }

    @Override
    public void clear() {
        for (DrawingModelListener l : listeners) {
            l.objectsRemoved(this, 0, objects.size());
        }
        objects = new ArrayList<>();
    }

    @Override
    public void addDrawingModelListener(DrawingModelListener l) {
        if (!listeners.contains(l)) {
            listeners.add(l);
        }
    }

    @Override
    public void removeDrawingModelListener(DrawingModelListener l) {
        listeners.remove(l);
    }

    @Override
    public Rectangle getBoundingBox() {
        
    	int left = Integer.MAX_VALUE;
        
    	int top = Integer.MAX_VALUE;
        
    	int right = 0;
        
    	int bottom = 0;

        for (GeometricalObject o : objects) {
            
        	Rectangle r = o.getBounds();
            
        	 if(bottom < r.y + r.height){
              	bottom = r.y + r.height;
              }
        	
        	 if(top > r.y){
             	top = r.y;
             }
        	
            if(left > r.x){
            	left = r.x;
            }
            
            if(right < r.x + r.width){
            	right = r.x + r.width;
            }

        }

        return new Rectangle(left, top, right - left, bottom - top);
    }
}