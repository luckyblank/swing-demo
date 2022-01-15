# swing-demo

#### 项目介绍：
> 基于**[beautyeye](https://gitee.com/jackjiang/beautyeye)**下的SwingSet2改造的Springboot项目
>
> Created by LuckyBlank

beautyeye是针对swing的美化包

目前看了作者的3个项目，都是swingui中的佼佼者，符合个人的UI审美的。

唯一美中不足的项目采用原生的Java项目，只好印着头皮进行改造成maven项目。

 代码仓库：

- SwingSet2/SwingSet3：https://gitee.com/jackjiang/beautyeye
- Swing9patch：  **https://github.com/JackJiang2011/Swing9patch**



#### 改造说明：

##### 1.创建标准的Springboot项目

（Springboot版本推荐2.5.6）

```xml
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.5.6</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
```

##### 2.创建目录

###### Java相关：

​	项目下创建lib目录用于放置beautyeye_lnf.jar：本地jar包。

​	主启动类同级目录添加demos包：用于存放swingset2的java文件。

###### Resources相关：

​	项目resources文件夹下创建相关文件夹

​	language：用于存放国际化配置文件；

​	static：静态资源文件

​	static/images：静态图片等资源

​	templates：存放html等视图文件

​	...

​	将对应的资源文件复制到对应的文件夹中

###### 项目包结构：

![image-20220115212133752](https://qny.luckyblank.cn/image-20220115212133752.png)

##### 3.添加本地Jar包依赖

beautyeye_lnf.jar

```xml
        <!-- beauty 相关的本地JAR -->
        <dependency>
            <groupId>beautyeye_lnf</groupId>
            <artifactId>beautyeye_lnf</artifactId>
            <version>3.7</version>
            <scope>system</scope>
            <systemPath>${project.basedir}/lib/beautyeye_lnf.jar</systemPath>
        </dependency>
        <!-- beauty 相关的本地JAR -->
```

##### 4.改造主启动类：

（SwingUtilities.invokeLater(...)可自行百度，似乎是为了不影响主线程）

```java
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
```

##### 5.修正对应package包名

##### 6.修改类相关文件

​	a.Swingset2.java中的demo数组中的类路径（改成全路径）(涉及到的全改成全类名)

```java
	/** The demos. */
	String[] demos = {
			"com.wuzf.swing.demos.ButtonDemo",
			"com.wuzf.swing.demos.ColorChooserDemo",
			"com.wuzf.swing.demos.ComboBoxDemo",
			"com.wuzf.swing.demos.FileChooserDemo",
			"com.wuzf.swing.demos.HtmlDemo",
			"com.wuzf.swing.demos.ListDemo",
			"com.wuzf.swing.demos.OptionPaneDemo",
			"com.wuzf.swing.demos.ProgressBarDemo",
			"com.wuzf.swing.demos.ScrollPaneDemo",
			"com.wuzf.swing.demos.SliderDemo",
			"com.wuzf.swing.demos.SplitPaneDemo",
			"com.wuzf.swing.demos.TabbedPaneDemo",
			"com.wuzf.swing.demos.TableDemo",
			"com.wuzf.swing.demos.ToolTipDemo",
			"com.wuzf.swing.demos.TreeDemo",
			"com.wuzf.swing.demos.TextAreaDemo"
	};
```

​	b.修改获取国际化资源字符串的方式：只留下类名

```java
	void loadDemo(String classname) {
		String afterClassName = classname.substring(classname.lastIndexOf(".")+1);
		setStatus(getString("Status.loading") + getString( afterClassName + ".name"));
		DemoModule demo = null;
		try {
			Class demoClass = Class.forName(classname);
			Constructor demoConstructor = demoClass.getConstructor(new Class[]{SwingSet2.class});
			demo = (DemoModule) demoConstructor.newInstance(new Object[]{this});
			addDemo(demo);
		} catch (Exception e) {
			System.out.println("Error occurred loading demo: " + classname);
		}
	}
```

c.修改静态图片文件地址：改成“/static/images”

```java
	public ImageIcon createImageIcon(String filename, String description) {
		String path = "/static/images/" + filename;
		return new ImageIcon(getClass().getResource(path)); 
	}
```

d.修改htmlDemo类中的path指向/templates/xxx.html

```java
    public HtmlDemo(SwingSet2 swingset) {
        // Set the title for this demo, and an icon used to represent this
        // demo inside the SwingSet2 app.
        super(swingset, "HtmlDemo"
        		, "toolbar/JEditorPane.gif");
	
        try {
	    URL url = null;
	    // System.getProperty("user.dir") +
	    // System.getProperty("file.separator");
	    String path = null;
	    try {
		path = "/templates/index.html";
		url = getClass().getResource(path);
            } catch (Exception e) {
		System.err.println("Failed to open " + path);
		url = null;
            }
	    
            if(url != null) {
                html = new JEditorPane(url);
//                html.setEditable(false);
                html.addHyperlinkListener(createHyperLinkListener());

		JScrollPane scroller = new JScrollPane();
		JViewport vp = scroller.getViewport();
		vp.add(html);
                getDemoPanel().add(scroller, BorderLayout.CENTER);
            }
        } catch (MalformedURLException e) {
            System.out.println("Malformed URL: " + e);
        } catch (IOException e) {
            System.out.println("IOException: " + e);
        }
    }
```

e.修正templates/xxx.html中的图片资源应用地址

```html
<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body bgcolor="#FFFFFF">
<p>&nbsp;</p>
<p align="center"><img src="../static/images/htmldemo/header.jpg" width="363" height="171"></p>
<p align="center"><img src="../static/images/Octavo/book.jpg" width="550" height="428"></p>
<p align="center">&nbsp;</p>
<p align="center">&nbsp;</p>
<blockquote>
  <h3><b><a href="title.html">Title Page</a></b></h3>
  <h3><b><a href="king.html">To The King</a></b></h3>
  <h3><b><a href="preface.html">The Preface</a></b></h3>
  <h3><a href="seaweed.html">Of the curious texture of Sea-weeds</a></h3>
  <h3><a href="ant.html">Of an Ant or Pismire</a></h3>
  <h3><a href="bug.html">Of a Louse</a> <br>
    <br>
    <br>
  </h3>
  <p><font color="#990000" size="4">Images and text used by permission of Octavo 
    Corporation (www.octavo.com),<br>
    </font><font color="#990000" size="4">(c) 1999 Octavo Corporation. All 
    rights reserved.</font> <br>
    <br>
    <br>
    <font size="2">Octavo Corporation is a publisher of rare 
    books and manuscripts with digital tools and formats through partnerships 
    with libraries, museums, and individuals. Using high-resolution digital imaging 
    technology, Octavo releases digital rare books on CD-ROM as Adobe PDF files 
    which can be viewed on and printed from almost any computing platform. You 
    can view each page and the binding on your computer screen, zoom in to view 
    detail up to 800% in some cases, and search, copy and paste the "live" text 
    placed invisibly behind the page images which is available for selected Editions. 
    Also included in each edition is the work's collation and provenance, as well 
    as commentary by a noted expert in its field. </font></p>
</blockquote>
<p>&nbsp;</p>
</body>
</html>
```

f.修改DemoModule类中的获取指定前缀的国际化文件方式:getBundle("language.swingset")

```JAVA
    public String getString(String key) {
	String value = "nada";
	if(bundle == null) {
	    if(getSwingSet2() != null) {
		bundle = getSwingSet2().getResourceBundle();
	    } else {
		bundle = ResourceBundle.getBundle("language.swingset");
	    }
	}
	try {
	    value = bundle.getString(key);
	} catch (MissingResourceException e) {
	    System.out.println("java.util.MissingResourceException: Couldn't find value for: " + key);
	}
	return value;
    }
```

##### 7.打包设置

修改pom.xml；设置主启动类，将本地lib/xxx.jar打进去

> 基于Springboot版本
>
> spring-boot-starter-parent V2.5.6
>
> 以下打包插件版本已经进过测试，推荐使用
>
> maven-resources-plugin V3.1.0
>
> maven-jar-plugin V3.0.2

```xml
<build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <executions>
                    <execution>
                        <goals>
                            <goal>repackage</goal>
                        </goals>
                    </execution>
                </executions>
                <configuration>
                    <includeSystemScope>true</includeSystemScope>
                    <excludes>
                        <exclude>
                            <groupId>org.projectlombok</groupId>
                            <artifactId>lombok</artifactId>
                        </exclude>
                    </excludes>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-resources-plugin</artifactId>
                <version>3.1.0</version>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-jar-plugin</artifactId>
                <version>3.0.2</version>
                <configuration>
                    <archive>
                        <manifest>
                            <addClasspath>true</addClasspath>
                            <!-- 此处为程序主入口-->
                            <mainClass>com.wuzf.swing.SwingDemoApplication</mainClass>
                        </manifest>
                    </archive>
                </configuration>
            </plugin>
            <!-- 跳过单元测试 -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <configuration>
                    <skipTests>true</skipTests>
                </configuration>
            </plugin>
        </plugins>
    </build>
```

#### 使用说明：

> 项目已经引用lombok
>
> IDEA、Eclipse需配置好对应的插件

开箱即用

#### 扩展说明：

按需扩展功能：

a.复制一份demo包下的任意xxxDemo的文件(例如：TextAreaDemo)

b.在国际化配置文件中写入相关的信息swingset_zh_CN.properties

```java
### DIY DEMO ###
TextAreaDemo.accessible_description=TextAreaDemo \u6f14\u793a
TextAreaDemo.name=TextAreaDemo \u6f14\u793a
TextAreaDemo.tooltip=TextAreaDemo \u6f14\u793a
```

c.在SwingSet2类文件的demos数组中添加刚刚创建的类的全类名

d.运行程序

![image-20220115220232814](https://qny.luckyblank.cn/image-20220115220232814.png)
