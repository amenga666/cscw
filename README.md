## 大学生竞赛网站

1. 导入项目

   * 下载项目后使用IDEA打开cscw文件夹，导入项目

2. 运行vue项目

   2.1 安装vue项目所需文件

      * 找到cscw/vue/package.json，正常情况下打开此文件IDEA会提示使用npm install命令安装所需文件，点击npm install等待安装即可；不提示的话可以打开IDEA下方Terminal，输入cd vue回车进入vue文件夹下，手动输入npm install安装所需文件

   2.2 编辑vue启动配置

      * 点击IDEA右上角Edit Configurations弹出配置窗口，点击左上角＋，选择npm，点击右侧package.json的下拉箭头，选择cscw\vue\package.json，再点击Scripts的下拉箭头，选择serve，点击下方OK配置完成

   2.3 启动vue项目

      * 点击Edit Configurations右侧三角形运行按钮，等待项目加载完毕，打开浏览器在地址栏输入http://localhost:8080 出现项目界面，vue项目启动成功

3. 运行spring boot项目

   3.1 下载spring boot项目所需依赖

      * 找到cscw\springboot\pom.xml，打开此文件IDEA会自动根据pom.xml文件下载依赖

   3.2 编辑spring boot启动配置

      * 正常情况下IDEA默认会配置spring boot的启动项，没有的话就打开Edit Configurations点击＋选择spring boot，设置Name为SpringbootApplication，JDK选择java8，模块选择springboot，启动类选择com.ameng.springboot.SpringbootApplication，点击OK配置完成

   3.3 启动spring boot项目
      * 点击Edit Configurations右侧三角形运行按钮，等待项目加载完毕，打开浏览器在地址栏输入http://localhost:9090 出现Whitelabel Error Page，spring boot项目启动成功