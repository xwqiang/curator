import java.util.concurrent.TimeUnit;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.joda.time.LocalDateTime;

class Worker{
    private String lockPath ;
    private InterProcessMutex lock ;
    private String name;
    public Worker(CuratorFramework client,String lockPath, String name){
        this.name = name;
        this.lockPath = lockPath;
        this.lock =  new InterProcessMutex(client, this.lockPath);
    }

    public void doWork() throws Exception {
        if (!lock.acquire(100, TimeUnit.SECONDS)) {
            throw new IllegalStateException( name + " could not acquire the lock");
        }
        try {
            System.out.println(LocalDateTime.now().toString("HH:mm:ss") + name + ": get lock");
            Thread.sleep((long) (3 * Math.random()));
        }finally {
            lock.release();// lock.isAcquiredInThisProcess();
        }
    }
}