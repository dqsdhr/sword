package net.guohaitao.sword.date;

import javax.annotation.Nonnegative;

/**
 * Created by i@guohaitao.net on 14-9-22.
 * Description: 农历工具类
 */
public final class Lunars {

    private final static String[] ANIMALS = {"鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊", "猴", "鸡", "狗", "猪"};
    private final static long[] LUNAR_INFO = new long[]
            {
                    0x04bd8, 0x04ae0, 0x0a570, 0x054d5, 0x0d260, 0x0d950, 0x16554, 0x056a0, 0x09ad0, 0x055d2,
                    0x04ae0, 0x0a5b6, 0x0a4d0, 0x0d250, 0x1d255, 0x0b540, 0x0d6a0, 0x0ada2, 0x095b0, 0x14977,
                    0x04970, 0x0a4b0, 0x0b4b5, 0x06a50, 0x06d40, 0x1ab54, 0x02b60, 0x09570, 0x052f2, 0x04970,
                    0x06566, 0x0d4a0, 0x0ea50, 0x06e95, 0x05ad0, 0x02b60, 0x186e3, 0x092e0, 0x1c8d7, 0x0c950,
                    0x0d4a0, 0x1d8a6, 0x0b550, 0x056a0, 0x1a5b4, 0x025d0, 0x092d0, 0x0d2b2, 0x0a950, 0x0b557,
                    0x06ca0, 0x0b550, 0x15355, 0x04da0, 0x0a5d0, 0x14573, 0x052d0, 0x0a9a8, 0x0e950, 0x06aa0,
                    0x0aea6, 0x0ab50, 0x04b60, 0x0aae4, 0x0a570, 0x05260, 0x0f263, 0x0d950, 0x05b57, 0x056a0,
                    0x096d0, 0x04dd5, 0x04ad0, 0x0a4d0, 0x0d4d4, 0x0d250, 0x0d558, 0x0b540, 0x0b5a0, 0x195a6,
                    0x095b0, 0x049b0, 0x0a974, 0x0a4b0, 0x0b27a, 0x06a50, 0x06d40, 0x0af46, 0x0ab60, 0x09570,
                    0x04af5, 0x04970, 0x064b0, 0x074a3, 0x0ea50, 0x06b58, 0x055c0, 0x0ab60, 0x096d5, 0x092e0,
                    0x0c960, 0x0d954, 0x0d4a0, 0x0da50, 0x07552, 0x056a0, 0x0abb7, 0x025d0, 0x092d0, 0x0cab5,
                    0x0a950, 0x0b4a0, 0x0baa4, 0x0ad50, 0x055d9, 0x04ba0, 0x0a5b0, 0x15176, 0x052b0, 0x0a930,
                    0x07954, 0x06aa0, 0x0ad50, 0x05b52, 0x04b60, 0x0a6e6, 0x0a4e0, 0x0d260, 0x0ea65, 0x0d530,
                    0x05aa0, 0x076a3, 0x096d0, 0x04bd7, 0x04ad0, 0x0a4d0, 0x1d0b6, 0x0d250, 0x0d520, 0x0dd45,
                    0x0b5a0, 0x056d0, 0x055b2, 0x049b0, 0x0a577, 0x0a4b0, 0x0aa50, 0x1b255, 0x06d20, 0x0ada0
            };

    private final static String[] GAN = new String[]{"甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸"};
    private final static String[] ZHI = new String[]{"子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌", "亥"};

    private Lunars() {
    }


    /**
     * 农历year年的总天数
     *
     * @param lunarYear
     *         农历年
     * @return
     */
    public static int yearDays(@Nonnegative int lunarYear) {
        int i, sum = 348;
        for (i = 0x8000; i > 0x8; i >>= 1) {
            if ((LUNAR_INFO[lunarYear - 1900] & i) != 0) {
                sum += 1;
            }
        }
        return sum + leapDays(lunarYear);
    }

    /**
     * 农历year年闰月的天数
     *
     * @param lunarYear
     * @return
     */
    public static int leapDays(@Nonnegative int lunarYear) {
        if (leapMonth(lunarYear) != 0) {
            return ((LUNAR_INFO[lunarYear - 1900] & 0x10000) != 0) ? 30 : 29;
        } else {
            return 0;
        }
    }

    /**
     * 农历year年闰哪个月 1-12
     * 没闰返回 0
     *
     * @param lunarYear
     * @return
     */
    public static int leapMonth(@Nonnegative int lunarYear) {
        return (int) (LUNAR_INFO[lunarYear - 1900] & 0xf);
    }

    /**
     * 农历year年month月的总天数
     *
     * @param year
     * @param month
     * @return
     */
    public static int monthDays(@Nonnegative int year, @Nonnegative int month) {
        return ((LUNAR_INFO[year - 1900] & (0x10000 >> month)) == 0) ? 29 : 30;
    }

    /**
     * 属相 by year
     *
     * @param lunarYear
     * @return
     */
    public static String animalsYear(@Nonnegative int lunarYear) {
        return ANIMALS[(lunarYear - 4) % 12];
    }

    /**
     * 返回 year对应的干支
     *
     * @param lunarYear
     * @return
     */
    public static String cyclical(@Nonnegative int lunarYear) {
        int num = lunarYear - 1900 + 36;
        return GAN[num % 10] + ZHI[num % 12];
    }


}
