package service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class OrderProcessor {
	ScheduledExecutorService executor = Executors.newScheduledThreadPool(5);
	public void orderExecutor() {
			Process process = new Process();
		executor.scheduleAtFixedRate(()->{
			process.changeStatus();
		}, 0, 60, TimeUnit.SECONDS);
	}
}
