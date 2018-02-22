package com.cos.common.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author qi
 */
public final class PrimaryKeyGenerator
{
    private static final long ONE_STEP = 100;

    private static long lastTime = System.currentTimeMillis();

    private static short count = 0;

    public static Map<String, String> tableMap = new HashMap<String, String>()
    {
        {
        	 put("sysaccount", "1");
        	 put("topic", "9");
        	 put("entry", "10");
        	 put("img", "22");
        	 put("comment", "8");
        	 put("reply", "7");
        	 put("grade", "6");
        	 put("fans", "5");
        	 put("topictotag", "3");
        	 put("collect_topic", "2");
        	 put("sysemail", "11");
        	 put("fansgroup", "12");
        	 put("fullindex", "13");
        	 put("imgfile", "15");
        	 put("basedictionary", "16");
        }
    };

  
    static public synchronized Long getPrimaryKey(String tablename)
    {
        try
        {
            if (count == ONE_STEP)
            {
                boolean done = false;
                while (!done)
                {
                    long now = System.currentTimeMillis();
                    if (now == lastTime)
                    {
                        try
                        {
                            Thread.sleep(1);
                        }
                        catch (java.lang.InterruptedException e)
                        {
                        }
                        continue;
                    }
                    else
                    {
                        lastTime = now;
                        count = 0;
                        done = true;
                    }
                }
            }
        }
        catch (Exception e)
        {
            throw new RuntimeException(e.getMessage());
        }
        String formatCount = "";
        count++;
        if (count < 10)
        {
            formatCount = "0" + count;
        }
        else
        {
            formatCount = Short.toString(count);
        }
        String result = tableMap.get(tablename) + lastTime/2000 + "" + formatCount;
        return Long.parseLong(result);
    }
}
