# Smart Book ContentProvider Demo

![Demo](https://github.com/KursX/Smart-Book-Content-Provider/blob/main/demo.gif)

[Actual APK for tests](https://github.com/KursX/Smart-Book-Content-Provider/blob/main/app-apk-release.apk)

# API

books -> content://com.kursx.smartbook/books

bookId|language|bookName|author|readProgress|lastReadTime|coverImageUrl                                          |deeplink  
-|-|-|-|-|-|-|-|
1|en|Alice in Wonderland|Lewis Carroll|34|1717439712283|content://com.kursx.smartbook/cover/alices_adventures_in_wonderland |smart-book://last-bookmark/alices_adventures_in_wonderland.epub  

# Usage

```
contentResolver.query("content://com.kursx.smartbook/books".toUri(), null, null, null, null)?.use { cursor ->  
  if (cursor.moveToFirst()) {
      val bookId = cursor.getLong(cursor.getColumnIndex("bookId"))
      val language = cursor.getString(cursor.getColumnIndex("language"))
      val bookName = cursor.getString(cursor.getColumnIndex("bookName"))  
      val author = cursor.getString(cursor.getColumnIndex("author"))
      val coverImageUrl = cursor.getString(cursor.getColumnIndex("coverImageUrl")).toUri()
      val deeplink = cursor.getString(cursor.getColumnIndex("deeplink")).toUri()
      val readProgress = cursor.getInt(cursor.getColumnIndex("readProgress"))
      val lastReadTime = cursor.getLong(cursor.getColumnIndex("lastReadTime"))
  }
}

getContentResolver().registerContentObserver("content://com.kursx.smartbook/books".toUri(), true, object : ContentObserver(null) {  
    override fun onChange(selfChange: Boolean) {  
        contentResolver.query(BOOKS_URI.toUri(), null, null, null, null)
    }
})

...

coil.compose.AsyncImage(model = cover)

...

startActivity(Intent(Intent.ACTION_VIEW, deeplink))

```
