package main;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class ReadTest {

    @BeforeEach
    void setUp() {
        // 初始化公共数据
    }

    private void setGraph1() {
        Read.wordGraph = new ArrayList<>();
        WordNode nodeA = new WordNode("a");
        WordNode nodeB = new WordNode("b");
        WordNode nodeC = new WordNode("c");
        WordNode nodeD = new WordNode("d");
        nodeA.addEdge("a", "b");
        nodeA.addEdge("a", "c");
        nodeB.addEdge("b", "d");
        nodeC.addEdge("c", "d");
        Read.wordGraph.add(nodeA);
        Read.wordGraph.add(nodeB);
        Read.wordGraph.add(nodeC);
        Read.wordGraph.add(nodeD);
        Read.words = new String[]{"a", "b", "c", "d"};
        Read.showDirectedGraph();
    }

    private void setGraph2() {
        Read.wordGraph = new ArrayList<>();
        WordNode nodeA = new WordNode("a");
        WordNode nodeB = new WordNode("b");
        WordNode nodeC = new WordNode("c");
        nodeA.addEdge("a", "b");
        nodeB.addEdge("b", "c");
        Read.wordGraph.add(nodeA);
        Read.wordGraph.add(nodeB);
        Read.wordGraph.add(nodeC);
        Read.words = new String[]{"a", "b", "c"};
        Read.showDirectedGraph();
    }

    private void setGraph3() {
        Read.wordGraph = new ArrayList<>();
        WordNode nodeA = new WordNode("a");
        WordNode nodeB = new WordNode("b");
        WordNode nodeC = new WordNode("c");
        nodeA.addEdge("a", "b");
        nodeB.addEdge("b", "c");
        Read.wordGraph.add(nodeA);
        Read.wordGraph.add(nodeB);
        Read.wordGraph.add(nodeC);
        Read.words = new String[]{"a", "b", "c"};
        Read.showDirectedGraph();
    }

    private void setEmptyGraph() {
        Read.wordGraph = new ArrayList<>();
        Read.words = new String[]{};
        Read.showDirectedGraph();
    }

    private void setSingleGraph() {
        Read.wordGraph = new ArrayList<>();
        WordNode nodeA = new WordNode("a");
        Read.wordGraph.add(nodeA);
        Read.words = new String[]{"a"};
        Read.showDirectedGraph();
    }

    @Test
    void queryBridgeWordsCase1() {
        setGraph1();
        String result = Read.queryBridgeWords("a", "d");
        assertEquals("  b c", result);
    }

    @Test
    void queryBridgeWordsCase2() {
        setGraph2();
        String result = Read.queryBridgeWords("a", "d");
        assertEquals(" ", result);
    }

    @Test
    void queryBridgeWordsCase3() {
        setGraph3();
        String result = Read.queryBridgeWords("x", "c");
        assertEquals(" ", result);
    }

    @Test
    void queryBridgeWordsCase4() {
        setGraph3();
        String result = Read.queryBridgeWords("a", "x");
        assertEquals(" ", result);
    }

    @Test
    void queryBridgeWordsCase5() {
        setGraph3();
        String result = Read.queryBridgeWords("x", "y");
        assertEquals(" ", result);
    }

    @Test
    void queryBridgeWordsCase6() {
        setGraph3();
        String result = Read.queryBridgeWords("", "a");
        assertEquals(" ", result);
    }

    @Test
    void queryBridgeWordsCase7() {
        setEmptyGraph();
        String result = Read.queryBridgeWords("a", "b");
        assertEquals(" ", result);
    }

    @Test
    void queryBridgeWordsCase8() {
        setSingleGraph();
        String result = Read.queryBridgeWords("a", "b");
        assertEquals(" ", result);
    }
}
