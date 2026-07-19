package com.example

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.webkit.JavascriptInterface
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button as AndroidButton
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color as ComposeColor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import kotlinx.coroutines.launch
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import kotlinx.coroutines.delay
import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.example.ui.theme.MyApplicationTheme
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.google.android.gms.ads.nativead.NativeAdView
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : ComponentActivity() {

    private var mInterstitialAd: InterstitialAd? = null
    private var nativeAdState = mutableStateOf<NativeAd?>(null)
    private var webViewInstance: WebView? = null
    private var fcmTokenState = mutableStateOf("")

    // Set to true in debug mode to test with official AdMob test ads, and false in release mode for Play Store release.
    private val USE_TEST_ADS = BuildConfig.DEBUG

    // AdMob Live Unit IDs
    private val LIVE_BANNER_ID = "ca-app-pub-4421171886586590/6553728229"
    private val LIVE_INTERSTITIAL_ID = "ca-app-pub-4421171886586590/9998717433"
    private val LIVE_NATIVE_ID = "ca-app-pub-4421171886586590/9918258169"

    // Official Google Test Unit IDs
    private val TEST_BANNER_ID = "ca-app-pub-3940256099942544/6300978111"
    private val TEST_INTERSTITIAL_ID = "ca-app-pub-3940256099942544/1033173712"
    private val TEST_NATIVE_ID = "ca-app-pub-3940256099942544/2247696110"

    private val BANNER_ID get() = if (USE_TEST_ADS) TEST_BANNER_ID else LIVE_BANNER_ID
    private val INTERSTITIAL_ID get() = if (USE_TEST_ADS) TEST_INTERSTITIAL_ID else LIVE_INTERSTITIAL_ID
    private val NATIVE_ID get() = if (USE_TEST_ADS) TEST_NATIVE_ID else LIVE_NATIVE_ID

    // Request notification permission for Android 13+
    @SuppressLint("InvalidFragmentVersionForActivityResult")
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            Log.d(TAG, "Notification permission granted.")
        } else {
            Log.d(TAG, "Notification permission denied.")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        // Pre-create WebView cache directories to prevent internal Chromium 'opendir' error logs
        try {
            val cacheDir = cacheDir
            val jsCacheDir = java.io.File(cacheDir, "WebView/Default/HTTP Cache/Code Cache/js")
            val wasmCacheDir = java.io.File(cacheDir, "WebView/Default/HTTP Cache/Code Cache/wasm")
            if (!jsCacheDir.exists()) {
                jsCacheDir.mkdirs()
            }
            if (!wasmCacheDir.exists()) {
                wasmCacheDir.mkdirs()
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error pre-creating WebView cache directories", e)
        }

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // 1. Initialize AdMob Mobile Ads SDK
        MobileAds.initialize(this) { status ->
            Log.d(TAG, "AdMob Initialized: $status")
            // Load interstitial and native ads after initialization
            loadInterstitialAd()
            loadNativeAd()
        }

        // 2. Fetch Firebase FCM Token
        fetchFcmToken()

        // 3. Request Notification Permission
        checkNotificationPermission()

        setContent {
            MyApplicationTheme {
                var showSplash by remember { mutableStateOf(true) }

                LaunchedEffect(Unit) {
                    delay(3000)
                    showSplash = false
                }

                if (showSplash) {
                    SplashScreen()
                } else {
                    MainScreen(
                        onRefreshWebView = { webViewInstance?.reload() },
                        fcmToken = fcmTokenState.value,
                        nativeAd = nativeAdState.value,
                        onRefreshNativeAd = { loadNativeAd() },
                        onShowInterstitial = { showInterstitial() },
                        onWebViewInit = { webView -> webViewInstance = webView },
                        bannerAdId = BANNER_ID
                    )
                }
            }
        }
    }

    private fun fetchFcmToken() {
        try {
            // Safe initialization of FirebaseApp to catch configuration/missing google-services.json errors
            com.google.firebase.FirebaseApp.initializeApp(this)

            val messaging = try {
                FirebaseMessaging.getInstance()
            } catch (e: IllegalStateException) {
                Log.w(TAG, "FirebaseApp is not initialized correctly. FCM will be disabled.", e)
                null
            }

            if (messaging != null) {
                messaging.token.addOnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                        return@addOnCompleteListener
                    }
                    val token = task.result
                    Log.d(TAG, "FCM Token: $token")
                    fcmTokenState.value = token

                    // Save locally
                    val sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
                    sharedPreferences.edit().putString("fcm_token", token).apply()

                    // Push to WebView if it's already running
                    webViewInstance?.post {
                        webViewInstance?.loadUrl("javascript:onFcmTokenReceived('$token')")
                    }
                }
            } else {
                fcmTokenState.value = "FCM_NOT_INITIALIZED"
            }
        } catch (e: Exception) {
            Log.e(TAG, "Firebase initialization failed. Ensure you have added google-services.json to the /app directory.", e)
            fcmTokenState.value = "FCM_NOT_CONFIGURED"
        }
    }

    private fun checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) !=
                PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    // Helper to load Google Interstitial Ad
    private fun loadInterstitialAd() {
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(
            this,
            INTERSTITIAL_ID,
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.e(TAG, "Interstitial ad failed to load: ${adError.message}. Code: ${adError.code}.")
                    Log.d(TAG, "Troubleshooting AdMob: If this is a real device like Samsung A53, make sure to add the test device ID programmatically or verify in Logcat (search for 'Use RequestConfiguration.Builder().setTestDeviceIds') to find the device's hashed ID.")
                    mInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    Log.d(TAG, "Interstitial ad loaded successfully.")
                    mInterstitialAd = interstitialAd
                    mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                        override fun onAdDismissedFullScreenContent() {
                            Log.d(TAG, "Interstitial ad was dismissed.")
                            mInterstitialAd = null
                            // Load the next one so it's ready
                            loadInterstitialAd()
                        }

                        override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                            Log.e(TAG, "Interstitial ad failed to show: ${adError.message}")
                            mInterstitialAd = null
                        }
                    }
                }
            }
        )
    }

    // Helper to show Interstitial Ad (exposed to Javascript Interface too)
    fun showInterstitial() {
        runOnUiThread {
            if (mInterstitialAd != null) {
                mInterstitialAd?.show(this)
            } else {
                Log.d(TAG, "Interstitial ad was not ready yet. Reloading silently...")
                loadInterstitialAd()
            }
        }
    }

    // Helper to load Native Ad
    private fun loadNativeAd() {
        val adLoader = AdLoader.Builder(this, NATIVE_ID)
            .forNativeAd { nativeAd ->
                Log.d(TAG, "Native Ad loaded successfully.")
                // Check if activity is destroyed before storing
                if (isDestroyed) {
                    nativeAd.destroy()
                    return@forNativeAd
                }
                // Destroy previous native ad to avoid memory leak
                nativeAdState.value?.destroy()
                nativeAdState.value = nativeAd
            }
            .withAdListener(object : AdListener() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.e(TAG, "Native Ad failed to load: ${adError.message}. Code: ${adError.code}.")
                    Log.d(TAG, "Troubleshooting AdMob: If this is a real device like Samsung A53, make sure to add the test device ID programmatically or verify in Logcat (search for 'Use RequestConfiguration.Builder().setTestDeviceIds') to find the device's hashed ID.")
                }
            })
            .withNativeAdOptions(NativeAdOptions.Builder().build())
            .build()

        adLoader.loadAd(AdRequest.Builder().build())
    }

    override fun onDestroy() {
        nativeAdState.value?.destroy()
        super.onDestroy()
    }

    companion object {
        private const val TAG = "MainActivity"
    }

    // Javascript Interface definition for WebView
    inner class WebAppInterface {
        @JavascriptInterface
        fun showInterstitial() {
            this@MainActivity.showInterstitial()
        }

        @JavascriptInterface
        fun getFcmToken(): String {
            return fcmTokenState.value
        }

        @JavascriptInterface
        fun refreshNativeAd() {
            runOnUiThread {
                loadNativeAd()
            }
        }
    }
}

