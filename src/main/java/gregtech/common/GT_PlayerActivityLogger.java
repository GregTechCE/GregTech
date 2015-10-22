package gregtech.common;

import gregtech.GT_Mod;
import gregtech.api.util.GT_Log;

import java.util.ArrayList;

public class GT_PlayerActivityLogger
        implements Runnable {
    public void run() {
        try {
            for (; ; ) {
                if (GT_Log.pal == null) {
                    return;
                }
                ArrayList<String> tList = GT_Mod.gregtechproxy.mBufferedPlayerActivity;
                GT_Mod.gregtechproxy.mBufferedPlayerActivity = new ArrayList();
                String tLastOutput = "";
                int i = 0;
                for (int j = tList.size(); i < j; i++) {
                    if (!tLastOutput.equals(tList.get(i))) {
                        GT_Log.pal.println((String) tList.get(i));
                    }
                    tLastOutput = (String) tList.get(i);
                }
                Thread.sleep(10000L);
            }
        } catch (Throwable e) {
        }
    }
}
