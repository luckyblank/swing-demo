package com.wuzf.swing;

import com.wuzf.swing.demos.SwingSet2;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import javax.swing.*;
import java.io.IOException;
import java.util.Properties;

@SpringBootApplication
public class SwingDemoApplication {

    private static final Logger log = LoggerFactory.getLogger(SwingDemoApplication.class);
    public static void main(String[] args) {


        //读取配置文件的值来选择启动ui
        try {
            Properties loadProperties = PropertiesLoaderUtils
                    .loadProperties(new EncodedResource(new ClassPathResource("application.properties"), "UTF-8"));

            String guiType = loadProperties.getProperty("swing.ui.type");

            //SwingUtilities.invokeLater的作用可以详细百度一下，我新手
            SwingUtilities.invokeLater(new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
                    log.info("正在启动GUI =======================>>> " + guiType );
                    if("SwingSet2GUI".equals(guiType)){
                        SwingSet2.main(args);
                    }else if("Swing9patchGUI".equals(guiType)){
                        //TO-DO...
                    }else {
                        log.error("启动GUI异常 =======================>>> 请检查GUITYPE是否存在 ");
                        throw new RuntimeException("GUI类型出错："+guiType);
                    }

                }
            });
        } catch (IOException e) {
            log.error("\n\t 启动GUI异常 >>>>>>>>>>>{},{}",e.getMessage(),e);
            e.printStackTrace();
        }

        //spring
        SpringApplication.run(SwingDemoApplication.class, args);
    }

}
