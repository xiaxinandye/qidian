# -*- coding: utf-8 -*-
from scrapy import Spider, Request
import requests
import re
from novels.items import ChapterItem
import json
from datetime import datetime

class ChapterSpider(Spider):
    name = 'chapter'
    allowed_domains = ['qidian.com']
    start_urls = ['http://qidian.com/']

    def parse(self, response):
        """
        提取出章节的有关信息
        """
        volumes_data = response.xpath('//script/text()').re('g_data.volumes = (.*)?;')[0] # type str
         # 这里字符串转换为 json 时可能会出现问题，如："cN":"1475章 达成"大富翁"称号","
         # 即 value 里面还有双引号，所以我们要将这些双引号变为中文的双引号,也要替换单引号便于正确插入到数据库
        p = re.compile('{.*?"cN":"(.*?)","uT"')
        results = re.findall(p, volumes_data)
        for result in results:
            if result.find('"'):
                volumes_data = volumes_data.replace(result, result.replace('"', '“'))
            if result.find("'"):
                volumes_data = volumes_data.replace(result, result.replace("'", "‘"))
        volumes = volumes_data
        # with open('data.json', 'w', encoding='utf-8') as f:
        #     f.write(volumes)
        volumes = json.loads(volumes)

        for volume in volumes:
            # volume 形如{"vId":52444831,"cCnt":1,"vS":0,"isD":0,"vN":"作品相关","cs":[{"uuid":87,"cN":"本书扣扣群","uT":"2018-09-21  14:41","cnt":13,"cU":"","id":427441455,"sS":1,"isLast":true,"_y":-52}],"wC":13,"hS":false}
            # print(type(volume)) # dict
            volume_name = volume['vN']
            chapters = volume['cs']
            # print(type(chapters)) # list
            for chapter in chapters:
                # 判断该章节信息是否已存在
                # self.logger.info('章节类型为：' + str(type(chapter['id']))) int
                # self.logger.info('值为：' + chapter['id'])
                if str(chapter['id']) in self.list_cid:
                    pass
                    # self.logger.info('该章节信息已存在:'  + str(chapter['id']))
                else:
                    item = ChapterItem()
                    item['novel_id'] = response.meta['novel_id']
                    # 从请求参数中获取小说id    
                    item['id'] = chapter['id']
                    item['volume_name'] = volume_name
                    item['name'] = chapter['cN']
                    item['word_counts'] = chapter['cnt']
                    item['create_datetime'] = chapter['uT']
                    yield item
          
    def start_requests(self):
        base_url = 'https://m.qidian.com/book/'
        # 利用 web api 获取章节id
        chaptersid_url = self.settings.get('CHAPTER_URL')
        chapter_ids = requests.get(chaptersid_url).json()
        self.list_cid = []
        for ids in chapter_ids:
            cid = ids[1]
            self.list_cid.append(cid)
        self.list_cid = set(self.list_cid) #装换为set极大地提升了效率
        id_url = self.settings.get('FLASK_URL')
        # 利用 web api 获取书籍id
        ids = requests.get(id_url).json()
        for id in ids:
            nid = id[0]
            url = base_url + nid + '/catalog'
            # 带上参数小说id
            yield Request(url=url, meta={'novel_id':nid}, callback=self.parse)
        
        # yield Request(url='https://m.qidian.com/book/1012237441', meta={'novel_id':'1012237441'}, callback=self.parse)
      