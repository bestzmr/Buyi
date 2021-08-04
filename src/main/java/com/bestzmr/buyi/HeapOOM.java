package com.bestzmr.buyi;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Merlin
 * @time: 2021/7/21 17:01
 */
public class HeapOOM {
    static class OOMObject {
    }

    public static void main(String[] args) {
        List<OOMObject> list = new ArrayList<>();
        while (true) {
            list.add(new OOMObject());
        }
    }
}
