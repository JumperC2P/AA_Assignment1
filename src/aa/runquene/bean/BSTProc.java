package aa.runquene.bean;

/**
 * @author Chih-Hsuan Lee <s3714761>
 *
 */
public class BSTProc extends Proc {
	
	private BSTProc parentNode;
	private BSTProc leftNode;
	private BSTProc rightNode;
	
	public BSTProc(String procLabel, int vt) {
		this(procLabel, vt, null, null, null);
	}
	
	public BSTProc(String procLabel, int vt, BSTProc parentNode, BSTProc leftNode, BSTProc rightNode) {
		super.setProcLabel(procLabel);
		super.setVt(vt);
		this.setParentNode(parentNode);
		this.setLeftNode(leftNode);
		this.setRightNode(rightNode);
	}

	public BSTProc getParentNode() {
		return parentNode;
	}

	public void setParentNode(BSTProc parentNode) {
		this.parentNode = parentNode;
	}

	public BSTProc getLeftNode() {
		return leftNode;
	}

	public void setLeftNode(BSTProc leftNode) {
		this.leftNode = leftNode;
	}

	public BSTProc getRightNode() {
		return rightNode;
	}

	public void setRightNode(BSTProc rightNode) {
		this.rightNode = rightNode;
	} 

}
