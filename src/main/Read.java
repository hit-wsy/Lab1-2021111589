package main;

import java.io.*;
import java.util.*;

public class Read {


    public static List<WordNode> wordGraph = new ArrayList<>();  //有向图(不含无边节点)
    public static String[] words ;  //全部单词

    private static String dotFormat = new String();

    public static String[] inputWords;  //输入单词列表

    private static Set<String> visitedNodes = new  LinkedHashSet<>();;
    private static Set<Edge> visitedEdges= new  LinkedHashSet<>();;;
    private static Random random = new Random();

    private static String shortestPathText =new String();

    /**
     * 根据bridge word 生成新文本
     * @param inputText
     * @return
     */
    public static String generateNewText(String inputText){
        String newText = " ";
        inputWords = inputText.toLowerCase().split("\\s+");
        if(inputWords.length == 1){
            return "生成新文本:"+ inputWords[0];
        }
        for (int i = 0; i < inputWords.length - 1; i++){
            String currentWord = inputWords[i];
            String nextWord = inputWords[i + 1];
            String bridge = queryBridgeWords(currentWord,nextWord);
            if(bridge.equals(" ")){
                newText += " "+currentWord;
            }else{
                String[] bridgeList = bridge.split("\\s+");
                Random random = new Random();
                int randomIndex = (random.nextInt(bridgeList.length-1))+1;
                String randomBridge =bridgeList[randomIndex];
                newText = newText + " " + currentWord + " " + randomBridge;
            }
            if( i == inputWords.length - 2){
                newText = newText + " " + nextWord;
            }
        }
        return "生成新文本:"+newText;
    }

    /**
     * 查询单词是否存在
     */
    public static int wordExist(String str) {
        int i = -1, j = -1;
        for (WordNode wordNode : wordGraph) {
            i++;
            if (wordNode.getWord().equals(str))
                j = i;
        }
        return j;
    }



    /**
     * 打印菜单
     */
    public static void printMenu() {
        System.out.println("-----请选择功能-----");
        System.out.println("1.展示有向图");
        System.out.println("2.查询桥接词");
        System.out.println("3.根据bridge word生成新文本");
        System.out.println("4.计算两个单词之间的最短路径");
        System.out.println("5.随机游走");
        System.out.println("6.exit");

    }

    /**
     * 打印有向图
     */
    public static void showDirectedGraph() {
        for (WordNode wordNode :wordGraph) {
            if(wordNode.getEdges().size() != 0 && wordNode.getEdges().get(0).getTo()!= null){
                String word = wordNode.getWord();
                System.out.print(word + " -> ");
                for (Edge edge : wordNode.getEdges()) {
                    System.out.print(edge.getTo() + " ");
                    dotFormat += word + " -> " +edge.getTo() + " [label=\"" + edge.getWeight() + "\"]" +";";
                }
            }
            System.out.println();
        }
        createDotGraph(dotFormat,"有向图");
    }

    /**
     * 查询桥接词
     */
    public static String queryBridgeWords(String word1, String word2){
        boolean flag1 = Arrays.asList(words).contains(word1);
        boolean flag2 = Arrays.asList(words).contains(word2);
        String word3 = " ";
        if (flag1 && flag2){
            List<Edge> word1Neighbors = wordGraph.get(wordExist(word1)).getEdges();

            for (Edge neighbor : word1Neighbors) {
                // 查找和相邻单词相邻的单词
                List<Edge> neighborNeighbors = wordGraph.get(wordExist(neighbor.to)).getEdges();
                if(!(neighborNeighbors.size() == 1 && neighborNeighbors.get(0).getTo() == null)){
                    for(Edge neighborNeighbor : neighborNeighbors){
                        if(neighborNeighbor.to.equals(word2.toLowerCase())){
                            word3 += " "+neighbor.to;
                        }
                    }
                }
            }
            if(word3.equals(" "))
            {
                System.out.println(" No bridge words from " + word1 + " to " + word2 + "!");
            }else {
                System.out.println("The bridge words from " + word1 + " to " + word2 + " is" + word3);
            }
        }else if(!flag1 && flag2){
            System.out.println("No " + word1 + " in the graph!");
        } else if (flag1) {
            System.out.println("No " + word2 + " in the graph!");
        }else{
            System.out.println("No " + word1 + " and " + word2 + " in the graph!");
        }
        return word3;
    }

