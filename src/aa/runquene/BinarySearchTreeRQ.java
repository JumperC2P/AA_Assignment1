package aa.runquene;

import java.io.PrintWriter;
import java.lang.String;

import aa.runquene.bean.BSTProc;
import aa.runquene.impl.Runqueue;

/**
 * Implementation of the Runqueue interface using a Binary Search Tree.
 *
 * Your task is to complete the implementation of this class.
 * You may add methods and attributes, but ensure your modified class compiles and runs.
 *
 * @author Sajal Halder, Minyi Li, Jeffrey Chan, Chih-Hsuan Lee<s3714761>, Yiheng Liu<s3673551>
 */
public class BinarySearchTreeRQ implements Runqueue {
	
	private final int REBUILD_SIZE = 5;
	BSTProc rootNode;
	int size;
	int index = 0;

    /**
     * Constructs empty queue
     */
    public BinarySearchTreeRQ() {
    	
    	rootNode = null;
    	size = 0;

    }  // end of BinarySearchTreeRQ()


    @Override
    public void enqueue(String procLabel, int vt) {

    	BSTProc newNode = new BSTProc(procLabel, vt);
    	
    	if (rootNode == null) {
    		rootNode = newNode;
    		size++;
    		return;
    	}
    	
    	insert(rootNode, newNode);
    	
    	size++;
    	
    	if (size % REBUILD_SIZE == 0) {
    		rebuild();
    	}
    } // end of enqueue()


    private void insert(BSTProc currentNode, BSTProc newNode) {
    	
		
    	if (newNode.getVt() < currentNode.getVt()) {
    		if (currentNode.getLeftNode()==null) {
    			currentNode.setLeftNode(newNode);
    			newNode.setParentNode(currentNode);
    		}else {
    			insert(currentNode.getLeftNode(), newNode);
    		}
    	}else {
    		if (currentNode.getRightNode()==null) {
    			currentNode.setRightNode(newNode);
    			newNode.setParentNode(currentNode);
    		}else {
    			insert(currentNode.getRightNode(), newNode);
    		}
    	}
	}
    
    
	@Override
    public String dequeue() {
		
		BSTProc minProc = findMinVruntime(rootNode);
		
		Boolean isRemove = removeProcess(minProc.getProcLabel());

        return isRemove?minProc.getProcLabel():""; // placeholder, modify this
    } // end of dequeue()


    private BSTProc findMinVruntime(BSTProc currentNode) {
    	
    	if (currentNode.getLeftNode() == null)
    		return currentNode;
    	else
    		return findMinVruntime(currentNode.getLeftNode());
	}


