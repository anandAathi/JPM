package com.cus.util;

import java.util.ArrayList;
import java.util.HashMap;

public class Sale{
	private AdjustPrice adjustPrice;
	private Product product;
	private HashMap<String, Product> lineItems = new HashMap();
	private double totalSalesValue;
	private ArrayList normalReports;
	private ArrayList adjustmentReports;
	// Constructor
	public Sale() {
		this.normalReports = new ArrayList();
		this.adjustmentReports = new ArrayList();
		this.totalSalesValue = 0.0;
	}

	public boolean processNotification(String saleNotice) {
		MessageParser messageParser = new MessageParser(saleNotice);
		String productType = messageParser.getProductType();
		if (productType.isEmpty()) {
			return false;
		}

		this.product = getProduct(productType);
		this.adjustPrice = new AdjustPrice(product);
		this.product.setProductQuantity(messageParser.getProductQuantity());
		this.product.setTotalQuantity(messageParser.getProductQuantity());
		this.product.setProductPrice(messageParser.getProductPrice());
		this.product.setAdjustmentOperator(messageParser.getOperatorType());

		setProductTotalPrice();
		setNormalReports(saleNotice);
		updateProduct(product);

		return true;
	}

	private void setProductTotalPrice() {
		double adjustedPrice;
		double productValue;

		if (!product.getAdjustmentOperator().isEmpty()) {
			adjustedPrice = adjustPrice.getAdjustedPrice();
			setAdjustmentReports(adjustPrice.adjustmentReport());
			product.setTotalPrice(adjustedPrice);
		} else {
			productValue = product.calculatePrice(product.getProductQuantity(), product.getProductPrice());
			product.appendTotalPrice(productValue);
		}
	}
	
	public Product getProduct(String type) {
		return lineItems.getOrDefault(type, new Product(type));
	}

	public void updateProduct(Product product) {
		lineItems.put(product.getProductType(), product);
	}

	public ArrayList getNormalReports() {
		return normalReports;
	}

	public void setNormalReports(String normalReport) {
		this.normalReports.add(normalReport);
	}

	public ArrayList getAdjustmentReports() {
		return adjustmentReports;
	}

	public void setAdjustmentReports(String adjustmentReport) {
		this.adjustmentReports.add(adjustmentReport);
	}

	public double getTotalSalesValue() {
		return totalSalesValue;
	}

	public void appendTotalSalesValue(double productTotalPrice) {
		totalSalesValue += productTotalPrice;
	}

	public void setTotalSalesValue(double productTotalPrice) {
		totalSalesValue = productTotalPrice;
	}
	
	public void report() {
		// Report after 10th iteration and not at the beginning.
		if ((normalReports.size() % 10) == 0 && normalReports.size() != 0) {
			setTotalSalesValue(0.0);
			// System.out.println(normalReports);
			System.out.println("10 sales appended to log");
			System.out.println("*************** Report *****************");
			System.out.println("Product           Quantity   Value      ");
			lineItems.forEach((k, v) -> formatReports(k, v));
			System.out.println();
			System.out.println(String.format("%-30s%-11.2f", "Total Sales", getTotalSalesValue()));
			System.out.println("\n\n");
			try {
				// Add 2 second pause
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		if ((normalReports.size() % 50) == 0 && normalReports.size() != 0) {
			System.out.println(
					"Application reached 50 messages and cannot process further. The following are the adjustment records made;\n");

			getAdjustmentReports().forEach(System.out::println);
			System.exit(1);
		}
	}
	
	public void formatReports(String type, Product product) {
		String lineItem = String.format("%-18s%-11d%-11.2f", product.getProductType(), product.getTotalQuantity(),
				product.getTotalPrice());
		appendTotalSalesValue(product.getTotalPrice());
		System.out.println(lineItem);
	}

}
