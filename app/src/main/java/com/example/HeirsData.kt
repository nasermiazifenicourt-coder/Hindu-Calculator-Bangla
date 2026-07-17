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
    Heir("son", "Living Son", "জীবিত পুত্র", "1"),
    Heir("predeceased_son_branch", "Predeceased Son's Family", "মৃত পুত্রের পরিবার", "2"),
    Heir("predeceased_grandson_branch", "Predeceased Grandson's Family", "মৃত পৌত্রের পরিবার", "3"),
    Heir("predeceased_great_grandson_branch", "Predeceased Great-Grandson's Family", "মৃত প্রপৌত্রের পরিবার", "4"),
    Heir("widow", "Widow", "বিধবা", "5"),
    Heir("unmarried_daughter", "Unmarried Daughter", "অবিবাহিত কন্যা", "6"),
    Heir("married_daughter_son", "Married Daughter with Son", "পুত্রবতী বা পুত্রসম্ভবা কন্যা", "7"),
    Heir("daughters_son", "Daughter's Son", "দৌহিত্র", "8"),
    Heir("father", "Father", "পিতা", "9"),
    Heir("mother", "Mother", "মাতা", "10"),
    Heir("brother", "Full Brother", "সহোদর ভাই", "11"),
    Heir("half_brother", "Half Brother", "বৈমাত্রেয় ভাই", "12"),
    Heir("brothers_son", "Full Brother's Son", "সহোদর ভাইপো", "13"),
    Heir("half_brothers_son", "Half Brother's Son", "বৈমাত্রেয় ভাইপো", "14"),
    Heir("brothers_son_son", "Full Brother's Son's Son", "সহোদরের পৌত্র", "15"),
    Heir("half_brothers_son_son", "Half Brother's Son's Son", "বৈমাত্রেয় ভাইয়ের পৌত্র", "16"),
    Heir("sisters_son", "Sister's Son", "ভাগিনেয়", "17"),
    Heir("paternal_grandfather", "Paternal Grandfather", "পিতামহ", "18"),
    Heir("paternal_grandmother", "Paternal Grandmother", "পিতামহী", "19"),
    Heir("paternal_uncle", "Full Paternal Uncle", "সহোদর কাকা", "20"),
    Heir("half_paternal_uncle", "Half Paternal Uncle", "বৈমাত্রেয় কাকা", "21"),
    Heir("paternal_uncles_son", "Full Paternal Uncle's Son", "সহোদর কাকার পুত্র", "22"),
    Heir("half_paternal_uncles_son", "Half Paternal Uncle's Son", "বৈমাত্রেয় কাকার পুত্র", "23"),
    Heir("paternal_uncles_son_son", "Full Paternal Uncle's Son's Son", "সহোদর কাকার পৌত্র", "24"),
    Heir("half_paternal_uncles_son_son", "Half Paternal Uncle's Son's Son", "বৈমাত্রেয় কাকার পৌত্র", "25"),
    Heir("fathers_sisters_son", "Father's Sister's Son", "ফুফাতো ভাই (পিতৃষ্বস্রীয়)", "26"),
    Heir("paternal_great_grandfather", "Paternal Great-Grandfather", "প্রপিতামহ", "27"),
    Heir("paternal_great_grandmother", "Paternal Great-Grandmother", "প্রপিতামহী", "28"),
    Heir("paternal_great_uncle", "Full Paternal Great-Uncle", "সহোদর জ্যাঠামশাই/কাকা", "29"),
    Heir("half_paternal_great_uncle", "Half Paternal Great-Uncle", "বৈমাত্রেয় জ্যাঠামশাই/কাকা", "30"),
    Heir("paternal_great_uncles_son", "Full Paternal Great-Uncle's Son", "সহোদর প্রপিতামহের পৌত্র", "31"),
    Heir("half_paternal_great_uncles_son", "Half Paternal Great-Uncle's Son", "বৈমাত্রেয় প্রপিতামহের পৌত্র", "32"),
    Heir("paternal_great_uncles_son_son", "Full Paternal Great-Uncle's Son's Son", "সহোদর প্রপিতামহের প্রপৌত্র", "33"),
    Heir("half_paternal_great_uncles_son_son", "Half Paternal Great-Uncle's Son's Son", "বৈমাত্রেয় প্রপিতামহের প্রপৌত্র", "34"),
    Heir("fathers_fathers_sisters_son", "Father's Paternal Aunt's Son", "পিতার ফুফাতো ভাই", "35"),
    Heir("maternal_grandfather", "Maternal Grandfather", "মাতামহ", "36"),
    Heir("maternal_uncle", "Maternal Uncle", "মামা", "37"),
    Heir("maternal_uncles_son", "Maternal Uncle's Son", "মামাতো ভাই", "38"),
    Heir("maternal_uncles_son_son", "Maternal Uncle's Son's Son", "মামাতো ভাইয়ের পুত্র", "39"),
    Heir("mothers_sisters_son", "Mother's Sister's Son", "মাসতুতো ভাই", "40"),
    Heir("maternal_great_grandfather", "Maternal Great-Grandfather", "প্রমাতামহ", "41"),
    Heir("maternal_great_uncle", "Maternal Great-Uncle", "প্রমাতামহের পুত্র (মাতার মামা)", "42"),
    Heir("maternal_great_uncles_son", "Maternal Great-Uncle's Son", "প্রমাতামহের পৌত্র", "43"),
    Heir("maternal_great_uncles_son_son", "Maternal Great-Uncle's Son's Son", "প্রমাতামহের প্রপৌত্র", "44"),
    Heir("mothers_fathers_sisters_son", "Mother's Paternal Aunt's Son", "মাতার ফুফাতো ভাই", "45"),
    Heir("maternal_great_great_grandfather", "Maternal Great-Great-Grandfather", "বৃদ্ধ প্রমাতামহ", "46"),
    Heir("maternal_great_great_uncle", "Maternal Great-Great-Uncle", "বৃদ্ধ প্রমাতামহের পুত্র", "47"),
    Heir("maternal_great_great_uncles_son", "Maternal Great-Great-Uncle's Son", "বৃদ্ধ প্রমাতামহের পৌত্র", "48"),
    Heir("maternal_great_great_uncles_son_son", "Maternal Great-Great-Uncle's Son's Son", "বৃদ্ধ প্রমাতামহের প্রপৌত্র", "49"),
    Heir("mothers_fathers_fathers_sisters_son", "Mother's Paternal Great-Aunt's Son", "মাতার পিতার ফুফাতো ভাই", "50"),
    Heir("brothers_daughter_son", "Brother's Daughter's Son", "ভাইয়ের কন্যার পুত্র (ভাতিজি-পুত্র)", "51"),
    Heir("sisters_daughter_son", "Sister's Daughter's Son", "বোনের কন্যার পুত্র (ভাগ্নি-পুত্র)", "52"),
    Heir("paternal_uncles_daughter_son", "Paternal Uncle's Daughter's Son", "চাচাতো বোনের পুত্র", "53")
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

    // Class I (Tier 1) heirs that inherit together under Dayabhaga with Hindu Women's Right to Property Act of 1937
    val class1Ids = setOf("son", "predeceased_son_branch", "predeceased_grandson_branch", "predeceased_great_grandson_branch", "widow")
    
    // Check if any Class I is selected and count > 0
    val activeClass1 = selectedHeirs.filter { it.key in class1Ids && it.value > 0 }

    if (activeClass1.isNotEmpty()) {
        // All active Class I heirs take together.
        // Under Dayabhaga, they share equally per stirpes. Widow takes same share as a son.
        // Total number of shares is the sum of quantities of all selected Class I heirs.
        val totalSharesCount = activeClass1.values.sum()
        
        for ((id, count) in selectedHeirs) {
            val heir = allHeirs.firstOrNull { it.id == id } ?: continue
            if (id in class1Ids) {
                val groupFraction = "$count/$totalSharesCount"
                val individualFraction = if (count > 1) "1/$totalSharesCount" else groupFraction
                val individualAmount = propertyTotal / totalSharesCount
                
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
                    val baseReason = if (count > 1) {
                        "স্বাভাবিক অংশীদার (শ্রেণী-১)। মোট $count জনের যৌথ অংশ $groupFraction (জনপ্রতি $individualFraction)।"
                    } else {
                        "স্বাভাবিক অংশীদার (শ্রেণী-১)।"
                    }
                    val reasonText = if (heir.id.startsWith("widow")) {
                        "$baseReason স্ত্রী বা বিধবা জীবনস্বত্বে ভোগ দখলকার হবে, এছাড়া খুব জুরুরী হলে আদালতের আদেশের মাধ্যমে সম্পত্তি বিক্রয় করতে পারেন।"
                    } else {
                        baseReason
                    }
                    results.add(HeirShare(
                        heir = individualHeir,
                        shareFraction = individualFraction,
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
                        reason = "নিকটবর্তী সপিণ্ড উত্তরাধিকারী (যেমন: পুত্র/বিধবা) এর উপস্থিতির কারণে অংশ বঞ্চিত।",
                        count = 1
                    ))
                }
            }
        }
    } else {
        // No Class I heirs are present.
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
                    val individualFraction = if (count > 1) "1/$count" else "1/1"
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
                        val baseReason = if (count > 1) {
                            "সর্বোচ্চ অগ্রাধিকারপ্রাপ্ত সপিণ্ড উত্তরাধিকারী (${heir.nameBn})। মোট $count জনের জনপ্রতি অংশ $individualFraction।"
                        } else {
                            "সর্বোচ্চ অগ্রাধিকারপ্রাপ্ত স্বাভাবিক সপিণ্ড উত্তরাধিকারী (${heir.nameBn})।"
                        }
                        val reasonText = if (heir.id.startsWith("widow")) {
                            "$baseReason স্ত্রী বা বিধবা জীবনস্বত্বে ভোগ দখলকার হবে, এছাড়া খুব জুরুরী হলে আদালতের আদেশের মাধ্যমে সম্পত্তি বিক্রয় করতে পারেন।"
                        } else {
                            baseReason
                        }
                        results.add(HeirShare(
                            heir = individualHeir,
                            shareFraction = individualFraction,
                            shareAmount = individualAmount,
                            isHeir = true,
                            reason = reasonText,
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
    
    // Sort results by priority, then by id suffix (to keep 1st, 2nd, 3rd in order)
    return results.sortedWith(compareBy<HeirShare> { it.heir.priority.toIntOrNull() ?: 999 }.thenBy { it.heir.id })
}
