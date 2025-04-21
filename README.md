# 🎨 ShadeIt

**Smart Color Generator for Your Perfect UI**

ShadeIt is an intuitive Android app built for developers and designers to simplify the color selection process in UI development. Whether you're designing from scratch or working on an existing mockup, ShadeIt helps you generate stunning **solid** and **gradient** colors tailored to your UI needs.

---

## 📱 Features

- 🎨 **Solid & Gradient Color Generator**  
  Generate beautiful solid and gradient color palettes for your projects.

- 🖼️ **AI-Powered Color Suggestions from UI Designs**  
  Upload UI mockups/screenshots with a short project description and get personalized color suggestions based on image content and context.

- ☁️ **Cloud-Based Storage**  
  - **Firebase Firestore** for storing color palettes  
  - **Supabase Storage** for uploaded UI images

- 🤖 **AI Integrations**  
  - **Gemini API** for extracting content and context from uploaded UI images  
  - **Groq AI** for analyzing design and suggesting appropriate color schemes

- 🔐 **Secure Authentication**  
  Firebase Authentication handles user login and security.

- 🌐 **Offline Support**  
  - Color generation requires internet connection  
  - Previously generated colors are cached and available offline

- 🧠 **Clean Architecture (MVVM)**  
  Scalable and maintainable architecture with a clear separation of concerns.

---

## 🛠️ Tech Stack

| Layer | Technology |
|-------|------------|
| **UI Development** | Kotlin, Jetpack Compose |
| **Architecture** | MVVM (Model-View-ViewModel) |
| **Authentication** | Firebase Authentication |
| **Database** | Firebase Firestore |
| **Image Storage** | Supabase Storage |
| **API Communication** | Retrofit |
| **AI for Image Understanding** | Gemini API |
| **AI for Color Suggestion** | Groq |
| **Offline Capability** | Local caching (SharedPreferences)* |

> \* Assumes SharedPreferences is used for offline caching of previously generated colors.

---

## 🚀 **Getting Started**

Here’s a step-by-step guide to run the TaskMate app, with detailed instructions and accompanying illustrations to make it easier to follow.

---

### 🛠️ **Installation**

#### 1. 📥 **Clone the Repository**

Open a terminal or Git Bash and enter the following command to clone the repository:
     
     ```
     git clone https://github.com/Shaurya-Bajpai/TaskMate.git
     ```

---

#### 2.  📂 **Open the Cloned Folder in Android Studio**
   - Open **Android Studio**.  
   - Click on **File > Open** and navigate to the folder where the project was cloned.  
   - Select the folder and click **OK**. Android Studio will load the project.

---

#### 3. 📱 **Prepare Your Smartphone for Debugging (Optional)**
   - **Connect your smartphone** to your computer using a USB cable.  
   - **Enable USB Debugging**:  
     1. Go to **Settings > About Phone** on your device.  
     2. Tap **Build Number** 7 times to unlock **Developer Options**.  
     3. Navigate to **Settings > Developer Options** and turn on **USB Debugging**.  

---

#### 4. 📱 **Run the Project**
   - In Android Studio, click the **Run** button (▶️) or press **Shift + F10**.  
   - Choose your connected smartphone as the target device. Android Studio will build and run the app on your phone.  

---

#### 5. 📲 **See the Result**
   - Once the app is deployed, it will open automatically on your smartphone. You can now explore the TaskMate app.  

2. Set up Firebase and Supabase credentials
3. Configure Gemini and Groq API access
4. Build and run the app using Android Studio


