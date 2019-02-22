package com.alex.test.some

object Test {
  def main(args: Array[String]): Unit = {
//    println( apply(layout, 10))


  }

  def printFactorial(): Unit = {
//    for (i )
  }

  //阶乘
  def factorial(i: Int): Int = {
    def fact(i: Int, accumulator: Int): Int = {
      if ( i <= 1 ) {
        accumulator
      } else {
        fact(i-1, i * accumulator)
      }
    }
    fact(i, 1)
  }

  def apply(f: Int => String, v: Int) = f(v)

  def layout[A](x: A) = "[" + x.toString + "]"
}
