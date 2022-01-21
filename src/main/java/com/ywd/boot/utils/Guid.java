package com.ywd.boot.utils;

import com.google.common.io.BaseEncoding;
import com.ywd.boot.exception.BaseErrorEnum;
import com.ywd.boot.exception.BizException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Random;
import java.util.UUID;

public class Guid {

    public static Long generateId() {
        Long id = 0L;
        try {
            IdWorker idWorker = IdWorker.getFlowIdWorkerInstance();
            id = idWorker.nextId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    public static String generateStrId() {
        return String.valueOf(generateId());
    }

    public static String randomUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String randomBase32UUID() {
        UUID uuid = UUID.randomUUID();
        byte[] uuidBytes = new byte[16];
        ByteBuffer.wrap(uuidBytes)
                .order(ByteOrder.BIG_ENDIAN)
                .putLong(uuid.getMostSignificantBits())
                .putLong(uuid.getLeastSignificantBits());
        return BaseEncoding.base32Hex().omitPadding().encode(uuidBytes);
    }

    public static String random(int n) {

        if (n < 1 || n > 10) {
            throw new BizException(BaseErrorEnum.INNER_LOGIC_ERROR, "cannot random " + n + " bit number");
        }
        Random ran = new Random();
        if (n == 1)
            return String.valueOf(ran.nextInt(10));

        int bitField = 0;
        char[] chs = new char[n];
        for (int i = 0; i < n; i++) {
            while (true) {
                int k = ran.nextInt(10);
                if ((bitField & (1 << k)) == 0) {
                    bitField |= 1 << k;
                    chs[i] = (char) (k + '0');
                    break;
                }
            }
        }
        return new String(chs);
    }

}
