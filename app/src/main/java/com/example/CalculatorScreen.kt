package com.example

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Brush

fun getSapindaSerialNumberBn(heirId: String): String? {
    return when (heirId) {
        "son" -> "১"
        "grandson" -> "২"
        "great_grandson" -> "৩"
        "widow" -> "৪"
        "unmarried_daughter" -> null // "কন্যার সাথে সিরিয়াল আসবে, অবিবাহিত কন্যা বা পুত্রবতী কন্যার সাথে সিরিয়াল থাকবেনা।"
        "daughter_with_son" -> null   // No serial number for these sub-types
        "daughters_son" -> "৬"
        "father" -> "৭"
        "mother" -> "৮"
        "brother" -> "৯"
        "brothers_son" -> "১০"
        "brothers_son_son" -> "১১"
        "sisters_son" -> "১২"
        "paternal_grandfather" -> "১৩"
        "paternal_grandmother" -> "১৪"
        "paternal_uncle" -> "১৫"
        "paternal_uncles_son" -> "১৬"
        "paternal_uncles_son_son" -> "১৭"
        "fathers_sisters_son" -> "১৮"
        "paternal_great_grandfather" -> "১৯"
        "paternal_great_grandmother" -> "২০"
        "paternal_great_uncle" -> "২১"
        "paternal_great_uncles_son" -> "২২"
        "paternal_great_uncles_son_son" -> "২৩"
        "fathers_fathers_sisters_son" -> "২৪"
        "sons_daughters_son" -> "২৫"
        "sons_sons_daughters_son" -> "২৬"
        "brothers_daughter_son" -> "২৭"
        "brothers_sons_daughters_son" -> "২৮"
        "paternal_uncles_daughter_son" -> "২৯"
        "paternal_uncles_sons_daughters_son" -> "৩০"
        "paternal_great_uncles_daughter_son" -> "৩১"
        "paternal_great_uncles_sons_daughters_son" -> "৩২"
        "maternal_grandfather" -> "৩৩"
        "maternal_uncle" -> "৩৪"
        "maternal_uncles_son" -> "৩৫"
        "maternal_uncles_son_son" -> "৩৬"
        "mothers_sisters_son" -> "৩৭"
        "maternal_great_grandfather" -> "৩৮"
        "maternal_great_uncle" -> "৩৯"
        "maternal_great_uncles_son" -> "৪০"
        "mothers_fathers_sisters_son" -> "৪১"
        "mothers_fathers_sisters_son_son" -> "৪২"
        "maternal_great_great_grandfather" -> "৪৩"
        "maternal_great_great_uncle" -> "৪৪"
        "maternal_great_great_uncles_son" -> "৪৫"
        "maternal_great_great_uncles_son_son" -> "৪৬"
        "mothers_fathers_fathers_sisters_son" -> "৪৭"
        "maternal_uncles_daughter_son" -> "৪৮"
        "maternal_uncles_sons_daughters_son" -> "৪৯"
        "maternal_great_uncles_daughter_son" -> "৫০"
        "maternal_great_uncles_sons_daughters_son" -> "৫১"
        "maternal_great_great_uncles_daughter_son" -> "৫২"
        "maternal_great_great_uncles_sons_daughters_son" -> "৫৩"
        else -> null
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculatorScreen(onShowInterstitial: () -> Unit = {}) {
    val context = LocalContext.current
    var takaInput by remember { mutableStateOf("") }
    var goldInput by remember { mutableStateOf("") }
    var landInput by remember { mutableStateOf("") }
    var searchQuery by remember { mutableStateOf("") }
    
    // Maintain list of added heir IDs and their counts (quantities)
    val selectedHeirIds = remember { mutableStateListOf<String>() }
    val selectedHeirCounts = remember { mutableStateMapOf<String, Int>() }
    
    var showResults by remember { mutableStateOf(false) }
    var resultsList by remember { mutableStateOf<List<HeirShare>>(emptyList()) }
    
    // Multiple assets results holders
    var takaResults by remember { mutableStateOf<List<HeirShare>>(emptyList()) }
    var goldResults by remember { mutableStateOf<List<HeirShare>>(emptyList()) }
    var landResults by remember { mutableStateOf<List<HeirShare>>(emptyList()) }

    // Colors to match Dayabhaga branding and the requested dark green theme
    val darkGreenBackground = Color(0xFF04140D)
    val darkGreenCard = Color(0xFF0C2419)
    val darkGreenBorder = Color(0xFF1B3D2B)
    val textWhite = Color.White
    val textSecondarySage = Color(0xFFA1B3A9)
    val primaryOrange = Color(0xFFFF9800) // Vibrant orange to pop on dark green
    val lightOrange = Color(0xFFFFE0B2)

    var isOthersSectionExpanded by remember { mutableStateOf(false) }
    var isGrandsonSelectedInSubBox by remember { mutableStateOf(false) }
    var isSonSectionExpanded by remember { mutableStateOf(false) }
    var isPredeceasedSonSectionExpanded by remember { mutableStateOf(false) }

    val predeceasedSons = remember { mutableStateListOf(PredeceasedSon(id = 1)) }

    val syncPredeceasedSons = {
        val totalWidows = predeceasedSons.sumOf { it.widowCount }
        val totalGrandsons = predeceasedSons.sumOf { it.grandsonCount }
        
        if (totalWidows > 0) {
            selectedHeirCounts["predeceased_son_widow"] = totalWidows
            if ("predeceased_son_widow" !in selectedHeirIds) {
                selectedHeirIds.add("predeceased_son_widow")
            }
        } else {
            selectedHeirCounts.remove("predeceased_son_widow")
            selectedHeirIds.remove("predeceased_son_widow")
        }
        
        if (totalGrandsons > 0) {
            selectedHeirCounts["grandson"] = totalGrandsons
            isGrandsonSelectedInSubBox = true
            if ("grandson" !in selectedHeirIds) {
                selectedHeirIds.add("grandson")
            }
        } else {
            if (isGrandsonSelectedInSubBox) {
                selectedHeirCounts.remove("grandson")
                selectedHeirIds.remove("grandson")
                isGrandsonSelectedInSubBox = false
            }
        }
    }

    if (showResults) {
        // Result Screen View in Dark Forest Green Theme
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(darkGreenBackground)
        ) {
            // Header back bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(darkGreenCard)
                    .border(BorderStroke(1.dp, darkGreenBorder))
                    .padding(horizontal = 4.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { showResults = false }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = primaryOrange
                    )
                }
                Text(
                    text = "ফলাফল ও বণ্টন বিবরণী",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = textWhite
                )
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                itemsIndexed(resultsList) { index, share ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = darkGreenCard),
                        border = BorderStroke(1.dp, darkGreenBorder)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text = share.heir.nameBn,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = textWhite
                                        )
                                        if (share.count > 1) {
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Surface(
                                                color = primaryOrange,
                                                shape = RoundedCornerShape(10.dp)
                                            ) {
                                                Text(
                                                    text = "${share.count} জন",
                                                    fontSize = 11.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = Color.White,
                                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                                )
                                            }
                                        }
                                    }
                                    Text(
                                        text = share.heir.nameEn,
                                        fontSize = 13.sp,
                                        color = textSecondarySage
                                    )
                                }
                                Column(horizontalAlignment = Alignment.End) {
                                    Text(
                                        text = "অনুপাত / Fraction:",
                                        fontSize = 11.sp,
                                        color = textSecondarySage
                                    )
                                    Text(
                                        text = if (share.isHeir) share.shareFraction else "0",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = primaryOrange
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            // Calculate individual shares for each entered asset category
                            val takaVal = takaResults.getOrNull(index)?.shareAmount ?: 0.0
                            val goldVal = goldResults.getOrNull(index)?.shareAmount ?: 0.0
                            val landVal = landResults.getOrNull(index)?.shareAmount ?: 0.0

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color(0xFF071C12), RoundedCornerShape(8.dp))
                                    .border(1.dp, darkGreenBorder, RoundedCornerShape(8.dp))
                                    .padding(10.dp),
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                if (takaInput.isNotEmpty()) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text("টাকা (Cash Taka):", fontSize = 12.sp, color = textSecondarySage)
                                        Text(
                                            text = "${String.format("%,.2f", if (share.isHeir) takaVal else 0.0)} টাকা",
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = textWhite
                                        )
                                    }
                                }
                                if (goldInput.isNotEmpty()) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text("স্বর্ণ (Gold):", fontSize = 12.sp, color = textSecondarySage)
                                        Text(
                                            text = "${String.format("%.2f", if (share.isHeir) goldVal else 0.0)} ভরি",
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = textWhite
                                        )
                                    }
                                }
                                if (landInput.isNotEmpty()) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text("জমি (Land):", fontSize = 12.sp, color = textSecondarySage)
                                        Text(
                                            text = "${String.format("%.2f", if (share.isHeir) landVal else 0.0)} শতক",
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = textWhite
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            // Custom progress bar
                            val progressValue = if (share.isHeir) {
                                val fracParts = share.shareFraction.split("/")
                                if (fracParts.size == 2) {
                                    val num = fracParts[0].toDoubleOrNull() ?: 1.0
                                    val den = fracParts[1].toDoubleOrNull() ?: 1.0
                                    (num / den).toFloat()
                                } else 1.0f
                            } else 0.0f

                            LinearProgressIndicator(
                                progress = { progressValue },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(6.dp)
                                    .clip(RoundedCornerShape(3.dp)),
                                color = primaryOrange,
                                trackColor = darkGreenBorder
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            // Helper notice block
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color(0xFF071C12), RoundedCornerShape(8.dp))
                                    .border(1.dp, darkGreenBorder, RoundedCornerShape(8.dp))
                                    .padding(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Info,
                                    contentDescription = "Heir type",
                                    tint = primaryOrange,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = share.reason,
                                    fontSize = 12.sp,
                                    color = textSecondarySage
                                )
                            }
                        }
                    }
                }

                item {
                    // Dark Green totals container inside scrollable LazyColumn
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp, bottom = 16.dp),
                        colors = CardDefaults.cardColors(containerColor = darkGreenCard),
                        border = BorderStroke(1.dp, darkGreenBorder),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "মোট বণ্টিত সম্পদ (TOTAL DISTRIBUTED)",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = textSecondarySage
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                if (takaInput.isNotEmpty()) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(Icons.Default.Payments, "Taka", tint = primaryOrange, modifier = Modifier.size(18.dp))
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Text("নগদ টাকা (Cash):", fontSize = 13.sp, color = textSecondarySage)
                                        }
                                        Text(
                                            text = "${String.format("%,.2f", takaInput.toDoubleOrNull() ?: 0.0)} টাকা",
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = textWhite
                                        )
                                    }
                                }
                                if (goldInput.isNotEmpty()) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(Icons.Default.WorkspacePremium, "Gold", tint = primaryOrange, modifier = Modifier.size(18.dp))
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Text("স্বর্ণালঙ্কার (Gold):", fontSize = 13.sp, color = textSecondarySage)
                                        }
                                        Text(
                                            text = "${String.format("%.2f", goldInput.toDoubleOrNull() ?: 0.0)} ভরি",
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = textWhite
                                        )
                                    }
                                }
                                if (landInput.isNotEmpty()) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(Icons.Default.Landscape, "Land", tint = primaryOrange, modifier = Modifier.size(18.dp))
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Text("স্থাবর জমি (Land):", fontSize = 13.sp, color = textSecondarySage)
                                        }
                                        Text(
                                            text = "${String.format("%.2f", landInput.toDoubleOrNull() ?: 0.0)} শতক",
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = textWhite
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            // Verification outline badge
                            Row(
                                modifier = Modifier
                                    .background(Color.Transparent, RoundedCornerShape(8.dp))
                                    .border(1.dp, primaryOrange.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                                    .padding(horizontal = 12.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Verified,
                                    contentDescription = "Verified",
                                    tint = primaryOrange,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Column {
                                    Text(
                                        text = "DAYABHAGA VERIFIED",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = primaryOrange
                                    )
                                    Text(
                                        text = "Bangladesh Hindu Law Compliance",
                                        fontSize = 8.sp,
                                        color = textSecondarySage
                                    )
                                }
                            }
                        }
                    }
                }

                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp, bottom = 16.dp),
                        colors = CardDefaults.cardColors(containerColor = darkGreenCard),
                        border = BorderStroke(1.dp, darkGreenBorder),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "হিসাবের ধাপ (Calculation Steps)",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = primaryOrange,
                                modifier = Modifier.padding(bottom = 12.dp)
                            )

                            // Principle Explanation
                            Text(
                                text = "উত্তরাধিকার বন্টনের মূলনীতি (দায়ভাগ আইন):",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = textWhite
                            )
                            Text(
                                text = "দায়ভাগ হিন্দু আইন অনুযায়ী উত্তরাধিকার মূলত পিণ্ডদান তত্ত্ব (Spiritual Benefit Doctrine) এবং রক্তের নৈকট্যের ভিত্তিতে নির্ধারিত হয়। নিকটবর্তী সপিণ্ড উত্তরাধিকারী উপস্থিত থাকলে দূরবর্তী উত্তরাধিকারীগণ সম্পূর্ণ বঞ্চিত হন।",
                                fontSize = 12.sp,
                                color = textSecondarySage,
                                modifier = Modifier.padding(top = 4.dp, bottom = 12.dp)
                            )

                            Spacer(modifier = Modifier.fillMaxWidth().height(1.dp).background(darkGreenBorder))
                            Spacer(modifier = Modifier.height(12.dp))

                            // Step 1: Class 1 validation
                            val class1Ids = setOf("son", "grandson", "great_grandson", "widow", "predeceased_son_widow")
                            val isClass1Active = selectedHeirCounts.filter { it.key in class1Ids && it.value > 0 }.isNotEmpty()

                            Text(
                                text = "ধাপ ১: শ্রেণী-১ (Class-I) উত্তরাধিকারী যাচাই",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = textWhite
                            )
                            if (isClass1Active) {
                                Text(
                                    text = "শ্রেণী-১ উত্তরাধিকারীগণ (পুত্র, পৌত্র, প্রপৌত্র, বিধবা স্ত্রী, মৃত পুত্রের বিধবা স্ত্রী) সশরীরে বা প্রতিনিধিত্বে জীবিত আছেন। দায়ভাগ বিধি অনুযায়ী তারা সমহারে সম্পত্তির অংশীদার হবেন (একাধিক বিধবা স্ত্রী থাকলে তারা ১টি অংশ সমান ভাগে ভাগ করেন)। অন্যান্য দূরবর্তী উত্তরাধিকারীগণ সম্পূর্ণ বঞ্চিত হবেন।",
                                    fontSize = 12.sp,
                                    color = textSecondarySage,
                                    modifier = Modifier.padding(top = 4.dp, bottom = 12.dp)
                                )
                            } else {
                                Text(
                                    text = "শ্রেণী-১ কোনো সপিণ্ড উত্তরাধিকারী জীবিত নেই। অতএব, সপিণ্ড অগ্রাধিকার তালিকার সর্বোচ্চ স্থানে থাকা জীবিত উত্তরাধিকারী বা উত্তরাধিকারীগণ সম্পূর্ণ সম্পত্তি লাভ করবেন এবং দূরবর্তী অন্যান্য সকলে বঞ্চিত হবেন।",
                                    fontSize = 12.sp,
                                    color = textSecondarySage,
                                    modifier = Modifier.padding(top = 4.dp, bottom = 12.dp)
                                )
                            }

                            Spacer(modifier = Modifier.fillMaxWidth().height(1.dp).background(darkGreenBorder))
                            Spacer(modifier = Modifier.height(12.dp))

                            // Step 2: Distribution detail for each selected heir
                            Text(
                                text = "ধাপ ২: প্রতিটি উত্তরাধিকারীর অংশ বণ্টন বিবরণী",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = textWhite,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )

                            resultsList.forEach { share ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp),
                                    colors = CardDefaults.cardColors(containerColor = Color(0xFF071C12)),
                                    border = BorderStroke(1.dp, darkGreenBorder),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Column(modifier = Modifier.padding(10.dp)) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = share.heir.nameBn,
                                                fontSize = 13.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = textWhite
                                            )
                                            Text(
                                                text = if (share.isHeir) "প্রাপ্ত অংশ: ${share.shareFraction}" else "বঞ্চিত (অংশ: ০)",
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = if (share.isHeir) primaryOrange else Color.Gray
                                            )
                                        }
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = share.reason,
                                            fontSize = 11.sp,
                                            color = textSecondarySage
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    } else {
        // Calculator Input Screen in custom premium Dark Forest Green theme, single scrollable flow
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(darkGreenBackground)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // CARD 1: ১. উত্তরাধিকারী নির্বাচন করুন (হিন্দু)
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = darkGreenCard),
                border = BorderStroke(1.dp, darkGreenBorder),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    // Header Row with Circular Badge
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .background(primaryOrange, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "১",
                                color = Color.White,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "উত্তরাধিকারী নির্বাচন করুন (হিন্দু)",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = textWhite
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Search field for searching across all 53 heirs
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = {
                            Text(
                                text = "যেমন: কন্যা, পুত্র, Widow (লিখে সপিণ্ড খুঁজুন)...",
                                color = textSecondarySage,
                                fontSize = 13.sp
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search",
                                tint = textSecondarySage,
                                modifier = Modifier.size(18.dp)
                            )
                        },
                        textStyle = TextStyle(
                            color = textWhite,
                            fontSize = 14.sp
                        ),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = textWhite,
                            unfocusedTextColor = textWhite,
                            focusedBorderColor = primaryOrange,
                            unfocusedBorderColor = darkGreenBorder,
                            focusedContainerColor = Color(0xFF071C12),
                            unfocusedContainerColor = Color(0xFF071C12)
                        ),
                        singleLine = true,
                        shape = RoundedCornerShape(8.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    if (searchQuery.isEmpty()) {
                        // SECTION 1: পুত্র, অধস্তন বংশধর এবং স্ত্রী
                        CategoryHeader(title = "পুত্র, অধস্তন বংশধর এবং স্ত্রী", primaryOrange = primaryOrange)

                        // 1. পুত্র (Son) [Collapsible nested wrapper at the very top]
                        val sonHeir = allHeirs.first { it.id == "son" }
                        val sonCount = selectedHeirCounts["son"] ?: 0
                        val subWidowCount = selectedHeirCounts["predeceased_son_widow"] ?: 0
                        val subGsCount = selectedHeirCounts["grandson"] ?: 0

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (sonCount > 0 || subWidowCount > 0 || (isGrandsonSelectedInSubBox && subGsCount > 0)) {
                                    Color(0xFF143B28)
                                } else darkGreenCard
                            ),
                            border = BorderStroke(
                                width = 1.dp,
                                color = if (sonCount > 0 || subWidowCount > 0 || (isGrandsonSelectedInSubBox && subGsCount > 0)) {
                                    primaryOrange
                                } else darkGreenBorder
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Column(modifier = Modifier.fillMaxWidth()) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { isSonSectionExpanded = !isSonSectionExpanded }
                                        .padding(horizontal = 12.dp, vertical = 12.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Column {
                                            Text(
                                                text = "পুত্র",
                                                fontSize = 14.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = textWhite
                                            )
                                            Text(
                                                text = "Son (ক্লিক করে বিস্তারিত দেখুন)",
                                                fontSize = 11.sp,
                                                color = textSecondarySage
                                            )
                                        }
                                    }

                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        if (sonCount > 0) {
                                            Surface(
                                                color = primaryOrange,
                                                shape = RoundedCornerShape(10.dp)
                                            ) {
                                                Text(
                                                    text = "পুত্র: $sonCount",
                                                    fontSize = 10.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = Color.White,
                                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                                )
                                            }
                                        }
                                        if (subWidowCount > 0 || (isGrandsonSelectedInSubBox && subGsCount > 0)) {
                                            Surface(
                                                color = Color(0xFFFF9800),
                                                shape = RoundedCornerShape(10.dp)
                                            ) {
                                                Text(
                                                    text = "মৃত পুত্র ওয়ারিশ",
                                                    fontSize = 10.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = Color.Black,
                                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                                )
                                            }
                                        }
                                        Icon(
                                            imageVector = if (isSonSectionExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                            contentDescription = "Toggle",
                                            tint = primaryOrange
                                        )
                                    }
                                }

                                AnimatedVisibility(visible = isSonSectionExpanded) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(Color(0xFF071C12))
                                            .padding(horizontal = 12.dp, vertical = 8.dp),
                                        verticalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        HeirCounterRow(
                                            heir = sonHeir.copy(nameBn = "পুত্র (পিতা মৃত সময় জীবিত)", nameEn = "Son (Living at father's death)"),
                                            count = sonCount,
                                            onIncrement = {
                                                if ("son" !in selectedHeirIds) selectedHeirIds.add("son")
                                                selectedHeirCounts["son"] = sonCount + 1
                                            },
                                            onDecrement = {
                                                if (sonCount > 1) {
                                                    selectedHeirCounts["son"] = sonCount - 1
                                                } else {
                                                    selectedHeirCounts.remove("son")
                                                    selectedHeirIds.remove("son")
                                                }
                                            },
                                            primaryOrange = primaryOrange,
                                            lightOrange = lightOrange,
                                            serialNo = null
                                        )

                                        val isSubGrandsonEntered = predeceasedSons.any { it.grandsonCount > 0 }
                                        val isMainListActive = (!isGrandsonSelectedInSubBox && (selectedHeirCounts["grandson"] ?: 0) > 0) || (selectedHeirCounts["great_grandson"] ?: 0) > 0

                                        Card(
                                            modifier = Modifier.fillMaxWidth(),
                                            colors = CardDefaults.cardColors(
                                                containerColor = if (predeceasedSons.any { it.widowCount > 0 || it.grandsonCount > 0 }) {
                                                    Color(0xFF24150E)
                                                } else darkGreenCard
                                            ),
                                            border = BorderStroke(
                                                width = 1.dp,
                                                color = if (predeceasedSons.any { it.widowCount > 0 || it.grandsonCount > 0 }) {
                                                    Color(0xFF8D6E63)
                                                } else darkGreenBorder
                                            ),
                                            shape = RoundedCornerShape(8.dp)
                                        ) {
                                            Column(modifier = Modifier.fillMaxWidth()) {
                                                Row(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .clickable { isPredeceasedSonSectionExpanded = !isPredeceasedSonSectionExpanded }
                                                        .padding(horizontal = 12.dp, vertical = 10.dp),
                                                    horizontalArrangement = Arrangement.SpaceBetween,
                                                    verticalAlignment = Alignment.CenterVertically
                                                ) {
                                                    Column {
                                                        Text(
                                                            text = "মৃত পুত্র (পিতা মৃত্যুর আগে মৃত)",
                                                            fontSize = 13.sp,
                                                            fontWeight = FontWeight.Bold,
                                                            color = Color(0xFFD7CCC8)
                                                        )
                                                        Text(
                                                            text = "Predeceased Son (ক্লিক করে ওয়ারিশ যুক্ত করুন)",
                                                            fontSize = 10.sp,
                                                            color = textSecondarySage
                                                        )
                                                    }
                                                    Icon(
                                                        imageVector = if (isPredeceasedSonSectionExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                                        contentDescription = "Toggle Sub-heirs",
                                                        tint = Color(0xFFD7CCC8),
                                                        modifier = Modifier.size(18.dp)
                                                    )
                                                }

                                                AnimatedVisibility(visible = isPredeceasedSonSectionExpanded) {
                                                    Column(
                                                        modifier = Modifier
                                                            .fillMaxWidth()
                                                            .background(Color(0xFF1E130F))
                                                            .padding(10.dp),
                                                        verticalArrangement = Arrangement.spacedBy(8.dp)
                                                    ) {
                                                        predeceasedSons.forEachIndexed { index, predSon ->
                                                            val ordinalBn = getBengaliOrdinal(index + 1)
                                                            Card(
                                                                modifier = Modifier
                                                                    .fillMaxWidth()
                                                                    .padding(vertical = 4.dp),
                                                                colors = CardDefaults.cardColors(
                                                                    containerColor = if (predSon.widowCount > 0 || predSon.grandsonCount > 0) {
                                                                        Color(0xFF2D1E18)
                                                                    } else Color(0xFF160D0A)
                                                                ),
                                                                border = BorderStroke(
                                                                    width = 1.dp,
                                                                    color = if (predSon.widowCount > 0 || predSon.grandsonCount > 0) {
                                                                        Color(0xFF8D6E63)
                                                                    } else Color(0xFF3E2723)
                                                                ),
                                                                shape = RoundedCornerShape(8.dp)
                                                            ) {
                                                                Column(modifier = Modifier.fillMaxWidth().padding(10.dp)) {
                                                                    Row(
                                                                        modifier = Modifier.fillMaxWidth(),
                                                                        horizontalArrangement = Arrangement.SpaceBetween,
                                                                        verticalAlignment = Alignment.CenterVertically
                                                                    ) {
                                                                        Text(
                                                                            text = "------ $ordinalBn মৃত পুত্রের ওয়ারিশ ------",
                                                                            fontSize = 12.sp,
                                                                            fontWeight = FontWeight.Bold,
                                                                            color = Color(0xFFD7CCC8)
                                                                        )
                                                                        if (predeceasedSons.size > 1) {
                                                                            IconButton(
                                                                                onClick = {
                                                                                    predeceasedSons.removeAt(index)
                                                                                    syncPredeceasedSons()
                                                                                },
                                                                                modifier = Modifier.size(24.dp),
                                                                                enabled = !isMainListActive
                                                                            ) {
                                                                                Icon(
                                                                                    imageVector = Icons.Default.Delete,
                                                                                    contentDescription = "Remove Predeceased Son",
                                                                                    tint = if (isMainListActive) Color.Gray else Color.Red,
                                                                                    modifier = Modifier.size(16.dp)
                                                                                )
                                                                            }
                                                                        }
                                                                    }

                                                                    Spacer(modifier = Modifier.height(6.dp))

                                                                    // Widow counter
                                                                    val subWidowHeir = allHeirs.first { it.id == "predeceased_son_widow" }
                                                                    HeirCounterRow(
                                                                        heir = subWidowHeir.copy(nameBn = "স্ত্রী (বিধবা)", nameEn = "Widow of predeceased son"),
                                                                        count = predSon.widowCount,
                                                                        enabled = !isMainListActive,
                                                                        disabledMessage = "মেইন লিস্টে পৌত্র/প্রপৌত্র এন্টি থাকায় লকড",
                                                                        onIncrement = {
                                                                            predeceasedSons[index] = predSon.copy(widowCount = predSon.widowCount + 1)
                                                                            syncPredeceasedSons()
                                                                        },
                                                                        onDecrement = {
                                                                            if (predSon.widowCount > 0) {
                                                                                predeceasedSons[index] = predSon.copy(widowCount = predSon.widowCount - 1)
                                                                                syncPredeceasedSons()
                                                                            }
                                                                        },
                                                                        primaryOrange = primaryOrange,
                                                                        lightOrange = lightOrange,
                                                                        serialNo = null
                                                                    )

                                                                    Spacer(modifier = Modifier.height(4.dp))

                                                                    // Grandson counter
                                                                    val subGsHeir = allHeirs.first { it.id == "grandson" }
                                                                    HeirCounterRow(
                                                                        heir = subGsHeir.copy(nameBn = "পুত্র (পৌত্র)", nameEn = "Son (Grandson)"),
                                                                        count = predSon.grandsonCount,
                                                                        enabled = !isMainListActive,
                                                                        disabledMessage = "মেইন লিস্টে পৌত্র/প্রপৌত্র এন্টি থাকায় লকড",
                                                                        onIncrement = {
                                                                            predeceasedSons[index] = predSon.copy(grandsonCount = predSon.grandsonCount + 1)
                                                                            syncPredeceasedSons()
                                                                        },
                                                                        onDecrement = {
                                                                            if (predSon.grandsonCount > 0) {
                                                                                predeceasedSons[index] = predSon.copy(grandsonCount = predSon.grandsonCount - 1)
                                                                                syncPredeceasedSons()
                                                                            }
                                                                        },
                                                                        primaryOrange = primaryOrange,
                                                                        lightOrange = lightOrange,
                                                                        serialNo = null
                                                                    )
                                                                }
                                                            }
                                                        }

                                                        Spacer(modifier = Modifier.height(4.dp))

                                                        Button(
                                                            onClick = {
                                                                val nextId = if (predeceasedSons.isEmpty()) 1 else predeceasedSons.maxOf { it.id } + 1
                                                                predeceasedSons.add(PredeceasedSon(id = nextId))
                                                                syncPredeceasedSons()
                                                            },
                                                            modifier = Modifier.fillMaxWidth().height(40.dp),
                                                            colors = ButtonDefaults.buttonColors(
                                                                containerColor = Color(0xFF3E2723),
                                                                disabledContainerColor = Color(0xFF1C1210)
                                                            ),
                                                            enabled = !isMainListActive,
                                                            shape = RoundedCornerShape(8.dp)
                                                        ) {
                                                            Icon(
                                                                imageVector = Icons.Default.Add,
                                                                contentDescription = "Add Predeceased Son",
                                                                tint = if (isMainListActive) Color.Gray else Color.White,
                                                                modifier = Modifier.size(16.dp)
                                                            )
                                                            Spacer(modifier = Modifier.width(6.dp))
                                                            Text(
                                                                text = "মৃত পুত্র যোগ করুন (Add Predeceased Son)",
                                                                fontSize = 12.sp,
                                                                color = if (isMainListActive) Color.Gray else Color.White,
                                                                fontWeight = FontWeight.Bold
                                                            )
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        // 2. পৌত্র (মৃত পুত্রের পুত্র) [Main List, Serialized '২']
                        val isSubActive = predeceasedSons.any { it.widowCount > 0 || it.grandsonCount > 0 }
                        val gsHeir = allHeirs.first { it.id == "grandson" }
                        val gsCount = selectedHeirCounts["grandson"] ?: 0
                        HeirCounterRow(
                            heir = gsHeir.copy(nameBn = "পৌত্র (মৃত পুত্রের পুত্র)", nameEn = "Grandson (Son's Son)"),
                            count = if (!isGrandsonSelectedInSubBox) gsCount else 0,
                            onIncrement = {
                                isGrandsonSelectedInSubBox = false
                                if ("grandson" !in selectedHeirIds) selectedHeirIds.add("grandson")
                                selectedHeirCounts["grandson"] = (if (!isGrandsonSelectedInSubBox) gsCount else 0) + 1
                            },
                            onDecrement = {
                                val current = if (!isGrandsonSelectedInSubBox) gsCount else 0
                                if (current > 1) {
                                    selectedHeirCounts["grandson"] = current - 1
                                } else {
                                    selectedHeirCounts.remove("grandson")
                                    selectedHeirIds.remove("grandson")
                                }
                            },
                            primaryOrange = primaryOrange,
                            lightOrange = lightOrange,
                            enabled = !isSubActive,
                            disabledMessage = if (isSubActive) "মৃত পুত্রের ওয়ারিশ বক্সে ডাটা থাকায় লকড" else null,
                            serialNo = "২"
                        )

                        // 3. প্রপৌত্র (মৃত পুত্রের পুত্রের পুত্র) [Serialized '৩']
                        val ggsHeir = allHeirs.first { it.id == "great_grandson" }
                        val ggsCount = selectedHeirCounts["great_grandson"] ?: 0
                        HeirCounterRow(
                            heir = ggsHeir,
                            count = ggsCount,
                            onIncrement = {
                                if ("great_grandson" !in selectedHeirIds) selectedHeirIds.add("great_grandson")
                                selectedHeirCounts["great_grandson"] = ggsCount + 1
                            },
                            onDecrement = {
                                if (ggsCount > 1) {
                                    selectedHeirCounts["great_grandson"] = ggsCount - 1
                                } else {
                                    selectedHeirCounts.remove("great_grandson")
                                    selectedHeirIds.remove("great_grandson")
                                }
                            },
                            primaryOrange = primaryOrange,
                            lightOrange = lightOrange,
                            enabled = !isSubActive,
                            disabledMessage = if (isSubActive) "মৃত পুত্রের ওয়ারিশ বক্সে ডাটা থাকায় লকড" else null,
                            serialNo = "৩"
                        )

                        // 4. বিধবা স্ত্রী (জীবনস্বত্ব) [Serialized '৪' - in 4th place!]
                        val widowHeir = allHeirs.first { it.id == "widow" }
                        val widowCount = selectedHeirCounts["widow"] ?: 0
                        HeirCounterRow(
                            heir = widowHeir.copy(nameBn = "বিধবা স্ত্রী (জীবনস্বত্ব)"),
                            count = widowCount,
                            onIncrement = {
                                if ("widow" !in selectedHeirIds) selectedHeirIds.add("widow")
                                selectedHeirCounts["widow"] = widowCount + 1
                            },
                            onDecrement = {
                                if (widowCount > 1) {
                                    selectedHeirCounts["widow"] = widowCount - 1
                                } else {
                                    selectedHeirCounts.remove("widow")
                                    selectedHeirIds.remove("widow")
                                }
                            },
                            primaryOrange = primaryOrange,
                            lightOrange = lightOrange,
                            serialNo = "৪"
                        )

                        // SECTION 2: কন্যা [Serialized '৫' for Section 2 title]
                        CategoryHeader(title = "কন্যা", primaryOrange = primaryOrange)

                        // 1. অবিবাহিতা কন্যা
                        val udHeir = allHeirs.first { it.id == "unmarried_daughter" }
                        val udCount = selectedHeirCounts["unmarried_daughter"] ?: 0
                        HeirCounterRow(
                            heir = udHeir,
                            count = udCount,
                            onIncrement = {
                                if ("unmarried_daughter" !in selectedHeirIds) selectedHeirIds.add("unmarried_daughter")
                                selectedHeirCounts["unmarried_daughter"] = udCount + 1
                            },
                            onDecrement = {
                                if (udCount > 1) {
                                    selectedHeirCounts["unmarried_daughter"] = udCount - 1
                                } else {
                                    selectedHeirCounts.remove("unmarried_daughter")
                                    selectedHeirIds.remove("unmarried_daughter")
                                }
                            },
                            primaryOrange = primaryOrange,
                            lightOrange = lightOrange,
                            serialNo = null // No serial number as per request
                        )

                        // 2. বিবাহিতা পুত্রবতী কন্যা
                        val dwsHeir = allHeirs.first { it.id == "daughter_with_son" }
                        val dwsCount = selectedHeirCounts["daughter_with_son"] ?: 0
                        HeirCounterRow(
                            heir = dwsHeir.copy(nameBn = "পুত্রবতী কন্যা"),
                            count = dwsCount,
                            onIncrement = {
                                if ("daughter_with_son" !in selectedHeirIds) selectedHeirIds.add("daughter_with_son")
                                selectedHeirCounts["daughter_with_son"] = dwsCount + 1
                            },
                            onDecrement = {
                                if (dwsCount > 1) {
                                    selectedHeirCounts["daughter_with_son"] = dwsCount - 1
                                } else {
                                    selectedHeirCounts.remove("daughter_with_son")
                                    selectedHeirIds.remove("daughter_with_son")
                                }
                            },
                            primaryOrange = primaryOrange,
                            lightOrange = lightOrange,
                            serialNo = null // No serial number as per request
                        )

                        // 3. কন্যার পুত্র [Serialized '৭']
                        val dsHeir = allHeirs.first { it.id == "daughters_son" }
                        val dsCount = selectedHeirCounts["daughters_son"] ?: 0
                        HeirCounterRow(
                            heir = dsHeir,
                            count = dsCount,
                            onIncrement = {
                                if ("daughters_son" !in selectedHeirIds) selectedHeirIds.add("daughters_son")
                                selectedHeirCounts["daughters_son"] = dsCount + 1
                            },
                            onDecrement = {
                                if (dsCount > 1) {
                                    selectedHeirCounts["daughters_son"] = dsCount - 1
                                } else {
                                    selectedHeirCounts.remove("daughters_son")
                                    selectedHeirIds.remove("daughters_son")
                                }
                            },
                            primaryOrange = primaryOrange,
                            lightOrange = lightOrange,
                            serialNo = "৬"
                        )

                        // SECTION 3: পিতা-মাতা এবং উর্ধ্বতন
                        CategoryHeader(title = "পিতা-মাতা এবং উর্ধ্বতন", primaryOrange = primaryOrange)

                        // 1. পিতা
                        val fatherHeir = allHeirs.first { it.id == "father" }
                        val fatherCount = selectedHeirCounts["father"] ?: 0
                        HeirCounterRow(
                            heir = fatherHeir,
                            count = fatherCount,
                            onIncrement = {
                                if ("father" !in selectedHeirIds) selectedHeirIds.add("father")
                                selectedHeirCounts["father"] = fatherCount + 1
                            },
                            onDecrement = {
                                if (fatherCount > 1) {
                                    selectedHeirCounts["father"] = fatherCount - 1
                                } else {
                                    selectedHeirCounts.remove("father")
                                    selectedHeirIds.remove("father")
                                }
                            },
                            primaryOrange = primaryOrange,
                            lightOrange = lightOrange,
                            serialNo = "৭"
                        )

                        // 2. মাতা (জীবনস্বত্ব)
                        val motherHeir = allHeirs.first { it.id == "mother" }
                        val motherCount = selectedHeirCounts["mother"] ?: 0
                        HeirCounterRow(
                            heir = motherHeir.copy(nameBn = "মাতা (জীবনস্বত্ব)"),
                            count = motherCount,
                            onIncrement = {
                                if ("mother" !in selectedHeirIds) selectedHeirIds.add("mother")
                                selectedHeirCounts["mother"] = motherCount + 1
                            },
                            onDecrement = {
                                if (motherCount > 1) {
                                    selectedHeirCounts["mother"] = motherCount - 1
                                } else {
                                    selectedHeirCounts.remove("mother")
                                    selectedHeirIds.remove("mother")
                                }
                            },
                            primaryOrange = primaryOrange,
                            lightOrange = lightOrange,
                            serialNo = "৮"
                        )

                        // 3. পিতামহ (দাদা)
                        val pgHeir = allHeirs.first { it.id == "paternal_grandfather" }
                        val pgCount = selectedHeirCounts["paternal_grandfather"] ?: 0
                        HeirCounterRow(
                            heir = pgHeir.copy(nameBn = "পিতামহ (দাদা)", nameEn = "Paternal Grandfather"),
                            count = pgCount,
                            onIncrement = {
                                if ("paternal_grandfather" !in selectedHeirIds) selectedHeirIds.add("paternal_grandfather")
                                selectedHeirCounts["paternal_grandfather"] = pgCount + 1
                            },
                            onDecrement = {
                                if (pgCount > 1) {
                                    selectedHeirCounts["paternal_grandfather"] = pgCount - 1
                                } else {
                                    selectedHeirCounts.remove("paternal_grandfather")
                                    selectedHeirIds.remove("paternal_grandfather")
                                }
                            },
                            primaryOrange = primaryOrange,
                            lightOrange = lightOrange,
                            serialNo = "১৩"
                        )

                        // 4. পিতামহী (দাদী)
                        val pgmHeir = allHeirs.first { it.id == "paternal_grandmother" }
                        val pgmCount = selectedHeirCounts["paternal_grandmother"] ?: 0
                        HeirCounterRow(
                            heir = pgmHeir.copy(nameBn = "পিতামহী (দাদী)", nameEn = "Paternal Grandmother"),
                            count = pgmCount,
                            onIncrement = {
                                if ("paternal_grandmother" !in selectedHeirIds) selectedHeirIds.add("paternal_grandmother")
                                selectedHeirCounts["paternal_grandmother"] = pgmCount + 1
                            },
                            onDecrement = {
                                if (pgmCount > 1) {
                                    selectedHeirCounts["paternal_grandmother"] = pgmCount - 1
                                } else {
                                    selectedHeirCounts.remove("paternal_grandmother")
                                    selectedHeirIds.remove("paternal_grandmother")
                                }
                            },
                            primaryOrange = primaryOrange,
                            lightOrange = lightOrange,
                            serialNo = "১৪"
                        )

                        // SECTION 4: ভাই-বোন
                        CategoryHeader(title = "ভাই-বোন", primaryOrange = primaryOrange)

                        // 1. সহোদর ভাই
                        val brotherHeir = allHeirs.first { it.id == "brother" }
                        val brotherCount = selectedHeirCounts["brother"] ?: 0
                        HeirCounterRow(
                            heir = brotherHeir.copy(nameBn = "সহোদর ভাই"),
                            count = brotherCount,
                            onIncrement = {
                                if ("brother" !in selectedHeirIds) selectedHeirIds.add("brother")
                                selectedHeirCounts["brother"] = brotherCount + 1
                            },
                            onDecrement = {
                                if (brotherCount > 1) {
                                    selectedHeirCounts["brother"] = brotherCount - 1
                                } else {
                                    selectedHeirCounts.remove("brother")
                                    selectedHeirIds.remove("brother")
                                }
                            },
                            primaryOrange = primaryOrange,
                            lightOrange = lightOrange,
                            serialNo = "১০"
                        )

                        // 2. সহোদর ভাইয়ের পুত্র
                        val bsHeir = allHeirs.first { it.id == "brothers_son" }
                        val bsCount = selectedHeirCounts["brothers_son"] ?: 0
                        HeirCounterRow(
                            heir = bsHeir.copy(nameBn = "সহোদর ভাইয়ের পুত্র"),
                            count = bsCount,
                            onIncrement = {
                                if ("brothers_son" !in selectedHeirIds) selectedHeirIds.add("brothers_son")
                                selectedHeirCounts["brothers_son"] = bsCount + 1
                            },
                            onDecrement = {
                                if (bsCount > 1) {
                                    selectedHeirCounts["brothers_son"] = bsCount - 1
                                } else {
                                    selectedHeirCounts.remove("brothers_son")
                                    selectedHeirIds.remove("brothers_son")
                                }
                            },
                            primaryOrange = primaryOrange,
                            lightOrange = lightOrange,
                            serialNo = "১০"
                        )

                        // 3. সহোদর বোনের পুত্র
                        val sisSonHeir = allHeirs.first { it.id == "sisters_son" }
                        val sisSonCount = selectedHeirCounts["sisters_son"] ?: 0
                        HeirCounterRow(
                            heir = sisSonHeir.copy(nameBn = "সহোদর বোনের পুত্র", nameEn = "Sister's Son"),
                            count = sisSonCount,
                            onIncrement = {
                                if ("sisters_son" !in selectedHeirIds) selectedHeirIds.add("sisters_son")
                                selectedHeirCounts["sisters_son"] = sisSonCount + 1
                            },
                            onDecrement = {
                                if (sisSonCount > 1) {
                                    selectedHeirCounts["sisters_son"] = sisSonCount - 1
                                } else {
                                    selectedHeirCounts.remove("sisters_son")
                                    selectedHeirIds.remove("sisters_son")
                                }
                            },
                            primaryOrange = primaryOrange,
                            lightOrange = lightOrange,
                            serialNo = "১২"
                        )

                        // SECTION 5: অন্যান্য সপিণ্ড উত্তরাধিকারীগণ (Other Heirs) - Collapsible
                        Spacer(modifier = Modifier.height(8.dp))
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { isOthersSectionExpanded = !isOthersSectionExpanded },
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
                            border = BorderStroke(1.dp, Color(0xFFE0E0E0)),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.Groups,
                                        contentDescription = "Other heirs",
                                        tint = Color.Gray
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "অন্যান্য সপিণ্ড উত্তরাধিকারীগণ (Other Heirs)",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp,
                                        color = Color.DarkGray
                                    )
                                }
                                Icon(
                                    imageVector = if (isOthersSectionExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                    contentDescription = "Toggle",
                                    tint = Color.Gray
                                )
                            }
                        }

                        if (isOthersSectionExpanded) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 8.dp)
                                    .border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(8.dp))
                                    .background(Color.White)
                                    .padding(8.dp),
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                val majorIds = setOf(
                                    "widow", "son", "predeceased_son_widow", "grandson", "great_grandson",
                                    "unmarried_daughter", "daughter_with_son", "daughters_son",
                                    "father", "mother", "paternal_grandfather", "paternal_grandmother",
                                    "brother", "brothers_son", "sisters_son"
                                )
                                allHeirs.filter { it.id !in majorIds }.forEach { otherHeir ->
                                    val count = selectedHeirCounts[otherHeir.id] ?: 0
                                    val priorityVal = otherHeir.priority.toIntOrNull() ?: 1
                                    val sVal = priorityVal
                                    val bengaliDigits = mapOf(
                                        '0' to '০', '1' to '১', '2' to '২', '3' to '৩', '4' to '৪',
                                        '5' to '৫', '6' to '৬', '7' to '৭', '8' to '৮', '9' to '৯'
                                    )
                                    val serialBn = sVal.toString().map { char -> bengaliDigits[char] ?: char }.joinToString("")

                                    HeirCounterRow(
                                        heir = otherHeir,
                                        count = count,
                                        onIncrement = {
                                            if (otherHeir.id !in selectedHeirIds) selectedHeirIds.add(otherHeir.id)
                                            selectedHeirCounts[otherHeir.id] = count + 1
                                        },
                                        onDecrement = {
                                            if (count > 1) {
                                                selectedHeirCounts[otherHeir.id] = count - 1
                                            } else {
                                                selectedHeirCounts.remove(otherHeir.id)
                                                selectedHeirIds.remove(otherHeir.id)
                                            }
                                        },
                                        primaryOrange = primaryOrange,
                                        lightOrange = lightOrange,
                                        serialNo = serialBn
                                    )
                                }
                            }
                        }
                    } else {
                        // Search results mode
                        val filteredHeirs = allHeirs.filter { heir ->
                            heir.nameEn.contains(searchQuery, ignoreCase = true) ||
                                    heir.nameBn.contains(searchQuery, ignoreCase = true)
                        }

                        if (filteredHeirs.isNotEmpty()) {
                            filteredHeirs.forEach { heir ->
                                val count = selectedHeirCounts[heir.id] ?: 0
                                val serialNoStr = when (heir.id) {
                                    "son" -> null
                                    "grandson" -> "২"
                                    "great_grandson" -> "৩"
                                    "widow" -> "৪"
                                    "predeceased_son_widow" -> null
                                    "predeceased_grandson_widow" -> null
                                    "unmarried_daughter" -> null
                                    "daughter_with_son" -> null
                                    "daughters_son" -> "৬"
                                    "father" -> "৭"
                                    "mother" -> "৮"
                                    "brother" -> "৯"
                                    "brothers_son" -> "১০"
                                    "sisters_son" -> "১২"
                                    "paternal_grandfather" -> "১৩"
                                    "paternal_grandmother" -> "১৪"
                                    else -> {
                                        val pVal = heir.priority.toIntOrNull() ?: 1
                                        val sVal = pVal
                                        val bengaliDigits = mapOf(
                                            '0' to '০', '1' to '১', '2' to '২', '3' to '৩', '4' to '৪',
                                            '5' to '৫', '6' to '৬', '7' to '৭', '8' to '৮', '9' to '৯'
                                        )
                                        sVal.toString().map { char -> bengaliDigits[char] ?: char }.joinToString("")
                                    }
                                }

                                HeirCounterRow(
                                    heir = heir,
                                    count = count,
                                    onIncrement = {
                                        if (heir.id !in selectedHeirIds) selectedHeirIds.add(heir.id)
                                        selectedHeirCounts[heir.id] = count + 1
                                    },
                                    onDecrement = {
                                        if (count > 1) {
                                            selectedHeirCounts[heir.id] = count - 1
                                        } else {
                                            selectedHeirCounts.remove(heir.id)
                                            selectedHeirIds.remove(heir.id)
                                        }
                                    },
                                    primaryOrange = primaryOrange,
                                    lightOrange = lightOrange,
                                    serialNo = serialNoStr
                                )
                            }
                        } else {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 24.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "কোনো উত্তরাধিকারী পাওয়া যায়নি।",
                                    fontSize = 13.sp,
                                    color = Color.Gray
                                )
                            }
                        }
                    }
                }
            }

            // CARD 2: ২. সম্পদের বিবরণ (Asset Details)
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = darkGreenCard),
                border = BorderStroke(1.dp, darkGreenBorder),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Header Row with Circular Badge
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier
                                            .size(28.dp)
                                            .background(primaryOrange, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "২",
                                color = Color.White,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "সম্পদের বিবরণ (Asset Details)",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = textWhite
                        )
                    }

                    Text(
                        text = "যেকোনো একটি বা একাধিক সম্পত্তিতে এন্ট্রি দিন:",
                        fontSize = 12.sp,
                        color = textSecondarySage
                    )

                    // 1. Taka Field
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "নগদ টাকা (Cash Taka)",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = textSecondarySage
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        OutlinedTextField(
                            value = takaInput,
                            onValueChange = { takaInput = it },
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = {
                                Text(
                                    text = "যেমন: ৫০০০০",
                                    color = textSecondarySage.copy(alpha = 0.5f),
                                    fontSize = 14.sp
                                )
                            },
                            textStyle = TextStyle(
                                color = textWhite,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold
                            ),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Payments,
                                    contentDescription = "Taka",
                                    tint = primaryOrange,
                                    modifier = Modifier.size(18.dp)
                                )
                            },
                            trailingIcon = {
                                Text(
                                    text = "টাকা (BDT)  ",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = primaryOrange
                                )
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = textWhite,
                                unfocusedTextColor = textWhite,
                                focusedBorderColor = primaryOrange,
                                unfocusedBorderColor = darkGreenBorder,
                                focusedContainerColor = Color(0xFF071C12),
                                unfocusedContainerColor = Color(0xFF071C12)
                            ),
                            singleLine = true,
                            shape = RoundedCornerShape(8.dp)
                        )
                    }

                    // 2. Gold Field
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "স্বর্ণ (Gold in Bhori)",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = textSecondarySage
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        OutlinedTextField(
                            value = goldInput,
                            onValueChange = { goldInput = it },
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = {
                                Text(
                                    text = "যেমন: ১০",
                                    color = textSecondarySage.copy(alpha = 0.5f),
                                    fontSize = 14.sp
                                )
                            },
                            textStyle = TextStyle(
                                color = textWhite,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold
                            ),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.WorkspacePremium,
                                    contentDescription = "Gold",
                                    tint = primaryOrange,
                                    modifier = Modifier.size(18.dp)
                                )
                            },
                            trailingIcon = {
                                Text(
                                    text = "ভরি (Bhori)  ",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = primaryOrange
                                )
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = textWhite,
                                unfocusedTextColor = textWhite,
                                focusedBorderColor = primaryOrange,
                                unfocusedBorderColor = darkGreenBorder,
                                focusedContainerColor = Color(0xFF071C12),
                                unfocusedContainerColor = Color(0xFF071C12)
                            ),
                            singleLine = true,
                            shape = RoundedCornerShape(8.dp)
                        )
                    }

                    // 3. Land Field
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "স্থাবর জমি (Land in Shatak)",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = textSecondarySage
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        OutlinedTextField(
                            value = landInput,
                            onValueChange = { landInput = it },
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = {
                                Text(
                                    text = "যেমন: ২৫",
                                    color = textSecondarySage.copy(alpha = 0.5f),
                                    fontSize = 14.sp
                                )
                            },
                            textStyle = TextStyle(
                                color = textWhite,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold
                            ),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Landscape,
                                    contentDescription = "Land",
                                    tint = primaryOrange,
                                    modifier = Modifier.size(18.dp)
                                )
                            },
                            trailingIcon = {
                                Text(
                                    text = "শতক (Shatak)  ",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = primaryOrange
                                )
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = textWhite,
                                unfocusedTextColor = textWhite,
                                focusedBorderColor = primaryOrange,
                                unfocusedBorderColor = darkGreenBorder,
                                focusedContainerColor = Color(0xFF071C12),
                                unfocusedContainerColor = Color(0xFF071C12)
                            ),
                            singleLine = true,
                            shape = RoundedCornerShape(8.dp)
                        )
                    }

                    val isEnabled = selectedHeirIds.isNotEmpty() && (takaInput.isNotEmpty() || goldInput.isNotEmpty() || landInput.isNotEmpty())
                    Button(
                        onClick = {
                            val takaVal = takaInput.toDoubleOrNull() ?: 0.0
                            val goldVal = goldInput.toDoubleOrNull() ?: 0.0
                            val landVal = landInput.toDoubleOrNull() ?: 0.0

                            takaResults = calculateDayabhagaShares(takaVal, selectedHeirCounts.toMap(), predeceasedSons.toList())
                            goldResults = calculateDayabhagaShares(goldVal, selectedHeirCounts.toMap(), predeceasedSons.toList())
                            landResults = calculateDayabhagaShares(landVal, selectedHeirCounts.toMap(), predeceasedSons.toList())

                            resultsList = calculateDayabhagaShares(
                                if (takaVal > 0.0) takaVal else if (goldVal > 0.0) goldVal else if (landVal > 0.0) landVal else 100.0,
                                selectedHeirCounts.toMap(),
                                predeceasedSons.toList()
                            )
                            showResults = true
                            onShowInterstitial()
                        },
                        modifier = Modifier
                                        .fillMaxWidth()
                                        .height(50.dp),
                        enabled = isEnabled,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = primaryOrange,
                            disabledContainerColor = Color(0xFF162D21)
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Calculate,
                                contentDescription = "Calculate",
                                tint = Color.White,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "বণ্টন হিসাব করুন (Calculate)",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryHeader(title: String, primaryOrange: Color) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 8.dp)
    ) {
        Text(
            text = title,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = primaryOrange
        )
        Spacer(modifier = Modifier.height(4.dp))
        HorizontalDivider(
            color = primaryOrange,
            thickness = 2.dp,
            modifier = Modifier.width(100.dp)
        )
    }
}

