package aa.runquene.bean;

/**
 * @author Chih-Hsuan Lee <s3714761>
 *
 */
public abstract class Proc {
	
	private String procLabel;
	
	private Integer vt;

	public String getProcLabel() {
		return procLabel;
	}

	public void setProcLabel(String procLabel) {
		this.procLabel = procLabel;
	}

	public Integer getVt() {
		return vt;
	}

	public void setVt(Integer vt) {
		this.vt = vt;
	}
	
	public String toString() {
		return procLabel +", " + vt;
	}

}
