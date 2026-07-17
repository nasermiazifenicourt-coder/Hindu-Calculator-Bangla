package com.example

import java.io.Serializable

data class Heir(
    val id: String,
    val nameEn: String,
    val nameBn: String,
    val priority: String,
    val category: String = "সপিণ্ড (Sapinda)"
) : Serializable

data class HeirShare(
    val heir: Heir,
    val shareFraction: String,
    val shareAmount: Double,
    val isHeir: Boolean,
    val reason: String,
    val count: Int = 1
) : Serializable

val allHeirs = listOf(
    Heir("son", "Son", "পুত্র", "1"),
    Heir("grandson", "Son's Son (Grandson)", "পুত্রের পুত্র", "2"),
    Heir("great_grandson", "Son's Son's Son (Great-Grandson)", "পুত্রের পুত্রের পুত্র", "3"),
    Heir("widow", "Widow", "স্ত্রী (পুত্রের স্ত্রী/পুত্রের পুত্রের স্ত্রী)", "4"),
    Heir("daughter", "Daughter", "কন্যা", "5"),
    Heir("daughters_son", "Daughter's Son", "কন্যার পুত্র", "6"),
    Heir("father", "Father", "পিতা", "7"),
    Heir("mother", "Mother", "মাতা", "8"),
    Heir("brother", "Brother", "ভ্রাতা", "9"),
    Heir("brothers_son", "Brother's Son", "ভ্রাতুষ্পুত্র", "10"),
    Heir("brothers_son_son", "Brother's Son's Son", "ভ্রাতুষ্পুত্রের পুত্র", "11"),
    Heir("sisters_son", "Sister's Son", "বোনের পুত্র", "12"),
    Heir("paternal_grandfather", "Paternal Grandfather", "পিতার পিতা", "13"),
    Heir("paternal_grandmother", "Paternal Grandmother", "পিতার মাতা", "14"),
    Heir("paternal_uncle", "Paternal Uncle", "পিতার ভ্রাতা", "15"),
    Heir("paternal_uncles_son", "Paternal Uncle's Son", "পিতার ভ্রাতার পুত্র", "16"),
    Heir("paternal_uncles_son_son", "Paternal Uncle's Son's Son", "পিতার ভ্রাতা পুত্রের পুত্র", "17"),
    Heir("fathers_sisters_son", "Father's Sister's Son", "পিতার ভগ্নীর পুত্র", "18"),
    Heir("paternal_great_grandfather", "Paternal Great-Grandfather", "পিতার পিতার পিতা", "19"),
    Heir("paternal_great_grandmother", "Paternal Great-Grandmother", "পিতার পিতার মাতা", "20"),
    Heir("paternal_great_uncle", "Paternal Great-Uncle", "পিতার পিতার ভ্রাতা", "21"),
    Heir("paternal_great_uncles_son", "Paternal Great-Uncle's Son", "পিতার খুড়ার পুত্র", "22"),
    Heir("paternal_great_uncles_son_son", "Paternal Great-Uncle's Son's Son", "পিতার খুড়ার পুত্রের পুত্র", "23"),
    Heir("fathers_fathers_sisters_son", "Father's Paternal Aunt's Son", "পিতার পিসির পুত্র", "24"),
    Heir("sons_daughters_son", "Son's Daughter's Son", "পুত্রের কন্যার পুত্র", "25"),
    Heir("sons_sons_daughters_son", "Son's Son's Daughter's Son", "পুত্রের পুত্রের কন্যার পুত্র", "26"),
    Heir("brothers_daughter_son", "Brother's Daughter's Son", "ভ্রাতার কন্যার পুত্র", "27"),
    Heir("brothers_sons_daughters_son", "Brother's Son's Daughter's Son", "ভ্রাতার পুত্রের কন্যার পুত্র", "28"),
    Heir("paternal_uncles_daughter_son", "Paternal Uncle's Daughter's Son", "খুড়ার কন্যার পুত্র", "29"),
    Heir("paternal_uncles_sons_daughters_son", "Paternal Uncle's Son's Daughter's Son", "খুড়ার পুত্রের কন্যার পুত্র", "30"),
    Heir("paternal_great_uncles_daughter_son", "Paternal Great-Uncle's Daughter's Son", "পিতার খুড়ার কন্যার পুত্র", "31"),
    Heir("paternal_great_uncles_sons_daughters_son", "Paternal Great-Uncle's Son's Daughter's Son", "পিতার খুড়ার পুত্রের কন্যার পুত্র", "32"),
    Heir("maternal_grandfather", "Maternal Grandfather", "مাতার পিতা", "33"),
    Heir("maternal_uncle", "Maternal Uncle", "মামা", "34"),
    Heir("maternal_uncles_son", "Maternal Uncle's Son", "মামার পুত্র", "35"),
    Heir("maternal_uncles_son_son", "Maternal Uncle's Son's Son", "মামার পুত্রের পুত্র", "36"),
    Heir("mothers_sisters_son", "Mother's Sister's Son", "মাসির পুত্র", "37"),
    Heir("maternal_great_grandfather", "Maternal Great-Grandfather", "মাতার পিতার পিতা", "38"),
    Heir("maternal_great_uncle", "Maternal Great-Uncle", "মাতার পিতার ভ্রাতা", "39"),
    Heir("maternal_great_uncles_son", "Maternal Great-Uncle's Son", "মাতার পিতার ভ্রাতার পুত্র", "40"),
    Heir("mothers_fathers_sisters_son", "Mother's Father's Sister's Son", "মাতার পিতার ভগ্নির পুত্র", "41"),
    Heir("mothers_fathers_sisters_son_son", "Mother's Father's Sister's Son's Son", "মাতার পিতার ভগ্নির পুত্রের পুত্র", "42"),
    Heir("maternal_great_great_grandfather", "Maternal Great-Great-Grandfather", "মাতার পিতার পিতার পিতা", "43"),
    Heir("maternal_great_great_uncle", "Maternal Great-Great-Uncle", "মাতার পিতার পিতার ভ্রাতা", "44"),
    Heir("maternal_great_great_uncles_son", "Maternal Great-Great-Uncle's Son", "মাতার পিতার পিতার ভ্রাতার পুত্র", "45"),
    Heir("maternal_great_great_uncles_son_son", "Maternal Great-Great-Uncle's Son's Son", "মাতার পিতার পিতার ভ্রাতার পুত্রের পুত্র", "46"),
    Heir("mothers_fathers_fathers_sisters_son", "Mother's Paternal Great-Aunt's Son", "মাতার পিতার পিতার ভগ্নির পুত্র", "47"),
    Heir("maternal_uncles_daughter_son", "Maternal Uncle's Daughter's Son", "মাতার ভ্রাতার কন্যার পুত্র", "48"),
    Heir("maternal_uncles_sons_daughters_son", "Maternal Uncle's Son's Daughter's Son", "মাতার ভ্রাতার পুত্রের কন্যার পুত্র", "49"),
    Heir("maternal_great_uncles_daughter_son", "Maternal Great-Uncle's Daughter's Son", "মাতার পিতার ভ্রাতার কন্যার পুত্র", "50"),
    Heir("maternal_great_uncles_sons_daughters_son", "Maternal Great-Uncle's Son's Daughter's Son", "মাতার পিতার ভ্রাতার পুত্রের কন্যার পুত্র", "51"),
    Heir("maternal_great_great_uncles_daughter_son", "Maternal Great-Great-Uncle's Daughter's Son", "মাতার পিতার পিতার ভ্রাতার কন্যার পুত্র", "52"),
    Heir("maternal_great_great_uncles_sons_daughters_son", "Maternal Great-Great-Uncle's Son's Daughter's Son", "মাতার পিতার পিতার ভ্রাতার পুত্রের কন্যার পুত্র", "53")
)

