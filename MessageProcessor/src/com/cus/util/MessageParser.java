package com.cus.util;

public class MessageParser {

	private String productType;

	private double productPrice;

	private int productQuantity;

	private String operatorType;

	public MessageParser(String message) {
		this.productType = "";
		this.productPrice = 0.0;
		this.productQuantity = 0;
		this.operatorType = "";
		parseMessage(message);
	}

	private boolean parseMessage(String message) {
		if (message == null || message.isEmpty()) {
			return false;
		}
		String[] messageArray = message.trim().split("\\s+");
		String firstWord = messageArray[0];
		if (firstWord.matches("Add|Subtract|Multiply")) {
			return parseMessageType3(messageArray);
		} else if (firstWord.matches("^\\d+")) {
			return parseMessageType2(messageArray);
		} else if (messageArray.length == 3 && messageArray[1].contains("at")) {
			return parseMessageType1(messageArray);
		} else {
			System.out.println("Wrong sales notice");
		}
		return true;
	}

	// message type 1
	private boolean parseMessageType1(String[] messageArray) {
		if (messageArray.length > 3 || messageArray.length < 3)
			return false;
		productType = messageArray[0];
		productPrice = Double.parseDouble(messageArray[2]);
		productQuantity = 1; // Will always be 1
		return true;
	}

	// message type 2
	private boolean parseMessageType2(String[] messageArray) {
		if (messageArray.length > 7 || messageArray.length < 7)
			return false;
		productType = messageArray[3];
		productPrice = Double.parseDouble(messageArray[5]);
		productQuantity = Integer.parseInt(messageArray[0]);
		return true;
	}

	// message type 3
	private boolean parseMessageType3(String[] messageArray) {
		if (messageArray.length > 3 || messageArray.length < 3)
			return false;
		operatorType = messageArray[0];
		productType = messageArray[2];
		productQuantity = 0;
		productPrice = Double.parseDouble(messageArray[1]);
		return true;
	}

	// Get the product type
	public String getProductType() {
		return productType;
	}

	// Get the product price
	public double getProductPrice() {
		return productPrice;
	}

	// Get the operator type
	public String getOperatorType() {
		return operatorType;
	}

	// Get the product quantity
	public int getProductQuantity() {
		return productQuantity;
	}

}
