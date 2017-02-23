import java.util.concurrent.TimeUnit;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * Curator 2.x.x - compatible with both ZooKeeper 3.4.x and ZooKeeper 3.5.x
 *
 * <p>Curator 3.x.x - compatible only with ZooKeeper 3.5.x and includes support for new features
 * such as dynamic reconfiguration, etc. Created by xuwuqiang on 2017/2/23.
 */
public class CuratorTest {

  private static String lockPath = "/mypath/curator";
  private static long maxWait = 10;
  private static TimeUnit waitUnit = TimeUnit.SECONDS;

  public static void main(String[] args) throws Exception {
    RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
    CuratorFramework client = CuratorFrameworkFactory.newClient("localhost:2181", retryPolicy);
    client.start();
    client.blockUntilConnected();
    client.create().creatingParentsIfNeeded().forPath(lockPath, "locked?".getBytes());
    InterProcessMutex lock = new InterProcessMutex(client, lockPath);
    if (lock.acquire(maxWait, waitUnit)) {
      try {
        // do some work inside of the critical section here
      } finally {
        lock.release();
      }
    }
  }
}
