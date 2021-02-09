package com.zyrox.task;


import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public final class TaskManager implements Runnable {

	private final ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
	
	private final Queue<Task> pendingTasks = new LinkedList<Task>();

	private final List<Task> activeTasks = new LinkedList<Task>();

	public TaskManager() {
		service.scheduleAtFixedRate(this, 0, 600, TimeUnit.MILLISECONDS);
	}

	@Override
	public void run() {
		try {
			Task t;
			while ((t = pendingTasks.poll()) != null) {
				if (t.isRunning()) {
					activeTasks.add(t);
				}
			}

			Iterator<Task> it = activeTasks.iterator();

			while (it.hasNext()) {
				t = it.next();
				if (!t.tick())
					it.remove();
			}
		} catch(Throwable e) {
			e.printStackTrace();
		}
	}

	public void submit(Task task) {
		if(!task.isRunning())
			return;
		if (task.isImmediate()) {
			task.execute();
		}
		pendingTasks.add(task);
	}
}
