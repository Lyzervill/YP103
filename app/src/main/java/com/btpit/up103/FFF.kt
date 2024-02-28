package com.btpit.up103

fun amountToStr(count:Int): String {
    return when(count){
        in 0..<1_000 -> count.toString()
        1000 -> "1K"
        in 999..<10_000 -> ((count/100).toFloat()/10).toString() + "K"
        in 10_000..<1_000_000 -> (count/1_000).toString() + "K"
        1_000_000 -> "1M"
        in 999_999..<10_000_000 -> ((count/100_000).toFloat()/10).toString() + "M"
        in 10_000_000..<1_000_000_000 -> (count/1_000_000).toString() + "M"
        else -> "Более млрд"
    }
}