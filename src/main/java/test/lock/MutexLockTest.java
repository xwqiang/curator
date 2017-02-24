package test.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;

/**
 * Curator 2.x.x - compatible with both ZooKeeper 3.4.x and ZooKeeper 3.5.x
 *
 * <p>Curator 3.x.x - compatible only with ZooKeeper 3.5.x and includes support for new features
 * such as dynamic reconfiguration, etc. Created by xuwuqiang on 2017/2/23.
 */
public class MutexLockTest {

    private static final int QTY = 30;
    private static String lockPath = "/mypath/curator";

    public static void main(String[] args) throws Exception {
        new MutexLockTest().foo();
    }

    public void foo() throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(QTY);
        for (int i = 0; i < QTY; i++) {
            final int index = i;
            service.submit(new Runnable() {
                @Override
                public void run() {
                    CuratorFramework client = null;
                    try {
                        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
                        client = CuratorFrameworkFactory
                            .newClient("localhost:2181", retryPolicy);
                        client.start();
                        client.blockUntilConnected();
                        Worker worker = new Worker(client, lockPath, "thread:" + index);
                        for (int i = 0; i < 20; i++) {
                            worker.doWork();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        CloseableUtils.closeQuietly(client);
                    }
                }
            });
        }
        service.shutdown();
        service.awaitTermination(10, TimeUnit.MINUTES);
    }

}
