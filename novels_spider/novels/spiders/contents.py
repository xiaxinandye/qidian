# -*- coding: utf-8 -*-
from scrapy import Request, Spider
from novels.items import ContentItem 

import requests

class ContentsSpider(Spider):
    name = 'contents'
    allowed_domains = ['qidian.com']
    start_urls = ['http://qidian.com/']

    def parse(self, response):
        is_vip = response.xpath('//script/text()').re('g_data.chapter = .*?"vipStatus":(.*?),')[0] # type str
        item = ContentItem()
        item['id'] = response.meta['id']
        # python 中 0, ''为 False，'0' 为  True
        if is_vip == '1':
            # 需要正版订阅
            item['is_qidian'] = 1
            item['is_vip'] = 1
            item['content'] = 'VIP章节需要订阅，暂无资源'
            #self.logger.info('暂无资源')
        else:
            data = response.xpath('//section[@class="read-section jsChapterWrapper"]//p').extract() 
            str = ''
            for d in data:
                d = d.strip()
                str += d
            # 注此时你可能在控制台上看到的item['content']中含有一些特殊字符 \u3000, 这是控制台处理了字符串的格式便于观察，不要紧的。
            item['content'] = str.replace("'","‘")
            item['is_qidian'] = 1
            item['is_vip'] = 0
            #self.logger.info('正在存储:' + item['id'])
        return item

    def start_requests(self):
        base_url = 'https://m.qidian.com/book/'
         # 利用 web api 获取章节id 和对应书籍id
        chaptersid_url = self.settings.get('CHAPTER_URL')
        chapter_ids = requests.get(chaptersid_url).json()
        for ids in chapter_ids:
            nid = ids[0]
            cid = ids[1]
            is_qidian = ids[2]
            if is_qidian == 1:
                #self.logger.info('该章节早已存储：' + cid)
                pass
            else:
                url = base_url + nid + '/' + cid
                yield Request(url=url, meta={'id':cid}, callback=self.parse)

    

               

   
