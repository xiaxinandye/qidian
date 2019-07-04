# -*- coding: utf-8 -*-

# Define here the models for your scraped items
#
# See documentation in:
# https://doc.scrapy.org/en/latest/topics/items.html

import scrapy


class NovelItem(scrapy.Item):
    id = scrapy.Field()
    name = scrapy.Field()
    author = scrapy.Field()
    cover_thumb = scrapy.Field()
    table = 'novel'
    category_id = scrapy.Field()

class NovelDetailsItem(scrapy.Item):
    description = scrapy.Field()
    cover_image = scrapy.Field()
    tickets_month = scrapy.Field()
    tickets_recommend = scrapy.Field()
    novel_id = scrapy.Field()
    table = 'novel' 

class ChapterItem(scrapy.Item):
    id = scrapy.Field()
    novel_id = scrapy.Field()
    volume_name = scrapy.Field()
    name = scrapy.Field()
    word_counts = scrapy.Field()
    create_datetime = scrapy.Field()
    table = 'chapter'

class ContentItem(scrapy.Item):
    id = scrapy.Field()
    is_qidian = scrapy.Field()
    is_vip = scrapy.Field()
    content = scrapy.Field()
    table = 'chapter'