    /**
     * 获取文件文本信息，返回StringBuilder字符串
     *
     * @return
     */
    public static StringBuilder readText() {
        // 文件路径
        String filePath = "test.txt";
        // 用于存储处理后的文本内容的StringBuilder
        StringBuilder cleanedText = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            // 逐行读取文件内容
            while ((line = reader.readLine()) != null) {
                // 替换标点符号和换行符为空格，并将文本转换为小写
                String cleanedLine = line.replaceAll("[^a-zA-Z\\s]", " ").toLowerCase();

                cleanedLine = cleanedLine.replaceAll("\\s+", " ");
                // 将处理后的行内容添加到StringBuilder中
                cleanedText.append(cleanedLine.trim());// 去除行首尾的空格
                cleanedText.append(" "); // 在每行末尾添加一个空格
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
            e.printStackTrace();
        }

        return cleanedText;

    }

    /**
     * 构建有向图
     */
    public static void buildWordGraph(StringBuilder cleanedText) {
        // 将 StringBuilder 转换为字符串
        String cleanedTextStr = cleanedText.toString();
        // 分割字符串为单词数组
        words = cleanedTextStr.split("\\s+");

        // 遍历单词数组，并构建有向图
        for (int i = 0; i < words.length; i++) {
            String currentWord = words[i];
            String nextWord;
            if(i == words.length-1)
                nextWord = null;
            else
                nextWord = words[i + 1];
            // 如果当前单词不在邻接表中，添加到邻接表中
            if (wordExist(currentWord) < 0) {
                wordGraph.add(new WordNode(currentWord));
            }

            //查找边是否存在
            int position = wordExist(currentWord);
            if (wordGraph.get(position).getEdges().size() == 0) {
                wordGraph.get(position).addEdge(currentWord, nextWord);
            } else {
                int j = 0;
                boolean flag = true;
                for (Edge edge : wordGraph.get(position).getEdges()) {
                    j++;
                    if (edge.to.equals(nextWord)){
                        edge.setWeight(edge.getWeight()+1);
                        flag = false;
                    }
                }

                if (flag  && j == wordGraph.get(position).getEdges().size()) {
                    if(nextWord != null || currentWord.equals(Arrays.asList(words).get(words.length-1)))
                        wordGraph.get(position).addEdge(currentWord, nextWord);
                }
            }
        }
    }

    public static String calcShortestPath(String word1, String word2) {
        List<String> visited = new ArrayList<>();
        Map<String, Integer> distances = new HashMap<>();
        Map<String, String> predecessors = new HashMap<>(); // 用于记录每个节点的前驱节点
        for (WordNode node : wordGraph) {
            distances.put(node.getWord(), Integer.MAX_VALUE);
        }
        distances.put(word1, 0);
        visited.add(word1);
        String word = word1;
        int min = Integer.MAX_VALUE;
        for (Edge edge : wordGraph.get(wordExist(word)).getEdges()) {
            distances.put(edge.to, edge.weight);
            predecessors.put(edge.to, word1); // 记录前驱节点
            if (edge.weight < min) {
                min = edge.weight;
                word = edge.getTo();
            }
        }
        visited.add(word);
        for (int i = 1; i < wordGraph.size() - 1; i++) {
            int distance = Integer.MAX_VALUE;
                for (Edge edge : wordGraph.get(wordExist(word)).getEdges()) {
                    if (edge.getTo()!= null && !visited.contains(edge.getTo()) && edge.getWeight() + distances.get(word) < distances.get(edge.to)) {
                        distances.put(edge.to, edge.getWeight() + distances.get(word));
                        predecessors.put(edge.to, word); // 记录前驱节点
                    }
                }

            for (String key : distances.keySet()) {
                if (!visited.contains(key) && distances.get(key) < distance) {
                    distance = distances.get(key);
                    word = key;
                }
            }
            visited.add(word);
        }

        // 构建最短路径
        List<String> shortestPath = new ArrayList<>();
        String currentWord = word2;
        shortestPathText = " ";
        while (currentWord != null) {
            if(predecessors.get(currentWord) != null)
                shortestPathText += predecessors.get(currentWord)+"->"+currentWord+"[color=red];";
            shortestPath.add(currentWord);
            currentWord = predecessors.get(currentWord);
        }
        Collections.reverse(shortestPath); // 因为是从 word2 倒推回来的，需要翻转列表
        createDotGraph(dotFormat+shortestPathText,"有向图");
        return String.join(" -> ", shortestPath);
    }

