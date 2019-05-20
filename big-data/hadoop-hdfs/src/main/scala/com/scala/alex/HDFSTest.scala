import com.scala.alex.HDFSUtils

object HDFSTest{


  def main(args: Array[String]): Unit = {
    System.setProperty("HADOOP_USER_NAME", "alex")
//    println(HDFSUtils.mkdir("/alex"))

//    println(HDFSUtils.deleteDir("/alex"))

//    val localFile = "D:\\tmp\\test.scala"
//    println(HDFSUtils.uploadLocalFile2HDFS(localFile, "/alex"))

//    println(HDFSUtils.listAll("/alex"))


//    println(HDFSUtils.createNewHDFSFile("/alex/hey", "this is my test\nthis is my test\nthis is my test\nthis is my test\nthis is my test\n"))
//    println(HDFSUtils.deleteHDFSFile("/alex/test.scala"))
//    println(HDFSUtils.readHDFSFile("/alex/hey").toString)



    testMkdirNull()
  }

  def testMkdirNull() : Unit = {
    assert(true, HDFSUtils.mkdir("/alex1"))
  }

}