package priv.time;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class Zone {
    /**
     * 计算当前时区相对于utc+0时区的小时偏移
     * @return
     */
    public static int calZoneHeroOffset() {
        // 获取当前时间
        ZonedDateTime now = ZonedDateTime.now();
        // 获取当前时区偏移量
        ZoneOffset offset = now.getOffset();
        // 计算偏移小时数
        int offsetInHours = offset.getTotalSeconds() / 3600;
        return offsetInHours;
    }

    public static void main(String[] args) {
        System.out.println(calZoneHeroOffset());
    }
}
