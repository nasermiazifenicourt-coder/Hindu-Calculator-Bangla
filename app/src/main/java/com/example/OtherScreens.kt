package com.example

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import android.content.Intent
import android.net.Uri
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource

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
            .background(Color.Black)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Privacy Policy for Hindu Calculator",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFFF9800)
        )
        Text(
            text = "Effective Date: 01/01/2026",
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFFB0BEC5)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF3E2723)
            )
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text(
                    text = "*****We clearly declared that, We use some disguised name in different drafting of law cases of this site.  Unfortunately if it’s may similar with anybody it just  undesired. Nobody is similar to those case character*****",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 20.sp,
                    color = Color(0xFFFF8A80)
                )
            }
        }

        Text(
            text = "At Hindu Calculator, we are committed to protecting your privacy and ensuring that your personal information is handled securely and responsibly. This Privacy Policy outlines how we collect, use, and protect your information when you visit our website, engage with our services, or interact with us in any other way.",
            fontSize = 15.sp,
            lineHeight = 22.sp,
            color = Color(0xFFECEFF1)
        )

        PolicySection(
            title = "1. Information We Collect",
            content = "We may collect various types of information from you when you visit our website or use our services, including but not limited to:\n\n" +
                    "• Personal Information: Name, email address, phone number, and other details you provide when you register, subscribe, or contact us.\n\n" +
                    "• Usage Data: Information such as IP address, browser type, operating system, and the pages you visit on our site.\n\n" +
                    "• Cookies: We may use cookies to enhance your experience on our site. Cookies help us remember your preferences and provide you with a tailored experience."
        )

        PolicySection(
            title = "2. How We Use Your Information",
            content = "We use the information we collect for the following purposes:\n" +
                    "• To provide and maintain our services.\n" +
                    "• To improve our website’s functionality and performance.\n" +
                    "• To respond to your inquiries and provide customer support.\n" +
                    "• To send newsletters, updates, and other promotional materials (only if you have opted in to receive them).\n" +
                    "• To comply with legal obligations or protect our legal rights."
        )

        PolicySection(
            title = "3. Third-Party Sharing",
            content = "We do not sell or rent your personal information to third parties. However, we may share your information with third parties in the following circumstances:\n" +
                    "• With service providers who help us operate the website or provide our services (such as hosting services, email services, etc.).\n" +
                    "• To comply with legal obligations, court orders, or government requests.\n" +
                    "• To protect the rights, property, or safety of Hindu Calculator mobile application, our users, or others."
        )

        PolicySection(
            title = "4. Cookies and Tracking Technologies",
            content = "Hindu Calculator uses cookies and other tracking technologies to enhance your experience on our website. You can control the use of cookies through your browser settings. However, disabling cookies may affect the functionality of the site."
        )

        PolicySection(
            title = "5. Data Security",
            content = "We take appropriate technical and organizational measures to protect your personal information from unauthorized access, use, disclosure, or destruction. However, no method of transmission over the internet is 100% secure, and we cannot guarantee absolute security."
        )

        PolicySection(
            title = "6. Your Rights",
            content = "You have the following rights regarding your personal information:\n" +
                    "• The right to access, update, or delete your personal data.\n" +
                    "• The right to object to or restrict the processing of your data.\n" +
                    "• The right to withdraw your consent for data processing (where consent is required).\n" +
                    "• The right to lodge a complaint with a data protection authority.\n" +
                    "• To exercise any of these rights, please contact us at [insert contact email]."
        )

        PolicySection(
            title = "7. Children’s Privacy",
            content = "Our website is not intended for children under the age of 13, and we do not knowingly collect personal information from children. If we discover that we have inadvertently collected information from a child under 13, we will take steps to delete the data as soon as possible."
        )

        PolicySection(
            title = "8. Changes to This Privacy Policy",
            content = "We reserve the right to update or modify this Privacy Policy at any time. Any changes will be posted on this page with an updated effective date. We encourage you to review this policy periodically."
        )

        PolicySection(
            title = "9. Contact Us",
            content = "If you have any questions or concerns regarding this Privacy Policy or the practices of Law Academy BD, please contact us at:\n\n" +
                    "Email: lawacademybd2020@gmail.com\n" +
                    "Phone: +8801874486972\n" +
                    "Address: 2nd floor, Taranibash, Trank Road, Feni, Bangladesh\n\n" +
                    "This Privacy Policy reflects the commitment of (Hindu Calculator) Law Academy BD to safeguarding your personal information and maintaining your trust."
        )
    }
}

