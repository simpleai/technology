//package com.xiaoruiit.knowledge.point.jdk;
//
//import java.util.HashMap;
//
///**
// * @author hanxiaorui
// * @date 2023/7/5
// */
//public class HashMapComment<K,V> extends HashMap<K, V> {
//
//    // put方法入口
//    @Override
//    public V put(K key, V value) {
//        // 计算哈希值
//        return putVal(hash(key), key, value, false, true);
//    }
//
//
//    final V putVal(int hash, K key, V value, boolean onlyIfAbsent,
//                   boolean evict) {
//        Node<K,V>[] tab; Node<K,V> p; int n, i;
//        // 判断数组是否为空，为空的话，则进行扩容初始化
//        if ((tab = table) == null || (n = tab.length) == 0)
//            n = (tab = resize()).length;
//        // 计算数组下标位置，判断下标位置元素是否为null
//        if ((p = tab[i = (n - 1) & hash]) == null)
//            tab[i] = newNode(hash, key, value, null);
//            // 数组中已经存在元素（处理哈希冲突）
//        else {
//            Node<K,V> e; K k;
//            // 判断元素值是否一样，如果一样则替换旧值
//            if (p.hash == hash && ((k = p.key) == key || (key != null && key.equals(k))))
//                e = p;
//            else if (p instanceof TreeNode)// 判断元素类型是否是红黑树
//                e = ((TreeNode<K,V>)p).putTreeVal(this, tab, hash, key, value);// 执行红黑树新增逻辑
//            else {// 不是红黑树类型则说明是链表
//                // 遍历链表
//                for (int binCount = 0; ; ++binCount) {
//                    // 到达链表的尾部
//                    if ((e = p.next) == null) {
//                        // 在尾部插入新结点
//                        p.next = newNode(hash, key, value, null);
//                        // 链表结点数量达到阈值(默认为 8 )，执行 treeifyBin 方法
//                        // 这个方法会根据 HashMap 数组来决定是否转换为红黑树。
//                        // 只有当数组长度大于或者等于 64 的情况下，才会执行转换红黑树操作，以减少搜索时间。否则，就是只是对数组扩容。
//                        if (binCount >= TREEIFY_THRESHOLD - 1)
//                            treeifyBin(tab, hash);
//                        break;
//                    }
//                    // 判断链表中结点的key值与插入的元素的key值是否相等
//                    if (e.hash == hash &&
//                            ((k = e.key) == key || (key != null && key.equals(k))))
//                        // 相等，跳出循环
//                        break;
//                    // 用于遍历桶中的链表，与前面的e = p.next组合，可以遍历链表
//                    p = e;
//                }
//            }
//            // 表示在数组中找到key值、哈希值与插入元素相等的结点
//            if (e != null) {
//                // 记录e的value
//                V oldValue = e.value;
//                // onlyIfAbsent为false或者旧值为null
//                if (!onlyIfAbsent || oldValue == null)
//                    //用新值替换旧值
//                    e.value = value;
//                // 访问后回调
//                afterNodeAccess(e);
//                // 返回旧值
//                return oldValue;
//            }
//        }
//        // 记录修改次数
//        ++modCount;
//        // 元素个数大于阈值则扩容
//        if (++size > threshold)
//            resize();
//        // 插入后回调
//        afterNodeInsertion(evict);
//        return null;
//    }
//}
