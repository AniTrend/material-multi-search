> This project adheres to [Semantic Versioning](http://semver.org/).

Change Log
==========

Version 0.1.2 *(2019-12-27)*
----------------------------

* Fix: Call `onItemSelected` when a search item is removed and the selection is changed. See commit: e8a6441c2c222d5526be42a55c0aa942813006aa
* Fix: Set `selectedSearchItemTab` reference to null when it is the only child item in the parent view. See commit: 552bace35a4cd55d8f3e9ae31f09b9d033306410 
* Optimization: Remove `EasingInterpolator` library. See commit: 2c513dd2d122f897ea9be70bd59a5535e40c0587
* Optimization: Moved `isSearchMode` flag from `MultiSearchContainer` into `MultiSearchPresenter`, allowing usecase to update the state of the view. See commit: 310d369666927dfda197f7ba6aa13912f8d0303a
* Optimization: Removed unused `SimpleAniMatorListener` contract. See commit: 8b18015f8a018d5abc915d97d862e20293754044


Version 0.1.1 *(2019-12-24)*
----------------------------

**Initial production release**
