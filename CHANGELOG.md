### 2020.06.15
1. 增加`checkout`及`q`命令，用于上下文的切换及退出

### 2020.06.12
1. JokerSDK新增命令行参数解析支持
2. 支持JokerSDK直接运行

### 2020.06.10

1. 修复`PluginScanner`所导入插件数据结构为`PluginModel`，更改为`ExploitModel`
2. 新增`help`指令用于展示不同命令节点可用的命令。resolve #5
3. 新增`show_options`子命令用于展示exploit插件所需填写的参数，同时输出插件编写者自定义帮助文档
4. 新增实例化对象缓存机制，防止重复实例化对象影响性能
5. 更新版本号为1.0.1.RELEASE

### 2020.06.08

1. 增加HelpCommand，用于完善`help`指令

### 2020.06.04

1. 将内部开发迭代版本4.0.0转换为正式发行版1.0.0.RELEASE
2. resolve #1
3. resolve #2
4. 暂时移除Server模块，将在下一个小版本重新添加回来