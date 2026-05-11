<div align="center">

<img src="https://raw.githubusercontent.com/Sourasamanta/ScreenShots/main/TryGrocery/Demo.gif" width="280" alt="TryGrocery App Demo"/>

# 🛒 TryGrocery

**A full-featured grocery shopping Android application built with Kotlin, MVVM, Room, LiveData, and a clean multi-screen user flow.**

[![Platform](https://img.shields.io/badge/Platform-Android-3DDC84?style=flat-square&logo=android)](https://developer.android.com)
[![Language](https://img.shields.io/badge/Language-Kotlin-7F52FF?style=flat-square&logo=kotlin)](https://kotlinlang.org)
[![Architecture](https://img.shields.io/badge/Architecture-MVVM-orange?style=flat-square)](https://developer.android.com/topic/architecture)
[![Database](https://img.shields.io/badge/Database-Room-blue?style=flat-square)](https://developer.android.com/training/data-storage/room)
[![Min SDK](https://img.shields.io/badge/Min_SDK-24-green?style=flat-square)](https://developer.android.com/studio/releases/platforms)

</div>

---

## 📌 Overview

**TryGrocery** is a complete Android grocery shopping app that demonstrates a practical e-commerce flow: mobile login, product browsing, category filtering, cart management, checkout, order placement, persistent local storage, and dark/light theme support.

The project is built using **Kotlin** and follows the **MVVM architecture pattern**, with **Room Database** for local persistence and **LiveData/ViewModel** for lifecycle-aware UI updates.

---

## 📱 Demo & Screenshots

<div align="center">

### App Demo

<img src="https://raw.githubusercontent.com/Sourasamanta/ScreenShots/main/TryGrocery/Demo.gif" width="260" alt="TryGrocery Demo GIF"/>

<br/><br/>

| Login | Login - Dark Mode |
|:---:|:---:|
| <img src="https://raw.githubusercontent.com/Sourasamanta/ScreenShots/main/TryGrocery/First.jpeg" width="220" alt="TryGrocery Login Screen"/> | <img src="https://raw.githubusercontent.com/Sourasamanta/ScreenShots/main/TryGrocery/FirstDark.jpeg" width="220" alt="TryGrocery Login Screen Dark Mode"/> |

| Home / Product Grid | Home - Dark Mode |
|:---:|:---:|
| <img src="https://raw.githubusercontent.com/Sourasamanta/ScreenShots/main/TryGrocery/Second.jpeg" width="220" alt="TryGrocery Home Screen"/> | <img src="https://raw.githubusercontent.com/Sourasamanta/ScreenShots/main/TryGrocery/SecondDark.jpeg" width="220" alt="TryGrocery Home Screen Dark Mode"/> |

| Cart | Navigation Drawer |
|:---:|:---:|
| <img src="https://raw.githubusercontent.com/Sourasamanta/ScreenShots/main/TryGrocery/Third.jpeg" width="220" alt="TryGrocery Cart Screen"/> | <img src="https://raw.githubusercontent.com/Sourasamanta/ScreenShots/main/TryGrocery/DrawerDark.jpeg" width="220" alt="TryGrocery Navigation Drawer"/> |

| Checkout | Order Success |
|:---:|:---:|
| <img src="https://raw.githubusercontent.com/Sourasamanta/ScreenShots/main/TryGrocery/ThirdDark.jpeg" width="220" alt="TryGrocery Checkout Screen"/> | <img src="https://raw.githubusercontent.com/Sourasamanta/ScreenShots/main/TryGrocery/FourthDark.jpeg" width="220" alt="TryGrocery Order Success Screen"/> |

</div>

---

## ✨ Features

- **Mobile OTP Login** — two-step login flow with 10-digit mobile validation and simulated OTP verification using `1234`
- **175 Preloaded Products** — grocery products seeded locally across 6 categories
- **Category Navigation Drawer** — filter products by category using a drawer-based category menu
- **Product Grid** — 2-column `RecyclerView` product listing with product image, price, unit, and add controls
- **Quantity Stepper** — product card button changes from `Add` to `− quantity +` after adding an item
- **Live Cart Badge** — toolbar cart icon updates automatically when cart quantity changes
- **Cart Management** — update quantity, delete items, and view real-time cart total
- **Checkout Flow** — customer name, phone, address validation, and payment method selection
- **Order Placement** — stores order and order items locally using Room Database
- **Order Success Screen** — displays generated order ID with staggered animation
- **Persistent Cart & Orders** — cart, orders, and order items are stored in SQLite through Room
- **Dark / Light Mode** — drawer-based theme toggle persisted with `SharedPreferences`

---

## 🏗️ Architecture

TryGrocery follows the **MVVM (Model–View–ViewModel)** architecture pattern.

```text
┌─────────────────────────────────────────────────────┐
│                      UI LAYER                        │
│  LoginFragment  HomeFragment  CartFragment           │
│  CheckoutFragment  OrderSuccessFragment              │
│  ProductAdapter  CartAdapter                         │
└────────────────────┬────────────────────────────────┘
                     │ observes LiveData / calls methods
┌────────────────────▼────────────────────────────────┐
│                  VIEWMODEL LAYER                     │
│  LoginViewModel   HomeViewModel   CartViewModel      │
│  MediatorLiveData for category filtering             │
└────────────────────┬────────────────────────────────┘
                     │ suspend functions / coroutines
┌────────────────────▼────────────────────────────────┐
│                REPOSITORY LAYER                      │
│                GroceryRepository                     │
└──────┬──────────────────────────────┬───────────────┘
       │                              │
┌──────▼──────┐              ┌────────▼────────┐
│ GroceryDao  │              │ CartDao         │
│ categories  │              │ OrderDao        │
│ items       │              │ cart/orders     │
└──────┬──────┘              └────────┬────────┘
       │                              │
       └──────────┬───────────────────┘
┌────────────────▼────────────────────────────────────┐
│              ROOM DATABASE / SQLITE                  │
│  categories · items · cart_items · orders            │
│  order_items                                         │
└─────────────────────────────────────────────────────┘
```

---

## 🗂️ Project Structure

```text
com.example.trygrocery/
├── MainActivity.kt
│
├── data/
│   ├── AppDatabase.kt
│   ├── CategoryEntity.kt
│   ├── ItemEntity.kt
│   ├── CartItemEntity.kt
│   ├── OrderEntity.kt
│   ├── OrderItemEntity.kt
│   ├── CategoryWithItems.kt
│   ├── GroceryDao.kt
│   ├── CartDao.kt
│   ├── OrderDao.kt
│   ├── GroceryRepository.kt
│   └── DatabaseSeeder.kt
│
├── ui/
│   ├── login/
│   │   ├── LoginFragment.kt
│   │   └── LoginViewModel.kt
│   │
│   ├── home/
│   │   ├── HomeFragment.kt
│   │   ├── HomeViewModel.kt
│   │   └── ProductAdapter.kt
│   │
│   ├── cart/
│   │   ├── CartFragment.kt
│   │   ├── CartAdapter.kt
│   │   └── CartViewModel.kt
│   │
│   ├── checkout/
│   │   └── CheckoutFragment.kt
│   │
│   └── order/
│       └── OrderSuccessFragment.kt
│
res/
├── layout/
│   ├── activity_main.xml
│   ├── fragment_login.xml
│   ├── fragment_home.xml
│   ├── item_product.xml
│   ├── fragment_cart.xml
│   ├── item_cart.xml
│   ├── fragment_checkout.xml
│   └── fragment_order_success.xml
│
├── values/
│   ├── colors.xml
│   └── themes.xml
│
└── values-night/
    └── colors.xml
```

---

## 🛠️ Tech Stack

| Technology | Usage |
|---|---|
| **Kotlin** | Primary programming language |
| **MVVM** | Application architecture pattern |
| **Room** | SQLite ORM for local database persistence |
| **LiveData** | Reactive UI updates |
| **MediatorLiveData** | Category-based product filtering |
| **ViewModel** | Lifecycle-aware UI state management |
| **Coroutines** | Background database operations |
| **RecyclerView** | Product grid and cart list |
| **DrawerLayout** | Category drawer and theme toggle |
| **AppCompatDelegate** | Dark/light theme switching |
| **SharedPreferences** | Persisting theme preference |
| **FragmentManager** | Manual fragment navigation and back stack handling |

---

## 🗄️ Database Schema

```text
categories                  items
──────────                  ─────
categoryId PK        ←──    categoryId FK
categoryName                itemId PK
categoryImage               itemName
                            price
                            unit
                            itemImage
                            description
                            isAvailable

cart_items                  orders
──────────                  ──────
itemId PK                   orderId PK autoGen
itemName                    orderDate
price                       totalAmount
unit                        customerName
itemImage                   deliveryAddress
categoryId                  phoneNumber
quantity                    paymentMethod
                            status

order_items
───────────
id PK autoGen
orderId
itemId
itemName
price
quantity
unit
```

> `cart_items.itemId` is used as a non-auto-generated primary key. This keeps only one row per product in the cart and allows the app to update quantity instead of inserting duplicate cart rows.

---

## 🔄 App Flow

```text
Launch App
    │
    ▼
MainActivity
    │
    ├── Reads saved theme preference
    └── Applies dark/light mode
    │
    ▼
LoginFragment
    │
    ├── Enter 10-digit mobile number
    ├── Validate mobile number
    ├── Show OTP field
    └── Enter OTP 1234
    │
    ▼
HomeFragment
    │
    ├── View product grid
    ├── Filter products by category
    ├── Toggle dark/light mode
    └── Add products to cart
    │
    ▼
CartFragment
    │
    ├── Update item quantity
    ├── Remove cart items
    └── Continue to checkout
    │
    ▼
CheckoutFragment
    │
    ├── Validate customer details
    ├── Select payment method
    └── Place order
    │
    ▼
OrderSuccessFragment
    │
    ├── Show order confirmation
    ├── Display generated order ID
    └── Navigate back to Home
```

---

## 🚀 How to Run

### Prerequisites

- Android Studio Hedgehog `2023.1.1` or later
- Android SDK 24 or higher
- Kotlin 1.9 or higher
- Gradle sync enabled in Android Studio

### Steps

```bash
# 1. Clone the repository
git clone https://github.com/Sourasamanta/TryGrocery.git

# 2. Open the project folder
cd TryGrocery

# 3. Open in Android Studio
# File → Open → select the TryGrocery folder

# 4. Sync Gradle
# Click "Sync Now" when Android Studio prompts

# 5. Run the app
# Run → Run 'app' or press Shift + F10
```

### Login Credentials

| Field | Value |
|---|---|
| Mobile Number | Any valid 10-digit number |
| OTP | `1234` |

> The app uses simulated OTP verification. Enter any 10-digit mobile number and use `1234` as the OTP.

---

## 🎨 Dark Mode

Dark mode is handled using `AppCompatDelegate` and `values-night/colors.xml`.

- The dark mode toggle is available inside the navigation drawer
- Theme preference is stored using `SharedPreferences`
- The app applies the saved theme before loading the main UI
- Colors are maintained through resource references instead of hardcoded values

| Token | Light Mode | Dark Mode |
|---|---|---|
| `bg_page` | `#F4FBF4` | `#121212` |
| `bg_card` | `#FFFFFF` | `#1E1E1E` |
| `toolbar_color` | `#388E3C` | `#1B5E20` |
| `text_primary` | `#1A1A1A` | `#F0F0F0` |
| `green_primary` | `#4CAF50` | `#66BB6A` |
| `price_color` | `#16A34A` | `#4ADE80` |

---

## 📦 Key Implementation Details

### Shared CartViewModel

`CartViewModel` is shared across cart-related screens using `activityViewModels()`. This keeps cart state synchronized across the product list, cart screen, and checkout flow.

### Category Filtering with MediatorLiveData

`HomeViewModel` combines the full product list and selected category using `MediatorLiveData`.

```kotlin
val items = MediatorLiveData<List<ItemEntity>>().apply {
    fun refresh() {
        val list = allItems.value ?: return
        val catId = _selectedCategoryId.value
        value = if (catId == null) list else list.filter { it.categoryId == catId }
    }

    addSource(allItems) { refresh() }
    addSource(_selectedCategoryId) { refresh() }
}
```

### Room Upsert Pattern

The cart uses `@Insert(onConflict = OnConflictStrategy.REPLACE)` with `itemId` as the primary key. This allows the app to update an existing cart item instead of inserting duplicate rows.

### Order Success Animation

The success screen uses staggered view animations with `OvershootInterpolator` to create a simple order-completion effect.

---

## 📋 Main Dependencies

```kotlin
// Core Android
implementation("androidx.core:core-ktx:1.12.0")
implementation("androidx.appcompat:appcompat:1.7.1")
implementation("com.google.android.material:material:1.11.0")
implementation("androidx.constraintlayout:constraintlayout:2.1.4")
implementation("androidx.drawerlayout:drawerlayout:1.2.0")
implementation("androidx.recyclerview:recyclerview:1.3.2")

// Room
implementation("androidx.room:room-runtime:2.6.1")
implementation("androidx.room:room-ktx:2.6.1")
kapt("androidx.room:room-compiler:2.6.1")

// ViewModel + LiveData
implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")

// Coroutines
implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
```

---

## 🤝 Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss the proposed update.

---

## 📄 License

```text
MIT License — free to use, modify, and distribute.
```

---

<div align="center">

Made with ❤️ using **Kotlin**, **Room**, **MVVM**, and **LiveData**

⭐ Star this repository if you found it useful.

</div>
