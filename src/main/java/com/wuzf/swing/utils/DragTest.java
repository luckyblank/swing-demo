/*
 * Copyright (C), 2022-2022, Freedom Person
 * FileName: DragTestUtil.java
 * Author:   lucky
 * Date:     2022/1/16 5:21
 * Description: //模块目的、功能描述
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名    修改时间    版本号       描述
 */
package com.wuzf.swing.utils;

import com.wuzf.swing.demos.SwingSet2;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 功能描述:<br>
 *
 * @author lucky
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */

public class DragTest extends JFrame{

    JPanel panel;//要接受拖拽的面板
    public DragTest()
    {

        panel = new JPanel();
        panel.setBackground(Color.WHITE);
        getContentPane().add(panel, BorderLayout.CENTER);
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(400, 200);

        panel.setLayout(null);// 将父JPanel的布局设置为绝对布局
        setTitle("最简单的拖拽示例：拖拽文件到下面");

        List<String> allowedFileTypeList = Arrays.asList(".jpg",".xlsx",".doc",".docx",".xls",".txt");
        JTextArea jta = new JTextArea();
        //在文本框上添加滚动条
        JScrollPane jsp = new JScrollPane(jta);

        //设置矩形大小.参数依次为(矩形左上角横坐标x,矩形左上角纵坐标y，矩形长度，矩形宽度)
        jsp.setBounds(30,30,400,250);

        //默认的设置是超过文本框才会显示滚动条，以下设置让滚动条一直显示
//        jsp.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        //把滚动条添加到容器里面
        panel.add(jsp);
        openDrag(jta,jta, allowedFileTypeList);//启用拖拽
    }
    public static void main(String[] args) throws Exception
    {
        //UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");//设置皮肤
        SwingSet2.initBeautyStyle();
        new DragTest().setVisible(true);;
    }
    public static void openDrag(JComponent allowDropComponent, JTextComponent showPathComponent, List<String> allowedFileTypeList)//定义的拖拽方法
    {
        DropTargetAdapter dropTargetAdapter = new DropTargetAdapter(){
            List<File> fileList = new ArrayList<>();
            @Override
            public void drop(DropTargetDropEvent dtde)//重写适配器的drop方法
            {
                try
                {
                    List<String> failfileList = new ArrayList<>();
                    if (dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor))//如果拖入的文件格式受支持
                    {
                        dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);//接收拖拽来的数据
                        fileList = (List<File>) (dtde.getTransferable().getTransferData(DataFlavor.javaFileListFlavor));
                        String temp="";
                        for(File file : fileList){

                            String fileAbsolutePath = file.getAbsolutePath();
                            String fileName = file.getName();
                            String fileSuffix = fileName.substring(fileName.lastIndexOf("."));
                            if (!allowedFileTypeList.contains(fileSuffix.toLowerCase()) && allowedFileTypeList.size() > 0){
                                failfileList.add(file.getAbsolutePath());
                                System.out.println(failfileList);
                                JOptionPane.showMessageDialog(null, "仅支持word/xlsx/jpg格式文件");
                                return ;
                            }

                            temp+= fileAbsolutePath +";\n";
                        }
                        //输入框中显示 文件路径
                        showPathComponent.setText(fileList.get(0).getAbsolutePath());
                        JOptionPane.showMessageDialog(null, temp);
                        dtde.dropComplete(true);//指示拖拽操作已完成
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "仅支持文件格式");
                        dtde.rejectDrop();//否则拒绝拖拽来的数据
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        };

        //panel表示要接受拖拽的控件
        DropTarget  target =   new DropTarget(allowDropComponent, DnDConstants.ACTION_COPY_OR_MOVE, dropTargetAdapter);
    }
}
