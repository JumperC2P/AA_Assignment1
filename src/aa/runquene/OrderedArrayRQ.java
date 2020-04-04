package aa.runquene;
import java.io.PrintWriter;
import java.lang.String;

import aa.runquene.bean.OrderArrayProc;
import aa.runquene.impl.Runqueue;


/**
 * Implementation of the Runqueue interface using an Ordered Array.
 *
 * Your task is to complete the implementation of this class.
 * You may add methods and attributes, but ensure your modified class compiles and runs.
 *
 * @author Sajal Halder, Minyi Li, Jeffrey Chan, Chih-Hsuan Lee<s3714761>, Yiheng Liu<s3673551>
 */
public class OrderedArrayRQ implements Runqueue {
	
	private final int SIZE = 10;
	private OrderArrayProc[] queneArray;

    /**
     * Constructs empty queue
     */
    public OrderedArrayRQ() {
       
    	this.queneArray = new OrderArrayProc[SIZE];

    }  // end of OrderedArrayRQ()


    @Override
    public void enqueue(String procLabel, int vt) {
    	
    	// create a new process object with procLabel and vt
    	OrderArrayProc newProcess = new OrderArrayProc(procLabel, vt);
    	
    	// If the process is the first one in the array, insert it and end the method.
    	if (this.queneArray[0] == null) {
    		this.queneArray[0] = newProcess;
    		return;
    	}
    	
    	// if the procLabel is duplicated, ignore it. 
    	if (findProcess(procLabel)) {
    		return;
    	}

    	// create a temporal array with size check
    	// if the original array is full, create a new array with original size plus indicated size.
    	// if not, create a new array with the same size with that of original array.
    	OrderArrayProc[] tempQueneArray;
    	if (this.queneArray[queneArray.length-1] != null)
    		tempQueneArray = new OrderArrayProc[this.queneArray.length+SIZE];
    	else
    		tempQueneArray = new OrderArrayProc[this.queneArray.length];
    	
    	// use the boolean to record whether the new process is inserted.
    	Boolean isInsert = false;
    	int position = 0;
    	for (int j = 0 ; j < this.queneArray.length ; j++) {
    		
    		// end the lopp if it comes to a null element
    		if (queneArray[j] == null) {
    			position = j;
    			break;
    		}
    		
    		// if the new process is inserted, move original elements backward 1 position
    		if (isInsert) {
    			tempQueneArray[j+1] = this.queneArray[j];
    		}else {
    			// if the vt of the element in array is higher than vt from input
    			// insert the new process into the current position
    			// and change the value of boolean from false to true
    			if (this.queneArray[j].getVt() > vt) {
    				tempQueneArray[j] = newProcess;
    				j--;
    				isInsert = true;
        		}else {
        			tempQueneArray[j] = this.queneArray[j];
        		}
    		}
    	}
    	
    	// if there is no change in the loop,
    	// it means that the vt of new process is the highest one.
    	// so insert the new process into the first position of null.
    	if (!isInsert)
    		tempQueneArray[position] = newProcess;
    	
    	this.queneArray = tempQueneArray;

    } // end of enqueue()


	@Override
    public String dequeue() {
		
		// create a temporal array with the same size of original array
		OrderArrayProc[] tempQueneArray = new OrderArrayProc[this.queneArray.length];
		
		// insert all the elements in the original array, except the first element.
		for (int i = 1 ; i < this.queneArray.length ; i++) {
			
			if (this.queneArray[i] == null)
				break;
			
			tempQueneArray[i-1] = this.queneArray[i];
		}
		
		String delProcLabel = this.queneArray[0].getProcLabel();
		this.queneArray = tempQueneArray;
		
        return delProcLabel; // placeholder,modify this
    } // end of dequeue()


    @Override
    public boolean findProcess(String procLabel){
        
    	// search the same procLabel in the array
    	for (OrderArrayProc process : this.queneArray) {
    		
    		if (process == null)
    			break;
    		
    		if (procLabel.equals(process.getProcLabel()))
    			return true;
    	}

        return false; 
    } // end of findProcess()


    @Override
    public boolean removeProcess(String procLabel) {
        
    	OrderArrayProc[] tempQueneArray = new OrderArrayProc[this.queneArray.length];
    	int i = 0;
    	Boolean isRemove = false;
    	
    	for (OrderArrayProc process : this.queneArray) {
    		
    		if (process == null)
    			break;
    		
    		if (procLabel.equals(process.getProcLabel())) {
    			isRemove = true;
    			continue;
    		}else
    			tempQueneArray[i] = process;
    		i++;
    	}
		
		this.queneArray = tempQueneArray;

        return isRemove;
    } // end of removeProcess()


    @Override
    public int precedingProcessTime(String procLabel) {
    	
    	int sum = 0;
    	Boolean isFound = false;
    	
    	for (OrderArrayProc process : this.queneArray) {
    		if (process == null)
    			break;
    		
    		if (procLabel.equals(process.getProcLabel())) {
    			isFound = true;
    			break;
    		}else
    			sum += process.getVt();
    		
    	}
    	
    	if (!isFound)
    		sum = -1;
    	
        return sum; // placeholder, modify this
    }// end of precedingProcessTime()


    @Override
    public int succeedingProcessTime(String procLabel) {
    	
    	int sum = 0;
    	Boolean isFound = false;
    	
    	for (OrderArrayProc process : this.queneArray) {
    		if (process == null)
    			break;
    		
    		if (isFound)
    			sum += process.getVt();
    		
    		if (procLabel.equals(process.getProcLabel())) {
    			isFound = true;
    			continue;
    		}
    	}
    	
    	if (!isFound)
    		sum = -1;
    	
        return sum;
    } // end of precedingProcessTime()


    @Override
    public void printAllProcesses(PrintWriter os) {
    	
    	StringBuffer sb = new StringBuffer();
    	
    	for (OrderArrayProc process : this.queneArray) {
    		if (process == null) {
    			break;
    		}else {
    			sb.append(process.getProcLabel()).append(" ");
    		}
    	}
    	
    	os.println(sb.toString());

    } // end of printAllProcesses()

} // end of class OrderedArrayRQ
