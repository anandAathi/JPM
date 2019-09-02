package com.cus.processor;

import java.io.BufferedReader;
import java.io.FileReader;

import com.cus.util.Sale;

public class SalesMessageProcessor {
	public static void main(String[] args) {
		Sale sale = new Sale();

		try {
			String line;
			BufferedReader inputFile = new BufferedReader(new FileReader("testInput/input.txt"));
			while ((line = inputFile.readLine()) != null) {
				sale.processNotification(line);

				sale.report();
			}
		} catch (java.io.IOException e) {
			e.printStackTrace();
		}
	}
}