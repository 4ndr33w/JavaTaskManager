package project.task.manager.notification_service.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author 4ndr33w
 * @version 1.0
 */
@Slf4j
@EnableAsync
@Configuration
public class AsyncConfig {
	
	/**
	 * Thread Pool по рекомендации от Леонида
	 * https://stackoverflow.com/questions/19528304/how-to-get-the-threadpoolexecutor-to-increase-threads-to-max-before-queueing#24493856
	 * Потоки создаются раньше чем задачи отправляются в очередь
	 *
	 * @return
	 */
	@Bean(name = "asyncExecutor")
	public Executor linkedTransferQueueThreadPool() {
		BlockingQueue<Runnable> queue = new LinkedTransferQueue<Runnable>() {
			@Override
			public boolean offer(Runnable e) {
				return tryTransfer(e);
			}
		};
		ThreadPoolExecutor threadPool = new ThreadPoolExecutor(1, 10, 60, TimeUnit.SECONDS, queue);
		threadPool.setRejectedExecutionHandler(new RejectedExecutionHandler() {
			@Override
			public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
				try {
					executor.getQueue().put(r);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
		});
		
		return threadPool;
	}
}