enum class DrawerItem(val titleBn: String, val icon: ImageVector) {
    CALCULATOR("হিন্দু উত্তরাধিকার ক্যালকুলেটর", Icons.Default.Calculate),
    SAPINDAS("৫৩ জন সপিণ্ডের তালিকা", Icons.Default.FormatListBulleted),
    USAGE("এপ ব্যবহার বিধি", Icons.Default.HelpOutline),
    PRINCIPLES("হিন্দু উত্তরাধিকারের নীতি", Icons.Default.Description),
    GUIDELINES("আইনি নির্দেশিকা", Icons.Default.MenuBook),
    COOPERATION("সহযোগীতায়", Icons.Default.FavoriteBorder),
    RESEARCH("তথ্য ও গবেষণা", Icons.Default.Search),
    CREDITS("কৃতজ্ঞতা", Icons.Default.Favorite),
    POLICY("Privacy Policy", Icons.Default.Lock),
    DISCLAIMER("Disclaimer", Icons.Default.Warning),
    DEVELOPER("ডেভেলপার", Icons.Default.Code)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onRefreshWebView: () -> Unit,
    fcmToken: String,
    nativeAd: NativeAd?,
    onRefreshNativeAd: () -> Unit,
    onShowInterstitial: () -> Unit,
    onWebViewInit: (WebView) -> Unit,
    bannerAdId: String
) {
    val context = LocalContext.current
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var currentScreen by remember { mutableStateOf(DrawerItem.CALCULATOR) }

    val primaryOrange = ComposeColor(0xFFE65100)
    val lightOrange = ComposeColor(0xFFFFF3E0)

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = ComposeColor.White,
                modifier = Modifier.width(300.dp)
            ) {
                // Header of Drawer with Scales of justice theme
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(primaryOrange)
                        .padding(20.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Gavel,
                            contentDescription = "Scales of Justice",
                            tint = ComposeColor.White,
                            modifier = Modifier.size(32.dp)
                        )
                        IconButton(onClick = { scope.launch { drawerState.close() } }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close Menu",
                                tint = ComposeColor.White
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "হিন্দু উত্তরাধিকার ক্যালকুলেটর",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = ComposeColor.White
                    )
                    Text(
                        text = "দায়ভাগ নীতি",
                        fontSize = 13.sp,
                        color = ComposeColor.White.copy(alpha = 0.8f)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Scrollable Drawer Items
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 12.dp)
                ) {
                    DrawerItem.values().forEach { item ->
                        val interactionSource = remember { MutableInteractionSource() }
                        val isHovered by interactionSource.collectIsHoveredAsState()
                        val isPressed by interactionSource.collectIsPressedAsState()
                        val isTriggered = isHovered || isPressed

                        val scale by animateFloatAsState(
                            targetValue = if (isTriggered) 1.03f else 1.0f,
                            label = "scale"
                        )
                        val elevationScale by animateFloatAsState(
                            targetValue = if (isTriggered) 6f else 0f,
                            label = "elevation"
                        )
                        val translationX by animateFloatAsState(
                            targetValue = if (isTriggered) 6f else 0f,
                            label = "translationX"
                        )

                        val containerColor = when {
                            currentScreen == item -> lightOrange
                            isTriggered -> lightOrange.copy(alpha = 0.5f)
                            else -> ComposeColor.Transparent
                        }

                        NavigationDrawerItem(
                            label = {
                                Text(
                                    text = item.titleBn,
                                    fontSize = 14.sp,
                                    fontWeight = if (currentScreen == item) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            selected = currentScreen == item,
                            onClick = {
                                if (currentScreen != item) {
                                    currentScreen = item
                                    val noAdScreens = listOf(
                                        DrawerItem.POLICY,
                                        DrawerItem.DEVELOPER,
                                        DrawerItem.DISCLAIMER,
                                        DrawerItem.CREDITS,
                                        DrawerItem.RESEARCH,
                                        DrawerItem.COOPERATION
                                    )
                                    if (item !in noAdScreens) {
                                        onShowInterstitial()
                                    }
                                }
                                scope.launch { drawerState.close() }
                            },
                            icon = {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = item.titleBn,
                                    tint = if (currentScreen == item) primaryOrange else ComposeColor.Gray
                                )
                            },
                            colors = NavigationDrawerItemDefaults.colors(
                                selectedContainerColor = lightOrange,
                                selectedIconColor = primaryOrange,
                                selectedTextColor = primaryOrange,
                                unselectedContainerColor = containerColor,
                                unselectedIconColor = ComposeColor.Gray,
                                unselectedTextColor = ComposeColor.Black
                            ),
                            interactionSource = interactionSource,
                            modifier = Modifier
                                .padding(vertical = 2.dp)
                                .graphicsLayer {
                                    scaleX = scale
                                    scaleY = scale
                                    this.translationX = density * translationX
                                    shadowElevation = density * elevationScale
                                    shape = RoundedCornerShape(12.dp)
                                    clip = true
                                },
                            shape = RoundedCornerShape(12.dp)
                        )
                    }
                }

                HorizontalDivider(color = ComposeColor(0xFFEEEEEE))

                // Drawer Footer
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "VERSION 1.7.0 • DAYABHAGA",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = ComposeColor.Gray
                    )
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                val clipboardManager = LocalClipboardManager.current
                Surface(
                    color = primaryOrange,
                    tonalElevation = 4.dp
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .statusBarsPadding()
                            .height(50.dp)
                            .padding(horizontal = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = { scope.launch { drawerState.open() } },
                            modifier = Modifier.size(48.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Open Drawer",
                                tint = ComposeColor.White,
                                modifier = Modifier.size(30.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = "হিন্দু উত্তরাধিকার (দায়ভাগ)",
                            fontWeight = FontWeight.Bold,
                            fontSize = 17.sp,
                            color = ComposeColor.White,
                            modifier = Modifier.weight(1f)
                        )

                        if (fcmToken.isNotEmpty() && fcmToken != "FCM_NOT_INITIALIZED" && fcmToken != "FCM_NOT_CONFIGURED") {
                            IconButton(
                                onClick = {
                                    try {
                                        clipboardManager.setText(AnnotatedString(fcmToken))
                                        Toast.makeText(context, "FCM Token কপি করা হয়েছে (FCM Token copied!)", Toast.LENGTH_SHORT).show()
                                    } catch (e: Exception) {
                                        Toast.makeText(context, "কপি করতে ব্যর্থ হয়েছে (Failed to copy)", Toast.LENGTH_SHORT).show()
                                    }
                                },
                                modifier = Modifier.size(48.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ContentCopy,
                                    contentDescription = "Copy FCM Token",
                                    tint = ComposeColor.White,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }
                }
            },
        ) { innerPadding ->
            var isAdLoadedState by remember { mutableStateOf<Boolean?>(null) } // null = loading, true = loaded, false = failed
            val bannerHeight = if (isAdLoadedState != false) 56.dp else 0.dp

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(ComposeColor.White)
            ) {
                // Top Banner Ad (replacing the previous Native/Sponsor Ad)
                Surface(
                    color = ComposeColor(0xFF4A148C), // Beautiful deep purple
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(bannerHeight)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        AdMobBannerView(bannerAdId = bannerAdId, onAdLoaded = { isAdLoadedState = it })
                    }
                }

                if (isAdLoadedState == true) {
                    HorizontalDivider(
                        color = MaterialTheme.colorScheme.outlineVariant,
                        thickness = 1.dp
                    )
                }

                // Render Active Composable based on selected drawer item
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    when (currentScreen) {
                        DrawerItem.CALCULATOR -> CalculatorScreen()
                        DrawerItem.SAPINDAS -> SapindasScreen()
                        DrawerItem.USAGE -> UsageGuidelinesScreen()
                        DrawerItem.PRINCIPLES -> PrinciplesScreen()
                        DrawerItem.GUIDELINES -> LegalGuideScreen()
                        DrawerItem.COOPERATION -> CooperationScreen()
                        DrawerItem.RESEARCH -> ResearchScreen()
                        DrawerItem.CREDITS -> CreditsScreen()
                        DrawerItem.POLICY -> UserDataPolicyScreen()
                        DrawerItem.DISCLAIMER -> DisclaimerScreen()
                        DrawerItem.DEVELOPER -> DeveloperScreen()
                    }
                }

                // Native Ad Section at the bottom of the screen
                AdMobNativeAdSection(
                    nativeAd = nativeAd,
                    onRefreshNativeAd = onRefreshNativeAd
                )
            }
        }
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebViewContainer(
    onWebViewInit: (WebView) -> Unit,
    fcmToken: String
) {
    val context = LocalContext.current
    val activity = context as? MainActivity

    AndroidView(
        factory = { ctx ->
            val swipeRefreshLayout = SwipeRefreshLayout(ctx).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            }

            val webView = WebView(ctx).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                
                // WebView Security and standard settings
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true
                settings.databaseEnabled = true
                settings.useWideViewPort = true
                settings.loadWithOverviewMode = true
                settings.builtInZoomControls = true
                settings.displayZoomControls = false

                // Configure cache mode based on initial network status
                val initialOnlineState = isNetworkAvailable(ctx)
                settings.cacheMode = if (initialOnlineState) {
                    WebSettings.LOAD_DEFAULT
                } else {
                    WebSettings.LOAD_CACHE_ELSE_NETWORK
                }
                
                var lastUrl: String? = null

                fun getBaseUrl(url: String?): String {
                    if (url == null) return ""
                    return url.split("?")[0].split("#")[0].trim().removeSuffix("/")
                }

                webViewClient = object : WebViewClient() {
                    override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
                        return false
                    }

                    override fun onPageStarted(view: WebView?, url: String?, favicon: android.graphics.Bitmap?) {
                        super.onPageStarted(view, url, favicon)
                        
                        // Dynamically update cache mode on every page load/reload based on network status
                        val online = isNetworkAvailable(ctx)
                        view?.settings?.cacheMode = if (online) {
                            WebSettings.LOAD_DEFAULT
                        } else {
                            WebSettings.LOAD_CACHE_ELSE_NETWORK
                        }

                        if (!online) {
                            Toast.makeText(
                                ctx,
                                "আপনি অফলাইনে আছেন। সংরক্ষিত পৃষ্ঠাটি দেখানো হচ্ছে। (Offline mode. Loading saved page.)",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        if (url != null) {
                            val currentBase = getBaseUrl(url)
                            val lastBase = lastUrl?.let { getBaseUrl(it) }
                            if (lastBase != null && currentBase != lastBase) {
                                activity?.showInterstitial()
                            }
                            lastUrl = url
                        }
                    }

                    override fun onPageFinished(view: WebView?, url: String?) {
                        super.onPageFinished(view, url)
                        swipeRefreshLayout.isRefreshing = false
                        // Once finished, inject the initial FCM Token
                        if (fcmToken.isNotEmpty()) {
                            view?.loadUrl("javascript:onFcmTokenReceived('$fcmToken')")
                        }
                    }

                    override fun onReceivedError(
                        view: WebView?,
                        request: WebResourceRequest?,
                        error: android.webkit.WebResourceError?
                    ) {
                        super.onReceivedError(view, request, error)
                        swipeRefreshLayout.isRefreshing = false
                    }
                }

                // Add Javascript bridge
                if (activity != null) {
                    addJavascriptInterface(activity.WebAppInterface(), "AndroidBridge")
                }

                // Load the online homepage URL as requested by the user
                loadUrl("https://lawacademybd.com/hinducalculator/")
                
                // Expose instance to main activity
                onWebViewInit(this)
            }

            swipeRefreshLayout.addView(webView)
            swipeRefreshLayout.setOnRefreshListener {
                val online = isNetworkAvailable(ctx)
                webView.settings.cacheMode = if (online) {
                    WebSettings.LOAD_DEFAULT
                } else {
                    WebSettings.LOAD_CACHE_ELSE_NETWORK
                }
                webView.reload()
            }

            swipeRefreshLayout
        },
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
fun AdMobBannerView(bannerAdId: String, onAdLoaded: (Boolean) -> Unit) {
    val context = LocalContext.current

    AndroidView(
        factory = { ctx ->
            AdView(ctx).apply {
                setAdSize(AdSize.BANNER)
                adUnitId = bannerAdId
                adListener = object : AdListener() {
                    override fun onAdLoaded() {
                        super.onAdLoaded()
                        onAdLoaded(true)
                    }

                    override fun onAdFailedToLoad(error: LoadAdError) {
                        super.onAdFailedToLoad(error)
                        onAdLoaded(false)
                    }
                }
                loadAd(AdRequest.Builder().build())
            }
        },
        modifier = Modifier.wrapContentSize()
    )
}

@Composable
fun AdMobNativeAdSection(
    nativeAd: NativeAd?,
    onRefreshNativeAd: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val purpleBackground = ComposeColor(0xFF311B92) // Beautiful deep solid purple container
    val borderPurple = ComposeColor(0xFF7B1FA2) // Vibrant purple border
    val textLight = ComposeColor(0xFFEDE7F6) // Clean high-contrast lavender off-white

    Card(
        colors = CardDefaults.cardColors(
            containerColor = purpleBackground
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = borderPurple,
                shape = RoundedCornerShape(12.dp)
            )
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Ad Info",
                        tint = ComposeColor(0xFFFFB74D), // Soft orange/amber icon
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "বিজ্ঞাপন / Sponsored Ad",
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = textLight
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    // "AD" badge indicator
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(ComposeColor(0xFFFF9800).copy(alpha = 0.2f))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "AD",
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Black,
                            color = ComposeColor(0xFFFFB74D)
                        )
                    }
                    
                    Spacer(modifier = Modifier.width(4.dp))

                    TextButton(
                        onClick = { expanded = !expanded },
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = if (expanded) "লুকান (Hide)" else "বিস্তারিত (Show)",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = ComposeColor(0xFFE040FB) // Light purple/pink for beautiful visibility
                        )
                    }
                }
            }

            AnimatedVisibility(
                visible = expanded,
                enter = fadeIn(animationSpec = tween(300)) + expandVertically(),
                exit = fadeOut(animationSpec = tween(300)) + shrinkVertically()
            ) {
                Column {
                    Spacer(modifier = Modifier.height(10.dp))
                    
                    // Inside the card, we wrap the ad content in a solid clean deep purple background
                    // to guarantee pristine text contrast and absolute compliance with AdMob rules.
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(ComposeColor(0xFF4A148C)) // Extra deep purple background
                            .padding(8.dp)
                    ) {
                        if (nativeAd != null) {
                            AndroidView(
                                factory = { context ->
                                    val nativeAdView = NativeAdView(context)
                                    populateNativeAdViewProgrammatically(context, nativeAd, nativeAdView)
                                    nativeAdView
                                },
                                update = { nativeAdView ->
                                    populateNativeAdViewProgrammatically(nativeAdView.context, nativeAd, nativeAdView)
                                },
                                modifier = Modifier.fillMaxWidth()
                            )
                        } else {
                            // High-fidelity fallback / guide layout
                            NativeAdPlaceholderLayout(onRefreshNativeAd)
                        }
                    }
                }
            }
        }
    }
}

