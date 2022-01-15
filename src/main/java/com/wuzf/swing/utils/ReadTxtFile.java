package com.wuzf.swing.utils;

import com.wuzf.swing.demos.T;
import com.wuzf.swing.vo.RowInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;


@Slf4j
public class ReadTxtFile {

    public static String readTxt(String filePath)
    {
      StringBuffer bf = new StringBuffer();
        try
        {
            String encoding = "UTF-8";
            File file = new File(filePath);
            if (file.isFile() && file.exists())
            { // 判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file), encoding);// 考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;

                StringBuffer sb = new StringBuffer();

                String replaceLength = "8";

                String replaceStr = "20220110";

                String placeHolder = "${ph}";

                while ((lineTxt = bufferedReader.readLine()) != null)
                {

                    //INSERT INTO `swing_demo`.`t_customer`(`id`, `username`, `password`, `nick_name`, `role_code`, `jobs`, `phone`, `creat_time`, `last_login_time`)
                    // VALUES ('3333333333333333333', '12666222', '1223322', '12222333333333', '1', NULL, NULL, NULL, NULL);

                    if (lineTxt.contains("INSERT")){
                        String leftvalue = lineTxt.substring(0,lineTxt.indexOf("VALUES (")+7);
                        String valuesStr =  lineTxt.substring(lineTxt.indexOf("VALUES (")+8,lineTxt.length()-2);
                        String[] splits = valuesStr.split(",");

                        //第一个
                       splits[0] = splits[0].substring(0,1)+ placeHolder + splits[0].substring(Integer.parseInt(replaceLength)+1) ;
                        System.out.println("splits[0]"+splits[0]);

                        //第4个
                        splits[3] =splits[3].trim().substring(0,1) + placeHolder + splits[3].substring(Integer.parseInt(replaceLength)+1) ;
                        System.out.println("splits[3]"+splits[3]);

                        splits[1]= splits[1].trim();
                        splits[2]= splits[1].trim();
                        splits[4]= splits[1].trim();
                        splits[5]= splits[1].trim();
                        splits[6]= splits[1].trim();
                        splits[7]= splits[1].trim();
                        splits[8]= splits[1].trim();

                      String rightValue =  Arrays.asList(splits).toString().replace("[","(").replace("]","};").replace("  "," ");
                        System.out.println("rightValue "+rightValue);
                      String finallValue = leftvalue + rightValue;
                        System.out.println("finallValue  "+finallValue);
                      String targetIndex = "250";

                      int gap =  Integer.parseInt(targetIndex) - finallValue.lastIndexOf(placeHolder);
                      if ( gap > 0){
                          StringBuffer sbf = new StringBuffer();

                          for (int i=0; i<gap; i++){
                              sbf.append(" ");
                          }

                          finallValue =   finallValue.replace((", '" + placeHolder), (sbf.toString() + ", '" + placeHolder));
                          System.out.println("finallValue  "+finallValue);
                      }
                        finallValue =  finallValue.replace(placeHolder,replaceStr);
                        System.out.println("finallValue  "+finallValue);
                      lineTxt = finallValue;
                    }

                    System.out.println("行数据:"+lineTxt);

                    String wrapChar = "\n";
                    bf.append(lineTxt + wrapChar);
                }
                System.out.println("finally:>>>\n"+bf.toString());
                bufferedReader.close();
                read.close();
                return bf.toString();
            }
            else
            {
                System.out.println("找不到指定的文件");
            }
        }
        catch (Exception e)
        {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
        return bf.toString();
    }

    /**使用FileOutputStream来写入txt文件
     * @param txtPath txt文件路径
     * @param content 需要写入的文本
     */
    public static void writeTxt(String txtPath,String content){
        log.info("内容:\n"+content);
        FileOutputStream fileOutputStream = null;
        File file = new File(txtPath);
        try {
            if(file.exists()){
                //判断文件是否存在，如果不存在就新建一个txt
                file.createNewFile();
            }
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(content.getBytes("UTF-8"));
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //验证方法：先写入文件后读取打印如下：
    public static void main(String[] args) throws Exception {


//        String str = readTxt("C:\\Users\\lucky\\Desktop\\demo.txt");
//
//        writeTxt("C:\\Users\\lucky\\Desktop\\demo_final.txt", str);

        ArrayList<RowInfo> initList = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            initList.add(new RowInfo((i+1),String.valueOf(i+1)));
        }

        ArrayList<RowInfo> repeatList  = delRepeat(initList);

        // 差集 (list1 - list2)
        ArrayList<RowInfo> reduce1 = (ArrayList<RowInfo>) initList.stream().filter(
                item ->  !repeatList.contains(item)
        ).collect(toList());
        System.out.println("---差集 reduce1 (list1 - list2)---");
        reduce1.parallelStream().forEach(System.out::println);

    }

    // Set去重并保持原先顺序的两种方法
    public static ArrayList<RowInfo> delRepeat(ArrayList<RowInfo> list) {
        Set<RowInfo> set = new LinkedHashSet<>();
        set.addAll(list);
        list.clear();
        list.addAll(set);

        System.out.println("---------------------------------------------"+list.hashCode());
        return (ArrayList<RowInfo>) list;
    }

    // Set去重并保持原先顺序的两种方法
    public static boolean hasRepeat(List<String> originList,List<String> afterList) {
        return  originList.size() == afterList.size();
    }

    // 差集 (list1 - list2)
    public static ArrayList<String> reduce(ArrayList<String> originList,ArrayList<String> afterList) {
        ArrayList<String> list = new ArrayList<>();
        for (String s : originList) {

            if (!afterList.contains(s)){
                list.add(s);
            }

        }
        System.out.println(originList);



        return originList;
    }
    /**
     * 差集(基于java8新特性)优化解法2 适用于大数据量
     * 求List1中有的但是List2中没有的元素
     */
    public static List<String> subList2(List<String> list1, List<String> list2) {
        Map<String, String> tempMap = list2.parallelStream().collect(Collectors.toMap(Function.identity(), Function.identity(), (oldData, newData) -> newData));
        return list1.parallelStream().filter(str-> !tempMap.containsKey(str)).collect(Collectors.toList());
    }



    public static <T> List<T> deepCopy(List<T> src) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteOut);
        out.writeObject(src);

        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
        ObjectInputStream in = new ObjectInputStream(byteIn);
        @SuppressWarnings("unchecked")
        List<T> dest = (List<T>) in.readObject();
        return dest;
    }

}
