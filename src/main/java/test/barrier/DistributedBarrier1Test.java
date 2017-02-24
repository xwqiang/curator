package test.barrier;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.barriers.DistributedBarrier;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;

/**
 * Created by xuwuqiang on 2017/2/23.
 */
public class DistributedBarrier1Test {
    private static String path = "/mypath/barrier";


    public static void main(String[] args) throws Exception {
        new DistributedBarrier1Test().foo();
    }
    public void foo() throws Exception {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory
            .newClient("localhost:2181", retryPolicy);
        client.start();
        client.blockUntilConnected();
        final DistributedBarrier distributedBarrier = new DistributedBarrier(client,path);
        System.out.println("start");
        //创建屏障节点
        distributedBarrier.waitOnBarrier();
        System.out.println("======屏障已经移除======");
        CloseableUtils.closeQuietly(client);
    }
}
