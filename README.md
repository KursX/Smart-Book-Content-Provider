# Smart Book ContentProvider Demo

![Demo](https://www.dropbox.com/scl/fi/m7qxu9ovun1y4qbwyihfo/smart_book_content_provider.gif?rlkey=akpbdxfpjgwnle4cahowjvyc4&st=pj97reyl&dl=1)



# API

books -> content://com.kursx.smartbook/books

|language|name|author|cover|deeplink
|-|-|-|-|-|
|en|Alice in Wonderland|Lewis Carroll|content://com.kursx.smartbook/cover/alices_adventures_in_wonderland|smart-book://last-bookmark/alices_adventures_in_wonderland.epub

# Usage

```
contentResolver.query("content://com.kursx.smartbook/books".toUri(), null, null, null, null)?.use { cursor ->  
  if (cursor.moveToFirst()) {
	  val language = cursor.getString(languageIndex)
      val name = cursor.getString(nameIndex)  
      val author = cursor.getString(authorIndex)
      val cover = cursor.getString(coverIndex).toUri()
      val deeplink = cursor.getString(deeplinkIndex).toUri()
  }
}
```
