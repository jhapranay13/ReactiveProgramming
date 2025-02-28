package com.rp.sec10BatchingWindowingGrouping.groupby;

import com.rp.common.Util;

public record PurchaseOrder(String item, String category, Integer price) {

    public static PurchaseOrder create() {
        var commerce = Util.getFaker().commerce();
        return new PurchaseOrder(commerce.productName(), commerce.department(), Util.getFaker().random().nextInt(10, 100));
    }
}
