/*
 * Copyright (C), 2022-2022, Freedom Person
 * FileName: JavaFileDragDrop.java
 * Author:   lucky
 * Date:     2022/1/16 13:57
 * Description: //模块目的、功能描述
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名    修改时间    版本号       描述
 */
package com.wuzf.swing.utils;

import javax.swing.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.util.Iterator;
import java.util.List;

/**
 * 功能描述:<br>
 *
 * @author lucky
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */

public class JavaFileDragDrop extends JFrame

{

    JTextArea jta;

    public JavaFileDragDrop()

    {

        this.setTitle("java file drag and drop test");

        this.setBounds(150,150,300,300);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setLayout(null);

        jta=new JTextArea();

        DropTargetAdapter kgd=new DropTargetAdapter() {

            public void drop(DropTargetDropEvent dtde) {
                try
                {

                    Transferable tf=dtde.getTransferable();

                    if(tf.isDataFlavorSupported(DataFlavor.javaFileListFlavor))

                    {

                        dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);

                        List lt=(List)tf.getTransferData(DataFlavor.javaFileListFlavor);

                        Iterator itor=lt.iterator();

                        while(itor.hasNext())

                        {
                            File f=(File)itor.next();
                            jta.setText(jta.getText()+"n"+f.getAbsolutePath());
                        }
                        dtde.dropComplete(true);
                    } else {
                        dtde.rejectDrop();
                    }

                }

                catch(Exception e)

                {

                    e.printStackTrace();

                }

            }

        };

        new DropTarget(jta, DnDConstants.ACTION_COPY_OR_MOVE,kgd);

        //在文本框上添加滚动条
        JScrollPane jsp = new JScrollPane(jta);

        //设置矩形大小.参数依次为(矩形左上角横坐标x,矩形左上角纵坐标y，矩形长度，矩形宽度)
        jsp.setBounds(30,30,250,250);

        //默认的设置是超过文本框才会显示滚动条，以下设置让滚动条一直显示
//        jsp.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        //把滚动条添加到容器里面
        this.add(jsp);

        this.setVisible(true);

    }

    public static void main(String[] args)

    {

        new JavaFileDragDrop();

    }

}