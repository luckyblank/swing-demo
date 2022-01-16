/*
 * Copyright (C), 2022-2022, Freedom Person
 * FileName: TabPanel.java
 * Author:   lucky
 * Date:     2022/1/17 23:03
 * Description: //模块目的、功能描述
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名    修改时间    版本号       描述
 */
package com.wuzf.swing.utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * 功能描述:<br>
 *
 * @author lucky
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */

public class TabPanel extends JPanel implements MouseListener {

        private JTabbedPane pane;	// 所在选项卡窗格

        private JLabel lab;

        public TabPanel(String title, JTabbedPane pane) {
            this.pane = pane;
            lab = new JLabel(title);
            lab.setHorizontalAlignment(JLabel.RIGHT);	//设置文字显示在最左边
            lab.addMouseListener(this);
            this.add(lab);
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            // TODO Auto-generated method stub
            int confirm = JOptionPane.showConfirmDialog(this.pane, "确认关闭选项卡", "提示", JOptionPane.YES_NO_OPTION);
            if (confirm == 0) { this.pane.remove(this.pane.indexOfTabComponent(this)); }
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            // TODO Auto-generated method stub
            this.lab.setText("X");
        }

        @Override
        public void mouseExited(MouseEvent e) {
            // TODO Auto-generated method stub
            this.lab.setText("");
        }

        @Override
        public void mousePressed(MouseEvent e) { }

        @Override
        public void mouseReleased(MouseEvent e) { }

}
