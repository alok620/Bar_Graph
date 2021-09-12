import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

public class LayoutPanel extends JPanel implements ActionListener {
	
  // fields
	private OptionPanel optionP;
	private BarPanel barP;
	
  // constructor
	public LayoutPanel() {
		this.setLayout(new BorderLayout());
		
		optionP = new OptionPanel(this);
		this.add(optionP, BorderLayout.NORTH);
    
		barP = new BarPanel(this);
		this.add(barP, BorderLayout.CENTER);
	}
	
	public OptionPanel getOptionP()			{ return optionP; }

  // methods
	public void actionPerformed(ActionEvent e) {
		if (optionP.isAutorunOn()) {
			barP.stepForward();
		}
	}
	
	public void performOption(OptionEvent type) {
		if (type == OptionEvent.ADD) {
			barP.addBar();
		} else if (type == OptionEvent.SORT) {
			if (optionP.getSortChoice().equals("BUBBLE SORT")) {
				barP.bubbleSort();
			} else if (optionP.getSortChoice().equals("SELECTION SORT")) {
				barP.selectionSort();
			}  else if (optionP.getSortChoice().equals("INSERTION SORT")) {
				barP.insertionSort();
			}	else if (optionP.getSortChoice().equals("MERGE SORT")) {
				barP.mergeSort();
			} else if (optionP.getSortChoice().equals("QUICK SORT")) {
				barP.quickSort();
			}
		} else if (type == OptionEvent.RANDOMIZE) {
			barP.randomize();
		} else if (type == OptionEvent.STEP) {
			barP.stepForward();
		}
	}
}
