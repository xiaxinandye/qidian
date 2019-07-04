# -*- coding: utf-8 -*-

# Scrapy settings for novels project
#
# For simplicity, this file contains only settings considered important or
# commonly used. You can find more settings consulting the documentation:
#
#     https://doc.scrapy.org/en/latest/topics/settings.html
#     https://doc.scrapy.org/en/latest/topics/downloader-middleware.html
#     https://doc.scrapy.org/en/latest/topics/spider-middleware.html

BOT_NAME = 'novels'

SPIDER_MODULES = ['novels.spiders']
NEWSPIDER_MODULE = 'novels.spiders'


# Crawl responsibly by identifying yourself (and your website) on the user-agent
#USER_AGENT = 'novels (+http://www.yourdomain.com)'

# Obey robots.txt rules
ROBOTSTXT_OBEY = False

# Configure maximum concurrent requests performed by Scrapy (default: 16)
CONCURRENT_REQUESTS = 100

# Configure a delay for requests for the same website (default: 0)
# See https://doc.scrapy.org/en/latest/topics/settings.html#download-delay
# See also autothrottle settings and docs
#DOWNLOAD_DELAY = 3
# The download delay setting will honor only one of:
#CONCURRENT_REQUESTS_PER_DOMAIN = 16
#CONCURRENT_REQUESTS_PER_IP = 16

# Disable cookies (enabled by default)
# COOKIES_ENABLED = False

# Disable Telnet Console (enabled by default)
#TELNETCONSOLE_ENABLED = False

# Override the default request headers:
#DEFAULT_REQUEST_HEADERS = {
#   'Accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8',
#   'Accept-Language': 'en',
#}

# Enable or disable spider middlewares
# See https://doc.scrapy.org/en/latest/topics/spider-middleware.html
#SPIDER_MIDDLEWARES = {
#    'novels.middlewares.NovelsSpiderMiddleware': 543,
#}

# Enable or disable downloader middlewares
# See https://doc.scrapy.org/en/latest/topics/downloader-middleware.html
#DOWNLOADER_MIDDLEWARES = {
#    'novels.middlewares.NovelsDownloaderMiddleware': 543,
#}

# Enable or disable extensions
# See https://doc.scrapy.org/en/latest/topics/extensions.html
#EXTENSIONS = {
#    'scrapy.extensions.telnet.TelnetConsole': None,
#}

# Configure item pipelines
# See https://doc.scrapy.org/en/latest/topics/item-pipeline.html
ITEM_PIPELINES = {
   'novels.pipelines.NovelPipeline': 300,
   'novels.pipelines.NovelDetailsPipeline': 301,
   'novels.pipelines.ChapterPipeline': 302,
   'novels.pipelines.ContentPipeline': 303
}

# Enable and configure the AutoThrottle extension (disabled by default)
# See https://doc.scrapy.org/en/latest/topics/autothrottle.html
#AUTOTHROTTLE_ENABLED = True
# The initial download delay
#AUTOTHROTTLE_START_DELAY = 5
# The maximum download delay to be set in case of high latencies
#AUTOTHROTTLE_MAX_DELAY = 60
# The average number of requests Scrapy should be sending in parallel to
# each remote server
#AUTOTHROTTLE_TARGET_CONCURRENCY = 1.0
# Enable showing throttling stats for every response received:
#AUTOTHROTTLE_DEBUG = False

# Enable and configure HTTP caching (disabled by default)
# See https://doc.scrapy.org/en/latest/topics/downloader-middleware.html#httpcache-middleware-settings
#HTTPCACHE_ENABLED = True
#HTTPCACHE_EXPIRATION_SECS = 0
#HTTPCACHE_DIR = 'httpcache'
#HTTPCACHE_IGNORE_HTTP_CODES = []
#HTTPCACHE_STORAGE = 'scrapy.extensions.httpcache.FilesystemCacheStorage'

# 每一个分类取的书籍数量
COUNT = 10
# 获取排行书籍所需翻的页数
MAX_PAGE = 2

# 分别对应玄幻、奇幻、武侠、仙侠、都市、现实、军事、历史、游戏、体育、科幻、悬疑灵异、二次元 共13个分类id
# category = ['21', '1', '2', '22', '4', '15', '6', '5', '7', '8', '9', '10', '12']
# 但是现实和都市的内容是一样的，考虑剔除掉现实这一分类。
CATEGORY = {
    '玄幻':'21', '奇幻':'1', '武侠':'2', '仙侠':'22', '都市':'4', '军事':'6', 
    '历史':'5', '游戏':'7', '体育':'8', '科幻':'9', '悬疑灵异':'10', '二次元':'12'
    }
# MySQL 配置
MYSQL_HOST = 'mysql host地址'
MYSQL_DATABASE = '数据库名'
MYSQL_USER = '用户名'
MYSQL_PASSWORD = '密码'
MYSQL_PORT = 3306


# 日志配置，若LOG_FILE 启用了，则终端只会打印设置的日志级别及以上的日志信息
# 若设置了 LOG_LEVEL， 那么日志文件只会保存设置的日志级别及以上的日志信息
LOG_FILE='scrapy.log'
LOG_LEVEL = 'INFO'

# flask url：用来获取书籍id
FLASK_URL = 'http://localhost:5000' 
# CHAPTER_URL 用来获取章节id
CHAPTER_URL = 'http://localhost:5000/chapters' 
