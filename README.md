# Clean Architecture Notes App

A modern Android notes application built with Clean Architecture principles, showcasing best practices in Android development using Jetpack Compose, Room Database, and Work Manager.

## 📱 Features

- **Note Management**: Create, edit, delete, and organize your notes
- **Colorful Notes**: Customize notes with different color themes
- **Wishlist/Favorites**: Mark important notes as favorites
- **Smart Sorting**: Sort notes by Title, Date, Color, or Favorites in Ascending/Descending order
- **Work Manager Demo**: Demonstrates background tasks with image download and color filtering
- **Material Design 3**: Modern UI with Material 3 components
- **Dark/Light Theme**: Automatic theme switching based on system settings
- **Dynamic Colors**: Support for dynamic colors on Android 12+

## 🏗️ Architecture

This project follows **Clean Architecture** principles, separating the codebase into three main layers:

### Presentation Layer

- **UI Components**: Jetpack Compose screens and components
- **ViewModels**: State management using MVVM pattern
- **State & Events**: Sealed classes for state and event handling

### Domain Layer

- **Use Cases**: Business logic for note operations
- **Repository Interface**: Abstraction for data operations
- **Models**: Domain entities
- **Utilities**: Order types and sorting logic

### Data Layer

- **Repository Implementation**: Concrete implementation of repository interface
- **Data Sources**: Room DAO for database operations
- **Database**: Room database with initialization callbacks
- **Remote**: Retrofit API service for network operations

## 🛠️ Tech Stack

### Core Technologies

- **Kotlin**: 100% Kotlin codebase
- **Jetpack Compose**: Modern declarative UI toolkit
- **Material Design 3**: Latest Material Design components

### Architecture Components

- **Room Database**: Local data persistence
- **ViewModel**: Lifecycle-aware state management
- **LiveData/StateFlow**: Reactive data streams
- **Navigation Compose**: Type-safe navigation

### Dependency Injection

- **Dagger Hilt**: Dependency injection framework

### Background Processing

- **Work Manager**: Background task execution
- **Coroutines**: Asynchronous programming

### Networking & Image Loading

- **Retrofit**: HTTP client for API calls
- **Coil**: Image loading library for Compose

### Serialization

- **Kotlinx Serialization**: Type-safe serialization
- **Gson**: JSON parsing for navigation arguments

## 📁 Project Structure

```
app/src/main/java/com/cleanarchitecturenotesapp/
├── di/                          # Dependency Injection
│   └── AppModule.kt
├── feature_note/                # Notes Feature Module
│   ├── data/
│   │   ├── data_source/         # Room Database & DAO
│   │   └── respositoryImpl/     # Repository Implementation
│   ├── domain/
│   │   ├── exceptions/         # Domain exceptions
│   │   ├── model/               # Domain models
│   │   ├── repository/         # Repository interfaces
│   │   ├── use_case/           # Business logic use cases
│   │   └── util/               # Domain utilities
│   └── presentation/
│       ├── add_edit_note/       # Add/Edit Note Screen
│       ├── notes/               # Notes List Screen
│       │   └── components/      # Reusable components
│       └── util/                # Presentation utilities
├── feature_work_manager/        # Work Manager Feature Module
│   ├── data/remote/            # API services
│   ├── domain/workmanager/     # Workers
│   └── presentation/           # Work Manager UI
├── ui/theme/                    # UI Theme & Components
│   ├── appComponents/          # Custom UI components
│   ├── Color.kt                # Color definitions
│   ├── Theme.kt                # Material Theme
│   └── Type.kt                 # Typography
└── NoteApp.kt                  # Application class
```

## 📋 Prerequisites

Before you begin, ensure you have the following installed:

- **Android Studio**: Hedgehog (2023.1.1) or later
- **JDK**: 11 or higher
- **Android SDK**: API 26 (Android 8.0) or higher
- **Gradle**: 8.9.3 or compatible version

## 🚀 Getting Started

### Installation

1. **Clone the repository**

   ```bash
   git clone https://github.com/yourusername/CleanArchitectureNotesApp.git
   cd CleanArchitectureNotesApp
   ```

2. **Open in Android Studio**

   - Open Android Studio
   - Select "Open an Existing Project"
   - Navigate to the cloned directory and select it

3. **Sync Gradle**

   - Android Studio will automatically sync Gradle dependencies
   - Wait for the sync to complete

4. **Run the app**
   - Connect an Android device or start an emulator (API 26+)
   - Click the "Run" button or press `Shift + F10`

### Build Configuration

The project uses:

- **Min SDK**: 26 (Android 8.0)
- **Target SDK**: 35
- **Compile SDK**: 35
- **Kotlin**: 2.1.20
- **Gradle**: 8.9.3

## 💻 Usage

### Creating a Note

1. Tap the floating action button (FAB) on the Notes screen
2. Enter a title and content
3. Select a color for your note
4. Tap the save button (checkmark icon)

### Editing a Note

1. Tap on any note from the list
2. Modify the title, content, or color
3. Tap the save button to update

### Sorting Notes

1. Tap the menu icon (three lines) in the top-right corner
2. Select sorting criteria:
   - **Title**: Alphabetical order
   - **Date**: Chronological order
   - **Color**: By color value
   - **Favourites**: Favorited notes first
3. Choose **Ascending** or **Descending** order

### Managing Favorites

- Tap the heart icon on any note to add/remove it from favorites

### Work Manager Demo

1. Navigate to "Work Manager Concept" from the main screen
2. Tap "Start Download" to begin image download and filtering
3. Watch the progress indicators for download and filter operations

## 🧪 Testing

The project includes basic test structure:

- **Unit Tests**: Located in `app/src/test/`
- **Instrumented Tests**: Located in `app/src/androidTest/`

Run tests using:

```bash
./gradlew test          # Unit tests
./gradlew connectedAndroidTest  # Instrumented tests
```

### Database

The app uses Room database with automatic initialization. Sample notes are populated on first launch.

### Work Manager

Work Manager is configured with Hilt for dependency injection. Workers require network connectivity for image download.

## 📝 Code Documentation

All methods in the project are documented with KDOC comments following Kotlin documentation standards. Each method includes:

- Purpose description
- Parameter documentation (`@param`)
- Return value documentation (`@return`)
- Exception documentation (`@throws`) where applicable

### Code Style

- Follow Kotlin coding conventions
- Use meaningful variable and function names
- Add KDOC comments for public methods
- Keep functions focused and single-purpose

## 🙏 Acknowledgments

- Jetpack Compose team for the amazing UI toolkit
- Android Architecture Components
- Material Design team
- All open-source libraries used in this project

**Built with ❤️ using Clean Architecture and Jetpack Compose**
