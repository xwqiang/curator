package test;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.barriers.DistributedBarrier;
import org.apache.curator.framework.recipes.queue.SimpleDistributedQueue;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;

/**
 * Created by xuwuqiang on 2017/2/23.
 */
public class DistributedQueueTest {
    private static String path = "/mypath/queue";


    public static void main(String[] args) throws Exception {
        new DistributedQueueTest().foo();
    }
    public void foo() throws Exception {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory
            .newClient("localhost:2181", retryPolicy);
        client.start();
        client.blockUntilConnected();
        SimpleDistributedQueue simpleDistributedQueue = new SimpleDistributedQueue(client, path);
        simpleDistributedQueue.offer("addc".getBytes());
        byte[] data = simpleDistributedQueue.take();
        System.out.println(new String(data));
        CloseableUtils.closeQuietly(client);
    }
}
