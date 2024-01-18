package com.coderscampus.assignment;

import java.util.concurrent.ExecutionException;

public class Main {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		Task runCode = new Task(new Assignment8(), 10);

		runCode.getData();
	}
}