package com.david

case class Range(begin: Int, end: Int){
  val minFunc = (a: Int, b: Int) => if(a<b) a else b
  val maxFunc = (a: Int, b: Int) => if(a>b) a else b

  def < (other: Range) = this.end < other.begin
  def > (other: Range) = !(this < other)

  def add(other: Range):List[Range] = {
    val overlaps = maxFunc(this.begin, other.begin) <= minFunc(this.end, other.end)
    if(overlaps) List(Range(minFunc(this.begin, other.begin), maxFunc(this.end, other.end)))
    else if(this<other) other :: this :: Nil
    else this :: other :: Nil
  }
}