package com.david

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class RangeServiceSpec extends AnyWordSpec with Matchers{
  trait Initial{
    val alreadyMergedRanges = List(Range(6, 9), Range(12, 18), Range(25, 33))
    val unorderRanges = List(Range(12, 18), Range(6, 9), Range(25, 33))
    val unmergedRanges = List(Range(12, 18), Range(6, 14), Range(25, 33))
  }

  "RangeService" should {
    "merge a new range into a list of ranges" when {
      "new item is before all other ranges" in new Initial (){
        val range = Range(1, 3)
        val result = RangeService.mergeRange(alreadyMergedRanges, range)
        result should equal(range :: alreadyMergedRanges)
      }

      "new item is in the middle of other ranges" in new Initial (){
        val range = Range(20, 22)
        val result = RangeService.mergeRange(alreadyMergedRanges, range)
        result should equal(List(Range(6, 9), Range(12, 18), Range(20, 22), Range(25, 33)))
      }

      "new item is at the end of all the ranges" in new Initial (){
        val range = Range(35, 40)
        val result = RangeService.mergeRange(alreadyMergedRanges, range)
        result should equal(alreadyMergedRanges ::: List(range))
      }

      "new item collide with first range of the list" in new Initial (){
        val range = Range(3, 8)
        val result = RangeService.mergeRange(alreadyMergedRanges, range)
        result should equal(List(Range(3, 9), Range(12, 18), Range(25, 33)))
      }

      "new item collide with first ranges of the list" in new Initial (){
        val range = Range(3, 14)
        val result = RangeService.mergeRange(alreadyMergedRanges, range)
        result should equal(List(Range(3, 18), Range(25, 33)))
      }

      "new item collide with range in the middle of the list" in new Initial (){
        val range = Range(17, 20)
        val result = RangeService.mergeRange(alreadyMergedRanges, range)
        result should equal(List(Range(6, 9), Range(12, 20), Range(25, 33)))
      }

      "new item collide with ranges in the middle of the list" in new Initial (){
        val range = Range(17, 27)
        val result = RangeService.mergeRange(alreadyMergedRanges, range)
        result should equal(List(Range(6, 9), Range(12, 33)))
      }

      "new item collide with last range of the list" in new Initial (){
        val range = Range(30, 38)
        val result = RangeService.mergeRange(alreadyMergedRanges, range)
        result should equal(List(Range(6, 9), Range(12, 18), Range(25, 38)))
      }

      "new item collide with ranges in the middle of the list in an unordered list" in new Initial (){
        val range = Range(17, 27)
        val result = RangeService.mergeRange(unorderRanges, range)
        result should equal(List(Range(6, 9), Range(12, 33)))
      }

      "new item collide with ranges in the beginning of the list in an unordered list" in new Initial (){
        val range = Range(3, 14)
        val result = RangeService.mergeRange(unorderRanges, range)
        result should equal(List(Range(3, 18), Range(25, 33)))
      }

      "new item collide with ranges in the middle of the list in an unmerged list" in new Initial (){
        val range = Range(17, 27)
        val result = RangeService.mergeRange(unmergedRanges, range)
        result should equal(List(Range(6, 33)))
      }
    }
  }
}
