package com.leetcode.helper.model.intnode;

import com.alibaba.fastjson.JSON;
import com.leetcode.helper.model.LeetCodeNode;

import java.util.Iterator;
import java.util.List;

public class ListNode implements LeetCodeNode {

    public int val;

    public ListNode next;

    public ListNode() {
    }

    public ListNode(int val) {
        this.val = val;
    }

    public ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

    @Override
    public LeetCodeNode convert(String parameter) throws Exception {
        if (parameter.contains(".")) {
            throw new Exception("parameter may have decimal, please use com.leetcode.helper.model.[floatnode or doublenode].ListNode instead");
        }
        List<Integer> list = JSON.parseArray(parameter, Integer.class);
        Iterator<Integer> iterator = list.iterator();
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
}
