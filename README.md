# Github User App
 Aplikasi myGithub ini berguna untuk mecari user github. kita dapat menambahkan user favorite ke dalam database local. Data user github diambil dari API resmi yang disediakan oleh github.

## LINK DEMONSTRASI PROJECT
LINK YOUTUBE = [Demonstrasi Porject](https://youtu.be/wYs7u4dv4RE)

## Features Github User App

- Mencari github user dari developer lain
- Menambahkan user github ke dalam favorite yang mana disimpan ke dalam database local dengan ROOM
- Dark Mode dan Light Mode dengan DataStore
- Menampilkan data user github

## Tech

Aplikasi ini dibangun dengan menggunakan :

- [ROOM / DataStore](https://developer.android.com/training/data-storage/room?hl=id) - DataStore with Room
- [Kotlin](https://kotlinlang.org/) - Kotlin Language
- [Retrofit](https://square.github.io/retrofit/) - Retrofit 
- [MVVM Architecture]()

## Installation

1. Download project
2. Extrack project
3. Open project dengan Android Studio
4. Sync project
5. Run Project

## Jika mengalami error data user tidak muncul (TOKEN EXPIRED SAAT PUSH DI REPO)
6. Generate token github sendiri
7. Copy paste token baru di project pada path (app->com->example->mygithub->data->retrofit->ApiConfig.kt->{AUTH_TOKEN}
8. ganti AUTH_TOKEN dengan token yang baru
9. Run project seperti biasa

## Requirement
1. Minimum Target SDK : API Level 21

## LINK DEMONSTRASI PROJECT
LINK YOUTUBE = [Demonstrasi Porject](https://youtu.be/wYs7u4dv4RE)
