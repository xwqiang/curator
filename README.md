ttps://curator.apache.org/curator-recipes/index.html

curator recipes demos

lock:
竞争方断开链接，锁将被释放
barrier:
如果不removeBarrier 将会一直阻塞，断开链接也不行。
可以应用于模块间的依赖（A必须完成 B才能执行）
Queue:
distributedQueue

About version:
>>Curator 2.x.x - compatible with both ZooKeeper 3.4.x and ZooKeeper 3.5.x
  Curator 3.x.x - compatible only with ZooKeeper 3.5.x and includes support for new features
  such as dynamic reconfiguration, etc. 

official code:https://git-wip-us.apache.org/repos/asf?p=curator.git;a=tree;f=curator-examples/src/main/java;h=1c5db31735585ed37656e281cd36f4b09fddceb8;hb=HEAD
