package com.clear.web;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Callable接口测试
 *
 * @author leizhimin 2008-11-26 9:20:13
 */
public class CallableDemo {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        List<Future<String>> resultList = new ArrayList<Future<String>>();
        //创建10个任务并执行
        for (int i = 0; i < 10; i++) {
            //使用ExecutorService执行Callable类型的任务，并将结果保存在future变量中
            Future<String> future = executorService.submit(new TaskWithResult(i));
            //将任务执行结果存储到List中
            resultList.add(future);
        }
        for (int i = 0; i < 9; i++) {
            Future<?> submit = executorService.submit(new TaskRunable(i));
        }
        //遍历任务的结果
        for (Future<String> fs : resultList) {
            try {
                System.out.println(fs.get());     //打印各个线程（任务）执行的结果
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } finally {
                //启动一次顺序关闭，执行以前提交的任务，但不接受新任务。如果已经关闭，则调用没有其他作用。
                executorService.shutdown();
            }
        }
    }
}

class TaskRunable implements Runnable {

    private int i;

    public TaskRunable(int i) {
        this.i = i;
    }

    @Override
    public void run() {
        System.out.println("---------------------------------" + i);
    }
}

class TaskWithResult implements Callable<String> {
    private int id;

    public TaskWithResult(int id) {
        this.id = id;
    }

    /**
     * 任务的具体过程，一旦任务传给ExecutorService的submit方法，则该方法自动在一个线程上执行。
     *
     * @return
     * @throws Exception
     */
    public String call() throws Exception {
        System.out.println("call()方法被自动调用,干活！！！             " + Thread.currentThread().getName());
        //一个模拟耗时的操作
        for (int i = 999999; i > 0; i--) ;
        return "call()方法被自动调用，任务的结果是：" + id + "    " + Thread.currentThread().getName();
    }
}