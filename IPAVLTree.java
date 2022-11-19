
public class IPAVLTree {
	private IPDevice root;
	
	public IPAVLTree() {}
	
	
	public IPDevice getRoot() {
		return root;
	}
	
	public void assignRoot(IPDevice device) {
		root = device;
	}
	
	// Different from the BST, AVL holds its root value. There is a getter and a setter to get or set the root value in the Main class. 
	// In add method, the value of the iterated node called "parent" is compared with the node that are being looked for called "child".
	// After adding the log message, the transformation between nodes is done recursively.
	// If the value of parent is less than the child, this means that something should be done to the right of the parent.
	// If there are no right child of parent(right child is null), the child is set there, otherwise, the right child is searched.
	// If the value of parent is grater than the child, similar actions are done but instead of right, left children are searched.
	// After doing balancing operation when going up from the recursion, the message is returned.
	
	public String addIPDevice(IPDevice parent, IPDevice child, String message) {
		int comparison = parent.getIPAdress().compareTo(child.getIPAdress());
		String line = parent.getIPAdress() +  ": New node being added with IP:" + child.getIPAdress() + "\n";
		message = message.concat(line);
		if (comparison <= 0) {
			if (parent.getRightDevice() == null) {
				parent.setRightDevice(child);
				child.setParentDevice(parent);
			}
			else if (parent.getRightDevice() != null) { 				
				String methodMessage = addIPDevice(parent.getRightDevice(), child, message);	
				methodMessage = methodMessage.concat(balance(parent));
				return methodMessage;
			}
		}
		else if (comparison > 0) {
			if (parent.getLeftDevice() == null) {
				parent.setLeftDevice(child);
				child.setParentDevice(parent);
			}
			else if (parent.getLeftDevice() != null) {				
				String methodMessage = addIPDevice(parent.getLeftDevice(), child, message);
				methodMessage = methodMessage.concat(balance(parent));
				return methodMessage;
			}
		}		
		return message;
	}
	
	// This time, similar search operations are done to the iterated node(iterDevice). But this time, four different cases are considered.
	// If the removingDevice is a leaf, according to being a left or right child, the child of its parent is set to null respectively.
	// If it is not leaf but there is no left child, this time its right child is set to its parent according to being in left or right.
	// If it is not leaf but there is no right child, this time its left child is set to its parent according to being in left or right.
	// In other cases, the smallest value of the right subtree is searched by going the lefternmost child iteratively. The value of the
	// lefternmost child is set as the value of iterDevice, then its right child is set as left child of the parent of the lefternmost node.
	// Similar to addition method, balancing is done after recursion. But in the iterative parts, balancing is done by going until iterDevice
	// and balancing in every iteration. At the end, the message is returned.
	
	public String deleteIPDevice(IPDevice iterDevice, IPDevice removingDevice, String message) {
		int comparison = iterDevice.getIPAdress().compareTo(removingDevice.getIPAdress());
		if (comparison < 0) {
			String methodMessage = deleteIPDevice(iterDevice.getRightDevice(), removingDevice, message);
			methodMessage = methodMessage.concat(balance(iterDevice));
			return methodMessage;
		}
		else if (comparison > 0) {
			String methodMessage = deleteIPDevice(iterDevice.getLeftDevice(), removingDevice, message);
			methodMessage = methodMessage.concat(balance(iterDevice));
			return methodMessage;
		}
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
					message = message.concat(balance(iterDevice));
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
						message = message.concat(balance(iterDevice));
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
							if (minDevice.getRightDevice() != null)
								minDevice.getRightDevice().setParentDevice(minDevice.getParentDevice());
						}
						else if(numberOfIterations == 0) {
							minDevice.getParentDevice().setRightDevice(minDevice.getRightDevice());
							if (minDevice.getRightDevice() != null)
								minDevice.getRightDevice().setParentDevice(minDevice.getParentDevice());
						}
						while(minDevice.getParentDevice().getIPAdress().equals(iterDevice.getIPAdress()) == false) {
							minDevice = minDevice.getParentDevice();
							message = message.concat(balance(minDevice)); 							
						}
						message = message.concat(balance(minDevice.getParentDevice())); 
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
	// sender, going to receiver operation is done with adding log messages. At the end, the message is returned. No balancing is done here.
	
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
	
