# -*- coding: utf-8 -*-

# Define your item pipelines here
#
# Don't forget to add your pipeline to the ITEM_PIPELINES setting
# See: https://doc.scrapy.org/en/latest/topics/item-pipeline.html


import pymysql
from novels.items import *
import time, threading

class NovelPipeline():
    def __init__(self, host, database, user, password, port):
        self.host = host
        self.database = database
        self.user = user
        self.password = password
        self.port = port

    @classmethod
    def from_crawler(cls, crawler):
        return cls(
            host = crawler.settings.get('MYSQL_HOST'),
            database = crawler.settings.get('MYSQL_DATABASE'),
            user = crawler.settings.get('MYSQL_USER'),
            password = crawler.settings.get('MYSQL_PASSWORD'),
            port = crawler.settings.get('MYSQL_PORT')
        )
    
    def open_spider(self, spider):
        self.db = pymysql.connect(self.host, self.user, self.password, self.database, charset='utf8', port=self.port)
        self.cursor = self.db.cursor()

    def close_spider(self, spider):
        self.db.close()

    def process_item(self, item, spider):
        if isinstance(item, NovelItem):
            keys = 'author, cover_thumb, id, name, category_id'
            values = [item['author'], item['cover_thumb'], item['id'], item['name'], item['category_id']]
            values_sql = ', '.join(['%s'] * len(values))
            sql = 'insert into %s (%s) values(%s)' % (item.table, keys, values_sql)
            # print(sql)
            try:
                self.cursor.execute(sql, tuple(values))
                self.db.commit()
            except:
                self.db.rollback()
        return item

class NovelDetailsPipeline():
    def __init__(self, host, database, user, password, port):
        self.host = host
        self.database = database
        self.user = user
        self.password = password
        self.port = port
   
    @classmethod
    def from_crawler(cls, crawler):
        return cls(
            host = crawler.settings.get('MYSQL_HOST'),
            database = crawler.settings.get('MYSQL_DATABASE'),
            user = crawler.settings.get('MYSQL_USER'),
            password = crawler.settings.get('MYSQL_PASSWORD'),
            port = crawler.settings.get('MYSQL_PORT'),
        )
    
    def open_spider(self, spider):
        self.db = pymysql.connect(self.host, self.user, self.password, self.database, charset='utf8', port=self.port)
        self.cursor = self.db.cursor()

    def close_spider(self, spider):
        self.db.close()

    def process_item(self, item, spider):
        if isinstance(item, NovelDetailsItem):
            sql = "update %s set description='%s',cover_image='%s',tickets_month=%s,tickets_recommend=%s where id = '%s'" % (item.table, item['description'],
                item['cover_image'], item['tickets_month'], item['tickets_recommend'], item['novel_id']
            )
            try:
                self.cursor.execute(sql)
                self.db.commit()
            except:
                self.db.rollback()
        return item


class ChapterPipeline():
    def __init__(self, host, database, user, password, port):
        self.host = host
        self.database = database
        self.user = user
        self.password = password
        self.port = port
        self.count = 0
        self.commit_count = 500
        self.value = []
        self.is_commit = 0
     
    @classmethod
    def from_crawler(cls, crawler):
        return cls(
            host = crawler.settings.get('MYSQL_HOST'),
            database = crawler.settings.get('MYSQL_DATABASE'),
            user = crawler.settings.get('MYSQL_USER'),
            password = crawler.settings.get('MYSQL_PASSWORD'),
            port = crawler.settings.get('MYSQL_PORT'),
        )
    
    def open_spider(self, spider):
        self.db = pymysql.connect(self.host, self.user, self.password, self.database, charset='utf8', port=self.port)
        self.cursor = self.db.cursor()
    
    def close_spider(self, spider):
        if self.is_commit == 0 and hasattr(self, 'item'):
                try:
                    sql = 'insert into %s (%s) values' % (self.item.table, self.keys)
                    for ll in self.value:
                        ll = list(ll)
                        sql += "('{v1}','{v2}','{v3}','{v4}',{v5},'{v6}'),".format(v1=ll[0],v2=ll[1],v3=ll[2],v4=ll[3],v5=ll[4],v6=ll[5])
                    sql = sql[0:-1]
                    self.cursor.execute(sql)
                    self.db.commit()
                    spider.logger.info('剩余插入数据插入成功')
                    self.is_commit = 1
                    self.count = 0
                    self.value = []
                    return self.item
                except:
                    self.db.rollback()
        self.db.close()


    def process_item(self, item, spider):
        if isinstance(item, ChapterItem):
            # 考虑到性能问题数据库插入的时候不要按章插入，应该批量插入,这里初步考虑批量提交200章
            self.count += 1
            data = dict(item)
            self.value.append(data.values())
            # for v in data.values():
            #     self.value.append(v)
               #spider.logger.info('value:')
            self.keys = keys = ', '.join(data.keys())
            #spider.logger.info(self.values_sql)
            self.item = item
            self.is_commit = 0
            if self.count >= self.commit_count:
                try:
                    # spider.logger.info('enter')
                    sql = 'insert into %s (%s) values' % (item.table, keys)
                    for ll in self.value:
                        ll = list(ll)
                        sql += "('{v1}','{v2}','{v3}','{v4}',{v5},'{v6}'),".format(v1=ll[0],v2=ll[1],v3=ll[2],v4=ll[3],v5=ll[4],v6=ll[5])
                    sql = sql[0:-1]
                    # spider.logger.info('正在更新日期。。。')
                    # spider.logger.info('count:' + str(self.count))
                    # spider.logger.info('sql:' + sql)
                    # spider.logger.info('len:' + str(len(self.value)))
                    self.cursor.execute(sql)
                    self.db.commit()
                    # spider.logger.info('success')
                    self.is_commit = 1
                    self.count = 0
                    self.value = []
                except:
                    spider.logger.info('插入失败：' + sql)
                    self.db.rollback()
        return item

