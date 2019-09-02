package com.cus.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class AdjustPrice {

	private double adjustedPrice;

	private Product product;

	public AdjustPrice(Product product) {
		this.product = product;
		this.adjustedPrice = 0.0;
	}

	
	public double getAdjustedPrice() {
		String adjustmentMethod = String.format("%sPrice", product.getAdjustmentOperator().toLowerCase());
		try {
			Method method = this.getClass().getMethod(adjustmentMethod, null);
			method.invoke(this, null);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return adjustedPrice;
	}

	public void addPrice() {
		this.adjustedPrice = this.product.getTotalPrice()
				+ (this.product.getTotalQuantity() * this.product.getProductPrice());
	}

	public void subtractPrice() {
		this.adjustedPrice = this.product.getTotalPrice()
				- (this.product.getTotalQuantity() * this.product.getProductPrice());
	}

	public void multiplyPrice() {
		this.adjustedPrice = this.product.getTotalPrice()
				+ (this.product.getTotalPrice() * this.product.getProductPrice())
				+ (this.product.getTotalQuantity() * this.product.getProductPrice());
	}

	public String adjustmentReport() {
		String adjustmentReport = String.format("Performed %s %.2fp to %d %s and price adjusted from %.2fp to %.2fp",
				this.product.getAdjustmentOperator(), this.product.getProductPrice(), this.product.getTotalQuantity(),
				this.product.getProductType(), this.product.getTotalPrice(), this.adjustedPrice);
		return adjustmentReport;
	}

}