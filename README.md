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

4. 已实现功能
   - 登录、注册、根据登录用户显示用户昵称

   - 路由配置（router\index.js点击菜单可跳转到相应的菜单界面，并高亮菜单，默认高亮报名公告菜单）

   - 权限配置（role）：1为管理员，2为普通用户，管理员可访问普通用户不能访问的菜单

   - 用户管理（User.vue管理员对用户进行增删改查操作）

   - 富文本图片上传 文件上传

   - 修复前端页面可以查看密码的问题

   - 评分功能已实现

5. 问题记录

   - 右上角显示用户昵称需要login中返回res

   - 更改缓存中role值可修改用户权限（已修复，原因code值 0 '0'）

   - 文件上传报错404（已修复，缺少Controller文件）

   - url传到数据库报错500（已修复，数据库中url长度过短，设置为VARCHAR(255)即可）

   - 未实现报名者对报名信息的权限控制，即每个人只能管理自己的报名

   - 报错400，本次问题，Failed to convert value of type 'java.lang.String' to required type 'java.lang.Long'，原因数据类型不匹配，将Long修改为String可解决页面报错，但是会访问undefined

   - 报错访问超时5000，原因debugger没有放过去

6. 笔记

   - 手写分页查询 P8

   - swagger（springboot中集成接口测试）