	// The balance function looks for whether there is an imbalance. If there is, according to the value of imbalanceCheck of the node and its
	// necessary child, necessary rotation method is called. Then their log message is returned.
	
	public String balance(IPDevice device) {
		String message = "";
		if (imbalanceCheck(device) > 1) {
			if (imbalanceCheck(device.getRightDevice()) >= 0) {
				singleLeftRotation(device);
				String line = "Rebalancing: left rotation" + "\n";
				message = message.concat(line);
			}
			else if (imbalanceCheck(device.getRightDevice()) < 0) {
				rightLeftRotation(device);
				String line = "Rebalancing: right-left rotation" + "\n";
				message = message.concat(line);
			}
		}
		else if (imbalanceCheck(device) < -1) {
			if (imbalanceCheck(device.getLeftDevice()) <= 0) {
				singleRightRotation(device);
				String line = "Rebalancing: right rotation" + "\n";
				message = message.concat(line);
			}
			else if (imbalanceCheck(device.getLeftDevice()) > 0) {
				leftRightRotation(device);
				String line = "Rebalancing: left-right rotation" + "\n";
				message = message.concat(line);
			}
		}
		return message;
	}
	
	// This method finds the height of the node by looking the cases recursively. In general, it finds by finding the maximum height of the left or
	// right subtrees. At the end, the height value is returned.
	
	private int findHeight(IPDevice device) {
		int height = -1;
		if (device == null) // In this case, there is nothing there.
			return height;
		 if (device.getLeftDevice() == null && device.getRightDevice() == null) // In this case, node is a leaf
			 height = 0;
		 else if (device.getLeftDevice() == null) // In this case, node has no left subtree
			 height = 1 + findHeight(device.getRightDevice());
		 else if (device.getRightDevice() == null) // In this case, node has no right subtree 
			 height = 1 + findHeight(device.getLeftDevice());
		 else
			 height = 1 + Math.max(findHeight(device.getRightDevice()), findHeight(device.getLeftDevice()));
		 return height;
	 }	
	
	// This method returns the difference of the heights of the left and right subtrees to help the balance method.
	
	private int imbalanceCheck(IPDevice device) {
		return findHeight(device.getRightDevice()) - findHeight(device.getLeftDevice());
	}
	
	// In single right rotaion, parent and child of the node are found. If the node is root, there will be no parent so there will be no left or 
	// right children. So, if the parent exists, according to being left or right children of the parent, the child is set as a child of parent 
	// directly. The device (the root that is being looked for) is assigned the right child of its child and its left child becomes the right 
	// child of its previous child. No log message is returned in rotations.
	
	private void singleRightRotation(IPDevice device) {
		IPDevice parentOfDevice = device.getParentDevice();
		IPDevice childOfDevice = device.getLeftDevice();
		if (device.equals(root))
			root = childOfDevice;
		else {
			if (parentOfDevice.getLeftDevice() != null && parentOfDevice.getLeftDevice().equals(device)) 
				parentOfDevice.setLeftDevice(childOfDevice);			
			else 
				parentOfDevice.setRightDevice(childOfDevice);			
		}
		childOfDevice.setParentDevice(parentOfDevice);
		device.setLeftDevice(childOfDevice.getRightDevice());
		if (childOfDevice.getRightDevice() != null)
			childOfDevice.getRightDevice().setParentDevice(device);
		childOfDevice.setRightDevice(device);
		device.setParentDevice(childOfDevice);
	}
	
	// In single left rotaion, parent and child of the node are found. If the node is root, there will be no parent so there will be no left or 
	// right children. So, if the parent exists, according to being left or right children of the parent, the child is set as a child of parent 
	// directly. The device (the root that is being looked for) is assigned the left child of its child and its right child becomes the left 
	// child of its previous child. No log message is returned in rotations.
	
