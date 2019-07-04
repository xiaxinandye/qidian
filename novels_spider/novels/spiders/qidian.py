# -*- coding: utf-8 -*-
from scrapy import Spider, Request
from urllib.parse import urlencode
from novels.items import NovelItem

class QidianSpider(Spider):
    name = 'qidian'
    allowed_domains = ['qidian.com']
    start_urls = ['http://qidian.com/']

    def parse(self, response):
        books = response.css('.book-img-text li')

        for book in books:
            item = NovelItem()
            item['id'] = book.css('.book-img-box a::attr("data-bid")').extract_first()
            # 日志打印
            self.logger.debug(item['id'])
            # 使用 response.urljoin 方法获取绝对图片src
            item['cover_thumb'] = response.urljoin(book.css('.book-img-box img::attr("src")').extract_first())
            self.logger.debug(item['cover_thumb'])
            item['name'] = book.css('.book-mid-info a::text').extract_first()
            self.logger.debug(item['name'])
            item['author'] = book.css('.author .name::text').extract_first()
            self.logger.debug(item['author'])
            item['category_id'] = response.meta['category_id']
            self.logger.debug(item['category_id'])
            yield item

    def start_requests(self):
        # 分别对应玄幻、奇幻、武侠、仙侠、都市、现实、军事、历史、游戏、体育、科幻、悬疑灵异、二次元 共13个分类id
        # category = ['21', '1', '2', '22', '4', '15', '6', '5', '7', '8', '9', '10', '12']
        # 但是现实和都市的内容是一样的，考虑剔除掉现实这一分类
        category = self.settings.get('CATEGORY').values()
        data = {}
        base_url = 'https://www.qidian.com/rank/yuepiao?'
        for chn in category:
            data['chn'] = chn
            for page in range(1, self.settings.get('MAX_PAGE') + 1):
                data['page'] = page
                params = urlencode(data)
                url = base_url + params
                #带上分类id
                yield Request(url=url, meta={'category_id':data['chn']},callback=self.parse)
     

