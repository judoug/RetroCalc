# RetroCalc 🧮

Retro Calculator for Horizon OS — your sleek, powerful companion for precise, lightning-fast calculations. Tap, type, swipe—then watch as your numbers come alive on a beautifully minimalist interface crafted just for Quest devices. Whether you're crunching numbers at work, school, or for play, Retro Calculator brings efficiency right where you need it. This sleek 2D panel app is built for lightning-fast calculations and multi-tasking .


![RetroCalc](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Kotlin](https://img.shields.io/badge/kotlin-%237F52FF.svg?style=for-the-badge&logo=kotlin&logoColor=white)
![Compose](https://img.shields.io/badge/Compose-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white)
![Quest](https://img.shields.io/badge/Meta%20Quest-00D4FF?style=for-the-badge&logo=meta&logoColor=white)

## ✨ Features

### 🎨 Retro Design
- **Cream & Orange Aesthetic** - Authentic retro calculator colors
- **Neumorphic Buttons** - Soft inner shadows and gradient backgrounds
- **Portrait Layout** - Classic 5×4 keypad with wide 0 key
- **Olive Display** - Vintage LCD-style display with tape text

### 🧮 Calculator Logic
- **Smart AC/C** - All Clear when pristine, Clear current entry when in use
- **Percentage Calculations** - Context-aware percentage operations
- **Repeated Equals** - Repeat last operation with last right operand
- **BigDecimal Math** - Precise decimal arithmetic with proper formatting
- **Error Handling** - Division by zero protection with clear error states

### 🎮 Quest Controller Support
- **D-pad Navigation** - Full arrow key navigation between keys
- **Pointer Interaction** - Quest controller pointer support with hover states
- **Haptic Feedback** - Tactile feedback on all button presses
- **Focus Rings** - 8dp focus indicators for comfortable targeting
- **Accessibility** - Full TalkBack and screen reader support

### 📱 Platform Optimized
- **Horizon OS Ready** - Runs as 2D floating window app
- **Arm64 Only** - Optimized for Quest hardware
- **Size Optimized** - < 15MB APK with comprehensive optimization
- **No Dependencies** - Zero Google Play Services requirements

## 🚀 Quick Start

### Building the APK

```bash
# Clone the repository
git clone https://github.com/yourusername/RetroCalc.git
cd RetroCalc

# Build debug APK (for sideloading)
./gradlew assembleDebug

# Build release APK (requires signing)
./gradlew assembleRelease
```

### Installing on Quest

1. **Enable Developer Mode** on your Quest
2. **Connect via USB** to your PC
3. **Install via Meta Quest Developer Hub** or ADB:
   ```bash
   adb install app/build/outputs/apk/debug/app-debug.apk
   ```
4. **Launch** from Quest app library as floating window

## 🎯 Calculator Layout

```
┌─────┬─────┬─────┬─────┐
│  C  │  ±  │  %  │  ÷  │
├─────┼─────┼─────┼─────┤
│  7  │  8  │  9  │  ×  │
├─────┼─────┼─────┼─────┤
│  4  │  5  │  6  │  −  │
├─────┼─────┼─────┼─────┤
│  1  │  2  │  3  │  +  │
├─────┴─────┼─────┼─────┤
│     0     │  .  │  =  │
└───────────┴─────┴─────┘
```

## 🧪 Testing

### Unit Tests
```bash
./gradlew test
```

### UI Tests
```bash
./gradlew connectedAndroidTest
```

### Test Coverage
- ✅ 12 + 7 = = → 26 (repeated equals)
- ✅ 42 × 10 = → 420 (multiplication)
- ✅ 5 + 50% = → 7.5 (percentage calculation)
- ✅ 9 ÷ 0 = → Error (division by zero)
- ✅ AC/C behavior and sign toggle
- ✅ Decimal input and operation chaining

## 🛠️ Technical Details

### Architecture
- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Math Engine**: BigDecimal with MathContext(12, HALF_UP)
- **State Management**: Compose State with remember
- **Theme**: Material3 with custom retro color scheme

### Dependencies
- `androidx.compose:compose-bom:2023.10.01`
- `androidx.compose.material3:material3`
- `androidx.activity:activity-compose`
- `androidx.lifecycle:lifecycle-runtime-ktx`

### Build Configuration
- **Min SDK**: 26 (Android 8.0)
- **Target SDK**: 34 (Android 14)
- **Compile SDK**: 34
- **Architecture**: Arm64-v8a only
- **Build Tools**: Gradle 8.4, Kotlin 1.9.20

## 📁 Project Structure

```
RetroCalc/
├── app/
│   ├── src/main/
│   │   ├── java/com/example/retrocalc/
│   │   │   ├── MainActivity.kt
│   │   │   ├── CalculatorScreen.kt
│   │   │   ├── CalcEngine.kt
│   │   │   └── ui/
│   │   │       ├── components/RetroComponents.kt
│   │   │       └── theme/
│   │   ├── res/
│   │   │   ├── drawable/ (icons, backgrounds)
│   │   │   ├── mipmap-anydpi-v26/ (adaptive icons)
│   │   │   └── values/ (colors, themes, strings)
│   │   └── AndroidManifest.xml
│   ├── src/test/ (unit tests)
│   ├── src/androidTest/ (UI tests)
│   └── build.gradle.kts
├── gradle/
├── QUEST_DEPLOYMENT.md
├── SHIP_CHECKLIST.md
└── README.md
```

## 🎮 Quest-Specific Features

### Controller Navigation
- **D-pad Support**: Navigate between keys with arrow keys
- **Activation**: Enter/Space/A buttons activate focused keys
- **Focus Traversal**: Logical tab order through all keys
- **Hover States**: Visual feedback for Quest controller pointer

### Window Management
- **Floating Window**: Runs as 2D app in portrait mode
- **Resizable**: Users can resize and reposition window
- **Portrait Lock**: Orientation enforced for optimal layout

### Performance
- **60fps Target**: Smooth animations and interactions
- **Memory Efficient**: Minimal memory footprint
- **Fast Startup**: Optimized launch time

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- **Meta Quest** for the amazing VR platform
- **Jetpack Compose** for the modern UI framework
- **Material Design** for the design system inspiration
- **iOS Calculator** for the behavior reference

## 📞 Support

If you encounter any issues or have questions:

1. Check the [QUEST_DEPLOYMENT.md](QUEST_DEPLOYMENT.md) for Quest-specific help
2. Review the [SHIP_CHECKLIST.md](SHIP_CHECKLIST.md) for feature verification
3. Open an issue on GitHub
4. Check existing issues for solutions

---

**Made with ❤️ for the Meta Quest community**
