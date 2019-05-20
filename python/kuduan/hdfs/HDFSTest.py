import sys
from hdfs.client import Client

reload(sys)
sys.setdefaultencoding("utf-8")


#读取hdfs文件内容,将每行存入数组返回
def read_hdfs_file(client, filename):
    lines = []
    with client.read(filename, encoding='utf-8', delimiter='\n') as reader:
        for line in reader:
            lines.append(line.strip())
    return lines

client = Client("http://192.168.70.101:50070")
read_hdfs_file(client, '/test/test.log')