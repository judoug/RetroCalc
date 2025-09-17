# RetroCalc ProGuard Rules

# Keep Compose metadata and runtime
-keep class androidx.compose.** { *; }
-keep class kotlin.Metadata { *; }
-keep class kotlinx.coroutines.** { *; }

# Keep BigDecimal usage for calculator math
-keep class java.math.BigDecimal { *; }
-keep class java.math.MathContext { *; }
-keep class java.math.RoundingMode { *; }

# Keep calculator engine classes
-keep class com.example.retrocalc.CalcEngine { *; }
-keep class com.example.retrocalc.CalcState { *; }
-keep class com.example.retrocalc.Op { *; }

# Keep MainActivity and UI components
-keep class com.example.retrocalc.MainActivity { *; }
-keep class com.example.retrocalc.CalculatorScreen { *; }
-keep class com.example.retrocalc.ui.components.** { *; }

# Keep theme and color classes
-keep class com.example.retrocalc.ui.theme.** { *; }

# Remove debug information
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile

# Optimize for size
-optimizations !code/simplification/arithmetic,!code/simplification/cast,!field/*,!class/merging/*
-optimizationpasses 5
-allowaccessmodification
-dontpreverify

# Remove unused code
-dontwarn kotlinx.coroutines.**
-dontwarn androidx.compose.**
