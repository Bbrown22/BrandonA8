package com.coderscampus.assignment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Task {
	 private Assignment8 assignment;
	 private ExecutorService executor;

	 public Task(Assignment8 assignment, int threadCount) {
	 this.assignment = assignment;
	 this.executor = Executors.newFixedThreadPool(threadCount);
	 }
	 
	 public CompletableFuture<List<Integer>> getNumbersAsync() {
		 return CompletableFuture.supplyAsync(assignment::getNumbers,executor);
	 }
	 public void shutdown() {
	     executor.shutdown();
	 }
	 
	
	 public void getData() throws InterruptedException, ExecutionException {
		    Assignment8 assignment = new Assignment8();
		    Task task = new Task(assignment, 10);

		    ConcurrentHashMap<Integer, Integer> frequencyMap = new ConcurrentHashMap<>();

		    List<CompletableFuture<Void>> futures = new ArrayList<>();
		    for (int i = 0; i < 1000; i++) {
		        CompletableFuture<Void> future = task.getNumbersAsync().thenAccept(numbersList -> {
		            for (Integer number : numbersList) {
		                frequencyMap.merge(number, 1, Integer::sum);
		            }
		        });
		        futures.add(future);
		    }

		    futures.stream().forEach(CompletableFuture::join);

		    task.shutdown();

		    for (int i = 1; i <= 10; i++) {
		        System.out.println(i + "=" + frequencyMap.getOrDefault(i, 0));
		    }
		}
}