    public static void calcShortestPath(String word) {
        Map<String, List<String>> shortestPaths = new HashMap<>(); // 存储所有最短路径
        for (WordNode node : wordGraph) {
            if (!node.getWord().equals(word)) {
                String shortestPath = calcShortestPath(word, node.getWord()); // 计算最短路径
                shortestPaths.put(node.getWord(), Arrays.asList(shortestPath.split(" -> "))); // 存储最短路径
            }
        }

        // 逐项展示最短路径
        for (Map.Entry<String, List<String>> entry : shortestPaths.entrySet()) {
            System.out.println("Shortest path from " + word + " to " + entry.getKey() + ": " + String.join(" -> ", entry.getValue()));
        }
    }

    public static void createDotGraph (String dotFormat, String fileName){
        GraphViz gv = new GraphViz();
        gv.addln(gv.start_graph());
        gv.add(dotFormat);
        gv.addln(gv.end_graph());
        String type = "png";
        gv.decreaseDpi();
        gv.decreaseDpi();
        File out = new File(fileName + "." + type);
        gv.writeGraphToFile(gv.getGraph(gv.getDotSource(), type), out);
    }

    public static String randomWalk() {
        visitedNodes.clear();
        visitedEdges.clear();
        // Choose a random starting node
        WordNode currentNode = wordGraph.get(random.nextInt(wordGraph.size()));
        StringBuilder walkPath = new StringBuilder(currentNode.getWord());

        while (true) {
            visitedNodes.add(currentNode.getWord());
            if (currentNode.getEdges().isEmpty())
                break;

            Edge randomEdge = currentNode.getEdges().get(random.nextInt(currentNode.getEdges().size()));

            if (visitedEdges.contains(randomEdge)){
                visitedNodes.add(currentNode.getWord());
                break;
            }
            visitedEdges.add(randomEdge);

            WordNode nextNode = findNode(randomEdge.getTo());
            if (nextNode == null)
                break;

            currentNode = nextNode;
            walkPath.append(" -> ").append(currentNode.getWord());
        }

        return walkPath.toString();
    }

    private static WordNode findNode(String word) {
        for (WordNode node : wordGraph) {
            if (node.getWord().equals(word))
                return node;
        }
        return null;
    }

    public static void writeWalkToFile(String content,String filePath)  {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(content);
            System.out.println("Content has been written to the file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        buildWordGraph(readText());
        int flag;
        String inputText;
        Scanner input = new Scanner(System.in);

        while (true) {
            printMenu();
            flag = input.nextInt();
            switch (flag){
                case 1:
                    showDirectedGraph();
                    break;
                case 2:
                    //查询桥接词
                    System.out.println("请输入两个单词：");
                    String word1 = input.next();
                    String word2 = input.next();
                    queryBridgeWords(word1,word2);
                    break;
                case 3:
                    //生成新文本
                    System.out.println("请输入文本");
                    input.nextLine();
                    inputText = input.nextLine();
                    System.out.println(generateNewText(inputText));
                    break;
                case 4:
                    System.out.println("请输入两个单词：");
                    String word3 = input.next();
                    String word4 = input.next();
                    if(word3.equals("#"))
                        calcShortestPath(word4);
                    else
                        System.out.println(calcShortestPath(word3,word4));
                    break;
                case 5:
                    String walkPath = randomWalk();
                    System.out.println("Random Walk Path: " + walkPath);
                    writeWalkToFile(walkPath,"random_walk.txt");
                    System.out.println("Random Walk Path written to random_walk.txt");
                    break;
                case 6:
                    break;
                default:
                    System.out.println("请输入正确的数字");
            }
        }
    }
}