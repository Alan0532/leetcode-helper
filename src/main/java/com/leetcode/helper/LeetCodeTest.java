package com.leetcode.helper;

import com.leetcode.helper.model.intnode.ListNode;

/*

leetcode-editor\src\main\java\com

${question.content}

package com.leetcode.editor.cn;

import com.leetcode.helper.LeetCodeHelper;
import com.leetcode.helper.model.intnode.ListNode;
import com.leetcode.helper.model.intnode.TreeNode;

import java.util.*;

//${question.title}
public class $!velocityTool.camelCaseName(${question.titleSlug}) {

    public static void main(String[] args) {
        LeetCodeHelper.code("输入");
    }

    ${question.code}
}
*/
public class LeetCodeTest {
    public static void main(String[] args) {
        LeetCodeHelper.code("l1 = [1,2,4], l2 = [1,3,4]");
    }

//leetcode submit region begin(Prohibit modification and deletion)

    /**
     * Definition for singly-linked list.
     * public class ListNode {
     * int val;
     * ListNode next;
     * ListNode() {}
     * ListNode(int val) { this.val = val; }
     * ListNode(int val, ListNode next) { this.val = val; this.next = next; }
     * }
     */
    class Solution {
        public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
            if (l1 == null) {
                return l2;
            } else if (l2 == null) {
                return l1;
            } else if (l1.val < l2.val) {
                l1.next = mergeTwoLists(l1.next, l2);
                return l1;
            } else {
                l2.next = mergeTwoLists(l1, l2.next);
                return l2;
            }
        }
    }
//leetcode submit region end(Prohibit modification and deletion)
}

