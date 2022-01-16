/*
 * Copyright (C), 2022-2022, Freedom Person
 * FileName: WebDriverutil.java
 * Author:   lucky
 * Date:     2022/1/17 21:23
 * Description: //模块目的、功能描述
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名    修改时间    版本号       描述
 */
package com.wuzf.swing.utils;

import com.machinepublishers.jbrowserdriver.JBrowserDriver;
import com.machinepublishers.jbrowserdriver.Settings;
import com.machinepublishers.jbrowserdriver.Timezone;

/**
 * 功能描述:<br>
 *
 * @author lucky
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */

public class WebDriverutil {
    public static void main(String[] args) {

        // You can optionally pass a Settings object here,
        // constructed using Settings.Builder
        JBrowserDriver driver = new JBrowserDriver(Settings.builder().
                timezone(Timezone.AMERICA_NEWYORK).build());

        // This will block for the page load and any
        // associated AJAX requests
        driver.get("https://codingdict.com/os/software/71112");

        // You can get status code unlike other Selenium drivers.
        // It blocks for AJAX requests and page loads after clicks
        // and keyboard events.
        System.out.println(driver.getStatusCode());

        // Returns the page source in its current state, including
        // any DOM updates that occurred after page load
        System.out.println(driver.getPageSource());

        // Close the browser. Allows this thread to terminate.
        driver.quit();
    }
}
