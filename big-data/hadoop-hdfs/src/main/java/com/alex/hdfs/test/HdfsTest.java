package com.alex.hdfs.test;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.apache.hadoop.hdfs.protocol.DatanodeInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName HdfsTest
 * @Description todo
 * @Author AlexWang
 * @Date 2019/5/6 0006 10:52
 * @Version 1.0
 **/

public class HdfsTest {
    public static void main(String[] args) {

        try {
//            copyFile();

//            writeDataToHDFS();

//            readFile();

//            createDir();

//            renameFileName();

//            deleteFile();

//            exists();

//            fileStatus();

//            getPathAllFile();

//            findLocalPathInfo();

            getHostName();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // 写入hdfs
    public static void writeDataToHDFS() throws IOException {
        FileSystem hdfs = getFileSystem();

        byte[] bytes = "hello hdfs\n".getBytes();
        Path dfsPath = new Path("/test.txt");
        FSDataOutputStream outputStream = hdfs.create(dfsPath);
        outputStream.write(bytes);
        outputStream.close();
    }

    // 读取hdfs文件
    public static void readFile() throws IOException {
        FileSystem hdfs = getFileSystem();

        Path dfsPath = new Path("/test.txt");
        FSDataInputStream inputStream = hdfs.open(dfsPath);
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

        String line;
        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }
        inputStream.close();
        br.close();
        hdfs.close();
    }

    // 上传本地文件
    public static void copyFile() throws IOException {
        FileSystem hdfs = getFileSystem();

        Path srcPath = new Path("D:\\tmp\\test.txt");
        Path dstPath = new Path("/user/alex/test");

        hdfs.copyFromLocalFile(srcPath, dstPath);

        FileStatus[] files = hdfs.listStatus(dstPath);

        for (FileStatus status : files) {
            System.out.println(status.getPath());
        }
    }

    // 创建HDFS目录
    public static void createDir() throws IOException {
        FileSystem hdfs = getFileSystem();

        Path path = new Path("/TextDir");
        hdfs.mkdirs(path);

        System.out.println(hdfs.exists(path));
    }

    // 重命名HDFS文件
    public static void renameFileName() throws IOException {
        FileSystem hdfs = getFileSystem();

        Path oldName = new Path("/test.txt");
        Path newName = new Path("/newTest.txt");

        hdfs.rename(oldName, newName);
        System.out.println(hdfs.exists(newName));
    }

    // 删除HDFS文件
    public static void deleteFile() throws IOException {
        FileSystem hdfs = getFileSystem();

        Path deletePath = new Path("/newTest.txt");

        hdfs.delete(deletePath, true);
    }

    // 查看HDFS文件是否存在
    public static void exists() throws IOException {
        FileSystem hdfs = getFileSystem();

        Path findPath = new Path("/test/test.txt");
        System.out.println("文件是否存在：" + hdfs.exists(findPath));
    }

    // 查看HDFS文件的信息状态
    public static void fileStatus() throws IOException {
        FileSystem hdfs = getFileSystem();

        Path findPath = new Path("/user/alex/test/test.txt");

        FileStatus fileStatus = hdfs.getFileStatus(findPath);

        long accessTime = fileStatus.getAccessTime();
        long modeTime = fileStatus.getModificationTime();
        long size = fileStatus.getBlockSize();
        long len = fileStatus.getLen();
        String owner = fileStatus.getOwner();
        Path path = fileStatus.getPath();
        String group = fileStatus.getGroup();

        //将时间戳转换为格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat();
        String time1 = sdf.format(new Date(accessTime));
        String time2 = sdf.format(new Date(modeTime));

        //获取文件的权限信息
        System.out.println("文件的权限：" + fileStatus.getPermission());
        System.out.println("文件创建时间：" + time1);
        System.out.println("文件修改时间：" + time2);
        System.out.println("HDFS文件块大小：" + size);
        System.out.println("文件大小：" + len);
        System.out.println("文件所有者：" + owner);
        System.out.println("文件所在路径：" + path);
        System.out.println("文件所属组：" + group);
    }

    // 读取HDFS某个目录下的所有文件
    public static void getPathAllFile() throws IOException {
        FileSystem hdfs = getFileSystem();

        Path dirPath = new Path("/user/alex/test/");
        FileStatus[] status = hdfs.listStatus(dirPath);

        for (int i = 0; i < status.length; i++) {
            System.out.println(status[i].getPath().toString());
        }
        hdfs.close();
    }

    // 查找某个文件在HDFS集群的位置
    public static void findLocalPathInfo() throws IOException {
        FileSystem hdfs = getFileSystem();
        Path path = new Path("/user/alex/test/test.txt");

        FileStatus status = hdfs.getFileStatus(path);

        BlockLocation[] blockLocations = hdfs.getFileBlockLocations(status, 0, status.getLen());

        for (int i = 0; i < blockLocations.length; i++) {
            String[] hosts = blockLocations[i].getHosts();
            System.out.println("block: " + i + " ; Location: " + hosts[0]);
        }
    }

    // 获取HDFS集群上所有节点名称信息
    public static void getHostName() throws IOException {
        FileSystem hdfs = getFileSystem();

        DistributedFileSystem fileSystem = (DistributedFileSystem) hdfs;
        DatanodeInfo[] datanodeInfos = fileSystem.getDataNodeStats();

        for (int i = 0; i < datanodeInfos.length; i++) {
            System.out.println("DataNode_" + i + " _Name: " + datanodeInfos[i].getHostName());
        }
     }

    public static FileSystem getFileSystem() throws IOException {
        System.setProperty("HADOOP_USER_NAME", "alex");
        Configuration config = new Configuration();
        config.set("fs.defaultFS", "hdfs://192.168.70.101:9000");
        FileSystem hdfs = FileSystem.get(config);
        return hdfs;
    }
}
