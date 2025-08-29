# 📷 AuricScan - Modern QR Code Scanner & Generator
### Internship Project @ SkillCraft Technology

AuricScan is a modern, high-performance QR code utility for Android, built entirely with Kotlin and Jetpack Compose. It provides a seamless user experience for both scanning and creating QR codes, with a focus on clean UI, robust functionality, and offline capabilities. The app features real-time scanning, context-aware actions, and a persistent scan history.

---
---

## 📸 Screenshots

| Splash Screen                                       | Home Screen                                       | Generator Screen                                        | Scanner Screen                                      | History Screen                                      |
| --------------------------------------------------- | ------------------------------------------------- | ------------------------------------------------------- | --------------------------------------------------- | --------------------------------------------------- |
| ![1 Splash Screen](https://github.com/user-attachments/assets/b2985ab2-2894-45c3-b08c-7f1d3c6bd8b1) | ![2 Home Page](https://github.com/user-attachments/assets/eb55e47d-ccbd-4c4e-b6ec-13eca21c1178) | ![4 Generate QR Code](https://github.com/user-attachments/assets/c1175c87-90cc-445a-93ca-02bef77fa3b0) | ![3 Scan QR Code](https://github.com/user-attachments/assets/b7664c40-bf5c-4722-9332-c4d115227f47)   | ![5 History](https://github.com/user-attachments/assets/3cfd1469-e380-462e-bf8e-0eef2045c654)        |

---

## 🚀 Features

| Feature                  | Description                                                                                                                              |
| ------------------------ | ---------------------------------------------------------------------------------------------------------------------------------------- |
| ⚡ **Real-Time Scanning** | Uses **CameraX** and **ML Kit** to instantly detect and decode QR codes from a live camera feed.                                          |
| ✨ **Modern UI/UX**      | Built entirely with **Jetpack Compose & Material 3** for a smooth, reactive, and visually appealing user interface.                        |
| 📝 **QR Code Generation**  | Create custom QR codes from any text or URL. The generated image can be saved to the device gallery or shared with other apps.          |
| 🧠 **Context-Aware Actions**| The app intelligently analyzes scanned content to provide relevant actions, such as "Open in Browser" for URLs or "Search on Web" for text.|
| 💾 **Persistent History**  | Automatically saves all scanned QR codes to a local **Room database**. Users can view, manage, and delete their entire scan history.   |
| 🔐 **Secure Sharing**    | Uses Android's `FileProvider` to securely share generated QR code images without exposing file paths.                                    |
| 📱 **Adaptive Design**   | Features a professional adaptive icon and a modern splash screen that works seamlessly on all Android devices (Android 12+).             |

---

## 🛠 Tech Stack

-   **Tech:** Kotlin, Coroutines, Flow
-   **Architecture:** MVVM (Model-View-ViewModel), AndroidViewModel
-   **UI:** Jetpack Compose, Material 3, Compose Navigation
-   **Camera & Image Analysis:**
    -   CameraX: For the live camera feed and lifecycle management.
    -   Google ML Kit: For high-performance, on-device QR code detection.
-   **Data Persistence:**
    -   Room: For creating and managing the local SQLite database for scan history.
-   **Utilities:**
    -   ZXing (core): For generating QR code bitmap data from text.
    -   Modern Splash Screen API

---

## 🔧 Installation

1.  Clone the repository:
    ```bash
    git clone https://github.com/therahuls916/SCT_AD_04.git
    ```
2.  Navigate to the project directory:
    ```bash
    cd SCT_AD_04
    ```
3.  Open the project in the latest stable version of Android Studio, let Gradle sync, and click ▶️ **Run**.

---

## 📂 Folder Structure

```plaintext
app/src/main/java/com/rahul/auric/auricscan/
├── data/
│   ├── AppDatabase.kt
│   ├── ScanHistory.kt
│   └── ScanHistoryDao.kt
├── generator/
│   └── GeneratorViewModel.kt
├── history/
│   └── HistoryViewModel.kt
├── scanner/
│   └── ScannerViewModel.kt
├── screens/
│   ├── GeneratorScreen.kt
│   ├── HistoryScreen.kt
│   ├── HomeScreen.kt
│   └── ScannerScreen.kt
├── splashscreen/
│   └── SplashScreenActivity.kt
├── ui/
│   ├── components/
│   │   ├── ActionCard.kt
│   │   ├── BottomNavBar.kt
│   │   ├── TopBar.kt
│   │   └── ViewfinderOverlay.kt
│   └── theme/
│       ├── Color.kt
│       ├── Shape.kt
│       ├── Theme.kt
│       └── Type.kt
├── AppNavigation.kt
└── MainActivity.kt

---

## 🔐 Permissions Used

| Permission                | Reason                                                                                                  |
| ------------------------- | ------------------------------------------------------------------------------------------------------- |
| `android.permission.CAMERA` | Essential for accessing the device's camera to provide the live preview for scanning QR codes.        |

---

## 🧠 How It Works

-   **UI Layer:** The entire UI is built with Jetpack Compose. Screens are stateless Composables that receive data and events from their respective ViewModels. The UI observes `StateFlow` from the ViewModels and automatically recomposes when data changes.
-   **State Management:** ViewModels hold and manage the screen state (e.g., text input, scanned results, history lists). This separation of concerns follows the principles of modern Android architecture.
-   **Scanning Engine:** `CameraX` provides a stable, lifecycle-aware camera preview. On each frame, `ML Kit`'s Barcode Scanning API analyzes the image for QR codes in real-time. A state-based "gate" in the `ScannerViewModel` prevents multiple detections of the same code.
-   **Database:** Room acts as an abstraction layer over a local SQLite database. It stores all successfully scanned codes with a timestamp. The `HistoryViewModel` uses a `Flow` to observe the database, ensuring the UI always displays the most up-to-date list of scans.

---

## ✅ Planned Features

-   [ ] ✨ Add an animated "lock-on" effect to the viewfinder when a QR code is detected.
-   [ ] 🖼️ Implement "Scan from Image" functionality, allowing users to select an image from their gallery.
-   [ ] 🎨 Add more context-aware actions (e.g., "Connect to Wi-Fi", "Add Contact", "Open in Maps").
-   [ ] 📋 Add a "Copy" button to the History items.
-   [ ] 🌙 Implement a Light/Dark theme toggle.

---

## 🤝 Contributing

Contributions are welcome! If you'd like to help, please fork the repository, create a new branch for your feature or fix, and submit a pull request.

---

## 📄 License

This project is licensed under the MIT License - see the `LICENSE` file for details.

---

## 👨‍💻 Developer

**Rahul Salunke**  
[GitHub](https://github.com/therahuls916) | [LinkedIn](https://www.linkedin.com/in/rahulasalunke/)