class ContentPipeline():
    def __init__(self, host, database, user, password, port):
        self.host = host
        self.database = database
        self.user = user
        self.password = password
        self.port = port
        self.count = 0
        self.commit_count = 50
        self.update_list = []
        self.is_commit = 0
   
    @classmethod
    def from_crawler(cls, crawler):
        return cls(
            host = crawler.settings.get('MYSQL_HOST'),
            database = crawler.settings.get('MYSQL_DATABASE'),
            user = crawler.settings.get('MYSQL_USER'),
            password = crawler.settings.get('MYSQL_PASSWORD'),
            port = crawler.settings.get('MYSQL_PORT'),
        )
    
    def open_spider(self, spider):
        self.db = pymysql.connect(self.host, self.user, self.password, self.database, charset='utf8', port=self.port)
        self.cursor = self.db.cursor()

    def close_spider(self, spider):
        if self.is_commit == 0 and hasattr(self, 'item'):
                try:
                    sql = 'INSERT INTO %s(id, is_qidian,content,is_vip) VALUES' % (self.item.table)
                    for l in self.update_list:
                        sql += "('{v1}',{v2}, '{v3}',{v4}),".format(v1=l[0],v2=l[1],v3=l[2],v4=l[3])
                    sql = sql[0:-1]
                    sql += 'on duplicate key update is_qidian=values(is_qidian),content=VALUES(content),is_vip=values(is_vip)'
                    self.cursor.execute(sql)
                    self.db.commit()
                    spider.logger.info('插入剩余章节内容成功')
                    self.is_commit = 1
                    self.count = 0
                    self.update_list = []
                except:
                    spider.logger.info('插入剩余章节内容失败：' + sql)
                    self.db.rollback()
        self.db.close()

    def process_item(self, item, spider):
        if isinstance(item, ContentItem):
            #spider.logger.info('enter')
            # 提高效率批量插入
            if item['is_qidian'] == 1:
                self.item = item
                self.count += 1
                l = [item['id'], item['is_qidian'],item['content'],item['is_vip']]
                self.update_list.append(l)
                self.is_commit = 0
                if self.count >= self.commit_count:
                    try:
                        sql = 'INSERT INTO %s(id, is_qidian,content,is_vip) VALUES' % (self.item.table)
                        for l in self.update_list:
                            sql += "('{v1}',{v2}, '{v3}',{v4}),".format(v1=l[0],v2=l[1],v3=l[2],v4=l[3])
                        sql = sql[0:-1]
                        sql += 'on duplicate key update is_qidian=values(is_qidian),content=VALUES(content),is_vip=values(is_vip)'
                        self.cursor.execute(sql)
                        self.db.commit()
                        #spider.logger.info('插入章节内容成功:' + self.item['id'] + 'sql: ' + sql)
                        self.is_commit = 1
                        self.count = 0
                        self.update_list = []
                    except:
                        spider.logger.info('插入章节内容失败：' + sql)
                        self.db.rollback()
        return item

