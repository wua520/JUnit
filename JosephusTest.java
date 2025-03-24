package test0;

import JosephusSolver0.JosephusSolver;
import junit.Test;

public class JosephusTest {
    @Test
    public void testJosephusWithSmallGroup() {
        int result = JosephusSolver.solveJosephus(5, 2);
        if (result != 3) {
            throw new AssertionError("测试失败: Josephus(5,2) 应该返回 3");}}
    @Test
    public void testJosephusWithLargeGroup() {
        int result = JosephusSolver.solveJosephus(41, 3);
        if (result != 31) {
            throw new AssertionError("测试失败: Josephus(41,3) 应该返回 31");}}
    @Test
    public void testJosephusWithKEquals1() {
        int result = JosephusSolver.solveJosephus(10, 1);
        if (result != 10) {
            throw new AssertionError("测试失败: Josephus(10,1) 应该返回 10");}}
    @Test
    public void testJosephusWithNKEquals() {
        int result = JosephusSolver.solveJosephus(7, 7);
        if (result != 5) {
            throw new AssertionError("测试失败: Josephus(7,7) 应该返回 5");}}
    @Test
    public void testJosephusWithOnePerson() {
        int result = JosephusSolver.solveJosephus(1, 3);
        if (result != 1) {
            throw new AssertionError("测试失败: Josephus(1,3) 应该返回 1");}}
}