	@Override
    public boolean findProcess(String procLabel) {
        
        return findProcessByLabel(procLabel, rootNode) != null;
    } // end of findProcess()

	
	private BSTProc findProcessByLabel(String procLabel, BSTProc currentNode) {
		
		BSTProc targetNode = null;
		
		if (procLabel.equals(currentNode.getProcLabel()))
			return currentNode;
		
		if (currentNode.getLeftNode() != null) {
			targetNode = findProcessByLabel(procLabel, currentNode.getLeftNode());
		}
		
		if (targetNode == null) {
			if (currentNode.getRightNode() != null) {
				targetNode = findProcessByLabel(procLabel, currentNode.getRightNode());
			}
		}
		return targetNode;
	}

	
    @Override
    public boolean removeProcess(String procLabel) {
    	
    	
    	BSTProc targetNode = findProcessByLabel(procLabel, rootNode);
    	if (targetNode == null)
    		return false;
    	
    	if (targetNode.getLeftNode() == null && targetNode.getRightNode() == null) {
    		return removeEndNode(targetNode);
    	}else if (targetNode.getLeftNode() != null && targetNode.getRightNode() != null) {
    		return removeTwoChildNode(targetNode);
    	}else {
    		return removeOneChildNode(targetNode);
    	}
    }
	
    
	private Boolean rearrangeNode(BSTProc targetNode, BSTProc substitueNode, Boolean isFullChildren) {
    	
    	BSTProc parentNode = targetNode.getParentNode();
    	BSTProc targetLeftNode = targetNode.getLeftNode();
    	BSTProc subParentNode = substitueNode!=null?substitueNode.getParentNode():null;
    	BSTProc targetRightNode = targetNode.getRightNode();
    	BSTProc subRightNode = substitueNode!=null?substitueNode.getRightNode():null;
    	
    	if (parentNode == null || isFullChildren) {
    		
    		if (parentNode == null) {
    			rootNode = substitueNode;
    		}
    		
    		
    		if (subRightNode != null) {
    			if (targetRightNode != null) {
    				BSTProc targetRightMinNode = findMinVruntime(targetRightNode);
    				if (!targetRightMinNode.getProcLabel().equals(substitueNode.getProcLabel())) {
    					subRightNode.setParentNode(targetRightMinNode);
    					targetRightMinNode.setLeftNode(subRightNode);
    				}
    			}
    		}
    		
    		if (targetLeftNode != null) {
    			if (!targetLeftNode.getProcLabel().equals(substitueNode.getProcLabel())) {
    				targetLeftNode.setParentNode(substitueNode);
    				substitueNode.setLeftNode(targetLeftNode);
    			}
    		}
    		
    		if (subParentNode != null && targetNode.getProcLabel().equals(subParentNode.getProcLabel())) {
    			
    			if (targetRightNode != null) {
    				if (!substitueNode.getProcLabel().equals(targetRightNode.getProcLabel()))
    					substitueNode.setRightNode(targetRightNode);
    			}
    		}else {
    			if (targetRightNode != null) {
    				targetRightNode.setParentNode(substitueNode);
	    			substitueNode.setRightNode(targetRightNode);
    			}
    			
    		}
    		
    		if (subParentNode != null && !subParentNode.getProcLabel().equals(targetNode.getProcLabel())) {
    			if (substitueNode.getVt() < subParentNode.getVt()) {
    				subParentNode.setLeftNode(null);
    			}else {
    				subParentNode.setRightNode(null);
    			}
    		}
    		
    	}
    	
    	if (substitueNode != null || (substitueNode != null && parentNode == null)) {
    		substitueNode.setParentNode(parentNode);
    	}
    	
    	if (parentNode != null) {
    		if (targetNode.getVt() < parentNode.getVt()) {
    			parentNode.setLeftNode(substitueNode);
    		}else {
    			parentNode.setRightNode(substitueNode);
    		}
		}
    	
		size--;
		return true;
    }

	
    private Boolean removeTwoChildNode(BSTProc targetNode) {
		
    	BSTProc minProc = findMinVruntime(targetNode.getRightNode());
    	if (minProc == null)
    		return false;
    	
		return rearrangeNode(targetNode, minProc, true);
	}
    
    
	private Boolean removeOneChildNode(BSTProc targetNode) {
		
		BSTProc childNode = targetNode.getLeftNode() != null?targetNode.getLeftNode():targetNode.getRightNode();
		return rearrangeNode(targetNode, childNode, false);
	}
	
	
	private Boolean removeEndNode(BSTProc targetNode) {
		return rearrangeNode(targetNode, null, false);
	}


	@Override
    public int precedingProcessTime(String procLabel) {
        
    	BSTProc targetNode = findProcessByLabel(procLabel, rootNode);
    	if (targetNode == null)
    		return -1;
    	
    	int sum = 0;
    	BSTProc targetLeftNode = targetNode.getLeftNode();
    	if (targetLeftNode != null)
    		sum = sumVruntime(sum, targetNode.getLeftNode());
    	
    	if (targetNode.getVt() >= rootNode.getVt() && !procLabel.equals(rootNode.getProcLabel())) {
    		BSTProc rootLeftNode = rootNode.getLeftNode();
    		if (rootLeftNode != null) {
    			sum = sumVruntime(sum, rootLeftNode);
    			sum += rootNode.getVt();
    		}
    	}
    	
    	sum = sumParentLeft(sum, targetNode);
    	return sum;
    } // end of precedingProcessTime()


    private Integer sumVruntime(int sum, BSTProc currentNode) {
    	
    	if (currentNode.getLeftNode() != null)
    		sum = sumVruntime(sum, currentNode.getLeftNode());
    	
    	if (currentNode.getRightNode() != null)
    		sum = sumVruntime(sum, currentNode.getRightNode());
    	
    	sum += currentNode.getVt();
		return sum;
	}


