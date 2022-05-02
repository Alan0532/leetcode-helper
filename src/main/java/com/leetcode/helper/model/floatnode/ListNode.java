package com.leetcode.helper.model.floatnode;

import com.alibaba.fastjson.JSON;
import com.leetcode.helper.model.LeetCodeNode;

import java.util.Iterator;
import java.util.List;

public class ListNode implements LeetCodeNode {

    public float val;

    public ListNode next;

    public ListNode() {
    }

    public ListNode(float val) {
        this.val = val;
    }

    public ListNode(float val, ListNode next) {
        this.val = val;
        this.next = next;
    }

    @Override
    public String toString() {
        ListNode node = this;
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        while (node != null) {
            sb.append(node.val);
            sb.append(',');
            node = node.next;
        }
        sb.setLength(sb.length() - 1);
        sb.append(']');
        return sb.toString();
    }

    @Override
    public LeetCodeNode convert(String parameter) {
        List<Float> list = JSON.parseArray(parameter, Float.class);
        Iterator<Float> iterator = list.iterator();
        ListNode listNode = this;
        while (iterator.hasNext()) {
            listNode.val = iterator.next();
            if (iterator.hasNext()) {
                listNode.next = new ListNode();
                listNode = listNode.next;
            }
        }
        return this;
    }

    @Override
    public LeetCodeNode[] convertArray(String parameter) {
        return null;
    }
}
