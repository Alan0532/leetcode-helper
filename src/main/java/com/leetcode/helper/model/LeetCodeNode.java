package com.leetcode.helper.model;

public interface LeetCodeNode {

    LeetCodeNode convert(String parameter) throws Exception;

    LeetCodeNode[] convertArray(String parameter);

}
