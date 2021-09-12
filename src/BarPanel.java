import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JPanel;

public class BarPanel extends JPanel {
	
  // fields
	private ArrayList<Bar> bars;
	private LayoutPanel layoutP;
	private ArrayList<Step> steps;
	private ArrayList<Bar> startBars;
	private ArrayList<Bar> buffer;
	private int stepCounter;
	
  // constants
  private static final int STEPSIZE = 10;
	
  // constructor
	public BarPanel(LayoutPanel lp) {
    this.layoutP = lp;
		bars = new ArrayList<Bar>();
		steps = new ArrayList<Step>();
		startBars = new ArrayList<Bar>();
		buffer = new ArrayList<Bar>();
		stepCounter = -1;
	}
	
	// getters
	public int getBarsSize()			{ return bars.size(); }
	
	
  // methods
	public void paintComponent(Graphics g) {
    super.paintComponent(g);

		if (bars.size() == 0) {
			return;
		}
		
		int totalBarWidth = getBarWidth() * bars.size();
		int startBarX = this.getWidth() / 2 - totalBarWidth / 2;
		
		for (int i = 0; i < bars.size(); i++) {
			Bar b = bars.get(i);
			g.setColor(b.getColor());
			int x = getBarX(i);
			int y = this.getHeight() - b.getHeight();
			int width = getBarWidth();
			int height = b.getHeight();
			g.fillRect(x, y, width, height);
		}
		drawBuffer(g);
		drawStepMark(g);
	}
	
  private void storeStartBars() {
    startBars.clear();
    for (Bar x : bars)
    {
      startBars.add(x);
    }
  }

  private void restoreStart() {
    bars.clear();
    for (Bar x : startBars)
    {
      bars.add(x);
    }
    this.stepCounter = 0;
  }

	public void addBar() {
		Bar b = new Bar((int) (Math.random()*(this.getHeight() - STEPSIZE) + STEPSIZE));
		bars.add(b);
		buffer.add(null);
		this.repaint();
	}

  private int getBarWidth()
  {
    return Math.min(this.getWidth() / bars.size(), 300);
  }
	
	private int getBarX(int b) {
		int totalBarWidth = getBarWidth() * bars.size();
		int startBarX = this.getWidth() / 2 - totalBarWidth / 2;
		return getBarWidth()*b + startBarX;
	}

  private void clearSteps() {
		steps.clear();
		stepCounter = -1;
	}
	
	private void drawStepMark(Graphics g) {
		if (stepCounter == -1 || stepCounter == steps.size()) {
			return;
		}

		Step s = steps.get(stepCounter);
		int x1 = getBarX(s.getIndex1());
		int x2 = getBarX(s.getIndex2());
		
		if (s.getType() == Step.COMPARE) {
			g.setColor(Color.ORANGE);	
		} else if (s.getType() == Step.SWAP) {
			g.setColor(Color.RED);
		}
		else if(s.getType() == Step.TOBUFFER)
			g.setColor(Color.BLUE);
		else if(s.getType() == Step.TOBAR)
			g.setColor(Color.CYAN);

		g.fillRect(x1, this.getHeight() - STEPSIZE, getBarWidth(), STEPSIZE);
		g.fillRect(x2, this.getHeight() - STEPSIZE, getBarWidth(), STEPSIZE);
	}
	
	public void bubbleSort() {
		if (bars.size() == 0) {
			return;
		}
	clearSteps();

    this.storeStartBars();
    for (int sortedIndex = bars.size(); sortedIndex > 0; sortedIndex--)
    {
      for (int index = 0; index < sortedIndex - 1; index++)
      {
        Bar bar1 = bars.get(index);
        Bar bar2 = bars.get(index+1);
        Step compareStep = new Step(index, index+1, Step.COMPARE);
        steps.add(compareStep);
        if (bar1.getHeight() > bar2.getHeight())
        {
          Step swapStep = new Step(index, index+1, Step.SWAP);
          steps.add(swapStep);
          swapBars(index, index+1);
        }
      }
    }

    this.restoreStart();
		this.repaint();
	}

  public void selectionSort() {
    if (bars.size() == 0)
      return;
    clearSteps();
    this.storeStartBars();
    for (int j = 0; j < bars.size(); j++)
    {
      int minIndex = j;
      for (int k = j + 1; k < bars.size(); k++)
      {
        Bar minBar = bars.get(minIndex);
        Bar bar = bars.get(k);
        Step compareStep = new Step(minIndex, k, Step.COMPARE);
        steps.add(compareStep);
        if (bar.getHeight() < minBar.getHeight())
        {
          minIndex = k;
        }
      }
      Step swapStep = new Step(j, minIndex, Step.SWAP);
      steps.add(swapStep);
      swapBars(j, minIndex);
    }

    this.restoreStart();
    this.repaint();
  }
  
