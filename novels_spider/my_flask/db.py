import pymysql
from my_flask.setting import MYSQL_HOST, MYSQL_DATABASE, MYSQL_USER, MYSQL_PASSWORD, MYSQL_PORT

class MySqlClient(object):
    def __init__(self, host=MYSQL_HOST, port=MYSQL_PORT, user=MYSQL_USER, password=MYSQL_PASSWORD, database=MYSQL_DATABASE):
        """
        初始化
        :param host: MySQL 地址
        :param port: MySQL 端口
        :param user: MySQL 用户
        :param password: MySQL 密码
        :param database: MySQL 数据库名
        """
        self.db = pymysql.connect(host, user, password, database, charset='utf8', port=port)
        self.cursor = self.db.cursor()

    def get_novelsId(self):
        """
        返回所有的小说id
        """
        sql = 'SELECT id FROM novel'
        self.cursor.execute(sql)
        print('Count:', self.cursor.rowcount)
        # 结果是tuple类型
        results = self.cursor.fetchall()
        return results
    
    def get_chaptersId(self):
        """
        返回所有的小说id 和章节id 和是否来自起点（标志该章节有内容，且是正版）
        """
        sql = 'SELECT novel_id, id, is_qidian, is_vip FROM chapter'
        self.cursor.execute(sql)
        print('Count:', self.cursor.rowcount)
        # 结果是tuple类型
        results = self.cursor.fetchall()
        print(type(results))
        return results
        
if __name__ == '__main__':
    mysql = MySqlClient()
    mysql.get_novelsId()
    mysql.get_chaptersId()
    