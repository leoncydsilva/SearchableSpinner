# Searchable Spinner

![](SampleImages/gif_01.gif)  ![](SampleImages/gif_02.gif)
![](SampleImages/image_01.png) ![](SampleImages/image_02.png)

## Installation

#### 1). Add it in your root build.gradle:
```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
#### 2).  Add this to your module's build.gradle:
```
dependencies {
	        implementation 'com.github.leoncydsilva:SearchableSpinner:$latestSearchableSpinnerVesion'
	}
```
##### Latest MaterialSearchVeiw version is [![](https://jitpack.io/v/leoncydsilva/SearchableSpinner.svg)](https://jitpack.io/#leoncydsilva/SearchableSpinner)

## Usage Kotlin

```kotlin
 val materialSearchView = MaterialSearchView(this)
         
    //Optional Parameters
     materialSearchView.setBackButtonTint(R.color.colorAccent)
     materialSearchView.animationDuration = 1000
     materialSearchView.searchHint = "Search"
     materialSearchView.backButtonDrawable = getDrawable(R.drawable.ic_arrow_back)
     materialSearchView.clearSearchOnDismiss = false
     materialSearchView.showKeyBoardDefault = false

     materialSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Toast.makeText(this@KotlinActivity, query, Toast.LENGTH_SHORT).show()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Toast.makeText(this@KotlinActivity, newText, Toast.LENGTH_SHORT).show()
                return false
            }

        })
```
```
        //Sample usage with toolbar menu
        val menu = materialToolbar.menu
        menu.findItem(R.id.searchMenuIcon).setOnMenuItemClickListener {
            Toast.makeText(this, "Showing Search from ToolbarMenu", Toast.LENGTH_LONG).show()

            //The method call where magic happens. 
            materialSearchView.show(findViewById(R.id.searchMenuIcon));false
        }
```
```
        //Sample usage with ImageView
        imageViewSearch.setOnClickListener {
            Toast.makeText(this, "Showing Search from ImageView", Toast.LENGTH_LONG).show()

            //The method call where magic happens.
            materialSearchView.show(it)
        }
```

## Usage Java

```
MaterialToolbar materialToolbar = findViewById(R.id.materialToolbar);
        ImageView imageView = findViewById(R.id.imageViewSearch);

final MaterialSearchView materialSearchView = new MaterialSearchView(this);
     
   //Optional Parameters
    materialSearchView.setBackButtonTint(R.color.colorAccent);
    materialSearchView.setAnimationDuration(1000);
    materialSearchView.setSearchHint("Search");
    materialSearchView.setBackButtonDrawable(getDrawable(R.drawable.ic_arrow_back));
    materialSearchView.setClearSearchOnDismiss(false);
    materialSearchView.setShowKeyBoardDefault(false);
    materialSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(JavaActivity.this, query, Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Toast.makeText(JavaActivity.this, newText, Toast.LENGTH_SHORT).show();
                return false;
            }
        });
```
```
       //Simple usage with toolbar menu
        Menu menu = materialToolbar.getMenu();
        menu.findItem(R.id.searchMenuIcon).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Toast.makeText(JavaActivity.this, "Showing Search from ToolbarMenu", Toast.LENGTH_LONG).show();
                
                //The method call where magic happens.
                materialSearchView.show(findViewById(R.id.searchMenuIcon));
                return false;
            }
        });
```
```

        //Sample usage with ImageView
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(JavaActivity.this, "Showing Search from ImageView", Toast.LENGTH_LONG).show();

                //The method call where magic happens.
                materialSearchView.show(v);
            }
        });
    }
```

## Public Methods

#### Kotlin and Java
```java
setBackButtonTint(@ColorRes Int color);
setAnimationDuration(Int animationDuration);
setSearchHint(String string);
setBackButtonDrawable(Drawable drawable);
setClearSearchOnDismiss(Boolean isClearSearchNeeded);
setShowKeyBoardDefault(Bolean showKeyboardDefalut);
setOnQueryTextListener(SearchView.OnQueryTextListener listener);
```
## License
[MIT](https://github.com/leoncydsilva/SearchableSpinner/blob/master/LICENSE)