fun getBengaliOrdinal(index: Int): String {
    return when (index) {
        1 -> "১ম"
        2 -> "২য়"
        3 -> "৩য়"
        4 -> "৪র্থ"
        5 -> "৫ম"
        6 -> "৬ষ্ঠ"
        7 -> "৭ম"
        8 -> "৮ম"
        9 -> "৯ম"
        10 -> "১০ম"
        else -> "${index}তম"
    }
}

fun getEnglishOrdinal(index: Int): String {
    return when (index) {
        1 -> "1st"
        2 -> "2nd"
        3 -> "3rd"
        4 -> "4th"
        5 -> "5th"
        else -> "${index}th"
    }
}

fun calculateDayabhagaShares(propertyTotal: Double, selectedHeirs: Map<String, Int>): List<HeirShare> {
    val results = mutableListOf<HeirShare>()
    if (selectedHeirs.isEmpty()) return emptyList()

    // Class I (Tier 1) heirs under Dayabhaga with Hindu Women's Right to Property Act of 1937
    val class1Ids = setOf("son", "grandson", "great_grandson", "widow")
    val femaleSapindas = setOf("widow", "daughter", "mother", "paternal_grandmother", "paternal_great_grandmother")

    val activeClass1 = selectedHeirs.filter { it.key in class1Ids && it.value > 0 }

    if (activeClass1.isNotEmpty()) {
        val numSons = activeClass1["son"] ?: 0
        val numGrandsons = activeClass1["grandson"] ?: 0
        val numGreatGrandsons = activeClass1["great_grandson"] ?: 0
        val numWidows = activeClass1["widow"] ?: 0

        val widowShareUnit = if (numWidows > 0) 1 else 0
        val totalSharesCount = numSons + numGrandsons + numGreatGrandsons + widowShareUnit

        for ((id, count) in selectedHeirs) {
            val heir = allHeirs.firstOrNull { it.id == id } ?: continue
            if (id in class1Ids) {
                val fractionDisplay = if (id == "widow" && count > 1) {
                    "1/${totalSharesCount * count}"
                } else {
                    "1/$totalSharesCount"
                }

                val individualAmount = if (id == "widow") {
                    propertyTotal / (totalSharesCount * count)
                } else {
                    propertyTotal / totalSharesCount
                }

                for (i in 1..count) {
                    val individualHeir = if (count > 1) {
                        Heir(
                            id = "${heir.id}_$i",
                            nameEn = "${getEnglishOrdinal(i)} ${heir.nameEn}",
                            nameBn = "${getBengaliOrdinal(i)} ${heir.nameBn}",
                            priority = heir.priority,
                            category = heir.category
                        )
                    } else {
                        heir
                    }

                    var reasonText = if (count > 1) {
                        "স্বাভাবিক অংশীদার (শ্রেণী-১)। মোট $count জনের জনপ্রতি অংশ $fractionDisplay।"
                    } else {
                        "স্বাভাবিক অংশীদার (শ্রেণী-১)। অংশ $fractionDisplay।"
                    }

                    if (id in femaleSapindas) {
                        reasonText += "\nমন্তব্য (সপিণ্ড মহিলা): ${individualHeir.nameBn} একজন সপিণ্ড মহিলা। তিনি জীবনস্বত্বে (Life Interest) এই সম্পত্তির মালিক হবেন। তিনি এটি ভোগ দখল করতে পারবেন কিন্তু হস্তান্তর বা বিক্রি করতে পারবেন না। তবে বিধবা স্ত্রী অত্যন্ত অস্বচ্ছল বা বিশেষ প্রয়োজনে আদালতের মাধ্যমে জমি বিক্রি করতে পারেন। তাঁর মৃত্যুর পর এই সম্পত্তি পুনরায় মূল মৃত ব্যক্তির নিকটবর্তী জীবিত উত্তরাধিকারীদের নিকট ফিরে যাবে।"
                    }

                    results.add(HeirShare(
                        heir = individualHeir,
                        shareFraction = fractionDisplay,
                        shareAmount = individualAmount,
                        isHeir = true,
                        reason = reasonText,
                        count = 1
                    ))
                }
            } else {
                for (i in 1..count) {
                    val individualHeir = if (count > 1) {
                        Heir(
                            id = "${heir.id}_$i",
                            nameEn = "${getEnglishOrdinal(i)} ${heir.nameEn}",
                            nameBn = "${getBengaliOrdinal(i)} ${heir.nameBn}",
                            priority = heir.priority,
                            category = heir.category
                        )
                    } else {
                        heir
                    }
                    results.add(HeirShare(
                        heir = individualHeir,
                        shareFraction = "0",
                        shareAmount = 0.0,
                        isHeir = false,
                        reason = "নিকটবর্তী সপিণ্ড উত্তরাধিকারী (পুত্র/পৌত্র/প্রপৌত্র/বিধবা) এর উপস্থিতির কারণে অংশ বঞ্চিত।",
                        count = 1
                    ))
                }
            }
        }
    } else {
        // Find the single highest priority (lowest rank index) selected heir
        val sortedSelectedHeirs = selectedHeirs
            .filter { it.value > 0 }
            .map { entry -> allHeirs.first { it.id == entry.key } to entry.value }
            .sortedBy { it.first.priority.toIntOrNull() ?: 999 }

        if (sortedSelectedHeirs.isNotEmpty()) {
            val (winningHeir, winningCount) = sortedSelectedHeirs.first()

            for ((id, count) in selectedHeirs) {
                val heir = allHeirs.first { it.id == id }
                if (id == winningHeir.id) {
                    val individualFraction = "1/$count"
                    val individualAmount = propertyTotal / count

                    for (i in 1..count) {
                        val individualHeir = if (count > 1) {
                            Heir(
                                id = "${heir.id}_$i",
                                nameEn = "${getEnglishOrdinal(i)} ${heir.nameEn}",
                                nameBn = "${getBengaliOrdinal(i)} ${heir.nameBn}",
                                priority = heir.priority,
                                category = heir.category
                            )
                        } else {
                            heir
                        }

                        var baseReason = if (count > 1) {
                            "সর্বোচ্চ অগ্রাধিকারপ্রাপ্ত সপিণ্ড উত্তরাধিকারী (${heir.nameBn})। মোট $count জনের জনপ্রতি অংশ $individualFraction।"
                        } else {
                            "সর্বোচ্চ অগ্রাধিকারপ্রাপ্ত স্বাভাবিক সপিণ্ড উত্তরাধিকারী (${heir.nameBn})।"
                        }

                        if (id in femaleSapindas) {
                            baseReason += "\nমন্তব্য (সপিণ্ড মহিলা): ${individualHeir.nameBn} একজন সপিণ্ড মহিলা। তিনি জীবনস্বত্বে (Life Interest) এই সম্পত্তির মালিক হবেন। তিনি এটি ভোগ দখল করতে পারবেন কিন্তু হস্তান্তর বা বিক্রি করতে পারবেন না। তাঁর মৃত্যুর পর এই সম্পত্তি পুনরায় মূল মৃত ব্যক্তির নিকটবর্তী জীবিত উত্তরাধিকারীদের নিকট ফিরে যাবে।"
                        }

                        results.add(HeirShare(
                            heir = individualHeir,
                            shareFraction = individualFraction,
                            shareAmount = individualAmount,
                            isHeir = true,
                            reason = baseReason,
                            count = 1
                        ))
                    }
                } else {
                    val winningName = winningHeir.nameBn
                    for (i in 1..count) {
                        val individualHeir = if (count > 1) {
                            Heir(
                                id = "${heir.id}_$i",
                                nameEn = "${getEnglishOrdinal(i)} ${heir.nameEn}",
                                nameBn = "${getBengaliOrdinal(i)} ${heir.nameBn}",
                                priority = heir.priority,
                                category = heir.category
                            )
                        } else {
                            heir
                        }
                        results.add(HeirShare(
                            heir = individualHeir,
                            shareFraction = "0",
                            shareAmount = 0.0,
                            isHeir = false,
                            reason = "নিকটবর্তী অগ্রাধিকারপ্রাপ্ত সপিণ্ড ($winningName) এর উপস্থিতির কারণে অংশ বঞ্চিত।",
                            count = 1
                        ))
                    }
                }
            }
        }
    }

    return results.sortedWith(compareBy<HeirShare> { it.heir.priority.toIntOrNull() ?: 999 }.thenBy { it.heir.id })
}
