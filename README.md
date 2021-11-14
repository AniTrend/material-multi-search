# Material multi search &nbsp; [![](https://jitpack.io/v/anitrend/material-multi-search.svg)](https://jitpack.io/#anitrend/material-multi-search) &nbsp; [![Codacy Badge](https://api.codacy.com/project/badge/Grade/6a3fadd09a404229a9b649bbcb9415dd)](https://www.codacy.com/manual/AniTrend/material-multi-search?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=AniTrend/material-multi-search&amp;utm_campaign=Badge_Grade) &nbsp; [![Build Status](https://travis-ci.com/AniTrend/material-multi-search.svg?branch=master)](https://travis-ci.com/AniTrend/material-multi-search)

A reworked code base fork of [MultiSearchView](https://github.com/iammert/MultiSearchView) with additional customization:
- Configurable search icon
- Configurable selection indicator
- Configurable search item delete icon
- Configurable search animation duration

> For additional changes please see [CHANGELOG.md](https://github.com/anitrend/material-multi-search/blob/master/CHANGELOG.md)

[![FOSSA Status](https://app.fossa.com/api/projects/git%2Bgithub.com%2FAniTrend%2Fmaterial-multi-search.svg?type=large)](https://app.fossa.com/projects/git%2Bgithub.com%2FAniTrend%2Fmaterial-multi-search?ref=badge_large)

All design credits goes to [Cuberto](https://dribbble.com/cuberto) And inspired from [this design](https://dribbble.com/shots/5922034-Multi-search-by-categories)

![](https://raw.githubusercontent.com/anitrend/material-multi-search/develop/art/multisearch.gif)

## Setup
```gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}

dependencies {
    implementation 'co.github.anitrend:material-multi-search:latest'
}
```

## Usage

How to set up the library in your project [setup guide here](https://jitpack.io/#anitrend/material-multi-search) and see the `sample` app project for a working demo

### 1. Add the view into your layout file
```xml
    <co.anitrend.multisearch.ui.MultiSearch
        android:id="@+id/multiSearch"
        android:layout_width="match_parent"
        android:layout_height="wrap_content" />
```

### 2. Setup callbacks for multi-search events
```kotlin
class MainActivity : AppCompatActivity() {

    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private fun onSearchStateChanged(search: Search) {
        when (search) {
            is Search.TextChanged ->
                Timber.i("changed: ${search.text}")
            is Search.Removed ->
                Timber.i("removed: ${search.index}")
            is Search.Selected ->
                Timber.i("selected: ${search.index} -> ${search.text}")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        lifecycleScope.launchWhenResumed {
            binding.multiSearch.searchChangeFlow()
                .filterNotNull()
                .onEach {
                    onSearchStateChanged(it)
                }
                .catch { e ->
                    // for any uncaught exceptions
                    Timber.w(e)
                }
                .collect()
        }
    }
}
```

Should result in something similar to this:

![](https://raw.githubusercontent.com/anitrend/material-multi-search/develop/art/art.webp)

## Customize

If you need customize multi-search, firstly you should add style set under `styles.xml`
```xml
     <!-- Search Text Style. -->
    <style name="MultiSearchEditTextStyle">
        <!-- Custom values write to here for SearchEditText. -->
        <item name="android:focusable">true</item>
        <item name="android:focusableInTouchMode">true</item>
        <item name="android:enabled">true</item>
        <item name="android:hint">Search</item>
        <item name="android:imeOptions">actionSearch</item>
        <item name="android:textSize">18sp</item>
        <item name="android:maxLength">15</item>
        <item name="android:inputType">textCapSentences</item>
        <item name="android:textColorHint">#80999999</item>
        <item name="android:textColor">#000</item>
    </style>
```

Thereafter, you should give style set to app:searchTextStyle under MultiSearchView

```xml
    <co.anitrend.multisearch.ui.MultiSearch
        android:id="@+id/multiSearch"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:searchTextAppearance="@style/MultiSearchEditTextStyle" />

```

If you need to customize icons, and other properties for multi search the following styles are available

```xml
    <declare-styleable name="MultiSearch">
        <!-- drawable shape resource to use for selection indicator -->
        <attr name="searchSelectionIndicator" format="reference" />

        <!-- Search text appearance as a style resource -->
        <attr name="searchTextAppearance" format="reference" />

        <!-- Search icon, the default that will be used is a 24dp vector image-->
        <attr name="searchIcon" format="reference" />

        <!-- Search term remove icon, the default is a 10dp vector image -->
        <attr name="searchRemoveIcon" format="reference" />

        <!-- Search animation effect duration, the default is 500ms -->
        <attr name="searchAnimationDuration" format="integer" />
    </declare-styleable>
```


License
--------


    Copyright 2019 AniTrend

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.