	@Override
    public int succeedingProcessTime(String procLabel) {
		
		BSTProc targetNode = findProcessByLabel(procLabel, rootNode);
    	if (targetNode == null)
    		return -1;
    	
    	Integer sum = 0;
    	BSTProc targetRightNode = targetNode.getRightNode();
    	if (targetRightNode != null)
    		sum = sumVruntime(sum, targetNode.getRightNode());
    	
    	if (targetNode.getVt() < rootNode.getVt() && !procLabel.equals(rootNode.getProcLabel())) {
    		BSTProc rootRightNode = rootNode.getRightNode();
    		if (rootRightNode != null) {
    			sum = sumVruntime(sum, rootRightNode);
    			sum += rootNode.getVt();
    		}
    	}
    	
    	sum = sumParentRight(sum, targetNode);
    	
    	return sum;
    } // end of precedingProcessTime()
	
	
	private int sumParentLeft(int sum, BSTProc targetNode) {
		
		BSTProc targetParentNode = targetNode.getParentNode();
		
		if (targetParentNode != null) {
			if (targetParentNode.getProcLabel().equals(rootNode.getProcLabel()))
				return sum;
			
			if (targetNode.getVt() >= targetParentNode.getVt()) {
				BSTProc targetParentLeftNode = targetParentNode.getLeftNode();
				if ( targetParentLeftNode != null && !targetParentLeftNode.getProcLabel().equals(targetNode.getProcLabel()) ) {
					sum = sumVruntime(sum, targetParentLeftNode);
					sum = sumParentLeft(sum, targetParentNode);
				}else {
					sum = sumParentLeft(sum, targetParentNode);
				}
				sum += targetParentNode.getVt();
			}else {
				sum = sumParentLeft(sum, targetParentNode);
			}
		}
		return sum;
	}


    private Integer sumParentRight(Integer sum, BSTProc targetNode) {
    	
    	BSTProc targetParentNode = targetNode.getParentNode();
    	
		if (targetParentNode != null) {
			if (targetParentNode.getProcLabel().equals(rootNode.getProcLabel()))
				return sum;
			
			if(targetNode.getVt() < targetParentNode.getVt()) {
				BSTProc targetParentRightNode = targetParentNode.getRightNode();
				if ( targetParentRightNode != null && !targetParentRightNode.getProcLabel().equals(targetNode.getProcLabel()) ) {
					sum = sumVruntime(sum, targetParentRightNode);
					sum = sumParentRight(sum, targetParentNode);
				}else {
					sum = sumParentRight(sum, targetParentNode);
				}
				sum += targetParentNode.getVt();
			}else {
				sum = sumParentRight(sum, targetParentNode);
			}
			
		}
		return sum;
	}


	@Override
    public void printAllProcesses(PrintWriter os) {
        
		os.println(this.toString());

    } // end of printAllProcess()
    
    private StringBuffer getLabelWithOrderAsc(StringBuffer sb, BSTProc currentNode) {
		
		if (currentNode.getLeftNode() != null) {
			sb = getLabelWithOrderAsc(sb, currentNode.getLeftNode());
		}
		
		sb.append(currentNode.getProcLabel()).append(" ");
		
		if (currentNode.getRightNode() != null) {
			return getLabelWithOrderAsc(sb, currentNode.getRightNode());
		}
		
		return sb;
	}
    
    public String toString() {
    	return getLabelWithOrderAsc(new StringBuffer(), rootNode).toString();
    }


    private void rebuild() {
		
		index = 0;
		BSTProc[] array = getProcessAsc(new BSTProc[size], rootNode);
		
		int middleIndex = Integer.valueOf(array.length/2);
		rootNode = array[middleIndex];
		rootNode.setParentNode(null);
		
		rootNode.setLeftNode(treeBuilder(array, 0, middleIndex-1, rootNode));
		rootNode.setRightNode(treeBuilder(array, middleIndex+1, array.length-1, rootNode));
		
	}


	private BSTProc treeBuilder(BSTProc[] array, int startIndex, int endIndex, BSTProc parentNode) {
		
		if (endIndex < startIndex) {
			return null;
		}
		
		int middleIndex = (startIndex + endIndex)/2;
		BSTProc currentNode = array[middleIndex];
		if ( currentNode != null) {
			currentNode.setParentNode(parentNode);
			currentNode.setLeftNode(treeBuilder(array, startIndex, middleIndex-1, currentNode));
			currentNode.setRightNode(treeBuilder(array, middleIndex+1, endIndex, currentNode));
		}
		
		return currentNode;
	}


	private BSTProc[] getProcessAsc(BSTProc[] array, BSTProc currentNode) {
		
		if (currentNode.getLeftNode() != null) {
			array = getProcessAsc(array, currentNode.getLeftNode());
		}
		
		array[index] = currentNode;
		index++;
		
		if (currentNode.getRightNode() != null) {
			array = getProcessAsc(array, currentNode.getRightNode());
		}
		
		return array;
	}
	
	public int getSize() {
		return size;
	}

} // end of class BinarySearchTreeRQ
