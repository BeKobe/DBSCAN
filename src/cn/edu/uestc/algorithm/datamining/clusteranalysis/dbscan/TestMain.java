package cn.edu.uestc.algorithm.datamining.clusteranalysis.dbscan;

/** Created by LCJ on 2016-05-10.*/
public class TestMain {
    public static void main(String[] args)
    {
        DBSCAN dbscan = new DBSCAN("test.txt", 1, 4);
        dbscan.DBSCAN_Execution();
    }
}
