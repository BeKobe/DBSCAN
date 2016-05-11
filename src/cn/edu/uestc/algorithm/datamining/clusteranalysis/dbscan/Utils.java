package cn.edu.uestc.algorithm.datamining.clusteranalysis.dbscan;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/** Created by LCJ on 2016-05-10.*/
@SuppressWarnings("WeakerAccess")
public class Utils {

    /**
     * 从文件中读取数据
     * */
    public static List<ArrayList<Double>> loadData(String filename)
    {
        List<ArrayList<Double>> data = new ArrayList<>();
        File file = new File(filename);
        BufferedReader bf;
        try{
            bf = new BufferedReader(new FileReader(file));
            String str;
            while ((str = bf.readLine()) != null)
            {
                if (Objects.equals(str, ""))
                {
                    continue;
                }
                List<String> tmp = Arrays.asList(str.split("\\t| |,"));
                ArrayList<Double> p =
                        tmp.stream().map(Double::valueOf).collect(Collectors.toCollection(ArrayList::new));
                data.add(p);
            }
            bf.close();
            return data;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return data;
        }
    }

    /**
     * 初始将数据库中的数据全部置为"unvisited"(这里为false)
     * */
    public static LinkedHashMap<ArrayList<Double>, Boolean> setDataUnvisited(List<ArrayList<Double>> data)
    {
        LinkedHashMap<ArrayList<Double>, Boolean> result = new LinkedHashMap<>();
        for (ArrayList<Double> d : data)
        {
            result.put(d, false);
        }
        return result;
    }

    /**
     * 计算两个向量的欧氏距离
     * */
    private static double calcEuclideanDistance(ArrayList<Double> a,
                                               ArrayList<Double> b)
    {
        if (a.size() != b.size())
        {
            throw new IndexOutOfBoundsException("维度不同, 无法计算欧氏距离!\n");
        }
        int size = a.size();
        double sum = 0.0;
        for (int i=0; i<size; i++)
        {
            sum += Math.pow(a.get(i) - b.get(i), 2);
        }
        return Math.sqrt(sum);
    }

    /**
     * 求一个向量的e邻域
     * */
    public static Set<ArrayList<Double>> find_e_neighborhood(ArrayList<Double> target,
                                                              double e,
                                                              List<ArrayList<Double>> data)
    {
        Set<ArrayList<Double>> result = new HashSet<>();
        result.add(target);
        data.stream().filter(d -> d != target).forEach(d -> {
            double distance = Utils.calcEuclideanDistance(d, target);
            if (distance <= e) {
                result.add(d);
            }
        });
        return result;
    }
}
