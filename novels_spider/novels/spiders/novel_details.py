# -*- coding: utf-8 -*-
from scrapy import Spider, Request
from novels.items import NovelDetailsItem
import requests
import re

class NovelDetailsSpider(Spider):
    name = 'novel_details'
    allowed_domains = ['qidian.com']
    start_urls = ['http://qidian.com/']

    def parse(self, response):
        item = NovelDetailsItem()
        des = response.css('.book-intro p::text').extract()
        # print(type(des)) list
        cover = response.css('.book-img img::attr(src)').extract_first()
        tickets_month = response.css('.month-ticket .num #monthCount::text').extract_first()
        tickets_recommend = response.css('.rec-ticket .num #recCount::text').extract_first()
        # 获取绝对src并去掉末尾的空白字符
        item['cover_image'] = response.urljoin(cover).strip()
        item['tickets_month'] = tickets_month
        item['tickets_recommend'] = tickets_recommend
        item['description'] = ''
        # 从请求参数中获取小说id    
        item['novel_id'] = response.meta['novel_id']
        # 去掉文本中的空白字符
        # 这里有个问题就是简介里面可能含有 ' 这个字符，为此我们将其转为 ’
        for de in des:
            de = de.strip()
            # print(type(de)) str
            if de.find('\''):
                 de = de.replace('\'', '‘')
            item['description'] += de
        yield item

    def start_requests(self):
        base_url = 'https://book.qidian.com/info/'
        id_url = self.settings.get('FLASK_URL')
        # 利用 web api 获取书籍id
        ids = requests.get(id_url).json()
        for id in ids:
            id = id[0]
            url = base_url + id
            # 带上参数小说id
            yield Request(url=url, meta={'novel_id':id}, callback=self.parse)

 
        
