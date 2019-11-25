package com.netcracker.models.enums;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public enum CreditStatusPaid {
    YES (new BigInteger("38")),
    NO (new BigInteger("39"));
    private BigInteger id ;

    CreditStatusPaid(BigInteger id) {
        this.id = id;
    }



//    private static final Map<BigInteger, CreditStatusPaid> map;
//    static {
//        map = new HashMap<BigInteger, CreditStatusPaid>();
//        for (CreditStatusPaid c : CreditStatusPaid.values()) {
//            map.put(c.id, c);
//        }
//    }

    public BigInteger getId() {
        return id;
    }

    public static CreditStatusPaid getStatusByKey(BigInteger key) {
        for (CreditStatusPaid c : values())
            if (c.getId().equals(key))
                return c;
            throw new IllegalArgumentException();
    }

//    public CreditStatusPaid getByKey (BigInteger key) {
//        return map.get(key);
//    }
}
