> This project adheres to [Semantic Versioning](http://semver.org/).

Change Log
==========

Version 0.1.3 *(2019-12-27)*
----------------------------

* Fix: Indicator showing up under a search item currently in focus, allowing the item to be clicked. See commit: [67530a2](https://github.com/AniTrend/material-multi-search/commit/67530a24a301b63495663e0d775a48302ce5485a)
* Fix: Event `onItemSelected` receiving a `AppCompatEditText` class name due to `toString` being called on view instead of `text` property. See commit: [886d13f](https://github.com/AniTrend/material-multi-search/commit/886d13f7fd72ed67512d83eb5f8b1325ce3863f4)
* New: Replaced default animation duration with configurable styleable attribute. See commits: [6f98492](https://github.com/AniTrend/material-multi-search/commit/6f9849245526d2405e1a1fb19768d8f6642d2d51) & [8f0ea77](https://github.com/AniTrend/material-multi-search/commit/8f0ea773977214113d7a8bd60df676725af1a265)
* Optimization: Remove redundant edit text view properties which are set programmatically at runtime. See commit: [cbb1515](https://github.com/AniTrend/material-multi-search/commit/cbb15153e53e442b26fc96683f9e8138edcfcceb)


Version 0.1.2 *(2019-12-27)*
----------------------------

* Fix: Call `onItemSelected` when a search item is removed and the selection is changed. See commit: [e8a6441](https://github.com/AniTrend/material-multi-search/commit/e8a6441c2c222d5526be42a55c0aa942813006aa)
* Fix: Set `selectedSearchItemTab` reference to null when it is the only child item in the parent view. See commit: [552bace](https://github.com/AniTrend/material-multi-search/commit/552bace35a4cd55d8f3e9ae31f09b9d033306410)
* Optimization: Remove `EasingInterpolator` library. See commit: [2c513dd](https://github.com/AniTrend/material-multi-search/commit/2c513dd2d122f897ea9be70bd59a5535e40c0587)
* Optimization: Moved `isSearchMode` flag from `MultiSearchContainer` into `MultiSearchPresenter`, allowing usecase to update the state of the view. See commit: [310d369](https://github.com/AniTrend/material-multi-search/commit/310d369666927dfda197f7ba6aa13912f8d0303a)
* Optimization: Removed unused `SimpleAnimatorListener` contract. See commit: [8b18015](https://github.com/AniTrend/material-multi-search/commit/8b18015f8a018d5abc915d97d862e20293754044)


Version 0.1.1 *(2019-12-24)*
----------------------------

**Initial production release**
