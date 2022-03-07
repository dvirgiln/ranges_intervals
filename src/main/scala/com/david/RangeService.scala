package com.david

object RangeService {
  def mergeRange(ranges: List[Range], range: Range): List[Range] ={
    ranges.foldLeft(List(range)){ case (x :: xs, r) => x.add(r) ::: xs}.reverse
  }
}
