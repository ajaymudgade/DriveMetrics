# 🚗 DriveMetrics – Android Automotive Telemetry Dashboard

<p align="center">
  <img src="https://img.shields.io/badge/Android-Automotive-blue?style=for-the-badge"/>
  <img src="https://img.shields.io/badge/Kotlin-100%25-purple?style=for-the-badge"/>
  <img src="https://img.shields.io/badge/Architecture-Clean-green?style=for-the-badge"/>
</p>

---

<p align="center">
  <img src="screenshots/demo.gif" width="300"/>
</p>

---

## ✨ Overview

DriveMetrics is a **real-time automotive dashboard simulation** built for Android Automotive OS.
It replicates a **car instrument cluster** with dynamic UI, smooth animations, and reactive data flow.

---

## 🚀 Features

* 🧭 **Speedometer Gauge (km/h)**
* 🔁 **RPM Gauge (Tachometer)**
* ⛽ **Fuel Level Arc (Dynamic Colors)**
* ⚠️ **Overspeed Warning System**
* 🔴 **High RPM Alert**
* 🎨 **Custom Canvas UI (No XML)**
* 🔄 **Smooth Animations (Interpolation)**
* 🧠 **Clean Architecture + Flow**
* 🧪 **Mock Data Fallback (Emulator Compatible)**

---


## 🧰 Tech Stack

| Layer        | Technology                |
| ------------ | ------------------------- |
| Language     | Kotlin                    |
| Architecture | MVVM + Clean Architecture |
| Async        | Coroutines + Flow         |
| UI           | Canvas (Custom Drawing)   |
| Automotive   | CarPropertyManager        |

---


## ⚙️ How It Works

```text
CarPropertyManager → Repository → ViewModel → Flow → Canvas UI
```

* Uses **CarPropertyManager** for vehicle data
* Falls back to **MockCarDataSource**
* ViewModel exposes data using **StateFlow**
* UI updates in real-time using **Canvas.invalidate()**

---

## 🎯 Key Engineering Highlights

✔ Built **dual gauge cluster (Speed + RPM)** from scratch

✔ Designed **real-time UI rendering using Canvas**

✔ Implemented **smooth animation system (no jitter)**

✔ Created **mock + real data pipeline for Automotive**

✔ Used **Flow for reactive UI updates**

---

## 🧠 Animation Logic

```kotlin
// Smooth animation
smoothValue = oldValue * 0.9f + newValue * 0.1f
```

---

## ⚠️ Limitations

* Real vehicle data requires **OEM permissions**
* Emulator has **limited Automotive support**
* RPM is currently **simulated**

---

## 🧪 Future Improvements

* 🚘 Gear Indicator (P / D / R)
* 🌙 Night Mode UI
* ⚡ Glow Effects
* 🎙️ Voice Integration
* 📊 Telemetry Graphs

---

## 👨‍💻 Author

**Ajay Mudgade**
Android Developer | Automotive Enthusiast

---

## ⭐ Support

If you like this project:

👉 Give it a ⭐ on GitHub
👉 Share with developers

---

<p align="center">
  🚀 Built with passion for Automotive & Android
</p>
