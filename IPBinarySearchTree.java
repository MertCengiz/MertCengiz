
public class IPBinarySearchTree {
	
	public IPBinarySearchTree () {}
	
	// BST does not hold any additional value than its nodes. 
	// In add method, the value of the iterated node called "parent" is compared with the node that are being looked for called "child".
	// After adding the log message, the transformation between nodes is done recursively.
	// If the value of parent is less than the child, this means that something should be done to the right of the parent.
	// If there are no right child of parent(right child is null), the child is set there, otherwise, the right child is searched.
	// If the value of parent is grater than the child, similar actions are done but instead of right, left children are searched.
	// At the end, the message is returned.
	
	public String addIPDevice(IPDevice parent, IPDevice child, String message) {
		int comparison = parent.getIPAdress().compareTo(child.getIPAdress());
		String line = parent.getIPAdress() +  ": New node being added with IP:" + child.getIPAdress() + "\n";
		message = message.concat(line);		
		if (comparison <= 0) {
			if (parent.getRightDevice() == null) {
				parent.setRightDevice(child);
				child.setParentDevice(parent);
			}
			else if (parent.getRightDevice() != null)
				return addIPDevice(parent.getRightDevice(), child, message);
		}
		else if (comparison > 0) {
			if (parent.getLeftDevice() == null) {
				parent.setLeftDevice(child);
				child.setParentDevice(parent);
			}
			else if (parent.getLeftDevice() != null)
				return addIPDevice(parent.getLeftDevice(), child, message);
		}		
		return message;
	}
	 
	// This time, similar search operations are done to the iterated node(iterDevice). But this time, four different cases are considered.
	// If the removingDevice is a leaf, according to being a left or right child, the child of its parent is set to null respectively.
	// If it is not leaf but there is no left child, this time its right child is set to its parent according to being in left or right.
	// If it is not leaf but there is no right child, this time its left child is set to its parent according to being in left or right.
	// In other cases, the smallest value of the right subtree is searched by going the lefternmost child iteratively. The value of the
	// lefternmost child is set as the value of iterDevice, then its right child is set as left child of the parent of the lefternmost node.
	// At the end, the message is returned.
	
	public String deleteIPDevice(IPDevice iterDevice, IPDevice removingDevice, String message) {
		int comparison = iterDevice.getIPAdress().compareTo(removingDevice.getIPAdress());
		if (comparison < 0) 			
			return deleteIPDevice(iterDevice.getRightDevice(), removingDevice, message);
		else if (comparison > 0) 			
			return deleteIPDevice(iterDevice.getLeftDevice(), removingDevice, message);
		else if (comparison == 0) {
			if (iterDevice.getLeftDevice() == null && iterDevice.getRightDevice() == null) {
				String line = iterDevice.getParentDevice().getIPAdress() +  ": Leaf Node Deleted: " + iterDevice.getIPAdress() + "\n";
				message = message.concat(line);
				if(iterDevice.getParentDevice().getIPAdress().compareTo(iterDevice.getIPAdress()) > 0)
					iterDevice.getParentDevice().setLeftDevice(null);				
				else if(iterDevice.getParentDevice().getIPAdress().compareTo(iterDevice.getIPAdress()) < 0)
					iterDevice.getParentDevice().setRightDevice(null);
			}
			else if (iterDevice.getLeftDevice() != null || iterDevice.getRightDevice() != null) {
				if (iterDevice.getLeftDevice() == null) {
					IPDevice parent = iterDevice.getParentDevice();
					IPDevice child = iterDevice.getRightDevice();
					String line = parent.getIPAdress() + ": Node with single child Deleted: " + iterDevice.getIPAdress() + "\n";					
					message = message.concat(line);
					if (parent.getLeftDevice() != null && parent.getLeftDevice().getIPAdress().equals(iterDevice.getIPAdress())) {
						parent.setLeftDevice(child);
						child.setParentDevice(parent);
					}
					else if(parent.getRightDevice() != null && parent.getRightDevice().getIPAdress().equals(iterDevice.getIPAdress())) {
						parent.setRightDevice(child);
						child.setParentDevice(parent);
					}
				}
				else if(iterDevice.getLeftDevice() != null) {
					IPDevice minDevice = iterDevice.getRightDevice();
					if (minDevice == null) {
						IPDevice parent = iterDevice.getParentDevice();
						IPDevice child = iterDevice.getLeftDevice();
						String line = parent.getIPAdress() + ": Node with single child Deleted: " + iterDevice.getIPAdress() + "\n";
						message = message.concat(line);
						if (parent.getLeftDevice() != null && parent.getLeftDevice().getIPAdress().equals(iterDevice.getIPAdress())) {
							parent.setLeftDevice(child);
							child.setParentDevice(parent);
						}
						else if(parent.getRightDevice() != null && parent.getRightDevice().getIPAdress().equals(iterDevice.getIPAdress())) {
							parent.setRightDevice(child);
							child.setParentDevice(parent);
						}
					}
					else if (minDevice != null) {
						int numberOfIterations = 0;
						while (minDevice.getLeftDevice() != null) {
							minDevice = minDevice.getLeftDevice();
							numberOfIterations++;
						}
						iterDevice.setIPAdress(minDevice.getIPAdress());
						String line = iterDevice.getParentDevice().getIPAdress() +  ": Non Leaf Node Deleted; removed: " + removingDevice.getIPAdress() + " replaced: " + minDevice.getIPAdress() + "\n";
						message = message.concat(line);
						if(numberOfIterations > 0) {
							minDevice.getParentDevice().setLeftDevice(minDevice.getRightDevice());
							if(minDevice.getRightDevice() != null)
								minDevice.getRightDevice().setParentDevice(minDevice.getParentDevice());
						}
						else if(numberOfIterations == 0) {
							minDevice.getParentDevice().setRightDevice(minDevice.getRightDevice());
							if (minDevice.getRightDevice() != null)
								minDevice.getRightDevice().setParentDevice(minDevice.getParentDevice());
						}
					}
				}
			}
		}
		return message;
	}
	
