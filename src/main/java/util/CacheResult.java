package util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class CacheResult<K, V>
{
    private Map<K, CacheValue> cache = new ConcurrentHashMap<>(1000);

    private Future cleaningInspectorThread;

    public void add(K key, V value)
    {
        cache.putIfAbsent(key, new CacheValue(value));
    }

    public V get(K key)
    {
        CacheValue cacheValue = cache.computeIfPresent(key, (k, v) -> {
            v.nbAccess++;
            v.lastAccess = System.currentTimeMillis();
            return v;
        });

        return cacheValue.value;
    }

    class CacheValue
    {
        V value;

        long nbAccess;

        long lastAccess;

        boolean shouldBeDetroyed;

        public CacheValue(V value)
        {
            super();
            this.value = value;
        }

    }

    /**
     * DOCUMENTEZ_MOI
     *
     * @param period     time between two execution
     * @param ceil       maxSize before trigger the cleaningInspector thread
     * @param lastAccess
     */
    public void startCleaningInspector(long period, long ceil, long lastAccess)
    {

        if (cleaningInspectorThread == null)
            cleaningInspectorThread = Executors.newScheduledThreadPool(1).scheduleAtFixedRate(() -> {
                if (cache.size() > ceil)
                {
                    long now = System.currentTimeMillis();

                    cache.values().parallelStream().forEach((v) -> {
                        if (now - v.lastAccess > lastAccess)
                            v.shouldBeDetroyed = true;
                    });

                    cache.keySet().parallelStream().forEach((k) -> {
                        if (cache.get(k).shouldBeDetroyed)
                            cache.remove(k);
                    });
                }
            }, 0L, period, TimeUnit.SECONDS);
    }

}