// Function to programmatically populate and style the AdMob NativeAdView strictly following design guidelines without XML
private fun populateNativeAdViewProgrammatically(
    context: Context,
    nativeAd: NativeAd,
    adView: NativeAdView
) {
    // Parent Container
    val parentLayout = LinearLayout(context).apply {
        orientation = LinearLayout.VERTICAL
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        setPadding(16, 16, 16, 16)
        setBackgroundColor(Color.TRANSPARENT)
    }

    // Headline
    val headline = TextView(context).apply {
        text = nativeAd.headline
        textSize = 15f
        setTypeface(null, Typeface.BOLD)
        setTextColor(Color.parseColor("#FFFF8D")) // Bright warm neon yellow for amazing contrast on purple
        layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            setMargins(0, 0, 0, 8)
        }
    }
    adView.headlineView = headline
    parentLayout.addView(headline)

    // Body
    val body = TextView(context).apply {
        text = nativeAd.body
        textSize = 13f
        setTextColor(Color.parseColor("#EDE7F6")) // Light lavender off-white for high readability
        layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            setMargins(0, 0, 0, 12)
        }
    }
    adView.bodyView = body
    parentLayout.addView(body)

    // Call to Action button
    val ctaButton = AndroidButton(context).apply {
        text = nativeAd.callToAction ?: "Visit"
        setBackgroundColor(Color.parseColor("#FF9800")) // Vibrant Orange CTA button that pops out beautifully
        setTextColor(Color.WHITE)
        textSize = 13f
        isAllCaps = false
        layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            96 // Heights in pixels
        )
    }
    adView.callToActionView = ctaButton
    parentLayout.addView(ctaButton)

    // Assign the populated view group to the AdView
    adView.removeAllViews()
    adView.addView(parentLayout)

    // Set the native ad object
    adView.setNativeAd(nativeAd)
}

