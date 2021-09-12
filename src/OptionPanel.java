import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class OptionPanel extends JPanel implements ActionListener {
	
  // fields
	private JButton[] buttons;
	private LayoutPanel layoutP;
	
	private JComboBox<String> sortChoices;
	private JCheckBox autorunToggle;
	
  // constructor
	public OptionPanel(LayoutPanel lp) {
		layoutP = lp;
		
		buttons = new JButton[4];
		buttons[0] = new JButton("ADD");
		buttons[1] = new JButton("RANDOMIZE");
		buttons[2] = new JButton("STEP");
		buttons[3] = new JButton("SORT");
		
		for (JButton b : buttons) {
			this.add(b);
			b.addActionListener(this);
		}
		
		sortChoices = new JComboBox<String>();
		sortChoices.addItem("BUBBLE SORT");
		sortChoices.addItem("SELECTION SORT");
		sortChoices.addItem("INSERTION SORT");
		sortChoices.addItem("MERGE SORT");
		sortChoices.addItem("QUICK SORT");
		this.add(sortChoices);
		
		autorunToggle = new JCheckBox("Autorun");
		this.add(autorunToggle);
	}

	// methods
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JButton) {
			JButton b = (JButton) e.getSource();
			
			if (b.getText().equals("ADD")) {
				layoutP.performOption(OptionEvent.ADD);
			}
			if (b.getText().equals("SORT")) {
				layoutP.performOption(OptionEvent.SORT);
			}
			if (b.getText().equals("RANDOMIZE")) {
				layoutP.performOption(OptionEvent.RANDOMIZE);
			}
			if (b.getText().equals("STEP")) {
				layoutP.performOption(OptionEvent.STEP);
			}
		}
	}
	
	public boolean isAutorunOn() {
		return autorunToggle.isSelected();
	}
	
	public String getSortChoice() {
		return (String) sortChoices.getSelectedItem();
	}
}
