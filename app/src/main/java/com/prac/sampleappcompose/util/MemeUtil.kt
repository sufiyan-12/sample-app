package com.prac.sampleappcompose.util

import com.prac.sampleappcompose.models.Meme

class MemeUtil {
    companion object {

        /**
         * this function update current list character frequency map
         * to display in bottom sheet
         */
        @JvmStatic
        fun statisticsBottomSheet(itemList: List<Meme>): Map<Char, Int> {
            val charFrequency = mutableMapOf<Char, Int>()

            // calculate character frequency
            itemList.forEach { meme ->
                meme.title?.forEach { char ->
                    if (char.isLetter()) {
                        charFrequency[char] = charFrequency.getOrDefault(char, 0) + 1
                    }
                }
            }

            // return top 3 characters
            return charFrequency
                .toList()
                .sortedByDescending { it.second }
                .take(3)
                .toMap()
        }
    }
}