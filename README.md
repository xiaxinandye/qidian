# 项目说明
此项目是我的毕业设计，之所以上传到给github上是为了进行保留（也方便后面来看自己当时的知识掌握程度），也希望能对后来者有点启发。本项目考虑到我的知识储备（暂还是小白），可能有不少BUG，希望大家多多担待。项目的数据来源是通过Python的Scrapy框架去爬取起点的数据，然后网站整体框架采用的是Java的Spring Boot框架。项目的已部署在：[https://www.xiaxinandye.cn](https://www.xiaxinandye.cn/)（预计云数据库到期时间2019-8月）。项目结构图如下所示：

![https://raw.githubusercontent.com/xiaxinandye/GitImage/master/qidain/%E9%A1%B9%E7%9B%AE%E7%BB%93%E6%9E%84%E5%9B%BE.png](https://raw.githubusercontent.com/xiaxinandye/GitImage/master/qidain/项目结构图.png)

## 效果截图

### 首页

![](https://raw.githubusercontent.com/xiaxinandye/GitImage/master/qidain/%E6%B7%B1%E5%BA%A6%E6%88%AA%E5%9B%BE_%E9%80%89%E6%8B%A9%E5%8C%BA%E5%9F%9F_20190704163228.png)

### 书架

![https://raw.githubusercontent.com/xiaxinandye/GitImage/master/qidain/%E4%B9%A6%E6%9E%B6.png](https://raw.githubusercontent.com/xiaxinandye/GitImage/master/qidain/书架.png)

### 书籍详细页

![https://raw.githubusercontent.com/xiaxinandye/GitImage/master/qidain/%E4%B9%A6%E7%B1%8D%E8%AF%A6%E7%BB%86%E9%A1%B5.png](https://raw.githubusercontent.com/xiaxinandye/GitImage/master/qidain/书籍详细页.png)

### 

### 排行详细图

![https://raw.githubusercontent.com/xiaxinandye/GitImage/master/qidain/%E6%8E%92%E8%A1%8C%E8%AF%A6%E7%BB%86%E5%9B%BE.png](https://raw.githubusercontent.com/xiaxinandye/GitImage/master/qidain/排行详细图.png)

### 章节正文内容图

![https://raw.githubusercontent.com/xiaxinandye/GitImage/master/qidain/%E7%AB%A0%E8%8A%82%E6%AD%A3%E6%96%87%E5%86%85%E5%AE%B9.png](https://raw.githubusercontent.com/xiaxinandye/GitImage/master/qidain/章节正文内容.png)

### 搜索书籍

![https://raw.githubusercontent.com/xiaxinandye/GitImage/master/qidain/%E6%90%9C%E7%B4%A2%E4%B9%A6%E7%B1%8D.png](https://raw.githubusercontent.com/xiaxinandye/GitImage/master/qidain/搜索书籍.png)

# 环境

1. 爬虫：Python 3.6 + Scrapy+ Flask + Visual Studio Code
2. 网站框架：Spring Boot 2.1.2 集成模块有：Cache、Web、Thymeleaf、Spring Security、MySQL、MyBatis、ElasticSearch
3. 前端框架：Booststrap 4
4. 资源服务器：云数据库MySQL（存储书籍）、阿里云或腾讯云服务器（爬虫、上线项目与搜索服务等）

# 注意事项

由于此项目需要用到一些服务器资源，因此若你想实现相应的功能，那么请将相应的服务器配置地址换成你的服务器地址。

# 尊重正版

为了保护起点中文网的知识产权及拥护正版，此项目爬虫部分只爬取的是起点中文网的免费章节，不会获取VIP章节。此项目纯粹为了学习交流之用，不用于任何商业用途。若有冒犯，请多海涵。

