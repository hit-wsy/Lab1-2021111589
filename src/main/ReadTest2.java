package test;

import main.Read;
import main.WordNode;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ReadTest {

    @Before
    public void setUp() {
        // 创建测试数据节点
        WordNode nodeA = new WordNode("A");
        WordNode nodeB = new WordNode("B");
        WordNode nodeC = new WordNode("C");
        WordNode nodeD = new WordNode("D");
        WordNode nodeE = new WordNode("E");
        WordNode nodeF = new WordNode("F");
        WordNode nodeG = new WordNode("G");
        WordNode nodeH = new WordNode("H");
        WordNode nodeI = new WordNode("I");
        WordNode nodeJ = new WordNode("J");

        // 设置节点之间的边
        nodeA.addEdge("A", "B");
        nodeA.addEdge("A", "C");
        nodeB.addEdge("B", "D");
        nodeC.addEdge("C", "D");
        nodeC.addEdge("C", "E");
        nodeD.addEdge("D", "F");
        nodeE.addEdge("E", "F");
        nodeF.addEdge("F", "G");
        nodeG.addEdge("G", "H");
        nodeG.addEdge("G", "I");
        nodeH.addEdge("H", "J");
        nodeI.addEdge("I", "J");

        // 设置边的权重
        nodeA.getEdges().get(0).setWeight(1); // A->B 权重为1
        nodeA.getEdges().get(1).setWeight(3); // A->C 权重为3
        nodeB.getEdges().get(0).setWeight(2); // B->D 权重为2
        nodeC.getEdges().get(0).setWeight(2); // C->D 权重为2
        nodeC.getEdges().get(1).setWeight(1); // C->E 权重为1
        nodeD.getEdges().get(0).setWeight(2); // D->F 权重为2
        nodeE.getEdges().get(0).setWeight(1); // E->F 权重为1
        nodeF.getEdges().get(0).setWeight(1); // F->G 权重为1
        nodeG.getEdges().get(0).setWeight(1); // G->H 权重为1
        nodeG.getEdges().get(1).setWeight(2); // G->I 权重为2
        nodeH.getEdges().get(0).setWeight(3); // H->J 权重为3
        nodeI.getEdges().get(0).setWeight(2); // I->J 权重为2

        // 将节点添加到Read类的wordGraph中
        Read.wordGraph = new ArrayList<>();
        Read.wordGraph.add(nodeA);
        Read.wordGraph.add(nodeB);
        Read.wordGraph.add(nodeC);
        Read.wordGraph.add(nodeD);
        Read.wordGraph.add(nodeE);
        Read.wordGraph.add(nodeF);
        Read.wordGraph.add(nodeG);
        Read.wordGraph.add(nodeH);
        Read.wordGraph.add(nodeI);
        Read.wordGraph.add(nodeJ);

        // 设置Read类的单词列表
        Read.words = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
    }

    // 测试从A到J的最短路径
    @Test
    public void testShortestPath_A_to_J() {
        String shortestPath = Read.calcShortestPath("A", "J");
        // 期望结果是通过路径 A -> C -> E -> F -> G -> H -> J 或 A -> B -> D -> F -> G -> H -> J 到达 J
        assertTrue("A -> C -> E -> F -> G -> H -> J".equals(shortestPath) ||
                "A -> B -> D -> F -> G -> H -> J".equals(shortestPath));
    }


    // 测试从B到F的最短路径
    @Test
    public void testShortestPath_B_to_F() {
        String shortestPath = Read.calcShortestPath("B", "F");
        // 期望结果是通过路径B -> D -> F到达F
        assertEquals("B -> D -> F", shortestPath);
    }

    // 测试从C到J的最短路径
    @Test
    public void testShortestPath_C_to_J() {
        String shortestPath = Read.calcShortestPath("C", "J");
        // 期望结果是通过路径C -> E -> F -> G -> H -> J到达J
        assertEquals("C -> E -> F -> G -> H -> J", shortestPath);
    }

    // 测试从D到G的最短路径
    @Test
    public void testShortestPath_D_to_G() {
        String shortestPath = Read.calcShortestPath("D", "G");
        // 期望结果是通过路径D -> F -> G到达G
        assertEquals("D -> F -> G", shortestPath);
    }

    // 测试起点和终点相同的最短路径
    @Test
    public void testShortestPath_E_to_E() {
        String shortestPath = Read.calcShortestPath("E", "E");
        // 期望结果是返回起点自身
        assertEquals("E", shortestPath);
    }

    // 测试从F到I的最短路径
    @Test
    public void testShortestPath_F_to_I() {
        String shortestPath = Read.calcShortestPath("F", "I");
        // 期望结果是通过路径F -> G -> I到达I
        assertEquals("F -> G -> I", shortestPath);
    }
}