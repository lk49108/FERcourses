package her.fer.zemris.java.tests;

import java.awt.Component;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;
import static org.junit.Assert.*;

import hr.fer.zemris.java.gui.layouts.RCPosition;

public class Test {

	@org.junit.Test
	public void testForRCPosition() {
		Map<Component, RCPosition> map = new HashMap<>();
		JPanel panel = new JPanel();
		map.put(panel, new RCPosition(1, 1));
		RCPosition position = map.get(panel);
		assertEquals(1, position.getColumn());
		
	}

}