@Composable
fun PolicySection(title: String, content: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1E1E1E)
        )
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFF9800),
                modifier = Modifier.padding(bottom = 6.dp)
            )
            Text(
                text = content,
                fontSize = 14.sp,
                lineHeight = 21.sp,
                color = Color(0xFFECEFF1)
            )
        }
    }
}

@Composable
fun DisclaimerScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "দাবিত্যাগ / Disclaimer",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFFF9800),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Bengali Disclaimer Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1A0C00)), // Subtle amber-tinted dark background
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = Color(0xFFE65100).copy(alpha = 0.5f),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(18.dp)
            ) {
                Text(
                    text = "দাবিত্যাগ (Disclaimer)",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFF9800),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "এই ক্যালকুলেটরের ফলাফল শুধুমাত্র সাধারণ তথ্যের জন্য তৈরি করা হয়েছে এবং এটি কোনো প্রকার চূড়ান্ত আইনি পরামর্শ নয়। পারিবারিক বিরোধ সমাধান বা দলিল রেজিস্ট্রি করার পূর্বে অবশ্যই কোনো প্রত্যয়িত আইনজীবী অথবা হিন্দু আইন বিশেষজ্ঞের সাথে পরামর্শ করা বাঞ্ছনীয়। গাণিতিক হিসাব বা তথ্যের কোনো অসঙ্গতির জন্য কতৃপক্ষ দায়ী থাকবে না।",
                    fontSize = 14.sp,
                    lineHeight = 22.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFFECEFF1)
                )
            }
        }

        // English Disclaimer Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF121212)),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = Color(0xFF2C2C2C),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(18.dp)
            ) {
                Text(
                    text = "Legal Disclaimer",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFF9800),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "The calculations and results provided by this calculator are for general informational purposes only and do not constitute formal legal advice. Before resolving family disputes, executing partition deeds, or registering property, it is strongly advised to consult with a certified advocate or Hindu law specialist. The application developers and authorities shall not be held liable for any mathematical inaccuracies, discrepancies, or consequences arising from the use of this information.",
                    fontSize = 14.sp,
                    lineHeight = 22.sp,
                    color = Color(0xFFECEFF1)
                )
            }
        }
    }
}

