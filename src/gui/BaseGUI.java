package gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;

public abstract class BaseGUI {
	
	private final double VERSION_NUMBER = 2.0;
	
	private JFrame window = new JFrame();
	private Container contPane = window.getContentPane();
	private ArrayList<JButton> actLis = new ArrayList<JButton>();
	
	public BaseGUI(String title) {
		window.setTitle("A* Algorithm v" + VERSION_NUMBER + " - " + title);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(800,500);
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		
	}
	
	protected void refresh(){
		window.setSize(window.getSize().width, window.getSize().height+1);
		window.setSize(window.getSize().width, window.getSize().height-1);
	}
	
	protected void add(JComponent component){
		contPane.add(component);
		
		if(component instanceof JButton){
			actLis.add((JButton)component);
		}
	}
	
	protected void removeComponent(Component component){
		contPane.remove(component);
		
		if(component instanceof JButton){
			for (int i = 0; i < actLis.size(); i++) {
				if(actLis.get(i).equals(component)){
					for (ActionListener act : actLis.get(i).getActionListeners()) {
						actLis.get(i).removeActionListener(act);
					}
					actLis.remove(i);
				}
			}
		}
	}
	
	protected void wipe(){
		for (Component comp : contPane.getComponents()) {
			removeComponent(comp);
		}
	}
}