  public void insertionSort()	{
	  if(bars.size() == 0)
		  return;
	  clearSteps();
	  this.storeStartBars();
	  for(int i = 0; i < bars.size()-1; i++)
	  {
		  for(int j = i+1; j > 0; j--)
		  {
			  Step compareStep = new Step(j-1, j, Step.COMPARE);
		      steps.add(compareStep);
			  if(bars.get(j).getHeight() < bars.get(j-1).getHeight())
			  {
				  Step swapStep = new Step(j-1, j, Step.SWAP);
			      steps.add(swapStep);
				  swapBars(j-1, j);
			  }
		  }
	  }
	  this.restoreStart();
	  this.repaint();
	  
  }
  public void mergeSort()
  {
	  clearSteps();
	  this.storeStartBars();
	  merge(0, bars.size()-1);
	  this.restoreStart();
	  this.repaint();
  }
  
  public void merge(int low, int high)	{
	  if(low >= high)
		  return;
	  if(low+1 == high)
	  {
		  Step compareStep = new Step(low, high, Step.COMPARE);
		  steps.add(compareStep);
		  if(bars.get(low).getHeight() > bars.get(high).getHeight())
		  {
			  Step swapStep = new Step(low, high, Step.SWAP);
			  steps.add(swapStep);
			  swapBars(low, high);
		  }
		  return;
	  }
	  int mid = (low+high)/2;
	  merge(low, mid);
	  merge(mid+1, high);
	  
	  int frontIndex = low;
	  int backIndex = mid+1;
	  
	  boolean frontIsDone = false;
	  boolean backIsDone = false;
	  
	  for(int b = low; b <= high; b++)
	  {
		  if(frontIsDone)
		  {
			  bufferSetAndSave(b, backIndex);
			  backIndex++;
			  continue;
		  }
		  if(backIsDone)
		  {
			  bufferSetAndSave(b, frontIndex);
			  frontIndex++;
			  continue;
		  }
		  if(bars.get(frontIndex).getHeight() < bars.get(backIndex).getHeight())
		  {
			  bufferSetAndSave(b, frontIndex);
			  frontIndex++;
			  if(frontIndex > mid)
				  frontIsDone = true;
		  }
		  else
		  {
			  bufferSetAndSave(b, backIndex);
			  backIndex++;
			  if(backIndex > high)
				  backIsDone = true;
		  }
	  }
	  for(int i = low; i <= high; i++)
	  {
		  Step barStep = new Step(i, i, Step.TOBAR);
		  steps.add(barStep);
		  bars.set(i, buffer.get(i));
		  buffer.set(i, null);
	  }
	  
  }
  
  public int qSortR(int low, int high)
  {
	  int ind = low-1;
	  Step pivotStep = new Step(high, high, Step.PIVOT);
	  int pivot = bars.get(high).getHeight();
	  for(int i = low; i <= high; i++)
	  {
		  Step compareStep = new Step(i, high, Step.COMPARE);
		  steps.add(compareStep);
		  if(bars.get(i).getHeight() < pivot)
		  {
			  ind++;
			  Step swapStep = new Step(ind, i, Step.SWAP);
			  steps.add(swapStep);
			  swapBars(i, ind);
		  }
	  }
	  Step swapStep = new Step(ind+1, high, Step.SWAP);
	  steps.add(swapStep);
	  swapBars(ind+1, high);
	  return ind+1;
  
  }
  
  public void qSort2(int low, int high)
  {
	  
	  if(low < high)
	  {
		  int pivot = qSortR(low, high);
		  qSort2(low, pivot-1);
		  qSort2(pivot+1, high);
	  }
  }
  
  public void quickSort()
  {
	  clearSteps();
	  this.storeStartBars();
	  qSort2(0, bars.size()-1);
	  this.restoreStart();
	  this.repaint();
  }
  
  public void bufferSetAndSave(int bufdex, int bardex)
  {
	  Step bufferStep = new Step(bufdex, bardex, Step.TOBUFFER);
	  steps.add(bufferStep);
	  buffer.set(bufdex, bars.get(bardex));
  }
  
	
	private void swapBars(int index1, int index2) {
		Bar b1 = bars.get(index1);
		Bar b2 = bars.get(index2);
		bars.set(index2, b1);
		bars.set(index1, b2);
	}
	
	public void stepForward() {
		if (stepCounter == -1 || stepCounter == steps.size()) {
			return;
		}

		Step s = steps.get(stepCounter);
		if (s.getType() == Step.SWAP) {
			swapBars(s.getIndex1(), s.getIndex2());
		} 
		
		if(s.getType() == Step.TOBUFFER)
		{
			buffer.set(s.getIndex1(), bars.get(s.getIndex2()));
		}
		
		if(s.getType() == Step.TOBAR)
		{
			bars.set(s.getIndex1(), buffer.get(s.getIndex2()));
			buffer.set(s.getIndex2(), null);
		}

		stepCounter++;
		this.repaint();
	}

  public void randomize() {
		for (int startIndex = 0; startIndex < bars.size(); startIndex++) {
			int randIndex = (int) (Math.random() * bars.size());
      swapBars(startIndex, randIndex);
		}

    this.clearSteps();
		this.repaint();
	}
  
  public void drawBuffer(Graphics g)
  {
	  for(int i = 0; i < buffer.size(); i++)
	  {
		  Bar b = buffer.get(i);
		  if(b == null)
			  continue;
		  g.setColor(Color.BLACK);
		  g.drawRect(getBarX(i),this.getHeight()-b.getHeight() , this.getBarWidth(), b.getHeight());
	  }
  }
  
  
  
}