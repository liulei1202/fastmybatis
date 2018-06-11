package com.myapp.common.util;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 线程处理
 * 
 * @author tanghc
 */
public class ExecutorUtil {

	private ExecutorUtil() {
	}

	private static class SingletonHolder {
		private static final ExecutorService INSTANCE = Executors.newCachedThreadPool();
	}

	/**
	 * 返回线程池对象
	 * 
	 * @author tanghc
	 * @return
	 */
	public static final ExecutorService getExecutor() {
		return SingletonHolder.INSTANCE;
	}

	/**
	 * 将任务添加到线程池中处理
	 * 
	 * @author tanghc
	 * @param runnable
	 */
	public static void execute(Runnable runnable) {
		getExecutor().execute(runnable);
	}
	
	/**
	 * 将任务添加到线程池中处理,并带有返回结果
	 * @author tanghc
	 * @param callable
	 * @return 返回结果
	 * @throws Exception
	 */
	public static <V> V execute(Callable<V> callable) throws Exception {
		Future<V> future = getExecutor().submit(callable);
		return future.get();
	}
}
