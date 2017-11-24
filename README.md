# SmallDemo


### 创建工程
- open Android Studio，点选 Start a new Android Studio project 创建一个Android工程。Application Name 本示例设置为 MySmall （您可以填写为自己的项目名称）。
### 集成Small
![gradle.jpeg](http://upload-images.jianshu.io/upload_images/3021778-13a790ed9d2354fc.jpeg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)



```
	dependencies 
	{    
		classpath 'com.android.tools.build:gradle:2.2.3' //注意gradle版本号   
		classpath 'net.wequick.tools.build:gradle-small:1.1.0-alpha2'  
	} 
	
	//在文件末尾加上
	apply plugin: 'net.wequick.small'
	
	small 
	{
	    aarVersion = '1.1.0-alpha2'
	}
```

### 验证Small环境
 
 > 在底部面板Terminal中输入命令：./gradlew small
 
 如果一切正常，将成功输出：
```
  gradle-small plugin : 1.1.0-alpha2 (maven)
            small aar : 1.1.0-alpha2 (maven)
          gradle core : 3.3
       android plugin : 2.2.3
                   OS : Mac OS X 10.13.1 (x86_64)
```

| type |   name   |  PP  | sdk |  aapt  | support | file(armeabi) |  size   |
|------|----------|------|-----|--------|---------|---------------|---------|
| host | app      |      | 26  | 26.0.2 | 26.+    |               |         |
| app  | app.main | 0x77 | 26  | 26.0.2 | 26.+    | *_main.so     | 43.7 KB |

**BUILD SUCCESSFUL**


### 配置宿主
- 新建Application，注入Small
![新建Application.jpeg](http://upload-images.jianshu.io/upload_images/3021778-3c1ecefa797d3607.jpeg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

- 添加构造方法来初始化Small
```
public class SmallApp extends Application
{    
		public SmallApp() 
		{        
			Small.preSetUp(this);    
		}
}
```
- 在AndroidManifest.xml 中指定这个 Application
### 创建插件模块
- 右键 app 模块 > New > Module
> 创建一个应用模块 Phone & Tablet Module，设置 Application/Library name 为 App.main，此时 Module name 自动为 app.main，Package name 为 com.leo.smalldemo.app.main

- 确认是否启动插件
 > 修改插件的布局文件 app.main > res > layout > activity_main.xml，将 TextView 的内容改为 This is Small Model!

### 编译插件
- 先编译公共库
```
./gradlew buildLib -q
```
- 再编译app.main
```
./gradlew buildBundle -q -Dbundle.arch=x86
```
> 在模拟器调试时，-Dbundle.arch=xx，可以使用x86架构，真机打包时，需要使用到armeabi架构

### 启动插件
- 右键 app 模块，New > Folder > Assets Folder 新建 assets 目录
![assets目录.jpeg](http://upload-images.jianshu.io/upload_images/3021778-87709e69f3c6a587.jpeg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)



- 再右键生成的 assets 目录，New > File 新建路由配置文件 bundle.json：
![bundle.json](http://upload-images.jianshu.io/upload_images/3021778-696bfe7deb7a8268.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

 
 - 修改bundle.json，添加路由
```
 {
  "version": "1.0.0",//文件格式版本，目前始终为1.0.0
  "bundles": [//插件数组
    {
      "uri": "main",//插件唯一ID
      "pkg": "com.leo.smalldemo.app.main"//插件包名
    }
  ]
}
```
- 启动app.main插件
 > 回到宿主的 app > java > com.leo.smalldemo > MainActivity，
在 onStart 方法中我们通过上述配置的 uri 来启动 app.main 插件：
```
 @Override
    protected void onStart() {
        super.onStart();
        Small.setUp(this, new Small.OnCompleteListener() {
            @Override
            public void onComplete() {
                Small.openUri("main",MainActivity.this);
            }
        });
    }
```
### 运行宿主
- 在顶部菜单栏，我们先选择宿主模块 app，再点击旁边的运行按钮

![run](http://upload-images.jianshu.io/upload_images/3021778-5b030d8d52149e67.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


- 成功运行后，将启动插件模块：

![succeed.jpg](http://upload-images.jianshu.io/upload_images/3021778-d390c4f3147e9f5d.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
