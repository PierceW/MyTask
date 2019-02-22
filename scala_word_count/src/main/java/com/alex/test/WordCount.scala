package com.alex.test

import org.apache.flink.api.scala._
import org.apache.flink.core.fs.FileSystem.WriteMode

object WordCount {
  def main(args: Array[String]): Unit = {
    val env = ExecutionEnvironment.createLocalEnvironment(1)

    // 从本地读取文件
    val text = env.readTextFile("C:/document/cdr/20180505-1556.log")

    // 单词统计
//    val counts = text.flatMap { _.toLowerCase.split("\\W+") filter { _.nonEmpty } }
//      .map { (_, 1) }
//      .groupBy(0)
//      .sum(1)

    val counts = text.flatMap(_.toLowerCase.split("\\W+"))
        .map((_, 1)).groupBy(0).sum(1)

    //输出结果
    counts.print()

    //保存文件到txt
    counts.writeAsText("c:/output.txt", WriteMode.OVERWRITE)
    env.execute("Scala WordCount Example")
  }

}