	// The sender and receiver are compared with the iterated device called "parent". If both of the compared values are greater or less than zero
	// this means that parent wouldn't be visited but their right or left children respectively, a recursive function is called to go to the children.
	// If the comparison values are not both greater or less than zero, or parent is sender or receiver, instead of recursion, the sender is tried to
	// find by iteration so that the traversal to the other subtree of parent would be possible. First, according to the value of the comparison,
	// sender is found by iteration. Then, until going back to the parent, going back is done with log message. Then, similar to the going to the 
	// sender, going to receiver operation is done with adding log messages. At the end, the message is returned.
	
	public String sendMessage(IPDevice parent, IPDevice sender, IPDevice receiver, String message) {
		int compareSender = parent.getIPAdress().compareTo(sender.getIPAdress());
		int compareReceiver = parent.getIPAdress().compareTo(receiver.getIPAdress());
		if (compareSender > 0 && compareReceiver > 0)
			return sendMessage(parent.getLeftDevice(), sender, receiver, message);
		else if (compareSender < 0 && compareReceiver < 0)
			return sendMessage(parent.getRightDevice(), sender, receiver, message);
		else {
			IPDevice iterDevice = parent;
			int comparison = iterDevice.getIPAdress().compareTo(sender.getIPAdress());
			while (comparison != 0) {
				if (comparison < 0) {
					iterDevice = iterDevice.getRightDevice();
					comparison = iterDevice.getIPAdress().compareTo(sender.getIPAdress());
				}
				else if (comparison > 0) {
					iterDevice = iterDevice.getLeftDevice();
					comparison = iterDevice.getIPAdress().compareTo(sender.getIPAdress());
				}
			}
			comparison = iterDevice.getIPAdress().compareTo(parent.getIPAdress());
			while (comparison != 0) {
				if (iterDevice.getParentDevice().getIPAdress().equals(receiver.getIPAdress()) == false) {
					String line = iterDevice.getParentDevice().getIPAdress() + ": Transmission from: " + iterDevice.getIPAdress() + " receiver: " + receiver.getIPAdress() + " sender:" + sender.getIPAdress() + "\n";
					message = message.concat(line);
				}
				iterDevice = iterDevice.getParentDevice();
				comparison = iterDevice.getIPAdress().compareTo(parent.getIPAdress());
			}
			comparison = iterDevice.getIPAdress().compareTo(receiver.getIPAdress());
			while (comparison != 0) {
				if (comparison < 0) {
					iterDevice = iterDevice.getRightDevice();
					comparison = iterDevice.getIPAdress().compareTo(receiver.getIPAdress());
				}
				else if (comparison > 0) {
					iterDevice = iterDevice.getLeftDevice();
					comparison = iterDevice.getIPAdress().compareTo(receiver.getIPAdress());
				}
				if (iterDevice.getIPAdress().equals(receiver.getIPAdress()) == false) {
					String line = iterDevice.getIPAdress() + ": Transmission from: " + iterDevice.getParentDevice().getIPAdress() + " receiver: " + receiver.getIPAdress() + " sender:" + sender.getIPAdress() + "\n";
					message = message.concat(line);
				}
			}
		}
		return message;
	}
	
	// For testing, this function is written. This function has no other jobs.
	
	public void printTree(IPDevice device) {
		if (device != null) {
			printTree(device.getLeftDevice());
			System.out.print(device.getIPAdress() + " ");
			printTree(device.getRightDevice());
		}
	}
}
