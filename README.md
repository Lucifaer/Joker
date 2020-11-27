# JokerFrameWork

## 0x00 简介

一个Java写的攻击框架，主要负责解决每次分析完漏洞后poc散乱放置的问题，同时也为了更方便的在分析过程中搭建各种服务，如开启`rmi server`这样的重复性劳动。

## 0x01 依赖

本项目主要由以下组件构成：
* SpringBoot：`1.5.8`(SpringShell自带的SpringBoot版本)
* SpringShell：`2.0.0.RELEASE`

## 0x02 使用

具体使用方式请查看`doc/UseGuide`下的文档

## 0x03 目前状态

目前在github上公开的版本为稀释出来的版本，可以稳定运行。

## 0x04 未来计划

目前正在重构该框架，将其变为一个exp及payload（如利用链、回显链）的知识库，方便快速利用框架生成各种exp代码。

- [ ] 增加`network`模块负责各类请求的server/client端
- [ ] 重构调度层代码，支持扫描等其他模块的hotswap支持
- [ ] 增加`gadget`代码生成器，思考不同gadget通用构建适配器的写法
- [ ] 完成`core/server/cli/web`的分离，支持web界面及cli端

...

想法是实现Java的MSF，而且更加贴合安全研究人员的成果留存，以及使用者的便捷度。
