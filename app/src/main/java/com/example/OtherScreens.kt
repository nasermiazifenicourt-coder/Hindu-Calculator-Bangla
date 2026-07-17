package com.example

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SapindasScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "৫৩ জন সপিণ্ড (53 Sapindas in Dayabhaga)",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "দায়ভাগ আইন অনুসারে সপিণ্ড হচ্ছেন তারা যারা পিণ্ড দান করতে পারেন অথবা পিণ্ড গ্রহণে অংশ নেন। এদের উত্তরাধিকারের অগ্রাধিকার ক্রম নিচে ক্রমানুসারে দেওয়া হলো:",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }

        allHeirs.forEach { heir ->
            Card(
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .background(Color(0xFFE65100), RoundedCornerShape(6.dp))
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "${heir.priority}. ${heir.nameBn}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "${heir.nameEn} (শ্রেণী: ${heir.category})",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun UsageGuidelinesScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            text = "এপ ব্যবহার বিধি",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFE65100),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        val steps = listOf(
            "১. মোট সম্পত্তি ইনপুট দিন:" to "হোম স্ক্রিনের শুরুতে 'মোট সম্পত্তি কত?' ফিল্ডে আপনার মোট জমির পরিমাণ (শতাংশে) অথবা মোট টাকা লিখুন। যেমন: ১০০।",
            "২. উত্তরাধিকারী খুঁজুন ও যোগ করুন:" to "অনুসন্ধান বক্সে উত্তরাধিকারীর নাম লিখুন (যেমন: 'বিধবা', 'পুত্র', 'Widow' ইত্যাদি) অথবা তালিকা থেকে খুঁজুন। প্রতিটি নামের ডানপাশে থাকা '+' বাটনে ক্লিক করে যোগ করুন।",
            "৩. নির্বাচিত তালিকা পর্যবেক্ষণ করুন:" to "যোগ করার সাথে সাথে নিচে 'SELECTED HEIRS (নির্বাচিত উত্তরাধিকারী)' তালিকায় তা যুক্ত হবে। কোনো ভুল হলে '-' বাটনে ক্লিক করে ডিলিট করতে পারবেন।",
            "৪. বণ্টন হিসাব করুন:" to "সবাইকে নির্বাচন করা শেষ হলে নিচে থাকা 'Calculate Distribution' বাটনে ক্লিক করুন।",
            "৫. ফলাফল ও নোটিশ দেখুন:" to "ফলাফল স্ক্রিনে প্রতিটি উত্তরাধিকারীর অংশের হার, মোট প্রাপ্ত পরিমাণ এবং আইনসম্মত ব্যাখ্যা দেখতে পাবেন।"
        )

        steps.forEach { (title, desc) ->
            Card(
                modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = title, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFFE65100))
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(text = desc, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
    }
}

@Composable
fun PrinciplesScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            text = "হিন্দু উত্তরাধিকারের নীতি (দায়ভাগ)",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFE65100),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        val principles = listOf(
            "আধ্যাত্মিক কল্যাণ তত্ত্ব (Doctrine of Spiritual Benefit):" to "দায়ভাগ ব্যবস্থায় উত্তরাধিকারের মূল ভিত্তি হলো পিণ্ডদান তত্ত্ব। যে ব্যক্তি মৃত ব্যক্তির উদ্দেশ্যে সর্বোচ্চ আধ্যাত্মিক কল্যাণ বা পিণ্ডদান করার অধিকারী, সে উত্তরাধিকারী হিসেবে অগ্রাধিকার পায়।",
            "সহ-অংশীদারিত্ব (Coparcenary):" to "মিতাক্ষরা আইনের মতো জন্মসূত্রে সম্পত্তিতে অধিকার জন্মায় না। পিতার জীবদ্দশায় পুত্রদের কোনো স্বত্ব থাকে না। পিতার মৃত্যুর পরেই কেবল পুত্ররা সম্পত্তিতে অংশীদারী স্বত্ব লাভ করে।",
            "নারী উত্তরাধিকারীদের অধিকার:" to "দায়ভাগ আইনে নির্দিষ্ট ৫ জন নারী উত্তরাধিকারী জীবনস্বত্বে (Life Estate) সম্পত্তি পেতে পারেন: বিধবা স্ত্রী, কন্যা, মাতা, পিতামহী এবং প্রপিতামহী। তারা সম্পত্তি ভোগ করতে পারেন কিন্তু হস্তান্তর করতে পারেন না, যদি না আইনি প্রয়োজন (Legal Necessity) দেখা দেয়।",
            "মৃতের প্রতিনিধিত্বের নীতি (Doctrine of Representation):" to "পুত্র, মৃত পুত্রের পরিবার বা মৃত পৌত্রের পরিবার একসাথে সম্পত্তি লাভ করতে পারে। একে প্রতিনিধিত্বের ধারা (Per Stirpes) বলা হয়।"
        )

        principles.forEach { (title, desc) ->
            OutlinedCard(
                modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = title, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = desc, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
    }
}

