package cn.edu.uestc.algorithm.datamining.clusteranalysis.dbscan;

import java.util.*;

/** Created by LCJ on 2016-05-10.*/
@SuppressWarnings("WeakerAccess")
public class DBSCAN {

    private List<ArrayList<Double>> data;
    private LinkedHashMap<ArrayList<Double>, Boolean> dataStatus;
    private double e;
    private int minPts;
    private List<Set<ArrayList<Double>>> clusters;

    DBSCAN(String filename, double e, int minPts)
    {
        this.data = Utils.loadData(filename);
        this.dataStatus = Utils.setDataUnvisited(this.data);
        this.e = e;
        this.minPts = minPts;
        this.clusters = new ArrayList<>();
    }

    /**
     * DBSCAN算法
     *
     * 首先随机(这里直接顺序遍历)选择一个未访问(unvisited)的对象p, 标记p为"visited",
     * 并检查p的e邻域是否至少包含minPts个对象,
     * 若是, 为p创建一个簇tempCluster, 并且把p的e邻域中的所有对象都放到其中(包括p),
     * 对tempCluster中标记为"unvisited"的对象, 标记为"visited", 并检查其e邻域中是否含有至少minPts个对象,
     * 若是则将其e邻域中的所有对象加入到tempCluster中, 如此迭代, 直到tempCluster不再增大为止。
     * 然后继续随机选择下一个(这里就是顺序遍历下一个)未访问(unvisited)的对象, 聚类过程如上, 直到所有数据库中的对象都被访问.
     *
     * */
    public void DBSCAN_Execution()
    {
        for (ArrayList<Double> everyData : data)
        {
            if (!dataStatus.get(everyData))
            {
                dataStatus.put(everyData, true);
                Set<ArrayList<Double>> tempCluster = Utils.find_e_neighborhood(everyData, e, data);
                if (tempCluster.size() >= minPts)
                {
                    int oldSize = 0;    // 记录tempCluster之前的大小, 以判断其是否增大作为聚类过程收敛的标志
                    Set<ArrayList<Double>> copy = new HashSet<>();
                    while (tempCluster.size() > oldSize) {
                        oldSize = tempCluster.size();
                        copy.addAll(tempCluster);
                        copy.stream().filter(tc -> !dataStatus.get(tc)).forEach(tc -> {
                            dataStatus.put(tc, true);
                            Set<ArrayList<Double>> temp = Utils.find_e_neighborhood(tc, e, data);
                            if (temp.size() >= minPts) {
                                tempCluster.addAll(temp);
                            }
                        });
                    }
                    clusters.add(tempCluster);
                }
            }
        }
        System.out.println(data);
        System.out.println(dataStatus);
        System.out.println(clusters);
    }

    @SuppressWarnings("unused")
    public int getMinPts() {
        return minPts;
    }

    @SuppressWarnings("unused")
    public List<Set<ArrayList<Double>>> getClusters() {
        return clusters;
    }

    @SuppressWarnings("unused")
    public double getE() {
        return e;
    }

    @SuppressWarnings("unused")
    public LinkedHashMap<ArrayList<Double>, Boolean> getDataStatus() {
        return dataStatus;
    }

    @SuppressWarnings("unused")
    public List<ArrayList<Double>> getData() {
        return data;
    }
}
