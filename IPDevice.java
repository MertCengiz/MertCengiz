
public class IPDevice {
	private String IPAdress;
	private IPDevice parentDevice;
	private IPDevice leftDevice;
	private IPDevice rightDevice;
	
	// This class creates the node, which is called "IPDevice". 
	// Every node includes its parent, left and right children and its data(called "IPAdress")
	// There are getters and setters since the data fields are private.
	// toString method is created for testing. There are no other purposes.
	
	public IPDevice(IPDevice parentDevice, IPDevice leftDevice, String IPAdress, IPDevice rightDevice) {
		this.parentDevice = parentDevice;
		this.leftDevice = leftDevice;
		this.IPAdress = IPAdress;
		this.rightDevice = rightDevice;
	}

	public String getIPAdress() {
		return IPAdress;
	}

	public void setIPAdress(String iPAdress) {
		IPAdress = iPAdress;
	}

	public IPDevice getLeftDevice() {
		return leftDevice;
	}

	public void setLeftDevice(IPDevice leftDevice) {
		this.leftDevice = leftDevice;
	}

	public IPDevice getRightDevice() {
		return rightDevice;
	}

	public void setRightDevice(IPDevice rightDevice) {
		this.rightDevice = rightDevice;
	}

	public IPDevice getParentDevice() {
		return parentDevice;
	}

	public void setParentDevice(IPDevice parentDevice) {
		this.parentDevice = parentDevice;
	}

	@Override
	public String toString() {
		return "IPDevice [IPAdress=" + IPAdress + ", parentDevice=" + parentDevice + ", leftDevice=" + leftDevice
				+ ", rightDevice=" + rightDevice + "]";
	}
	
}