@Composable
fun HeirCounterRow(
    heir: Heir,
    count: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    primaryOrange: Color,
    lightOrange: Color,
    enabled: Boolean = true,
    disabledMessage: String? = null,
    serialNo: String? = null
) {
    val darkGreenCard = Color(0xFF0C2419)
    val darkGreenBorder = Color(0xFF1B3D2B)
    val textSecondarySage = Color(0xFFA1B3A9)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (!enabled) Color(0xFF061810) else if (count > 0) Color(0xFF143B28) else darkGreenCard
        ),
        border = BorderStroke(
            width = 1.dp,
            color = if (!enabled) Color(0xFF122C1F) else if (count > 0) primaryOrange else darkGreenBorder
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                val displayNameBn = heir.nameBn
                Text(
                    text = displayNameBn,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (!enabled) Color.Gray else Color.White
                )
                Text(
                    text = if (!enabled && disabledMessage != null) disabledMessage else heir.nameEn,
                    fontSize = 11.sp,
                    color = if (!enabled) Color(0xFF758A7E) else textSecondarySage
                )
            }

            if (enabled) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .border(1.dp, darkGreenBorder, RoundedCornerShape(24.dp))
                        .background(Color(0xFF04140D), RoundedCornerShape(24.dp))
                        .padding(horizontal = 2.dp, vertical = 2.dp)
                ) {
                    IconButton(
                        onClick = onDecrement,
                        modifier = Modifier.size(28.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Remove,
                            contentDescription = "Decrease",
                            tint = if (count > 0) primaryOrange else Color.Gray,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                    Text(
                        text = "$count",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (count > 0) Color.White else textSecondarySage,
                        modifier = Modifier.padding(horizontal = 8.dp),
                        textAlign = TextAlign.Center
                    )
                    IconButton(
                        onClick = onIncrement,
                        modifier = Modifier.size(28.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Increase",
                            tint = primaryOrange,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            } else {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Disabled",
                        tint = Color.Gray,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = "লকড",
                        fontSize = 12.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
