package com.scala.alex

import java.io.ByteArrayInputStream
import java.net.URI
import java.util.{ArrayList, List}

import org.apache.commons.lang.StringUtils
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.hadoop.io.IOUtils

object HDFSUtils {
  val hdfsUrl = "hdfs://192.168.70.101:9000"
  var realUrl = ""

  /**
    * make a new dir in the HDFS
    * @param dir
    * @return
    */
  def mkdir(dir : String) : Boolean = {
    var result = false

    if (StringUtils.isNotBlank(dir)) {
      realUrl = hdfsUrl + dir
      val config = new Configuration()
      val fs = FileSystem.get(URI.create(realUrl), config)
      if (!fs.exists(new Path(realUrl))) {
        fs.mkdirs(new Path(realUrl))
      }
      fs.close()
      result = true
    }
    result
  }

  /**
    * delete a dir in the HDFS
    * if dir not exists, it will throw FileNotFoundException
    * @param dir
    * @return
    */
  def deleteDir(dir : String) : Boolean = {
    var result = false
    if (StringUtils.isNotBlank(dir)) {
      realUrl = hdfsUrl + dir
      val config = new Configuration()
      val fs = FileSystem.get(URI.create(realUrl), config)
      fs.delete(new Path(realUrl), true)
      fs.close()
      result = true
    }
    result
  }

  /**
    * list files/directories/links names under a directory
    * not include embed objects
    *
    * @param dir
    * @return
    */
  def listAll(dir : String) : List[String] = {
    val names : List[String] = new ArrayList[String]()
    if (StringUtils.isNotBlank(dir)) {
      realUrl = hdfsUrl + dir
      val config = new Configuration()
      val fs = FileSystem.get(URI.create(realUrl), config)
      val stats = fs.listStatus(new Path(realUrl))
      for (i <- 0 to stats.length -1) {
        if (stats(i).isFile) {
          names.add(stats(i).getPath.toString)
        } else if (stats(i).isDirectory) {
          names.add(stats(i).getPath.toString)
        } else if (stats(i).isSymlink) {
          names.add(stats(i).getPath.toString)
        }
      }
    }
    names
  }

  /**
    * upload the local file to the hdfs
    * notice that the path is full like /tmp/test.txt
    * if local file not exists, it will throw a FileNotFoundException
    *
    * @param localFile
    * @param hdfsFile
    * @return
    */
  def uploadLocalFile2HDFS(localFile : String, hdfsFile : String) : Boolean = {
    var result = false
    if (StringUtils.isNotBlank(localFile) && StringUtils.isNotBlank(hdfsFile)) {
      realUrl = hdfsUrl + hdfsFile
      val config = new Configuration()
      val hdfs = FileSystem.get(URI.create(realUrl), config)
      val src = new Path(localFile)
      val dst = new Path(realUrl)
      hdfs.copyFromLocalFile(src, dst)
      hdfs.close()
      result = true
    }
    result
  }

  /**
    * create a new file in the hdfs.
    * notice that the to create file path is the full path and write the content to the hdfs file.
    *
    * @param newFile
    * @param content
    * @return
    */
  def createNewHDFSFile(newFile : String, content : String) : Boolean = {
    var result = false
    if (StringUtils.isNotBlank(newFile) && StringUtils.isNotBlank(content)) {
      realUrl = hdfsUrl + newFile
      val config = new Configuration()
      val hdfs = FileSystem.get(URI.create(realUrl), config)
      val os = hdfs.create(new Path(realUrl))
      os.write(content.getBytes("UTF-8"))
      os.close()
      hdfs.close()
      result = true
    }
    result
  }

  /**
    * delete the hdfs file
    * @param hdfsFile
    * @return
    */
  def deleteHDFSFile(hdfsFile : String) : Boolean = {
    var result = false
    if (StringUtils.isNotBlank(hdfsFile)) {
      realUrl = hdfsUrl + hdfsFile
      val config = new Configuration()
      val hdfs = FileSystem.get(URI.create(realUrl), config)
      val path = new Path(realUrl)
      val isDeleted = hdfs.delete(path, true)
      hdfs.close()
      result = true
    }

    result
  }

  /**
    * read the hdfs file content
    * @param hdfsFile
    * @return
    */
  def readHDFSFile(hdfsFile : String) : Array[Byte] = {
    var result = new Array[Byte](0)
    if (StringUtils.isNotBlank(hdfsFile)) {
      realUrl = hdfsUrl + hdfsFile
      val config = new Configuration()
      val hdfs = FileSystem.get(URI.create(realUrl), config)
      val path = new Path(realUrl)
      if (hdfs.exists(path)) {
        val inputStream = hdfs.open(path)
        val stat = hdfs.getFileStatus(path)
        val length = stat.getLen.toInt
        val buffer = new Array[Byte](length)
        inputStream.readFully(buffer)
        inputStream.close()
        hdfs.close()
        result = buffer
      }
    }
    result
  }

  /**
    * append something to file dst
    * @param hdfsFile
    * @param content
    * @return
    */
  def append(hdfsFile : String, content : String) : Boolean = {
    var result = false
    if (StringUtils.isNotBlank(hdfsFile) && StringUtils.isNotBlank(content)) {
      realUrl = hdfsUrl + hdfsFile
      val config = new Configuration()
      config.set("dfs.client.block.write.replace-datanode-on-failure.policy", "NEVER")
      config.set("dfs.client.block.write.replace-datanode-on-failure.enable", "true")

      val hdfs = FileSystem.get(URI.create(realUrl), config)
      val path = new Path(realUrl)

      if (hdfs.exists(path)) {
        val inputStream = new ByteArrayInputStream(content.getBytes())
        val outputStream = hdfs.append(path)
        IOUtils.copyBytes(inputStream, outputStream, 4096, true)
        outputStream.close()
        inputStream.close()
        hdfs.close()
        result = true
      }
    } else {
      createNewHDFSFile(hdfsFile, content)
      result = true
    }
    result
  }
}
