package com.leetcode.helper.model.floatnode;

import com.alibaba.fastjson.JSON;
import com.leetcode.helper.model.LeetCodeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class TreeNode implements LeetCodeNode {

    public float val;

    public TreeNode left;

    public TreeNode right;

    public TreeNode() {
    }

    public TreeNode(float x) {
        val = x;
    }

    @Override
    public LeetCodeNode convert(String parameter) throws Exception {
        if (parameter.contains(".")) {
            throw new Exception("parameter may have decimal, please use com.leetcode.helper.model.[floatnode or doublenode].LeetCodeNode instead");
        }
        List<String> list = JSON.parseArray(parameter, String.class);
        if (list.size() > 0) {
            String strVal = list.get(0);
            if (strVal != null) {
                this.val = Float.parseFloat(strVal);
                buildLeft(this, 1, list);
                buildRight(this, 1, list);
            }
        }
        return this;
    }

    private void buildLeft(TreeNode parent, int parentIndex, List<String> list) {
        int index = parentIndex * 2;
        if (index <= list.size()) {
            String strVal = list.get(index - 1);
            if (strVal != null) {
                parent.left = new TreeNode(Float.parseFloat(strVal));
                buildLeft(parent.left, index, list);
                buildRight(parent.left, index, list);
            }
        }
    }

    private void buildRight(TreeNode parent, int parentIndex, List<String> list) {
        int index = parentIndex * 2 + 1;
        if (index <= list.size()) {
            String strVal = list.get(index - 1);
            if (strVal != null) {
                parent.right = new TreeNode(Float.parseFloat(strVal));
                buildLeft(parent.right, index, list);
                buildRight(parent.right, index, list);
            }
        }
    }

    @Override
    public LeetCodeNode[] convertArray(String parameter) {
        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        List<Float> list = levelOrder(this);
        for (Float val : list) {
            sb.append(val);
            sb.append(',');
        }
        sb.setLength(sb.length() - 1);
        sb.append(']');
        return sb.toString();
    }

    private List<Float> levelOrder(TreeNode root) {
        List<Float> ret = new ArrayList<>();
        if (root == null) {
            return ret;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            List<Float> level = new ArrayList<>();
            int nullNum = 0;
            for (int i = 1; i <= queue.size(); ++i) {
                TreeNode node = queue.poll();
                if (node == null) {
                    level.add(null);
                    queue.offer(null);
                    queue.offer(null);
                    nullNum++;
                } else {
                    level.add(node.val);
                    queue.offer(node.left);
                    if (node.left == null) {
                        nullNum++;
                    }
                    queue.offer(node.right);
                    if (node.right == null) {
                        nullNum++;
                    }
                }
            }
            if (level.size() != nullNum) {
                ret.addAll(level);
            } else {
                break;
            }
        }
        return ret;
    }

}
