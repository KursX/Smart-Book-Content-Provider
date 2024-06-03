# Smart Book ContentProvider Demo

![Demo](https://github.com/KursX/Smart-Book-Content-Provider/blob/main/demo.gif)



# API

books -> content://com.kursx.smartbook/books

|language|name|author|cover|deeplink
|-|-|-|-|-|
|en|Alice in Wonderland|Lewis Carroll|content://com.kursx.smartbook/cover/alices_adventures_in_wonderland|smart-book://last-bookmark/alices_adventures_in_wonderland.epub

# Usage

```
contentResolver.query("content://com.kursx.smartbook/books".toUri(), null, null, null, null)?.use { cursor ->  
  if (cursor.moveToFirst()) {
      val language = cursor.getString(cursor.getColumnIndex("language"))
      val name = cursor.getString(cursor.getColumnIndex("name"))  
      val author = cursor.getString(cursor.getColumnIndex("author"))
      val cover = cursor.getString(cursor.getColumnIndex("cover")).toUri()
      val deeplink = cursor.getString(cursor.getColumnIndex("deeplink")).toUri()
  }
}

...

coil.compose.AsyncImage(model = cover)

...

startActivity(Intent(Intent.ACTION_VIEW, deeplink))

```
