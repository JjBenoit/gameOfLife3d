package gameOfLife3d;

import java.util.concurrent.ForkJoinPool;

import util.CacheResult;

public class TestCache
{
    public static void main(String[] args)
    {
        CacheResult<String, String> cache = new CacheResult<>();
        cache.startCleaningInspector(2, 100, 2);

        ForkJoinPool.commonPool().submit(() -> {
            for (int i = 0; i < 1000; i++)
            {
                cache.add("" + i, "" + (i + 1));
            }
        });

        ForkJoinPool.commonPool().submit(() -> {
            for (int i = 0; i < 1000; i++)
            {
                cache.get("" + i);
            }
        }).join();

    }

    public static void main1(String[] args)
    {
        CacheResult<String, String> cache = new CacheResult<>();

        cache.add("1111100000011111", "111000111");
        cache.add("1111100000011111", "111000111");
        String res1 = cache.get("1111100000011111");
        String res2 = cache.get("1111100000011111");

    }
}