@Composable
fun LegalGuideScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            text = "আইনি নির্দেশিকা ও রেফারেন্স",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFE65100),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Important Info",
                    tint = MaterialTheme.colorScheme.onErrorContainer
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "গুরুত্বপূর্ণ নোটিশ: এই ক্যালকুলেটরটি বাংলাদেশ ও ভারতের পশ্চিমবঙ্গ অঞ্চলে প্রচলিত জিমুতবাহনের দায়ভাগ শাস্ত্রীয় হিন্দু আইন এবং হিন্দু আইন (সম্পত্তি অধিকার) আইন ১৯৩৭-এর উপর ভিত্তি করে তৈরি।",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onErrorContainer
                )
            }
        }

        val legalSections = listOf(
            "১. হিন্দু নারী সম্পত্তি অধিকার আইন, ১৯৩৭ (Act XVIII of 1937):" to "এই আইনের আওতায় মৃত ব্যক্তির অংশীদারী বা ব্যক্তিগত সম্পত্তিতে তার বিধবা স্ত্রী ঠিক একজন পুত্রের সমান অংশ পাবেন। যদি কোনো পুত্র পূর্বে মারা গিয়ে থাকে এবং তার স্ত্রী জীবিত থাকে, তিনিও অংশ লাভ করবেন।",
            "২. জীবনস্বত্ব (Life Estate) এবং পরমস্বত্ব:" to "নারী উত্তরাধিকারীগণ যে সম্পত্তি লাভ করেন তা সাধারণত জীবনস্বত্ব। তাদের মৃত্যুর পর সেই সম্পত্তি তাদের নিজেদের উত্তরাধিকারীদের নিকট না গিয়ে মূল মালিকের পরবর্তী জীবিত সপিণ্ডের নিকট ফিরে যায়।",
            "৩. দায়ভাগ আদালতের নজির:" to "বাংলাদেশ সুপ্রিম কোর্টের বিভিন্ন যুগান্তকারী রায় অনুযায়ী দায়ভাগ রীতিনীতি চূড়ান্ত আইন হিসেবে বিবেচিত হয়।"
        )

        legalSections.forEach { (title, desc) ->
            Card(
                modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = title, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(text = desc, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
    }
}

@Composable
fun CooperationScreen() {
    Box(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "সহযোগীতায় ও পরামর্শ",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFE65100),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "এই অ্যাপ্লিকেশনটির মাধ্যমে সনাতন ধর্মের আইনি অধিকার সম্পর্কে সবাইকে সচেতন করাই আমাদের মূল উদ্দেশ্য। কোনো তথ্যগত ত্রুটি পরিলক্ষিত হলে বা আপনার মূল্যবান পরামর্শ ও মতামত থাকলে দয়া করে আমাদের সাথে ইমেইলের মাধ্যমে যোগাযোগ করুন।",
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 22.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = { /* Handle action */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE65100))
                ) {
                    Text("মতামত পাঠান (Send Feedback)", color = Color.White)
                }
            }
        }
    }
}

