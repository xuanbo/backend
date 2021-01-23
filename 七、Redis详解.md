# Redis详解

> Redis is an open source (BSD licensed), in-memory data structure store, used as a database, cache, and message broker. Redis provides data structures such as strings, hashes, lists, sets, sorted sets with range queries, bitmaps, hyperloglogs, geospatial indexes, and streams. Redis has built-in replication, Lua scripting, LRU eviction, transactions, and different levels of on-disk persistence, and provides high availability via Redis Sentinel and automatic partitioning with Redis Cluster.

## 安装部署

Docker 单机安装：

```shell
docker run --name redis -p 6379:6379 -d redis:4.0.14
```

哨兵：

[Redis哨兵（Sentinel）模式](https://www.jianshu.com/p/06ab9daf921d)

集群：

[Redis集群搭建及原理](https://www.cnblogs.com/yufeng218/p/13688582.html)

## 五大数据结构

### strings（字符串）

strings 类型是 Redis 最基本的数据类型，一个 key 对应一个 value，strings 类型的值最大能存储 512MB。

```shell
set key value

get key
```

常用来k-v存储，用作缓存。

### lists（列表）

列表是简单的字符串列表，按照插入顺序排序，一个 key 对应一个 value 数组。你可以添加一个元素到列表的头部（左边）或者尾部（右边）。最多可存储 2^32 - 1 元素 (4294967295, 每个列表可存储40多亿)。

```shell
lpush key value1
lpush key value2
rpush key value3

lrange key 0 -1
```

常用来模拟队列、栈。

### sets（集合）

sets 是 strings 类型的无序不重复集合，集合中最大的成员数为 2^32 - 1(4294967295, 每个集合可存储40多亿)

```shell
sadd key value1
sadd key value2
sadd key value3
sadd key value1

smembers key
```

去重、多个集合找出相同的值

### zset（sorted set、有序集合）

zset 与 sets 一样，不同的是，每个值有一个分数，可通过该分数进行排序。

```shell
zadd key 10 value1
zadd key 20 value2
zadd key 50 value3
zadd key 100 value4

zrangebyscore key 10 60
```

去重、排序

### hashes（哈希）

hashes 一个 key 对应一个 hash 表，每个 hash 最大的成员数为 2^32 - 1(4294967295, 每个集合可存储40多亿)

```shell
hmset key name "zhagsan" age "22"

hget key name
hget key age
```

值中进行 key value 查找

## Java客户端

### 纯粹的客户端

- jedis
- lettuce

专注于客户端，对于 spring 项目建议直接使用 spring data redis 模块，底层可更换上述两者实现，从 2.0 开始 lettuce 成为默认。

### 多功能客户端

- redisson

除了客户端外，提供更多基于 redis 实现的功能：

- 发布订阅（pub-sub）
- 分布式锁
- rpc
- 选举
- ...

## 缓存

- 读操作

    1. 先查询缓存，如果查询到直接返回
    1. 缓存不存在，查询库，写入库

- 写操作

    1. 先写库
    1. 清理缓存

思考：

- 写入时，为什么不是先清理缓存，再写库

查询和更新并发执行时，如果先删，但是查询并发放入，导致缓存里为脏数据（读多写少使这种情况更容易出现）。

- 写入时，为什么不是写库后，直接更新缓存

更新并发执行时，数据库更新和缓存更新不是原子操作，会出现先更新数据库的后更新缓存，出现时序不一致问题。

当然：

在更新和查询并行的情况下，从数据库中查询出数据准备放置缓存，但是 GC 暂停（或者查询后做了些重操作）；接着更新操作删除缓存，查询恢复放置缓存，极端情况也可能出现脏数据。

解决思路：

1. 根据场景设置合适的缓存过期时间，即使不一致，也只是缓存过期时间内的不一致，过期时间越短，数据一致性越高，但是查数据库就会越频繁。
1. 为了保持时序一致性可以采用版本化或者加锁机制（影响吞吐量）

其说是保证了一致性，不如说我们是在提高缓存一致性。

## 缓存雪崩、缓存穿透、缓存击穿

### 缓存雪崩

#### 雪崩现象

某一时刻发生大规模的缓存失效的情况，或者缓存服务宕机了。会产生大量的请求到数据库去查询，导致数据库由于压力过大而宕掉。

#### 雪崩解决办法

- 不要使用统一过期时间，增加随机过期值
- 缓存请求限流（Hystrix）
- 缓存高可用，使用 redis cluster

### 缓存穿透

#### 穿透现象

正常情况下，我们去查询数据都是存在。

那么请求去查询一条压根儿数据库中根本就不存在的数据，也就是缓存和数据库都查询不到这条数据，但是请求每次都会打到数据库上面去。

这种查询不存在数据的现象我们称为缓存穿透。

#### 穿透带来的问题

试想一下，如果有黑客会对你的系统进行攻击，拿一个不存在的 id 去查询数据，会产生大量的请求到数据库去查询。可能会导致你的数据库由于压力过大而宕掉。

#### 穿透解决办法

1. 缓存空值
1. 布隆过滤

### 缓存击穿

#### 击穿现象

大量的请求同时查询一个 key 时，此时这个 key 正好失效了，就会导致大量的请求都落到数据库上面去。这种现象我们称为缓存击穿。

#### 击穿带来的问题

会造成某一时刻数据库请求量过大，压力剧增。

#### 击穿解决办法

同一个 key 大量读请求排队、限流。

## 原子性

比如，我们需要控制某个操作最多只能同时执行 10 次。

利用 strings 数据结构将总体凭据设置为 10，每次执行时判断凭据是否大于 0，如果不大于 0，说明获取不到了，无法执行。释放凭据时，再加 1。

我们很容易写入这样的代码：

```java
public boolean tryAcquire() {
    Jedis jedis = null;
    try {
        jedis = pool.getResource();
        // 获取当前剩余的凭据数
        String value = jedis.get(key);
        if (current == null) {
            return false;
        }
        Long current = Long.valueOf(value);
        if (current > 0) {
            // 凭据数大于0，则获取成功，减一
            jedis.incr(key);
            return true;
        }
        return false;
    } catch (JedisException e) {
        LOG.error("try acquire error", e);
        return false;
    } finally {
        returnResource(jedis);
    }
}
```

然而 get(key)、incr(key) 不是原子性操作，因此时存在问题的。

由于 redis server 执行 key 操作是单线程的，因此我们可以用 lua 脚本封装多个命令，redis 执行时是原子性的。

```java
/**
 * 基于redis lua实现Semaphore
 *
 * @author xuanbo
 * @version 1.0.0
 */
public class RedisSemaphore implements Semaphore {

    private static final Logger LOG = LoggerFactory.getLogger(Semaphore.class);

    /**
     * redis默认存储的key
     */
    private static final String DEFAULT_KEY = "rate-limit:semaphore";

    /**
     * lua执行脚本，如果大于0，则减一，返回1，代表获取成功
     */
    private static final String SCRIPT_LIMIT =
            "local key = KEYS[1] " +
            "local current = tonumber(redis.call('get', key)) " +
            "local res = 0 " +
            "if current > 0 then " +
            "   redis.call('decr', key) " +
            "   res = 1 " +
            "end " +
            "return res ";

    /**
     * Redis连接池
     */
    private final Pool<Jedis> pool;

    /**
     * redis存储的key
     */
    private final String key;

    /**
     * 凭据限制的数目
     */
    private final Long limits;

    public RedisSemaphore(Pool<Jedis> pool, Long limits) {
        this(pool, DEFAULT_KEY, limits);
    }

    public RedisSemaphore(Pool<Jedis> pool, String key, Long limits) {
        this.pool = pool;
        this.key = key;
        this.limits = limits;
        setup();
    }

    /**
     * 尝试获取凭据，获取不到凭据不等待，直接返回
     *
     * @return 获取到凭据返回true，否则返回false
     */
    @Override
    public boolean tryAcquire() {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            Long res = (Long) jedis.eval(SCRIPT_LIMIT, Collections.singletonList(key), Collections.<String>emptyList();
            return res > 0;
        } catch (JedisException e) {
            LOG.error("try acquire error", e);
            return false;
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 释放获取到的凭据
     */
    @Override
    public void release() {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.incr(key);
        } catch (JedisException e) {
            LOG.error("release error", e);
        } finally {
            returnResource(jedis);
        }
    }

    private void setup() {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.del(key);
            jedis.incrBy(key, limits);
        } finally {
            returnResource(jedis);
        }
    }

    private void returnResource(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }

}
```

## redis其他操作

使用 redisson 库，避免工作中重复造轮子。

### 发布订阅（pub-sub）

集群广播、聊天。

在 spring data redis 中发送消息：

```java
redisTemplate.convertAndSend("simple:topic:city:findById", id);
```

接收消息：

```java
public class PrintingMessageListener implements MessageListener {

    private final Logger logger = LoggerFactory.getLogger(PrintingMessageListener.class);

    @Override
    public void onMessage(Message message, byte[] pattern) {
        byte[] channel = message.getChannel();
        byte[] body = message.getBody();
        logger.info("pattern: {}, channel: {}, body:{}", new String(pattern), new String(channel), new String(body));
    }

}
```

### pipeline

多个命令打包一起发送，减少网络开销。

```java
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class PipelineTest {

    private final Logger logger = LoggerFactory.getLogger(LuaScriptTest.class);

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    public void pipeline() {
        List<Object> values = redisTemplate.executePipelined(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                StringRedisConnection sc = (StringRedisConnection) connection;
                // 执行多个命令
                sc.get("simple:lua");
                sc.get("simple:lua");
                sc.get("simple:lua");
                sc.get("simple:lua");
                sc.get("simple:lua");
                return null;
            }
        });
        // 对结果批量处理
        logger.info("values: {}", values);
    }

}
```

### 分布式锁

分布式锁。

[Distributed locks and synchronizers](https://github.com/redisson/redisson/wiki/8.-Distributed-locks-and-synchronizers)

### 选举

集群选举

[用redisson的分布式锁实现主从选举（leader election）](https://www.jianshu.com/p/786dcb08e2a0?utm_campaign=maleskine&utm_content=note&utm_medium=seo_notes&utm_source=recommendation)