@Composable
fun DeveloperScreen() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            text = "ডেভেলপার পরিচিতি",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFFF9800),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Interactive Developer Card with Image
        val interactionSource = remember { MutableInteractionSource() }
        val isHovered by interactionSource.collectIsHoveredAsState()
        val isPressed by interactionSource.collectIsPressedAsState()
        val isInteracted = isHovered || isPressed

        var isPhotoZoomed by remember { mutableStateOf(false) }
        val scale by animateFloatAsState(
            targetValue = if (isPhotoZoomed) 1.22f else (if (isInteracted) 1.05f else 1.0f),
            label = "cardScale"
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                }
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = {
                        scope.launch {
                            isPhotoZoomed = true
                            delay(250)
                            openFacebookLink(context, "https://facebook.com/numiazi")
                            isPhotoZoomed = false
                        }
                    }
                ),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF121212)),
            elevation = CardDefaults.cardElevation(defaultElevation = if (isInteracted) 12.dp else 4.dp),
            shape = RoundedCornerShape(24.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
                    .border(
                        width = 1.dp,
                        color = if (isPhotoZoomed || isInteracted) Color(0xFFFF9800) else Color(0xFF2C2C2C),
                        shape = RoundedCornerShape(24.dp)
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Interactive Photo Frame with Gradient Glow Border
                Box(
                    modifier = Modifier
                        .size(160.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .border(
                            width = 4.dp,
                            brush = Brush.linearGradient(
                                colors = if (isPhotoZoomed || isInteracted) {
                                    listOf(Color(0xFFFFD54F), Color(0xFFFF3D00))
                                } else {
                                    listOf(Color(0xFFFF9800), Color(0xFFE65100))
                                }
                            ),
                            shape = RoundedCornerShape(20.dp)
                        )
                        .background(Color(0xFF1E1E1E))
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.dev),
                        contentDescription = "Naser Miazi Photo",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Naser Miazi (Advocate)",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Owner of the Law Academy BD.",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFFFFB74D),
                    textAlign = TextAlign.Center
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = "ছবির উপরে ক্লিক করে আমার সাথে ফেসবুকে যোগাযোগ করুন।",
                    fontSize = 12.sp,
                    color = Color.LightGray,
                    textAlign = TextAlign.Center
                )
            }
        }

        // Contact & Social Info Section Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF121212)),
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .border(
                        width = 1.dp,
                        color = Color(0xFF2C2C2C),
                        shape = RoundedCornerShape(20.dp)
                    )
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                DeveloperInfoRow(
                    icon = Icons.Default.Email,
                    label = "Email",
                    value = "lawacademybd2020@gmail.com",
                    iconColor = Color(0xFFFF9800)
                )

                Spacer(
                    modifier = Modifier
                        .height(1.dp)
                        .fillMaxWidth()
                        .background(Color(0xFF2C2C2C))
                )

                DeveloperInfoRow(
                    icon = Icons.Default.Phone,
                    label = "Phone",
                    value = "+8801874486972",
                    iconColor = Color(0xFF4CAF50)
                )

                Spacer(
                    modifier = Modifier
                        .height(1.dp)
                        .fillMaxWidth()
                        .background(Color(0xFF2C2C2C))
                )

                ClickableDeveloperInfoRow(
                    icon = Icons.Default.Share,
                    label = "Facebook Page (আমাদের ফেসবুক পেইজ)",
                    value = "https://facebook.com/advmiazi",
                    iconColor = Color(0xFF2196F3),
                    onClick = {
                        openFacebookLink(context, "https://facebook.com/advmiazi")
                    }
                )

                Spacer(
                    modifier = Modifier
                        .height(1.dp)
                        .fillMaxWidth()
                        .background(Color(0xFF2C2C2C))
                )

                ClickableDeveloperInfoRow(
                    icon = Icons.Default.PlayArrow,
                    label = "Play Store ID (আমাদের সকল অ্যাপস)",
                    value = "Play Store Developer Profile",
                    iconColor = Color(0xFF00E676),
                    onClick = {
                        openPlayStoreDeveloperLink(context, "https://play.google.com/store/apps/dev?id=4698126341534001801")
                    }
                )

                Spacer(
                    modifier = Modifier
                        .height(1.dp)
                        .fillMaxWidth()
                        .background(Color(0xFF2C2C2C))
                )

                DeveloperInfoRow(
                    icon = Icons.Default.LocationOn,
                    label = "Address",
                    value = "Room- Law Academy, 2nd floor, Taranibash, Trank Road, Feni, Bangladesh",
                    iconColor = Color(0xFFF44336)
                )
            }
        }
    }
}

private fun openFacebookLink(context: android.content.Context, url: String) {
    try {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("fb://facewebmodal/f?href=$url"))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    } catch (e: Exception) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        } catch (ex: Exception) {
            // Ignored
        }
    }
}

private fun openPlayStoreDeveloperLink(context: android.content.Context, url: String) {
    try {
        val devId = url.substringAfter("id=").trim()
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("market://dev?id=$devId"))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    } catch (e: Exception) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        } catch (ex: Exception) {
            // Ignored
        }
    }
}

@Composable
fun ClickableDeveloperInfoRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String,
    iconColor: Color,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(iconColor.copy(alpha = 0.15f), RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = iconColor,
                modifier = Modifier.size(22.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = label,
                fontSize = 12.sp,
                color = Color(0xFFB0BEC5),
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = value,
                fontSize = 15.sp,
                color = Color(0xFFFFB74D),
                fontWeight = FontWeight.Bold,
                lineHeight = 20.sp
            )
        }
    }
}

@Composable
fun DeveloperInfoRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String,
    iconColor: Color
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(iconColor.copy(alpha = 0.15f), RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = iconColor,
                modifier = Modifier.size(22.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = label,
                fontSize = 12.sp,
                color = Color(0xFFB0BEC5),
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = value,
                fontSize = 15.sp,
                color = Color.White,
                fontWeight = FontWeight.Normal,
                lineHeight = 20.sp
            )
        }
    }
}
