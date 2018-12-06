package com.tdtk;


import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//测试三种并发集合的读写效率
public class ConcurrentHashMapTest {

    public static void main(String[] args) {
    
//        System.out.println("abcdef");
        //HashMap不是线程安全的,通过 Collections.synchronizedMap()转换成线程安全的.
        final Map<Integer, Integer> hm = Collections.synchronizedMap(new HashMap<Integer, Integer>());
        //HashTable内部自带同步,线程安全的.
        final Map<Integer, Integer> ht = new Hashtable<Integer, Integer>();
        //JDK1.5之后提供的并发集合.
        final Map<Integer, Integer> chm = new ConcurrentHashMap<Integer, Integer>();
        putMap(hm,"hm");//
        putMap(ht,"ht");//
        putMap(chm,"chm");//数据量达到一定程度之后,会比前两种快3~4倍左右.
//        //TODO JDK1.8
////      8312
////      7053
////      6712
//        //TODO JDK1.7
////      8672
////      8332
////      6842

    }

    private static void putMap(final Map<Integer, Integer> hm ,String str) {
        long begin = System.currentTimeMillis();
        for (int k = 0; k < 100; k++) {//为了让效果更明显,再循环100次.
            for (int i = 0; i < 1000; i++) {//1000条线程
                final int key = i;
                new Thread(new Runnable() {
                    public void run() {
                        for (int j = 0; j < 1000; j++) {//每条线程向其中添加1000次
                            hm.put(key, j);
                            System.out.println(" 测试 :" +key);
                        }
                    }
                }).start();
            }
        }
        long end = System.currentTimeMillis();
        System.out.println(str+ ":" + (end - begin));
    }
}

