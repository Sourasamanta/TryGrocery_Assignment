<div align="center">

<img src="https://raw.githubusercontent.com/Sourasamanta/ScreenShots/main/TryGrocery/Demo.gif" width="260" alt="TryGrocery Demo"/>

# 🛒 TryGrocery

**A full-featured grocery shopping Android app built with Kotlin & MVVM architecture**

[![Platform](https://img.shields.io/badge/Platform-Android-3DDC84?style=flat-square&logo=android)](https://developer.android.com)
[![Language](https://img.shields.io/badge/Language-Kotlin-7F52FF?style=flat-square&logo=kotlin)](https://kotlinlang.org)
[![Architecture](https://img.shields.io/badge/Architecture-MVVM-orange?style=flat-square)](https://developer.android.com/topic/architecture)
[![Database](https://img.shields.io/badge/Database-Room-blue?style=flat-square)](https://developer.android.com/training/data-storage/room)
[![Min SDK](https://img.shields.io/badge/Min_SDK-24-green?style=flat-square)](https://developer.android.com/studio/releases/platforms)

</div>

---

## 📱 Demo

<div align="center">

| Light Mode | Dark Mode |
|:-----------:|:---------:|
| <img src="https://raw.githubusercontent.com/Sourasamanta/ScreenShots/main/TryGrocery/First.jpeg" width="200" alt="Login Light"/> | <img src="https://raw.githubusercontent.com/Sourasamanta/ScreenShots/main/TryGrocery/FirstDark.jpeg" width="200" alt="Login Dark"/> |
| Login Screen | Login Screen (Dark) |

| Light Mode | Dark Mode |
|:-----------:|:---------:|
| <img src="https://raw.githubusercontent.com/Sourasamanta/ScreenShots/main/TryGrocery/Second.jpeg" width="200" alt="Home Light"/> | <img src="https://raw.githubusercontent.com/Sourasamanta/ScreenShots/main/TryGrocery/SecondDark.jpeg" width="200" alt="Home Dark"/> |
| Home / Product Grid | Home (Dark) |

| Cart Screen | Category Drawer |
|:-----------:|:--------------:|
| <img src="https://raw.githubusercontent.com/Sourasamanta/ScreenShots/main/TryGrocery/Third.jpeg" width="200" alt="Cart Light"/> | <img src="https://raw.githubusercontent.com/Sourasamanta/ScreenShots/main/TryGrocery/DrawerDark.jpeg" width="200" alt="Drawer Dark"/> |
| Cart (Light) | Nav Drawer (Dark) |

| Checkout | Order Success |
|:--------:|:------------:|
| <img src="https://raw.githubusercontent.com/Sourasamanta/ScreenShots/main/TryGrocery/ThirdDark.jpeg" width="200" alt="Checkout Dark"/> | <img src="https://raw.githubusercontent.com/Sourasamanta/ScreenShots/main/TryGrocery/FourthDark.jpeg" width="200" alt="Order Success"/> |
| Checkout (Dark) | Order Placed! (Dark) |

</div>

---

## ✨ Features

- **OTP Login** — Mobile number entry → fake OTP verification (1234) → Home
- **175 Products** across 6 categories (Fruits, Vegetables, Dairy, Beverages, Snacks, Bakery)
- **Category Drawer** — Navigation drawer to filter products by category with animated highlight
- **Quantity Controls** — Add button transforms into `−  count  +` stepper on the product card
- **Cart Icon Badge** — Live count badge on toolbar cart icon
- **Cart Screen** — View all items, adjust quantities, delete items, see running total
- **Checkout Screen** — Name, address, phone validation + 4 payment methods (Cash / Card / UPI / Net Banking)
- **Order Success Animation** — Staggered entrance animation with OvershootInterpolator showing unique Order ID
- **Room Database** — Persistent cart, orders, and order items stored in SQLite via Room ORM
- **Dark Mode / Light Mode** — Toggle inside the drawer; persisted across restarts via SharedPreferences

---

## 🏗️ Architecture

TryGrocery is built on **MVVM (Model–View–ViewModel)** — Google's recommended architecture for Android.

```
┌─────────────────────────────────────────────────────┐
│                      UI LAYER                        │
│  LoginFragment  HomeFragment  CartFragment           │
│  CheckoutFragment  OrderSuccessFragment              │
│  ProductAdapter  CartAdapter                         │
└────────────────────┬────────────────────────────────┘
                     │  observes LiveData / calls functions
┌────────────────────▼────────────────────────────────┐
│                  VIEWMODEL LAYER                     │
│  LoginViewModel   HomeViewModel   CartViewModel      │
│  (MediatorLiveData for category filter)              │
└────────────────────┬────────────────────────────────┘
                     │  suspend functions / coroutines
┌────────────────────▼────────────────────────────────┐
│               REPOSITORY LAYER                       │
│           GroceryRepository                          │
└──────┬──────────────────────────────┬───────────────┘
       │                              │
┌──────▼──────┐              ┌────────▼────────┐
│  GroceryDao  │              │  CartDao         │
│  (items,     │              │  OrderDao        │
│  categories) │              │  (cart_items,    │
└──────┬───────┘              │   orders,        │
       │                      │   order_items)   │
       └──────────┬───────────┘
┌────────────────▼────────────────────────────────────┐
│              ROOM DATABASE  (SQLite)                 │
│  categories · items · cart_items · orders            │
│  order_items                                         │
└─────────────────────────────────────────────────────┘
```

---

## 🗂️ Project Structure

```
com.example.trygrocery/
├── MainActivity.kt                  ← AppCompatActivity, dark mode init, DB seed
│
├── data/
│   ├── AppDatabase.kt               ← Room DB singleton, version 2
│   ├── CategoryEntity.kt            ← @Entity: categories table
│   ├── ItemEntity.kt                ← @Entity: items table (FK → categories)
│   ├── CartItemEntity.kt            ← @Entity: cart_items (itemId as PK)
│   ├── OrderEntity.kt               ← @Entity: orders table
│   ├── OrderItemEntity.kt           ← @Entity: order_items table
│   ├── CategoryWithItems.kt         ← @Relation data class
│   ├── GroceryDao.kt                ← DAO for categories + items
│   ├── CartDao.kt                   ← DAO for cart CRUD + LiveData totals
│   ├── OrderDao.kt                  ← DAO for placing orders
│   ├── GroceryRepository.kt         ← Single data access point for all ViewModels
│   └── DatabaseSeeder.kt            ← 175 pre-loaded products across 6 categories
│
├── ui/
│   ├── login/
│   │   ├── LoginFragment.kt         ← Mobile + OTP two-step flow
│   │   └── LoginViewModel.kt        ← State machine for login steps
│   │
│   ├── home/
│   │   ├── HomeFragment.kt          ← DrawerLayout, grid, badges, dark mode switch
│   │   ├── HomeViewModel.kt         ← MediatorLiveData for category filtering
│   │   └── ProductAdapter.kt        ← RecyclerView with Add/Qty toggle per card
│   │
│   ├── cart/
│   │   ├── CartFragment.kt          ← Cart list, totals, continue to checkout
│   │   ├── CartAdapter.kt           ← Per-item qty controls + delete
│   │   └── CartViewModel.kt         ← Shared ViewModel (activityViewModels)
│   │
│   ├── checkout/
│   │   └── CheckoutFragment.kt      ← Form validation + payment method + place order
│   │
│   └── order/
│       └── OrderSuccessFragment.kt  ← Staggered animation + order ID display
│
res/
├── layout/
│   ├── activity_main.xml            ← Single FragmentContainerView host
│   ├── fragment_login.xml
│   ├── fragment_home.xml            ← DrawerLayout root
│   ├── item_product.xml             ← CardView with Add/Qty toggle
│   ├── fragment_cart.xml
│   ├── item_cart.xml
│   ├── fragment_checkout.xml
│   └── fragment_order_success.xml
├── values/colors.xml                ← Light mode palette
├── values-night/colors.xml          ← Dark mode palette
└── values/themes.xml                ← Theme.AppCompat.DayNight.NoActionBar
```

---

## 🛠️ Tech Stack

| Technology | Usage |
|---|---|
| **Kotlin** | Primary language |
| **MVVM** | Architecture pattern |
| **Room** | SQLite ORM — 5 tables, v2 |
| **LiveData** | Reactive UI updates |
| **MediatorLiveData** | Category filter combining two LiveData sources |
| **ViewModel** | Lifecycle-aware state holders |
| **Coroutines** | Async DB operations on IO dispatcher |
| **DrawerLayout** | Navigation drawer for categories |
| **RecyclerView** | Product grid (GridLayoutManager 2-col) & cart list |
| **AppCompatDelegate** | Programmatic dark/light mode switching |
| **SharedPreferences** | Persisting dark mode preference |
| **FragmentManager** | Manual back-stack navigation |

---

## 🗄️ Database Schema

```
categories          items                  cart_items
──────────          ──────                 ──────────
categoryId PK  ←─  categoryId FK          itemId PK (non-auto)
categoryName        itemId PK              itemName
categoryImage       itemName               price
                    price                  unit
                    unit                   itemImage
                    itemImage              categoryId
                    description            quantity (default 1)
                    isAvailable

orders                          order_items
──────                          ───────────
orderId PK autoGen              id PK autoGen
orderDate (timestamp)           orderId
totalAmount                     itemId
customerName                    itemName
deliveryAddress                 price
phoneNumber                     quantity
paymentMethod                   unit
status (default "Placed")
```

> `cart_items.itemId` is a **non-autoGenerate primary key** — this guarantees one row per product. `upsert` (INSERT OR REPLACE) increments the quantity instead of adding duplicate rows.

---

## 🚀 How to Run

### Prerequisites
- Android Studio Hedgehog (2023.1.1) or later
- Android SDK 24+
- Kotlin 1.9+

### Steps

```bash
# 1. Clone the repository
git clone https://github.com/Sourasamanta/<repo-name>.git

# 2. Open in Android Studio
#    File → Open → select the TryGrocery folder

# 3. Sync Gradle
#    Android Studio will prompt — click "Sync Now"

# 4. Run on emulator or physical device
#    Run → Run 'app'  (Shift+F10)
```

### Login Credentials
| Field | Value |
|---|---|
| Mobile Number | Any 10-digit number |
| OTP | `1234` |

> The app uses a simulated OTP — enter any 10-digit number as the mobile, then enter `1234` as the OTP to log in.

---

## 🔄 App Flow

```
Launch App
    │
    ▼
MainActivity ──── reads SharedPreferences ──── sets Dark/Light Mode
    │
    ▼
LoginFragment
    ├── Enter 10-digit mobile  →  ViewModel validates  →  show OTP step
    └── Enter OTP "1234"       →  navigate to HomeFragment
                                         │
                              ┌──────────┴──────────┐
                              │                     │
                         Open Drawer            Tap Cart Icon
                              │                     │
                    Filter by Category         CartFragment
                    (MediatorLiveData)              │
                                                Continue
                                                    │
                                            CheckoutFragment
                                           (validate form)
                                                    │
                                             Place Order
                                                    │
                                     Room: insert order + items
                                          clear cart
                                     post orderId via LiveData
                                                    │
                                        OrderSuccessFragment
                                     (staggered animation + ID)
                                                    │
                                             Go to Home
                                     (clear back stack completely)
```

---

## 🎨 Dark Mode

Dark mode is implemented using `AppCompatDelegate` + `values-night/colors.xml`:

- Toggle the **Dark Mode switch** inside the navigation drawer
- Preference is saved to `SharedPreferences("settings")` key `"dark_mode"`
- On next launch, mode is applied **before** `super.onCreate()` to prevent flicker
- All colors are defined as `@color/` references — never hardcoded hex

| Token | Light | Dark |
|---|---|---|
| `bg_page` | `#F4FBF4` | `#121212` |
| `bg_card` | `#FFFFFF` | `#1E1E1E` |
| `toolbar_color` | `#388E3C` | `#1B5E20` |
| `text_primary` | `#1A1A1A` | `#F0F0F0` |
| `green_primary` | `#4CAF50` | `#66BB6A` |
| `price_color` | `#16A34A` | `#4ADE80` |

---

## 📦 Key Implementation Details

### CartViewModel — Shared via `activityViewModels()`
`CartViewModel` extends `AndroidViewModel` and is obtained via `activityViewModels()` in three fragments. This means all three fragments share **the same ViewModel instance** — cart state is always in sync.

### MediatorLiveData for Category Filtering
`HomeViewModel` uses `MediatorLiveData` to combine two sources:
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

### Order Success Animation
Staggered entrance using `view.animate()` with `OvershootInterpolator`:
- Circle: scale 0→1 at 0ms
- Check icon: scale 0→1 at 450ms
- Title: translate+fade at 750ms
- Order ID: fade at 1000ms
- Button: fade at 1200ms

### Room Upsert Pattern
`@Insert(onConflict = OnConflictStrategy.REPLACE)` combined with the `itemId` primary key implements upsert — adding the same product multiple times just increments `quantity`.

---

## 📋 Dependencies

```kotlin
// app/build.gradle.kts
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

Pull requests are welcome. For major changes, open an issue first to discuss what you would like to change.

---

## 📄 License

```
MIT License — free to use, modify, and distribute.
```

---

<div align="center">

Made with ❤️ using **Kotlin** · **Room** · **MVVM** · **LiveData**

⭐ Star this repo if you found it useful!

</div>
