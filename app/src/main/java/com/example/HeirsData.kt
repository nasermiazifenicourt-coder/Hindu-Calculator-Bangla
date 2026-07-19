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

data class PredeceasedSon(
    val id: Int,
    val widowCount: Int = 0,
    val grandsonCount: Int = 0
) : Serializable

val allHeirs = listOf(
    Heir("son", "Son", "পুত্র", "1"),
    Heir("grandson", "Son's Son (Grandson)", "মৃত পুত্রের পুত্র (পৌত্র)", "2"),
    Heir("great_grandson", "Son's Son's Son (Great-Grandson)", "মৃত পুত্রের পুত্রের পুত্র (প্রপৌত্র)", "3"),
    Heir("widow", "Widow", "স্ত্রী (জীবনস্বত্ব)", "4"),
    Heir("predeceased_son_widow", "Predeceased Son's Widow", "১ম মৃত পুত্রের স্ত্রী (বিধবা)", "4"),
    Heir("unmarried_daughter", "Unmarried Daughter", "অবিবাহিতা কন্যা", "5"),
    Heir("daughter_with_son", "Daughter with Son (or likely to have a son)", "পুত্রবতী কন্যা", "5"),
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
    Heir("maternal_grandfather", "Maternal Grandfather", "মাতার পিতা (মাতামহ)", "33"),
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

fun calculateDayabhagaShares(
    propertyTotal: Double,
    selectedHeirs: Map<String, Int>,
    predeceasedSonsList: List<PredeceasedSon> = emptyList()
): List<HeirShare> {
    val results = mutableListOf<HeirShare>()
    if (selectedHeirs.isEmpty()) return emptyList()

    // Class I (Tier 1) heirs under Dayabhaga with Hindu Women's Right to Property Act of 1937
    val class1Ids = setOf("son", "grandson", "great_grandson", "widow", "predeceased_son_widow")
    val femaleSapindas = setOf("widow", "predeceased_son_widow", "unmarried_daughter", "daughter_with_son", "mother", "paternal_grandmother", "paternal_great_grandmother")

    val activeClass1 = selectedHeirs.filter { it.key in class1Ids && it.value > 0 }

    if (activeClass1.isNotEmpty()) {
        // Build/normalize predeceased sons list
        var pSons = predeceasedSonsList
        if (pSons.isEmpty()) {
            val totalWidows = selectedHeirs["predeceased_son_widow"] ?: 0
            val totalGrandsons = selectedHeirs["grandson"] ?: 0
            if (totalWidows > 0 || totalGrandsons > 0) {
                if (totalWidows > 0 && totalGrandsons > 0) {
                    pSons = listOf(PredeceasedSon(id = 1, widowCount = totalWidows, grandsonCount = totalGrandsons))
                } else if (totalWidows > 0) {
                    pSons = List(totalWidows) { PredeceasedSon(id = it + 1, widowCount = 1) }
                } else {
                    pSons = List(totalGrandsons) { PredeceasedSon(id = it + 1, grandsonCount = 1) }
                }
            }
        }

        val activePredeceasedSons = pSons.filter { it.widowCount > 0 || it.grandsonCount > 0 }

        val numSons = selectedHeirs["son"] ?: 0
        val numWidows = selectedHeirs["widow"] ?: 0
        val numGreatGrandsons = selectedHeirs["great_grandson"] ?: 0

        val widowShareUnit = if (numWidows > 0) 1 else 0
        val totalBranchesCount = numSons + widowShareUnit + numGreatGrandsons + activePredeceasedSons.size

        val branchAmount = if (totalBranchesCount > 0) propertyTotal / totalBranchesCount else 0.0
        val branchFraction = "1/$totalBranchesCount"

        // 1. Process Living Sons
        if (numSons > 0) {
            val heir = allHeirs.first { it.id == "son" }
            for (i in 1..numSons) {
                val individualHeir = if (numSons > 1) {
                    Heir(
                        id = "son_$i",
                        nameEn = "${getEnglishOrdinal(i)} ${heir.nameEn}",
                        nameBn = if (heir.nameBn.startsWith("১ম ")) {
                            heir.nameBn.replaceFirst("১ম ", "${getBengaliOrdinal(i)} ")
                        } else {
                            "${getBengaliOrdinal(i)} ${heir.nameBn}"
                        },
                        priority = heir.priority,
                        category = heir.category
                    )
                } else {
                    heir
                }
                results.add(HeirShare(
                    heir = individualHeir,
                    shareFraction = branchFraction,
                    shareAmount = branchAmount,
                    isHeir = true,
                    reason = if (numSons > 1) "স্বাভাবিক অংশীদার (শ্রেণী-১)। মোট $numSons জন পুত্রের জনপ্রতি অংশ $branchFraction।" else "স্বাভাবিক অংশীদার (শ্রেণী-১)। অংশ $branchFraction।",
                    count = 1
                ))
            }
        }

        // 2. Process Deceased's Widows
        if (numWidows > 0) {
            val heir = allHeirs.first { it.id == "widow" }
            val fractionDisplay = if (numWidows > 1) "1/${totalBranchesCount * numWidows}" else branchFraction
            val individualAmount = branchAmount / numWidows
            for (i in 1..numWidows) {
                val individualHeir = if (numWidows > 1) {
                    Heir(
                        id = "widow_$i",
                        nameEn = "${getEnglishOrdinal(i)} ${heir.nameEn}",
                        nameBn = if (heir.nameBn.startsWith("১ম ")) {
                            heir.nameBn.replaceFirst("১ম ", "${getBengaliOrdinal(i)} ")
                        } else {
                            "${getBengaliOrdinal(i)} ${heir.nameBn}"
                        },
                        priority = heir.priority,
                        category = heir.category
                    )
                } else {
                    heir
                }
                var reasonText = if (numWidows > 1) {
                    "স্বাভাবিক অংশীদার (শ্রেণী-১)। মোট $numWidows জন বিধবার জনপ্রতি অংশ $fractionDisplay (১টি স্ত্রীর অংশ সমান ভাগে বণ্টিত)।"
                } else {
                    "স্বাভাবিক অংশীদার (শ্রেণী-১)। অংশ $fractionDisplay।"
                }
                reasonText += "\nমন্তব্য (সপিণ্ড মহিলা): ${individualHeir.nameBn} একজন সপিণ্ড মহিলা। তিনি জীবনস্বত্বে (Life Interest) এই সম্পত্তির মালিক হবেন। তিনি এটি ভোগ দখল করতে পারবেন কিন্তু হস্তান্তর বা বিক্রি করতে পারবেন না। তবে বিধবা স্ত্রী অত্যন্ত অস্বচ্ছল বা বিশেষ প্রয়োজনে আদালতের মাধ্যমে জমি বিক্রি করতে পারেন। তাঁর মৃত্যুর পর এই সম্পত্তি পুনরায় মূল মৃত ব্যক্তির নিকটবর্তী জীবিত উত্তরাধিকারীদের নিকট ফিরে যাবে।"
                results.add(HeirShare(
                    heir = individualHeir,
                    shareFraction = fractionDisplay,
                    shareAmount = individualAmount,
                    isHeir = true,
                    reason = reasonText,
                    count = 1
                ))
            }
        }

        // 3. Process Great Grandsons
        if (numGreatGrandsons > 0) {
            val heir = allHeirs.first { it.id == "great_grandson" }
            for (i in 1..numGreatGrandsons) {
                val individualHeir = if (numGreatGrandsons > 1) {
                    Heir(
                        id = "great_grandson_$i",
                        nameEn = "${getEnglishOrdinal(i)} ${heir.nameEn}",
                        nameBn = if (heir.nameBn.startsWith("১ম ")) {
                            heir.nameBn.replaceFirst("১ম ", "${getBengaliOrdinal(i)} ")
                        } else {
                            "${getBengaliOrdinal(i)} ${heir.nameBn}"
                        },
                        priority = heir.priority,
                        category = heir.category
                    )
                } else {
                    heir
                }
                results.add(HeirShare(
                    heir = individualHeir,
                    shareFraction = branchFraction,
                    shareAmount = branchAmount,
                    isHeir = true,
                    reason = "স্বাভাবিক অংশীদার (শ্রেণী-১)। প্রপৌত্র হিসেবে তার সপিণ্ড শাখার অংশ। অংশ $branchFraction।",
                    count = 1
                ))
            }
        }

        // 4. Process Predeceased Sons representation
        activePretestedSonsOrNormal(activePredeceasedSons, totalBranchesCount, branchAmount, branchFraction, results)

        // 5. Process Non-Winning / Excluded Heirs
        selectedHeirs.forEach { (id, count) ->
            if (id !in class1Ids) {
                val heir = allHeirs.firstOrNull { it.id == id } ?: return@forEach
                for (i in 1..count) {
                    val individualHeir = if (count > 1) {
                        Heir(
                            id = "${heir.id}_$i",
                            nameEn = "${getEnglishOrdinal(i)} ${heir.nameEn}",
                            nameBn = if (heir.nameBn.startsWith("১ম ")) {
                                heir.nameBn.replaceFirst("১ম ", "${getBengaliOrdinal(i)} ")
                            } else {
                                "${getBengaliOrdinal(i)} ${heir.nameBn}"
                            },
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
        // Special daughter exclusion rule: unmarried_daughter (Priority 5) excludes daughter_with_son (Priority 5)
        val sortedCandidates = selectedHeirs
            .filter { it.value > 0 }
            .map { entry -> allHeirs.first { it.id == entry.key } }
            .sortedBy { it.priority.toIntOrNull() ?: 999 }

        if (sortedCandidates.isNotEmpty()) {
            val hasUnmarried = (selectedHeirs["unmarried_daughter"] ?: 0) > 0
            val hasDaughterWithSon = (selectedHeirs["daughter_with_son"] ?: 0) > 0

            val winningId = if (hasUnmarried) {
                "unmarried_daughter"
            } else if (hasDaughterWithSon) {
                "daughter_with_son"
            } else {
                sortedCandidates.first().id
            }

            val finalWinningHeir = allHeirs.first { it.id == winningId }
            val winningCount = selectedHeirs[winningId] ?: 1

            for ((id, count) in selectedHeirs) {
                val heir = allHeirs.first { it.id == id }
                if (id == winningId) {
                    val individualFraction = "1/$winningCount"
                    val individualAmount = propertyTotal / winningCount

                    for (i in 1..count) {
                        val individualHeir = if (count > 1) {
                            Heir(
                                id = "${heir.id}_$i",
                                nameEn = "${getEnglishOrdinal(i)} ${heir.nameEn}",
                                nameBn = if (heir.nameBn.startsWith("১ম ")) {
                                    heir.nameBn.replaceFirst("১ম ", "${getBengaliOrdinal(i)} ")
                                } else {
                                    "${getBengaliOrdinal(i)} ${heir.nameBn}"
                                },
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
                    val winningName = finalWinningHeir.nameBn
                    val reasonText = if (id == "daughter_with_son" && hasUnmarried) {
                        "অবিবাহিতা কন্যার উপস্থিতির কারণে পুত্রবতী কন্যা অংশ বঞ্চিত।"
                    } else {
                        "নিকটবর্তী অগ্রাধিকারপ্রাপ্ত সপিণ্ড ($winningName) এর উপস্থিতির কারণে অংশ বঞ্চিত।"
                    }

                    for (i in 1..count) {
                        val individualHeir = if (count > 1) {
                            Heir(
                                id = "${heir.id}_$i",
                                nameEn = "${getEnglishOrdinal(i)} ${heir.nameEn}",
                                nameBn = if (heir.nameBn.startsWith("১ম ")) {
                                    heir.nameBn.replaceFirst("১ম ", "${getBengaliOrdinal(i)} ")
                                } else {
                                    "${getBengaliOrdinal(i)} ${heir.nameBn}"
                                },
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
                            reason = reasonText,
                            count = 1
                        ))
                    }
                }
            }
        }
    }

    return results.sortedWith(compareBy<HeirShare> { it.heir.priority.toIntOrNull() ?: 999 }.thenBy { it.heir.id })
}

private fun activePretestedSonsOrNormal(
    activePredeceasedSons: List<PredeceasedSon>,
    totalBranchesCount: Int,
    branchAmount: Double,
    branchFraction: String,
    results: MutableList<HeirShare>
) {
    val femaleSapindas = setOf("widow", "predeceased_son_widow", "unmarried_daughter", "daughter_with_son", "mother", "paternal_grandmother", "paternal_great_grandmother")
    activePredeceasedSons.forEachIndexed { idx, predSon ->
        val wCount = predSon.widowCount
        val gCount = predSon.grandsonCount
        val totalSubShares = wCount + gCount
        if (totalSubShares > 0) {
            val individualAmount = branchAmount / totalSubShares
            val fractionDisplay = if (totalSubShares > 1) "1/${totalBranchesCount * totalSubShares}" else branchFraction
            val ordinalBn = getBengaliOrdinal(idx + 1)
            val ordinalEn = getEnglishOrdinal(idx + 1)

            // Add Predeceased Son's Widow(s)
            if (wCount > 0) {
                val baseHeir = allHeirs.first { it.id == "predeceased_son_widow" }
                for (wi in 1..wCount) {
                    val subOrdinalBn = if (wCount > 1) " (${getBengaliOrdinal(wi)} স্ত্রী)" else ""
                    val subOrdinalEn = if (wCount > 1) " (${getEnglishOrdinal(wi)} Wife)" else ""
                    val individualHeir = Heir(
                        id = "predeceased_son_widow_${predSon.id}_$wi",
                        nameEn = "$ordinalEn Predeceased Son's Widow$subOrdinalEn",
                        nameBn = "$ordinalBn মৃত পুত্রের স্ত্রী (বিধবা)$subOrdinalBn",
                        priority = baseHeir.priority,
                        category = baseHeir.category
                    )
                    var reasonText = "মৃত পুত্রের উত্তরাধিকারী (প্রতিনিধিত্বের নীতি অনুসারে)। " +
                            "$ordinalBn মৃত পুত্রের মোট প্রাপ্য অংশ $branchFraction, যা তাঁর $totalSubShares জন ওয়ারিশের (বিধবা ও পুত্র/পৌত্র) মধ্যে সমান ভাগে বণ্টিত হয়েছে। জনপ্রতি অংশ $fractionDisplay।"
                    if (individualHeir.id in femaleSapindas || "predeceased_son_widow" in femaleSapindas) {
                        reasonText += "\nমন্তব্য (সপিণ্ড মহিলা): ${individualHeir.nameBn} একজন সপিণ্ড মহিলা। তিনি জীবনস্বত্বে (Life Interest) এই সম্পত্তির মালিক হবেন। তিনি এটি ভোগ দখল করতে পারবেন কিন্তু হস্তান্তর বা বিক্রি করতে পারবেন না। তাঁর মৃত্যুর পর এই সম্পত্তি পুনরায় মূল মৃত ব্যক্তির নিকটবর্তী জীবিত উত্তরাধিকারীদের নিকট ফিরে যাবে।"
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
            }

            // Add Predeceased Son's Son(s) / Grandsons
            if (gCount > 0) {
                val baseHeir = allHeirs.first { it.id == "grandson" }
                for (gi in 1..gCount) {
                    val subOrdinalBn = if (gCount > 1) " (${getBengaliOrdinal(gi)} পুত্র)" else ""
                    val subOrdinalEn = if (gCount > 1) " (${getEnglishOrdinal(gi)} Son)" else ""
                    val individualHeir = Heir(
                        id = "grandson_${predSon.id}_$gi",
                        nameEn = "$ordinalEn Predeceased Son's Son (Grandson)$subOrdinalEn",
                        nameBn = "$ordinalBn মৃত পুত্রের পুত্র (পৌত্র)$subOrdinalBn",
                        priority = baseHeir.priority,
                        category = baseHeir.category
                    )
                    val reasonText = "মৃত পুত্রের উত্তরাধিকারী (প্রতিনিধিত্বের নীতি অনুসারে)। " +
                            "$ordinalBn মৃত পুত্রের মোট প্রাপ্য অংশ $branchFraction, যা তাঁর $totalSubShares জন ওয়ারিশের (বিধবা ও পুত্র/পৌত্র) মধ্যে সমান ভাগে বণ্টিত হয়েছে। জনপ্রতি অংশ $fractionDisplay।"

                    results.add(HeirShare(
                        heir = individualHeir,
                        shareFraction = fractionDisplay,
                        shareAmount = individualAmount,
                        isHeir = true,
                        reason = reasonText,
                        count = 1
                    ))
                }
            }
        }
    }
}