@Composable
fun NativeAdPlaceholderLayout(onRefresh: () -> Unit) {
    val primaryOrange = ComposeColor(0xFFFF9800)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(ComposeColor(0xFF4A148C)) // Solid purple background
            .border(1.dp, ComposeColor(0xFF7B1FA2), RoundedCornerShape(8.dp))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "নেটিভ এড প্লেসহোল্ডার (Test Ad Demo Layout)",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = ComposeColor(0xFFFFEB3B) // Bright yellow headline
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = "রিয়েল নেটিভ এড লোড করতে AdMob একাউন্টের লাইভ প্লেসমেন্ট আইডি ও রিলিজ বিল্ড ব্যবহার করুন।",
            fontSize = 12.sp,
            color = ComposeColor(0xFFEDE7F6), // Clean light lavender contrast text
            modifier = Modifier.padding(horizontal = 8.dp),
            lineHeight = 16.sp
        )
        Spacer(modifier = Modifier.height(12.dp))
        Button(
            onClick = onRefresh,
            colors = ButtonDefaults.buttonColors(containerColor = primaryOrange),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp)
        ) {
            Text(text = "এড লোড রিফ্রেশ (Refresh Ad)", fontSize = 12.sp, color = ComposeColor.White)
        }
    }
}

fun isNetworkAvailable(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager ?: return false
    val nw = connectivityManager.activeNetwork ?: return false
    val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
    return when {
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
        else -> false
    }
}

@Composable
fun SplashScreen() {
    val context = LocalContext.current
    val imageBitmap = remember {
        try {
            context.assets.open("img_splash.png").use { inputStream ->
                BitmapFactory.decodeStream(inputStream)?.asImageBitmap()
            }
        } catch (e: Exception) {
            null
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(ComposeColor.Black), // Premium black background
        contentAlignment = Alignment.Center
    ) {
        if (imageBitmap != null) {
            Image(
                bitmap = imageBitmap,
                contentDescription = "Splash Screen Image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit
            )
        }
    }
}
