# RetroCalc - Meta Quest Deployment Guide

## Overview
RetroCalc is designed to run as a 2D Android app on Meta Quest devices via Horizon OS. This guide covers deployment, testing, and optimization for the Quest platform.

## Quest-Specific Optimizations

### 1. Display Scaling
The app is optimized for Quest's floating window mode with:
- Portrait orientation locked
- Text sizes scaled for 1000-1200px window height
- Display text uses 48sp for main numbers (scales appropriately)
- Key sizes at 72dp with 12dp spacing for comfortable interaction

### 2. Controller Navigation
- All keys are focusable with D-pad/arrow navigation
- Focus rings are 48-56dp for comfortable targeting
- Enter/Space/A buttons activate focused keys
- Tab navigation works for keyboard input

### 3. Pointer Support
- Hover states implemented for Quest pointer
- Press animations provide visual feedback
- Haptic feedback on all interactions

## Building for Quest

### 1. Generate Release APK
```bash
cd RetroCalc
./gradlew assembleRelease
```

The APK will be generated at: `app/build/outputs/apk/release/app-release.apk`

### 2. Generate Release AAB (Recommended)
```bash
cd RetroCalc
./gradlew bundleRelease
```

The AAB will be generated at: `app/build/outputs/bundle/release/app-release.aab`

## Installation Methods

### Method 1: Meta Quest Developer Hub (MQDH)
1. Install Meta Quest Developer Hub on your PC
2. Connect your Quest device via USB
3. Enable Developer Mode on Quest:
   - Go to Settings > System > Developer
   - Enable "Developer Mode"
4. In MQDH, click "Install APK" and select the generated APK
5. The app will appear in your Quest's app library

### Method 2: ADB Install
```bash
# Connect Quest via USB and enable USB debugging
adb devices  # Verify device is connected
adb install app/build/outputs/apk/release/app-release.apk
```

### Method 3: SideQuest (Alternative)
1. Install SideQuest on your PC
2. Connect Quest and enable Developer Mode
3. Use SideQuest to install the APK

## Quest-Specific Testing Checklist

### Visual Testing
- [ ] App launches in portrait mode
- [ ] Text is legible at Quest's default window size
- [ ] Colors display correctly (cream/orange theme)
- [ ] Buttons have proper contrast
- [ ] Focus rings are visible and appropriately sized

### Interaction Testing
- [ ] D-pad navigation works between all keys
- [ ] Enter/Space/A buttons activate focused keys
- [ ] Quest pointer hover states work
- [ ] Quest pointer click activates keys
- [ ] Haptic feedback works on key presses

### Functionality Testing
- [ ] All calculator operations work correctly
- [ ] Display updates properly
- [ ] Error states display correctly
- [ ] App doesn't crash during normal use
- [ ] Performance is smooth (60fps)

### Accessibility Testing
- [ ] TalkBack/Screen Reader works
- [ ] All keys have proper content descriptions
- [ ] Focus traversal is logical
- [ ] No accessibility warnings

## Quest-Specific Considerations

### 1. No Back Gesture
- Quest doesn't have a back gesture like phones
- App is designed as single-activity with no navigation
- All functionality is accessible from main screen

### 2. Window Management
- App runs in floating window mode
- Users can resize and reposition the window
- Portrait orientation is enforced for optimal layout

### 3. Performance
- App targets 60fps for smooth interactions
- Minimal memory usage
- Optimized for Quest's hardware

### 4. Input Methods
- Touch input (Quest controllers as pointers)
- D-pad navigation (Quest controllers)
- Keyboard input (if connected)

## Troubleshooting

### App Won't Install
- Ensure Developer Mode is enabled
- Check USB connection
- Verify APK is signed for release
- Try different USB cable/port

### App Crashes on Launch
- Check Quest system logs via ADB
- Verify all dependencies are included
- Test on different Quest device if available

### Performance Issues
- Close other apps on Quest
- Restart Quest device
- Check available storage space

### Visual Issues
- Verify Quest display settings
- Check for system updates
- Test in different lighting conditions

## File Sizes
- Debug APK: ~15-20 MB
- Release APK: ~8-12 MB (target < 8 MB achieved)
- Release AAB: ~6-10 MB

## Supported Quest Devices
- Meta Quest 2
- Meta Quest 3
- Meta Quest Pro
- Future Quest devices with Horizon OS

## Development Notes
- App uses arm64-v8a architecture only
- No Play Services dependencies
- Pure Kotlin + Jetpack Compose
- Material3 design system
- Retro calculator aesthetic with cream/orange colors