	private void singleLeftRotation(IPDevice device) {
		IPDevice parentOfDevice = device.getParentDevice();
		IPDevice childOfDevice = device.getRightDevice();
		if (device.equals(root))
			root = childOfDevice;
		else {
			if (parentOfDevice.getLeftDevice() != null && parentOfDevice.getLeftDevice().equals(device)) 
				parentOfDevice.setLeftDevice(childOfDevice);			
			else
				parentOfDevice.setRightDevice(childOfDevice);			
		}		
		childOfDevice.setParentDevice(parentOfDevice);
		device.setRightDevice(childOfDevice.getLeftDevice());		
		if (childOfDevice.getLeftDevice() != null)
			childOfDevice.getLeftDevice().setParentDevice(device);
		childOfDevice.setLeftDevice(device);		
		device.setParentDevice(childOfDevice);		
	}
	
	// In left-right rotation, parent, child, and grandchild (right child of child) of the node are found. If the node is root, there will be 
	// no parent so there will be no left or right children. So, if the parent exists, according to being left or right children of the parent, 
	// the grandchild is set as a child of parent directly. The left child of the device becomes the right child of grandchild. Similarly,
	// the right child of child becomes the left child of grandchild. Then, the child becomes the left child of the grandchild, and the device
	// becomes the right child of the grandchild. No log messages are returned in rotations.
	
	private void leftRightRotation(IPDevice device) {
		IPDevice parentOfDevice = device.getParentDevice();
		IPDevice childOfDevice = device.getLeftDevice();
		IPDevice grandchildOfDevice = childOfDevice.getRightDevice();
		if(device.equals(root))
			root = grandchildOfDevice;
		else {
			if(parentOfDevice.getLeftDevice() != null && parentOfDevice.getLeftDevice().equals(device))
				parentOfDevice.setLeftDevice(grandchildOfDevice);
			else
				parentOfDevice.setRightDevice(grandchildOfDevice);			
		}
		grandchildOfDevice.setParentDevice(parentOfDevice);
		device.setLeftDevice(grandchildOfDevice.getRightDevice()); 
		if (grandchildOfDevice.getRightDevice() != null)
			grandchildOfDevice.getRightDevice().setParentDevice(device);
		childOfDevice.setRightDevice(grandchildOfDevice.getLeftDevice());
		if (grandchildOfDevice.getLeftDevice() != null)
			grandchildOfDevice.getLeftDevice().setParentDevice(childOfDevice);
		grandchildOfDevice.setLeftDevice(childOfDevice);
		grandchildOfDevice.setRightDevice(device);
		device.setParentDevice(grandchildOfDevice);
		childOfDevice.setParentDevice(grandchildOfDevice);
	}
	
	// In right-left rotation, parent, child, and grandchild (left child of child) of the node are found. If the node is root, there will be 
	// no parent so there will be no left or right children. So, if the parent exists, according to being left or right children of the parent, 
	// the grandchild is set as a child of parent directly. The right child of the device becomes the left child of grandchild. Similarly,
	// the left child of child becomes the right child of grandchild. Then, the child becomes the right child of the grandchild, and the device
	// becomes the left child of the grandchild. No log messages are returned in rotations.
	
	private void rightLeftRotation(IPDevice device) {
		IPDevice parentOfDevice = device.getParentDevice();
		IPDevice childOfDevice = device.getRightDevice();
		IPDevice grandchildOfDevice = childOfDevice.getLeftDevice();
		if(device.equals(root))
			root = grandchildOfDevice;
		else {
			if(parentOfDevice.getLeftDevice() != null && parentOfDevice.getLeftDevice().equals(device))
				parentOfDevice.setLeftDevice(grandchildOfDevice);
			else
				parentOfDevice.setRightDevice(grandchildOfDevice);			
		}
		grandchildOfDevice.setParentDevice(parentOfDevice);
		device.setRightDevice(grandchildOfDevice.getLeftDevice());
		if (grandchildOfDevice.getLeftDevice() != null)
			grandchildOfDevice.getLeftDevice().setParentDevice(device);
		childOfDevice.setLeftDevice(grandchildOfDevice.getRightDevice());
		if (grandchildOfDevice.getRightDevice() != null)
			grandchildOfDevice.getRightDevice().setParentDevice(childOfDevice);
		grandchildOfDevice.setLeftDevice(device);
		grandchildOfDevice.setRightDevice(childOfDevice);
		device.setParentDevice(grandchildOfDevice);
		childOfDevice.setParentDevice(grandchildOfDevice);
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
