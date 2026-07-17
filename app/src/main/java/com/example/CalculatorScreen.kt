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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculatorScreen() {
    val context = LocalContext.current
    var propertyInput by remember { mutableStateOf("100") }
    var searchQuery by remember { mutableStateOf("") }
    
    // Maintain list of added heir IDs and their counts (quantities)
    val selectedHeirIds = remember { mutableStateListOf<String>() }
    val selectedHeirCounts = remember { mutableStateMapOf<String, Int>() }
    
    var showResults by remember { mutableStateOf(false) }
    var resultsList by remember { mutableStateOf<List<HeirShare>>(emptyList()) }

    // Colors to match Dayabhaga branding
    val primaryOrange = Color(0xFFE65100)
    val lightOrange = Color(0xFFFFF3E0)

    if (showResults) {
        // Result Screen View
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
        ) {
            // Header back bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
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
                    color = Color.Black
                )
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(resultsList) { share ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
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
                                            color = Color.Black
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
                                        color = Color.Gray
                                    )
                                }
                                Column(horizontalAlignment = Alignment.End) {
                                    Text(
                                        text = if (share.isHeir) {
                                            String.format("%.1f", share.shareAmount)
                                        } else "0",
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Black
                                    )
                                    Text(
                                        text = "UNITS OF TOTAL",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Gray
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = if (share.isHeir && share.count > 1) {
                                    "SHARE: ${share.shareFraction} (জনপ্রতি / per individual)"
                                } else {
                                    "SHARE: ${if (share.isHeir) share.shareFraction else "0"}"
                                },
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = primaryOrange
                            )

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
                                trackColor = Color(0xFFE0E0E0)
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            // Helper notice block
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color(0xFFFAFAFA), RoundedCornerShape(8.dp))
                                    .border(1.dp, Color(0xFFEEEEEE), RoundedCornerShape(8.dp))
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
                                    color = Color.DarkGray
                                )
                            }
                        }
                    }
                }
            }

            // Black Container at bottom
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E)),
                shape = RoundedCornerShape(24.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .background(primaryOrange, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = "Distributed",
                                tint = Color.White
                            )
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(
                                text = "TOTAL DISTRIBUTED",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.LightGray
                            )
                            Row(verticalAlignment = Alignment.Bottom) {
                                Text(
                                    text = propertyInput.ifEmpty { "0" },
                                    fontSize = 32.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "Units",
                                    fontSize = 16.sp,
                                    color = Color.LightGray,
                                    modifier = Modifier.padding(bottom = 6.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Verification outline badge
                    Row(
                        modifier = Modifier
                            .background(Color.Transparent, RoundedCornerShape(12.dp))
                            .border(1.dp, Color(0xFF333333), RoundedCornerShape(12.dp))
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Verified,
                            contentDescription = "Verified",
                            tint = primaryOrange,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                text = "DAYABHAGA VERIFIED",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = primaryOrange
                            )
                            Text(
                                text = "Bangladesh Hindu Law Compliance",
                                fontSize = 10.sp,
                                color = Color.Gray
                            )
                        }
                    }
                }
            }
        }
    } else {
        // Calculator Input Screen with Scrollable Layout to prevent collapse and support small screens
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // 1. Header & Property Input (Fixed, no scroll)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .background(lightOrange, RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Calculate,
                        contentDescription = "Input Information",
                        tint = primaryOrange,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "তথ্য ইনপুট দিন",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "TOTAL PROPERTY (মোট সম্পত্তি কত?, শতাংশে বা টাকায় লিখুন)",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.DarkGray
                )
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = propertyInput,
                    onValueChange = { propertyInput = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        Text(
                            text = "100",
                            color = Color(0xFF222222), // Deep black/charcoal placeholder
                            fontWeight = FontWeight.Black,
                            fontSize = 14.sp
                        )
                    },
                    textStyle = TextStyle(
                        color = Color.Black, // High contrast entered text
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    trailingIcon = {
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .background(Color(0xFFEFEFEF))
                                .padding(horizontal = 12.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "UNITS (টাকা বা জমি শতক)",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Black,
                                color = Color(0xFF111111)
                            )
                        }
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedPlaceholderColor = Color(0xFF222222),
                        unfocusedPlaceholderColor = Color(0xFF222222),
                        focusedBorderColor = primaryOrange,
                        unfocusedBorderColor = primaryOrange, // Vivid orange border to highlight beautifully
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    ),
                    singleLine = true,
                    shape = RoundedCornerShape(8.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 2. Search & All 53 Heirs Selection List (Fixed Height Scrollable Box to prevent collapse, increased to 380dp for better visibility)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(380.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search icon",
                        tint = Color.Gray,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "উত্তরাধিকার তালিকা (৫৩ জন সপিণ্ড থেকে খুঁজুন)",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.DarkGray
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        Text(
                            text = "যেমন: কন্যা, পুত্র, Widow (লিখে সপিণ্ড খুঁজুন)...",
                            color = Color(0xFF555555), // Solid dark gray placeholder
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                    },
                    textStyle = TextStyle(
                        color = Color.Black, // High contrast black entered text
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal
                    ),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedPlaceholderColor = Color(0xFF555555),
                        unfocusedPlaceholderColor = Color(0xFF555555),
                        focusedBorderColor = primaryOrange,
                        unfocusedBorderColor = primaryOrange.copy(alpha = 0.5f), // Soft visible orange border
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    ),
                    singleLine = true,
                    shape = RoundedCornerShape(8.dp)
                )

                Spacer(modifier = Modifier.height(6.dp))

                // Scrollable container for the list of 53 heirs
                val filteredHeirs = allHeirs.filter { heir ->
                    heir.nameEn.contains(searchQuery, ignoreCase = true) ||
                            heir.nameBn.contains(searchQuery, ignoreCase = true)
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .border(1.dp, Color(0xFFEEEEEE), RoundedCornerShape(8.dp))
                        .background(Color(0xFFFAFAFA))
                        .padding(4.dp)
                ) {
                    if (filteredHeirs.isNotEmpty()) {
                        val heirsScrollState = rememberScrollState()
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(heirsScrollState),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            filteredHeirs.forEach { heir ->
                                val count = selectedHeirCounts[heir.id] ?: 0
                                HeirSelectionRow(
                                    heir = heir,
                                    count = count,
                                    onAdd = {
                                        if (heir.id !in selectedHeirIds) {
                                            selectedHeirIds.add(heir.id)
                                        }
                                        selectedHeirCounts[heir.id] = (selectedHeirCounts[heir.id] ?: 0) + 1
                                        Toast.makeText(
                                            context,
                                            "${heir.nameBn} যোগ করা হয়েছে।",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    },
                                    primaryOrange = primaryOrange,
                                    lightOrange = lightOrange
                                )
                            }
                        }
                    } else {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = if (searchQuery.isNotEmpty()) "কোনো উত্তরাধিকারী পাওয়া যায়নি।" else "সব উত্তরাধিকারী যোগ করা হয়েছে।",
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 3. Selected Heirs List - Dynamically expanding to keep Calculate button right below it without nested scrolls
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFFFF3E0), RoundedCornerShape(8.dp))
                        .border(1.dp, Color(0xFFFFB74D), RoundedCornerShape(8.dp))
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.People,
                            contentDescription = "Selected Heirs",
                            tint = Color(0xFFE65100),
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "নির্বাচিত উত্তরাধিকারী ও সংখ্যা",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFE65100)
                        )
                    }
                    Box(
                        modifier = Modifier
                            .background(Color(0xFFE65100), RoundedCornerShape(6.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        val totalSelectedCount = selectedHeirCounts.values.sum()
                        Text(
                            text = "$totalSelectedCount জন নির্বাচিত",
                            fontSize = 11.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, Color(0xFFEEEEEE), RoundedCornerShape(8.dp))
                        .background(Color(0xFFFCFCFC))
                        .padding(6.dp)
                ) {
                    if (selectedHeirIds.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 24.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(
                                    imageVector = Icons.Default.PersonAdd,
                                    contentDescription = "No heirs",
                                    tint = Color.LightGray,
                                    modifier = Modifier.size(36.dp)
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "উত্তরাধিকারীর পাশে (+) এ ক্লিক করে যোগ করুন",
                                    fontSize = 12.sp,
                                    color = Color.Gray
                                )
                            }
                        }
                    } else {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            selectedHeirIds.toList().forEach { id ->
                                val heir = allHeirs.firstOrNull { it.id == id }
                                if (heir != null) {
                                    val count = selectedHeirCounts[id] ?: 1
                                    SelectedHeirCard(
                                        heir = heir,
                                        count = count,
                                        onIncrement = {
                                            selectedHeirCounts[id] = count + 1
                                        },
                                        onDecrement = {
                                            if (count > 1) {
                                                selectedHeirCounts[id] = count - 1
                                            } else {
                                                selectedHeirCounts.remove(id)
                                                selectedHeirIds.remove(id)
                                                Toast.makeText(
                                                    context,
                                                    "${heir.nameBn} বাদ দেওয়া হয়েছে।",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        },
                                        onDelete = {
                                            selectedHeirCounts.remove(id)
                                            selectedHeirIds.remove(id)
                                            Toast.makeText(
                                                context,
                                                "${heir.nameBn} বাদ দেওয়া হয়েছে।",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        },
                                        primaryOrange = primaryOrange
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 4. Calculate Button
            val isEnabled = selectedHeirIds.isNotEmpty() && propertyInput.isNotEmpty()
            Button(
                onClick = {
                    val propValue = propertyInput.toDoubleOrNull() ?: 100.0
                    resultsList = calculateDayabhagaShares(propValue, selectedHeirCounts.toMap())
                    showResults = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                enabled = isEnabled,
                colors = ButtonDefaults.buttonColors(
                    containerColor = primaryOrange,
                    disabledContainerColor = Color(0xFFB0BEC5)
                ),
                shape = RoundedCornerShape(10.dp)
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

@Composable
fun HeirSelectionRow(
    heir: Heir,
    count: Int,
    onAdd: () -> Unit,
    primaryOrange: Color,
    lightOrange: Color
) {
    var isHovered by remember { mutableStateOf(false) }
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    while (true) {
                        val event = awaitPointerEvent()
                        when (event.type) {
                            PointerEventType.Enter, PointerEventType.Move, PointerEventType.Press -> {
                                isHovered = true
                            }
                            PointerEventType.Exit, PointerEventType.Release -> {
                                isHovered = false
                            }
                        }
                    }
                }
            }
            .clickable { onAdd() },
        colors = CardDefaults.cardColors(
            containerColor = if (isHovered) lightOrange else Color.White
        ),
        border = BorderStroke(
            width = 1.dp,
            color = if (isHovered) primaryOrange else Color(0xFFEEEEEE)
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "${heir.priority}. ${heir.nameBn}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )
                    if (count > 0) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Surface(
                            color = primaryOrange.copy(alpha = 0.15f),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text(
                                text = "$count বার যুক্ত",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = primaryOrange,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                }
                Text(
                    text = heir.nameEn,
                    fontSize = 11.sp,
                    color = Color.Gray
                )
            }
            
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                AnimatedVisibility(visible = isHovered) {
                    Surface(
                        color = primaryOrange,
                        shape = RoundedCornerShape(6.dp),
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Text(
                            text = "Add",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
                
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .background(primaryOrange, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add heir",
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun SelectedHeirCard(
    heir: Heir,
    count: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    onDelete: () -> Unit,
    primaryOrange: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFDE7)),
        border = BorderStroke(1.dp, Color(0xFFFFF59D)),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = heir.nameBn,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Surface(
                        color = primaryOrange,
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(
                            text = "x $count",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
                Text(
                    text = heir.nameEn,
                    fontSize = 11.sp,
                    color = Color.DarkGray
                )
            }
            
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                IconButton(
                    onClick = onDecrement,
                    modifier = Modifier
                        .size(28.dp)
                        .background(Color.White, CircleShape)
                        .border(1.dp, Color(0xFFE0E0E0), CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.Remove,
                        contentDescription = "Decrease count",
                        tint = if (count > 1) Color.Black else Color.Gray,
                        modifier = Modifier.size(14.dp)
                    )
                }
                
                Box(
                    modifier = Modifier.width(20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "$count",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
                
                IconButton(
                    onClick = onIncrement,
                    modifier = Modifier
                        .size(28.dp)
                        .background(Color.White, CircleShape)
                        .border(1.dp, Color(0xFFE0E0E0), CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Increase count",
                        tint = Color.Black,
                        modifier = Modifier.size(14.dp)
                    )
                }
                
                Spacer(modifier = Modifier.width(2.dp))
                
                IconButton(
                    onClick = onDelete,
                    modifier = Modifier
                        .size(32.dp)
                        .background(Color(0xFFFFEBEE), CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete heir",
                        tint = Color(0xFFD32F2F),
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}
