package com.ymkj.im.thread.notify;

/**
 * wait 和 notify 一般用在同步代码块内持有锁的对象
 * 且调用wait后会释放锁
 */
public class NotifyTest {
    static byte[] b = new byte[0];

    public static void main(String[] args) throws InterruptedException {
        new Thread(()->{
            try {
                testWait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"thread-01").start();
        new Thread(()->{
            try {
                testWait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"thread-02").start();
        new Thread(()->{
            try {
                testWait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"thread-03").start();
        new Thread(()->{
            try {
                testWait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"thread-04").start();
        new Thread(()->{
            try {
                testWait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"thread-05").start();
        new Thread(()->{
            try {
                testWait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"thread-06").start();
        Thread.sleep(2000);
        new Thread(()->{
                testNotify();
            testNotify();
            testNotify();
            testNotify();
            testNotify();
            testNotifyAll();
        },"thread-11").start();
    }

    private static  void testWait() throws InterruptedException {
        synchronized (b) {
            System.out.println(Thread.currentThread().getName() + "开始执行");
            b.wait();
            System.out.println(Thread.currentThread().getName() + "执行完成");
        }
    }

    private static void testNotify(){
        synchronized (b){
            b.notify();
        }
    }

    private static void testNotifyAll(){
        synchronized (b){
            b.notifyAll();
        }
    }

}

/**
 *并非所有的阻塞方法都抛出InterruptedException。输入和输出流类会阻塞等待 I/O 完成，但是它们不抛出InterruptedException，
 * 而且在被中断的情况下也不会提前返回。然而，对于套接字 I/O，如果一个线程关闭套接字，则那个套接字上的阻塞 I/O 操作将提前结束，
 * 并抛出一个SocketException。java.nio中的非阻塞 I/O 类也不支持可中断 I/O，但是同样可以通过关闭通道或者请求Selector上的唤醒来取消阻塞操作。
 * 类似地，尝试获取一个内部锁的操作（进入一个synchronized 块）是不能被中断的，但是ReentrantLock支持可中断的获取模式。
 * 如果一个线程由于同步记性IO操作导致阻塞，中断请求不会抛出InterruptedException，该如何中断此线程呢？
 * public class Test32 extends Thread {
 *     public static final int BUF_SIZE = 512;
 *
 *     Socket socket;
 *     InputStream in;
 *
 *     public Test32(Socket socket) throws IOException {
 *         this.socket = socket;
 *         this.in = socket.getInputStream();
 *     }
 *
 *     @Override
 *     public void interrupt() {
 *         try {
 *             socket.close();
 *         } catch (IOException e) {
 *
 *         } finally {
 *             super.interrupt();
 *         }
 *     }
 *
 *     @Override
 *     public void run() {
 *         try {
 *             byte[] buf = new byte[BUF_SIZE];
 *             while (true) {
 *                 int count = in.read(buf);
 *                 if (count < 0) {
 *                     break;
 *                 } else if (count > 0) {
 *
 *                 }
 *             }
 *         } catch (IOException e) {
 *
 *         }
 *
 *     }
 *     通过改写了Thread.interrupt方法，当调用interrupt方法时，会关闭socket，如果此时read方法阻塞，
 *     那么会抛出IOException异常，此时线程任务也就结束了
 *
 *     处理InterruptedException
 * （1）如果抛出InterrruptedException意味着一个方法是阻塞方法，那么调用一个阻塞方法则意味着你的方法也是一个阻塞方法，应该有某种策略来处理InterrruptedException。通常最容易的策略是自己抛出InterrruptedException，这样做可以使方法对中断做出响应，而且只需将InterruptedException添加到throws子句。
 *
 * 1
 * 2
 * 3
 * 4
 * 5
 * 6
 * 7
 * 8
 * 9
 * 10
 * 11
 * 12
 * 13
 * public class TaskQueue {
 *     private static final int MAX_TASKS = 1000;
 *
 *     private BlockingQueue<Task> queue = new LinkedBlockingQueue<Task>(MAX_TASKS);
 *
 *     public void putTask(Task r) throws InterruptedException {
 *         queue.put(r);
 *     }
 *
 *     public Task getTask() throws InterruptedException {
 *         return queue.take();
 *     }
 * }
 * （2）有时候需要在传播异常之前进行一些清理工作，在这种情况下，可以捕获InterruptedException，执行清理，然后抛出异常。　　
 *
 * 1
 * 2
 * 3
 * 4
 * 5
 * 6
 * 7
 * 8
 * 9
 * 10
 * 11
 * 12
 * 13
 * 14
 * 15
 * 16
 * 17
 * 18
 * 19
 * 20
 * 21
 * 22
 * 23
 * 24
 * 25
 * 26
 * 27
 * public class PlayerMatcher {
 *     private PlayerSource players;
 *
 *     public PlayerMatcher(PlayerSource players) {
 *         this.players = players;
 *     }
 *
 *     public void matchPlayers() throws InterruptedException {
 *         try {
 *              Player playerOne, playerTwo;
 *              while (true) {
 *                  playerOne = playerTwo = null;
 *                  // Wait for two players to arrive and start a new game
 *                  playerOne = players.waitForPlayer(); // could throw IE
 *                  playerTwo = players.waitForPlayer(); // could throw IE
 *                  startNewGame(playerOne, playerTwo);
 *              }
 *          }
 *          catch (InterruptedException e) {
 *              // If we got one player and were interrupted, put that player back
 *              if (playerOne != null)
 *                  players.addFirst(playerOne);
 *              // Then propagate the exception
 *              throw e;
 *          }
 *     }
 * }
 * （3）不要生吞中断：有时候抛出InterruptedException并不合适，例如当由Runnable定义的任务调用一个可中断的方法时，在这种情况下，不能重新抛出InterruptedException，因为Runnable接口的run方法不允许抛出异常。当一个阻塞方法检测到中断并抛出InterruptedException但是不能重新抛出它，那么应该保留中断发生的证据，以便调用栈中更高层的代码能知道中断，并对中断做出响应，该任务可以通过调用interrupt()以重新中断当前线程来完成。
 *
 * public class TaskRunner implements Runnable {
 *     private BlockingQueue<Task> queue;
 *
 *     public TaskRunner(BlockingQueue<Task> queue) {
 *         this.queue = queue;
 *     }
 *
 *     public void run() {
 *         try {
 *              while (true) {
 *                  Task task = queue.take(10, TimeUnit.SECONDS);
 *                  task.execute();
 *              }
 *          }
 *          catch (InterruptedException e) {
 *              // Restore the interrupted status
 *              Thread.currentThread().interrupt();
 *          }
 *     }
 * }　　
 *
 *
 * 实例2
 * package com.huawei.thread;
 *
 * public class Test28 extends Thread {
 *     volatile Boolean stop = false;
 *
 *     public static void main(String[] args) throws InterruptedException {
 *         Test28 t = new Test28();
 *         System.out.println("starting thread...");
 *         t.start();
 *
 *         Thread.sleep(3000);
 *
 *         System.out.println("asking thread to stop...");
 *                 // 必须要在interrupt之前设置
 *         // 如果线程阻塞，将不会检查此变量，调用interrupt之后，线程就可以尽早的终结被阻塞状态，能够检查这一变量
 *         t.stop = true;
 *                 // 如果线程没有被阻塞，这时调用interrupt将不起作用。
 *         // 这一方法实际上完成的是：在线程受到阻塞时抛出一个中断信号，这样线程就可以退出阻塞状态
 *         t.interrupt();
 *
 *         Thread.sleep(3000);
 *         System.out.println("stopping app...");
 *     }
 *
 *     @Override
 *     public void run() {
 *         while (!stop) {
 *             System.out.println("running...");
 *             try {
 *                 Thread.sleep(2000);
 *             } catch (InterruptedException e) {
 *                 System.out.println("interrupted...");
 *             }
 *         }
 *         System.out.println("thread exit...");
 *     }
 *
 * }　　
 * 把握几个重点：stop变量、run方法中的sleep()、interrupt()、InterruptedException。串接起来就是这个意思：
 *
 * 当我们在run方法中调用sleep（或其他阻塞线程的方法）时，如果线程阻塞的时间过长，比如10s，那在这10s内，线程阻塞，run方法不被执行；
 * 但是如果在这10s内，stop被设置成true，表明要终止这个线程；但是，现在线程是阻塞的，它的run方法不能执行，自然也就不能检查stop，所以线程不能终止；
 * 这个时候，我们就可以用interrupt()方法了：我们在thread.stop = true;语句后调用thread.interrupt()方法， 该方法将在线程阻塞时抛出一个中断信号，该信号将被catch语句捕获到，一旦捕获到这个信号，线程就提前终结自己的阻塞状态；
 * 这样，它就能够 再次运行run 方法了，然后检查到stop = true，while循环就不会再被执行，在执行了while后面的清理工作之后，run方法执行完 毕，线程终止。
 * 当代码调用中需要抛出一个InterruptedException，可以选择吧中断状态复位，也可以选在向外抛出InterruptedException，由外层的调用者来决定。
 *
 * 阻塞方法
 * 当一个方法抛出InterruptedException时，它不仅告诉你它可以抛出一个特定的检查异常，而且还告诉你其他一些事情。例如，它告诉你它是一个阻塞（blocking）方法，
 * 如果你响应得当的话，它将尝试消除阻塞并尽早返回。
 * 阻塞方法不同于一般的要运行较长时间的方法。一般方法的完成只取决于它所要做的事情，以及是否有足够多可用的计算资源（CPU 周期和内存）。
 * 而阻塞方法的完成还取决于一些外部的事件，例如计时器到期，I/O 完成，或者另一个线程的动作（释放一个锁，设置一个标志，或者将一个任务放在一个工作队列中）。
 * 一般方法在它们的工作做完后即可结束，而阻塞方法较难于预测，因为它们取决于外部事件。阻塞方法可能影响响应能力，因为难于预测它们何时会结束。
 *
 * 阻塞方法可能因为等不到所等的事件而无法终止，因此令阻塞方法可取消就非常有用（如果长时间运行的非阻塞方法是可取消的，那么通常也非常有用）。
 * 可取消操作是指能从外部使之在正常完成之前终止的操作。由Thread提供并受Thread.sleep()和Object.wait()支持的中断机制就是一种取消机制；
 * 它允许一个线程请求另一个线程停止它正在做的事情。当一个方法抛出InterruptedException时，它是在告诉你，如果执行该方法的线程被中断，
 * 它将尝试停止它正在做的事情而提前返回，并通过抛出InterruptedException表明它提前返回。行为良好的阻塞库方法应该能对中断作出响应并抛出InterruptedException，
 * 以便能够用于可取消活动中，而不至于影响响应。
 **/