@Composable
fun ResearchScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            text = "তথ্য ও গবেষণা",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFE65100),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "আইনি উৎসসমূহ:", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "• জিমুতবাহনের দায়ভাগ গ্রন্থ\n• ডি এফ মুল্লার হিন্দু আইন (Mulla's Hindu Law)\n• বাংলাদেশ সুপ্রিম কোর্টের নজিরসমূহ (DLR, BLC, AD)\n• বাংলাদেশ ল কমিশনের রিপোর্ট ও সুপারিশমালা",
                    fontSize = 14.sp,
                    lineHeight = 22.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "উদ্দেশ্য ও দর্শন:", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "হিন্দু সমাজ ব্যবস্থায় সম্পত্তি বণ্টন ও পারিবারিক বিরোধ নিরসনে একটি সঠিক ও রেফারেন্সযোগ্য ডিজিটাল মাধ্যম তৈরি করা, যা সহজে সাধারণ মানুষের বোধগম্য হয়।",
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun CreditsScreen() {
    Box(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3E0))
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "কৃতজ্ঞতা স্বীকার",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFE65100),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "আমরা কৃতজ্ঞতা প্রকাশ করছি সকল হিন্দু আইন গবেষক, সুপ্রিম কোর্টের বিজ্ঞ আইনজীবী এবং ধর্মীয় পণ্ডিতদের প্রতি, যারা তথ্য যাচাইকরণ এবং ক্যালকুলেটরটির গাণিতিক শুদ্ধতা বজায় রাখতে আন্তরিক সহযোগিতা প্রদান করেছেন।",
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 22.sp,
                    color = Color.Black
                )
            }
        }
    }
}

@Composable
fun UserDataPolicyScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            text = "User Data Policy",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFE65100),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "আমরা ব্যবহারকারীর গোপনীয়তাকে সর্বোচ্চ গুরুত্ব দিয়ে থাকি। এই অ্যাপ্লিকেশনটি কোনো ব্যবহারকারীর ব্যক্তিগত তথ্য, যেমন নাম, ইমেইল, মোবাইল নম্বর বা লোকেশন সংরক্ষণ অথবা কোনো সার্ভারে আপলোড করে না। সমস্ত হিসাব-নিকাশ সম্পূর্ণভাবে অফলাইনে এবং আপনার নিজের ডিভাইসে প্রসেস করা হয়।",
                    fontSize = 14.sp,
                    lineHeight = 22.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun DisclaimerScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            text = "Disclaimer",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFE65100),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "দাবিত্যাগ (Disclaimer): এই ক্যালকুলেটরের ফলাফল শুধুমাত্র সাধারণ তথ্যের জন্য তৈরি করা হয়েছে এবং এটি কোনো প্রকার চূড়ান্ত আইনি পরামর্শ নয়। পারিবারিক বিরোধ সমাধান বা দলিল রেজিস্ট্রি করার পূর্বে অবশ্যই কোনো প্রত্যয়িত আইনজীবী অথবা হিন্দু আইন বিশেষজ্ঞের সাথে পরামর্শ করা বাঞ্ছনীয়। গাণিতিক হিসাব বা তথ্যের কোনো অসঙ্গতির জন্য কতৃপক্ষ দায়ী থাকবে না।",
                    fontSize = 14.sp,
                    lineHeight = 22.sp,
                    color = MaterialTheme.colorScheme.onErrorContainer
                )
            }
        }
    }
}

@Composable
fun DeveloperScreen() {
    Box(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "ডেভেলপার পরিচিতি",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFE65100)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "নাসের মিয়াঁজি ফেনীকোর্ট",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "nasermiazifenicourt@gmail.com",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "সনাতন ধর্মাবলম্বীদের অধিকার ও শিক্ষা প্রসারের জন্য একটি ডিজিটাল সমাধান।",
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
